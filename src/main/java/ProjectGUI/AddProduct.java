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

public class AddProduct extends JFrame {
    private JLabel nameLabel, sellingPriceLabel, markedPriceLabel, purchasedPriceLabel, expiryLabel, barcodeLabel, hsnLabel, taxLabel, categoryLabel, subcategoryLabel, quantityLabel;
    private JTextField nameField, sellingPriceField, markedPriceField, purchasedPriceField, expiryField, barcodeField, hsnField, taxField, categoryField, subcategoryField, quantityField;
    private JButton addButton;
    private List<String> suggestions;
    private DatabaseReference productsRef;

    public AddProduct(DefaultTableModel model) {
        setTitle("Add Product");
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

        addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
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

                // Here, you would perform the action of adding the product
                // For demonstration purposes, we just print the product details
                System.out.println("Added product: " + name + " - Selling Price: $" + sellingPrice +
                                   ", Marked Price: $" + markedPrice + ", Purchased Price: $" + purchasedPrice +
                                   ", Expiry: " + expiryDate + ", Barcode: " + barcode + ", HSN: " + hsn +
                                   ", Tax: " + tax + ", Category: " + category + ", Subcategory: " + subcategory +
                                   ", Quantity: " + quantity);
                addProductToDatabase();
                model.addRow(new Object[]{barcode,name,hsn,category,subcategory,expiryDate,tax,purchasedPrice,markedPrice,sellingPrice,quantity});
                // You may want to clear the fields after adding the product
                clearFields();
            }
        });

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

        gbc.gridx = 1;
        gbc.gridy = 11;
        panel.add(addButton, gbc);

        add(panel);

        setVisible(true);
    }

   

	private void clearFields() {
        nameField.setText("");
        sellingPriceField.setText("");
        markedPriceField.setText("");
        purchasedPriceField.setText("");
        expiryField.setText("");
        barcodeField.setText("");
        hsnField.setText("");
        taxField.setText("");
        categoryField.setText("");
        subcategoryField.setText("");
        quantityField.setText("");
    }

    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                new AddProduct(null));
//            }
//        });
    }

    private void addProductToDatabase() {
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
        productsRef = FirebaseDatabase.getInstance().getReference().child(ID).child("product");
        // Create a new product map
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
        productsRef.child(barcode).setValueAsync(product);
        
        JOptionPane.showMessageDialog(null, "Product Added SuccessFully", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
