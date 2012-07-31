package cn.autosense.browser.gui.componment;

import static cn.autosense.browser.util.Const.ELEMENT_TYPE;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import lombok.Getter;
import cn.autosense.browser.data.ComponentBean;
import cn.autosense.browser.data.RuntimeDataBean;
import cn.autosense.browser.data.SelectedFieldBean;
import cn.autosense.browser.gui.event.JMeFieldHtmlAdapter;

import com.breeze.core.util.UtilGUI;

import craky.componentc.JCButton;
import craky.componentc.JCCheckBox;
import craky.componentc.JCComboBox;
import craky.componentc.JCScrollPane;
import craky.componentc.JCTable;
import craky.componentc.JCTextField;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2011-7-27 下午5:11:57<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
public class JMeFieldCollectionPanel extends JPanel {

	/**
     * 
     */
	private static final long	serialVersionUID	= 1L;

	@Getter
	private JCTable				selectedElementTable;
	@Getter
	private SelectedFieldBean	selectedBean;
	@Getter
	private JCComboBox			typeCmb;
	@Getter
	private JCButton			updateBtn;
	@Getter
	private JCButton			deleteBtn;
	@Getter
	private JCButton			clearBtn;
	@Getter
	private ComponentBean		bean;
	@Getter
	private JCTextField			pagePathTxf;
	@Getter
	private int					selectedRowCount;
	private JCScrollPane		pagePathScrollPane;
	private JPanel				selectedFiledOperatePanel;
	private JPanel				panel;
	private JCCheckBox			isLockChk;
	
	private JMeFieldHtmlAdapter fieldHtmlAdapter;

	/**
	 * Create the panel.
	 */
	public JMeFieldCollectionPanel() {
		this.bean = ComponentBean.getInstance();
		initComponents();
		this.bean.setFieldHtmlPanel(this);
	}

	private void initComponents() {
		setLayout(new BorderLayout(0, 0));

		selectedFiledOperatePanel = new JPanel();
		selectedFiledOperatePanel.setName("fieldOperatePanel");
		selectedFiledOperatePanel.setPreferredSize(new Dimension(280, 125));
		selectedFiledOperatePanel.setLayout(null);
		this.add(selectedFiledOperatePanel, BorderLayout.CENTER);
		
		JCScrollPane scrollPane = new JCScrollPane();
		scrollPane.setBounds(0, 0, 735, 202);
		selectedFiledOperatePanel.add(scrollPane);
		scrollPane.setPreferredSize(new Dimension(288, 80));
		
		selectedElementTable = new JCTable();
		selectedElementTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		selectedElementTable.setShowRowLines(true);
		selectedElementTable.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Name", "Type", "XPath", "CSSPath", "uuid" }) {

			private static final long	serialVersionUID	= 1L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
										String.class,
										String.class,
										String.class,
										String.class,
										String.class };

			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables	= new boolean[] { false, true, false,
												false, false };

			@Override
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		// 选择
		selectedElementTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() >= 2) {
					int selectedRow = selectedElementTable.getSelectedRow();
					if (selectedRowCount >= 0) {
						String uuid = (String) selectedElementTable.getValueAt(
								selectedRow,
								selectedElementTable.getColumnCount() - 1);
						SelectedFieldBean bean = RuntimeDataBean.getInstance().getSelectedData().get(uuid);
						UtilGUI.show(new JMeFieldInfoDetailPanel(bean), 680,
								422, "详细信息", JFrame.DISPOSE_ON_CLOSE, true);
					}
				}
			}
		});
								
//		selectedElementTable.getColumnModel().getColumn(0).setPreferredWidth(30);
//		selectedElementTable.getColumnModel().getColumn(1).setPreferredWidth(80);
//		selectedElementTable.getColumnModel().getColumn(2).setPreferredWidth(80);
//		selectedElementTable.getColumnModel().getColumn(3).setPreferredWidth(80);
//		selectedElementTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		UtilGUI.addComboBoxToTable(selectedElementTable, 1, ELEMENT_TYPE);
		// 隐藏列
		UtilGUI.hideTableColumn(selectedElementTable, selectedElementTable.getColumnCount() - 1);
		scrollPane.setViewportView(selectedElementTable);

		isLockChk = new JCCheckBox(" IsLock");
		isLockChk.setSelected(true);
		isLockChk.setBounds(553, 84, 68, 23);
		//selectedFiledOperatePanel.add(isLockChk);
		
		JLabel typeLbl = new JLabel("Type: ");
		typeLbl.setBounds(543, 56, 54, 15);
		//selectedFiledOperatePanel.add(typeLbl);

		typeCmb = new JCComboBox();
		typeCmb.setModel(new DefaultComboBoxModel(ELEMENT_TYPE));
		typeCmb.setBounds(578, 53, 129, 21);
		//selectedFiledOperatePanel.add(typeCmb);

		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(543, 94, 280, 72);
		panel.setLayout(null);
		//selectedFiledOperatePanel.add(panel);

		pagePathScrollPane = new JCScrollPane();
		pagePathScrollPane.setBounds(10, 10, 260, 52);
		pagePathScrollPane.getHeaderLabel().setText("Page Path");
		panel.add(pagePathScrollPane);

		pagePathTxf = new JCTextField();
		pagePathTxf.setEditable(false);
		//pagePathScrollPane.setViewportView(pagePathTxf);
		
		fieldHtmlAdapter = new JMeFieldHtmlAdapter(this);
		
		updateBtn = new JCButton("Update");
		updateBtn.setBounds(755, 84, 68, 23);
		updateBtn.addActionListener(fieldHtmlAdapter);
		//selectedFiledOperatePanel.add(updateBtn);

		deleteBtn = new JCButton("Delete");
		deleteBtn.setBounds(755, 10, 68, 23);
		deleteBtn.addActionListener(fieldHtmlAdapter);
		selectedFiledOperatePanel.add(deleteBtn);

		clearBtn = new JCButton("Clear");
		clearBtn.addActionListener(fieldHtmlAdapter);
		clearBtn.setBounds(755, 42, 68, 22);
		clearBtn.setName("clearBtn");
		selectedFiledOperatePanel.add(this.clearBtn);
	}

	/*-----------------------------get values---------------------------------*/
	/**
	 * relative path's text
	 * 
	 * @return
	 */
	public String getRelativePath() {
		return pagePathTxf.getText();
	}

	/*----------------------------main------------------------------------*/
	public static void main(String[] args) {
		UtilGUI.show(new JMeFieldCollectionPanel(), 315, 600);
	}
}
