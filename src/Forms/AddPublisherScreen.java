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

import dao.AuthorDataDAO;
import dao.PublisherDataDAO;
import models.Author;
import models.Publisher;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;

public class AddPublisherScreen extends JPanel {
	private JTextField textFieldPublisherName;
	private JTextField textFieldPhone;
	private JTextField textFieldAddress;
	private MainView mainView;

	/**
	 * Create the panel.
	 */
	public AddPublisherScreen(MainView mainView) {
		this.mainView = mainView; 
		setBackground(new Color(64, 224, 208));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {30, 90, 160, 160, 90, 30, 0};
		gridBagLayout.rowHeights = new int[] {70, 0, 30, 35, 30, 35, 30, 35, 30, 35, 30, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblAddPublisherTitle = new JLabel("Add Publisher");
		lblAddPublisherTitle.setForeground(new Color(0, 128, 0));
		lblAddPublisherTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddPublisherTitle.setFont(new Font("Brush Script MT", Font.BOLD | Font.ITALIC, 60));
		GridBagConstraints gbc_lblAddPublisherTitle = new GridBagConstraints();
		gbc_lblAddPublisherTitle.fill = GridBagConstraints.BOTH;
		gbc_lblAddPublisherTitle.insets = new Insets(0, 0, 5, 5);
		gbc_lblAddPublisherTitle.gridx = 1;
		gbc_lblAddPublisherTitle.gridy = 0;
		gbc_lblAddPublisherTitle.gridwidth = 4;
		add(lblAddPublisherTitle, gbc_lblAddPublisherTitle);
		
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
		
		JLabel lblPublisherNameHead = new JLabel("Publisher Name:");
		lblPublisherNameHead.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_lblPublisherNameHead = new GridBagConstraints();
		gbc_lblPublisherNameHead.fill = GridBagConstraints.BOTH;
		gbc_lblPublisherNameHead.insets = new Insets(0, 0, 5, 5);
		gbc_lblPublisherNameHead.gridx = 2;
		gbc_lblPublisherNameHead.gridy = 3;
		add(lblPublisherNameHead, gbc_lblPublisherNameHead);
		
		textFieldPublisherName = new JTextField();
		textFieldPublisherName.setFont(new Font("Calibri", Font.PLAIN, 14));
		textFieldPublisherName.setColumns(10);
		textFieldPublisherName.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_textFieldPublisherName = new GridBagConstraints();
		gbc_textFieldPublisherName.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldPublisherName.fill = GridBagConstraints.BOTH;
		gbc_textFieldPublisherName.gridx = 3;
		gbc_textFieldPublisherName.gridy = 3;
		add(textFieldPublisherName, gbc_textFieldPublisherName);
		
		JLabel lblPhoneHead = new JLabel("Phone:");
		lblPhoneHead.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_lblPhoneHead = new GridBagConstraints();
		gbc_lblPhoneHead.fill = GridBagConstraints.BOTH;
		gbc_lblPhoneHead.insets = new Insets(0, 0, 5, 5);
		gbc_lblPhoneHead.gridx = 2;
		gbc_lblPhoneHead.gridy = 5;
		add(lblPhoneHead, gbc_lblPhoneHead);
		
		textFieldPhone = new JTextField();
		textFieldPhone.setFont(new Font("Calibri", Font.PLAIN, 14));
		textFieldPhone.setColumns(10);
		textFieldPhone.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_textFieldPhone = new GridBagConstraints();
		gbc_textFieldPhone.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldPhone.fill = GridBagConstraints.BOTH;
		gbc_textFieldPhone.gridx = 3;
		gbc_textFieldPhone.gridy = 5;
		add(textFieldPhone, gbc_textFieldPhone);
		
		JLabel lblAddressHead = new JLabel("Address:");
		lblAddressHead.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_lblAddressHead = new GridBagConstraints();
		gbc_lblAddressHead.fill = GridBagConstraints.BOTH;
		gbc_lblAddressHead.insets = new Insets(0, 0, 5, 5);
		gbc_lblAddressHead.gridx = 2;
		gbc_lblAddressHead.gridy = 7;
		add(lblAddressHead, gbc_lblAddressHead);
		
		textFieldAddress = new JTextField();
		textFieldAddress.setFont(new Font("Calibri", Font.PLAIN, 14));
		textFieldAddress.setColumns(10);
		textFieldAddress.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_textFieldAddress = new GridBagConstraints();
		gbc_textFieldAddress.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldAddress.fill = GridBagConstraints.BOTH;
		gbc_textFieldAddress.gridx = 3;
		gbc_textFieldAddress.gridy = 7;
		add(textFieldAddress, gbc_textFieldAddress);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String publisherName = textFieldPublisherName.getText().trim();
				String phone = textFieldPhone.getText().trim();
				String address = textFieldAddress.getText().trim();
				Pattern pattern = Pattern.compile("^[0-9]+$");
		        Matcher matcher = pattern.matcher(phone);
		        if (!matcher.matches()) {
		        	lblErrorMessage.setText("** Invalid Phone Number!");
		        } else {
				Publisher publisher = new Publisher(null, publisherName,address, phone);
				String error = PublisherDataDAO.addPublisher(publisher);
					if (error != null) {
						lblErrorMessage.setText("** " + error + "!");
					} else {
						lblErrorMessage.setText("");
						textFieldPublisherName.setText("");
						textFieldAddress.setText("");
						textFieldPhone.setText("");
						setVisible(false);
						mainView.natavigateToPanel(mainView.bookSearchScreen);
						JOptionPane.showMessageDialog(null, "Publisher added Successfully!");
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
		gbc_btnAdd.gridy = 9;
		add(btnAdd, gbc_btnAdd);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblErrorMessage.setText("");
				textFieldPublisherName.setText("");
				textFieldAddress.setText("");
				textFieldPhone.setText("");
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
		gbc_btnCancel.gridy = 9;
		add(btnCancel, gbc_btnCancel);

	}

}
