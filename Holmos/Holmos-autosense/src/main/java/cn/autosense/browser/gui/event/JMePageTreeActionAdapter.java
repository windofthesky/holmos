package cn.autosense.browser.gui.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultTreeModel;

import cn.autosense.browser.data.ComponentBean;
import cn.autosense.browser.gui.JMeAddPageDialog;
import cn.autosense.browser.gui.componment.JMePageTree;
import cn.autosense.browser.gui.render.VarNode;
import cn.autosense.plug.VarFactory;
import cn.autosense.plug.psm.VarInfo;

import com.breeze.core.util.Util;

import craky.componentc.JCTextField;
import craky.componentc.JCTree;


/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-6-12 下午3:36:34<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
public class JMePageTreeActionAdapter implements ActionListener {

	private JMePageTree tree;
	//private IDataExchange dataExchange;
	
	public JMePageTreeActionAdapter(JMePageTree tree) {
		this.tree = tree;
		//dataExchange = InitDataBean.getInstance().getDataExchange();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == tree.getSelectItem()) {
			selectItem_click(tree.getTree(), e);
		} else if (e.getSource() == tree.getAddItem()) {
			addItem_click(tree.getTree(), e);
		} else if (e.getSource() == tree.getDelItem()) {
			delItem_click(tree.getTree(), e);
		} else if (e.getSource() == tree.getEditItem()) {
			editItem_click(tree.getTree(), e);
		}
	}

	/**
	 * 选择Page操作
	 * @param tree
	 * @param e
	 */
	private void selectItem_click(JCTree tree, ActionEvent e) {
		VarNode selectNode = (VarNode) tree.getLastSelectedPathComponent();
		
		String selectPagePath = selectNode.getInfo().getName();// TODO
		
		int isRemove = JOptionPane.showConfirmDialog(null, "你选择的Page是: \n" + selectPagePath, "温馨提示", JOptionPane.YES_NO_OPTION);
        if (isRemove == JOptionPane.YES_OPTION) {
        	// 
        	JCTextField pagePathTxf = ComponentBean.getInstance().getPageTreePanel().getPageSelectTextField().getTextField();
        	String pagePathOld = pagePathTxf.getText();
        	if(Util.strIsNullOrEmpty(pagePathOld)) {
        		pagePathTxf.setText(selectPagePath);
        		//设置选择路径
    			//RuntimeDataBean.getInstance().setSelectPagePath(selectPagePath);
        	} else {
        		int isSelect = JOptionPane.showConfirmDialog(null, "你选择的Page是: \n" + selectPagePath + "\n之前的Page是: \n" + "你确定要修改", "温馨提示", JOptionPane.YES_NO_OPTION);
        		if(isSelect == JOptionPane.YES_OPTION) {
        			pagePathTxf.setText(selectPagePath);
        			//设置选择路径
        			//RuntimeDataBean.getInstance().setSelectPagePath(selectPagePath);
        		} else {
        			return;
        		}
        	}
        } else {
            return;
        }
	}

	/**
	 * 添加Page操作
	 * @param tree
	 * @param e
	 */
	private void addItem_click(JCTree tree, ActionEvent e) {
		VarNode selectNode = (VarNode) tree.getLastSelectedPathComponent();
		JMeAddPageDialog dialog = new JMeAddPageDialog();
		if(!dialog.isShowing()) {
			// TODO 添加的是不是合法, 比如: page add page
			if(!dialog.getPageType().name().equalsIgnoreCase("page")) {
				JOptionPane.showMessageDialog(null, "非常抱歉, 功能正在完善! 敬请期待");
			}else {
				VarInfo info = VarFactory.create(dialog.getPageName(), dialog.getPageComment(), dialog.getPageType());
				// TODO 验证info是否可以添加
				selectNode.add(new VarNode(info));
				tree.expandPath(tree.getSelectionPath());
			}
		}
	}

	/**
	 * 删除Page操作
	 * @param tree
	 * @param e
	 */
	private void delItem_click(JCTree tree, ActionEvent e) {
		VarNode node = (VarNode) tree.getLastSelectedPathComponent();
		if (node.isRoot()) {
			JOptionPane.showMessageDialog(null, "不能删除根目录?", "温馨提示", JOptionPane.ERROR_MESSAGE);
			return;
		}
		int isRemove = JOptionPane.showConfirmDialog(null, "是否要删除?", "温馨提示", JOptionPane.YES_NO_OPTION);
        if (isRemove == JOptionPane.YES_OPTION) {
            ((DefaultTreeModel) tree.getModel()).removeNodeFromParent(node);
            // name
            /*JsonCreater creater = new JsonCreater();
			creater.put("name", node.toString());
			creater.put("comment", "");
			String jsonData = creater.getJson();*/

			//dataExchange.removePage(RuntimeDataBean.getInstance().getSelectPagePath(), node.toString());
        } else {
            return;
        }
	}

	/**
	 * 编辑Page操作
	 * @param tree
	 * @param e
	 */
	private void editItem_click(JCTree tree, ActionEvent e) {
		tree.startEditingAtPath(tree.getSelectionPath());
	}


}
