package cn.autosense.browser.gui.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import cn.autosense.browser.data.InitDataBean;
import cn.autosense.browser.data.RuntimeDataBean;
import cn.autosense.browser.gui.componment.JMeFieldCollectionPanel;
import cn.autosense.browser.util.CommonUtil;

import com.breeze.core.util.Util;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-6-22 下午5:34:46<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
public class JMeFieldHtmlAdapter implements ActionListener {
	
	private JMeFieldCollectionPanel fieldHtmlPanel;
	
	public JMeFieldHtmlAdapter(JMeFieldCollectionPanel fieldHtmlPanel) {
		this.fieldHtmlPanel = fieldHtmlPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == fieldHtmlPanel.getClearBtn()) {
			clearBtn_actionPerformed(e);
		}
		if (e.getSource() == fieldHtmlPanel.getUpdateBtn()) {
			updateBtn_click(fieldHtmlPanel.getSelectedElementTable());
		}
		if (e.getSource() == fieldHtmlPanel.getDeleteBtn()) {
			deleteBtn_click(fieldHtmlPanel.getSelectedElementTable());
		}
	}


	/**
	 * update按钮点击事件
	 */
	protected void updateBtn_click(JTable table) {
		if (table.getRowCount() > 0 && fieldHtmlPanel.getSelectedRowCount() >= 0) {
		} else {
			JOptionPane.showMessageDialog(null, "请选择要修改的行!", "警告",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * delete按钮点击事件
	 */
	protected void deleteBtn_click(JTable table) {
		int selectRowCount = fieldHtmlPanel.getSelectedRowCount();
		if (table.getRowCount() > 0 && selectRowCount >= 0) {
			((DefaultTableModel) table.getModel()).removeRow(selectRowCount);
		} else {
			JOptionPane.showMessageDialog(null, "请选择要删除的行!", "警告",
					JOptionPane.WARNING_MESSAGE);
		}

		fieldHtmlPanel.getTypeCmb().setSelectedIndex(0);
		table.updateUI();
		// 在文件中删除该元素
		String selectPagePath = RuntimeDataBean.getInstance().getSelectPagePath();
    	if(Util.strIsNullOrEmpty(selectPagePath)) {
    		JOptionPane.showMessageDialog(null, "请先选择page!", "警告", JOptionPane.WARNING_MESSAGE);
    	}else {
    		InitDataBean.getInstance().getDataExchange().removeElement(selectPagePath, table.getModel().getValueAt(selectRowCount, 0).toString());
    	}
		
	}

	/**
	 * clear按钮点击事件
	 * 
	 * @param e
	 */
	protected void clearBtn_actionPerformed(ActionEvent e) {
		int isOK = JOptionPane.showConfirmDialog(null, "你确定要删除表格中所有选择的元素吗?",
				"提示", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (isOK == JOptionPane.YES_OPTION) {
			CommonUtil.clearTable((DefaultTableModel) fieldHtmlPanel.getSelectedElementTable()
					.getModel());
			// 文件中删除
			String selectPagePath = RuntimeDataBean.getInstance().getSelectPagePath();
	    	if(Util.strIsNullOrEmpty(selectPagePath)) {
	    		JOptionPane.showMessageDialog(null, "请先选择page!", "警告", JOptionPane.WARNING_MESSAGE);
	    	}else {
	    		InitDataBean.getInstance().getDataExchange().removeAllElements(selectPagePath);
	    	}
		} else {
			return;
		}
	}
	
}
