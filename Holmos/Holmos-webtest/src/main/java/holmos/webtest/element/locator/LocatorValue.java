package holmos.webtest.element.locator;

import org.openqa.selenium.WebElement;

/**保存locator链路上的信息，包含两个元素
 * （1）一个节点的locator信息
 * （2）下一个节点的对象
 * @author 吴银龙(15857164387)
 * */
public interface LocatorValue {
	public Locator getLocator();
	public String getComment();
	public void setComment(String comment);
	public WebElement getElement();
	public void setElement(WebElement element);
	public String getLocatorCurrent();
	public void setLocatorCurrent(String locatorCurrent);
	public boolean isExist();
	public LocatorChain getInfoChain();
	public void setWholeComment(String currentComment);
}
