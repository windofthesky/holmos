package Holmos.webtest.element;

public class Image extends Element{

	public Image(String comment) {
		super(comment);
	}
	public String getSrc(){
		return this.getAttribute("src");
	}
}
