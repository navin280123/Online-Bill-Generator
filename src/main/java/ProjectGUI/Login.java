package ProjectGUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.api.core.ApiFuture;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseError;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Login extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public static void main(String[] args) throws IOException {
        FileInputStream serviceAccount = new FileInputStream("google-services.json");
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://store-management-762de-default-rtdb.firebaseio.com")
                .build();
        FirebaseApp.initializeApp(options);

        EventQueue.invokeLater(new Runnable() {
            public void run() {
            	Properties properties = new Properties();
                InputStream inputStream = null;
                
                try {
                    // Load properties file
                    inputStream = new FileInputStream("config.properties");
                    properties.load(inputStream);

                    // Access variables
                    String userStatus = properties.getProperty("Login.Status");
                    if(userStatus.equals("true")) {
                    	mainPage main = new mainPage();
                    }
                    else {
                      try {
	                      Login frame = new Login();
	                      frame.setVisible(true);
	                  } 
                      catch (Exception e) {
	                      e.printStackTrace();
	                  }	
                   }
                    
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

            }
        });
    }

    public Login() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);

        JPanel panel = new JPanel();
        panel.setBackground(Color.LIGHT_GRAY);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setForeground(Color.WHITE);
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setForeground(Color.WHITE);
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));

        usernameField = new JTextField();
        usernameField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setColumns(10);

        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String mobile = usernameField.getText().toString();
            	String password = passwordField.getText();
            	DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("credential");
            	database.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                    	System.out.println(dataSnapshot.hasChild(mobile));
                    	System.out.println(password);
                    	System.out.println(dataSnapshot.child(mobile).getValue());
                    	System.out.println(dataSnapshot.child(mobile).getValue().equals(password));
                    	
                        if (dataSnapshot.hasChild(mobile)&& dataSnapshot.child(mobile).getValue().equals(password)) {
                            System.out.println("The 'phoneNumber' node exists under the 'credential' node.");
                            

                            Properties properties = new Properties();
                            InputStream inputStream = null;
                            OutputStream outputStream = null;

                            try {
                                inputStream = new FileInputStream("config.properties");
                                properties.load(inputStream);

                                // Change property value
	                              properties.setProperty("Login.Id",mobile);
	                              properties.setProperty("Login.Pass", password);
	                              properties.setProperty("Login.Status", "true");

                                // Save the modified properties back to the file
                                outputStream = new FileOutputStream("config.properties");
                                properties.store(outputStream, null);

                                System.out.println("Property value changed successfully.");
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                // Close streams
                                try {
                                    if (inputStream != null)
                                        inputStream.close();
                                    if (outputStream != null)
                                        outputStream.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            mainPage main = new mainPage();
                            dispose();
                            
                        } 
                        else {
                            System.out.println("The 'phoneNumber' node does not exist under the 'credential' node.");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("Error reading from database: " + databaseError.getMessage());
                    }
                });
            }
        });

        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
        	gl_panel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblUsername)
        				.addComponent(lblPassword, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addComponent(usernameField, 171, 171, 171)
        				.addComponent(passwordField, 171, 171, 171))
        			.addContainerGap())
        		.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
        			.addGap(59)
        			.addComponent(btnLogin, GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
        			.addGap(45))
        );
        gl_panel.setVerticalGroup(
        	gl_panel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel.createSequentialGroup()
        			.addGap(33)
        			.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblUsername, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
        				.addComponent(usernameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(28)
        			.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblPassword, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
        				.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(54)
        			.addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(129, Short.MAX_VALUE))
        );
        panel.setLayout(gl_panel);

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(Color.LIGHT_GRAY);

        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
            gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(gl_contentPane.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, GroupLayout.PREFERRED_SIZE, 275, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                .addContainerGap())
        );
        gl_contentPane.setVerticalGroup(
            gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(gl_contentPane.createSequentialGroup()
                .addContainerGap()
                .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
                    .addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        
        JLabel lblStoreName = new JLabel("Store Name:");
        lblStoreName.setFont(new Font("Tahoma", Font.PLAIN, 14));
        
        
        JTextField storeNameField = new JTextField();
        storeNameField.setColumns(10);
        
        JLabel lblOwnerName = new JLabel("Owner Name:");
        lblOwnerName.setFont(new Font("Tahoma", Font.PLAIN, 14));
        
        JTextField ownerNameField = new JTextField();
        ownerNameField.setColumns(10);
        
        JLabel lblMobile = new JLabel("Mobile:");
        lblMobile.setFont(new Font("Tahoma", Font.PLAIN, 14));
        
        JTextField mobileField = new JTextField();
        mobileField.setColumns(10);
        
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
        
        JTextField emailField = new JTextField();
        emailField.setColumns(10);
        
        JLabel lblStoreType = new JLabel("Store Type:");
        lblStoreType.setFont(new Font("Tahoma", Font.PLAIN, 14));
        
        JComboBox<String> storeTypeComboBox = new JComboBox<>();
        storeTypeComboBox.addItem("Retail");
        storeTypeComboBox.addItem("Grocery");
        storeTypeComboBox.addItem("Pharmacy");
        storeTypeComboBox.addItem("Electronics");
        storeTypeComboBox.addItem("Other");
        
        JLabel lblPasswordSignUp = new JLabel("Password:");
        lblPasswordSignUp.setFont(new Font("Tahoma", Font.PLAIN, 14));
        
        JPasswordField passwordSignUpField = new JPasswordField();
        passwordSignUpField.setColumns(10);
        
        JButton btnSignUp = new JButton("Sign Up");
        btnSignUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String storeName = storeNameField.getText();
            	String name = ownerNameField.getText();
            	String mobile = mobileField.getText();
            	String email = emailField.getText();
            	String storeType = storeTypeComboBox.getSelectedItem().toString();
            	String password = passwordSignUpField.getText();
            	Map<String, Object> details = new HashMap<>();
                details.put("storeName", storeName);
                details.put("name", name);
                details.put("mobile", mobile);
                details.put("email", email);
                details.put("storeType", storeType);
                details.put("password", password); 
                if(verifyInputText(password, mobile,email)) {
                	DatabaseReference database = FirebaseDatabase.getInstance().getReference();

                	// Save details under mobile node
                	database.child(mobile).child("details").setValueAsync(details);

                	// Create a Map to store credentials
                	Map<String, Object> updateData = new HashMap<>();
                	updateData.put(mobile, password);

                	// Create a Map to store email and phone
                	Map<String, Object> emailAndPhoneData = new HashMap<>();
                	emailAndPhoneData.put(mobile,email);

                	database.child("credential").updateChildren(updateData, new DatabaseReference.CompletionListener() {
                	    @Override
                	    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                	        if (databaseError != null) {
                	            System.out.println("Data could not be saved " + databaseError.getMessage());
                	        } else {
                	            database.child("EmailAndPhone").updateChildren(emailAndPhoneData, new DatabaseReference.CompletionListener() {
                	                @Override
                	                public void onComplete(DatabaseError emailAndPhoneError, DatabaseReference emailAndPhoneReference) {
                	                    if (emailAndPhoneError != null) {
                	                        System.out.println("Email and Phone data could not be saved " + emailAndPhoneError.getMessage());
                	                    } else {
                	                        Properties properties = new Properties();
                	                        InputStream inputStream = null;
                	                        OutputStream outputStream = null;

                	                        try {
                	                            inputStream = new FileInputStream("config.properties");
                	                            properties.load(inputStream);

                	                            // Change property value
                	                            properties.setProperty("Login.Id", mobile);
                	                            properties.setProperty("Login.Pass", password);
                	                            properties.setProperty("Login.Status", "true");

                	                            // Save the modified properties back to the file
                	                            outputStream = new FileOutputStream("config.properties");
                	                            properties.store(outputStream, null);

                	                            System.out.println("Property value changed successfully.");
                	                        } catch (IOException e) {
                	                            e.printStackTrace();
                	                        } finally {
                	                            // Close streams
                	                            try {
                	                                if (inputStream != null)
                	                                    inputStream.close();
                	                                if (outputStream != null)
                	                                    outputStream.close();
                	                            } catch (IOException e) {
                	                                e.printStackTrace();
                	                            }
                	                        }
                	                        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

                	                		// Create a new user with email and password
                	                		createUserWithEmailAndPassword(firebaseAuth, email, password);
                	                        mainPage main = new mainPage();
                	                        dispose();
                	                        System.out.println("Data saved successfully.");
                	                    }
                	                }
                	            });
                	        }
                	    }
                	});

                }
//            	
            }
        });
        
        GroupLayout gl_panel_1 = new GroupLayout(panel_1);
        gl_panel_1.setHorizontalGroup(
        	gl_panel_1.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_1.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
        				.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
        					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
        						.addComponent(lblStoreName)
        						.addComponent(lblOwnerName)
        						.addComponent(lblMobile)
        						.addComponent(lblEmail)
        						.addComponent(lblStoreType)
        						.addComponent(lblPasswordSignUp))
        					.addGap(18)
        					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
        						.addComponent(passwordSignUpField, GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
        						.addComponent(storeTypeComboBox, 0, 147, Short.MAX_VALUE)
        						.addComponent(emailField, GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
        						.addComponent(mobileField, GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
        						.addComponent(ownerNameField, GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
        						.addComponent(storeNameField, GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE))
        					.addContainerGap())
        				.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
        					.addComponent(btnSignUp, GroupLayout.PREFERRED_SIZE, 221, GroupLayout.PREFERRED_SIZE)
        					.addGap(20))))
        );
        gl_panel_1.setVerticalGroup(
        	gl_panel_1.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_1.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblStoreName)
        				.addComponent(storeNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblOwnerName)
        				.addComponent(ownerNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblMobile)
        				.addComponent(mobileField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblEmail)
        				.addComponent(emailField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblStoreType)
        				.addComponent(storeTypeComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblPasswordSignUp)
        				.addComponent(passwordSignUpField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(49)
        			.addComponent(btnSignUp, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(85, Short.MAX_VALUE))
        );
        panel_1.setLayout(gl_panel_1);
        
        contentPane.setLayout(gl_contentPane);
        setVisible(true);
    }
    public static boolean verifyInputText(String password, String mobile, String mail) {

		if (password.length() < 8 || password.length() == 0) {
			JOptionPane.showMessageDialog(null, "Password Must contain 8 letters or More", "Invalid Password",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (mobile.length() != 10) {
			JOptionPane.showMessageDialog(null, "Invalid Phone Number", "Invalid Mobile", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (!isValidEmail(mail)) {
			JOptionPane.showMessageDialog(null, "Invalid Email", "Invalid Email", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	public static boolean isValidEmail(String email) {
		String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		Pattern pattern = Pattern.compile(EMAIL_REGEX);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	public static boolean createUserWithEmailAndPassword(FirebaseAuth firebaseAuth, String email, String password) {
		try {
			// Create request to create a new user
			CreateRequest request = new CreateRequest().setEmail(email).setPassword(password);

			// Create the user
			UserRecord userRecord = firebaseAuth.createUser(request);
			System.out.println("Successfully created new user: " + userRecord.getUid());
			return true;
		} catch (Exception e) {
			System.err.println("Error creating user: " + e.getMessage());
			return false;
		}
	}

}