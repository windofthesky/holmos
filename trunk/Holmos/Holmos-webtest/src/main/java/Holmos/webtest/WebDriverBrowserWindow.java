package Holmos.webtest;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import Holmos.webtest.basetools.HolmosBaseTools;
import Holmos.webtest.basetools.HolmosWindow;
import Holmos.webtest.constvalue.ConfigConstValue;
/**
 * @author 吴银龙(15857164387)
 * */
public class WebDriverBrowserWindow implements BrowserWindow{
	static {
		HolmosBaseTools.configLogProperties();
	}
	/**当前窗口的句柄，此句柄由底层webdriver引擎获取*/
	public WebDriverBrowserWindow(SeleniumDriver driver){
		this.driver= driver;
		this.enginetype=driver.getType();
	}
	private String windowHandle;
	private EngineType enginetype;
	/**获得webdriver页面的句柄*/
	public String getHandle() {
		if(windowHandle==null){
			BrowserWindow windBrowserWindow=Allocator.getInstance().currentWindow;
			if(!windBrowserWindow.equals(this)){
				this.focus();
				windowHandle=((WebDriver)getDriver()).getWindowHandle();
				windBrowserWindow.focus();
			}else{
				windowHandle=((WebDriver)driver.getEngine()).getWindowHandle();
			}
		}
		return windowHandle;
	}

	public void setWidowHandle(String widowHandle) {
		this.windowHandle = widowHandle;
	}

	public void setDriver(SeleniumDriver driver) {
		this.driver = driver;
	}

	/**底层驱动引擎，一定是webdriver类型*/
	private SeleniumDriver driver;
	
	public SeleniumDriver getDriver() {
		// TODO Auto-generated method stub
		return this.driver;
	}

	public String getUrl() {
		// TODO Auto-generated method stub
		focus();
		return ((WebDriver)driver.getEngine()).getCurrentUrl();
	}

	public void refresh() {
		// TODO Auto-generated method stub
		focus();
		((WebDriver)driver.getEngine()).navigate().refresh();
	}

	public void close() {
		// TODO Auto-generated method stub
		focus();
		((WebDriver)driver.getEngine()).close();
	}

	public void goForward() {
		// TODO Auto-generated method stub
		focus();
		((WebDriver)driver.getEngine()).navigate().forward();
	}

	public void goBack() {
		// TODO Auto-generated method stub
		focus();
		((WebDriver)driver.getEngine()).navigate().back();
	}

	public EngineType getEngineType() {
		// TODO Auto-generated method stub
		return this.enginetype;
	}

	public void open(String url) {
		// TODO Auto-generated method stub
		focus();
		MyGetThread getThread=new MyGetThread((WebDriver) driver.getEngine(), url);
		getThread.run();
		int count=0;
		while(count++<ConfigConstValue.defaultWaitCount){
			if(getThread.isGetSucceed)
				break;
			HolmosBaseTools.sleep(1000);
		}
		if(count>ConfigConstValue.defaultWaitCount){
			try{
				getThread.interrupt();
				getThread=null;
			}catch (Exception e) {
				System.out.println("页面加载超时了!但是case继续执行了，不影响~");
			}
		}
	}

	public void focus() {
		// TODO Auto-generated method stub
		if(Allocator.getInstance().currentWindow.equals(this))
			return;
		((WebDriver)driver.getEngine()).switchTo().window(getHandle());
		Allocator.getInstance().currentWindow=this;
	}

	public void maxSizeWindow() {
		// TODO Auto-generated method stub
		String MAXIMIZE_BROWSER_WINDOW = "if (window.screen) {window.moveTo(0, 0);" +
				"window.resizeTo(window.screen.availWidth,window.screen.availHeight);};";
		focus();
		HolmosWindow.runJavaScript(MAXIMIZE_BROWSER_WINDOW);
	}

	public void moveWindowTo(int xLocation, int yLocation) {
		// TODO Auto-generated method stub
		focus();
		StringBuilder message=new StringBuilder("移动窗口位置");
		if(xLocation>=0&&yLocation>=0){
			message.append("x:"+xLocation+"  y:"+yLocation);
			((WebDriver)driver.getEngine()).manage().window().setPosition(new Point(xLocation, yLocation));
			logger.info(message);
		}else{
			message.append("窗口位置设置错误！");
			logger.error(message);
		}
	}

	public void resizeTo(int horizonSize, int verticalSize) {
		// TODO Auto-generated method stub
		focus();
		StringBuilder message=new StringBuilder("窗口重新设置大小");
		if(horizonSize>=0&&verticalSize>=0){
			message.append("宽度:"+horizonSize+"  高度:"+verticalSize);
			((WebDriver)driver.getEngine()).manage().window().setSize(new Dimension(horizonSize, verticalSize));
			logger.info(message);
		}else{
			message.append("窗口大小设置错误！");
			logger.error(message);
		}
	}

	public void start() {
		// TODO Auto-generated method stub
//		((WebDriver)getDriver().getEngine()).get(getUrl())
	}
	
	public String dealAlert() {
		// TODO Auto-generated method stub
		String alertMessage=null;
		focus();
		try{
			Alert alert=((WebDriver)getDriver().getEngine()).switchTo().alert();
			alertMessage= alert.getText();
			alert.accept();
			return alertMessage;
		}catch (Exception e) {
			logger.error("没有找到Alert窗口!");
		}
		return null;
	}

	public String dealPrompt(String input,boolean isYes) {
		// TODO Auto-generated method stub
		String promptMessage=null;
		focus();
		try{
			Alert alert=((WebDriver)getDriver().getEngine()).switchTo().alert();
			alert.sendKeys(input);
			promptMessage= alert.getText();
			if(isYes)
				alert.accept();
			else 
				alert.dismiss();
			return promptMessage;
		}catch (Exception e) {
			logger.error("没有找到Prompt窗口!");
		}return null;
	}

	public String dealConfirm(boolean isYes) {
		// TODO Auto-generated method stub
		String confirmMessage=null;
		focus();
		try{
			Alert alert=((WebDriver)getDriver().getEngine()).switchTo().alert();
			confirmMessage=alert.getText();
			if(isYes)alert.accept();
			else alert.dismiss();
			alert.accept();
			return confirmMessage;
		}catch (Exception e) {
			logger.error("没有找到Prompt窗口!");
		}return null;
	}

	@Override
	public void openNewWindow(String url) {
		// TODO Auto-generated method stub
		open(url);
	}
	public class MyGetThread extends Thread{
		private WebDriver driver;
		private boolean isGetSucceed=false;
		private String url;
		public MyGetThread(WebDriver driver,String url){
			this.driver=driver;
			this.url=url;
		}
		@Override
		public void run() {
			driver.get(url);
			isGetSucceed=true;
			windowHandle=driver.getWindowHandle();
		}
	}
	@Override
	public void quit() {
		((WebDriver)driver.getEngine()).quit();
	}
	/**
	 * 截图位置放到了
	 * */
	public void TakeScreenshot(String fileName){
		TakesScreenshot TCS=(TakesScreenshot) ((WebDriver)driver.getEngine());
		File screenShot=TCS.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(screenShot, new File(Allocator.getInstance().getScreenShotDir()+fileName));
			logger.info("截图成功!");
		} catch (IOException e) {
			logger.error("截图失败!");
			e.printStackTrace();
		}
	}
}
