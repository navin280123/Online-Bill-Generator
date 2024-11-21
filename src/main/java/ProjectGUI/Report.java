package ProjectGUI;
import javax.swing.*;
import java.awt.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.awt.event.ActionEvent;

public class Report extends JPanel {
    JPanel reportPanel;
    DefaultCategoryDataset todayDataset;
    DefaultCategoryDataset thisMonthDataset;
    DefaultCategoryDataset thisYearDataset;
    static LinkedHashMap<String,Double> todays = new LinkedHashMap<>();
    static LinkedHashMap<String,Double> month = new LinkedHashMap<>();
    static LinkedHashMap<String,Double> year = new LinkedHashMap<>();
    String currentDate,currentMonth,currentYear;
    public Report() {
        setLayout(new BorderLayout());
        fillhashmap();
        LocalDateTime now = LocalDateTime.now();
        // Format date and time as string without separators
        currentDate = now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        currentMonth = now.format(DateTimeFormatter.ofPattern("MM"));
        currentYear = now.format(DateTimeFormatter.ofPattern("yyyy"));
        
        System.out.println(currentDate);
        System.out.println(currentMonth);
        System.out.println(currentYear);
        
        getAllData();
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        add(buttonPanel, BorderLayout.WEST);

        GridBagConstraints gbc_todaySale = new GridBagConstraints();
        gbc_todaySale.gridx = 0;
        gbc_todaySale.gridy = 0;
        gbc_todaySale.anchor = GridBagConstraints.WEST;
        gbc_todaySale.insets = new Insets(10, 10, 10, 10);

        JButton todaySale = new JButton("Today's Sale");
        todaySale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showChart(todayDataset,"Sales Today");
            }
        });

        todaySale.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
        buttonPanel.add(todaySale, gbc_todaySale);

        JButton thisMonth = new JButton("This Month");
        thisMonth.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showChart(thisMonthDataset,"Sales This Month");
            }
        });
        GridBagConstraints gbc_thisMonth = new GridBagConstraints();
        gbc_thisMonth.insets = new Insets(0, 0, 5, 0);
        gbc_thisMonth.gridx = 0;
        gbc_thisMonth.gridy = 1;
        buttonPanel.add(thisMonth, gbc_thisMonth);

        JButton thisYear = new JButton("This Year");
        thisYear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showChart(thisYearDataset,"Sales This Year");
            }
        });
        GridBagConstraints gbc_thisYear = new GridBagConstraints();
        gbc_thisYear.insets = new Insets(0, 0, 5, 0);
        gbc_thisYear.gridx = 0;
        gbc_thisYear.gridy = 2;
        buttonPanel.add(thisYear, gbc_thisYear);
        
        JButton refresh = new JButton("Refresh");
        refresh.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		getAllData();
        	}
        });
        GridBagConstraints gbc_refresh = new GridBagConstraints();
        gbc_refresh.gridx = 0;
        gbc_refresh.gridy = 3;
        buttonPanel.add(refresh, gbc_refresh);

        reportPanel = new JPanel(new BorderLayout());
        add(reportPanel, BorderLayout.CENTER);

        // Initialize datasets
        

        // Initial chart
        JFreeChart chart = ChartFactory.createBarChart(
                "Sample Bar Chart",
                "Category",
                "Value",
                todayDataset
        );
        ChartPanel chartPanel = new ChartPanel(chart);
        reportPanel.add(chartPanel, BorderLayout.CENTER);
    }

    private void fillhashmap() {
    	for(int i=1;i<=31;i++) {
    		if(i<10) {
    			month.put("0"+i,0.0);
    		}
    		else {
    			month.put(""+i, 0.0);
    		}
    	}
    	for(int i=1;i<=12;i++) {
    		if(i<10) {
    			year.put("0"+i,0.0);
    		}
    		else {
    			year.put(""+i, 0.0);
    		}
    	}
    	System.out.println(month);
    	System.out.println(year);
		
	}

	private void getAllData() {
		System.out.println("report online called");
    	Properties properties = new Properties();
        InputStream inputStream = null;
        todays.clear();
        month.clear();
        year.clear();
        fillhashmap();
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
        System.out.println("this method called and the properties file read dne"+ID);
    	FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference billsReference = firebaseDatabase.getReference(ID).child("Bills");
        
        billsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    	String BillDate = productSnapshot.child("details").child("BillDate").getValue(String.class);
                    	String Amount = productSnapshot.child("details").child("Amount").getValue(String.class);
                    	if(BillDate.equals(currentDate)) {
                    		System.out.println("current bill daded");
                    		todays.put(productSnapshot.getKey(), Double.parseDouble(Amount));
                    	}
                    	if(BillDate.split("/")[1].equals(currentMonth)) {
                    		System.out.println("month passed");
                    		if (month.containsKey(BillDate.split("/")[0])) {
                                double currentValue = month.get(BillDate.split("/")[0]);
                                month.put(BillDate.split("/")[0], currentValue + Double.parseDouble(Amount));
                            }
                    	}
                    	if(BillDate.split("/")[2].equals(currentYear)) {
                    		System.out.println("year passed");
                    		if (year.containsKey(BillDate.split("/")[1])) {
                                double currentValue = year.get(BillDate.split("/")[1]);
                                year.put(BillDate.split("/")[1], currentValue + Double.parseDouble(Amount));
                            }
                    	}	
                    }
                    todayDataset = createDataset(todays);
                    thisMonthDataset = createDataset(month);
                    thisYearDataset = createDataset(year);
                
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
		
	}

	// Method to populate data inside the table with random data
    private DefaultCategoryDataset createDataset(HashMap<String,Double> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String,Double> entry : data.entrySet()) {
        	dataset.addValue(entry.getValue(),"Data",entry.getKey());
        }
        return dataset;
    }

    // Method to update the chart with a new dataset
    private void showChart(DefaultCategoryDataset dataset,String heading) {
        reportPanel.removeAll();
        JFreeChart chart = ChartFactory.createBarChart(
                heading,
                "Category",
                "Value",
                dataset
        );
        ChartPanel chartPanel = new ChartPanel(chart);
        reportPanel.add(chartPanel, BorderLayout.CENTER);
        reportPanel.revalidate();
        reportPanel.repaint();
    }
}


