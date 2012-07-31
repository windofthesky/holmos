package cn.autosense.browser.gui.componment;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.TitledBorder;

import lombok.Getter;
import cn.autosense.browser.util.PageType;

import com.breeze.core.util.Util;

import craky.componentc.JCButton;
import craky.componentc.JCComboBox;
import craky.componentc.JCDialog;
import craky.componentc.JCLabel;
import craky.componentc.JCTextField;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-7-9 下午8:04:37<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
public class JMeAddPagePanel extends JPanel implements FocusListener {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	private JCLabel typeLbl;
	@Getter
	private JCComboBox typeCmb;
	private JCLabel nameLbl;
	@Getter
	private JCTextField nameTxf;
	private JCLabel commentLbl;
	@Getter
	private JCTextField commentTxf;
	private JSeparator separator;
	@Getter
	private JCButton cancelBtn;
	@Getter
	private JCButton resetBtn;
	@Getter
	private JCButton submitBtn;
	private JLabel nameWarnLbl;

	/**
	 * Create the panel.
	 */
	public JMeAddPagePanel() {
		setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(null);
		
		typeLbl = new JCLabel("Type: ");
		typeLbl.setBounds(10, 13, 54, 15);
		add(typeLbl);
		
		typeCmb = new JCComboBox();
		typeCmb.setModel(new DefaultComboBoxModel(PageType.values()));
		typeCmb.setBounds(76, 10, 145, 21);
		add(typeCmb);
		
		nameLbl = new JCLabel("Name: ");
		nameLbl.setBounds(10, 40, 54, 15);
		add(nameLbl);
		
		nameTxf = new JCTextField();
		nameTxf.addFocusListener(this);
		nameTxf.setBounds(76, 36, 145, 21);
		add(nameTxf);
		nameTxf.setColumns(30);
		
		nameWarnLbl = new JLabel("");
		nameWarnLbl.setForeground(Color.RED);
		nameWarnLbl.setBounds(226, 41, 19, 15);
		add(nameWarnLbl);
		
		commentLbl = new JCLabel("Commont: ");
		commentLbl.setBounds(10, 66, 72, 15);
		add(commentLbl);
		
		commentTxf = new JCTextField();
		commentTxf.setColumns(30);
		commentTxf.setBounds(76, 63, 145, 21);
		add(commentTxf);
		
		separator = new JSeparator();
		separator.setBounds(10, 91, 211, 6);
		add(separator);
		
		cancelBtn = new JCButton("\u53D6 \u6D88");
		cancelBtn.setBounds(10, 103, 62, 23);
		add(cancelBtn);
		
		submitBtn = new JCButton("\u786E \u5B9A");
		
		resetBtn = new JCButton("\u68C0\u6D4B");
		resetBtn.setText("\u91CD \u7F6E");
		resetBtn.setBounds(86, 102, 62, 23);
		add(resetBtn);
		submitBtn.setBounds(159, 102, 62, 23);
		add(submitBtn);
	}
	
	public static void main(String[] args) {
		JCDialog dialog = new JCDialog();
		dialog.setTitle("添加");
		dialog.setDefaultCloseOperation(JCDialog.DISPOSE_ON_CLOSE);
		dialog.getContentPane().add(new JMeAddPagePanel());
		dialog.setVisible(true);
		dialog.setSize(238, 166);
		dialog.setResizable(false);
	}
	public void focusGained(FocusEvent e) {
	}
	public void focusLost(FocusEvent e) {
		if (e.getSource() == nameTxf) {
			nameTxf_focusLost(e);
		}
	}

	/**
	 * 
	 * @param e
	 */
	protected void nameTxf_focusLost(FocusEvent e) {
		if(validateName()) {
			nameWarnLbl.setText("*");
		} else {
			nameWarnLbl.setText("");
		}
	}

	/**
	 * 验证输入name值是否合法
	 * @return
	 */
	private boolean validateName() {
		String name = nameTxf.getText();
		return Util.strIsNullOrEmpty(name) || !name.matches("^[a-zA-Z]\\w*[a-zA-Z0-9]*$");
	}
	
}
