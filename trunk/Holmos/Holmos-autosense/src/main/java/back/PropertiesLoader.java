package back;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import cn.autosense.browser.util.Const;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-6-1 ����1:54:47<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
public class PropertiesLoader {
	
	//private static PropertiesLoader loader;
	private Properties prop;
	
	public PropertiesLoader() {
		try {
			prop = new Properties();
			InputStream is = new BufferedInputStream(new FileInputStream(Const.INIT_FILE));
			prop.load(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static PropertiesLoader getInstance(String file) {
		return null;
	}
}
