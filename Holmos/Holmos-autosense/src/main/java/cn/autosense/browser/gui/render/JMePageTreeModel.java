package cn.autosense.browser.gui.render;

import javax.swing.JPanel;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;

import cn.autosense.browser.gui.componment.JMePageTree;
import cn.autosense.plug.psm.GroupInfo;
import cn.autosense.plug.psm.impl.FolderInfo;

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
	
	public JMePageTreeModel(VarNode root) {
		super(root);
	}

	@Override
	public void addTreeModelListener(TreeModelListener l) {
		// TODO Auto-generated method stub
		super.addTreeModelListener(l);
		System.out.println("--------------------");
	}
	
	public void insertNodeInto(VarNode node, VarNode parent, int index) {
		insertNodeInto(node, parent, index);
	}
	
	public void insertNode(GroupInfo[] groupInfos, MutableTreeNode parent) {
		boolean hasNode = false;
		for (GroupInfo info : groupInfos) {
			int childCount = parent.getChildCount();
			for (int i = 0; i < childCount; i++) {
				if(info.equals(parent.getChildAt(i).toString())) {
					hasNode = true;
					break;
				}
			}
			if(!hasNode || childCount == 0) {
				insertNodeInto(new VarNode(info), parent, childCount);
			}
		}
	}

	////////////////////////
	public static void main(String[] args) {
		JMePageTreeModel model = new JMePageTreeModel();
		String rootPath = "aaaaaaaaaaa";
    	System.out.println(rootPath + "------------------------");
    	
    	FolderInfo info = new FolderInfo(rootPath, "root");
    	VarNode root = new VarNode(info);
		
		model.setRoot(root);
		
		JMePageTree tree = new JMePageTree();
		tree.setModel(model);
		tree.setBounds(5, 5, 200, 300);
		
		JPanel panel = new JPanel();
		//panel.setLayout(new CardLayout());
		panel.setLayout(null);
		panel.add(tree);
		
		UtilGUI.show(panel, 300, 400);
	}
}
