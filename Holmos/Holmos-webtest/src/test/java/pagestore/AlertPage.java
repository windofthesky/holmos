package pagestore;

import holmos.webtest.element.Button;
import holmos.webtest.struct.Frame;
import holmos.webtest.struct.Page;

public class AlertPage extends Page{
	public AlertPage(){
		super();
		this.comment="alert弹框";
		this.init();
	}
	public AlertFrame alertFrame=new AlertFrame("frame");
	{
		alertFrame.addTagNameLocator("iframe");
	}
	public class AlertFrame extends Frame{
		public AlertFrame(String comment) {
			super(comment);
		}
		public Button alsertBtn=new Button("alsert弹框按钮");
		{
			alsertBtn.addXpathLocator("html/body/input");
		}
	}
}
