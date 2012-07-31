package Holmos.webtest;

import org.openqa.selenium.android.AndroidDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.iphone.IPhoneDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.thoughtworks.selenium.DefaultSelenium;

/**selenium 驱动引擎类，Holmos框架整合了Selenium1<br>
 * 和webdriver，那么新开窗口的时候用的只需要启动一个SeleniumDriver<br>
 * 引擎即可,默认为WebDriver  Internet Explorer引擎
 * @author 吴银龙(15857164387)
 * */
public class SeleniumDriver {
	public EngineType getType() {
		return type;
	}
	private static final String defaultHost="localhost";
	private static final int defaultPort=4444;
	private static final String seleniumIE="*iexplore";
	private static final String seleniumFirefox="*firefox";
	private static final String seleniumChrome="*googlechrome";
	private static final String seleniumSafari="*safari";
	private static final String seleniumOpera="*opera";
	/**浏览器底层驱动引擎，可以是Selenium1引擎，也可以是Webdriver引擎*/
	private Object engine;
	/**当前驱动的类型*/
	private EngineType type;
	public Object getEngine() {
		return engine;
	}
	private SeleniumDriver(Object engine){
		if(engine==null){
			this.engine=new InternetExplorerDriver();
		}else{
			this.engine=engine;
		}
	}
	/**新建Selenium IE引擎，主页为url
	 * @param url 引擎打开的主页*/
	public static SeleniumDriver createSeleniumIEEngine(String url){
		SeleniumDriver driver=new SeleniumDriver(new DefaultSelenium(defaultHost, defaultPort, seleniumIE, url));
		driver.type=EngineType.SeleniumIE;
		return driver;
	}
	/**新建Selenium firefox引擎，主页为url
	 * @param url 引擎打开的主页*/
	public static SeleniumDriver createSeleniumFireFoxEngine(String url){
		SeleniumDriver driver=new SeleniumDriver(new DefaultSelenium(defaultHost, defaultPort, seleniumFirefox, url));
		driver.type=EngineType.SeleniumFirefox;
		return driver;
	}
	/**新建Selenium chrome引擎，主页为url
	 * @param url 引擎打开的主页*/
	public static SeleniumDriver createSeleniumChromeEngine(String url){
		SeleniumDriver driver=new SeleniumDriver(new DefaultSelenium(defaultHost, defaultPort, seleniumChrome, url));
		driver.type=EngineType.SeleniumChrome;
		return driver;
	}
	/**新建Selenium safari引擎，主页为url
	 * @param url 引擎打开的主页*/
	public static SeleniumDriver createSeleniumSafariEngine(String url){
		SeleniumDriver driver=new SeleniumDriver(new DefaultSelenium(defaultHost, defaultPort, seleniumSafari, url));
		driver.type=EngineType.SeleniumSafari;
		return driver;
	}
	/**新建Selenium opera引擎，主页为url
	 * @param url 引擎打开的主页*/
	public static SeleniumDriver createSeleniumOperaEngine(String url){
		SeleniumDriver driver=new SeleniumDriver(new DefaultSelenium(defaultHost, defaultPort, seleniumOpera, url));
		driver.type=EngineType.SeleniumOpera;
		return driver;
	}
	/**新建WebDriver IE引擎，主页为url
	 * @param url 引擎打开的主页*/
	public static SeleniumDriver createWebDriverIEEngine(){
		SeleniumDriver driver=new SeleniumDriver(new InternetExplorerDriver());
		driver.type=EngineType.WebDriverIE;
		return driver;
	}
	/**新建WebDriver Safari引擎，主页为url
	 * @param url 引擎打开的主页*/
	public static SeleniumDriver createWebDriverSafariEngine() {
		SeleniumDriver driver=new SeleniumDriver(new SafariDriver());
		driver.type=EngineType.WebDriverSafari;
		return driver;
	}
	/**新建WebDriver firefox引擎，主页为url
	 * @param url 引擎打开的主页*/
	public static SeleniumDriver createWebDriverFireFoxEngine(){
		SeleniumDriver driver=new SeleniumDriver(new FirefoxDriver());
		driver.type=EngineType.WebDriverFirefox;
		return driver;
	}
	/**新建WebDriver Chrome引擎，主页为url
	 * @param url 引擎打开的主页*/
	public static SeleniumDriver createWebDriverChromeEngine(){
		SeleniumDriver driver=new SeleniumDriver(new ChromeDriver());
		driver.type=EngineType.WebDriverChrome;
		return driver;
	}
	/**新建WebDriver android引擎，主页为url
	 * @param url 引擎打开的主页*/
	public static SeleniumDriver createWebDriverAndroidEngine(){
		SeleniumDriver driver=new SeleniumDriver(new AndroidDriver());
		driver.type=EngineType.WebDriverAndroid;
		return driver;
	}
	/**新建WebDriver iphone引擎，主页为url
	 * @param url 引擎打开的主页*/
	public static SeleniumDriver createWebDriverIphoneEngine(){
		SeleniumDriver driver=null;
		try {
			driver = new SeleniumDriver(new IPhoneDriver());
			driver.type=EngineType.WebDriverIphone;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return driver;
	}
	/**新建WebDriver IE引擎，主页为url
	 * @param url 引擎打开的主页*/
	public static SeleniumDriver createWebDriverHtmlUnitEngine(){
		SeleniumDriver driver=new SeleniumDriver(new HtmlUnitDriver());
		driver.type=EngineType.HtmlUnit;
		return driver;
	}
	/**根据类型进行添加selenium底层驱动引擎<br>
	 * @param enginetype 浏览器类型
	 * @param url 初始化url
	 * @return 新建的selenium驱动引擎*/
	public static SeleniumDriver createSeleniumEngine(EngineType enginetype,String url){
		switch (enginetype) {
		case SeleniumIE:
			return createSeleniumChromeEngine(url);
		case SeleniumFirefox:
			return createSeleniumFireFoxEngine(url);
		case SeleniumChrome:
			return createSeleniumChromeEngine(url);
		case SeleniumOpera:
			return createSeleniumOperaEngine(url);
		case SeleniumSafari:
			return createSeleniumSafariEngine(url);
		default:
			return null;
		}
	}
	/**根据类型进行添加WebDriver底层驱动引擎<br>
	 * @param enginetype 浏览器类型
	 * @param url 初始化url
	 * @return 新建的WebDriver驱动引擎*/
	public static SeleniumDriver createWebDriverEngine(EngineType enginetype){
		switch (enginetype) {
		case WebDriverIE:
			return createWebDriverIEEngine();
		case WebDriverFirefox:
			return createWebDriverFireFoxEngine();
		case WebDriverChrome:
			return createWebDriverChromeEngine();
		case WebDriverSafari:
			return createWebDriverSafariEngine();
		case WebDriverIphone:
			return createWebDriverIphoneEngine();
		case WebDriverAndroid:
			return createWebDriverAndroidEngine();
		case HtmlUnit:
			return createWebDriverHtmlUnitEngine();
		default:
			return null;
		}
	}
	/**根据类型新建底层驱动引擎<br>
	 * @param enginetype 浏览器类型
	 * @param url 初始化url
	 * @return 新建的底层驱动引擎*/
	public static SeleniumDriver createEngine(EngineType enginetype,String url){
		switch (enginetype) {
		case SeleniumIE:
		case SeleniumChrome:
		case SeleniumFirefox:
		case SeleniumOpera:
		case SeleniumSafari:
			return createSeleniumEngine(enginetype, url);
		case WebDriverAndroid:
		case WebDriverChrome:
		case WebDriverFirefox:
		case WebDriverIE:
		case WebDriverIphone:
		case HtmlUnit:
			return createWebDriverEngine(enginetype);
		default:
			return null;
		}
	}
}
