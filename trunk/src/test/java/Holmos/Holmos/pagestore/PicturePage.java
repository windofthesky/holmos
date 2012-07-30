package Holmos.Holmos.pagestore;

import Holmos.Holmos.element.Element;
import Holmos.Holmos.struct.Page;

public class PicturePage extends Page{
	public PicturePage(){
		this.comment="头像校验";
	}
	public Element picture=new Element("头像");
	{
		picture.addCSSLocator("html body img");
	}
}
