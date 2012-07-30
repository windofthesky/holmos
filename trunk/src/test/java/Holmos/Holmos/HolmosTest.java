package Holmos.Holmos;

import org.junit.Test;

import Holmos.Holmos.pagestore.BaiduPage;
import Holmos.Holmos.pagestore.TripPage;
import Holmos.Holmos.pagestore.taobao.MyTaobaoPage;
import Holmos.Holmos.pagestore.taobao.TaobaoHomePage;
import Holmos.Holmos.pagestore.taobao.TaobaoLoginPage;
import Holmos.Holmos.pagestore.taobao.TaobaoRecordPage;
import Holmos.Holmos.pagestore.w3school.AlertPage;
import Holmos.Holmos.pagestore.w3school.ConfirmPage;
import Holmos.Holmos.pagestore.w3school.PromptPage;
import Holmos.Holmos.pagestore.xinlangziliao.HomePage;
import Holmos.Holmos.pagestore.xinlangziliao.SearchResultPage;
import Holmos.Holmos.tools.HolmosWindow;

public class HolmosTest {
	BaiduPage baidu=new BaiduPage();
	TaobaoLoginPage taobao=new TaobaoLoginPage();
	@Test
	public void testWIE(){
		HolmosWindow.openNewWindow("http://www.baidu.com");
		HolmosWindow.closeAllWindows();
	}
	@Test
	public void testWChrome(){
		HolmosWindow.openNewWindow(EngineType.WebDriverChrome,"http://www.baidu.com");
		HolmosWindow.TakeScreenshot("firstScreenshot");
		HolmosWindow.closeAllWindows();
	}
	@Test
	public void testWFirefox(){
		HolmosWindow.openNewWindow(EngineType.WebDriverFirefox,"http://www.baidu.com");
		HolmosWindow.closeAllWindows();
	}
	@Test
	public void testWSafari(){
		HolmosWindow.openNewWindow(EngineType.WebDriverSafari,"http://www.baidu.com");
		
		HolmosWindow.closeAllWindows();
	}
	@Test
	public void testSIE(){
		HolmosWindow.openNewWindow(EngineType.SeleniumIE,"http://www.baidu.com");
		HolmosWindow.closeAllWindows();
	}
	@Test
	public void testSChrome(){
		HolmosWindow.openNewWindow(EngineType.SeleniumChrome,"http://www.baidu.com");
		HolmosWindow.closeAllWindows();
	}
	@Test
	public void testSFirefox(){
		HolmosWindow.openNewWindow(EngineType.SeleniumFirefox,"http://www.baidu.com");
		HolmosWindow.closeAllWindows();
	}
	@Test
	public void testSSafari(){
		//需要掉地safari版本 3.x
		HolmosWindow.openNewWindow(EngineType.SeleniumSafari,"http://www.baidu.com");
		HolmosWindow.closeAllWindows();
	}
	@Test
	public void testSOpera(){
		HolmosWindow.openNewWindow(EngineType.SeleniumOpera,"http://www.baidu.com");
		HolmosWindow.closeAllWindows();
	}
	@Test
	public void testSubPage(){
		HolmosWindow.openNewWindow(EngineType.WebDriverChrome,"http://www.baidu.com");
		baidu.navigate.mp3.click();
		HolmosWindow.closeAllWindows();
	}
	@Test
	public void testCollection(){
		HomePage xinlang=new HomePage();
		SearchResultPage resultPage=new SearchResultPage();
		HolmosWindow.openNewWindow(EngineType.WebDriverChrome,"http://ishare.iask.sina.com.cn/");
		xinlang.input.setText("福尔摩斯");
		xinlang.search.click();
		for(int i=1;i<=5;i++){
			resultPage.result.select(i);
			System.out.println(resultPage.result.resultImg.getInfoChain().getInfoNodes().size());
			System.out.println(resultPage.result.resultImg.getInfoChain().getInfoNodes().get(1).getLocator().getLocatorByXpath());
			System.out.println(resultPage.result.resultImg.getLocator().getLocatorByXpath());
			System.out.println(resultPage.result.resultImg.isExist());
			System.out.println(resultPage.result.resultLink.getText());
			System.out.println(resultPage.result.resultSort.getText());
		}
		HolmosWindow.closeAllWindows();
	}
	@Test
	public void testFrame(){
		TaobaoLoginPage taobao=new TaobaoLoginPage();
		TaobaoRecordPage record=new TaobaoRecordPage();
		taobao.login("黄庭", "hello1234");
		HolmosWindow.open("http://blog.jianghu.taobao.com/article/edit_article.htm");
		record.recordTitle.setText("新日志");
		System.out.println(record.recordTitle.getCSSValue("textAlign"));
		record.recordContent.select();
		record.recordContent.recordContent.setText("日志内容");
		record.recordContent.selectTopPage();
		record.submitBtn.clickAndWaitForIncludeUrl("show_article");
		HolmosWindow.closeAllWindows();
	}
	@Test
	public void testSubPageRecursion(){
		TaobaoHomePage taobaoHomePage=new TaobaoHomePage();
		HolmosWindow.openNewWindow(EngineType.WebDriverChrome,"http://www.taobao.com/");
		System.out.println(taobaoHomePage.category.isExist());
		System.out.println(taobaoHomePage.category.allCategoryImg.isExist());
		System.out.println(taobaoHomePage.category.virtualCategory.recharge.getText());
		HolmosWindow.closeAllWindows();
	}
	@Test
	public void testCollectionRecursion(){
		TripPage tripPage=new TripPage();
		HolmosWindow.openNewWindow(EngineType.WebDriverChrome, "http://trip.taobao.com");
		tripPage.time.click();
		tripPage.today.click();
	}
	@Test
	public void testFrameRecursion(){
		
	}
	@Test
	public void testJavascript(){
		
	}
	@Test
	public void testSearch(){
		BaiduPage baidu=new BaiduPage();
		HolmosWindow.openNewWindow(EngineType.WebDriverChrome, "http://www.baidu.com");
		baidu.input.setText("福尔摩斯");
		baidu.search.click();
		HolmosWindow.closeAllWindows();
	}
	@Test
	public void testAlert(){
		AlertPage alertPage=new AlertPage();
		HolmosWindow.openNewWindow("http://www.w3school.com.cn/tiy/t.asp?f=jseg_alert");
		alertPage.alertFrame.select();
		alertPage.alertFrame.alert.click();
		System.out.println(HolmosWindow.dealAlert());
		HolmosWindow.closeAllWindows();
	}
	@Test
	public void testPrompt(){
		PromptPage promptPage=new PromptPage();
		HolmosWindow.openNewWindow(EngineType.WebDriverChrome,"http://www.w3school.com.cn/tiy/t.asp?f=jseg_prompt");
		promptPage.promptFrame.select();
		promptPage.promptFrame.prompt.click();
		System.out.println(HolmosWindow.dealPrompt("吴银龙",true));
		HolmosWindow.closeAllWindows();
	}
	@Test
	public void testConfirm(){
		ConfirmPage confirmPage=new ConfirmPage();
		HolmosWindow.openNewWindow(EngineType.WebDriverChrome,"http://www.w3school.com.cn/tiy/t.asp?f=jseg_confirm");
		confirmPage.confirmFrame.select();
		confirmPage.confirmFrame.confirm.click();
		System.out.println(HolmosWindow.dealConfirm(true));
		HolmosWindow.closeAllWindows();
	}
	@Test
	public void testMultiPage(){
		TaobaoHomePage taobaoHomePage=new TaobaoHomePage();
		HolmosWindow.openNewWindow(EngineType.WebDriverChrome,"http://www.taobao.com");
		taobaoHomePage.category.virtualCategory.recharge.click();
		System.out.println(HolmosWindow.getUrl());
		HolmosWindow.attachByContains("chongzhi");
		System.out.println(HolmosWindow.getUrl());
		HolmosWindow.closeAllWindows();
	}
	@Test
	public void testUpLoad(){
		MyTaobaoPage myTaobaoPage=new MyTaobaoPage();
		taobao.login("黄庭", "hello1234");
		myTaobaoPage.prior.click();
		myTaobaoPage.input.setText("叽歪");
		myTaobaoPage.upLoadPic.click();
		HolmosWindow.upLoad("打开", "20100406223837-1385075249.jpg");
		HolmosWindow.closeAllWindows();
	}
	@Test
	public void testClassLoader(){
		
		Thread currentThread=Thread.currentThread();
		System.out.println(System.currentTimeMillis());
		Thread.yield();
		System.out.println(System.currentTimeMillis());
	}
	
}
