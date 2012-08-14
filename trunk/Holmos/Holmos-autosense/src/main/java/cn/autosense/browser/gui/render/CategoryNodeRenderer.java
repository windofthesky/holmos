package cn.autosense.browser.gui.render;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

public class CategoryNodeRenderer extends DefaultTreeCellRenderer {

	private static final long		serialVersionUID	= 8532405600839140757L;

	private static final ImageIcon	categoryLeafIcon	= new ImageIcon(
																CategoryNodeRenderer.class
																		.getResource("/images/001.gif"));
	private static final ImageIcon	openFolderIcon		= new ImageIcon(
																CategoryNodeRenderer.class
																		.getResource("/images/005.gif"));
	private static final ImageIcon	closedFolderIcon	= new ImageIcon(
																CategoryNodeRenderer.class
																		.getResource("/images/001.gif"));

	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {

		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
				row, hasFocus);

		if (leaf) {
			setIcon(categoryLeafIcon);
		} else if (expanded) {
			setIcon(openFolderIcon);
		} else {
			setIcon(closedFolderIcon);
		}

		return this;
	}
	
	
}