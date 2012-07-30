package Holmos.Holmos.pagestore;

import Holmos.Holmos.element.*;
import Holmos.Holmos.struct.*;

public class BaiduPage extends Page{
	public BaiduPage(){
		this.comment="百度首页";
		this.init();
	}
	/**导航条*/
	public Navigate navigate=new Navigate("百度首页导航条");
	{
		navigate.addIDLocator("nv");
		navigate.addXpathLocator(".//*[@id='nv']");
	}
	public class Navigate extends SubPage{
		public Navigate(String comment) {
			super(comment);
		}
		/**mp3连接*/
		public Link mp3=new Link("mp3连接");
		{
			mp3.addNameLocator("tj_mp3");
			mp3.addLinkTextLocator("MP3");
		}
		/**新闻链接*/
		public Link news=new Link("新闻链接");
		{
			news.addNameLocator("tj_news");
			news.addLinkTextLocator("新 闻");
		}
	}
	/**输入框*/
	public TextField input=new TextField("百度输入框");
	{
		input.addIDLocator("kw");
		input.addCSSLocator("#kw");
		input.addNameLocator("wd");
		input.addXpathLocator(".//*[@id='kw']");
	}
	/**搜索按钮*/
	public Button search=new Button("搜索按钮");
	{
		search.addIDLocator("su");
		search.addCSSLocator("#su");
		search.addNameLocator("");
		search.addXpathLocator(".//*[@id='su']");
	}
	
}

