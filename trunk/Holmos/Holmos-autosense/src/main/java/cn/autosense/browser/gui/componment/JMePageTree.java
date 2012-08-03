package cn.autosense.browser.gui.componment;

import java.awt.event.MouseEvent;

import javax.swing.tree.TreeSelectionModel;

import lombok.Getter;
import cn.autosense.browser.data.InitDataBean;
import cn.autosense.browser.gui.event.JMePageTreeActionAdapter;
import cn.autosense.browser.gui.event.JMePageTreeMouseAdapter;
import cn.autosense.browser.gui.render.JMePageTreeModel;
import cn.autosense.browser.gui.render.VarNode;

import com.breeze.core.util.UtilGUI;

import craky.componentc.JCMenuItem;
import craky.componentc.JCPopupMenu;
import craky.componentc.JCScrollPane;
import craky.componentc.JCTree;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-5-24 下午20:07:06<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
public class JMePageTree extends JCScrollPane {

	private static final long serialVersionUID = 1L;

	@Getter
	private JCTree tree;
	@Getter
	private JCPopupMenu popMenu;
	@Getter
	private JCMenuItem selectItem;
	@Getter
	private JCMenuItem addItem;
	@Getter
	private JCMenuItem delItem;
	@Getter
	private JCMenuItem editItem;
	@Getter
	private JMePageTreeActionAdapter	pageTreeAdpter;
	@Getter
	private JMePageTreeMouseAdapter pageTreeMouseAdapter;
	@Getter
	private JMePageTreeModel treeModel;
	
	public JMePageTree() {
		this.getHeaderLabel().setText("Page Tree");

		tree = new JCTree() {
			private static final long	serialVersionUID	= 1L;
			public String getToolTipText(MouseEvent event) {
				try {
					VarNode node = (VarNode)tree.getPathForLocation(event.getX(), event.getY()).getLastPathComponent();
					// TODO
					/*if(node.isRoot()) {
						return "";
					}*/
					return node.getName();
				} catch (Exception e) {
					return "";
				}
			}
		};
		tree.setEditable(true);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		//tree.setCellEditor(new DefaultTreeCellEditor(tree, new DefaultTreeCellRenderer()));
		tree.setToolTipText("");

		treeModel = new JMePageTreeModel();
		// TODO
		/*DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
		treeModel.setRoot(root);
		String[] nodes = {"aaaa", "bbbb"};
		treeModel.insertNode(nodes, root);*/

		tree.setModel(treeModel);

		selectItem = new JCMenuItem("选择");
		addItem = new JCMenuItem("添加");
		delItem = new JCMenuItem("删除");
		editItem = new JCMenuItem("修改");

		popMenu = new JCPopupMenu();
		popMenu.add(selectItem);
		popMenu.addSeparator();// 添加分割线
		popMenu.add(addItem);
		popMenu.add(delItem);
		popMenu.add(editItem);

		pageTreeAdpter = new JMePageTreeActionAdapter(this);
		selectItem.addActionListener(pageTreeAdpter);
		addItem.addActionListener(pageTreeAdpter);
		delItem.addActionListener(pageTreeAdpter);
		editItem.addActionListener(pageTreeAdpter);

		pageTreeMouseAdapter = new JMePageTreeMouseAdapter(this);
		tree.addMouseListener(pageTreeMouseAdapter);
		
		this.setViewportView(tree);
		this.setVisible(true);

		initTree();
	}

//	public DefaultMutableTreeNode getRoot() {
//		return (DefaultMutableTreeNode)tree.getModel().getRoot();
//	}
	
	 /**
     * 
     */
    private void initTree() {
    	String rootPath = InitDataBean.getInstance().getRootPath();
    	if(rootPath.length() > 10) {
			rootPath = rootPath.substring(0, 7) + "...";
		}//"root(" + rootPath + ")"
		//VarNode rootNode = new VarNode(InitDataBean.getInstance().getDataExchange().root());
		/*if(rootNode.hasChild()) {
			rootNode.add(newChild)
		}*/
		// TODO 得到一级
    	//treeModel.setRoot(rootNode);
    }

	public static void main(String[] args) {
		UtilGUI.show(new JMePageTree(), 300, 600);
	}
}