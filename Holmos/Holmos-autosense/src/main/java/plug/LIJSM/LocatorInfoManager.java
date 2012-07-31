package Holmos.Holmos.plug.LIJSM;

import java.util.ArrayList;

/**
 * LocatorInfo 信息管理类，负责和Locator信息存储的底层工作;
   <li>LocatorInfo信息的读写</li>
   <li>LocatorInfo信息的存储方式</li>
   <li>实现java文件,xml,DB三种方式的存储</li>
   <li>提供以后其他存储格式的扩展,只要实现这个接口就可以啦</li>
  @author 吴银龙(15857164387)
*/
public interface LocatorInfoManager {
	public void removeLocatorInfo();
	/**从LocatorInfo存储地取LocatorInfo:
	 * @param name 元素name
	 * @return 与elementId匹配的元素的LocatorInfo信息
	 * */
	public LocatorInfo getLocatorInfoByContent(ArrayList<String>locatorInfoContent);
	/**将LocatorInfo 产生LocatorInfo信息 
	 * @param locatorInfo 供生产的LocatorInfo信息源*/
	public ArrayList<String> createLocatorInfo();
	public void addIDInfo(String id);
	public void addNameInfo(String name);
	public void addXpathInfo(String xpath);
	public void addCSSInfo(String css);
	public void addTextInfo(String text);
	public void addAttributeInfo(String name,String value);
	
	public void removeIDInfo(String id);
	public void removeNameInfo(String name);
	public void removeXpathInfo(String xpath);
	public void removeCSSInfo(String css);
	public void removeTextInfo(String text);
	public void removeAttributeInfo(String name);
	
	public void changeIDInfo(String id);
	public void changeNameInfo(String name);
	public void changeXpathInfo(String xpath);
	public void changeCSSInfo(String css);
	public void changeTextInfo(String text);
	public void changeAttributeInfo(String name,String value);
}
