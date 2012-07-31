package cn.autosense.browser.util;

import java.util.ResourceBundle;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-7-26  下午‏‎9:15:05<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
public class InitPropUtil {
	private static final String ROBOTARM = "init";
	private static final ResourceBundle bundle;

	static {
		bundle = ResourceBundle.getBundle(ROBOTARM);
	}

	public static String getPropValue(String key) {
		return bundle.getString(key);
	}

	public static void main(String[] args) {
		System.out.println(InitPropUtil.getPropValue("db-config"));
		System.out.println(InitPropUtil.getPropValue("poolType"));
	}

}
