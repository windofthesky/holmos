package Holmos.Holmos.pagestore.qq;

import Holmos.Holmos.element.Button;
import Holmos.Holmos.element.TextField;
import Holmos.Holmos.struct.Page;

public class QZoneLoginPage extends Page{
	public QZoneLoginPage(){
		super();
		this.comment="qq空间登录页面";
		init();
	}
	
	public Button loginBtn=new Button("登录按钮");
	{
		loginBtn.addIDLocator("subbtn");
	}
	public TextField userName=new TextField("用户名");
	public TextField password=new TextField("密码");
	{
		userName.addIDLocator("u");
		password.addIDLocator("p");
	}
	public void login(String username,String password){
		this.userName.setText(username);
		this.password.setText(password);
		this.loginBtn.click();
	}
}
