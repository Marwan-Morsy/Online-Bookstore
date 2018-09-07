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

import org.apache.commons.validator.routines.EmailValidator;

import dao.UserDataDAO;
import models.User;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;


public class UpgradeUserScreen extends JPanel {
	private JTextField textFieldUsername;
	private JTextField textFieldEmail;
	private MainView mainview;

	/**
	 * Create the panel.
	 */
	public UpgradeUserScreen(MainView mainView) {
		this.mainview = mainView;
		setBackground(new Color(64, 224, 208));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {30, 90, 160, 160, 90, 30, 0};
		gridBagLayout.rowHeights = new int[] {70, 10, 0, 35, 35, 35, 30, 35, 30, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblUpgradeUserTitle = new JLabel("Promote User");
		lblUpgradeUserTitle.setForeground(new Color(0, 128, 0));
		lblUpgradeUserTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblUpgradeUserTitle.setFont(new Font("Brush Script MT", Font.BOLD | Font.ITALIC, 60));
		GridBagConstraints gbc_lblUpgradeUserTitle = new GridBagConstraints();
		gbc_lblUpgradeUserTitle.fill = GridBagConstraints.BOTH;
		gbc_lblUpgradeUserTitle.insets = new Insets(0, 0, 5, 5);
		gbc_lblUpgradeUserTitle.gridx = 1;
		gbc_lblUpgradeUserTitle.gridy = 0;
		gbc_lblUpgradeUserTitle.gridwidth = 4;
		add(lblUpgradeUserTitle, gbc_lblUpgradeUserTitle);
		
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
		
		JLabel lblUserNameHead = new JLabel("Username:");
		lblUserNameHead.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_lblUserNameHead = new GridBagConstraints();
		gbc_lblUserNameHead.fill = GridBagConstraints.BOTH;
		gbc_lblUserNameHead.insets = new Insets(0, 0, 5, 5);
		gbc_lblUserNameHead.gridx = 2;
		gbc_lblUserNameHead.gridy = 3;
		add(lblUserNameHead, gbc_lblUserNameHead);
		
		textFieldUsername = new JTextField();
		textFieldUsername.setFont(new Font("Calibri", Font.PLAIN, 14));
		textFieldUsername.setColumns(10);
		textFieldUsername.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_textFieldUsername = new GridBagConstraints();
		gbc_textFieldUsername.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldUsername.fill = GridBagConstraints.BOTH;
		gbc_textFieldUsername.gridx = 3;
		gbc_textFieldUsername.gridy = 3;
		add(textFieldUsername, gbc_textFieldUsername);
		
		JLabel lblQuantityHead = new JLabel("Email:");
		lblQuantityHead.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_lblQuantityHead = new GridBagConstraints();
		gbc_lblQuantityHead.fill = GridBagConstraints.BOTH;
		gbc_lblQuantityHead.insets = new Insets(0, 0, 5, 5);
		gbc_lblQuantityHead.gridx = 2;
		gbc_lblQuantityHead.gridy = 5;
		add(lblQuantityHead, gbc_lblQuantityHead);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setFont(new Font("Calibri", Font.PLAIN, 14));
		textFieldEmail.setColumns(10);
		textFieldEmail.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_textFieldEmail = new GridBagConstraints();
		gbc_textFieldEmail.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldEmail.fill = GridBagConstraints.BOTH;
		gbc_textFieldEmail.gridx = 3;
		gbc_textFieldEmail.gridy = 5;
		add(textFieldEmail, gbc_textFieldEmail);
		
		JButton btnUpgrade = new JButton("Promote");
		btnUpgrade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				 String email = textFieldEmail.getText().trim();
				 String userName = textFieldUsername.getText().trim();
				 if (!userName.isEmpty() && !email.isEmpty()) {
					 if (EmailValidator.getInstance().isValid(email)) {
					 	User user = UserDataDAO.findUserByEmail(userName, email);
					 	if(user != null) {
					 		if (!user.isManager()) {
					 			User userTemp = new User("", "", "", user.getUid(), true, "", "","", "");
					 			
					 			String error = UserDataDAO.updateUser(userTemp);
					 			if (error != null) {
					 				lblErrorMessage.setText("**" + error + "!");
					 			} else {
					 				lblErrorMessage.setText("");
					 				textFieldEmail.setText("");
									textFieldUsername.setText("");
									setVisible(false);
									mainView.natavigateToPanel(mainView.bookSearchScreen);
									JOptionPane.showMessageDialog(null, "User promoted Successfully!");
					 			}
					 		} else {
					 			JOptionPane.showMessageDialog(null, "User is already Manager!");
					 		}
					 	} else {
					 		lblErrorMessage.setText("** Invalid Username or Email!");
					 	}
				 	} else {
					 	lblErrorMessage.setText("** Invalid Email Format!");
				 	}
				 } else {
					 lblErrorMessage.setText("** Fill out all fields!");
				 }
			}
		});
		btnUpgrade.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnUpgrade.setBackground(new Color(210, 105, 30));
		btnUpgrade.setFont(new Font("Calibri", Font.BOLD, 14));
		btnUpgrade.setRequestFocusEnabled(false);
		GridBagConstraints gbc_btnUpgrade = new GridBagConstraints();
		gbc_btnUpgrade.fill = GridBagConstraints.BOTH;
		gbc_btnUpgrade.insets = new Insets(0, 0, 5, 5);
		gbc_btnUpgrade.gridx = 1;
		gbc_btnUpgrade.gridy = 7;
		add(btnUpgrade, gbc_btnUpgrade);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblErrorMessage.setText("");
				textFieldEmail.setText("");
				textFieldUsername.setText("");
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
