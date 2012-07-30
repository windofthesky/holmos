package Holmos.Holmos.pagestore.xinlangziliao;

import Holmos.Holmos.element.Button;
import Holmos.Holmos.element.Element;
import Holmos.Holmos.element.Link;
import Holmos.Holmos.element.TextField;
import Holmos.Holmos.struct.Collection;
import Holmos.Holmos.struct.Page;

public class SearchResultPage extends Page{
	public SearchResultPage(){
		super();
		this.comment="新浪资料搜索结果页";
		init();
	}
	/**搜索输入框*/
	public TextField input=new TextField("搜索输入框");
	{
		input.addIDLocator("is");
	}
	/**搜索按钮*/
	public Button search=new Button("搜索按钮");
	{
		search.addXpathLocator("html/body/table[1]/tbody/tr/td[3]/table/tbody/tr[2]/td/input[2]");
	}
	public SearchResult result=new SearchResult("搜索结果");
	{
		result.addXpathLocator("html/body/div[1]/table");
	}
	public class SearchResult extends Collection{

		public SearchResult(String comment) {
			super(comment);
		}
		
		public Element resultImg=new Element("搜索结果图片");
		public Link resultLink=new Link("搜索结果连接");
		public Element resultSort=new Element("结果分类信息");
		public Element otherInfo=new Element("其他结果信息");
		{
			resultImg.addXpathLocator("/tbody/tr/td/div[1]/img");
			resultLink.addXpathLocator("/tbody/tr/td/div[1]/a/span");
			resultSort.addXpathLocator("/tbody/tr/td/div[2]");
			otherInfo.addXpathLocator("/tbody/tr/td/span[2]/span[1]");
		}
	}
}
