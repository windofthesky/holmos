package Holmos.webtest.element.tool;

import Holmos.webtest.element.locator.Locator;
import Holmos.webtest.element.locator.LocatorChain;
import Holmos.webtest.element.locator.LocatorValue;

/**用来判断数据结构是否存在的工具类*/
public class WebElementExist {
	protected LocatorValue webElement;
	protected LocatorChain infoChain;
	protected Locator locator;
	public WebElementExist(LocatorValue webElement){
		this.webElement=webElement;
		this.infoChain=webElement.getInfoChain();
		this.locator=webElement.getLocator();
	}
	/**
	 * 自己设置等待次数，一次50ms
	 * @param WaitCount 等待次数
	 * */
	public boolean isElementExist(int WaitCount){
		return false;
	}
	/**只等待一次*/
	public boolean isElementExistForCheckOnce(){
		return false;
	}
}
