package cn.autosense.plug.psm.impl;

import java.util.ArrayList;
import java.util.List;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import cn.autosense.plug.psm.GroupInfo;
import cn.autosense.plug.psm.VarException;
import cn.autosense.plug.psm.VarInfo;
import cn.autosense.plug.psm.adapter.CollectionInfoAdapter;
import cn.autosense.plug.psm.type.VarType;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-8-2 下午1:22:18<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
@Log4j
@NoArgsConstructor
public class PageInfo extends CollectionInfoAdapter implements GroupInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected List<GroupInfo> groupInfos = new ArrayList<GroupInfo>();

	public PageInfo(String name, String comment) {
		super(name, comment, VarType.PAGE);
	}

	@Override
	@SneakyThrows(VarException.class)
	public void setType(VarType type) {
		super.setType(VarType.PAGE);
		throw new VarException("PageInfo's Type is only PAGE, but not " + type.name());
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
	public boolean isRoot() {
		return false;
	}

	@Override
	@SneakyThrows(VarException.class)
	public boolean add(GroupInfo info){
		if(info.getType() == VarType.FOLDER 
				|| info.getType() == VarType.PAGE) {
			log.error("PageInfo can not add FolderInfo or PageInfo...");
			throw new VarException("PageInfo can not add FolderInfo or PageInfo...");
		}
		return groupInfos.add(info);
	}

	@Override
	public boolean remove(GroupInfo info) {
		log.debug("remove GroupInfo " + info.getName());
		return groupInfos.remove(info);
	}

	@Override
	public GroupInfo group(String name) {
		for (GroupInfo info : groupInfos) {
			if(name.equalsIgnoreCase(info.getName())){
				return info;
			}
		}
		return null;
	}

	@Override
	public List<VarInfo> child() {
		// TODO Auto-generated method stub
		return null;
	}

}
