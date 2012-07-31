package cn.autosense.browser.gui.componment;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Icon;
import javax.swing.JComponent;

import cn.autosense.browser.util.Const;
import cn.autosense.browser.util.OperateComponentPosition;
import craky.componentc.JCCheckBox;
import craky.util.SwingResourceManager;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-5-29 下午3:48:17<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
public class JMeSplitButton extends JCCheckBox implements ItemListener {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	private static final String		SHOW_LIST	= "\u663E\u793A\u6708\u4EFD";
	private static final String		HIDE_LIST	= "\u9690\u85CF\u6708\u4EFD";
	//private static final String		SHOW_STAT	= "\u663E\u793A\u7EDF\u8BA1";
	//private static final String		HIDE_STAT	= "\u9690\u85CF\u7EDF\u8BA1";
	
	private boolean					showComponent;
	//private boolean					showStat;

	protected Icon					splitLeft, splitRight, emptyIcon;
	
	private JComponent component;

	public JMeSplitButton(JComponent component, OperateComponentPosition position) {
		this.component = component;
		setIcon(position);
		setPreferredSize(new Dimension(6, -1));
		setFocusable(false);
		setToolTipText(SHOW_LIST);
		addItemListener(this);
		setIcon(emptyIcon);
		setSelectedIcon(emptyIcon);
		setRolloverIcon(splitRight);
		setRolloverSelectedIcon(splitLeft);
		setPressedIcon(splitRight);
		setPressedSelectedIcon(splitLeft);

		setVisible(true);
		setSelected(true);
	}
	
	private void setIcon(OperateComponentPosition position) {
		switch(position) {
			case TOP : {
				this.splitLeft = getIcon("split_down.png");
				this.splitRight = getIcon("split_up.png");
				this.emptyIcon = getIcon("split_empty_ud.png");
			}
			case BOTTOM : {
				this.splitLeft = getIcon("split_up.png");
				this.splitRight = getIcon("split_down.png");
				this.emptyIcon = getIcon("split_empty_ud.png");
			}
			case LEFT : {
				this.splitLeft = getIcon("split_left.png");
				this.splitRight = getIcon("split_right.png");
				this.emptyIcon = getIcon("split_empty_lr.png");
			}
			case RIGHT : {
				this.splitLeft = getIcon("split_right.png");
				this.splitRight = getIcon("split_left.png");
				this.emptyIcon = getIcon("split_empty_lr.png");
			}
			default : {
				this.splitLeft = getIcon("split_left.png");
				this.splitRight = getIcon("split_right.png");
				this.emptyIcon = getIcon("split_empty_lr.png");
			}
		}
	}
	
	
	public static Icon getIcon(String name) {
		return SwingResourceManager.getIcon(Const.IMAGE_DIR + name);
	}
	
	public void itemStateChanged(ItemEvent e) {
		boolean selected = e.getStateChange() == ItemEvent.SELECTED;
		setShowComponent(selected);
		setToolTipText(selected ? HIDE_LIST : SHOW_LIST);
	}

	public boolean isShowComponent() {
		return showComponent;
	}

	public void setShowComponent(boolean isShowComponent) {
		this.showComponent = isShowComponent;
		if (null != component) {
			component.setVisible(isShowComponent);
			this.revalidate();
		}
	}

}
