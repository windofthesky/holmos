package holmos.webtest;

import holmos.webtest.log.MyLogger;

import java.util.Set;

import org.openqa.selenium.Cookie;

/**浏览器窗口的基本单位<br>
 * 窗口句柄，句柄在整个框架里面唯一，当为webdriver的时候，由底层<br>
 * 框架自动生成，当为Selenium的时候，由Holmos框架生成并管理<br>
 * @author 吴银龙(15857164387)
 * */
public interface BrowserWindow {

	static MyLogger logger=MyLogger.getLogger(BrowserWindow.class);
	/**获取当前窗体的title*/
	public String getTitle();
	public String getHandle();
	/**获得当前窗口当前页面的url<br>*/
	public String getUrl();
	/**对当前窗口的的当前页面进行刷新<br>
	 * 对于webdrvier先将控制权交予用到的窗口<br>
	 * */
	public void refresh();
	/**关闭当前窗口，并销毁其在分配者Allocator里面的资源<br>
	 * 对于webdrvier先将控制权交予用到的窗口<br>*/
	public void close();
	/**模拟浏览器的前进按钮<br>
	 * 对于webdrvier先将控制权交予用到的窗口<br>*/
	public void goForward();
	/**模拟浏览器的后退按钮<br>
	 * 对于webdrvier先将控制权交予用到的窗口<br>*/
	public void goBack();
	/**获取当前窗口的底层驱动引擎类型
	 * @return */
	public EngineType getEngineType();
	/**新开窗口，打开连接url<br>
	 * 对于webdrvier先将控制权交予用到的窗口<br>*/
	public void openNewWindow(String url);
	/**不新开窗口，在当前窗口打开连接url<br>
	 * 对于webdrvier先将控制权交予用到的窗口<br>*/
	public void open(String url);
	/**获得驱动引擎*/
	public SeleniumDriver getDriver();
	/**让当前窗口获得焦点，并且将Allocator.getInstance().currentWindow<br>
	 * 设置为此窗口对象*/
	public void focus();
	/**最大化窗口*/
	public void maxSizeWindow();
	/**移动窗口至(xLocation,xLocation)<br>
	 * @param xLocation x坐标
	 * @param yLocation y坐标
	 * 相对于电脑屏幕的左上角*/
	public void moveWindowTo(int xLocation,int yLocation);
	/**变更窗口的大小为(horizonSize,verticalSize)<br>
	 * @param horizonSize 窗口的宽度
	 * @param verticalSize 窗口的高度*/
	public void resizeTo(int horizonSize,int verticalSize);
	/**在当前窗口用当前窗口拥有的引擎开始开启页面
	 * */
	public void start();
	/**处理此页面Alert弹出框，并返回Alert窗口的内容信息<br>
	 * @return Alert的信息*/
	public String dealAlert();
	/**处理此页面的prompt窗口<br>
	 * @param input prompt 窗口的输入信息
	 * @return prompt窗口上的内容*/
	public String dealPrompt(String input,boolean isYes);
	/**处理此页面的confirm窗口<br>
	 * @param isYes true 点击确认
	 * 		  false 点击取消
	 * @return confirm窗口上的内容
	 * */
	public String dealConfirm(boolean isYes);
	/**当driver类型为webdriver的时候，将driver的功能丧失，释放*/
	public void quit();
	/**
	 * 截图位置放到了
	 * */
	public void TakeScreenshot(String fileName);
	/**在当前窗口添加cookie*/
	public void addCookie(Cookie cookie);
	/**得到当前窗口所在的Driver的cookie*/
	public Set<Cookie> getAllCookies();
	/**得到当前窗口所在的Driver指定名字的Cookie对象*/
	public Cookie getCookieByName(String name);
	/**移除当前窗口所在的Driver指定name的Cookie*/
	public void removeCookieByName(String name);
	/**移除当前窗口所在的Driver具有的所有的Cookie*/
	public void removeAllCookies();
}
