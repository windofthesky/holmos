package holmos.webtest.element.tool;

import holmos.webtest.constvalue.ConstValue;
import holmos.webtest.element.locator.Locator;
import holmos.webtest.element.locator.LocatorChain;
import holmos.webtest.element.locator.LocatorValue;

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
	protected void initComment(){
		StringBuilder commentTemp=new StringBuilder();
		for(int i=0;i<infoChain.getInfoNodes().size();i++){
			commentTemp.append(infoChain.getInfoNodes().get(i).getComment()+"-->");
		}
		commentTemp.append(webElement.getComment());
		webElement.setWholeComment(commentTemp.toString());
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
	public int getListElementSize(){
		return ConstValue.ERROR;
		
	}
}
