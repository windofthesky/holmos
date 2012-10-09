package pagestore;

import holmos.webtest.EngineType;
import holmos.webtest.basetools.HolmosWindow;
import holmos.webtest.element.Button;
import holmos.webtest.element.TextField;
import holmos.webtest.struct.Page;

public class SinaHomePage extends Page{
	public SinaHomePage(){
		this.comment="新浪资料搜索首页";
		this.init();
	}
	public TextField input=new TextField("搜索框");
	public Button search=new Button("搜索按钮");
	{
		input.addIDLocator("is");
		search.addXpathLocator("html/body/center[3]/table/tbody/tr/td[3]/table/tbody/tr[2]/td[2]/input[1]");
	}
	public void search(String content){
		HolmosWindow.openNewWindow(EngineType.WebDriverChrome, "http://ishare.iask.sina.com.cn/");
		input.setText(content);
		search.click();
	}
}
