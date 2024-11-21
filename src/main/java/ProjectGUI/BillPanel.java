package ProjectGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class BillPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTable table;
    DefaultTableModel model;
    private String ID="";
    private String StoreType ="";
    private DatabaseReference billref;
	private static DatabaseReference databaseReference;
    public BillPanel() {
        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane();
        add(scrollPane, BorderLayout.CENTER);

        table = new JTable();
        scrollPane.setViewportView(table);
        table.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"S. No.", "Bill Number", "Bill Date", "Customer Name", "Payment", "Amount"}
        ) {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells uneditable
            }
        });
        table.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));

        // Make rows clickable
       

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        add(buttonPanel, BorderLayout.WEST);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton btncreateBill = new JButton("Create Bill");

        btncreateBill.addActionListener(e -> {
        	LocalDateTime now = LocalDateTime.now();
            // Format date and time as string without separators
            String billNumber = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            createBillPanel add = new createBillPanel(model,billNumber);
            add.setVisible(true);
        });
        btncreateBill.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
        buttonPanel.add(btncreateBill, gbc);
        
        JButton refresh = new JButton("Refresh");
        refresh.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		removeAllRows(model);
        		populateRandomData();
        	}
        });
        GridBagConstraints gbc_refresh = new GridBagConstraints();
        gbc_refresh.gridx = 0;
        gbc_refresh.gridy = 1;
        buttonPanel.add(refresh, gbc_refresh);

        // Populate table with random data
        populateRandomData();
        System.out.println("populateRandomData called");// Change 10 to the desired number of rows
    }

    // Method to populate data inside the table with random data
    private void populateRandomData() {
        model = (DefaultTableModel) table.getModel();
        Properties properties = new Properties();
        InputStream inputStream = null;
        

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
        databaseReference = database.getReference(ID).child("Bills");
        System.out.println(databaseReference);
		databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
            	System.out.println(dataSnapshot);
            	for (DataSnapshot BillSnapshot : dataSnapshot.getChildren()) {
            		 String BillNumber = BillSnapshot.child("details").child("BillNo").getValue(String.class);
                     String BillDate = BillSnapshot.child("details").child("BillDate").getValue(String.class);
                     String CustomerName = BillSnapshot.child("details").child("CustomerName").getValue(String.class);
                     String Payment = BillSnapshot.child("details").child("Payment").getValue(String.class);
                     String Amount = BillSnapshot.child("details").child("Amount").getValue(String.class);
                     System.out.println(BillNumber+BillDate+CustomerName+Payment+Amount);
                     model.addRow(new Object[]{model.getRowCount()+1,BillNumber,BillDate,CustomerName,Payment,Amount});

            	}
//                    new String[]{"S. No.", "Bill Number", "Bill Date", "Customer Name", "Payment", "Amount
                               	
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error fetching product details: " + databaseError.getMessage());
            }
        });
    }
 // Method to remove all items from the table
    private void removeAllRows(DefaultTableModel model) {
        int rowCount = model.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
        }
    }

}

