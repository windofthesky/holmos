package cn.autosense.browser.gui.event;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

import cn.autosense.browser.gui.componment.JMePageTree;
import cn.autosense.browser.gui.render.VarNode;
import cn.autosense.browser.util.CommonUtil;
import cn.autosense.plug.psm.GroupInfo;
import craky.componentc.JCTree;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-6-13 上午10:54:29<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
public class JMePageTreeMouseAdapter extends MouseAdapter {
	
	private JMePageTree pageTree;
	
	public JMePageTreeMouseAdapter(JMePageTree pageTree) {
		this.pageTree = pageTree;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JTree tree = (JTree)e.getSource();
		// 设置选择的节点名称
		VarNode selectNode = (VarNode) tree.getLastSelectedPathComponent();
		//String selectPagePath = CommonUtil.getSelectTreePath(tree);
		if(null == selectNode) {
			// TODO 选择的node为null
			return;
		}
		if(selectNode.isRoot()) {
			// TODO
		}
		
		// 增加子节点
		List<GroupInfo> children = CommonUtil.getChildInfo(selectNode.getInfo());
		if(null != children) {
			selectNode.addAllInfo(children);
		}
		// 展开选择的节点
		tree.expandPath(tree.getSelectionPath());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		JCTree tree = pageTree.getTree();
		TreePath path = tree.getPathForLocation(e.getX(), e.getY());
		if (path == null) {
			return;
		}
		tree.setSelectionPath(path);

		if (e.getButton() == 3) {
			if(((VarNode)tree.getLastSelectedPathComponent()).isRoot()) { // 根节点
				setMenuItemVisiable(false);
			} else {
				setMenuItemVisiable(true);
			}
			pageTree.getPopMenu().show(tree, e.getX(), e.getY());
		}
	}

	/**
	 * 设置Root根节点和非Root节点显示menu不同
	 * @param isVisiable
	 */
	private void setMenuItemVisiable(boolean isVisiable) {
		pageTree.getPopMenu().getComponent(1).setVisible(isVisiable);// 分割线
		pageTree.getSelectItem().setVisible(isVisiable);
		pageTree.getDelItem().setVisible(isVisiable);
		pageTree.getEditItem().setVisible(isVisiable);
	}
}
