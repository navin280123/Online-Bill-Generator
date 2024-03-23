package ProjectGUI;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class BillPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public BillPanel() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		// Create data for the table
        Vector<Vector<Object>> data = new Vector<>();
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Bar Code");
        columnNames.add("Name");
        columnNames.add("HSN No");
        columnNames.add("Category");
        columnNames.add("Sub Category");
        columnNames.add("Tax");
        columnNames.add("Purchased Price");
        columnNames.add("Marked Price");
        columnNames.add("Selling Price");
        // Create the table model
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Disable cell editing
            }
        };
        // Create the table
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Add row selection listener
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        System.out.println("Selected Product: " + table.getValueAt(selectedRow, 0));
                    }
                }
            }
        });

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        JButton  btn = new JButton("Create Bill");
        btn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		createBillPanel add = new createBillPanel();
        	}
        });
        add(btn,BorderLayout.SOUTH);
        add(scrollPane, BorderLayout.CENTER);
        addSampleData(model);

	}

	private void addSampleData(DefaultTableModel model) {
		// TODO Auto-generated method stub
		model.addRow(new Object[]{"barcode","name","hsn","category","subcategory","tax","purchasedPrice","markedPrice","sellingPrice"});
	}

}
