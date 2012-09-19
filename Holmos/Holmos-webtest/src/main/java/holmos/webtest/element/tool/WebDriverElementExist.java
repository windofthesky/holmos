package holmos.webtest.element.tool;

import holmos.webtest.Allocator;
import holmos.webtest.element.ListElement;
import holmos.webtest.element.locator.Locator;
import holmos.webtest.element.locator.LocatorFinder;
import holmos.webtest.element.locator.LocatorValue;
import holmos.webtest.struct.Collection;
import holmos.webtest.struct.Frame;
import holmos.webtest.struct.Page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
/**
 * 用来判断webdriver web元素是否存在的类，采用的是策略模式
 * @author 吴银龙(15857164387)
 * */
public class WebDriverElementExist extends WebElementExist{
	/**
	 * 元素的判断采用策略模式实现，此为WebDriver类型的数据判断
	 * @param webElement 待判断的元素
	 * */
	public WebDriverElementExist(LocatorValue webElement){
		super(webElement);
	}
	
	@Override
	public boolean isElementExist(int waitCount){
		initComment();
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
	/**判断单层元素是否存在*/
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
		if(webElement instanceof Collection){
			int index=((Collection)webElement).getIndex();
			element=findCollection(finder, webElement, index);
		}else if(webElement instanceof ListElement){
			int index=((ListElement)webElement).getIndex();
			element=findListElement(finder, webElement, index);
		}else{
			element=findElementOnly(finder, webElement);
		}
		return element;
	}
	private WebElement findListElement(LocatorFinder finder,LocatorValue webElement,int index){
		Locator locator=webElement.getLocator();
		WebElement element=findListElementById(finder,locator.getLocatorById(),index);
		if(element==null)
			element=findListElementByName(finder,locator.getLocatorByName(),index);
		if(element==null)
			element=findListElementByClass(finder, locator.getLocatorByClass(), index);
		if(element==null)
			element=findListElementByXpath(finder,locator.getLocatorByXpath(),index);
		if(element==null)
			element=findListElementByCss(finder,locator.getLocatorByCSS(),index);
		if(element==null)
			element=findListElementByLinkText(finder,locator.getLocatorByLinkText(),index);
		if(element==null)
			element=findListElementByPartLinkText(finder,locator.getLocatorByPartialLinkText(),index);
		if(element==null)
			element=findListElementByTagName(finder,locator.getLocatorByTagName(),index);
		return element;
	}
	private WebElement findListElementById(LocatorFinder finder,String id,int index){
		if(null==id||"".equalsIgnoreCase(id))
			return null;
		try{
			return finder.findElements(By.id(id)).get(index);
		}catch (Exception e) {
			return null;
		}
	}
	private WebElement findListElementByName(LocatorFinder finder,String name,int index){
		if(null==name||"".equalsIgnoreCase(name))
			return null;
		try{
			return finder.findElements(By.name(name)).get(index);
		}catch (Exception e) {
			return null;
		}
	}
	private WebElement findListElementByXpath(LocatorFinder finder,String xpath,int index){
		if(null==xpath||"".equalsIgnoreCase(xpath))
			return null;
		try{
			return finder.findElements(By.xpath(xpath)).get(index);
		}catch (Exception e) {
			return null;
		}
	}
	private WebElement findListElementByCss(LocatorFinder finder,String css,int index){
		if(null==css||"".equalsIgnoreCase(css))
			return null;
		try{
			return finder.findElements(By.cssSelector(css)).get(index);
		}catch (Exception e) {
			return null;
		}
	}
	private WebElement findListElementByLinkText(LocatorFinder finder,String linkText,int index){
		if(null==linkText||"".equalsIgnoreCase(linkText))
			return null;
		try{
			return finder.findElements(By.linkText(linkText)).get(index);
		}catch (Exception e) {
			return null;
		}
	}
	private WebElement findListElementByPartLinkText(LocatorFinder finder,String partLinkText,int index){
		if(null==partLinkText||"".equalsIgnoreCase(partLinkText))
			return null;
		try{
			return finder.findElements(By.partialLinkText(partLinkText)).get(index);
		}catch (Exception e) {
			return null;
		}
	}
	private WebElement findListElementByTagName(LocatorFinder finder,String tagName,int index){
		if(null==tagName||"".equalsIgnoreCase(tagName))
			return null;
		try{
			return finder.findElements(By.tagName(tagName)).get(index);
		}catch (Exception e) {
			return null;
		}
	}
	private WebElement findListElementByClass(LocatorFinder finder,String className,int index){
		if(null==className||"".equalsIgnoreCase(className))
			return null;
		try{
			return finder.findElements(By.className(className)).get(index);
		}catch (Exception e) {
			return null;
		}
	}
	/**
	 * 定位普通Element类型
	 * @param finder 定位引擎
	 * @param webElement 待定位的元素
	 * @return 定位到普通Element元素，并返回此元素
	 * */
	private WebElement findElementOnly(LocatorFinder finder,LocatorValue webElement){
		Locator locator=webElement.getLocator();
		WebElement element=findElementById(finder,locator.getLocatorById());
		if(element==null)
			element=findElementByName(finder,locator.getLocatorByName());
		if(element==null)
			element=findElementByClass(finder, locator.getLocatorByClass());
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
		return element;
	}
	/**
	 * 定位Collection类型的元素
	 * @param finder 定位引擎
	 * @param webElement 待定位的元素
	 * @param index Collection类型元素的索引，定位到这个索引上
	 * @return 定位到的指定索引指代的Collection类型元素，并返回此元素
	 * */
	private WebElement findCollection(LocatorFinder finder,LocatorValue webElement,int index){
		Locator locator=webElement.getLocator();
		WebElement element=findCollectionById(finder,locator.getLocatorById(),index);
		if(element==null)
			element=findCollectionByName(finder,locator.getLocatorByName(),index);
		if(element==null)
			element=findCollectionByClass(finder, locator.getLocatorByClass(), index);
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
	private WebElement findElementByClass(LocatorFinder finder, String className) {
		if(className==null||"".equalsIgnoreCase(className))
			return null;
		try{
			return finder.findElement(By.className(className));
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
			return finder.findElements(By.xpath(Xpath)).get(index);
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
	private WebElement findCollectionByClass(LocatorFinder finder, String className,int index){
		if(className==null||"".equalsIgnoreCase(className))
			return null;
		try{
			return finder.findElements(By.className(className)).get(index);
		}catch (Exception e) {
			return null;
		}
	}
}
