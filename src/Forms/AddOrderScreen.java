package Forms;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import dao.OrderDataDAO;
import models.Order;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;

public class AddOrderScreen extends JPanel {
	private JTextField textFieldISBN;
	private MainView mainView ;
	/**
	 * Create the panel.
	 */
	public AddOrderScreen(MainView mainView) {
		this.mainView=mainView;
		setBackground(new Color(64, 224, 208));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {30, 90, 160, 160, 90, 30, 0};
		gridBagLayout.rowHeights = new int[] {70, 10, 0, 35, 35, 35, 30, 35, 30, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblAddOrderTitle = new JLabel("Add Order");
		lblAddOrderTitle.setForeground(new Color(0, 128, 0));
		lblAddOrderTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddOrderTitle.setFont(new Font("Brush Script MT", Font.BOLD | Font.ITALIC, 60));
		GridBagConstraints gbc_lblAddOrderTitle = new GridBagConstraints();
		gbc_lblAddOrderTitle.fill = GridBagConstraints.BOTH;
		gbc_lblAddOrderTitle.insets = new Insets(0, 0, 5, 5);
		gbc_lblAddOrderTitle.gridx = 1;
		gbc_lblAddOrderTitle.gridy = 0;
		gbc_lblAddOrderTitle.gridwidth = 4;
		add(lblAddOrderTitle, gbc_lblAddOrderTitle);
		
		JLabel lblErrorMessage = new JLabel("");
		lblErrorMessage.setForeground(new Color(255, 0, 0));
		lblErrorMessage.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_lblErrorMessage = new GridBagConstraints();
		gbc_lblErrorMessage.fill = GridBagConstraints.VERTICAL;
		gbc_lblErrorMessage.anchor = GridBagConstraints.WEST;
		gbc_lblErrorMessage.insets = new Insets(0, 0, 5, 5);
		gbc_lblErrorMessage.gridx = 2;
		gbc_lblErrorMessage.gridy = 1;
		gbc_lblErrorMessage.gridwidth = 2;
		add(lblErrorMessage, gbc_lblErrorMessage);
		
		JLabel lblISBNHead = new JLabel("ISBN:");
		lblISBNHead.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_lblISBNHead = new GridBagConstraints();
		gbc_lblISBNHead.fill = GridBagConstraints.BOTH;
		gbc_lblISBNHead.insets = new Insets(0, 0, 5, 5);
		gbc_lblISBNHead.gridx = 2;
		gbc_lblISBNHead.gridy = 3;
		add(lblISBNHead, gbc_lblISBNHead);
		
		textFieldISBN = new JTextField();
		textFieldISBN.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldISBN.setFont(new Font("Calibri", Font.PLAIN, 14));
		textFieldISBN.setColumns(10);
		textFieldISBN.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_textFieldISBN = new GridBagConstraints();
		gbc_textFieldISBN.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldISBN.fill = GridBagConstraints.BOTH;
		gbc_textFieldISBN.gridx = 3;
		gbc_textFieldISBN.gridy = 3;
		add(textFieldISBN, gbc_textFieldISBN);
		
		JLabel lblQuantityHead = new JLabel("Quantity:");
		lblQuantityHead.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_lblQuantityHead = new GridBagConstraints();
		gbc_lblQuantityHead.fill = GridBagConstraints.BOTH;
		gbc_lblQuantityHead.insets = new Insets(0, 0, 5, 5);
		gbc_lblQuantityHead.gridx = 2;
		gbc_lblQuantityHead.gridy = 5;
		add(lblQuantityHead, gbc_lblQuantityHead);
		
		JSpinner spinnerQuantity = new JSpinner();
		spinnerQuantity.setRequestFocusEnabled(false);
		spinnerQuantity.setBorder(null);
		spinnerQuantity.setFont(new Font("Calibri", Font.PLAIN, 14));
		spinnerQuantity.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		GridBagConstraints gbc_spinnerQuantity = new GridBagConstraints();
		gbc_spinnerQuantity.fill = GridBagConstraints.BOTH;
		gbc_spinnerQuantity.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerQuantity.gridx = 3;
		gbc_spinnerQuantity.gridy = 5;
		add(spinnerQuantity, gbc_spinnerQuantity);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String isbn = textFieldISBN.getText().trim();
				Pattern pattern = Pattern.compile("^[0-9]+$");
		        Matcher matcher = pattern.matcher(isbn);
		        int quantity = (int) spinnerQuantity.getModel().getValue();
		        if (!matcher.matches()) {
		        	lblErrorMessage.setText("** Invalid ISBN!");
		        } else {
		        	Order order = new Order(Long.valueOf(isbn), null, quantity);
		        	String error = OrderDataDAO.AddOrder(order); 
		        	if (error != null) {
		        		lblErrorMessage.setText("**" + error + "!");
		        	} else {
		        		lblErrorMessage.setText("");
		        		textFieldISBN.setText("");
						spinnerQuantity.setValue(1);
						JOptionPane.showMessageDialog(null, "Book Order added Successfully!");
		        		setVisible(false);
		        		mainView.natavigateToPanel(mainView.bookSearchScreen);
		        	}
		        }
		        
		        
			}
		});
		btnAdd.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnAdd.setBackground(new Color(210, 105, 30));
		btnAdd.setFont(new Font("Calibri", Font.BOLD, 14));
		btnAdd.setRequestFocusEnabled(false);
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.fill = GridBagConstraints.BOTH;
		gbc_btnAdd.insets = new Insets(0, 0, 5, 5);
		gbc_btnAdd.gridx = 1;
		gbc_btnAdd.gridy = 7;
		add(btnAdd, gbc_btnAdd);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblErrorMessage.setText("");
				textFieldISBN.setText("");
				spinnerQuantity.setValue(1);
				setVisible(false);
				mainView.natavigateToPanel(mainView.bookSearchScreen);
			}
		});
		btnCancel.setRequestFocusEnabled(false);
		btnCancel.setFont(new Font("Calibri", Font.BOLD, 14));
		btnCancel.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnCancel.setBackground(new Color(210, 105, 30));
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.fill = GridBagConstraints.BOTH;
		gbc_btnCancel.insets = new Insets(0, 0, 5, 5);
		gbc_btnCancel.gridx = 4;
		gbc_btnCancel.gridy = 7;
		add(btnCancel, gbc_btnCancel);

	}

}
