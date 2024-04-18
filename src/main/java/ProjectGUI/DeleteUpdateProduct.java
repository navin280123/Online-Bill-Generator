package ProjectGUI;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteUpdateProduct extends JFrame {
    private JLabel nameLabel, sellingPriceLabel, markedPriceLabel, purchasedPriceLabel, expiryLabel, barcodeLabel, hsnLabel, taxLabel, categoryLabel, subcategoryLabel, quantityLabel;
    private JTextField nameField, sellingPriceField, markedPriceField, purchasedPriceField, expiryField, barcodeField, hsnField, taxField, categoryField, subcategoryField, quantityField;
    private JButton updateButton, deleteButton;
   
    public DeleteUpdateProduct(ArrayList<String> data,DefaultTableModel model, int selectedRow) {
        setTitle("Delete Update Product");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        
        nameLabel = new JLabel("Name:");
        nameField = new JTextField(15);

        sellingPriceLabel = new JLabel("Selling Price:");
        sellingPriceField = new JTextField(15);

        markedPriceLabel = new JLabel("Marked Price:");
        markedPriceField = new JTextField(15);

        purchasedPriceLabel = new JLabel("Purchased Price:");
        purchasedPriceField = new JTextField(15);

        expiryLabel = new JLabel("Expiry Date:");
        expiryField = new JTextField(15);

        barcodeLabel = new JLabel("Barcode:");
        barcodeField = new JTextField(15);
        barcodeField.setEditable(false);
       
        hsnLabel = new JLabel("HSN:");
        hsnField = new JTextField(15);

        taxLabel = new JLabel("Tax:");
        taxField = new JTextField(15);

        categoryLabel = new JLabel("Category:");
        categoryField = new JTextField(15);

        subcategoryLabel = new JLabel("Sub Category:");
        subcategoryField = new JTextField(15);

        quantityLabel = new JLabel("Quantity:");
        quantityField = new JTextField(15);

        

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(sellingPriceLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(sellingPriceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(markedPriceLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(markedPriceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(purchasedPriceLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(purchasedPriceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(expiryLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(expiryField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(barcodeLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(barcodeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(hsnLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        panel.add(hsnField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(taxLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        panel.add(taxField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        panel.add(categoryLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 8;
        panel.add(categoryField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        panel.add(subcategoryLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 9;
        panel.add(subcategoryField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 10;
        panel.add(quantityLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 10;
        panel.add(quantityField, gbc);

        updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateProduct(data,model,selectedRow);
            }

        });

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteProduct(data,model,selectedRow);
            }
        });

        // Add components to panel using GridBagConstraints

        gbc.gridx = 0;
        gbc.gridy = 11;
        panel.add(updateButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 11;
        panel.add(deleteButton, gbc);

        add(panel);
        setDateToTextField(data);
        setVisible(true);
    }

   

	protected void deleteProduct(ArrayList<String> data, DefaultTableModel model, int selectedRow) {
;
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
        // Initialize Firebase database reference
   	 DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child(ID).child("product");

     // Path to the node you want to delete

     // Delete the node
     databaseRef.child(data.get(0)).removeValueAsync();
     model.removeRow(selectedRow);
     JOptionPane.showMessageDialog(null, "Product Deleted SuccessFully", "Success", JOptionPane.INFORMATION_MESSAGE);
     dispose();
	}



	protected void updateProduct(ArrayList<String> data, DefaultTableModel model, int selectedRow) {
		// TODO Auto-generated method 
		String productName = nameField.getText();
        double sellingPrice = Double.parseDouble(sellingPriceField.getText());
        double markedPrice = Double.parseDouble(markedPriceField.getText());
        double purchasedPrice = Double.parseDouble(purchasedPriceField.getText());
        String expiryDate = expiryField.getText();
        String barcode = barcodeField.getText();
        String hsn = hsnField.getText();
        double tax = Double.parseDouble(taxField.getText());
        String category = categoryField.getText();
        String subcategory = subcategoryField.getText();
        int quantity = Integer.parseInt(quantityField.getText());
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
	        // Initialize Firebase database reference
	   	DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child(ID).child("product");
	   	Map<String, Object> product = new HashMap<>();
        product.put("name", productName);
        product.put("sellingPrice", sellingPrice);
        product.put("markedPrice", markedPrice);
        product.put("purchasedPrice", purchasedPrice);
        product.put("expiryDate", expiryDate);
        product.put("barcode", barcode);
        product.put("hsn", hsn);
        product.put("tax", tax);
        product.put("category", category);
        product.put("subcategory", subcategory);
        product.put("quantity", quantity);
        // Generate a new key for the product

        // Add the product to the database under the generated key
        databaseRef.child(barcode).setValueAsync(product);
        
        JOptionPane.showMessageDialog(null, "Product Updated SuccessFully", "Success", JOptionPane.INFORMATION_MESSAGE);
        model.removeRow(selectedRow);
        model.addRow(new Object[]{barcode,productName,hsn,category,subcategory,expiryDate,tax,purchasedPrice,markedPrice,sellingPrice,quantity});
		dispose();
	}



	private void setDateToTextField(ArrayList<String> data) {
		// TODO Auto-generated method stub
		barcodeField.setText(data.get(0));
		nameField.setText(data.get(1));
        sellingPriceField.setText(data.get(9));
        markedPriceField.setText(data.get(8));
        purchasedPriceField.setText(data.get(7));
        expiryField.setText(data.get(5));
        hsnField.setText(data.get(2));
        taxField.setText(data.get(6));
        categoryField.setText(data.get(3));
        subcategoryField.setText(data.get(4));
        quantityField.setText(data.get(10));
	}




    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                new DeleteUpdateProduct(null);
//            }
//        });
    }

    
}
