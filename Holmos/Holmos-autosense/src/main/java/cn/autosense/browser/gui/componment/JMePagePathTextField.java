package cn.autosense.browser.gui.componment;

import lombok.Getter;
import lombok.Setter;
import craky.componentc.JCScrollPane;
import craky.componentc.JCTextField;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-5-30 上午10:00:00<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
public class JMePagePathTextField extends JCScrollPane {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@Setter
	@Getter
	private JCTextField textField;

	public JMePagePathTextField() {
		this("");
	}

	public JMePagePathTextField(String selectedNode) {
		getHeaderLabel().setText("Selected Node");
		
		textField = new JCTextField();
		textField.setEditable(false);
		textField.setColumns(10);
		setViewportView(textField);
	}

}
