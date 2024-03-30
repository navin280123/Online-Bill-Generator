package ProjectGUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javax.swing.JTabbedPane;
import javax.swing.JMenuBar;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class mainPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static ArrayList<Product> pr;
	private static DatabaseReference databaseReference;

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
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Properties properties = new Properties();
                InputStream inputStream = null;
                OutputStream outputStream = null;

                try {
                    inputStream = new FileInputStream("config.properties");
                    properties.load(inputStream);

                    // Change property value
                      properties.setProperty("Login.Id","null");
                      properties.setProperty("Login.Pass", "null");
                      properties.setProperty("Login.Status", "false");

                    // Save the modified properties back to the file
                    outputStream = new FileOutputStream("config.properties");
                    properties.store(outputStream, null);

                    System.out.println("Property value changed successfully.");
                } catch (IOException e1) {
                    e1.printStackTrace();
                } finally {
                    // Close streams
                    try {
                        if (inputStream != null)
                            inputStream.close();
                        if (outputStream != null)
                            outputStream.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                Login main = new Login();
                dispose();
			}
		});
	
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
        columnNames.add("Name");
        columnNames.add("HSN No");
        columnNames.add("Category");
        columnNames.add("Sub Category");
        columnNames.add("Expiry Date");
        columnNames.add("Tax");
        columnNames.add("Purchased Price");
        columnNames.add("Marked Price");
        columnNames.add("Selling Price");
        columnNames.add("Quantity");
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
        		AddProduct add = new AddProduct(model);
        	}
        });
        panel.add(btn,BorderLayout.SOUTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        addSampleData(model);
        System.out.println(model.toString());
//		********************************************************************Bill History  Panel******************************************************************************************
		BillPanel panel_1 = new BillPanel();
		tabbedPane.addTab("Bill Panel", null, panel_1, null);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_2, null);
		
		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_3, null);
		setVisible(true);
	}
	private static void addSampleData(DefaultTableModel model) {
		Properties properties = new Properties();
        InputStream inputStream = null;
        
        String ID="";
        try {
            // Load properties file
            inputStream = new FileInputStream("config.properties");
            properties.load(inputStream);

            // Access variables
            ID = properties.getProperty("Login.Id");
            
        } 
        catch (IOException e) {
            e.printStackTrace();
        } 
        finally {
            // Close InputStream
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(ID);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference(ID).child("product");
        System.out.println(databaseReference);
		databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
            	System.out.println(dataSnapshot.getChildrenCount());
            	System.out.println(dataSnapshot);
            	for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    String productId = productSnapshot.getKey();
                    System.out.println("Product ID: " + productId);
                    String barcode = productSnapshot.child("barcode").getValue(String.class);
                    System.out.println("Barcode: " + barcode);
                    String category = productSnapshot.child("category").getValue(String.class);
                    System.out.println("Category: " + category);
                    String expiryDate = productSnapshot.child("expiryDate").getValue(String.class);
                    System.out.println("Expiry Date: " + expiryDate);
                    String hsn = productSnapshot.child("hsn").getValue(String.class);
                    System.out.println("HSN: " + hsn);
                    double markedPrice = productSnapshot.child("markedPrice").getValue(Double.class);
                    System.out.println("Marked Price: " + markedPrice);
                    String name = productSnapshot.child("name").getValue(String.class);
                    System.out.println("Name: " + name);
                    double purchasedPrice = productSnapshot.child("purchasedPrice").getValue(Double.class);
                    System.out.println("Purchased Price: " + purchasedPrice);
                    int quantity = productSnapshot.child("quantity").getValue(Integer.class);
                    System.out.println("Quantity: " + quantity);
                    double sellingPrice = productSnapshot.child("sellingPrice").getValue(Double.class);
                    System.out.println("Selling Price: " + sellingPrice);
                    String subcategory = productSnapshot.child("subcategory").getValue(String.class);
                    System.out.println("Subcategory: " + subcategory);
                    double tax = productSnapshot.child("tax").getValue(Double.class);
                    System.out.println("Tax: " + tax);
                    System.out.println("--------------------------------------");
                    model.addRow(new Object[]{barcode,name,hsn,category,subcategory,expiryDate,tax,purchasedPrice,markedPrice,sellingPrice,quantity});
            	}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error fetching product details: " + databaseError.getMessage());
            }
        });
		
    }
}
