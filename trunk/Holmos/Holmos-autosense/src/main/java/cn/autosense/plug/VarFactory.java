package cn.autosense.plug;

import lombok.SneakyThrows;
import cn.autosense.plug.psm.GroupInfo;
import cn.autosense.plug.psm.VarException;
import cn.autosense.plug.psm.impl.FolderInfo;
import cn.autosense.plug.psm.impl.PageInfo;
import cn.autosense.plug.psm.type.VarType;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-8-3 下午2:11:24<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
public class VarFactory {

	@SneakyThrows(VarException.class)
	public static GroupInfo create(String name, String comment, VarType type) {
		// TODO
		GroupInfo info = null;
		switch(type) {
		case PAGE : 
			info = new PageInfo(name, comment);
			break;
		case FOLDER :
			info = new FolderInfo(name, comment);
			break;
		default:
			throw new VarException("你选择的类型不对, 请重新选择!");
		}
		return info;
	}

}
