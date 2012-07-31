package Holmos.webtest.element.tool;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import Holmos.webtest.Allocator;
import Holmos.webtest.element.locator.Locator;
import Holmos.webtest.element.locator.LocatorFinder;
import Holmos.webtest.element.locator.LocatorValue;
/**
 * 用来判断webdriver web元素是否存在的类，采用的是策略模式
 * @author 吴银龙(15857164387)
 * */
public class WebDriverElementExist extends WebElementExist{
	
	public WebDriverElementExist(LocatorValue webElement){
		super(webElement);
	}
	@Override
	public boolean isElementExist(int waitCount){
		LocatorValue lastNode=infoChain.getInfoNodes().get(infoChain.getInfoNodes().size()-1);
		if(lastNode instanceof Page || lastNode instanceof Frame){
			return findOneLevelElement(waitCount);
		}else{
			return findMuiltiLevelElement(waitCount);
		}
	}
	@Override
	public boolean isElementExistForCheckOnce(){
		LocatorValue lastNode=infoChain.getInfoNodes().get(infoChain.getInfoNodes().size()-1);
		if(lastNode instanceof Page || lastNode instanceof Frame){
			return findOneLevelElement(1);
		}else{
			return findMuiltiLevelElement(1);
		}
	}
	private boolean findMuiltiLevelElement(int waitCount) {
		int startLevel=infoChain.getInfoNodes().size()-1;
		for(;startLevel>1;startLevel--){
			if(infoChain.getInfoNodes().get(startLevel) instanceof Frame){
				//找到Frame层级，跳到下一层
				startLevel++;
				break;
			}
		}
		WebDriver driverTemp=(WebDriver) Allocator.getInstance().currentWindow.getDriver().getEngine();
		WebElement elementTemp=null;
		elementTemp=findElement(new LocatorFinder(driverTemp), infoChain.getInfoNodes().get(startLevel));
		if(elementTemp==null)return false;
		for(int i=startLevel+1;i<infoChain.getInfoNodes().size();i++){
			for(int j=0;j<waitCount;j++){
				elementTemp=findElement(new LocatorFinder(elementTemp), infoChain.getInfoNodes().get(startLevel));//这个地方对于Collection需要修改
				if(elementTemp!=null)break;
			}
			if(elementTemp==null)return false;
		}
		
		elementTemp=findElement(new LocatorFinder(elementTemp),webElement);
		if(elementTemp!=null){
			webElement.setElement(elementTemp);return true;
		}
		return false;
	}
	/**判断多层元素是否存在*/
	private boolean findOneLevelElement(int waitCount) {
		LocatorFinder finder=new LocatorFinder(Allocator.getInstance().currentWindow.getDriver().getEngine());
		WebElement elementTemp;
		for(int i=0;i<waitCount;i++){
			elementTemp=findElement(finder,webElement);
			if(elementTemp!=null){
				webElement.setElement(elementTemp);
				return true;
			}
		}
		return false;
	}
	/**查找多级别web层元素，判断其是否存在*/
	private WebElement findElement(LocatorFinder finder,LocatorValue webElement){
		WebElement element = null;
		Locator locator=webElement.getLocator();
		if(webElement instanceof Collection){
			int index=((Collection)webElement).getIndex();
			element=findCollectionById(finder,locator.getLocatorById(),index);
			if(element==null)
				element=findCollectionByName(finder,locator.getLocatorByName(),index);
			if(element==null)
				element=findCollectionByXpath(finder,locator.getLocatorByXpath(),index);
			if(element==null)
				element=findCollectionByCss(finder,locator.getLocatorByCSS(),index);
			if(element==null)
				element=findCollectionByLinkText(finder,locator.getLocatorByLinkText(),index);
			if(element==null)
				element=findCollectionByPartLinkText(finder,locator.getLocatorByPartialLinkText(),index);
			if(element==null)
				element=findCollectionByTagName(finder,locator.getLocatorByTagName(),index);
		}else{
			element=findElementById(finder,locator.getLocatorById());
			if(element==null)
				element=findElementByName(finder,locator.getLocatorByName());
			if(element==null)
				element=findElementByXpath(finder,locator.getLocatorByXpath());
			if(element==null)
				element=findElementByCss(finder,locator.getLocatorByCSS());
			if(element==null)
				element=findElementByLinkText(finder,locator.getLocatorByLinkText());
			if(element==null)
				element=findElementByPartLinkText(finder,locator.getLocatorByPartialLinkText());
			if(element==null)
				element=findElementByTagName(finder,locator.getLocatorByTagName());
		}
		return element;
	}
	private WebElement findElementByTagName(LocatorFinder finder,
			String TagName) {
		if(TagName==null||"".equalsIgnoreCase(TagName))
			return null;
		try{
			return finder.findElement(By.tagName(TagName));
		}catch (Exception e) {
			return null;
		}
	}
	private WebElement findElementByPartLinkText(LocatorFinder finder,
			String PartialLinkText) {
		if(PartialLinkText==null||"".equalsIgnoreCase(PartialLinkText))
			return null;
		try{
			return finder.findElement(By.partialLinkText(PartialLinkText));
		}catch (Exception e) {
			return null;
		}
	}
	private WebElement findElementByLinkText(LocatorFinder finder,
			String LinkText) {
		if(LinkText==null||"".equalsIgnoreCase(LinkText))
			return null;
		try{
			return finder.findElement(By.linkText(LinkText));
		}catch (Exception e) {
			return null;
		}
	}
	private WebElement findElementByCss(LocatorFinder finder,
			String CSS) {
		if(CSS==null||"".equalsIgnoreCase(CSS))
			return null;
		try{
			return finder.findElement(By.cssSelector(CSS));
		}catch (Exception e) {
			return null;
		}
	}
	private WebElement findElementByXpath(LocatorFinder finder,
			String Xpath) {
		if(Xpath==null||"".equalsIgnoreCase(Xpath))
			return null;
		try{
			return finder.findElement(By.xpath(Xpath));
		}catch (Exception e) {
			return null;
		}
	}
	private WebElement findElementByName(LocatorFinder finder,
			String Name) {
		if(Name==null||"".equalsIgnoreCase(Name))
			return null;
		try{
			return finder.findElement(By.name(Name));
		}catch (Exception e) {
			return null;
		}
	}
	private WebElement findElementById(LocatorFinder finder, String Id) {
		if(Id==null||"".equalsIgnoreCase(Id))
			return null;
		try{
			return finder.findElement(By.id(Id));
		}catch (Exception e) {
			return null;
		}
	}
	
	
	
	private WebElement findCollectionByTagName(LocatorFinder finder,
			String TagName,int index) {
		if(TagName==null||"".equalsIgnoreCase(TagName))
			return null;
		try{
			return finder.findElements(By.tagName(TagName)).get(index);
		}catch (Exception e) {
			return null;
		}
	}
	private WebElement findCollectionByPartLinkText(LocatorFinder finder,
			String PartialLinkText,int index) {
		if(PartialLinkText==null||"".equalsIgnoreCase(PartialLinkText))
			return null;
		try{
			return finder.findElements(By.partialLinkText(PartialLinkText)).get(index);
		}catch (Exception e) {
			return null;
		}
	}
	private WebElement findCollectionByLinkText(LocatorFinder finder,
			String LinkText,int index) {
		if(LinkText==null||"".equalsIgnoreCase(LinkText))
			return null;
		try{
			return finder.findElements(By.linkText(LinkText)).get(index);
		}catch (Exception e) {
			return null;
		}
	}
	private WebElement findCollectionByCss(LocatorFinder finder,
			String CSS,int index) {
		if(CSS==null||"".equalsIgnoreCase(CSS))
			return null;
		try{
			return finder.findElements(By.cssSelector(CSS)).get(index);
		}catch (Exception e) {
			return null;
		}
	}
	private WebElement findCollectionByXpath(LocatorFinder finder,
			String Xpath,int index) {
		if(Xpath==null||"".equalsIgnoreCase(Xpath))
			return null;
		try{
			return finder.findElement(By.xpath(Xpath+"["+index+"]"));
		}catch (Exception e) {
			return null;
		}
	}
	private WebElement findCollectionByName(LocatorFinder finder,
			String Name,int index) {
		if(Name==null||"".equalsIgnoreCase(Name))
			return null;
		try{
			return finder.findElements(By.name(Name)).get(index);
		}catch (Exception e) {
			return null;
		}
	}
	private WebElement findCollectionById(LocatorFinder finder, String Id,int index) {
		if(Id==null||"".equalsIgnoreCase(Id))
			return null;
		try{
			return finder.findElements(By.id(Id)).get(index);
		}catch (Exception e) {
			return null;
		}
	}
}
