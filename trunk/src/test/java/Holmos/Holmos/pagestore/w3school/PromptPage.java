package Holmos.Holmos.pagestore.w3school;

import Holmos.Holmos.element.Button;
import Holmos.Holmos.pagestore.w3school.ConfirmPage.ConfirmFrame;
import Holmos.Holmos.struct.Frame;
import Holmos.Holmos.struct.Page;

public class PromptPage extends Page{
	public PromptPage(){
		super();
		this.comment="提示框";
		init();
	}
	public PromptFrame promptFrame=new PromptFrame("PromptFrame");
	{
		promptFrame.addNameLocator("i");
	}
	public class PromptFrame extends Frame{

		public PromptFrame(String comment) {
			super(comment);
		}
		public Button prompt=new Button("框按钮");
		{
			prompt.addXpathLocator("html/body/input");
		}
	}
}
