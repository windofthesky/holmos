package cn.autosense.plug.psm.type;

import java.io.Serializable;



import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-8-1 下午7:13:00<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
public class ReturnInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private ReturnType type;
	
}
