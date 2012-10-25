package pagestore;

import holmos.webtest.EngineType;
import holmos.webtest.basetools.HolmosWindow;

import org.junit.Test;


public class Hao123Test {
	
	Hao123Page hao=new Hao123Page();
	public void testHao123(){
		HolmosWindow.openNewWindow(EngineType.WebDriverChrome, "http://www.hao123.com");
		hao.mailUserName.setText("opentest1");
		hao.mailselect.click();
		hao.mailList.waitForExist();
		hao.mailList.select(3);
		hao.mailList.click();
	}
}
