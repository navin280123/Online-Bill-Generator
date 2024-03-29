package ProjectGUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.awt.event.ActionEvent;

public class createBillPanel extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private JComboBox barcodeTextField;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private boolean isRunning = true;

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
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        table = new JTable();
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);
        contentPane.add(tablePanel, BorderLayout.CENTER);

        // Barcode Entry Panel
        JPanel barcodePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel barcodeLabel = new JLabel("Enter Product Code:");
        barcodeTextField = new JComboBox();
        barcodeTextField.setEditable(true);
        addProduct(barcodeTextField);
        
        JButton addButton = new JButton("Add Product");
        addButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String barocdevalue= (String) barcodeTextField.getSelectedItem();
        		
        	}
        });
        JButton createBill = new JButton("Create Bill");
        createBill.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		isRunning= false;
        	}
        });
        barcodePanel.add(barcodeLabel);
        barcodePanel.add(barcodeTextField);
        barcodePanel.add(addButton);
        barcodePanel.add(createBill);
        contentPane.add(barcodePanel, BorderLayout.SOUTH);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning) {
                    try {
                        // Call your method here
                        fetchProducts();
                        System.out.println("fetching....");
                        Thread.sleep(2000); // Sleep for 2 seconds
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        // Initialize Firebase
        setVisible(true);
        // Fetch data from Firebase
        fetchProducts();
        
    }

    private void fetchProducts() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference billsReference = firebaseDatabase.getReference("7004394490").child("Bills").child("1234567890");
        
        billsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Product> productList = new ArrayList<>();
                
                    for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                        Product product = productSnapshot.getValue(Product.class);
                        if (product != null) {
                            productList.add(product);
                        }
                    }
                
                
                // Convert productList to Object[][] for JTable
                Object[][] data = new Object[productList.size()][3];
                for (int i = 0; i < productList.size(); i++) {
                    Product product = productList.get(i);
                    data[i][0] = product.getName();
                    data[i][1] = product.getBarcode();
                    data[i][2] = product.getPrice();
                }
                
                String[] columns = {"Name", "Barcode", "Price"};
                table.setModel(new javax.swing.table.DefaultTableModel(data, columns));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }


    private void addProduct(JComboBox barcodeTextField2) {
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
                    
                    barcodeTextField2.addItem(barcode);
            	}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error fetching product details: " + databaseError.getMessage());
            }
        });

        // Perform actions to add product with the barcode entered by the user
        // You need to implement this method according to your requirements
    }

    // Define a Product class to map Firebase data
    private static class Product {
        private String name;
        private String barcode;
        private String price;

        public Product() {
            // Default constructor required for calls to DataSnapshot.getValue(Product.class)
        }

        public String getName() {
            return name;
        }

        public String getBarcode() {
            return barcode;
        }

        public String getPrice() {
            return price;
        }
    }
}
