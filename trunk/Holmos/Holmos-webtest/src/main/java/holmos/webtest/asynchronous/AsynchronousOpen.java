package holmos.webtest.asynchronous;

import holmos.webtest.BrowserWindow;
import holmos.webtest.SeleniumBrowserWindow;
import holmos.webtest.WebDriverBrowserWindow;

import org.openqa.selenium.WebDriver;

import com.thoughtworks.selenium.Selenium;
/**
 * 异步操作get
 * */
public class AsynchronousOpen extends Thread{
	private BrowserWindow window;
	private boolean isGetSucceed=false;
	private String url;
	public AsynchronousOpen(BrowserWindow window,String url){
		this.window=window;
		this.url=url;
	}
	@Override
	public void run() {
		if(window instanceof SeleniumBrowserWindow){
			((Selenium)window.getDriver().getEngine()).open(url);
			
		}else if(window instanceof WebDriverBrowserWindow){
			((WebDriver)(window.getDriver().getEngine())).get(url);
			window.setHandle(((WebDriver)(window.getDriver().getEngine())).getWindowHandle());
		}
		setGetSucceed(true);
		
	}
	public boolean isGetSucceed() {
		return isGetSucceed;
	}
	public void setGetSucceed(boolean isGetSucceed) {
		this.isGetSucceed = isGetSucceed;
	}
}
