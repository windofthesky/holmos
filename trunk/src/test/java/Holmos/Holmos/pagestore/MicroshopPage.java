package Holmos.Holmos.pagestore;

import Holmos.Holmos.element.Button;
import Holmos.Holmos.struct.Page;

public class MicroshopPage extends Page{
	public MicroshopPage(){
		this.comment="掌柜说主页";
	}
	public Button attention=new Button("关注按钮");
	{
		attention.addIDLocator("'J_AddFollow");
	}
}
