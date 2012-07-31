package back;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import craky.componentc.JCButton;
import craky.componentc.JCComboBox;
import craky.componentc.JCLabel;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-5-17 ����8:52:19<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
public class JMeAllSelectedFieldPanel extends JPanel {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	private JPanel panel;
	private JCLabel typeLbl;
	private JCComboBox comboBox;
	private JCButton updateBtn;
	private JCButton deleteBtn;
	private JCButton clearBtn;
	private JScrollPane scrollPane;
	private JTable selectedElementTable;

	/**
	 * Create the panel.
	 */
	public JMeAllSelectedFieldPanel() {
		
		panel = new JPanel();
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(280, 125));
		panel.setName("fieldOperatePanel");
		panel.setBorder(new TitledBorder(null, "Field Operate", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel);
		
		typeLbl = new JCLabel("Type: ");
		typeLbl.setBounds(165, 22, 54, 15);
		panel.add(typeLbl);
		
		comboBox = new JCComboBox();
		comboBox.setBounds(200, 19, 78, 21);
		panel.add(comboBox);
		
		updateBtn = new JCButton("Update");
		updateBtn.setBounds(5, 95, 68, 23);
		panel.add(updateBtn);
		
		deleteBtn = new JCButton("Delete");
		deleteBtn.setBounds(75, 95, 68, 23);
		panel.add(deleteBtn);
		
		clearBtn = new JCButton("Clear");
		clearBtn.setName("clearBtn");
		clearBtn.setBounds(145, 95, 68, 22);
		panel.add(clearBtn);
		
		selectedElementTable = new JTable();
		selectedElementTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		selectedElementTable.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Target", "Type", "Key", "Dynamics", "text", "Path" }) {

		    private static final long serialVersionUID = 1L;
		    @SuppressWarnings("rawtypes")
		    Class[]                   columnTypes      = new Class[] { String.class, String.class, Boolean.class, String.class, String.class, String.class };

		    @SuppressWarnings({ "rawtypes", "unchecked" })
		    @Override
		    public Class getColumnClass(int columnIndex) {
		        return columnTypes[columnIndex];
		    }

		    boolean[] columnEditables = new boolean[] { false, false, false, false, false, false };

		    @Override
		    public boolean isCellEditable(int row,
		                                  int column) {
		        return columnEditables[column];
		    }
		});
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 288, 80);
		panel.add(scrollPane);
		scrollPane.setPreferredSize(new Dimension(288, 80));
		scrollPane.setViewportView(selectedElementTable);
	}

}
