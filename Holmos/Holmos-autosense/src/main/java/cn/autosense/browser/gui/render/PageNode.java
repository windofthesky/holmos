package cn.autosense.browser.gui.render;

import javax.swing.Icon;
import javax.swing.tree.DefaultMutableTreeNode;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import Holmos.Holmos.plug.PSM.NetPageInfo;
import Holmos.Holmos.plug.PSM.VariableInfo;
import Holmos.Holmos.plug.PSM.container.PageInfo;
import Holmos.Holmos.plug.PSM.folder.FolderInfo;
import cn.autosense.browser.util.PageType;

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
public class PageNode extends DefaultMutableTreeNode {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	@Getter
	@Setter
	private Icon icon;
	/*@Getter
	@Setter
	private VariableInfo varInfo;
	@Getter
	@Setter
	private FolderInfo folderInfo;*/
	@Getter
	@Setter
	private PageType type;
	@Getter
	@Setter
	private NetPageInfo info;
	
	public PageNode(NetPageInfo info, PageType type) {
		this.info = info;
		this.type = type;
	}
	/*public PageNode(FolderInfo folderInfo, PageType type) {
		this.folderInfo = folderInfo;
		this.type = type;
	}
	
	public PageNode(VariableInfo varInfo, PageType type) {
		this.varInfo = varInfo;
		this.type = type;
	}*/

	private String getStrFromPageStore(FolderInfo pageStore) {
		//pageStore.getInfoFromDisk();
		StringBuffer sb = new StringBuffer();
		/* 遍历Folder */
		for (FolderInfo folder : pageStore.getChildFolders()) {
			sb.append(folder.getName()).append(",");
		}
		/* 遍历Page */
		for (PageInfo page : pageStore.getPages()) {
			sb.append(page.getComment()).append(",");
		}
		if(sb.length() > 1) {
			return sb.substring(0, sb.length() - 1).trim();
		} else {
			return "";
		}
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

}
