package cn.autosense.browser.gui.componment;

import static cn.autosense.browser.util.Const.ELEMENT_TYPE;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import cn.autosense.browser.data.SelectedFieldBean;
import cn.autosense.browser.util.CommonUtil;
import cn.autosense.plug.data.LocatorType;

import com.breeze.core.util.Util;
import com.breeze.core.util.UtilGUI;

import craky.componentc.JCButton;
import craky.componentc.JCCheckBox;
import craky.componentc.JCComboBox;
import craky.componentc.JCLabel;
import craky.componentc.JCScrollPane;
import craky.componentc.JCSeparator;
import craky.componentc.JCTable;
import craky.componentc.JCTextArea;
import craky.componentc.JCTextField;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-5-24 下午9:50:24<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
public class JMeFieldInfoDetailPanel extends JPanel {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	private JCLabel pagePathLbl;
	private JCTextField pagePathTxf;

	private JCLabel pageNameLbl;
	private JCTextField pageNameTxf;

	private JCLabel pageCommentLbl;
	private JCTextField conmentTxf;

	private JCTextField nameTxf;
	private JCComboBox typeCmb;
	private JCTextArea cssPathTxa;
	private JCTextArea xpathTxa;
	private JCTable	attrsTable;

	private JCCheckBox modifyCkb;
	private JCButton updateBtn;
	private JCButton saveBtn;
	private JCButton deleteBtn;
	
	// data
	private SelectedFieldBean bean;
	private JCTextField eleComment;
	private JCLabel commentLbl;
	private JCLabel tagLbl;
	private JCTextField tagTxf;
	
	public JMeFieldInfoDetailPanel() {
		this(null);
	}

	public JMeFieldInfoDetailPanel(SelectedFieldBean bean) {
		setLayout(null);
		
		JPanel pageInfoPanel = new JPanel();
		pageInfoPanel.setBorder(BorderFactory.createTitledBorder(""));
		pageInfoPanel.setBounds(10, 10, 644, 364);
		add(pageInfoPanel);
		pageInfoPanel.setLayout(null);

		pagePathLbl = new JCLabel("aaa");
		pagePathLbl.setText("pagePath:");
		pagePathLbl.setBounds(10, 13, 80, 15);
		pageInfoPanel.add(pagePathLbl);
		
		pagePathTxf = new JCTextField();
		pagePathTxf.setBounds(83, 10, 106, 21);
		pageInfoPanel.add(pagePathTxf);
		pagePathTxf.setColumns(10);
		
		pageNameLbl = new JCLabel("aaa");
		pageNameLbl.setText("PageName:");
		pageNameLbl.setBounds(199, 13, 80, 15);
		pageInfoPanel.add(pageNameLbl);
		
		pageNameTxf = new JCTextField();
		pageNameTxf.setColumns(10);
		pageNameTxf.setBounds(272, 10, 106, 21);
		pageInfoPanel.add(pageNameTxf);
		
		pageCommentLbl = new JCLabel("aaa");
		pageCommentLbl.setText("PageComment:");
		pageCommentLbl.setBounds(388, 13, 93, 15);
		pageInfoPanel.add(pageCommentLbl);
		
		conmentTxf = new JCTextField();
		conmentTxf.setColumns(10);
		conmentTxf.setBounds(481, 10, 153, 21);
		pageInfoPanel.add(conmentTxf);
		
		JCSeparator separator_1 = new JCSeparator();
		separator_1.setBounds(10, 38, 624, 2);
		pageInfoPanel.add(separator_1);
		
		JCLabel nameLbl = new JCLabel("ID:");
		nameLbl.setText("Name:");
		nameLbl.setBounds(10, 53, 80, 15);
		pageInfoPanel.add(nameLbl);
		
		nameTxf = new JCTextField();
		nameTxf.setColumns(10);
		nameTxf.setBounds(83, 49, 106, 21);
		pageInfoPanel.add(nameTxf);
		
		JCLabel typeLbl = new JCLabel("Type:");
		typeLbl.setBounds(197, 50, 39, 15);
		pageInfoPanel.add(typeLbl);
		
		typeCmb = new JCComboBox();
		typeCmb.setModel(new DefaultComboBoxModel(ELEMENT_TYPE));
		typeCmb.setBounds(272, 47, 106, 21);
		pageInfoPanel.add(typeCmb);
		
		JCScrollPane cssPathScrollPane = new JCScrollPane();
		cssPathScrollPane.setBounds(10, 109, 302, 100);
		cssPathScrollPane.getHeaderLabel().setText("CSS Path");
		pageInfoPanel.add(cssPathScrollPane);

		cssPathTxa = new JCTextArea();
		cssPathTxa.setLineWrap(true);
		cssPathScrollPane.setViewportView(cssPathTxa);

		JCScrollPane xpathScrollPane = new JCScrollPane();
		xpathScrollPane.setBounds(10, 211, 302, 100);
		xpathScrollPane.getHeaderLabel().setText("xpath");
		xpathScrollPane.setPreferredSize(new Dimension(300, 50));
		pageInfoPanel.add(xpathScrollPane);

		xpathTxa = new JCTextArea();
		xpathTxa.setLineWrap(true);
		xpathScrollPane.setViewportView(xpathTxa);

		JPanel attrPanel = new JPanel();
		attrPanel.setBounds(318, 105, 316, 206);
        attrPanel.setName("attrPanel");
        attrPanel.setBorder(BorderFactory.createTitledBorder("Attributes"));
        attrPanel.setPreferredSize(new Dimension(300, 50));
        attrPanel.setLayout(new BorderLayout(0, 0));
        
		JCScrollPane scrollPane = new JCScrollPane();
		attrPanel.add(scrollPane, BorderLayout.CENTER);

		attrsTable = new JCTable();
		attrsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		attrsTable.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Name", "Value" }) {

			private static final long	serialVersionUID	= 1L;

			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { String.class, String.class };

			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false };

			@Override
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		attrsTable.getColumnModel().getColumn(1).setPreferredWidth(185);
		scrollPane.setViewportView(attrsTable);
		pageInfoPanel.add(attrPanel);
		
		JCSeparator separator_2 = new JCSeparator();
		separator_2.setBounds(10, 321, 624, 2);
		pageInfoPanel.add(separator_2);
		
		updateBtn = new JCButton("Update");
		updateBtn.setBounds(175, 331, 93, 23);
		//pageInfoPanel.add(updateBtn);
		
		saveBtn = new JCButton("Save");
		saveBtn.setBounds(292, 331, 93, 23);
		//pageInfoPanel.add(saveBtn);
		
		deleteBtn = new JCButton("Delete");
		deleteBtn.setBounds(409, 331, 93, 23);
		//pageInfoPanel.add(deleteBtn);
		
		modifyCkb = new JCCheckBox(" IsModify");
		modifyCkb.addChangeListener(new ModifyCkbChangeListener());
		modifyCkb.setBounds(20, 331, 103, 23);
		pageInfoPanel.add(modifyCkb);
		
		eleComment = new JCTextField();
		eleComment.setEditable(false);
		eleComment.setColumns(10);
		eleComment.setBounds(83, 78, 295, 21);
		pageInfoPanel.add(eleComment);
		
		commentLbl = new JCLabel("ID:");
		commentLbl.setText("Comment:");
		commentLbl.setBounds(10, 80, 368, 15);
		pageInfoPanel.add(commentLbl);
		
		tagLbl = new JCLabel("ID:");
		tagLbl.setText("Tag:");
		tagLbl.setBounds(388, 51, 80, 15);
		pageInfoPanel.add(tagLbl);
		
		tagTxf = new JCTextField();
		tagTxf.setEditable(false);
		tagTxf.setColumns(10);
		tagTxf.setBounds(430, 47, 97, 21);
		pageInfoPanel.add(tagTxf);

		this.bean = bean;
		putDate();
		
		setAllEnabled(false);
	}

	private void putDate() {
		if(!Util.isNull(bean)) {
			nameTxf.setText(bean.getName());
			tagTxf.setText(bean.getTagName());
			eleComment.setText(bean.getComment());
			typeCmb.setSelectedItem(CommonUtil.firstUpper(bean.getType().name()));
			xpathTxa.setText(bean.getLocatorInfo().locator(LocatorType.XPATH));
			cssPathTxa.setText(bean.getLocatorInfo().locator(LocatorType.XPATH));
            CommonUtil.setAttrsForTable(attrsTable, bean.getLocatorInfo().attr());
		}
	}

	private void setAllEnabled(boolean isModify) {
		pagePathTxf.setEditable(isModify);
		pageNameTxf.setEditable(isModify);
		conmentTxf.setEditable(isModify);
		nameTxf.setEditable(isModify);
		typeCmb.setEditableAll(isModify);
		xpathTxa.setEditable(isModify);
		cssPathTxa.setEditable(isModify);
		tagTxf.setEditable(isModify);
		eleComment.setEditable(isModify);
//		if(isModify) {
//			attrsTable.setEditingColumn(1);
//		} else {
//			//attrsTable.
//			//attrsTable.setSelectionMode(ListSelectionModel.);
//		}
	}

	public static void main(String[] args) {
		UtilGUI.show(new JMeFieldInfoDetailPanel(), 680, 420);
	}

	private class ModifyCkbChangeListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			setAllEnabled(modifyCkb.isSelected());
		}
	}
}
