package Holmos.webtest.element;
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
}
