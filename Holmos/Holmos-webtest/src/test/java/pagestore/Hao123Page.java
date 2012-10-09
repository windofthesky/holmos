package pagestore;

import holmos.webtest.element.Button;
import holmos.webtest.element.Label;
import holmos.webtest.element.Link;
import holmos.webtest.element.ListElement;
import holmos.webtest.element.TextField;
import holmos.webtest.struct.Page;


public class Hao123Page extends Page {
	
	public Hao123Page(){
		super();
		this.comment="hao123";
		init();
	}
	public Link login=new Link("login");
	public TextField mailUserName=new TextField("mailname");
	public Label mailselect=new Label("mailselect");
	public TextField mailPassWord=new TextField("mail");
	public Button mailsubmit=new Button("mainsubmit");
	public TextField userName=new TextField("username");
	public TextField passWord=new TextField("password");
	public Button loginButton=new Button("loginButton");
	{
		mailUserName.addIDLocator("mailUserName");
		mailPassWord.addIDLocator("mailPassWord");
		mailselect.addIDLocator("mailselect");
		mailsubmit.addIDLocator("mailsubmit");
		login.addXpathLocator("/html/body/div/div/div/div/div[2]/ul/li[5]/a");
		userName.addIDLocator("pass_login_username_0");
		passWord.addIDLocator("pass_login_password_0");
		loginButton.addIDLocator("pass_login_input_submit_0");
	}
	public ListElement mailList=new ListElement("邮箱列表");
	{
		mailList.addXpathLocator("//*[@id='mail_list']/li");
	}
}
