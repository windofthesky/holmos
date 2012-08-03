package cn.autosense.browser.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import lombok.Getter;
import cn.autosense.browser.data.ComponentBean;
import cn.autosense.browser.gui.componment.JMePagePathTextField;
import cn.autosense.browser.gui.componment.JMePageTree;


/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-5-30 上午10:13:00<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
public class JMePageTreePanel extends JPanel {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@Getter
	private JMePagePathTextField pageSelectTextField;
	@Getter
	private JMePageTree pageTree;

	/**
	 * Create the panel.
	 */
	public JMePageTreePanel() {
		setLayout(new BorderLayout(0, 0));
		this.setPreferredSize(new Dimension(200, 800));
		
		pageSelectTextField = new JMePagePathTextField();
		add(pageSelectTextField, BorderLayout.NORTH);
		
		pageTree = new JMePageTree();
		add(pageTree, BorderLayout.CENTER);
		
		ComponentBean.getInstance().setPageTreePanel(this);
	}
	
	public String getSelectNodePath() {
		return pageSelectTextField.getTextField().getText();
	}

}
