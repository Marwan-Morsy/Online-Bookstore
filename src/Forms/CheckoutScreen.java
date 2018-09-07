package Forms;


import com.mysql.cj.util.StringUtils;
import dao.BookDataDAO;
import dao.PurchasesDAO;
import models.Book;
import models.CartItem;
import models.Purchase;

import javax.swing.*;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.border.LineBorder;

public class CheckoutScreen extends JPanel {
	private JTextField textFieldOwner;
	private JTextField textFieldCardNumber;
	private JTextField textFieldCVV;
	private JComboBox comboBoxWay;
	private JComboBox comboBoxYear;
	private JComboBox comboBoxMonth;
	private MainView mainView;
	private static String CVV = "257";
	private static String CARD_NUMBER = "4716108999716531";

	/**
	 * Create the panel.
	 */
	public CheckoutScreen(MainView mainView) {
		this.mainView = mainView;
		setFont(new Font("Calibri", Font.PLAIN, 12));
		setBackground(new Color(64, 224, 208));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {30, 130, 150, 90, 100, 130, 30};
		gridBagLayout.rowHeights = new int[] {30, 30, 30, 20, 30, 30, 30, 30, 30, 30, 30, 30};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
		setLayout(gridBagLayout);
		
		JLabel lblCheckout = new JLabel("Checkout");
		lblCheckout.setHorizontalAlignment(SwingConstants.CENTER);
		lblCheckout.setForeground(new Color(0, 128, 0));
		lblCheckout.setFont(new Font("Brush Script MT", Font.BOLD | Font.ITALIC, 60));
		GridBagConstraints gbc_lblCheckout = new GridBagConstraints();
		gbc_lblCheckout.fill = GridBagConstraints.BOTH;
		gbc_lblCheckout.insets = new Insets(0, 0, 5, 5);
		gbc_lblCheckout.gridx = 1;
		gbc_lblCheckout.gridy = 1;
		gbc_lblCheckout.gridwidth = 5;
		add(lblCheckout, gbc_lblCheckout);
		
		JLabel lblErrorMessage = new JLabel("");
		lblErrorMessage.setForeground(new Color(255, 0, 0));
		lblErrorMessage.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_lblErrorMessage = new GridBagConstraints();
		gbc_lblErrorMessage.fill = GridBagConstraints.BOTH;
		gbc_lblErrorMessage.insets = new Insets(0, 0, 5, 5);
		gbc_lblErrorMessage.gridx = 1;
		gbc_lblErrorMessage.gridy = 3;
		gbc_lblErrorMessage.gridwidth = 5;
		add(lblErrorMessage, gbc_lblErrorMessage);
		
		JButton btnConfirmPurchase = new JButton("Purchase");
		btnConfirmPurchase.setRequestFocusEnabled(false);
		btnConfirmPurchase.setFont(new Font("Calibri", Font.BOLD, 14));
		btnConfirmPurchase.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnConfirmPurchase.setBackground(new Color(210, 105, 30));
		GridBagConstraints gbc_btnConfirmPurchase = new GridBagConstraints();
		gbc_btnConfirmPurchase.fill = GridBagConstraints.BOTH;
		gbc_btnConfirmPurchase.insets = new Insets(0, 0, 5, 5);
		gbc_btnConfirmPurchase.gridx = 1;
		gbc_btnConfirmPurchase.gridy = 4;
		add(btnConfirmPurchase, gbc_btnConfirmPurchase);
		
		JButton btnBack = new JButton("To Cart");
		btnBack.setRequestFocusEnabled(false);
		btnBack.setFont(new Font("Calibri", Font.BOLD, 14));
		btnBack.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnBack.setBackground(new Color(210, 105, 30));
		GridBagConstraints gbc_btnBack = new GridBagConstraints();
		gbc_btnBack.fill = GridBagConstraints.BOTH;
		gbc_btnBack.insets = new Insets(0, 0, 5, 5);
		gbc_btnBack.gridx = 5;
		gbc_btnBack.gridy = 4;
		add(btnBack, gbc_btnBack);
		
		JLabel labelTotalPriceHead = new JLabel("Total Price:");
		labelTotalPriceHead.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_labelTotalPriceHead = new GridBagConstraints();
		gbc_labelTotalPriceHead.fill = GridBagConstraints.BOTH;
		gbc_labelTotalPriceHead.insets = new Insets(0, 0, 5, 5);
		gbc_labelTotalPriceHead.gridx = 2;
		gbc_labelTotalPriceHead.gridy = 5;
		add(labelTotalPriceHead, gbc_labelTotalPriceHead);
		
		JLabel lblTotalPrice = new JLabel(mainView.userCart.getTotalPrice()+"$");
		lblTotalPrice.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalPrice.setFont(new Font("Calibri", Font.PLAIN, 14));
		GridBagConstraints gbc_lblTotalPrice = new GridBagConstraints();
		gbc_lblTotalPrice.fill = GridBagConstraints.BOTH;
		gbc_lblTotalPrice.insets = new Insets(0, 0, 5, 5);
		gbc_lblTotalPrice.gridx = 3;
		gbc_lblTotalPrice.gridy = 5;
		gbc_lblTotalPrice.gridwidth = 2;
		add(lblTotalPrice, gbc_lblTotalPrice);
		
		JLabel lblOwner = new JLabel("Owner:");
		lblOwner.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_lblOwner = new GridBagConstraints();
		gbc_lblOwner.fill = GridBagConstraints.BOTH;
		gbc_lblOwner.insets = new Insets(0, 0, 5, 5);
		gbc_lblOwner.gridx = 2;
		gbc_lblOwner.gridy = 6;
		add(lblOwner, gbc_lblOwner);
		
		textFieldOwner = new JTextField();
		textFieldOwner.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_textFieldOwner = new GridBagConstraints();
		gbc_textFieldOwner.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldOwner.fill = GridBagConstraints.BOTH;
		gbc_textFieldOwner.gridx = 3;
		gbc_textFieldOwner.gridy = 6;
		gbc_textFieldOwner.gridwidth = 2;
		add(textFieldOwner, gbc_textFieldOwner);
		textFieldOwner.setColumns(10);
		
		JLabel lblCardNumber = new JLabel("Card Number:");
		lblCardNumber.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_lblCardNumber = new GridBagConstraints();
		gbc_lblCardNumber.fill = GridBagConstraints.BOTH;
		gbc_lblCardNumber.insets = new Insets(0, 0, 5, 5);
		gbc_lblCardNumber.gridx = 2;
		gbc_lblCardNumber.gridy = 7;
		
		add(lblCardNumber, gbc_lblCardNumber);
		
		textFieldCardNumber = new JTextField();
		textFieldCardNumber.setBorder(new LineBorder(new Color(0, 0, 0)));
		textFieldCardNumber.setColumns(10);
		GridBagConstraints gbc_textFieldCardNumber = new GridBagConstraints();
		gbc_textFieldCardNumber.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldCardNumber.fill = GridBagConstraints.BOTH;
		gbc_textFieldCardNumber.gridx = 3;
		gbc_textFieldCardNumber.gridy = 7;
		gbc_textFieldCardNumber.gridwidth = 2;
		add(textFieldCardNumber, gbc_textFieldCardNumber);
		
		JLabel lblCVV = new JLabel("CVV:");
		lblCVV.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_lblCVV = new GridBagConstraints();
		gbc_lblCVV.fill = GridBagConstraints.BOTH;
		gbc_lblCVV.insets = new Insets(0, 0, 5, 5);
		gbc_lblCVV.gridx = 2;
		gbc_lblCVV.gridy = 8;
		add(lblCVV, gbc_lblCVV);
		
		textFieldCVV = new JTextField();
		textFieldCVV.setBorder(new LineBorder(new Color(0, 0, 0)));
		textFieldCVV.setColumns(10);
		GridBagConstraints gbc_textFieldCVV = new GridBagConstraints();
		gbc_textFieldCVV.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldCVV.fill = GridBagConstraints.BOTH;
		gbc_textFieldCVV.gridx = 3;
		gbc_textFieldCVV.gridy = 8;
		gbc_textFieldCVV.gridwidth = 2;
		add(textFieldCVV, gbc_textFieldCVV);
		
		JLabel labelExpirationDate = new JLabel("Expiration Date:");
		labelExpirationDate.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_labelExpirationDate = new GridBagConstraints();
		gbc_labelExpirationDate.fill = GridBagConstraints.BOTH;
		gbc_labelExpirationDate.insets = new Insets(0, 0, 5, 5);
		gbc_labelExpirationDate.gridx = 2;
		gbc_labelExpirationDate.gridy = 9;
		add(labelExpirationDate, gbc_labelExpirationDate);
		
		comboBoxMonth = new JComboBox();
		comboBoxMonth.setBorder(new LineBorder(new Color(0, 0, 0)));
		comboBoxMonth.setRequestFocusEnabled(false);
		comboBoxMonth.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		GridBagConstraints gbc_comboBoxMonth = new GridBagConstraints();
		gbc_comboBoxMonth.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxMonth.fill = GridBagConstraints.BOTH;
		gbc_comboBoxMonth.gridx = 3;
		gbc_comboBoxMonth.gridy = 9;
		add(comboBoxMonth, gbc_comboBoxMonth);
		
		comboBoxYear = new JComboBox();
		comboBoxYear.setBorder(new LineBorder(new Color(0, 0, 0)));
		comboBoxYear.setRequestFocusEnabled(false);
		comboBoxYear.setModel(new DefaultComboBoxModel(new String[] {"2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025"}));
		GridBagConstraints gbc_comboBoxYear = new GridBagConstraints();
		gbc_comboBoxYear.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxYear.fill = GridBagConstraints.BOTH;
		gbc_comboBoxYear.gridx = 4;
		gbc_comboBoxYear.gridy = 9;
		add(comboBoxYear, gbc_comboBoxYear);
		
		comboBoxWay = new JComboBox();
		comboBoxWay.setBorder(new LineBorder(new Color(0, 0, 0)));
		comboBoxWay.setRequestFocusEnabled(false);
		comboBoxWay.setModel(new DefaultComboBoxModel(new String[] {"Visa", "Master Card", "American Express"}));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.BOTH;
		gbc_comboBox.gridx = 5;
		gbc_comboBox.gridy = 9;
		add(comboBoxWay, gbc_comboBox);
		
		JLabel lblHint = new JLabel("* Card Number = 4716108999716531 CVV = 257 using any to test form");
		lblHint.setFont(new Font("Calibri", Font.PLAIN, 12));
		GridBagConstraints gbc_lblHint = new GridBagConstraints();
		gbc_lblHint.fill = GridBagConstraints.BOTH;
		gbc_lblHint.insets = new Insets(0, 0, 5, 5);
		gbc_lblHint.gridx = 1;
		gbc_lblHint.gridy = 10;
		gbc_lblHint.gridwidth = 5;
		add(lblHint, gbc_lblHint);
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblTotalPrice.setText("");
				textFieldCardNumber.setText("");
				textFieldCVV.setText("");
				textFieldOwner.setText("");
				setVisible(false);
				mainView.natavigateToPanel(mainView.myCartScreen);
			}
		});
		btnConfirmPurchase.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				 boolean isDataValid = true;
				 String ownerNameText       = textFieldOwner.getText().trim().replace("  "," ");
				 String ownerCardNumberText = textFieldCardNumber.getText().trim();
				 String ownerCVVText        = textFieldCVV.getText().trim();
				 

				 if (StringUtils.isEmptyOrWhitespaceOnly(ownerNameText)||StringUtils.isEmptyOrWhitespaceOnly(ownerCardNumberText)
						  ||StringUtils.isEmptyOrWhitespaceOnly(ownerCVVText)){
				  	
				  	lblErrorMessage.setText("You must fill out all entries!");
				  }  else {
					  Pattern pattern = Pattern.compile("^[0-9]+$");
					  Matcher matcherCN = pattern.matcher(ownerCardNumberText);
					  Matcher matcherCVV = pattern.matcher(ownerCVVText);
					  if(isDataValid){
						  Calendar c = Calendar.getInstance();
						  if (Integer.valueOf(comboBoxMonth.getSelectedItem().toString()) >= (c.get(Calendar.MONTH) + 1)) {
							  if ( matcherCN.matches() && matcherCVV.matches() &&
						  			ownerCardNumberText.compareTo(CARD_NUMBER) == 0 && ownerCVVText.compareTo(CVV) == 0)  {
						  		JFrame frame = new JFrame();
						  		int reply = JOptionPane.showConfirmDialog(frame, "Are you sure want to buy ?", "Confirm purchase", JOptionPane.YES_NO_OPTION);
						  		if (reply == JOptionPane.YES_OPTION) {
						  			try {
						  				String errorMessage = PurchasesDAO.handleCart(mainView.userCart,mainView.userSignedIn.getUid());
						  				if(errorMessage == null){
						  					lblErrorMessage.setText("");
						  					lblTotalPrice.setText("");
						  					textFieldCardNumber.setText("");
						  					textFieldCVV.setText("");
						  					textFieldOwner.setText("");
						  					JOptionPane.showMessageDialog(null, "Purchase is added Successfully!");
						  					mainView.userCart.clearCart();
						  					mainView.myCartScreen = new MyCartScreen(mainView);
						  				} else{
						  					lblErrorMessage.setText("** " + errorMessage + "!");
						  				}
						  			} catch (SQLException e1) {
						  				lblErrorMessage.setText("**" + e1.getMessage() + "!");
						  			}
						  		}else {
						  			frame.setVisible(false);
						  		}
						  	} else {
						  		lblErrorMessage.setText("** Invalid Credit Card or CVV!"); 
						  	}
						  } else {
							  lblErrorMessage.setText("** Credit Card Expired!");
						  }
					  } else {
						  lblErrorMessage.setText("** Invalid Owner name!");
					  }
				  }
			}
		});
	}

}
