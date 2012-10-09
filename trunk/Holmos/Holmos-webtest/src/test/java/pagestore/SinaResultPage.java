package pagestore;

import holmos.webtest.struct.Page;

public class SinaResultPage extends Page{
	public SinaResultPage(){
		this.comment="新浪资料搜索结果页面";
		this.init();
	}
	public ResultCollection result=new ResultCollection("新浪搜索结果");
	{
		result.addXpathLocator("html/body/div[2]/table");
	}
}
