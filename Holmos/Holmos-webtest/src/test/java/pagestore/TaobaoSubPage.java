package pagestore;

import holmos.webtest.element.Label;
import holmos.webtest.struct.SubPage;

public class TaobaoSubPage extends SubPage{

	public TaobaoSubPage(String comment) {
		super(comment);
	}
	public Label buy=new Label("购物标签");
	public Label life=new Label("生活标签");
	public Label tool=new Label("工具标签");
	public Label social=new Label("社区标签");
	public Label other=new Label("其他标签");
	{
		buy.addXpathLocator("./dl[1]/dt");
		life.addXpathLocator("./dl[2]/dt");
		tool.addXpathLocator("./dl[3]/dt");
		social.addXpathLocator("./dl[4]/dt");
		other.addXpathLocator("./dl[5]/dt");
	}
}
