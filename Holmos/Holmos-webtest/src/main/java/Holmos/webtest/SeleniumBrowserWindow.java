package holmos.webtest;

import holmos.webtest.basetools.HolmosBaseTools;
import holmos.webtest.basetools.HolmosWindow;

import com.thoughtworks.selenium.Selenium;
/**
 * @author 吴银龙(15857164387)*/
public class SeleniumBrowserWindow implements BrowserWindow{
	static {
		HolmosBaseTools.configLogProperties();
	}
	/**当前窗口的句柄，此句柄Holmos框架产生*/
	private String windowHandle;
	private EngineType engineType;
	public SeleniumBrowserWindow(SeleniumDriver driver){
		this.driver=driver;
		this.engineType=driver.getType();
	}
	/**获得以Selenium为引擎的窗口的句柄
	 * @return 以Selenium为引擎的窗口的句柄*/
	public String getHandle() {
		if(windowHandle==null){
			windowHandle=SeleniumBrowserWindowHandleProductor.getInstance().productHandle();
		}
		return windowHandle;
	}

	public void setWindowHandle(String windowHandle) {
		this.windowHandle = windowHandle;
	}

	public void setDriver(SeleniumDriver driver) {
		this.driver = driver;
	}

	public SeleniumDriver getDriver() {
		// TODO Auto-generated method stub
		return this.driver;
	}

	/**底层驱动引擎，一定是selenium类型*/
	private SeleniumDriver driver;
	
	public String getUrl() {
		// TODO Auto-generated method stub
		return ((Selenium)driver.getEngine()).getLocation();
	}

	public void refresh() {
		// TODO Auto-generated method stub
		((Selenium)driver.getEngine()).refresh();
	}

	public void close() {
		// TODO Auto-generated method stub
		((Selenium)driver.getEngine()).close();
		((Selenium)driver.getEngine()).stop();
	}

	public void goForward() {
		// TODO Auto-generated method stub
		((Selenium)driver.getEngine()).runScript("window.history.forward();");
	}

	public void goBack() {
		// TODO Auto-generated method stub
		((Selenium)driver.getEngine()).goBack();
	}

	public EngineType getEngineType() {
		// TODO Auto-generated method stub
		return this.engineType;
	}
	
	public void openNewWindow(String url){
		start();
		open(url);
	}
	public void open(String url) {
		// TODO Auto-generated method stub
		focus();
		((Selenium)driver.getEngine()).open(url);
	}

	public void focus() {
		// TODO Auto-generated method stub
		if(Allocator.getInstance().currentWindow.equals(this))
			return;
		for(BrowserWindow window:Allocator.getInstance().getWindows()){
			if(((SeleniumBrowserWindow)window).getHandle().equalsIgnoreCase(windowHandle)){
				Allocator.getInstance().currentWindow=this;
				((Selenium)getDriver().getEngine()).windowFocus();
			}
		}
	}

	public void maxSizeWindow() {
		// TODO Auto-generated method stub
		focus();
		((Selenium)driver.getEngine()).windowMaximize();
	}

	public void moveWindowTo(int xLocation, int yLocation) {
		// TODO Auto-generated method stub
		focus();
		StringBuilder message=new StringBuilder("移动窗口位置");
		if(xLocation>=0&&yLocation>=0){
			message.append("x:"+xLocation+"  y:"+yLocation);
			String script="window.moveTo("+xLocation+","+yLocation+")";
			HolmosWindow.runJavaScript(script);
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
			String script="window.resizeTo("+horizonSize+","+verticalSize+")";
			HolmosWindow.runJavaScript(script);
			logger.info(message);
		}else{
			message.append("窗口大小设置错误！");
			logger.error(message);
		}
	}

	public void start() {
		// TODO Auto-generated method stub
		((Selenium)getDriver().getEngine()).start();
		((Selenium)driver.getEngine()).useXpathLibrary("javascript-xpath");
	}
	public String dealAlert() {
		// TODO Auto-generated method stub
		focus();
		try{
			return ((Selenium)getDriver().getEngine()).getAlert();
		}catch (Exception e) {
			logger.error("没有找到Alert窗口!");
		}
		return null;
	}
	public String dealPrompt(String input,boolean isYes) {
		focus();
		try{
			((Selenium)getDriver().getEngine()).answerOnNextPrompt(input);
			return ((Selenium)getDriver().getEngine()).getPrompt();
		}catch (Exception e) {
			logger.error("没有找到Prompt窗口!");
		}
		return null;
		// TODO Auto-generated method stub
		
	}
	public String dealConfirm(boolean isYes) {
		focus();
		try{
			if(isYes){
				((Selenium)getDriver().getEngine()).chooseOkOnNextConfirmation();
				return ((Selenium)getDriver().getEngine()).getConfirmation();
			}else{
				((Selenium)getDriver().getEngine()).chooseCancelOnNextConfirmation();
				return ((Selenium)getDriver().getEngine()).getConfirmation();
			}
		}catch (Exception e) {
			logger.error("没有找到Prompt窗口!");
		}
		return null;
	}
	@Override
	public void quit() {
		// TODO Auto-generated method stub
		//do nothing here
	}
	/**
	 * 截图位置放到了
	 * */
	public void TakeScreenshot(String fileName){
		((Selenium)getDriver().getEngine()).captureEntirePageScreenshot(Allocator.getInstance().getScreenShotDir()+fileName, "");
	}
}
