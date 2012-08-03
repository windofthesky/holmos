package cn.autosense.browser.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;

import org.jsoup.nodes.Element;
import org.nutz.lang.Files;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cn.autosense.browser.gui.render.VarNode;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2011-9-6 上午10:56:08<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
public class CommonUtil {
	
	/**
	 * 读取Properties文件
	 * @param propFilePath
	 * @return
	 */
	public static Properties loadProperties(String propFile) {
		try {
			Properties prop = new Properties();
			InputStream is = new BufferedInputStream(new FileInputStream(propFile));
			prop.load(is);
			return prop;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 把字符串首字母大写
	 * @param value
	 * @return
	 */
	public static String firstUpper(String value) {
		String str = value.toLowerCase();
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

    /**
     * 把属性字符串解析放到JTable中
     * 
     * @param table
     * @param attrMap
     */
    public static void setAttrsForTable(JTable table, Map<String, String> attrMap) {
    	if(null != attrMap) {
            DefaultTableModel model = (DefaultTableModel)table.getModel();
            clearTable(model);
            for (Entry<String, String> entry : attrMap.entrySet()) {
            	model.addRow(new Object[] {entry.getKey(), entry.getValue()});
            }
            table.updateUI();
    	}
    }

    /**
     * 得到选择JTree节点到根节点的路径
     * @param tree
     * @return
     */
    public static String getSelectTreePath(JTree tree) {
		String selectTreePath = tree.getSelectionPath().toString().trim();
		return selectTreePath.substring(selectTreePath.indexOf(")") + 1, selectTreePath.length()-1).replaceAll(",", "/").replace(" ", "");
    }

    /**
     * 把JTable中的数据清空
     * 
     * @param model
     */
    public static void clearTable(DefaultTableModel model) {
        int count = model.getRowCount();
        while (count > 0) {
            model.removeRow(--count);
        }
    }

    /**
     * 根据传进的Node，得到所有的父节点List，返回值按照节点层级关系决定顺序，第一个是根节点
     * 
     * @param doc
     * @param node
     * @return
     */
    public static List<Node> getParentNodeList(Node node){
        List<Node> nodeList = new ArrayList<Node>();
        nodeList.add(node);
        Node parentNode = node.getParentNode(); // a
        while(parentNode != null) {
            nodeList.add(parentNode);
            parentNode = parentNode.getParentNode();
        }
        List<Node> nodeListTemp = new ArrayList<Node>();
        for (int i = nodeList.size()-1; i >= 0; i--) {
            nodeListTemp.add(nodeList.get(i));
        }
        return nodeListTemp;
    }

    public static void getLeafNodes(Element root,
                                    List<Element> leafNodes) {
        for (Element e : root.children()) {
            if (e.children().size() > 0) {
                if (e.hasText()) {
                    leafNodes.add(e);
                }
                getLeafNodes(e, leafNodes);
            } else {
                leafNodes.add(e);
            }
        }
    }
    
    public static List<VarNode> getChildNode(VarNode node) {
    	return null;
    }
    

    /**
     * 读取目录下面一级的所有文件或者文件夹名称
     * @param filepath
     * @return
     */
	public static List<String> readFileName(String filepath) {
		List<String> list = new ArrayList<String>();
		File file = new File(filepath);
		if (file.isDirectory()) {
			String[] filelist = file.list();
			for (String string : filelist) {
				File readfile = new File(filepath + "/" + string);
				list.add(readfile.getName());
			}
		}
		return list;
	}

    /**
     * 
     * 
     * @param selectedNode
     * @param leafNodes
     */
    public static void getLeafNodes(Node selectedNode, List<Node> leafNodes) {
        short type = selectedNode.getNodeType();
        //Assume the text to find (sampleText) is only in a Text node or a CDATA node.
        if (isTextNodeType(type)) {
            String nValue = selectedNode.getNodeValue();
            if (!nValue.trim().isEmpty()){
                System.out.println(selectedNode.getParentNode().getNodeName()+"/"+selectedNode.getNodeName() + ": "+nValue);
                leafNodes.add(selectedNode.getParentNode());
            }
        }

        //for non-text nodes, recursively explore attributes
        if (selectedNode.getNodeType() == Node.ELEMENT_NODE) {
            NodeList children = selectedNode.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                Node child = children.item(i);
                //Drill down.
                getLeafNodes(child, leafNodes);
            }
        }
        return; //do nothing for other types of nodes
    }

    public static boolean isTextNodeType(short nodeType) {
        if (Node.CDATA_SECTION_NODE == nodeType || Node.TEXT_NODE == nodeType) {
            return true;
        }
        return false;
    }

    public enum AddPosition {
        BEFORE, AFTER
    }

    public static String saveToFile(String fileName,
                                    String text) {
        String path = Const.USER_DIR + "\\bin\\" + fileName;
        Files.write(path, text);
        return path;
    }

    public static void saveToFile(String fileName,
                                  String text,
                                  String path) {
        Files.write(path + "/" + fileName, text);
    }

}
