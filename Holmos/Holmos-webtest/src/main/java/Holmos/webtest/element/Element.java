package Holmos.webtest.element;

import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import Holmos.webtest.Allocator;
import Holmos.webtest.BrowserWindow;
import Holmos.webtest.EngineType;
import Holmos.webtest.SeleniumBrowserWindow;
import Holmos.webtest.WebDriverBrowserWindow;
import Holmos.webtest.basetools.HolmosBaseTools;
import Holmos.webtest.basetools.HolmosWindow;
import Holmos.webtest.constvalue.ConfigConstValue;
import Holmos.webtest.element.locator.Locator;
import Holmos.webtest.element.locator.LocatorChain;
import Holmos.webtest.element.locator.LocatorValue;
import Holmos.webtest.element.property.Location;
import Holmos.webtest.element.tool.SeleniumElementExist;
import Holmos.webtest.element.tool.WebDriverElementExist;
import Holmos.webtest.element.tool.WebElementExist;
import Holmos.webtest.log.MyLogger;

import com.thoughtworks.selenium.Selenium;
/**元素的基础类，对应页面的一个确定元素，具有很多的基于元素的方法和元素具有的属性
 * @author 吴银龙(15857164387)
 * */
public class Element implements LocatorValue{
	private WebElementExist exist;
	/**指示这个元素是否是Page里面的直接元素，<br>
	 * 如果是的话，那么可以用id和name直接定位<br>
	 * 如果不是的话，那么就必须得用变换的到得xpath<br>
	 * 定位，这当然是针对selenium底层的情况*/
	/**SubPage里面所包含的链信息<br>
	 * 用来保存这个节点所用到的comment和locator信息<br>*/
	private LocatorChain infoChain=new LocatorChain();
	public LocatorChain getInfoChain() {
		return infoChain;
	}
	/**此元素的全名，在css校验的时候，为了给css本地文件取名设置的变量信息*/
	protected String fullName="";
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getFullName() {
		return fullName;
	}
	/**元素的注释说明，交代了这个元素是干什么用的*/
	protected String comment;
	/**元素的定位器*/
	protected Locator locator;
	/**当前所用到的定位器*/
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Locator getLocator() {
		return locator;
	}
	public void setLocator(Locator locator) {
		this.locator = locator;
	}
	/**这个元素当前用到的locator*/
	protected String locatorCurrent="";
	/**在webdriver的时候的封装元素*/
	WebElement element=null;
	public void setElement(WebElement element) {
		this.element = element;
	}
	public WebElement getElement() {
		return element;
	}
	/**元素操作的日志记录器*/
	protected MyLogger logger=MyLogger.getLogger(this.getClass());
	{
		HolmosBaseTools.configLogProperties();
	}
	public Element(String comment){
		this.comment=comment;
		this.locator=new Locator();
	}
	public void addIDLocator(String id){
		this.locator.addIdLocator(id);
	}
	public void addNameLocator(String name){
		this.locator.addNameLocator(name);
	}
	public void addXpathLocator(String xpath){
		this.locator.addXpathLocator(xpath);
	}
	public void addCSSLocator(String css){
		this.locator.addCSSLocator(css);
	}
	public void addLinkTextLocator(String linkText){
		this.locator.addLinkTextLocator(linkText);
	}
	public void addPartialLinkTextLocator(String partialLinkText){
		this.locator.addPartialLinkTextLocator(partialLinkText);
	}
	public void addAttributeLocator(String attributeName,String attributeValue){
		this.locator.addAttributeLocator(attributeName, attributeValue);
	}
	public void addTagNameLocator(String tagName){
		this.locator.addTagNameLocator(tagName);
	}
	public void addClassLocator(String className){
		this.locator.addClassLocator(className);
	}
	/**
	 * 查看元素是否存在，等待加载waitCount次
	 * */
	private boolean isElementExist(int waitCount){
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(currentWindow instanceof WebDriverBrowserWindow)
			exist=new WebDriverElementExist(this);
		else if(currentWindow instanceof SeleniumBrowserWindow)
			exist=new SeleniumElementExist(this);
		return exist.isElementExist(waitCount);
	}
	
	/**判断此元素在其所在的页面上是否存在<br>
	 * 进行多方定位，直到找到或者所有的定位方法失败为止<br>
	 * 定位方法顺序id-name-xpath-css-link text-partial link text-tag name
	 * 默认等待加载的时间是5s，折5s不算框架本身所耗费的时间*/
	public boolean isExist(){
		return isElementExist(ConfigConstValue.defaultWaitCount);
	}
	/**校验元素是否存在，则失败返回*/
	public void assertExist(){
		StringBuilder message=new StringBuilder();
		if(isExist()){
			message.append(this.comment+":");
			message.append(":元素存在性校验成功！元素存在！");
			logger.info(message);
		}else{
			message.append(this.comment+":");
			message.append(":元素存在校验失败！元素不存在!");
			logger.error(message);
			HolmosWindow.closeAllWindows();
			fail(message.toString());
		}
	}
	/**校验元素是否不存在，则失败返回*/
	public void assertNotExist(){
		StringBuilder message=new StringBuilder();
		if(!isExist()){
			message.append(this.comment+":");
			message.append("元素不存在性校验成功！元素不存在！");
			logger.info(message);
		}else{
			message.append(this.comment+":");
			message.append("元素存在校验失败！元素存在!");
			logger.error(message);
			HolmosWindow.closeAllWindows();
			fail(message.toString());
		}
	}
	/**校验元素是否存在，失败继续运行*/
	public void verifyExist(){
		StringBuilder message=new StringBuilder();
		if(isExist()){
			message.append(this.comment+":");
			message.append("元素存在性校验成功！元素存在！");
			logger.info(message);
		}else{
			message.append(this.comment+":");
			message.append("元素存在校验失败！元素不存在!");
			logger.error(message);
		}
	}
	/**校验元素是否不存在，失败继续运行*/
	public void verifyNotExist(){
		StringBuilder message=new StringBuilder();
		if(!isExist()){
			message.append(this.comment+":");
			message.append("元素不存在性校验成功！元素不存在！");
			logger.info(message);
		}else{
			message.append(this.comment+":");
			message.append("元素存在校验失败！元素存在!");
			logger.error(message);
		}
	}
	/**等待元素出现，失败打错误日志，程序继续运行<br>
	 * 默认等待时间是30s
	 * */
	public boolean waitForExist() {
	    if(!isElementExist(ConfigConstValue.waitCount)){
            logger.error("元素"+comment+"一直没有出现");
            return false;
        }return true;
	}
	/**等待元素消失,失败打错误日志，程序一直运行*/
	public void waitForDisppear() {
		int waitCount=0;
		while(waitCount++<ConfigConstValue.waitCount){
			if(!exist.isElementExistForCheckOnce())
				return;
		}
	}
	/**点击页面元素，如果当前页面的引擎为selenium的时候，如果此点击打开了新的页面<br>
	 * 不做记录，也就是说新的页面无法操控,所以对于点击新开页面的操作，对于selenium<br>
	 * selenium引擎用clickAndWaitForPageLoad()方法<br>*/
	public void click(){
		StringBuilder message=new StringBuilder();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(isExist()){
			message.append(this.comment+":");
			if(currentWindow instanceof SeleniumBrowserWindow){
				((Selenium)currentWindow.getDriver().getEngine()).click(locator.getSeleniumCurrentLocator());
			}else if(currentWindow instanceof WebDriverBrowserWindow){
				element.click();
				try{
					Allocator.getInstance().updateWindows();
				}catch (Exception e) {
					//do nothing
					/*当点击按钮弹出的是alert等消息框的时候,js会被挂起，这个时候getUrl会失败*/
				}
			}
			message.append("click鼠标左键单击成功!");
			logger.info(message);
		}else{
			message.append(this.comment+":");
			message.append("click鼠标左键单击失败!找不到元素！");
			logger.error(message);
		}
	}
	/**click元素操作，这个用于下面这种比较特殊的情况：<br>
	 * 页面发生了跳转，但是跳转后的页面不是我们的目标页面，我们会里面进入下一个页面<br>
	 * 而这个条赚钱的页面的一些行为会影响到我们的目标页面的执行，这样就会显式的调用<br>
	 * waitForPageLoad()，等待跳转后的页面加载完成，也就是保证跳转前的页面的操作做完<br>
	 * 比如说登陆页面登陆的时候会跳转到我的淘宝，但是这个时候如果你需要进入掌柜说页面<br>
	 * 那么你就会调用open方法打开掌柜说页面，那么这个时候就有可能造成session没有<br>
	 * 存入的情况，造成和登录是一样的结果<br>
	 * 当引擎为webdriver的时候，和click()的处理方法一致
	 * */
	public void clickAndWaitForLoad(){
		StringBuilder message=new StringBuilder();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(isExist()){
			message.append(this.comment+":");
			if(currentWindow instanceof SeleniumBrowserWindow){
				Selenium selenium = (Selenium) currentWindow.getDriver().getEngine();
				try{
					/*检查是否发有a标签，和href属性，再检查target属性*/
					/*url为a标签href属性的值，target为target的值，如果没有则为null，
					 * 先检查元素本身的，如果没有再检查base的*/
					String target,url;
					url=selenium.getAttribute(locator.getSeleniumCurrentLocator()+"@href");
					if(url.endsWith("#")){
						selenium.click(locator.getSeleniumCurrentLocator());
						selenium.waitForPageToLoad(Integer.toString(ConfigConstValue.waitPageLoadTime));
					}else{
						try{
							target=selenium.getAttribute(locator.getSeleniumCurrentLocator()+"@target");
							HolmosBaseTools.openByTarget(url, target);
						}catch(Exception e){
							try{
								target=selenium.getAttribute("xpath=/html/head/base@target");
								HolmosBaseTools.openByTarget(url, target);
							}catch (Exception e1) {
								try{
									target=selenium.getAttribute("xpath=/htm/head/base@target");
									HolmosBaseTools.openByTarget(url, target);
								}catch (Exception e11) {
									HolmosBaseTools.openByTarget(url, null);
								}
							}
						}
					}
				}catch(Exception e){
					selenium.click(locator.getSeleniumCurrentLocator());
					selenium.waitForPageToLoad(Integer.toString(ConfigConstValue.waitPageLoadTime));
				}
			}else if(currentWindow instanceof WebDriverBrowserWindow){
				element.click();
				if(Allocator.getInstance().currentWindow.getEngineType().equals(EngineType.WebDriverFirefox))
					((WebDriver)(Allocator.getInstance().currentWindow.getDriver().getEngine())).manage().timeouts()
						.pageLoadTimeout(10, TimeUnit.SECONDS);
				else{
					HolmosBaseTools.sleep(1000);
				}
				try{
					Allocator.getInstance().updateWindows();
				}catch (Exception e) {
					
				}
			}
			message.append("click()操作成功！");
			logger.info(message.toString());
		}else{
			message.append(this.comment+":");
			message.append("click()鼠标左键单击操作失败！找不到元素！");
			logger.error(message);
		}
	}
	/**
	 * 为了实现页面的成功跳转，那么僵跳转到得url给出，如果当前页面的url不是给出的url那么跳转就没有成功，默认等待加载时间3s左右
	 * */
	public void clickAndWaitForUrl(String url){
		click();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		int count=0;
		while(count++<ConfigConstValue.defaultWaitCount*10){
			if(currentWindow.getUrl().equalsIgnoreCase(url))
				break;
			HolmosBaseTools.sleep(50);
		}
	}
	public void clickAndWaitForIncludeUrl(String partUrl){
		click();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		int count=0;
		while(count++<ConfigConstValue.defaultWaitCount*10){
			if(currentWindow.getUrl().contains(partUrl))
				break;
			HolmosBaseTools.sleep(50);
		}
	}
	/**鼠标悬浮在元素上*/
	public void mouseOver(){
		StringBuilder message=new StringBuilder();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(isExist()){
			message.append(this.comment+":");
			if(currentWindow instanceof SeleniumBrowserWindow){
				((Selenium)currentWindow.getDriver().getEngine()).mouseOver(locator.getSeleniumCurrentLocator());
			}else if(currentWindow instanceof WebDriverBrowserWindow){
				Actions action=new Actions((WebDriver) currentWindow.getDriver().getEngine());
				action.moveToElement(element);
			}
			message.append("悬停成功!");
			logger.info(message);
		}else{
			message.append(this.comment+":");
			message.append("悬停失败！找不到元素！");
			logger.error(message);
		}
	}
	/**获得这个元素的文本，在textField和richTextField里面对这个方法进行了重写<br>
	 * 这个获得了标签<biaoqian>text</biaoqian>之间的text
	 * @return 两标签之间的文本值*/
	public String getText(){
		StringBuilder message=new StringBuilder();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		String text=null;
		if(isExist()){
			message.append(this.comment+":");
			if(currentWindow instanceof SeleniumBrowserWindow){
				text=((Selenium)currentWindow.getDriver().getEngine()).getText(locator.getSeleniumCurrentLocator());
			}else if(currentWindow instanceof WebDriverBrowserWindow){
				text=element.getText();
			}
			message.append("文本活的成功，为:"+text);
			logger.info(message);
		}else{
			message.append(this.comment+":");
			message.append("文本活的失败，元素找不到!");
			logger.error(message);
		}
		return text;
	}
	/**打印出两标签之间的文本值，这个只是为了方便封装了一下*/
	public void outputText(){
		System.out.println(getText());
	}
	/**让当前组件活的焦点*/
	public void focus(){
		StringBuilder message=new StringBuilder();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(isExist()){
			message.append(this.comment+":");
			if(currentWindow instanceof SeleniumBrowserWindow){
				((Selenium)currentWindow.getDriver().getEngine()).focus(locator.getSeleniumCurrentLocator());
			}else if(currentWindow instanceof WebDriverBrowserWindow){
				element.sendKeys("");
			}
			message.append("焦点获得成功!");
			logger.info(message);
		}else{
			message.append(this.comment+":");
			message.append("焦点获得失败，找不到元素!");
			logger.error(message);
		}
	}
	/**模拟元素鼠标左键双击操作*/
	public void doubleClick(){
		StringBuilder message=new StringBuilder();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(isExist()){
			message.append(this.comment+":");
			if(currentWindow instanceof SeleniumBrowserWindow){
				((Selenium)currentWindow.getDriver().getEngine()).doubleClick(locator.getSeleniumCurrentLocator());
			}else if(currentWindow instanceof WebDriverBrowserWindow){
				Actions action=new Actions((WebDriver) currentWindow.getDriver().getEngine());
				action.doubleClick(element);
			}
			message.append("双击操作成功!");
			logger.info(message);
		}else {
			message.append(this.comment+":");
			message.append("双击操作失败！找不到元素!");
			logger.error(message);
		}
	}
	/**模拟在这个元素上按下一个键盘按钮，即以此元素为焦点*/
	public void keyPress(Keys keyValue){
		StringBuilder message=new StringBuilder();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(isExist()){
			message.append(this.comment+":");
			if(currentWindow instanceof SeleniumBrowserWindow){
				((Selenium)currentWindow.getDriver().getEngine()).keyPress(locator.getSeleniumCurrentLocator(), keyValue.toString());
			}else{
				Actions action=new Actions((WebDriver) currentWindow.getDriver().getEngine());
				action.keyDown(element, keyValue);
			}
			message.append("按钮"+keyValue+"已按下!");
			logger.info(message);
		}else{
			message.append(this.comment+":");
			message.append("按钮按下失败，找不到元素！");
			logger.error(message);
		}
	}
	/**模拟松开键盘的操作，以此元素为焦点*/
	public void keyUp(Keys keyValue){
		StringBuilder message=new StringBuilder();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(isExist()){
			message.append(this.comment+":");
			if(currentWindow instanceof SeleniumBrowserWindow){
				((Selenium)currentWindow.getDriver().getEngine()).keyUp(locator.getSeleniumCurrentLocator(), keyValue.toString());
			}else{
				Actions action=new Actions((WebDriver) currentWindow.getDriver().getEngine());
				action.keyUp(element, keyValue);
			}
			message.append("按钮"+keyValue+"已松开!");
			logger.info(message);
		}else{
			message.append(this.comment+":");
			message.append("按钮松开失败，找不到元素！");
			logger.error(message);
		}
	}
	/**模拟在此元素上面按下鼠标左键操作*/
	public void leftMouseDown(){
		StringBuilder message=new StringBuilder();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(isExist()){
			message.append(this.comment+":");
			if(currentWindow instanceof SeleniumBrowserWindow){
				((Selenium)currentWindow.getDriver().getEngine()).mouseDown(locator.getSeleniumCurrentLocator());
			}else if(currentWindow instanceof WebDriverBrowserWindow){
				Actions action=new Actions((WebDriver) currentWindow.getDriver().getEngine());
				action.clickAndHold(element);
			}
			message.append("鼠标左键在"+this.comment+"已按下!");
			logger.info(message);
		}else{
			message.append(this.comment+":");
			message.append("鼠标左键按下失败!找不到元素!");
			logger.error(message);
		}
	}
	/**模拟在这个元素上面松开鼠标左键*/
	public void leftMouseUp(){
		StringBuilder message=new StringBuilder();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(isExist()){
			message.append(this.comment+":");
			if(currentWindow instanceof SeleniumBrowserWindow){
				((Selenium)currentWindow.getDriver().getEngine()).mouseUp(locator.getSeleniumCurrentLocator());
			}else if(currentWindow instanceof WebDriverBrowserWindow){
				Actions action=new Actions((WebDriver) currentWindow.getDriver().getEngine());
				action.release(element);
			}
			message.append("鼠标左键在"+this.comment+"已松开!");
			logger.info(message);
		}else{
			message.append(this.comment+":");
			message.append("鼠标左键松开失败!找不到元素!");
			logger.error(message);
		}
	}
	/**模拟在这个元素上面按下鼠标右键*/
	public void rightMouseDown(){
		StringBuilder message=new StringBuilder();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(isExist()){
			message.append(this.comment+":");
			if(currentWindow instanceof SeleniumBrowserWindow){
				((Selenium)currentWindow.getDriver().getEngine()).mouseDownRight(locator.getSeleniumCurrentLocator());
			}else{
				/*右键如何按下*/
			}
			message.append("右键按下成功!");
			logger.info(message);
		}else{
			message.append(this.comment+":");
			message.append("右键按下失败!找不到元素！");
			logger.error(message);
		}
	}
	/**模拟在这个元素上面松开鼠标右键*/
	public void rightMouseUp(){
		StringBuilder message=new StringBuilder();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(isExist()){
			message.append(this.comment+":");
			if(currentWindow instanceof SeleniumBrowserWindow){
				((Selenium)currentWindow.getDriver().getEngine()).mouseUpRight(locator.getSeleniumCurrentLocator());
			}else{
				/*右键如何松开*/
			}
			message.append("右键松开成功!");
			logger.info(message);
		}else{
			message.append(this.comment+":");
			message.append("右键松开失败!找不到元素！");
			logger.error(message);
		}
	}
	/**模拟在该元素上单击鼠标右键*/
	public void rightMouseClick(){
		StringBuilder message=new StringBuilder();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(isExist()){
			message.append(this.comment+":");
			if(currentWindow instanceof SeleniumBrowserWindow){
				((Selenium)currentWindow.getDriver().getEngine()).mouseDownRight(locator.getSeleniumCurrentLocator());
				((Selenium)currentWindow.getDriver().getEngine()).mouseUpRight(locator.getSeleniumCurrentLocator());
			}else{
				Actions action = new Actions((WebDriver) currentWindow.getDriver().getEngine());
				action.contextClick(element);
			}
			message.append("右键按下成功!");
			logger.info(message);
		}else{
			message.append(this.comment+":");
			message.append("右键按下失败!找不到元素！");
			logger.error(message);
		}
	}
	/**拖拽元素到targetLocation<br>
	 * @param targetLocation 目的地坐标，是这个元素的中间坐标*/
	public void mouseDragAndDrop(Location targetLocation){
		StringBuilder message=new StringBuilder();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(isExist()){
			message.append(this.comment+":");
			if(currentWindow instanceof SeleniumBrowserWindow){
//				((Selenium)currentWindow.getDriver().getEngine()).dr
			}else if(currentWindow instanceof WebDriverBrowserWindow){
				Actions action=new Actions((WebDriver) currentWindow.getDriver().getEngine());
				action.dragAndDropBy(element, targetLocation.getxLocation(), targetLocation.getyLocation());
			}
			message.append("拖拽至("+targetLocation.getxLocation()+","+targetLocation.getyLocation()+")成功!");
			logger.info(message);
		}else{
			message.append(this.comment+":");
			message.append("拖拽失败，找不到元素！");
		}
	}
	/**判断这个元素是否可编辑<br>
	 * @return 可编辑 ：true ；不可编辑  false */
	public boolean isEditable(){
		StringBuilder message=new StringBuilder();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		boolean editable=false;
		if(isExist()){
			message.append(this.comment+":");
			if(currentWindow instanceof SeleniumBrowserWindow){
				editable=((Selenium)currentWindow.getDriver().getEngine()).isEditable(locator.getSeleniumCurrentLocator());
			}else if(currentWindow instanceof WebDriverBrowserWindow){
				editable=element.isEnabled();
			}
			message.append("可编辑属性获取成功!");
			logger.info(message);
		}else{
			message.append(this.comment+":");
			message.append("可编辑属性获取失败!找不到元素!");
			logger.error(message);
		}
		return editable;
	}
	/**判断这个元素是否可见<br>
	 * @return 可见 ：true ；不可见  false */
	public boolean isVisable(){
		StringBuilder message=new StringBuilder();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		boolean visible=false;
		message.append(this.comment+":");
		if(isExist()){
			if(currentWindow instanceof SeleniumBrowserWindow){
				visible=((Selenium)currentWindow.getDriver().getEngine()).isVisible(locator.getSeleniumCurrentLocator());
			}else if(currentWindow instanceof WebDriverBrowserWindow){
				visible=element.isDisplayed();
			}
			message.append("可见属性获取成功!");
			logger.info(message);
		}else{
			message.append(this.comment+":");
			message.append("可见属性获取失败!找不到元素!");
			logger.error(message);
		}
		return visible;
	}
	/**校验text是否和value相同，如果不同，则退出运行<br>
	 * @parameter value 期望的字符窜，与这个元素的文本进行比较<br>*/
	public void assertEqualText(String expected){
		StringBuilder message=new StringBuilder();
		String actual=getText();
		message.append(this.comment+":");
		if(actual==null){
			if(expected==null){
				message.append("文本相同校验成功,预期和实际都是null");
				logger.info(message);
			}else{
				message.append("文本相同校验失败,预期值为"+expected+",实际值为null!");
				logger.error(message);
				HolmosWindow.closeAllWindows();
				fail(message.toString());
			}
		}
		else if(actual.equalsIgnoreCase(expected)){
			message.append(this.comment+":");
			message.append("文本相同校验成功,预期和实际都是null");
			logger.info(message);
		}else{
			message.append("文本相同校验失败,预期值为"+expected+",实际值为"+actual+"!");
			logger.error(message);
			HolmosWindow.closeAllWindows();
			fail(message.toString());
		}
	}
	/**校验text是否和value相同，如果相同，则退出运行<br>
	 * @parameter value 期望的字符窜，与这个元素的文本进行比较<br>*/
	public void assertNotEqualText(String expected){
		StringBuilder message=new StringBuilder();
		String actual=getText();
		message.append(this.comment+":");
		if(actual==null){
			if(expected==null){
				message.append("文本不相同校验失败,预期和实际都是null");
				logger.error(message);
				HolmosWindow.closeAllWindows();
				fail(message.toString());
			}else{
				message.append("文本不相同校验成功,预期值为"+expected+",实际值为null!");
				logger.info(message);
			}
		}
		else if(actual.equalsIgnoreCase(expected)){
			message.append("文本不相同校验失败,预期和实际都是"+expected);
			logger.error(message);
			HolmosWindow.closeAllWindows();
			fail(message.toString());
		}else{
			message.append("文本不相同校验成功,预期值为"+expected+",实际值为"+actual+"!");
			logger.info(message);
		}
	}
	/**校验text是否和value相同，如果不同，会继续执行，但是给出日志信息，校验错误<br>
	 * @parameter value 期望的字符窜，与这个元素的文本进行比较<br>*/
	public void verifyEqualText(String expected){
		StringBuilder message=new StringBuilder();
		String actual=getText();
		message.append(this.comment+":");
		if(actual==null){
			if(expected==null){
				message.append("文本相同校验成功,预期和实际都是null");
				logger.info(message);
			}else{
				message.append("文本相同校验失败,预期值为"+expected+",实际值为null!");
				logger.error(message);
			}
		}
		else if(actual.equalsIgnoreCase(expected)){
			message.append("文本相同校验成功,预期和实际都是null");
			logger.info(message);
		}else{
			message.append("文本相同校验失败,预期值为"+expected+",实际值为"+actual+"!");
			logger.error(message);
		}
	}
	/**校验text是否和value相同，如果相同，会继续运行，但是日志会给出校验错误信息<br>
	 * @parameter value 期望的字符窜，与这个元素的文本进行比较<br>*/
	public void verifyNotEqualText(String expected){
		StringBuilder message=new StringBuilder();
		String actual=getText();
		message.append(this.comment+":");
		if(actual==null){
			if(expected==null){
				message.append("文本不相同校验失败,预期和实际都是null");
				logger.error(message);
			}else{
				message.append("文本不相同校验成功,预期值为"+expected+",实际值为null!");
				logger.info(message);
			}
		}
		else if(actual.equalsIgnoreCase(expected)){
			message.append("文本不相同校验失败,预期和实际都是"+expected);
			logger.error(message);
		}else{
			message.append("文本不相同校验成功,预期值为"+expected+",实际值为"+actual+"!");
			logger.info(message);
		}
	}
	/**校验text是否和regExpected匹配，regExpected为正则表达式，<br>
	 * 如果匹配则校验成功,程序继续执行，否则校验失败，程序退出<br>
	 * @param regExpected 期望的字符窜，与这个元素的文本进行匹配<br>*/
	public void assertRegMatchText(String regExpected){
		StringBuilder message=new StringBuilder();
		Pattern pattern=Pattern.compile(regExpected);
		String actual=getText();
		Matcher matcher=pattern.matcher(actual);
		message.append(this.comment+":");
		if(matcher.matches()){
			message.append("正则匹配校验成功,预期的正则表达式为:"+regExpected+"|实际的文本值为:"+actual);
			logger.info(message);
		}else{
			message.append("正则匹配校验失败,预期的正则表达式为:"+regExpected+"|实际的文本值为:"+actual);
			logger.error(message);
			HolmosWindow.closeAllWindows();
			fail(message.toString());
		}
	}
	/**校验text是否和regExpected匹配，regExpected为正则表达式，如果不匹配则校验成功<br>
	 * 否则校验失败，程序退出<br>
	 * @parameter regExpected 期望的字符窜，与这个元素的文本进行匹配<br>*/
	public void assertNotRegMatchText(String regExpected){
		StringBuilder message=new StringBuilder();
		Pattern pattern=Pattern.compile(regExpected);
		String actual=getText();
		Matcher matcher=pattern.matcher(actual);
		message.append(this.comment+":");
		if(matcher.matches()){
			message.append("正则不匹配校验失败,预期的正则表达式为:"+regExpected+"|实际的文本值为:"+actual);
			logger.info(message);
			HolmosWindow.closeAllWindows();
			fail(message.toString());
		}else{
			message.append("正则不匹配校验成功,预期的正则表达式为:"+regExpected+"|实际的文本值为:"+actual);
			logger.error(message);
		}
	}
	/**校验text是否和regExpected匹配，regExpected为正则表达式，如果匹配则校验成功<br>
	 * 否则校验失败，程序会继续运行，但是日志会给出错误信息<br>
	 * @parameter regExpected 期望的字符窜，与这个元素的文本进行匹配<br>*/
	public void verifyRegMatchText(String regExpected){
		StringBuilder message=new StringBuilder();
		Pattern pattern=Pattern.compile(regExpected);
		String actual=getText();
		Matcher matcher=pattern.matcher(actual);
		message.append(this.comment+":");
		if(matcher.matches()){
			message.append("正则匹配校验成功,预期的正则表达式为:"+regExpected+"|实际的文本值为:"+actual);
			logger.info(message);
		}else{
			message.append("正则匹配校验失败,预期的正则表达式为:"+regExpected+"|实际的文本值为:"+actual);
			logger.error(message);
		}
	}
	/**校验text是否和regExpected匹配，regExpected为正则表达式，如果不匹配则校验成功<br>
	 * 否则校验失败，但程序继续执行<br>
	 * @parameter regExpected 期望的字符窜，与这个元素的文本进行匹配<br>*/
	public void verifyNotRegMatchText(String regExpected){
		StringBuilder message=new StringBuilder();
		Pattern pattern=Pattern.compile(regExpected);
		String actual=getText();
		Matcher matcher=pattern.matcher(actual);
		message.append(this.comment+":");
		if(matcher.matches()){
			message.append("正则不匹配校验失败,预期的正则表达式为:"+regExpected+"|实际的文本值为:"+actual);
			logger.info(message);
		}else{
			message.append("正则不匹配校验成功,预期的正则表达式为:"+regExpected+"|实际的文本值为:"+actual);
			logger.error(message);
		}
	}
	/**获取一个元素的一个属性<br>
	 * @param attributeName 属性的名字，例如href
	 * @return 获取的属性信息，字符窜
	 * */
	public String getAttribute(String attributeName){
		StringBuilder message=new StringBuilder();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		String attribute=null;
		if(isExist()){
			message.append(this.comment+":");
			if(currentWindow instanceof SeleniumBrowserWindow){
				try{
					attribute=((Selenium)currentWindow.getDriver().getEngine()).getAttribute(locator.getSeleniumCurrentLocator()+"@"+attributeName);
					message.append("属性获取成功!");
					logger.info(message);
				}catch (Exception e) {
					message.append("属性获取失败，此元素没有"+attributeName+"属性!");
					logger.error(message);
				}
			}else if(currentWindow instanceof WebDriverBrowserWindow){
				try{
					attribute=element.getAttribute(attributeName);
					message.append("属性获取成功!");
					logger.info(message);
				}catch (Exception e) {
					message.append("属性获取失败，此元素没有"+attributeName+"属性!");
					logger.error(message);
				}
			}
		}else{
			message.append(this.comment+":");
			message.append("属性获取失败,找不到元素!");
			logger.error(message);
		}
		return attribute;
	}
	/**校验text是否包含value，value为一字符窜，如果包含则校验成功<br>
	 * 程序继续运行，否则校验失败，程序退出<br>
	 * @parameter value 期望的字符窜，与这个元素的文本进行包含校验<br>*/
	public void assertInclude(String value){
		StringBuilder message=new StringBuilder();
		String actual=getText();
		message.append(this.comment+":");
		if(actual==null){
			message.append("包含校验失败!获取元素的text为null!");
			logger.error(message);
			HolmosWindow.closeAllWindows();
			fail(message.toString());
		}else if(actual.contains(value)){
			message.append("包含校验成功!实际获得的文本为:"+actual+"包含字符窜为:"+value);
			logger.info(message);
		}else{
			message.append("包含校验失败!实际获得的文本为:"+actual+"包含字符窜为:"+value);
			logger.error(message);
			HolmosWindow.closeAllWindows();
			fail(message.toString());
		}
	}
	/**校验text是否包含value，value为一字符窜，如果包含则校验成功<br>
	 * 程序继续运行，否则校验失败，程序继续执行<br>
	 * @parameter value 期望的字符窜，与这个元素的文本进行包含校验<br>*/
	public void verifyInclude(String value){
		StringBuilder message=new StringBuilder();
		String actual=getText();
		message.append(this.comment+":");
		if(actual==null){
			message.append("包含校验失败!获取元素的text为null!");
			logger.error(message);
		}else if(actual.contains(value)){
			message.append("包含校验成功!实际获得的文本为:"+actual+"包含字符窜为:"+value);
			logger.info(message);
		}else{
			message.append("包含校验失败!实际获得的文本为:"+actual+"包含字符窜为:"+value);
			logger.error(message);
		}
	}
	/**校验text是否包含value，value为一字符窜，如果包含则校验失败<br>
	 * 程序退出，否则校验失败，程序继续运行<br>
	 * @parameter value 期望的字符窜，与这个元素的文本进行不包含校验<br>*/
	public void assertNotInclude(String value){
		StringBuilder message=new StringBuilder();
		String actual=getText();
		message.append(this.comment+":");
		if(actual==null){
			message.append("不包含校验失败!获取元素的text为null!");
			logger.error(message);
			HolmosWindow.closeAllWindows();
			fail(message.toString());
		}else if(actual.contains(value)){
			message.append("不包含校验失败!实际获得的文本为:"+actual+"包含字符窜为:"+value);
			logger.error(message);
			HolmosWindow.closeAllWindows();
			fail(message.toString());
		}else{
			message.append("不包含校验成功!实际获得的文本为:"+actual+"包含字符窜为:"+value);
			logger.info(message);
		}
	}
	/**校验text是否包含value，value为一字符窜，如果包含则校验失败<br>
	 * 程序继续执行，否则校验失败，程序继续运行<br>
	 * @parameter value 期望的字符窜，与这个元素的文本进行不包含校验<br>*/
	public void verifyNotInclude(String value){
		StringBuilder message=new StringBuilder();
		String actual=getText();
		message.append(this.comment+":");
		if(actual==null){
			message.append("不包含校验失败!获取元素的text为null!");
			logger.error(message);
		}else if(actual.contains(value)){
			message.append("不包含校验失败!实际获得的文本为:"+actual+"包含字符窜为:"+value);
			logger.error(message);
		}else{
			message.append("不包含校验成功!实际获得的文本为:"+actual+"包含字符窜为:"+value);
			logger.info(message);
		}
	}
	/**等待元素的文本变为value,默认的等待时间是30s<br>
	 * 如果等待30s，仍然没有出现，则等待失败，但程序继续执行<br>
	 * @param value 期待的文本*/
	public void waitForText(String value){
		StringBuilder message=new StringBuilder();
		int waitCount=0;
		String actual=null;
		if(waitForExist()){
			message.append(this.comment+":");
			while(waitCount++<ConfigConstValue.waitCount){
				actual=getText();
				if(actual==null)continue;
				else if(actual.equalsIgnoreCase(value)){
					message.append("文本值:"+value+"已经出现!等待成功!");
					logger.info(message);
					break;
				}
			}
			if(waitCount>ConfigConstValue.waitCount){
				message.append("一直等到文本"+value+"出现，最后一次等待出现的文本是:"+actual);
				logger.error(message);
			}
		}
	}
	/**
	 * 等待元素的文本包含有value，默认等待时间为30s<br>
	 * 如果等待30s，仍然没有出现，则等待失败，但程序继续执行<br>
	 * @param value 等待被包含的文本
	 * */
	public void waitForIncludeText(String value){
		StringBuilder message=new StringBuilder();
		int waitCount=0;
		String actual=null;
		if(waitForExist()){
			message.append(this.comment+":");
			while(waitCount++<ConfigConstValue.waitCount){
				actual=getText();
				if(actual==null)continue;
				else if(actual.contains(value)){
					message.append("文本值:"+value+"已经被包含!等待成功!");
					logger.info(message);
					break;
				}
			}
			if(waitCount>ConfigConstValue.waitCount){
				message.append("一直等到文本"+value+"被包含，最后一次等待出现的文本是:"+actual);
				logger.error(message);
			}
		}
	}
	/**android和iphone的单击<br>
	 * 这个单击不能用selenium引擎，只能用webdriver引擎<br>*/
	public void clickPhone(){
		StringBuilder message=new StringBuilder();
		if(isExist()){
			message.append(this.comment+":");
			BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
			if(currentWindow instanceof SeleniumBrowserWindow){
				message.append("您当前用的是selenium引擎，这个引擎不支持");
			}else if(currentWindow instanceof WebDriverBrowserWindow){
				
			}
		}
	}
	public String getLocatorCurrent() {
		// TODO Auto-generated method stub
		return locatorCurrent;
	}
	public void setLocatorCurrent(String locatorCurrent) {
		// TODO Auto-generated method stub
		this.locatorCurrent=locatorCurrent;
	}
	/**获得元素css属性值<br>
	 * @param cssName css属性的名字
	 * @return css属性的值*/
	public String getCSSValue(String cssName){
		StringBuilder message=new StringBuilder();
		if(isExist()){
			message.append(this.comment+":");
			BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
			if(currentWindow instanceof SeleniumBrowserWindow){
				message.append("您当前用的是selenium引擎，这个引擎不支持");
			}else if(currentWindow instanceof WebDriverBrowserWindow){
				return element.getCssValue(cssName);
			}
		}return null;
	}
	/**
	 * 获取此元素的大小
	 * */
	public Dimension getSize(){
		StringBuilder message=new StringBuilder();
		if(isExist()){
			message.append(this.comment+":");
			BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
			if(currentWindow instanceof SeleniumBrowserWindow){
				message.append("您当前用的是selenium引擎，这个引擎不支持");
			}else if(currentWindow instanceof WebDriverBrowserWindow){
				return element.getSize();
			}
		}
		return null;
	}
	/**
	 * 获取此元素的位置,左上角的位置
	 * */
	public Point getLocation(){
		StringBuilder message=new StringBuilder(this.comment+":");
		if(isExist()){
			message.append(this.comment+":");
			BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
			if(currentWindow instanceof SeleniumBrowserWindow){
				message.append("您当前用的是selenium引擎，这个引擎不支持");
			}else if(currentWindow instanceof WebDriverBrowserWindow){
				return element.getLocation();
			}
		}
		return null;
	}
	/**
	 * 高亮显示此Element
	 * */
	public void highLight(){
		if(isExist()){
			BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
			if(currentWindow instanceof SeleniumBrowserWindow){
				Selenium selenium=(Selenium) currentWindow.getDriver().getEngine();
				selenium.highlight(locatorCurrent);
			}else if(currentWindow instanceof WebDriverBrowserWindow){
				HolmosWindow.runJavaScript("arguments[0].style.border = \"5px solid yellow\"",element);
			}
			
		}
	}
}