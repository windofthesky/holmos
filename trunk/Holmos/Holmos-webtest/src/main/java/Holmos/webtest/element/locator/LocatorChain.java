package Holmos.webtest.element.locator;

import java.util.ArrayList;
import java.util.List;

/**Locator链，保存了多级元素的locator链
 * 为了保证能在运行的时候寻找locator
 * @author 吴银龙(15857164387)
 * */
public class LocatorChain {
	private List<LocatorValue> infoNodes;
	public List<LocatorValue> getInfoNodes() {
		return infoNodes;
	}
	public LocatorChain(){
		this.infoNodes=new ArrayList<LocatorValue>();
	}
	public void addNode(LocatorValue infoNode){
		this.infoNodes.add(infoNode);
	}
	public void addParentNode(List<LocatorValue> parentNode){
		this.infoNodes.addAll(parentNode);
	}
}
