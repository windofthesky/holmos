package pagestore.tianya;

import Holmos.webtest.EngineType;
import Holmos.webtest.basetools.HolmosWindow;
import Holmos.webtest.element.Button;
import Holmos.webtest.element.TextField;
import Holmos.webtest.struct.Page;

public class LoginPage extends Page{
	public LoginPage(){
		super();
		this.comment="天涯登录页面";
		init();
	}
	public TextField username=new TextField("用户名输入框");
	public TextField password=new TextField("密码输入框");
	public Button loginButton=new Button("登录按钮");
	{
		username.addIDLocator("text1");
		password.addIDLocator("password1");
		loginButton.addIDLocator("button1");
	}
	public void login(String username,String password){
		HolmosWindow.openNewWindow(EngineType.WebDriverFirefox,"http://www.tianya.cn/");
		this.username.setText(username);
		this.password.setText(password);
		this.loginButton.click();
	}
}
