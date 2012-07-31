package back;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.breeze.core.util.Util;

import cn.autosense.browser.exchange.util.CatalogDataType;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-6-8 ����2:44:02<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
public class JMeTreeModel extends DefaultTreeModel {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	public JMeTreeModel(TreeNode root) {
		super(root);
	}
	public JMeTreeModel(String data, CatalogDataType type) {
		this(null);
	}
	
    /**
     * ��Htmlת��ΪJTree��model
     * 
     * @param data
     * @return
     */
    public static TreeModel xmlToTreeModel(String data) {
        if (Util.strIsNullOrEmpty(data)) {
            return new DefaultTreeModel(new DefaultMutableTreeNode(""));
        }
        return xmlToTreeModel(Jsoup.parse(data));
    }

    /**
     * ��ѡ���html��Documentת��ΪJTree��model
     * 
     * @param dataDoc
     * @return
     */
    public static TreeModel xmlToTreeModel(Document dataDoc) {
        if (Util.isNull(dataDoc)) {
            return new DefaultTreeModel(new DefaultMutableTreeNode(""));
        }
        Element root = dataDoc.child(0).child(1).child(0);
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(root.tagName());
        class Inner {
            public void createSelectedTree(Element root,
                                           DefaultMutableTreeNode rootNode) {
                for (Element e : root.children()) {
                    DefaultMutableTreeNode node = new DefaultMutableTreeNode(e.tagName());
                    rootNode.add(node);
                    if (e.children().size() > 0) {
                        createSelectedTree(e, node);
                    }
                }
            }
        }
        new Inner().createSelectedTree(root, rootNode);
        DefaultTreeModel model = new DefaultTreeModel(rootNode);
        return model;
    }
}
