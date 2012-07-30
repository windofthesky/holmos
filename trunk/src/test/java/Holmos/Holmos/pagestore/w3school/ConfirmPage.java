package Holmos.Holmos.pagestore.w3school;

import Holmos.Holmos.element.Button;
import Holmos.Holmos.struct.Frame;
import Holmos.Holmos.struct.Page;

public class ConfirmPage extends Page{
	public ConfirmPage(){
		super();
		this.comment="确认框页面";
		init();
	}
	public ConfirmFrame confirmFrame=new ConfirmFrame("alertFrame");
	{
		confirmFrame.addNameLocator("i");
	}
	public class ConfirmFrame extends Frame{

		public ConfirmFrame(String comment) {
			super(comment);
		}
		public Button confirm=new Button("弹出框按钮");
		{
			confirm.addXpathLocator("html/body/input");
		}
	}
}
