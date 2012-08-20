package cn.autosense.browser.data;

import java.io.Serializable;
import java.util.Map;

import lombok.Data;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-5-20 下午7:55:31<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
@Data
public class HtmlFiledBean implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	private String tagName;
	private String xpath;
	private String selector;
	private Map<String, String> attrs;
	private String text;
	private String html;

}
