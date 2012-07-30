package Holmos.Holmos.pagestore.taobao;

import Holmos.Holmos.element.Element;
import Holmos.Holmos.element.Link;
import Holmos.Holmos.struct.Page;
import Holmos.Holmos.struct.SubPage;

public class TaobaoHomePage extends Page{
	public TaobaoHomePage(){
		super();
		this.comment="淘宝首页";
		init();
	}
	public AllCategory category=new AllCategory("全部类目");
	{
		category.addIDLocator("J_CategoryHover");
	}
	
	public class AllCategory extends SubPage{
		public AllCategory(String comment) {
			super(comment);
		}
		public Element allCategoryImg=new Element("所有类目的图片");
		public VirtualCategory virtualCategory=new VirtualCategory("虚拟充值子类目");
		{
			allCategoryImg.addXpathLocator("/div[1]/h4/a/img");
			virtualCategory.addXpathLocator("/div[2]");
		}
		public class VirtualCategory extends SubPage{

			public VirtualCategory(String comment) {
				super(comment);
			}
			public Link recharge = new Link("充值标签");
			{
				recharge.addXpathLocator("/ul/li[1]/h5/a");
			}
		}
	}
}
