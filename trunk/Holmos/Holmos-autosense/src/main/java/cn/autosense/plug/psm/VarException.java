package cn.autosense.plug.psm;

import lombok.NoArgsConstructor;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-8-2 下午4:32:06<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
@NoArgsConstructor
public class VarException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VarException(String msg) {
		super(msg);
	}
	
}
