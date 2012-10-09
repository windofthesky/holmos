package holmos.webtest.element;

import holmos.webtest.Allocator;
import holmos.webtest.BrowserWindow;
import holmos.webtest.SeleniumBrowserWindow;
import holmos.webtest.WebDriverBrowserWindow;
import holmos.webtest.element.tool.SeleniumElementExist;
import holmos.webtest.element.tool.WebDriverElementExist;

/**
 * 对于Collection结构但是每个元素不是容器的类型的元素
 * @author 吴银龙(307087558)
 * */
public class ListElement extends Element{

	public ListElement(String comment) {
		super(comment);
	}
	private int index=1;
	public int getIndex() {
		return index;
	}
	public void select(int index) {
		this.index = index;
	}
	public String getComment(){
		return "第"+index+"个"+comment;
	}
	/**
	 * 获得ListElement里面包含的Element数量
	 * */
	public int getElemenetsSize(){
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(currentWindow instanceof WebDriverBrowserWindow)
			exist=new WebDriverElementExist(this);
		else if(currentWindow instanceof SeleniumBrowserWindow)
			exist=new SeleniumElementExist(this);
		return exist.getListElementSize();
	}
}
