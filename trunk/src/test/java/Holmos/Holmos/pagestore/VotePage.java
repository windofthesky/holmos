package Holmos.Holmos.pagestore;
import Holmos.Holmos.element.Button;
import Holmos.Holmos.struct.Page;

public class VotePage extends Page{
	public VotePage(){
		this.comment="投票";
	}
	public Button voteBtn=new Button("投票");
	{
		voteBtn.addCSSLocator("html body div#main.clearfix div.colLeft div.article div.article_con div.article_con_head div div.main_pic p a");
	}
}
