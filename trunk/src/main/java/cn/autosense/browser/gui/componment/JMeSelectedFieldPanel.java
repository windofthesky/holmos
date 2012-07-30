package cn.autosense.browser.gui.componment;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultTreeModel;

import lombok.Getter;
import cn.autosense.browser.data.ComponentBean;
import cn.autosense.browser.util.Const;

import com.breeze.core.util.UtilGUI;

import craky.componentc.JCButton;
import craky.componentc.JCScrollPane;
import craky.componentc.JCTable;
import craky.componentc.JCTextArea;
import craky.componentc.JCTree;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2011-7-27 下午2:46:30<br>
 * @since 1.0<br>
 * @version 1.0<br>
 * 			Remark:<br>
 * 
 */
public class JMeSelectedFieldPanel extends JPanel implements Const {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Getter
    private JCTree             htmlTree;
    @Getter
    private JCTextArea         htmlCodeTxa;
    @Getter
    private JCTextArea         xpathTxa;
    @Getter
    private JCTextArea		   cssPathTxa;
    @Getter
    private JCTable            attrsTable;
    @Getter
    private JCButton           testXpathBtn;
    @Getter
    private JCButton           cancelXpathBtn;

    private ComponentBean     bean;
    private JPanel            pathPanel;

    /**
     * Create the panel.
     */
    public JMeSelectedFieldPanel() {
        this.bean = ComponentBean.getInstance();
        setLayout(new GridLayout(1, 0, 0, 0));

        pathPanel = new JPanel();
        pathPanel.setPreferredSize(new Dimension(300, 50));
        pathPanel.setLayout(new GridLayout(0, 1, 0, 0));

    	JCScrollPane cssPathScrollPane = new JCScrollPane();
    	cssPathScrollPane.getHeaderLabel().setText("CSS Path");
    	pathPanel.add(cssPathScrollPane);

		cssPathTxa = new JCTextArea();
		cssPathTxa.setLineWrap(true);
		cssPathScrollPane.setViewportView(cssPathTxa);

        JCScrollPane xpathScrollPane = new JCScrollPane();
        xpathScrollPane.getHeaderLabel().setText("xpath");
        xpathScrollPane.setPreferredSize(new Dimension(300, 50));
        pathPanel.add(xpathScrollPane);

        xpathTxa = new JCTextArea();
        xpathTxa.setLineWrap(true);
        xpathScrollPane.setViewportView(xpathTxa);

        add(this.pathPanel);

    	JCScrollPane scrollPane = new JCScrollPane();
    	add(scrollPane);
    	
        attrsTable = new JCTable();
        attrsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        attrsTable.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Name", "Value" }) {
            private static final long serialVersionUID = 1L;

            @SuppressWarnings("rawtypes")
            Class[] columnTypes = new Class[] { String.class, String.class };

            @SuppressWarnings({ "rawtypes", "unchecked" })
            @Override
            public Class getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }

            boolean[] columnEditables = new boolean[] { false, true };

            @Override
            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        attrsTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        scrollPane.setViewportView(attrsTable);

    	JCScrollPane htmlCodeScrollPane = new JCScrollPane();
    	add(htmlCodeScrollPane);
    	htmlCodeScrollPane.getHeaderLabel().setText("Html Code");

        htmlCodeTxa = new JCTextArea();
        htmlCodeTxa.setLineWrap(true);
        htmlCodeTxa.setEditable(false);
        htmlCodeScrollPane.setViewportView(htmlCodeTxa);
        // panel.add(htmlTree, BorderLayout.CENTER);

        htmlTree = new JCTree();
        htmlTree.setModel(new DefaultTreeModel(null));

        JCScrollPane htmlTreeScrollPane = new JCScrollPane(htmlTree);
        add(htmlTreeScrollPane);
        htmlTreeScrollPane.getHeaderLabel().setText("Seleted Html Tree");
        this.bean.setSelectHtmlPanel(this);
    }

    public static void main(String[] args) {
		UtilGUI.show(new JMeSelectedFieldPanel(), 300, 600);
	}
}