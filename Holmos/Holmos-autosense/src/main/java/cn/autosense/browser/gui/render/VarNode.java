package cn.autosense.browser.gui.render;

import java.util.Enumeration;
import java.util.List;

import javax.swing.Icon;
import javax.swing.tree.DefaultMutableTreeNode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import cn.autosense.plug.psm.VarInfo;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-7-27 上午12:13:58<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
@NoArgsConstructor
@AllArgsConstructor
public class VarNode extends DefaultMutableTreeNode {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	@Getter
	@Setter
	private VarInfo info;
	@Getter
	@Setter
	private Icon icon;
	
	public VarNode(VarInfo info) {
		super();
		this.info = info;
	}

	public String getName() {
		if (info != null) {
			return info.getName();
		} else {
			String str = userObject.toString();
			int index = str.lastIndexOf(".");
			if (index != -1) {
				return str.substring(++index);
			} else {
				return null;
			}
		}
	}

	public void addAll(List<VarNode> children) {
		for (VarNode node : children) {
			add(node);
		}
	}

	public void addAllInfo(List<VarInfo> infoList) {
		for (VarInfo info : infoList) {
			add(new VarNode(info));
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public Enumeration<VarInfo> children() {
		return super.children();
	}
	
	public boolean hasChild() {
		return getChildCount() > 0 ? true : false;
	}
}
