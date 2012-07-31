package back;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import cn.autosense.browser.gui.JMeInitFrame;

import com.breeze.core.util.UtilGUI;

import craky.componentc.JCFrame;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-6-1 ����3:13:28<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
public class JMeInitFrameaaa extends JCFrame {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	private JMeInitFrame meInitPanel;

	public JMeInitFrameaaa() {
		meInitPanel = new JMeInitFrame();
		getContentPane().add(meInitPanel, BorderLayout.CENTER);
		
		UtilGUI.setScreenCenter(this);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setSize(380, 325);
	}

}
