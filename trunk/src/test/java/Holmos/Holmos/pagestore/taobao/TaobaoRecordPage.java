package Holmos.Holmos.pagestore.taobao;

import Holmos.Holmos.element.Button;
import Holmos.Holmos.element.RichTextField;
import Holmos.Holmos.element.TextField;
import Holmos.Holmos.struct.Frame;
import Holmos.Holmos.struct.Page;

public class TaobaoRecordPage extends Page{
	public TaobaoRecordPage(){
		super();
		this.comment="淘宝写日志页面";
		init();
	}
	public TextField recordTitle=new TextField("日志标题");
	public RecordContentFrame recordContent=new RecordContentFrame("日志内容");
	public Button submitBtn=new Button("发表按钮");
	{
		recordTitle.addIDLocator("articleTitle");
		recordContent.addXpathLocator(".//*[@id='editArticleForm']/div[2]/div/ul/li[3]/div/div[2]/iframe");
		submitBtn.addIDLocator("editArticleBtn");
	}
	public class RecordContentFrame extends Frame{

		public RecordContentFrame(String comment) {
			super(comment);
		}
		public TextField recordContent=new TextField("日志内容");
		{
			recordContent.addXpathLocator("html/body");
		}
	}
}
