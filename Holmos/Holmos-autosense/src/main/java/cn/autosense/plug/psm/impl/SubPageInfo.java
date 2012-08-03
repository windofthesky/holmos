package cn.autosense.plug.psm.impl;

import java.util.List;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import cn.autosense.plug.psm.ElementInfo;
import cn.autosense.plug.psm.VarException;
import cn.autosense.plug.psm.VarInfo;
import cn.autosense.plug.psm.adapter.CollectionInfoAdapter;
import cn.autosense.plug.psm.type.VarType;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-8-2 下午3:05:26<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
@NoArgsConstructor
public class SubPageInfo extends CollectionInfoAdapter {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SubPageInfo(String name, String comment) {
		super(name, comment, VarType.SUBPAGE);
	}

	@Override
	@SneakyThrows(VarException.class)
	public void setType(VarType type) {
		super.setType(VarType.SUBPAGE);
		throw new VarException("SubPageInfo's Type is only SUBPAGE, but not " + type.name());
	}
	
	@Override
	public boolean add(ElementInfo info) {
		// TODO Auto-generated method stub
		return super.add(info);
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
