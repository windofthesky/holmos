package Holmos.Holmos.pagestore.taobao;

import Holmos.Holmos.EngineType;
import Holmos.Holmos.element.Button;
import Holmos.Holmos.element.CheckBox;
import Holmos.Holmos.element.TextField;
import Holmos.Holmos.struct.Page;
import Holmos.Holmos.tools.HolmosWindow;

public class TaobaoLoginPage extends Page{
	public TaobaoLoginPage(){
		super();
		this.comment="淘宝页面";
	}
	public TextField userName=new TextField("用户名输入框");
	public TextField password=new TextField("密码输入框");
	public CheckBox safeWidget=new CheckBox("安全控件");
	public Button login=new Button("登陆按钮");
	{
		userName.addIDLocator("TPL_username_1");
		userName.addNameLocator("TPL_username");
		userName.addXpathLocator(".//*[@id='TPL_username_1']");
		userName.addCSSLocator("#TPL_username_1");
		
		password.addIDLocator("TPL_password_1");
		//password.addCSSLocator("");
		
		//safeWidget.addIDlocator("J_SafeLoginCheck");
		//safeWidget.addXpathLocator(".//*[@id='J_SafeLoginCheck']");
		safeWidget.addCSSLocator("#J_SafeLoginCheck");
		
		//login.addIDlocator("");
		//login.addNameLocator("");
		login.addXpathLocator(".//*[@class='J_Submit']");
		login.addCSSLocator(".J_Submit");
		login.addLinkTextLocator("登录");
		login.addPartialLinkTextLocator("登");
	}
	public void login(String userName,String password){
		HolmosWindow.openNewWindow(EngineType.WebDriverChrome,"http://login.taobao.com");
		this.userName.setText(userName);
		if(this.safeWidget.isChecked()){
			this.safeWidget.click();
		}this.password.setText(password);
		this.login.clickAndWaitForIncludeUrl("http://i.taobao.com/my_taobao");
	}
}
