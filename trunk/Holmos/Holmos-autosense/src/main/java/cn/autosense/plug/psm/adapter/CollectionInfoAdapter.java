package cn.autosense.plug.psm.adapter;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import cn.autosense.plug.psm.CollectionInfo;
import cn.autosense.plug.psm.ElementInfo;
import cn.autosense.plug.psm.GroupInfo;
import cn.autosense.plug.psm.VarInfo;
import cn.autosense.plug.psm.type.VarType;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-8-2 下午3:14:24<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
@NoArgsConstructor
@AllArgsConstructor
public abstract class CollectionInfoAdapter implements CollectionInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected final List<ElementInfo> varInfos = new ArrayList<ElementInfo>();

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
	public boolean add(ElementInfo info) {
		return varInfos.add(info);
	}

	@Override
	public boolean remove(ElementInfo info) {
		return varInfos.remove(info);
	}
	
	@Override
	public ElementInfo element(String name) {
		for (ElementInfo info : varInfos) {
			if(name.equalsIgnoreCase(info.getName())){
				return info;
			}
		}
		return null;
	}

	@Override
	public boolean add(CollectionInfo info) {
		return false;
	}

	@Override
	public boolean remove(CollectionInfo info) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public GroupInfo group(String name) {
		// TODO Auto-generated method stub
		return null;
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

}
