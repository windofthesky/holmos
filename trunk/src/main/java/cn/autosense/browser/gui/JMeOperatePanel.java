package cn.autosense.browser.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import cn.autosense.browser.gui.componment.JMeSplitButton;
import cn.autosense.browser.util.OperateComponentPosition;


/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-5-29 下午3:45:40<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
public class JMeOperatePanel extends JPanel {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	private JMeTabbedPane 			tabbedPane;
	private JMeSplitButton			splitBtn;

	public JMeOperatePanel() {
		setLayout(new BorderLayout(0, 0));

		tabbedPane = new JMeTabbedPane();
		add(tabbedPane, BorderLayout.CENTER);

		splitBtn = new JMeSplitButton(tabbedPane, OperateComponentPosition.BOTTOM);
		add(splitBtn, BorderLayout.NORTH);
		this.setPreferredSize(new Dimension(900, 200));
	}

}
