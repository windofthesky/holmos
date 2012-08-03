package cn.autosense.plug.psm.impl;

import java.util.List;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import cn.autosense.plug.psm.VarException;
import cn.autosense.plug.psm.VarInfo;
import cn.autosense.plug.psm.adapter.GroupInfoAdapter;
import cn.autosense.plug.psm.type.VarType;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-8-2 上午8:51:06<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
@NoArgsConstructor
public class FolderInfo extends GroupInfoAdapter {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public FolderInfo(String name, String comment) {
		super(name, comment, VarType.FOLDER);
	}

	@Override
	@SneakyThrows(VarException.class)
	public void setType(VarType type) {
		super.setType(VarType.FOLDER);
		throw new VarException("PolderInfo's Type is only FOLDER, but not " + type.name());
	}

	@Override
	public boolean isRoot() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void parent(VarInfo parent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public VarInfo parent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VarInfo> child() {
		// TODO Auto-generated method stub
		return null;
	}

}
