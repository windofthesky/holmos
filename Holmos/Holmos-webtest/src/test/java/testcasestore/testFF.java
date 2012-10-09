package testcasestore;

import holmos.webtest.EngineType;
import holmos.webtest.basetools.HolmosWindow;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import pagestore.SinaHomePage;
import pagestore.SinaResultPage;
import pagestore.TaobaoPage;

public class testFF {
	@Test
	public void testCreateDriver(){
		WebDriver driver=new FirefoxDriver();
		driver.get("http://www.baidu.com");
		WebElement input=driver.findElement(By.id("kw"));
		input.sendKeys("哈哈");
	}
	@Test
	public void testSubPage(){
		TaobaoPage taobao=new TaobaoPage();
		HolmosWindow.openNewWindow(EngineType.WebDriverChrome,"http://www.taobao.com");
		System.out.println(taobao.taobaoService.isExist());
		taobao.taobaoService.buy.outputText();
		taobao.taobaoService.life.outputText();
		taobao.taobaoService.other.outputText();
		taobao.taobaoService.social.outputText();
		taobao.taobaoService.tool.outputText();
		HolmosWindow.closeAllWindows();
	}
	@Test
	public void testCollection(){
		SinaResultPage sina=new SinaResultPage();
		SinaHomePage sinaHome=new SinaHomePage();
		sinaHome.search("福尔摩斯");
		for(int i=1;i<5;i++){
			System.out.println(sina.result.isExist());
			System.out.println(sina.result.isExist(i));
			sina.result.select(i);
			sina.result.resultLink.outputText();
		}
		//HolmosWindow.dealPrompt(input, isYes)
		HolmosWindow.closeAllWindows();
	}
}
