package cn.autosense.browser.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import lombok.Getter;
import cn.autosense.browser.gui.componment.JMeAddPagePanel;
import cn.autosense.plug.psm.type.VarType;

import com.breeze.core.util.Util;
import com.breeze.core.util.UtilGUI;

import craky.componentc.JCDialog;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-7-9 下午9:01:16<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
public class JMeAddPageDialog extends JCDialog implements ActionListener {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	private JPanel contentPanel;
	@Getter
	private JMeAddPagePanel addPagePanel;

	/**
	 * Create the dialog.
	 */
	public JMeAddPageDialog() {
		setModal(true);
		setTitle(" \u6DFB\u52A0");
		setSize(245, 170);
		UtilGUI.setScreenCenter(this);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		addPagePanel = new JMeAddPagePanel();
		
		addPagePanel.getCancelBtn().addActionListener(this);
		addPagePanel.getResetBtn().addActionListener(this);
		addPagePanel.getSubmitBtn().addActionListener(this);
		
		contentPanel.add(addPagePanel);
		
		setVisible(true);
	}

	/**
	 * 
	 * @return
	 */
	public String getPageName() {
		return addPagePanel.getNameTxf().getText();
	}

	public String getPageComment() {
		return addPagePanel.getCommentTxf().getText();
	}

	public VarType getPageType() {
		return (VarType) addPagePanel.getTypeCmb().getSelectedItem();
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		JMeAddPageDialog dialog = new JMeAddPageDialog();
		dialog.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addPagePanel.getCancelBtn()) {
			cancelBtn_actionPerformed(e);
		}
		if (e.getSource() == addPagePanel.getResetBtn()) {
			resetBtn_actionPerformed(e);
		}
		if (e.getSource() == addPagePanel.getSubmitBtn()) {
			submitBtn_actionPerformed(e);
		}
	}

	/**
	 * 确认按钮
	 * @param e
	 */
	protected void submitBtn_actionPerformed(ActionEvent e) {
		if(!validateName() && !Util.strIsNullOrEmpty(addPagePanel.getCommentTxf().getText())) {
			this.setVisible(false);
		} else {
			
		}
	}

	/**
	 * 重置按钮
	 * @param e
	 */
	protected void resetBtn_actionPerformed(ActionEvent e) {
		addPagePanel.getTypeCmb().setSelectedIndex(0);
		addPagePanel.getNameTxf().setText("");
		addPagePanel.getCommentTxf().setText("");
	}

	/**
	 * 取消按钮
	 * @param e
	 */
	protected void cancelBtn_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}

	/**
	 * 验证Name值是否合法
	 * @return
	 */
	private boolean validateName() {
		String name = addPagePanel.getNameTxf().getText();
		return Util.strIsNullOrEmpty(name) || !name.matches("^[a-zA-Z]\\w*[a-zA-Z0-9]*$");
	}
}
