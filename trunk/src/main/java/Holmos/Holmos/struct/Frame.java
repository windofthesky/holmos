package Holmos.Holmos.struct;

import java.util.NoSuchElementException;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import Holmos.Holmos.Allocator;
import Holmos.Holmos.BrowserWindow;
import Holmos.Holmos.SeleniumBrowserWindow;
import Holmos.Holmos.WebDriverBrowserWindow;
import Holmos.Holmos.constvalue.ConfigConstValue;
import Holmos.Holmos.constvalue.ConstValue;
import Holmos.Holmos.element.locator.Locator;
import Holmos.Holmos.element.locator.LocatorChain;
import Holmos.Holmos.element.locator.LocatorFinder;

import com.thoughtworks.selenium.Selenium;

/**页面的Frame结构，支持两种结构(1)Frame(2)iframe  继承于Page
 * @author 吴银龙(15857164387)
 * */
public class Frame extends Page{
	/**用来指示当前的Frame对象是否被选中*/
	private boolean selected;//先定在这，接下来再想确切的解决方案
	/**此元素的全名，在css校验的时候，为了给css本地文件取名设置的变量信息*/
	protected String fullName="";
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	private WebElement element;
	/**Frame的定位器*/
	private Locator locator=new Locator();
	/**Frame操作的日志记录器*/
	private static Logger logger=Logger.getLogger(Frame.class.getName());
	public Locator getLocator() {
		return locator;
	}
	public void setLocator(Locator locator) {
		this.locator = locator;
	}
	public Frame(String comment){
		super();
		this.selected=false;
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
		if(currentWindow instanceof SeleniumBrowserWindow)
			return isSeleniumFrameExist(waitCount);
		else if(currentWindow instanceof WebDriverBrowserWindow)
			return isWebDriverFrameExist(waitCount);
		return false;
	}
	private boolean isSeleniumFrameExist(int waitCount){
		int count=0;
		Selenium seleniumEngine=(Selenium) Allocator.getInstance().currentWindow.getDriver().getEngine();
		if(infoChain.getInfoNodes().size()<=1){
			while(count++<waitCount){
				if(findSeleniumFrame(seleniumEngine))
					return true;
			}
		}else{
			while(count++<waitCount){
				if(seleniumEngine.isElementPresent("xpath="+locatorCurrent)){
					return true;
				}
			}
		}
		return false;
	}
	private boolean findSeleniumFrame(Selenium seleniumEngine){
		if(findSeleniumFrameById(seleniumEngine))return true;
		if(findSeleniumFrameByName(seleniumEngine))return true;
		if(findSeleniumFrameByXpath(seleniumEngine))return true;
		if(findSeleniumFrameByCss(seleniumEngine))return true;
		if(findSeleniumFrameByLinkText(seleniumEngine))return true;
		if(findSeleniumFrameByRegularLinkText(seleniumEngine))return true;
		return false;
	}
	private boolean findSeleniumFrameById(Selenium seleniumEngine){
		if(locator.getLocatorById()!=null&&!"".equalsIgnoreCase(locator.getLocatorById())){
			if(seleniumEngine.isElementPresent("id="+locator.getLocatorById())){
				locatorCurrent="id="+locator.getLocatorById();
				return true;
			}
		}return false;
	}
	private boolean findSeleniumFrameByName(Selenium seleniumEngine){
		if(locator.getLocatorByName()!=null&&!"".equalsIgnoreCase(locator.getLocatorByName())){
			if(seleniumEngine.isElementPresent("name="+locator.getLocatorByName())){
				locatorCurrent="name="+locator.getLocatorByName();
				return true;
			}
		}return false;
	}
	private boolean findSeleniumFrameByXpath(Selenium seleniumEngine){
		if(locator.getLocatorByXpath()!=null&&!"".equalsIgnoreCase(locator.getLocatorByXpath())){
			if(seleniumEngine.isElementPresent("xpath="+locator.getLocatorByXpath())){
				locatorCurrent="xpath="+locator.getLocatorByXpath();
				return true;
			}
		}return false;
	}
	private boolean findSeleniumFrameByCss(Selenium seleniumEngine){
		if(locator.getLocatorByCSS()!=null&&!"".equalsIgnoreCase(locator.getLocatorByCSS())){
			if(seleniumEngine.isElementPresent("css="+locator.getLocatorByCSS())){
				locatorCurrent="css="+locator.getLocatorByCSS();
				return true;
			}
		}return false;
	}
	private boolean findSeleniumFrameByLinkText(Selenium seleniumEngine){
		if(locator.getLocatorByLinkText()!=null&&!"".equalsIgnoreCase(locator.getLocatorByLinkText())){
			if(seleniumEngine.isElementPresent("link="+locator.getLocatorByLinkText())){
				locatorCurrent="link="+locator.getLocatorByLinkText();
				return true;
			}
		}return false;
	}
	private boolean findSeleniumFrameByRegularLinkText(Selenium seleniumEngine){
		if(locator.getLocatorByRegular()!=null&&!"".equalsIgnoreCase(locator.getLocatorByRegular())){
			if(seleniumEngine.isElementPresent("link=glob:"+locator.getLocatorByRegular())){
				locatorCurrent="link=glob:"+locator.getLocatorByRegular();
				return true;
			}
		}return false;
	}
	private boolean isWebDriverFrameExist(int waitCount){
		int count=0;
		LocatorFinder finder=new LocatorFinder(Allocator.getInstance().currentWindow.getDriver().getEngine());
		if(infoChain.getInfoNodes().size()<=1){
			while(count++<waitCount){
				if(findWebDriverFrame(finder))
					return true;
			}
		}
//		}else{
//			while(count++<waitCount){
//				try{
//					this.element=finder.findElement(By.xpath(locatorCurrent));
//					return true;
//				}catch (NoSuchElementException e) {
//					continue;
//				}
//			}
//		}
		return false;
	}
	
	private boolean findWebDriverFrame(LocatorFinder finder){
		if(findWebDriverFrameById(finder))return true;
		if(findWebDriverFrameByName(finder))return true;
		if(findWebDriverFrameByXpath(finder))return true;
		if(findWebDriverFrameByCss(finder))return true;
		if(findWebDriverFrameByLinkText(finder))return true;
		if(findWebDriverFrameByPartialLinkText(finder))return true;
		if(findWebDriverFrameByTagName(finder))return true;
		return false;
	}
	private boolean findWebDriverFrameById(LocatorFinder finder){
		if(locator.getLocatorById()==null||"".equalsIgnoreCase(locator.getLocatorById()))
			return false;
		try{
			element=finder.findElement(By.id(locator.getLocatorById()));
			this.setElement(element);
			return true;
		}catch(NoSuchElementException e){
			return false;
		}
	}
	private boolean findWebDriverFrameByName(LocatorFinder finder){
		if(locator.getLocatorByName()==null||"".equalsIgnoreCase(locator.getLocatorByName()))
			return false;
		try{
			element=finder.findElement(By.name(locator.getLocatorByName()));
			this.setElement(element);
			return true;
		}catch(NoSuchElementException e){
			return false;
		}
	}
	private boolean findWebDriverFrameByXpath(LocatorFinder finder){
		if(locator.getLocatorByXpath()==null||"".equalsIgnoreCase(locator.getLocatorByXpath()))
			return false;
		try{
			element=finder.findElement(By.xpath(locator.getLocatorByXpath()));
			this.setElement(element);
			return true;
		}catch(NoSuchElementException e){
			return false;
		}
	}
	private boolean findWebDriverFrameByCss(LocatorFinder finder){
		if(locator.getLocatorByCSS()==null||"".equalsIgnoreCase(locator.getLocatorByCSS()))
			return false;
		try{
			element=finder.findElement(By.cssSelector(locator.getLocatorByCSS()));
			this.setElement(element);
			return true;
		}catch(NoSuchElementException e){
			return false;
		}
	}
	private boolean findWebDriverFrameByLinkText(LocatorFinder finder){
		if(locator.getLocatorByLinkText()==null||"".equalsIgnoreCase(locator.getLocatorByLinkText()))
			return false;
		try{
			element=finder.findElement(By.linkText(locator.getLocatorByLinkText()));
			this.setElement(element);
			return true;
		}catch(NoSuchElementException e){
			return false;
		}
	}
	private boolean findWebDriverFrameByPartialLinkText(LocatorFinder finder){
		if(locator.getLocatorByPartialLinkText()==null||"".equalsIgnoreCase(locator.getLocatorByPartialLinkText()))
			return false;
		try{
			element=finder.findElement(By.partialLinkText(locator.getLocatorByPartialLinkText()));
			this.setElement(element);
			return true;
		}catch(NoSuchElementException e){
			return false;
		}
	}
	private boolean findWebDriverFrameByTagName(LocatorFinder finder){
		if(locator.getLocatorByTagName()==null||"".equalsIgnoreCase(locator.getLocatorByTagName()))
			return false;
		try{
			element=finder.findElement(By.tagName(locator.getLocatorByTagName()));
			this.setElement(element);
			return true;
		}catch(NoSuchElementException e){
			return false;
		}
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
	public void select(){
		StringBuilder message=new StringBuilder(this.comment+":");
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(isExist()){
			if(currentWindow instanceof SeleniumBrowserWindow){
				((Selenium)currentWindow.getDriver().getEngine()).selectFrame(locator.getSeleniumCurrentLocator());
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
				}catch (Exception e) {
					message.append("定位Frame失败，没有找到这个Frame!");
					logger.error(message);
				}
			}
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
}
