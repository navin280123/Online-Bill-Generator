package ProjectGUI;

import java.awt.EventQueue;
import java.util.Date; // Import Date from java.util

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.awt.Component;
import javax.swing.JSpinner;

public class createBillPanel extends JFrame {

	
	private static final long serialVersionUID = 1L;
	protected static final JLabel PQuantityfield = null;
	private JPanel contentPane;
	
	private JTable table;
	private JLabel lblBillDate;
	private JButton btnSave;
	private double totalsum=0;
	private JSpinner spinner;
	private JTextField totalAmountfield;
	private JLabel lblbilldate;
	private JTextField namefield;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					createBillPanel frame = new createBillPanel();
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
	public createBillPanel() {
		setTitle("Create Bill");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		 setResizable(false);
		 setSize(663, 500);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblBillNo = new JLabel("Bill Number");
		lblBillNo.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		lblBillNo.setBounds(10, 11, 73, 14);
		contentPane.add(lblBillNo);
		
		
	        
		JLabel lblProductName = new JLabel("Product Name");
		lblProductName.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		lblProductName.setBounds(10, 59, 96, 14);
		contentPane.add(lblProductName);
		
		JLabel lblProductQuantity = new JLabel("Product Quantity");
		lblProductQuantity.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		lblProductQuantity.setBounds(368, 59, 121, 14);
		contentPane.add(lblProductQuantity);
		
		 JComboBox<Integer> Items = new JComboBox<>();
	        Items.setModel(new DefaultComboBoxModel<>(new Integer[] { 1,2,3,4,5,6,7,8,9,10}));
	        Items.setBounds(10, 84, 96, 20);
		
		contentPane.add(Items);
		AutoCompleteDecorator.decorate(Items);
		//Items.setColumns(10);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Retrieve values from text fields
		      //  String productName = Items.getText();
		     //   double productPrice = Double.parseDouble(PPricefield.getText());
		        int productQuantity = Integer.parseInt(PQuantityfield.getText());

		        // Calculate amount
		     //   double amount = productPrice * productQuantity;

		        // Add data to the table model
		        DefaultTableModel model = (DefaultTableModel) table.getModel();
		      //  model.addRow(new Object[]{model.getRowCount() + 1, productName, productPrice, productQuantity, amount});

		        // Clear input fields after adding to the table
		    //    PNamefield.setText("");
		    //    PPricefield.setText("");
		        PQuantityfield.setText("");
		   //     totalsum(amount);
			}
		});
		btnAdd.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		btnAdd.setBounds(513, 82, 89, 23);
		contentPane.add(btnAdd);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setToolTipText("");
		scrollPane.setBounds(10, 133, 627, 234);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"S. No.", "Product Name", "Product Price ", "Product Quantity", "Amount","Tax","Discount"
			}
		));
		
		lblBillDate = new JLabel("Bill Date");
		lblBillDate.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		lblBillDate.setBounds(164, 12, 52, 14);
		contentPane.add(lblBillDate);
		
		btnSave = new JButton("Save");
		btnSave.setAlignmentY(Component.TOP_ALIGNMENT);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// String billNumber = billnofield.getText();
	             //   String billDate = billdatefield.getText();
	               // String productName = PNamefield.getText();
//	                double productPrice = Double.parseDouble(PPricefield.getText());
//	                int productQuantity = Integer.parseInt(PQuantityfield.getText());

	               
			}
		});
		btnSave.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		btnSave.setBounds(479, 423, 86, 27);
		contentPane.add(btnSave);
		
		JLabel lblname = new JLabel("Customer Name");
		lblname.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		lblname.setBounds(342, 12, 102, 14);
		contentPane.add(lblname);
		
		spinner = new JSpinner();
		spinner.setBounds(368, 84, 86, 20);
		contentPane.add(spinner);
		
		JLabel lbltotalAmount = new JLabel("Total Amount");
		lbltotalAmount.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		lbltotalAmount.setBounds(368, 388, 111, 20);
		contentPane.add(lbltotalAmount);
		
		totalAmountfield = new JTextField();
		totalAmountfield.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		totalAmountfield.setBounds(479, 392, 86, 20);
		contentPane.add(totalAmountfield);
		totalAmountfield.setColumns(10);
		
		JLabel lblbillno = new JLabel("");
		lblbillno.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		lblbillno.setBounds(93, 12, 52, 14);
		contentPane.add(lblbillno);
		
		lblbilldate = new JLabel("");
		lblbilldate.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		lblbilldate.setBounds(226, 12, 73, 14);
		contentPane.add(lblbilldate);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // You can change the date format as needed
        Date currentDate = new Date();
        lblbilldate.setText(dateFormat.format(currentDate));
        
        namefield = new JTextField();
        namefield.setBounds(454, 9, 135, 20);
        contentPane.add(namefield);
        namefield.setColumns(10);
	}
	private void totalsum(double value) {
		totalsum = totalsum + value;
		 totalAmountfield.setText(String.valueOf(totalsum));
	}
}
