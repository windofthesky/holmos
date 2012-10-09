import holmos.webtest.EngineType;
import holmos.webtest.basetools.HolmosWindow;

import org.junit.Test;

import pagestore.BaiduPage;

public class TestBaidu {
	BaiduPage baiduPage=new BaiduPage();
	/**
	 * 获得logo url实现
	 * */
	@Test
	public void testGetLogoUrl(){
		HolmosWindow.openNewWindow(EngineType.WebDriverChrome, "http://www.baidu.com/");
		System.out.println(baiduPage.logo.getSrc());
		HolmosWindow.closeAllWindows();
	}
	/**
	 * 登录实现
	 * */
	@Test
	public void testLogin(){
		HolmosWindow.openNewWindow(EngineType.WebDriverChrome, "http://www.baidu.com/");
		baiduPage.loginLink.click();
		baiduPage.userinfo.waitForExist();
		baiduPage.userinfo.select();
		baiduPage.userinfo.username.setText("opentesting@163.com");
		baiduPage.userinfo.password.setText("opentest");
		baiduPage.userinfo.loginBtn.click();
		HolmosWindow.closeAllWindows();
	}
	/**
	 * 搜索实现
	 * */
	@Test
	public void testSearch(){
		HolmosWindow.openNewWindow(EngineType.WebDriverChrome, "http://www.baidu.com/");
		baiduPage.input.setText("福尔摩斯");
		baiduPage.search.click();
		HolmosWindow.closeAllWindows();
	}
}
