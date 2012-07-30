package Holmos.Holmos.pagestore.xinlangziliao;

import Holmos.Holmos.element.Button;
import Holmos.Holmos.element.TextField;
import Holmos.Holmos.struct.Page;

public class HomePage extends Page{
	public HomePage(){
		super();
		this.comment="新浪资料首页";
	}
	/**搜索输入框*/
	public TextField input=new TextField("搜索输入框");
	{
		input.addIDLocator("is");
	}
	/**搜索按钮*/
	public Button search=new Button("搜索按钮");
	{
		search.addXpathLocator("html/body/center[3]/table/tbody/tr/td[3]/table/tbody/tr[2]/td[2]/input[1]");
	}
}
