package Holmos.webtest.element.tool;

import Holmos.webtest.element.locator.Locator;
import Holmos.webtest.element.locator.LocatorChain;
import Holmos.webtest.element.locator.LocatorValue;
import Holmos.webtest.struct.Collection;

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
		if(webElement instanceof Collection||hasCollection()){
			webElement.setComment("");
			comComment();
		}else{
			if(webElement.getComment().contains("-->"))
				return;
			comComment();
		}
	}
	private boolean hasCollection(){
		for(LocatorValue element:infoChain.getInfoNodes()){
			if(element instanceof Collection)
				return true;
		}
		return false;
	}
	private void comComment(){
		StringBuilder commentTemp=new StringBuilder();
		for(int i=0;i<infoChain.getInfoNodes().size();i++){
			commentTemp.append(infoChain.getInfoNodes().get(i).getComment()+"-->");
		}
		commentTemp.append(webElement.getComment());
		webElement.setComment(commentTemp.toString());
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
