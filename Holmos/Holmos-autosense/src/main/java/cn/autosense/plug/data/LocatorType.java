package cn.autosense.plug.data;

import lombok.Getter;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-8-2 上午‏‎10:33:40<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
public enum LocatorType {

	SELECTOR("selector"),
	XPATH("xpath"),
	TEXT("text");

	@Getter
	private String name;

	LocatorType(String name){
		this.name = name;
	}
	
}
