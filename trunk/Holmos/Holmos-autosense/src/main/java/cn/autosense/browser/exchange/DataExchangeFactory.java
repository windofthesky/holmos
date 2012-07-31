package cn.autosense.browser.exchange;

import cn.autosense.browser.data.InitDataBean;
import cn.autosense.browser.exchange.impl.holmos.HolmosDataExchangeSupport;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-6-4 下午3:01:36<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
public class DataExchangeFactory {

	@SuppressWarnings("unchecked")
	public static IDataExchange build(String className) {
		IDataExchange instance = new HolmosDataExchangeSupport();
		try {
			Class<IDataExchange> c = (Class<IDataExchange>) Class.forName(className);
			instance = c.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return instance;
	}
	
	public static IDataExchange build() {
		String clazz = InitDataBean.getInstance().getDataExchangeClassName();
		return build(clazz);
	}
}
