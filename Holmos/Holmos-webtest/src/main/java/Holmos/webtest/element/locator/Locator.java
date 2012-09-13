package Holmos.webtest.element.locator;

import java.util.HashMap;
import java.util.Map;

/**元素定位器类，这个定位器很高级，采用了多级定位，即多个方法定位<br>
 * 如果第一级别定位失败或者无定位器，那么采用第二级定位，以此类推<br>
 * 共分为id->name->xpath->css->linkText->partialLinkText->tag name
 * 其中有两个特殊的匹配方式：
 * (1)selenium支持正则匹配，webdriver不支持，selenium不支持纯粹的部分匹配
 * (2)webdriver支持tag name匹配，selenium不支持
 *  @author 吴银龙(15857164387)
 *  */
public class Locator {
	/**由于定位方案多了，所以在Selenium引擎的时候需记录当前的locator<br>
	 * 当引擎为webdriver的时候，WebElement已经生成，不用进行记录*/
	private String seleniumCurrentLocator;
	public String getSeleniumCurrentLocator() {
		return seleniumCurrentLocator;
	}
	public void setSeleniumCurrentLocator(String seleniumCurrentLocator) {
		this.seleniumCurrentLocator = seleniumCurrentLocator;
	}
	private Map<String, String> locators=new HashMap<String, String>();
	public void addIdLocator(String id){
		locators.put("id", id);
	}
	public void addNameLocator(String name){
		locators.put("name", name);
	}
	public void addXpathLocator(String xpath){
		locators.put("xpath", xpath);
	}
	public void addCSSLocator(String css){
		locators.put("css", css);
	}
	public void addLinkTextLocator(String linkText){
		locators.put("linktext",linkText);
	}
	public void addPartialLinkTextLocator(String partialLinkText){
		locators.put("partiallinktext",partialLinkText);
	}
	public void addTagNameLocator(String tagName){
		locators.put("tagname", tagName);
	}
	public void addRegularLocator(String regular){
		locators.put("regular",regular);
	}
	public void addClassLocator(String className){
		locators.put("class", className);
	}
	public void addAttributeLocator(String attributeName,String attributeValue){
		locators.put(attributeName, attributeValue);
	}
	/**根据locatorType获得locator*/
	public String getLocator(String locatorType){
		return locators.get(locatorType);
	}
	public String getLocatorById(){
		return locators.get("id");
	}
	public String getLocatorByName(){
		return locators.get("name");
	}
	public String getLocatorByXpath(){
		return locators.get("xpath");
	}
	public String getLocatorByCSS(){
		return locators.get("css");
	}
	public String getLocatorByLinkText(){
		return locators.get("linktext");
	}
	public String getLocatorByPartialLinkText(){
		return locators.get("partiallinktext");
	}
	public String getLocatorByRegular(){
		return locators.get("regular");
	}
	public String getLocatorByTagName(){
		return locators.get("tagname");
	}
	public String getLocatorByClass(){
		return locators.get("class");
	}
	public String getXpathFromTagName(){
		if(locators.get("tagname")!=null)
			return ".//"+locators.get("tagname");
		return null;
	}
	public String getXpathFromId(){
		if(locators.get("id")!=null)
			return ".//*[@id=\'"+locators.get("id")+"\']";
		return null;
	}
	public String getXpathFromName(){
		if(locators.get("name")!=null)
			return ".//*[@name=\'"+locators.get("name")+"\']";
		return null;
	}
	public String getXpathFromClass(){
		if(locators.get("class")!=null)
			return ".//*[@class=\'"+locators.get("class")+"\']";
		return null;
	}
	public String getXpathFromLinkText(){
		if(locators.get("linktext")!=null)
			return ".//*[text()=\'"+locators.get("linktext")+"\']";
		return null;
	}
	public String getXpathFromPartialLinkText(){
		if(locators.get("partiallinktext")!=null)
			return ".//*[contains(text(),\'"+locators.get("partiallinktext")+"\')]";
		return null;
	}
	
}
