package pagestore.tianya;

import java.util.ArrayList;

import Holmos.webtest.element.Label;
import Holmos.webtest.element.Link;
import Holmos.webtest.struct.Collection;
import Holmos.webtest.struct.Page;

public class ForumContentPage extends Page{
	public ForumContentPage(){
		super();
		this.comment="天涯论坛页面内容页面";
		init();
	}
	public String jijingUrl="http://www.tianya.cn/publicforum/articleslist/0/develop.shtml";
	public String chuangyeUrl="http://www.tianya.cn/publicforum/articleslist/0/enterprise.shtml";
	public String zhichangUrl="http://www.tianya.cn/publicforum/articleslist/0/no20.shtml";
	public String fangchanUrl="http://www.tianya.cn/publicforum/articleslist/0/house.shtml";
	
	public class PostsCollection extends Collection{

		public PostsCollection(String comment){
			super(comment);
		}
		public Link tiezi=new Link("帖子");
		public Link zuozhe=new Link("作者");
		public Label fangwen=new Label("访问量");
		public Label huifu=new Label("回复量");
		public Label gengxin=new Label("更新日期");
		{
			tiezi.addXpathLocator("tbody/tr/td[2]/a");
			zuozhe.addXpathLocator("tbody/tr/td[3]/a");
			fangwen.addXpathLocator("tbody/tr/td[4]");
			huifu.addXpathLocator("tbody/tr/td[5]");
			gengxin.addXpathLocator("tbody/tr/td[6]");
		}
	}
	public PostsCollection content=new PostsCollection("论坛帖子内容区");
	{
		content.addXpathLocator(".//*[@id='forumContentDiv']/table");
	}
	public Link nextPage=new Link("下一页连接");
	{
		nextPage.addLinkTextLocator("下一页");
	}
	public ArrayList<InfoStructure>getInfos(int pageCount){
		ArrayList<InfoStructure>infos=new ArrayList<InfoStructure>();
		for(int j=0;j<pageCount;j++){
			for(int i=9;i<=53;i++){
				InfoStructure info=new InfoStructure();
				content.select(i);
				info.setFangwen(Integer.parseInt(content.fangwen.getText()));
				info.setHuifu(Integer.parseInt(content.huifu.getText()));
				info.setUrl(content.tiezi.getHref());
				infos.add(info);
			}
			System.out.println("点击下一页");
			nextPage.click();
		}
		return infos;
	}
}
