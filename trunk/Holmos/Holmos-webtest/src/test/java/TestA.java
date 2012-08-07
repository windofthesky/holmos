import org.junit.Test;

import Holmos.webtest.EngineType;
import Holmos.webtest.basetools.HolmosWindow;


public class TestA {
	@Test
	public void test(){
		HolmosWindow.openNewWindow(EngineType.WebDriverFirefox,"http://www.baidu.com");
	}
}
