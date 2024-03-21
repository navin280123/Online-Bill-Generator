package ProjectGUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTabbedPane;
import javax.swing.SpringLayout;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JList;
import javax.swing.JSpinner;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class mainPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainPage frame = new mainPage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public mainPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Setting");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Logout");
		mnNewMenu.add(mntmNewMenuItem);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane);
		
//		********************************************************************Product Panel******************************************************************************************
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Product Panel", null, panel, null);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		// Create data for the table
        Vector<Vector<Object>> data = new Vector<>();
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Bar Code");
        columnNames.add("Category");
        columnNames.add("HSN No");
        columnNames.add("Selling Price");
        columnNames.add("Marked Price");
        columnNames.add("Name");
        columnNames.add("Purchased Price");
        columnNames.add("Tax");
        columnNames.add("Sub Category");

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
        JButton  btn = new JButton("Add Product");
        btn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		AddProduct add = new AddProduct();
        	}
        });
        panel.add(btn,BorderLayout.SOUTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        addSampleData(model);
//		********************************************************************Bill History  Panel******************************************************************************************
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_1, null);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_2, null);
		
		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_3, null);
		setVisible(true);
	}
	private static void addSampleData(DefaultTableModel model) {
		
        model.addRow(new Object[]{"Product A", 10.99, "2024-12-31", 100, "123456789"});
        model.addRow(new Object[]{"Product B", 15.49, "2024-10-15", 50, "987654321"});
        model.addRow(new Object[]{"Product C", 5.99, "2025-03-20", 200, "456789123"});
    }
}
