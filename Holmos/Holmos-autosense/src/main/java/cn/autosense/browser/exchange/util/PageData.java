package cn.autosense.browser.exchange.util;

import java.io.Serializable;

import lombok.Data;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-6-11 上午11:36:24<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
@Data
public class PageData implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	private String name;
	private String comment;
	private String pageType;

}
