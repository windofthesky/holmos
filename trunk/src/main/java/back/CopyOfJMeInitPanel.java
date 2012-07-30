package back;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import lombok.Getter;
import lombok.Setter;
import cn.autosense.browser.data.InitDataBean;
import cn.autosense.browser.gui.JMeMainFrame;

import com.breeze.core.util.UtilGUI;

import craky.componentc.JCButton;
import craky.componentc.JCLabel;
import craky.componentc.JCTextField;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-6-1 ����1:30:25<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
public class CopyOfJMeInitPanel extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	private JPanel panel;
	@Setter
	@Getter
	private JCButton fileChooserBtn;
	@Setter
	@Getter
	private JCLabel rootpathLbl;
	@Setter
	@Getter
	private JCTextField rootPathTxf;
	private JSeparator separator;
	@Setter
	@Getter
	private JCButton submitBtn;
	@Setter
	@Getter
	private JCButton cancelBtn;

	public CopyOfJMeInitPanel() {
		setLayout(null);

		panel = new JPanel();
		panel.setBounds(10, 10, 365, 280);
		add(panel);
		panel.setLayout(null);
		
		fileChooserBtn = new JCButton("...");
		fileChooserBtn.addActionListener(this);
		fileChooserBtn.setBounds(302, 11, 29, 20);
		panel.add(fileChooserBtn);
		
		rootPathTxf = new JCTextField();
		rootPathTxf.setEditable(false);
		rootPathTxf.setBounds(82, 11, 210, 21);
		panel.add(rootPathTxf);
		rootPathTxf.setColumns(10);
		
		rootpathLbl = new JCLabel("RootPath :");
		rootpathLbl.setBounds(10, 14, 62, 15);
		panel.add(rootpathLbl);
		
		separator = new JSeparator();
		separator.setBounds(10, 231, 342, 7);
		panel.add(separator);
		
		submitBtn = new JCButton("\u786E\u5B9A");// ȷ��
		submitBtn.addActionListener(this);
		submitBtn.setBounds(36, 247, 93, 23);
		panel.add(submitBtn);
		
		cancelBtn = new JCButton("\u53D6\u6D88");// ȡ��
		cancelBtn.addActionListener(this);
		cancelBtn.setBounds(153, 247, 93, 23);
		panel.add(cancelBtn);
	}

	/**
	 * ����ť����¼�
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == fileChooserBtn) {// ��Ŀ¼ѡ��
			fileChooserBtn_click();
		} else if(e.getSource() == submitBtn) {// ȷ��
			submitBtn_click();
		} else if(e.getSource() == cancelBtn) {// ȡ��
			cancelBtn_click();
		}
	}
	
	protected void fileChooserBtn_click() {
		JFileChooser c = new JFileChooser();
		c.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		c.setDialogTitle("ѡ��·��...");
		int result = c.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			String path = c.getSelectedFile().getAbsolutePath();
			rootPathTxf.setText(path);
			InitDataBean.getInstance().setRootPath(path);
			// TODO д��Properties�ļ�δʵ��
		}
	}
	
	protected void submitBtn_click() {
		this.setVisible(false);
		new JMeMainFrame();
	}
	
	protected void cancelBtn_click() {
		JOptionPane.showMessageDialog(null, "cancel");
	}

	public static void main(String[] args) {
		UtilGUI.show(new CopyOfJMeInitPanel(), 400, 330);
	}
}
