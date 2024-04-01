package ProjectGUI;

import java.util.ArrayList;
import java.util.Date; // Import Date from java.util
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import javax.swing.*;
import java.awt.*;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;

public class createBillPanel extends JFrame {

	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static DatabaseReference databaseReference;
	private JTable table;
	private JLabel lblBillDate;
	private JButton btnSave;
	private double totalsum=0;
	private JSpinner spinner;
	private JTextField totalAmountfield;
	private JLabel lblbilldate;
	private JTextField namefield;
	private boolean isRunning = true;
	private LinkedHashMap<String,Product> product= new LinkedHashMap<>();
	private ArrayList<String> list = new ArrayList<>();
	private DatabaseReference billref;
	private String ID="";
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					createBillPanel frame = new createBillPanel(null);
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
	}

	/**
	 * Create the frame.
	 * @param model 
	 */
	public createBillPanel(DefaultTableModel model) {
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
		
		LocalDateTime now = LocalDateTime.now();
        // Format date and time as string without separators
        String billNumber = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        
		JLabel lblbillno = new JLabel(billNumber);
		lblbillno.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		lblbillno.setBounds(93, 12, 231, 14);
		contentPane.add(lblbillno);
		
		JLabel barcodeImage = new JLabel("");
        barcodeImage.setBounds(10, 36, 150, 30);
        contentPane.add(barcodeImage);
         // Data to be encoded in the barcode
        try {
			generateBarcode(billNumber,barcodeImage);
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	        
		JLabel lblProductName = new JLabel("Product Name");
		lblProductName.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		lblProductName.setBounds(10, 77, 96, 14);
		contentPane.add(lblProductName);
		
		JLabel lblProductQuantity = new JLabel("Product Quantity");
		lblProductQuantity.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		lblProductQuantity.setBounds(368, 77, 121, 14);
		contentPane.add(lblProductQuantity);
		
		spinner = new JSpinner();
		spinner.setBounds(368, 102, 86, 20);
		contentPane.add(spinner);
		
//		ProductArrayList pal = new ProductArrayList();
//		ProductHelper ph = new ProductHelper(pal.productArrayList);
		
		JComboBox<String> Items = new JComboBox<>();
		addsuggestions(Items);
	    Items.setBounds(10, 102, 289, 20);
		contentPane.add(Items);
		AutoCompleteDecorator.decorate(Items);
		//Items.setColumns(10);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Product selectedProduct =product.get(list.get(Items.getSelectedIndex()));
		        
		        System.out.println(product.size());
//		        double total =(selectedProduct.sellingPrice+((selectedProduct.tax/selectedProduct.sellingPrice)*100))	*(Integer)spinner.getValue();
//		        DecimalFormat df = new DecimalFormat("#.##");
//		        String formattedValuetotal = df.format(total);
//		        double roundedTotal =Double.parseDouble(formattedValuetotal);

//		        totalsum(roundedTotal);
		        addProductToBillNode((Integer)spinner.getValue(),billNumber,selectedProduct);
			}
		});
		btnAdd.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		btnAdd.setBounds(521, 102, 102, 21);
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
				"S. No.", "Product Name", "MRP","Discount","SP","Tax","Quantity", "Amount"
			}
		));
		
		lblBillDate = new JLabel("Bill Date");
		lblBillDate.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		lblBillDate.setBounds(467, 11, 52, 14);
		contentPane.add(lblBillDate);
		
		btnSave = new JButton("Save");
		btnSave.setAlignmentY(Component.TOP_ALIGNMENT);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isRunning= false;
	            savebillDetails(billNumber);
	            model.addRow(new Object[]{model.getRowCount()+1,billNumber,lblbillno.getText(),namefield.getText(),"Cash",totalAmountfield.getText()});
	            dispose();
			}
		});
		btnSave.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		btnSave.setBounds(479, 423, 86, 27);
		contentPane.add(btnSave);
		
		JLabel lblname = new JLabel("Customer Name");
		lblname.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		lblname.setBounds(273, 52, 102, 14);
		contentPane.add(lblname);
		
		
		
		JLabel lbltotalAmount = new JLabel("Total Amount");
		lbltotalAmount.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		lbltotalAmount.setBounds(368, 388, 111, 20);
		contentPane.add(lbltotalAmount);
		
		totalAmountfield = new JTextField();
		totalAmountfield.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		totalAmountfield.setBounds(479, 392, 86, 20);
		contentPane.add(totalAmountfield);
		totalAmountfield.setColumns(10);
		
		
		
		lblbilldate = new JLabel("");
		lblbilldate.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblbilldate.setBounds(529, 11, 94, 14);
		contentPane.add(lblbilldate);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); // You can change the date format as needed
        Date currentDate = new Date();
        lblbilldate.setText(dateFormat.format(currentDate));
        
        namefield = new JTextField();
        namefield.setBounds(385, 46, 238, 20);
        contentPane.add(namefield);
        namefield.setColumns(10);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning) {
                    try {
                        // Call your method here
                        fetchProducts(billNumber);
                        System.out.println("fetching....");
                        Thread.sleep(2000); // Sleep for 2 seconds
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	int option = JOptionPane.showConfirmDialog(
                        createBillPanel.this, 
                        "Do You Want To Save The Bill", 
                        "Confirmation", 
                        JOptionPane.YES_NO_OPTION
                    );
                    
                    if (option == JOptionPane.YES_OPTION) {
                        savebillDetails(billNumber);
                        isRunning=false;
                    } else {
                    	 DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child(ID).child("Bills");

                         // Path to the node you want to delete

                         // Delete the node
                         databaseRef.child(billNumber).removeValueAsync();
                         isRunning=false;
                    }
            }
        });
        // Initialize Firebase
        setVisible(true);
        // Fetch data from Firebase
        fetchProducts(billNumber);
        
	}
	private void totalsum(double value) {
		totalsum = totalsum + value;
		totalAmountfield.setText(String.valueOf(totalsum));
	}
	private void addsuggestions(JComboBox<String> item) {
		Properties properties = new Properties();
        InputStream inputStream = null;
        ArrayList<String> arr = new ArrayList<>();
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
//                    System.out.println("Product ID: " + productId);
                    String barcode = productSnapshot.child("barcode").getValue(String.class);
//                    System.out.println("Barcode: " + barcode);
                    String category = productSnapshot.child("category").getValue(String.class);
//                    System.out.println("Category: " + category);
                    String expiryDate = productSnapshot.child("expiryDate").getValue(String.class);
//                    System.out.println("Expiry Date: " + expiryDate);
                    String hsn = productSnapshot.child("hsn").getValue(String.class);
//                    System.out.println("HSN: " + hsn);
                    double markedPrice = productSnapshot.child("markedPrice").getValue(Double.class);
//                    System.out.println("Marked Price: " + markedPrice);
                    String name = productSnapshot.child("name").getValue(String.class);
//                    System.out.println("Name: " + name);
                    double purchasedPrice = productSnapshot.child("purchasedPrice").getValue(Double.class);
//                    System.out.println("Purchased Price: " + purchasedPrice);
                    int quantity = productSnapshot.child("quantity").getValue(Integer.class);
//                    System.out.println("Quantity: " + quantity);
                    double sellingPrice = productSnapshot.child("sellingPrice").getValue(Double.class);
//                    System.out.println("Selling Price: " + sellingPrice);
                    String subcategory = productSnapshot.child("subcategory").getValue(String.class);
//                    System.out.println("Subcategory: " + subcategory);
                    double tax = productSnapshot.child("tax").getValue(Double.class);
//                    System.out.println("Tax: " + tax);
//                    System.out.println("--------------------------------------");
                    arr.add(name+"    "+barcode);
                    list.add(barcode);
                    product.put(barcode,new Product(barcode,category,expiryDate,hsn,name,subcategory,markedPrice,purchasedPrice,sellingPrice,tax,quantity));
                    
            	}
            	System.out.println(arr);
        		item.setModel(new DefaultComboBoxModel<>(arr.toArray(new String[0])));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error fetching product details: " + databaseError.getMessage());
            }
        });
		
		
	} 
	public static void generateBarcode(String barcodeData, JLabel barcodeImage2) throws WriterException {
        int width = 150; // Width of the barcode
        int height = 30; // Height of the barcode

        // Create the barcode writer
        Code128Writer barcodeWriter = new Code128Writer();
        BitMatrix bitMatrix;
        // Encode the data into a BitMatrix
		bitMatrix = barcodeWriter.encode(barcodeData, BarcodeFormat.CODE_128, width, height);

		// Create a buffered image from the BitMatrix
		Image barcodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

		// Display the image in a frame
		barcodeImage2.setIcon(new ImageIcon(barcodeImage));
    }
	private void addProductToBillNode(Integer quantity, String billNumber, Product selectedProduct) {
        
        // Initialize Firebase database reference
        billref = FirebaseDatabase.getInstance().getReference().child(ID).child("Bills");
        // Create a new product map
        Map<String, Object> product = new HashMap<>();
        double tax =((selectedProduct.tax/selectedProduct.sellingPrice)*100);
        double total =(selectedProduct.sellingPrice+((selectedProduct.tax/selectedProduct.sellingPrice)*100))	*(Integer)spinner.getValue();
        DecimalFormat df = new DecimalFormat("#.##");
        String formattedValuetax = df.format(tax);
        String formattedValuetotal = df.format(total);
        double roundedtax = Double.parseDouble(formattedValuetax);
        double roundedTotal =Double.parseDouble(formattedValuetotal);
        product.put("name", selectedProduct.name);
        product.put("MRP",selectedProduct.markedPrice);
        product.put("discount", selectedProduct.markedPrice-selectedProduct.sellingPrice);
        product.put("sellingPrice", selectedProduct.sellingPrice);
        product.put("tax",roundedtax);
        product.put("quantity", quantity);
        product.put("total",roundedTotal);
        // Generate a new key for the product
        // Add the product to the database under the generated key
        billref.child(billNumber).child(selectedProduct.barcode).setValueAsync(product);
        
    }
	private void fetchProducts(String billNumber) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference billsReference = firebaseDatabase.getReference(ID).child("Bills").child(billNumber);
        
        billsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
            	totalsum=0;
            	ArrayList<billadapter> bills = new ArrayList<>();
                    for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    	String name = productSnapshot.child("name").getValue(String.class);
                    	Double mrp = productSnapshot.child("MRP").getValue(Double.class);
                    	Double discount = productSnapshot.child("discount").getValue(Double.class);
                    	Double sp = productSnapshot.child("sellingPrice").getValue(Double.class);
                    	Double tax = productSnapshot.child("tax").getValue(Double.class);
                    	Double quantity = productSnapshot.child("quantity").getValue(Double.class);
                    	Double total = productSnapshot.child("total").getValue(Double.class);
                    	totalsum(total);
                    	bills.add(new billadapter(name,mrp,discount,sp,tax,quantity,total));
                    }
                    tablerefresh(bills);
                
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
	private void tablerefresh(ArrayList<billadapter> bills) {
    	DefaultTableModel model = (DefaultTableModel) table.getModel();
    	while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
		for(billadapter b :bills) {
			model.addRow(new Object[]{model.getRowCount()+1,b.name,b.mrp,b.discount,b.sp,b.tax,b.quantity,b.total});
		}
       
	}
	public static String formatDateString(String inputDateStr) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date inputDate = inputFormat.parse(inputDateStr);
            return outputFormat.format(inputDate);
        } catch (Exception e) {
            System.out.println("Error parsing date: " + e.getMessage());
            return null;
        }
    }

	private void savebillDetails(String billNumber) {
		billref = FirebaseDatabase.getInstance().getReference().child(ID).child("Bills");
        // Create a new product map
        Map<String, Object> product = new HashMap<>();
        product.put("BillNo", billNumber);
        product.put("BillDate",formatDateString(billNumber.substring(0,8)) );
        product.put("CustomerName", namefield.getText());
        product.put("Payment", "Cash");
        product.put("Amount",totalAmountfield.getText());
        // Generate a new key for the product
        // Add the product to the database under the generated key
        billref.child(billNumber).child("details").setValueAsync(product);
	}
}
class billadapter{
	String name;
	double mrp,discount,sp,tax,quantity,total;
	billadapter(String name,double mrp,double discount,double sp,double tax,double quantity,double total){
		this.name =name;
		this.mrp=mrp;
		this.discount =discount;
		this.sp=sp;
		this.tax=tax;
		this.quantity=quantity;
		this.total=total;
		
	}
}
