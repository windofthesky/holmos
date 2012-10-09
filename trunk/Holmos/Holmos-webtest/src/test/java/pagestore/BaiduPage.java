package pagestore;

import holmos.webtest.element.Button;
import holmos.webtest.element.Image;
import holmos.webtest.element.Link;
import holmos.webtest.element.TextField;
import holmos.webtest.struct.Frame;
import holmos.webtest.struct.Page;

public class BaiduPage extends Page{
	public BaiduPage(){
		super();
		this.comment="百度首页";
		this.init();
	}
	public Image logo=new Image("百度logo");
	public Link loginLink=new Link("登录标签");
	public TextField input=new TextField("百度输入框");
	public Button search=new Button("百度一下");
	public UserInfoFrame userinfo=new UserInfoFrame("登录信息");
	{
		logo.addXpathLocator(".//*[@id='lg']/img");
		loginLink.addIDLocator("lb");
		input.addIDLocator("kw");
		search.addIDLocator("su");
		userinfo.addIDlocator("login_iframe");
	}
	public class UserInfoFrame extends Frame{
		public UserInfoFrame(String comment) {
			super(comment);
		}
		public TextField username=new TextField("用户名输入框");
		public TextField password=new TextField("密码输入框");
		public Button loginBtn=new Button("登录按钮");
		{
			username.addIDLocator("pass_login_username_0");
			password.addIDLocator("pass_login_password_0");
			loginBtn.addIDLocator("pass_login_input_submit_0");
		}
	}
	
}
