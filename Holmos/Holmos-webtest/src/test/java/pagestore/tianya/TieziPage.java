package pagestore.tianya;

import Holmos.webtest.element.Button;
import Holmos.webtest.element.TextField;
import Holmos.webtest.struct.Page;

public class TieziPage extends Page{
	public TieziPage(){
		super();
		this.comment="帖子页面";
		init();
	}
	public TextField tieziComment=new TextField("回帖输入框");
	public Button submit=new Button("回帖按钮");
	{
		tieziComment.addIDLocator("adsp_content_replybox_area");
		submit.addIDLocator("submit2");
	}
	public void huitie(String content){
		tieziComment.setText(content);
		submit.click();
	}
}
