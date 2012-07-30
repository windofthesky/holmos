package Holmos.Holmos.pagestore.qq;

import Holmos.Holmos.element.RichTextField;
import Holmos.Holmos.element.TextField;
import Holmos.Holmos.struct.Frame;
import Holmos.Holmos.struct.Page;

public class QZoneRecordPage extends Page{
	public QZoneRecordPage(){
		super();
		this.comment="qq空间写日志页面";
		init();
	}
	public TextField recordTitle=new TextField("日志标题");
	{
		recordTitle.addIDLocator("blog-editor-warp");
	}
	public RecordContentFrame recordContent=new RecordContentFrame("日志内容iframe");
	{
		recordContent.addIDlocator("blogContent_Iframe");
	}
	public class RecordContentFrame extends Frame{

		public RecordContentFrame(String comment) {
			super(comment);
		}
		public RichTextField recordContent=new RichTextField("日志内容区");
		{
			recordContent.addXpathLocator("/html/body");
		}
	}
}
