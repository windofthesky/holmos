package holmos.webtest.struct;

import holmos.webtest.Allocator;
import holmos.webtest.BrowserWindow;
import holmos.webtest.SeleniumBrowserWindow;
import holmos.webtest.WebDriverBrowserWindow;
import holmos.webtest.constvalue.ConfigConstValue;
import holmos.webtest.element.locator.Locator;
import holmos.webtest.element.locator.LocatorChain;
import holmos.webtest.element.locator.LocatorValue;
import holmos.webtest.element.tool.SeleniumElementExist;
import holmos.webtest.element.tool.WebDriverElementExist;
import holmos.webtest.element.tool.WebElementExist;
import holmos.webtest.log.MyLogger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.thoughtworks.selenium.Selenium;

/**页面的Frame结构，支持两种结构(1)Frame(2)iframe  继承于Page
 * @author 吴银龙(15857164387)
 * */
public class Frame extends Page{
	private WebElementExist exist;
	/**用来指示当前的Frame对象是否被选中*/
	
	/**此元素的全名，在css校验的时候，为了给css本地文件取名设置的变量信息*/
	protected String fullName="";
	/**此Frame的父容器*/
	protected Page parent;
	
	public void setParent(Page parent) {
		this.parent = parent;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	private WebElement element;
	public WebElement getElement() {
		return element;
	}
	public void setElement(WebElement element) {
		this.element = element;
	}
	/**Frame的定位器*/
	private Locator locator=new Locator();
	/**Frame操作的日志记录器*/
	private static MyLogger logger=MyLogger.getLogger(Frame.class);
	public Locator getLocator() {
		return locator;
	}
	public void setLocator(Locator locator) {
		this.locator = locator;
	}
	public Frame(String comment){
		super();
		this.setSelected(false);
		this.comment=comment;
		this.init();
	}
	public void addIDlocator(String id){
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
	public void addAttributeLocator(String attributeName, String attributeValue) {
		this.locator.addAttributeLocator(attributeName, attributeValue);
	}
	public void addTagNameLocator(String tagName){
		this.locator.addTagNameLocator(tagName);
	}
	public void addClassLocator(String className){
		this.locator.addClassLocator(className);
	}
	/**Frame里面所包含的链信息<br>
	 * 用来保存这个节点所用到的comment和locator信息<br>*/
	private LocatorChain infoChain=new LocatorChain();
	/**当前的locatorCurrent*/
	private String locatorCurrent;
	public LocatorChain getInfoChain() {
		return infoChain;
	}
	/**
	 * 查看Frame是否存在，等待加载waitCount次
	 * */
	private boolean isFrameExist(int waitCount){
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(currentWindow instanceof SeleniumBrowserWindow){
			exist=new SeleniumElementExist(this);
		}
		else if(currentWindow instanceof WebDriverBrowserWindow){
			exist=new WebDriverElementExist(this);
		}
		return exist.isElementExist(waitCount);
	}
	/**
	 * 检查此Frame是否存在
	 * @return true 存在 <br> false 不存在 
	 * */
	public boolean isExist(){
		return isFrameExist(ConfigConstValue.defaultWaitCount);
	}
	/**选中当前的Frame，如果有多级Frame，需要按照层级<br>
	 * 级级递进的顺序，步步选择<br>*/
	private void selectFrame(){
		StringBuilder message=new StringBuilder();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(isExist()){
			message.append(this.comment+":");
			if(currentWindow instanceof SeleniumBrowserWindow){
				((Selenium)currentWindow.getDriver().getEngine()).selectFrame(locator.getSeleniumCurrentLocator());
				setSelected(true);parent.setSelected(false);
			}else if(currentWindow instanceof WebDriverBrowserWindow){
				try{
					if(locator.getLocatorById()!=null)
						((WebDriver)currentWindow.getDriver().getEngine()).switchTo().frame(locator.getLocatorById());
					else if(locator.getLocatorByName()!=null){
						((WebDriver)currentWindow.getDriver().getEngine()).switchTo().frame(locator.getLocatorByName());
					}else{
						((WebDriver)currentWindow.getDriver().getEngine()).switchTo().frame(element);
					}
					message.append("定位Frame成功!现在的控制权交予这个Frame!");
					logger.info(message);
					setSelected(true);
					parent.setSelected(false);
				}catch (Exception e) {
					setSelected(false);
					message.append("定位Frame失败，没有找到这个Frame!");
					logger.error(message);
				}
			}
		}else{
			logger.error("该frame不存在");
			setSelected(false);
		}
	}
	public void select(){
		if(!isSelected()){
			selectTopPage();//将控制权交予主页面
			for(int i=1;i<infoChain.getInfoNodes().size();i++){
				((Frame)infoChain.getInfoNodes().get(i)).selectFrame();
			}
			selectFrame();
		}
	}
	/**
	 * 选择此Frame的父容器对象
	 * */
	public void selectParentContainer(){
		StringBuilder message=new StringBuilder(this.comment+":");
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(currentWindow instanceof SeleniumBrowserWindow){
			((Selenium)currentWindow.getDriver().getEngine()).selectFrame("relative=up");
		}else if(currentWindow instanceof WebDriverBrowserWindow){
			
		}
	}
	/**
	 * 选择最上层的页面
	 * */
	public void selectTopPage(){
		StringBuilder message=new StringBuilder(this.comment+":");
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(currentWindow instanceof SeleniumBrowserWindow){
			((Selenium)currentWindow.getDriver().getEngine()).selectFrame("relative=top");
		}else if(currentWindow instanceof WebDriverBrowserWindow){
			((WebDriver)currentWindow.getDriver().getEngine()).switchTo().defaultContent();
		}
	}
	/**等待SubPage出现，失败打错误日志，程序继续运行<br>
	 * 默认等待时间是30s
	 * */
	public boolean waitForExist() {
	    if(!isFrameExist(ConfigConstValue.waitCount)){
            logger.error("元素"+comment+"一直没有出现");
            return false;
        }return true;
	}
	/**等待SubPage消失,失败打错误日志，程序一直运行*/
	public void waitForDisppear() {
		int waitCount=0;
		while(waitCount++<ConfigConstValue.waitCount){
			if(!exist.isElementExistForCheckOnce())
				return;
		}
	}
	
	
}
