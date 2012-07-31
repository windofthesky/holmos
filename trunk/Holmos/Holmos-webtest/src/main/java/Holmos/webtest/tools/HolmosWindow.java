package Holmos.webtest.tools;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import Holmos.webtest.Allocator;
import Holmos.webtest.BrowserWindow;
import Holmos.webtest.EngineType;
import Holmos.webtest.SeleniumBrowserWindow;
import Holmos.webtest.SeleniumDriver;
import Holmos.webtest.WebDriverBrowserWindow;

import com.thoughtworks.selenium.Selenium;
/**
 * @author 吴银龙(15857164387)
 * */
public class HolmosWindow {
	protected static Logger logger=Logger.getLogger(HolmosWindow.class);
	static {
		HolmosBaseTools.configLogProperties();
	}
	/**关闭当前分配者所管理的所有浏览器窗口，进行资源的回收，至此分配者仍然存在<br>
	 * 但是分配者无资源进行管理，就是一个光杆司令，当然了，这个分配者可以继续打开<br>
	 * 页面，重新获取其管理资源*/
	public static void closeAllWindows(){
		Allocator.getInstance().closeAllWindows();
	}
	/**在当前窗口上执行一段无参数的js命令<br>
	 * @param javascript 待javascript命令*/
	public static void runJavaScript(String javascript){
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(currentWindow instanceof SeleniumBrowserWindow){
			try{
				((Selenium)currentWindow.getDriver().getEngine()).getEval(javascript);
				logger.info(javascript+"执行成功!");
			}catch(Exception e){
				logger.error(javascript+"执行失败!");
			}
		}else if(currentWindow instanceof WebDriverBrowserWindow){
			try{
				JavascriptExecutor executor=(JavascriptExecutor)(WebDriver)currentWindow.getDriver().getEngine();
				executor.executeScript(javascript);
				logger.info(javascript+"执行成功!");
			}catch (Exception e) {
				logger.error(javascript+"执行失败!");
			}
		}
	}
	/**移动当前窗口至(xLocation,xLocation)<br>
	 * @param xLocation x坐标
	 * @param yLocation y坐标
	 * 相对于电脑屏幕的左上角*/
	public static void moveWindowTo(int xLocation,int yLocation){
		Allocator.getInstance().currentWindow.moveWindowTo(xLocation, yLocation);
	}
	/**变更当前窗口的大小为(horizonSize,verticalSize)<br>
	 * @param horizonSize 窗口的宽度
	 * @param verticalSize 窗口的高度*/
	public static void resizeTo(int horizonSize,int verticalSize){
		Allocator.getInstance().currentWindow.resizeTo(horizonSize, verticalSize);
	}
	/**获得当前窗口当前页面的url*/
	public static String getUrl(){
		return Allocator.getInstance().currentWindow.getUrl();
	}
	/**对当前窗口的的当前页面进行刷新<br>
	 * 对于webdrvier先将控制权交予用到的窗口<br>
	 * */
	public static void refresh(){
		Allocator.getInstance().currentWindow.refresh();
	}
	/**关闭当前窗口，并销毁其在分配者Allocator里面的资源<br>
	 * 对于webdrvier先将控制权交予用到的窗口<br>*/
	public static void close(){
		Allocator.getInstance().currentWindow.close();
	}
	/**对当前窗口模拟浏览器的前进按钮<br>*/
	public static void goForward(){
		Allocator.getInstance().currentWindow.goForward();
	}
	/**对当前窗口模拟浏览器的后退按钮<br>*/
	public static void goBack(){
		Allocator.getInstance().currentWindow.goBack();
	}
	/**获取当前窗口的底层驱动引擎类型
	 * @return EngineType 当前窗口的底层驱动类型*/
	public static EngineType getEngineType(){
		return Allocator.getInstance().currentWindow.getEngineType();
	}
	/**不新开窗口，在当前窗口打开连接url<br>*/
	public static void open(String url){
		Allocator.getInstance().currentWindow.open(url);
	}
	/**获得当前窗口的驱动引擎*/
	public static SeleniumDriver getDriver(){
		return Allocator.getInstance().currentWindow.getDriver();
	}
	/**最大化当前窗口*/
	public static void maxSizeWindow(){
		Allocator.getInstance().currentWindow.maxSizeWindow();
	}
	/**在新的页面开启一个窗口，并将该窗口设置为当前窗口<br>
	 * 默认底层是IE webdriver 
	 * @param url 新开页面的url*/
	public static void openNewWindow(String url){
		Allocator.getInstance().addAndSetBrowserWindow(EngineType.WebDriverIE, url);
		Allocator.getInstance().currentWindow.open(url);
	}
	/**在新的页面开启一个窗口，并将该窗口设置为当前窗口<br>
	 * 根据参数类型来选择浏览器和底层框架<br>
	 * @param url 新开页面的url
	 * @param engineType 浏览器和底层框架类型*/
	public static void openNewWindow(EngineType engineType,String url){
		Allocator.getInstance().addAndSetBrowserWindow(engineType, url);
		Allocator.getInstance().currentWindow.openNewWindow(url);
	}
	/**按照windows顺序，将控制权交予找到的第一个为url的窗口<br>
	 * 匹配方法:全匹配
	 * @param url 目标url<br>*/
	public static void attach(String url){
		Allocator.getInstance().attach(url);
	}
	/**按照windows顺序，将控制权交予找到的第一个为url的窗口<br>
	 * 匹配方法:包含匹配
	 * @param url 目标url<br>*/
	public static void attachByContains(String url){
		Allocator.getInstance().attachByContains(url);
	}
	/**按照windows顺序，将控制权交予找到的第一个为url的窗口<br>
	 * 匹配方法:正则匹配
	 * @param url 目标url<br>*/
	public static void attachByRegular(String url){
		Allocator.getInstance().attachByRegular(url);
	}
	/**按下Shift键,在松开之前一直处于按下状态*/
	public static void shiftKeyDown(){
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(currentWindow instanceof SeleniumBrowserWindow){
			((Selenium)currentWindow.getDriver().getEngine()).shiftKeyDown();
		}else if(currentWindow instanceof WebDriverBrowserWindow){
			Actions action=new Actions((WebDriver)currentWindow.getDriver().getEngine());
			action.keyDown(Keys.SHIFT);
		}
	}
	/**按下Ctrl键,在松开之前一直处于按下状态*/
	public static void ctrlKeyDown(){
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(currentWindow instanceof SeleniumBrowserWindow){
			((Selenium)currentWindow.getDriver().getEngine()).controlKeyDown();
		}else if(currentWindow instanceof WebDriverBrowserWindow){
			Actions action=new Actions((WebDriver)currentWindow.getDriver().getEngine());
			action.keyDown(Keys.CONTROL);
		}
	}
	/**按下Meta键,在松开之前一直处于按下状态*/
	public static void metaKeyDown(){
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(currentWindow instanceof SeleniumBrowserWindow){
			((Selenium)currentWindow.getDriver().getEngine()).metaKeyDown();
		}else if(currentWindow instanceof WebDriverBrowserWindow){
			Actions action=new Actions((WebDriver)currentWindow.getDriver().getEngine());
			action.keyDown(Keys.META);
		}
	}
	/**按下Alt键,在松开之前一直处于按下状态*/
	public static void altKeyDown(){
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(currentWindow instanceof SeleniumBrowserWindow){
			((Selenium)currentWindow.getDriver().getEngine()).altKeyDown();
		}else if(currentWindow instanceof WebDriverBrowserWindow){
			Actions action=new Actions((WebDriver)currentWindow.getDriver().getEngine());
			action.keyDown(Keys.ALT);
		}
	}
	/**松开Shift键,若之前处于松开状态，则没有影响*/
	public static void shiftKeyUp(){
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(currentWindow instanceof SeleniumBrowserWindow){
			((Selenium)currentWindow.getDriver().getEngine()).shiftKeyUp();
		}else if(currentWindow instanceof WebDriverBrowserWindow){
			Actions action=new Actions((WebDriver)currentWindow.getDriver().getEngine());
			action.keyUp(Keys.SHIFT);
		}
	}
	/**松开Ctrl键,若之前处于松开状态，则没有影响*/
	public static void ctrlKeyUp(){
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(currentWindow instanceof SeleniumBrowserWindow){
			((Selenium)currentWindow.getDriver().getEngine()).controlKeyUp();
		}else if(currentWindow instanceof WebDriverBrowserWindow){
			Actions action=new Actions((WebDriver)currentWindow.getDriver().getEngine());
			action.keyUp(Keys.CONTROL);
		}
	}
	/**松开Meta键,若之前处于松开状态，则没有影响*/
	public static void metaKeyUp(){
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(currentWindow instanceof SeleniumBrowserWindow){
			((Selenium)currentWindow.getDriver().getEngine()).metaKeyUp();
		}else if(currentWindow instanceof WebDriverBrowserWindow){
			Actions action=new Actions((WebDriver)currentWindow.getDriver().getEngine());
			action.keyUp(Keys.META);
		}
	}
	/**松开Alt键,若之前处于松开状态，则没有影响*/
	public static void altKeyUp(){
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(currentWindow instanceof SeleniumBrowserWindow){
			((Selenium)currentWindow.getDriver().getEngine()).altKeyUp();
		}else if(currentWindow instanceof WebDriverBrowserWindow){
			Actions action=new Actions((WebDriver)currentWindow.getDriver().getEngine());
			action.keyUp(Keys.ALT);
		}
	}
	/**按下某个键*/
	public static void KeyDown(KeyEvent key){
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(currentWindow instanceof SeleniumBrowserWindow){
			((Selenium)currentWindow.getDriver().getEngine()).keyPressNative(Character.toString(key.getKeyChar()));
		}else if(currentWindow instanceof WebDriverBrowserWindow){
			Actions action=new Actions((WebDriver)currentWindow.getDriver().getEngine());
			action.keyDown(Keys.valueOf(Character.toString(key.getKeyChar())));
		}
	}
	/**松开某个键*/
	public static void KeyUp(KeyEvent key){
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(currentWindow instanceof SeleniumBrowserWindow){
			((Selenium)currentWindow.getDriver().getEngine()).keyDownNative(Character.toString(key.getKeyChar()));
		}else if(currentWindow instanceof WebDriverBrowserWindow){
			Actions action=new Actions((WebDriver)currentWindow.getDriver().getEngine());
			action.keyUp(Keys.valueOf(Character.toString(key.getKeyChar())));
		}
	}

	/**滚动条滚动到顶端*/
	public static void scrollToTop(){
		HolmosWindow.runJavaScript("window.scrollTo(0,0)");
		logger.info("滚动至页面顶端成功!");
	}
	/**滚动条滚动到底端*/
	public static void scrollToBottom(){
		HolmosWindow.runJavaScript("window.scrollTo(0,document.body.scrollHeight)");
		logger.info("滚动至页面底部成功");
	}
	/**滚动条滚动到特定位置*/
	public static void scrollTo(int xLocation,int yLocation){
		HolmosWindow.runJavaScript("window.scrollTo("+xLocation+","+yLocation+");");
	}
	/**这个一定要找相对路径，和testcasestore相对的路径,这个方法默认窗口的title为选择要上传的文件
	 * 默认的title为"文件上传"
	 * @parameter localFilePath:相对于testcasestore的路径*/
	public static void upLoad(String localFilePath){
		StringBuilder message=new StringBuilder(localFilePath+"文件上传");
		try {
			String scriptPath=ConstValue.CURRENDIR+"\\autoItScripts\\upload.exe";
			String filePath=ConstValue.TESTCASESTOREDIR+"\\"+localFilePath;
			if(!new File(scriptPath).exists()){
				message.append(":提供上传功能的脚本不存在!请联系黄庭同学！");
				logger.error(message);
			}else if(!new File(filePath).exists()){
				message.append(":上传文件不存在!请联系黄庭同学！");
				logger.error(message);
			}else{
				String cmd=scriptPath+" "+"选择要上传的文件 "+filePath;
				Runtime.getRuntime().exec(cmd);
				message.append(":脚本启动成功！");
				logger.info(message);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**这个一定要找相对路径，和testcasestore相对的路径,这个方法默认窗口的title为选择要上传的文件
	 * 默认的title为"文件上传"
	 * @parameter windowTitle
	 * @parameter localFilePath:相对于testcasestore的路径
	 * */
	public static void upLoad(String windowTitle,String localFilePath){
		StringBuilder message=new StringBuilder(localFilePath+"文件上传");
		try {
			String scriptPath=ConstValue.CURRENDIR+"\\autoItScripts\\upload.exe";
			String filePath=ConstValue.TESTCASESTOREDIR+"\\"+localFilePath;
			if(!new File(scriptPath).exists()){
				message.append(":提供上传功能的脚本不存在!请联系黄庭同学！");
				logger.error(message);
			}else if(!new File(filePath).exists()){
				message.append(":上传文件不存在!请检查要上传的文件！");
				logger.error(message);
			}else{
				String cmd=scriptPath+" "+windowTitle+" "+filePath;
				Runtime.getRuntime().exec(cmd);
				message.append(":脚本启动成功！");
				logger.info(message);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**这个一定要找相对路径，和testcasestore相对的路径，这个默认窗口的title为"另存为"
	 * @parameter localFilePath:和testcasestore相对的路径
	 * */
	public static void downLoad(String localFilePath){
		StringBuilder message=new StringBuilder(localFilePath+"文件下载");
		try {
			String scriptPath=ConstValue.CURRENDIR+"\\autoItScripts\\download.exe";
			String filePath=ConstValue.TESTCASESTOREDIR+"\\"+localFilePath;
			if(!new File(scriptPath).exists()){
				message.append(":提供上传功能的脚本不存在!请联系黄庭同学！");
				logger.error(message);
			}else{
				String cmd=scriptPath+" "+"另存为 "+filePath;
				Runtime.getRuntime().exec(cmd);
				message.append(":脚本启动成功！");
				logger.info(message);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**这个一定要找相对路径，和testcasestore相对的路径，这个默认窗口的title为
	 * @parameter windowTitle：另存为的window窗口
	 * @parameter localFilePath:和testcasestore相对的路径
	 * */
	public static void downLoad(String windowTitle,String localFilePath){
		StringBuilder message=new StringBuilder(localFilePath+"文件下载");
		try {
			String scriptPath=ConstValue.CURRENDIR+"\\autoItScripts\\download.exe";
			String filePath=ConstValue.TESTCASESTOREDIR+"\\"+localFilePath;
			if(!new File(scriptPath).exists()){
				message.append(":提供上传功能的脚本不存在!请联系黄庭同学！");
				logger.error(message);
			}else{
				String cmd=scriptPath+" "+windowTitle+" "+filePath;
				Runtime.getRuntime().exec(cmd);
				message.append(":脚本启动成功！");
				logger.info(message);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**处理当前页面Alert弹出框，并返回Alert窗口的内容信息<br>
	 * @return Alert的信息*/
	public static String dealAlert(){
		return Allocator.getInstance().currentWindow.dealAlert();
	}
	/**处理当前页面的prompt窗口<br>
	 * @param input prompt 窗口的输入信息
	 * @return prompt窗口上的内容*/
	public static String dealPrompt(String input,boolean isYes){
		return Allocator.getInstance().currentWindow.dealPrompt(input,isYes);
	}
	/**处理当前页面的confirm窗口<br>
	 * @param isYes true 点击确认
	 * 		  false 点击取消
	 * @return confirm窗口上的内容
	 * */
	public static String dealConfirm(boolean isYes){
		return Allocator.getInstance().currentWindow.dealConfirm(isYes);
	}
	/**截图，是当前获得焦点页面的图*/
	public static void TakeScreenshot(String fileName){
		Allocator.getInstance().currentWindow.TakeScreenshot(fileName+".png");
	}
	/**截图，是当前获得焦点页面的图，图的名字为当前时间*/
	public static void takeScreenshot(){
		Allocator.getInstance().currentWindow.TakeScreenshot(System.currentTimeMillis()+".png");
	}
}
