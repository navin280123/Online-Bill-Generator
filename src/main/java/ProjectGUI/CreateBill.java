package ProjectGUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;

public class CreateBill extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField billnofield;
	private JTextField PNamefield;
	private JTextField PPricefield;
	private JTextField PQuantityfield;
	private JTable table;
	private JLabel lblBillDate;
	private JTextField billdatefield;
	private JLabel lblsubtotal;
	private JLabel lbltax;
	private JLabel lbldiscount;
	private JLabel lblgrandtotal;
	private JTextField subtotalfield;
	private JTextField taxfield;
	private JTextField discountfield;
	private JTextField grandtotalfield;
	private JButton btnSave;
	private JTextField namefield;
	private double totalsum=0;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateBill frame = new CreateBill();
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
	public CreateBill() {
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
		
		billnofield = new JTextField();
		billnofield.setBounds(90, 9, 52, 20);
		contentPane.add(billnofield);
		billnofield.setColumns(10);
		
		JLabel lblProductName = new JLabel("Product Name");
		lblProductName.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		lblProductName.setBounds(10, 46, 96, 14);
		contentPane.add(lblProductName);
		
		JLabel lblProductPrice = new JLabel("Product Price");
		lblProductPrice.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		lblProductPrice.setBounds(205, 46, 86, 14);
		contentPane.add(lblProductPrice);
		
		JLabel lblProductQuantity = new JLabel("Product Quantity");
		lblProductQuantity.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		lblProductQuantity.setBounds(358, 46, 121, 14);
		contentPane.add(lblProductQuantity);
		
		PNamefield = new JTextField();
		PNamefield.setBounds(10, 71, 96, 20);
		contentPane.add(PNamefield);
		PNamefield.setColumns(10);
		
		PPricefield = new JTextField();
		PPricefield.setBounds(205, 71, 86, 20);
		contentPane.add(PPricefield);
		PPricefield.setColumns(10);
		
		PQuantityfield = new JTextField();
		PQuantityfield.setBounds(368, 71, 90, 20);
		contentPane.add(PQuantityfield);
		PQuantityfield.setColumns(10);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Retrieve values from text fields
		        String productName = PNamefield.getText();
		        double productPrice = Double.parseDouble(PPricefield.getText());
		        int productQuantity = Integer.parseInt(PQuantityfield.getText());

		        // Calculate amount
		        double amount = productPrice * productQuantity;

		        // Add data to the table model
		        DefaultTableModel model = (DefaultTableModel) table.getModel();
		        model.addRow(new Object[]{model.getRowCount() + 1, productName, productPrice, productQuantity, amount});

		        // Clear input fields after adding to the table
		        PNamefield.setText("");
		        PPricefield.setText("");
		        PQuantityfield.setText("");
		        totalsum(amount);
			}
		});
		btnAdd.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		btnAdd.setBounds(513, 69, 89, 23);
		contentPane.add(btnAdd);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setToolTipText("");
		scrollPane.setBounds(10, 114, 627, 169);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"S. No.", "Product Name", "Product Price ", "Product Quantity", "Amount"
			}
		));
		
		lblBillDate = new JLabel("Bill Date");
		lblBillDate.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		lblBillDate.setBounds(164, 12, 52, 14);
		contentPane.add(lblBillDate);
		
		billdatefield = new JTextField();
		billdatefield.setBounds(226, 9, 86, 20);
		contentPane.add(billdatefield);
		billdatefield.setColumns(10);
		
		lblsubtotal = new JLabel("Sub Total");
		lblsubtotal.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
		lblsubtotal.setBounds(406, 294, 52, 14);
		contentPane.add(lblsubtotal);
		
		lbltax = new JLabel("Tax");
		lbltax.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
		lbltax.setBounds(439, 319, 19, 14);
		contentPane.add(lbltax);
		
		lbldiscount = new JLabel("Discount");
		lbldiscount.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
		lbldiscount.setBounds(406, 344, 62, 14);
		contentPane.add(lbldiscount);
		
		lblgrandtotal = new JLabel("Grand Total");
		lblgrandtotal.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		lblgrandtotal.setBounds(385, 369, 73, 14);
		contentPane.add(lblgrandtotal);
		
		subtotalfield = new JTextField();
		subtotalfield.setBounds(486, 294, 79, 20);
		contentPane.add(subtotalfield);
		subtotalfield.setColumns(10);
		
		taxfield = new JTextField();
		taxfield.setBounds(486, 317, 79, 20);
		contentPane.add(taxfield);
		taxfield.setColumns(10);
		
		discountfield = new JTextField();
		discountfield.setBounds(486, 342, 79, 20);
		contentPane.add(discountfield);
		discountfield.setColumns(10);
		
		grandtotalfield = new JTextField();
		grandtotalfield.setBounds(486, 367, 79, 20);
		contentPane.add(grandtotalfield);
		grandtotalfield.setColumns(10);
		
		btnSave = new JButton("Save");
		btnSave.setAlignmentY(Component.TOP_ALIGNMENT);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 String billNumber = billnofield.getText();
	                String billDate = billdatefield.getText();
	                String productName = PNamefield.getText();
	                double productPrice = Double.parseDouble(PPricefield.getText());
	                int productQuantity = Integer.parseInt(PQuantityfield.getText());

	               
			}
		});
		btnSave.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		btnSave.setBounds(526, 413, 89, 21);
		contentPane.add(btnSave);
		
		JLabel lblname = new JLabel("Customer Name");
		lblname.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		lblname.setBounds(342, 12, 102, 14);
		contentPane.add(lblname);
		
		namefield = new JTextField();
		namefield.setBounds(442, 9, 160, 20);
		contentPane.add(namefield);
		namefield.setColumns(10);
		
	}
	private void totalsum(double value) {
		totalsum = totalsum + value;
		 subtotalfield.setText(String.valueOf(totalsum));
	}
}

