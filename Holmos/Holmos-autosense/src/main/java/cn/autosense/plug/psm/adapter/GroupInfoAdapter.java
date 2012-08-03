package cn.autosense.plug.psm.adapter;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import cn.autosense.plug.psm.GroupInfo;
import cn.autosense.plug.psm.type.VarType;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-8-2 下午3:07:23<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
@Log4j
@NoArgsConstructor
@AllArgsConstructor
public abstract class GroupInfoAdapter implements GroupInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected final List<GroupInfo> groupInfos = new ArrayList<GroupInfo>();

	@Getter
	@Setter
	private String name;
	@Getter
	@Setter
	private String comment;
	@Getter
	@Setter
	private VarType type;

	@Override
	public boolean add(GroupInfo info) {
		log.debug("add GroupInfo " + info.getName());
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

}
