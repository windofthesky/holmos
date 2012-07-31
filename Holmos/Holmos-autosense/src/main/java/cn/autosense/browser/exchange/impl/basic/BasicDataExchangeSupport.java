package cn.autosense.browser.exchange.impl.basic;

import lombok.Getter;
import lombok.Setter;
import cn.autosense.browser.exchange.util.CatalogDataType;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-6-4 下午3:29:49<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
public class BasicDataExchangeSupport {
//public class BasicDataExchangeSupport implements IDataExchange {

	@Getter
	@Setter
	private CatalogDataType catalogDataType;
	@Getter
	@Setter
	private String selectPagePath;
	@Getter
	@Setter
	private String selectPageName;
	

}
