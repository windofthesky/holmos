import org.junit.Test;
import org.openqa.selenium.Keys;

import Holmos.webtest.EngineType;
import Holmos.webtest.basetools.HolmosBaseTools;
import Holmos.webtest.basetools.HolmosWindow;


public class TestA {
	@Test
	public void test(){
		HolmosWindow.openNewWindow(EngineType.WebDriverChrome,"http://www.baidu.com");
		HolmosBaseTools.sleep(5000);
		HolmosWindow.KeyDown(Keys.ENTER);
	}
}
