package Holmos.webtest.element;
/**
 * @author 吴银龙(15857164387)
 * */
public class Link extends Element{

	public Link(String comment) {
		super(comment);
	}
	public String getHref(){
		return this.getAttribute("href");
	}
}
