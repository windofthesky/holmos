package cn.autosense.browser.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import lombok.Getter;

import cn.autosense.browser.data.ComponentBean;
import cn.autosense.browser.gui.componment.JMeSplitButton;
import cn.autosense.browser.util.OperateComponentPosition;


/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-5-29 下午3:30:50<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
public class JMePagePanel extends JPanel {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@Getter
	private JMePageTreePanel		pageTreePanel;
	@Getter
	private JMeSplitButton			splitBtn;

	public JMePagePanel() {
		setLayout(new BorderLayout(0, 0));
		
		// TODO
		pageTreePanel = new JMePageTreePanel();
		add(pageTreePanel, BorderLayout.CENTER);

		splitBtn = new JMeSplitButton(pageTreePanel, OperateComponentPosition.LEFT);
		add(splitBtn, BorderLayout.EAST);
		
		ComponentBean.getInstance().setPagePanel(this);
	}


}
