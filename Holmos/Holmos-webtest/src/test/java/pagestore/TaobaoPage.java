package pagestore;

import holmos.webtest.struct.Page;

public class TaobaoPage extends Page{
	public TaobaoPage(){
		this.comment="淘宝首页";
		this.init();
	}
	public TaobaoSubPage taobaoService=new TaobaoSubPage("淘宝服务");
	{
		taobaoService.addXpathLocator(".//*[@id='J_ProductList']/div");
	}
}
