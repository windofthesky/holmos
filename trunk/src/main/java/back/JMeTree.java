package back;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.breeze.core.util.UtilGUI;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-6-11 ����2:47:03<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
public class JMeTree extends JTree {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * Create the panel.
	 */
	public JMeTree() {
		setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("JTree") {
				/**
				 * 
				 */
				private static final long	serialVersionUID	= 1L;

				{
					DefaultMutableTreeNode node_1;
					node_1 = new DefaultMutableTreeNode("bbbb");
						node_1.add(new DefaultMutableTreeNode("aaaaa"));
						node_1.add(new DefaultMutableTreeNode("ccccc"));
						node_1.add(new DefaultMutableTreeNode("dddd"));
						node_1.add(new DefaultMutableTreeNode("yellow"));
					add(node_1);
					node_1 = new DefaultMutableTreeNode("sports");
						node_1.add(new DefaultMutableTreeNode("basketball"));
						node_1.add(new DefaultMutableTreeNode("soccer"));
						node_1.add(new DefaultMutableTreeNode("football"));
						node_1.add(new DefaultMutableTreeNode("hockey"));
					add(node_1);
					node_1 = new DefaultMutableTreeNode("food");
						node_1.add(new DefaultMutableTreeNode("hot dogs"));
						node_1.add(new DefaultMutableTreeNode("pizza"));
						node_1.add(new DefaultMutableTreeNode("ravioli"));
						node_1.add(new DefaultMutableTreeNode("bananas"));
					add(node_1);
				}
			}
		));
		setToolTipText(getToolTipText());
	}

	/*@Override
	public String getToolTipText(MouseEvent event) {
		try {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)this.getPathForLocation(event.getX(), event.getY()).getLastPathComponent();
			return node.toString();
		} catch (Exception e) {
			return "";
		}
	}*/

	public static void main(String[] args) {
		UtilGUI.show(new JMeTree(), 300, 600);
	}
}
