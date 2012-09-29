package holmos.android.element.locator;

import java.util.HashMap;
import java.util.Map;
/**
 * Android App测试窗体元素的定位器，定位方式包括:id,text,
 * */
public class ALocator {
	public static final String ID="id";
	public static final String TEXT="text";
	
	private Map<String, String> locators=new HashMap<String, String>();
	public void addIdLocator(String id){
		locators.put(ID, id);
	}
	public void addTextLocator(String text){
		locators.put(TEXT, text);
	}
	
	public String getLocatorById(){
		return locators.get(ID);
	}
	public String getLocatorByText(){
		return locators.get(TEXT);
	}
}
