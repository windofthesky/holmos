package cn.autosense.browser.gui.render;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import Holmos.Holmos.plug.PSM.VariableInfo;
import Holmos.Holmos.plug.PSM.folder.FolderInfo;
import cn.autosense.browser.util.PageType;

import com.breeze.core.util.UtilGUI;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-6-13 上午9:10:59<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
public class JMePageTreeModel extends DefaultTreeModel {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	public JMePageTreeModel() {
		this(null);
	}
	
	public JMePageTreeModel(TreeNode root) {
		super(root);
	}
	
	public void insertNodeInto(PageNode node, PageNode parent, int index) {
		insertNodeInto(node, parent, index);
	}

	public void insertNode(FolderInfo[] folderInfos, MutableTreeNode parent) {
		boolean hasNode = false;
		for (FolderInfo info : folderInfos) {
			int childCount = parent.getChildCount();
			for (int i = 0; i < childCount; i++) {
				if(info.equals(parent.getChildAt(i).toString())) {
					hasNode = true;
					break;
				}
			}
			if(!hasNode || childCount == 0) {
				insertNodeInto(new PageNode(info, PageType.Folder), parent, childCount);
			}
		}
	}
	
	public void insertNode(VariableInfo[] variableInfos, MutableTreeNode parent) {
		boolean hasNode = false;
		for (VariableInfo info : variableInfos) {
			int childCount = parent.getChildCount();
			for (int i = 0; i < childCount; i++) {
				if(info.equals(parent.getChildAt(i).toString())) {
					hasNode = true;
					break;
				}
			}
			if(!hasNode || childCount == 0) {
				insertNodeInto(new PageNode(info, PageType.Folder), parent, childCount);
			}
		}
	}
	
	public static void main(String[] args) {
		
		JMePageTreeModel model = new JMePageTreeModel();
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
		String[] nodes = {"aaaa", "bbbb"};
		//model.insertNode(nodes, root);
		
		model.setRoot(root);
		
		JTree tree = new JTree();
		tree.setModel(model);
		tree.setBounds(5, 5, 200, 300);
		
		JPanel panel = new JPanel();
		//panel.setLayout(new CardLayout());
		panel.setLayout(null);
		panel.add(tree);
		
		UtilGUI.show(panel, 300, 400);
	}
}
