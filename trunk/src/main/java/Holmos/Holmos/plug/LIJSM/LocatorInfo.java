package Holmos.Holmos.plug.LIJSM;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class LocatorInfo {
	/*元素的直接定位器，例如css,xpath,text*/
	private Map<LocatorType, String> locators=new HashMap<LocatorType, String>();
	/*元素的属性列表*/
	private Map<String,String> attributes=new HashMap<String, String>();
	public Map<String, String> getAttributes() {
		return attributes;
	}
	public void addAttribute(String attributeName, String attributeValue){
		this.attributes.put(attributeName, attributeValue);
	}
	public void addCss(String css){
		this.locators.put(LocatorType.CSS, css);
	}
	public void addText(String text){
		this.locators.put(LocatorType.TEXT, text);
	}
	public void addXpath(String xpath){
		this.locators.put(LocatorType.XPATH, xpath);
	}
	
	/**
	 * 添加元素的直接定位器的方法, by mashupeye
	 * @param type
	 * @param value
	 */
	public void addLocator(LocatorType type, String value) {
		this.locators.put(type, value);
	}
	
	public String getCss(){
		return this.locators.get(LocatorType.CSS);
	}
	public String getText(){
		return this.locators.get(LocatorType.TEXT);
	}
	public String getXpath(){
		return this.locators.get(LocatorType.XPATH);
	}  
	
	/**
	 * 得到元素的直接定位器的方法, by mashupeye
	 * @param type
	 * @return
	 */
	public String getLocator(LocatorType type){
		return this.locators.get(type);
	}  
	
	public String getAttribute(String attributeName){
		return this.attributes.get(attributeName);
	}
	public boolean equals(LocatorInfo anotherLocator){
		if(locators.size()!=anotherLocator.locators.size())
			return false;
		if(attributes.size()!=anotherLocator.attributes.size())
			return false;
		for(Entry<LocatorType, String>entry:locators.entrySet()){
			if(entry.getValue()!=null&&!entry.getValue().equalsIgnoreCase(anotherLocator.locators.get(entry.getKey()))){
				return false;
			}
		}
		for(Entry<String, String>entry:attributes.entrySet()){
			if(entry.getValue()!=null&&!entry.getValue().equalsIgnoreCase(anotherLocator.attributes.get(entry.getKey()))){
				return false;
			}
		}
		return true;
	}
}
