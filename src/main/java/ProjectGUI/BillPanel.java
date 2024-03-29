package ProjectGUI;

import javax.swing.JPanel;
import javax.swing.JTable;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BillPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;

	
	public BillPanel() {
		setLayout(new BorderLayout());
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(122, 0, 470, 364);
		add(scrollPane,BorderLayout.CENTER);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"S. No.", "Bill Number", "Bill Date", "Customer Name", "Payment", "Amount"
			}
		));
		table.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		JPanel buttonPanel = new JPanel(new GridBagLayout());
        add(buttonPanel, BorderLayout.WEST);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

		
		JButton btncreateBill = new JButton("Create Bill");
		
		btncreateBill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createBillPanel add = new createBillPanel();
				add.setVisible(true);
			}
		});
		btncreateBill.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		btncreateBill.setBounds(0, 166, 121, 23);
		 buttonPanel.add(btncreateBill, gbc);

	}
}