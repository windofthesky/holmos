package Holmos.Holmos.pagestore.w3school;

import Holmos.Holmos.element.Button;
import Holmos.Holmos.struct.Frame;
import Holmos.Holmos.struct.Page;

public class AlertPage extends Page{
	public AlertPage(){
		super();
		this.comment="alert弹出框页面";
		init();
	}
	public AlertFrame alertFrame=new AlertFrame("alertFrame");
	{
		alertFrame.addNameLocator("i");
	}
	public class AlertFrame extends Frame{

		public AlertFrame(String comment) {
			super(comment);
		}
		public Button alert=new Button("弹出框按钮");
		{
			alert.addXpathLocator("html/body/input");
		}
	}
	
}
