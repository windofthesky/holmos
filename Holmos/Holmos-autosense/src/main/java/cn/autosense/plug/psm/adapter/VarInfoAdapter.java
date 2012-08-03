package cn.autosense.plug.psm.adapter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import cn.autosense.plug.psm.VarInfo;
import cn.autosense.plug.psm.type.VarType;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-8-2 下午3:15:25<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
@NoArgsConstructor
@AllArgsConstructor
public abstract class VarInfoAdapter implements VarInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private String name;
	@Getter
	@Setter
	private String comment;
	@Getter
	@Setter
	private VarType type;

/*	@Override
	public abstract void parent(VarInfo parent);

	@Override
	public abstract VarInfo parent();

	@Override
	public abstract List<VarInfo> child();*/

}
