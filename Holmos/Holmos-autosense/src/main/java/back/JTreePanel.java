package back;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-5-24 ����9:02:54<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
@SuppressWarnings("serial")
public class JTreePanel extends JPanel {
	private JTree tree;

	/**
	 * Create the panel.
	 */
	public JTreePanel() {
		setLayout(null);
		
		tree = new JTree();
		tree.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("JTree") {
				{
				}
			}
		));
		tree.setBounds(22, 5, 242, 251);
		add(tree);

	}

}
