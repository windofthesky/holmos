package holmos.webtest;

import holmos.webtest.basetools.HolmosBaseTools;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.openqa.selenium.WebDriver;

/**最为窗口的全局分配者<br>
 * 管理所有Holmos框架打开的窗口的分配，来实现对窗口的选择<br>
 * 也作为Holmos框架给其他开发者暴露的除了原生框架Selenium和<br>
 * Webdriver之外的唯一开发入口，会暴露出绝大多数底层API接口<br>
 * <br>
 * <em>采用了单件模式，分配者在框架中独一无二，不能有其他的分配者存在</em>
 * @author 吴银龙(15857164387)
 * */
public class Allocator {
	
	/**所有框架已经打开的窗口,分配者就是管理这些窗口，<br>
	 * 包括所有的Selenium和Webdriver打开的窗口<br>*/
	private List<BrowserWindow>windows;
	private String screenShotDir;
	public String getScreenShotDir() {
		return screenShotDir;
	}
	public List<BrowserWindow> getWindows() {
		return windows;
	}
	/**支持多线程调用，allocator是一个线程安全的分配器*/
	private volatile static Allocator allocator;
	/**当前窗口实例，在holmos框架中，只允许当前有一个活动窗口*/
	public BrowserWindow currentWindow;
	private Allocator(){
		screenShotDir=HolmosBaseTools.getScreenShotDirPath();
		HolmosBaseTools.configScreenShotLocation();
		this.windows=new ArrayList<BrowserWindow>();
	}
	/**获得独一无二的分配者，如果不存在，则将分配者初始化*/
	public static Allocator getInstance(){
		if(allocator==null){
			synchronized (Allocator.class) {
				if(allocator==null){
					allocator=new Allocator();
				}
			}
		}
		return allocator;
	}
	/**销毁此分配者，至此建立的单例分配者将不复存在，也是框架进行复位的命令<br>
	 * 这个时候如果重新调用分配者，那么得到的是新建立的分配者，之前分配者管理的<br>
	 * 资源将无法进行管理，对框架来讲也就没有什么意义，在销毁分配者之前，会对之<br>
	 * 前的资源进行销毁和回收，接下来才会销毁分配者<br>*/
	public void distroy(){
		this.currentWindow=null;
		closeAllWindows();
	}
	
	/**关闭当前分配者所管理的所有浏览器窗口，进行资源的回收，至此分配者仍然存在<br>
	 * 但是分配者无资源进行管理，就是一个光杆司令，当然了，这个分配者可以继续打开<br>
	 * 页面，重新获取其管理资源*/
	public void closeAllWindows(){
		for(BrowserWindow window:windows){
			window.close();
		}
//		for(BrowserWindow window:windows){
//			if(window instanceof WebDriverBrowserWindow){
//				((WebDriver)(window.getDriver().getEngine())).quit();
//				break;
//			}
//		}
		windows.clear();
	}
	/**关闭连接为url的第一个窗口，如果没有连接为url<br>
	 * 那么将不会影响管理者的资源，并返回false<br>
	 * 若关闭的是当前窗口，那么将当前窗口变成为第一个打开的窗口，<br>
	 * 若只有这一个窗口，那么等于是closeAllWindows()<br>
	 * @param url 关闭的连接url
	 * @return true 找到了连接为url的窗口，并将其关闭<br>
	 * false 没有找到连接为url的窗口
	 * */
	public boolean closeWindowByUrl(String url){
		for(BrowserWindow window:windows){
			if(window.getUrl().equalsIgnoreCase(url)){
				window.close();
				windows.remove(window);
				if(currentWindow.equals(window)){
					if(windows.size()==0){
						this.currentWindow=null;
					}else{
						this.currentWindow=this.windows.get(0);
					}
				}
				return true;
			}
		}
		return false;
	}
	/**关闭连接为url的所有窗口，如果没有连接为url<br>
	 * 那么将不会影响管理者的资源，并返回false<br>
	 * @param url 关闭的连接url
	 * @return true 找到了连接为url的窗口，并将其关闭<br>
	 * false 没有找到连接为url的窗口
	 * */
	public boolean closeAllWindowsByUrl(String url){
		for(BrowserWindow window:windows){
			if(window.getUrl().equalsIgnoreCase(url)){
				window.close();
				windows.remove(window);
				if(currentWindow.equals(window)){
					if(windows.size()==0){
						this.currentWindow=null;
					}else{
						this.currentWindow=this.windows.get(0);
					}
				}
			}
			return true;
		}
		return false;
	}
	/**当前窗口为webdriver的时候，点击页面链接，<br>
	 * 可能会新开页面，这个时候就需要更新windows<br>
	 * 这个时候，一定是当前窗口的引擎所控制的窗口产生了变化<br>
	 * 支持点击一下新开若干个页面,不一定是一个<br>*/
	public void updateWindows(){
		int i=0;
		for(String handle:((WebDriver)currentWindow.getDriver().getEngine()).getWindowHandles()){
			i=0;
			for(;i<windows.size();i++){
				if(handle.equalsIgnoreCase(windows.get(i).getHandle())){
					break;
				}
			}
			if(i>=windows.size()){
				WebDriverBrowserWindow webDriverBrowserWindow=new WebDriverBrowserWindow(currentWindow.getDriver());
				webDriverBrowserWindow.setWidowHandle(handle);
				windows.add(webDriverBrowserWindow);
			}
		}
	}
	/**按照windows顺序，将控制权交予找到的第一个为url的窗口<br>
	 * 匹配方法:全匹配
	 * @param url 目标url<br>*/
	public void attach(String url){
		for(BrowserWindow window:windows){
			if(window.getUrl().equalsIgnoreCase(url)){
				window.focus();break;
			}
		}
	}
	/**按照windows顺序，将控制权交予找到的第一个为url的窗口<br>
	 * 匹配方法:包含匹配
	 * @param url 目标url<br>*/
	public void attachByContains(String url){
		for(BrowserWindow window:windows){
			if(window.getUrl().contains(url)){
				window.focus();break;
			}
		}
	}
	/**按照windows顺序，将控制权交予找到的第一个为url的窗口<br>
	 * 匹配方法:正则匹配
	 * @param url 目标url<br>*/
	public void attachByRegular(String url){
		Pattern pattern=Pattern.compile(url);
		for(BrowserWindow window:windows){
			if(pattern.matcher(window.getUrl()).matches()){
				window.focus();break;
			}
		}
	}
	/**新建WebDriver IE窗口,但只是单纯的新建<br>
	 * 没有设置成当前窗口*/
	public void addBrowserWindow(){
		addWebDriverIEBrowserWindow();
	}
	/**新建BrowserWindow窗口,但只是单纯的新建<br>
	 * 没有设置成当前窗口*/
	public void addBrowserWindow(EngineType engineType,String url){
		switch (engineType) {
		case WebDriverAndroid:
		case WebDriverChrome:
		case WebDriverFirefox:
		case WebDriverSafari:
		case WebDriverIE:
		case WebDriverIphone:
		case HtmlUnit:
			addWebDriverBrowserWindow(engineType);
			break;
		case SeleniumChrome:
		case SeleniumFirefox:
		case SeleniumIE:
		case SeleniumOpera:
		case SeleniumSafari:
			addSeleniumBrowserWindow(engineType, url);
			break;
		default:
			break;
		}
	}
	/**新建WebDriver IE窗口,但只是单纯的新建和添加<br>
	 * 没有设置成当前窗口*/
	public void addWebDriverIEBrowserWindow(){
		WebDriverBrowserWindow window=new WebDriverBrowserWindow( 
				 SeleniumDriver.createWebDriverIEEngine());
		this.windows.add(window);
	}
	/**新建WebDriver Firefox窗口,但只是单纯的新建和添加<br>
	 * 没有设置成当前窗口*/
	public void addWebDriverFirefoxBrowserWindow(){
		WebDriverBrowserWindow window=new WebDriverBrowserWindow( 
				SeleniumDriver.createWebDriverFireFoxEngine());
		this.windows.add(window);
	}
	/**新建WebDriver Chrome窗口,但只是单纯的新建和添加<br>
	 * 没有设置成当前窗口*/
	public void addWebDriverChromeBrowserWindow(){
		WebDriverBrowserWindow window=new WebDriverBrowserWindow(
				 SeleniumDriver.createWebDriverChromeEngine());
		this.windows.add(window);
	}
	/**新建WebDriver Safari窗口,但只是单纯的新建和添加<br>
	 * 没有设置成当前窗口*/
	public void addWebDriverSafariBrowserWindow(){
		WebDriverBrowserWindow window=new WebDriverBrowserWindow(
				 SeleniumDriver.createWebDriverSafariEngine());
		this.windows.add(window);
	}
	/**新建WebDriver Iphone窗口,但只是单纯的新建和添加<br>
	 * 没有设置成当前窗口*/
	public void addWebDriverIphoneBrowserWindow(){
		WebDriverBrowserWindow window=new WebDriverBrowserWindow(
				SeleniumDriver.createWebDriverIphoneEngine());
		this.windows.add(window);
	}
	/**新建WebDriver Android窗口,但只是单纯的新建和添加<br>
	 * 没有设置成当前窗口*/
	public void addWebDriverAndroidBrowserWindow(){
		WebDriverBrowserWindow window=new WebDriverBrowserWindow( 
				SeleniumDriver.createWebDriverAndroidEngine());
		this.windows.add(window);
	}
	/**新建HtmlUnit Driver，没有实际的浏览器窗口，所哟的操作全在内存中完成，速度特别快<br>
	 * 没有设置当前窗口
	 * */
	private void addHtmlUnitDriverBrowserWindow() {
		WebDriverBrowserWindow window=new WebDriverBrowserWindow( 
				 SeleniumDriver.createWebDriverHtmlUnitEngine());
		this.windows.add(window);
	}
	/**新建Selenium Firefox窗口，但只是单纯的新建和添加<br>
	 * 没有设置成当前窗口*/
	public void addSeleniumFirefoxBrowswerWindow(String url){
		SeleniumBrowserWindow window=new SeleniumBrowserWindow(
				SeleniumDriver.createSeleniumFireFoxEngine(url));
		this.windows.add(window);
	}
	/**新建Selenium Chrome窗口，但只是单纯的新建和添加<br>
	 * 没有设置成当前窗口*/
	public void addSeleniumChromeBrowswerWindow(String url){
		SeleniumBrowserWindow window=new SeleniumBrowserWindow(
				SeleniumDriver.createSeleniumChromeEngine(url));
		this.windows.add(window);
	}
	/**新建Selenium Safari窗口，但只是单纯的新建和添加<br>
	 * 没有设置成当前窗口*/
	public void addSeleniumSafariBrowswerWindow(String url){
		SeleniumBrowserWindow window=new SeleniumBrowserWindow(
				SeleniumDriver.createSeleniumSafariEngine(url));
		this.windows.add(window);
	}
	/**新建Selenium IE窗口，但只是单纯的新建和添加<br>
	 * 没有设置成当前窗口*/
	public void addSeleniumOperaBrowswerWindow(String url){
		SeleniumBrowserWindow window=new SeleniumBrowserWindow(
				SeleniumDriver.createSeleniumOperaEngine(url));
		this.windows.add(window);
	}
	/**新建Selenium IE窗口，但只是单纯的新建和添加<br>
	 * 没有设置成当前窗口*/
	public void addSeleniumIEBrowswerWindow(String url){
		SeleniumBrowserWindow window=new SeleniumBrowserWindow(
				SeleniumDriver.createSeleniumIEEngine(url));
		this.windows.add(window);
	}
	/**新建WebDriver窗口，根据类型参数来判断创建哪种浏览器的<br>
	 * @param engineType 浏览器类型
	 * */
	public void addWebDriverBrowserWindow(EngineType engineType){
		switch (engineType) {
		case WebDriverIE:
			addWebDriverIEBrowserWindow();
			break;
		case WebDriverFirefox:
			addWebDriverFirefoxBrowserWindow();
			break;
		case WebDriverChrome:
			addWebDriverChromeBrowserWindow();
			break;
		case WebDriverSafari:
			addWebDriverSafariBrowserWindow();
		case WebDriverIphone:
			addWebDriverIphoneBrowserWindow();
			break;
		case WebDriverAndroid:
			addWebDriverAndroidBrowserWindow();
			break;
		case HtmlUnit:
			addHtmlUnitDriverBrowserWindow();
		default:
			break;
		}
	}
	
	/**新建Selenium窗口，根据类型参数来判断创建哪种浏览器的<br>
	 * @param engineType 浏览器类型
	 * */
	public void addSeleniumBrowserWindow(EngineType engineType,String url){
		switch (engineType) {
		case SeleniumIE:
			addSeleniumIEBrowswerWindow(url);
			break;
		case SeleniumFirefox:
			addSeleniumFirefoxBrowswerWindow(url);
			break;
		case SeleniumChrome:
			addSeleniumChromeBrowswerWindow(url);
			break;
		case SeleniumSafari:
			addSeleniumSafariBrowswerWindow(url);
			break;
		case SeleniumOpera:
			addSeleniumOperaBrowswerWindow(url);
		default:
			break;
		}
	}
	/**新建窗口，并将此窗口设置为当前窗口<br>
	 * @param engineType 浏览器类型
	 * @param url 当为selenium类型的时候的原始url,当底层是webdriver类型的时候，此值为null*/
	public void addAndSetBrowserWindow(EngineType engineType,String url){
		addBrowserWindow(engineType, url);
		currentWindow=windows.get(windows.size()-1);
	}
	
}
