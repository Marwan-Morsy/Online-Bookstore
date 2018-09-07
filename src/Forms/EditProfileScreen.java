package Forms;

import com.mysql.cj.util.StringUtils;
import dao.UserDataDAO;
import models.Book;
import models.User;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditProfileScreen extends JPanel {

	private JTextField textFieldFirstName;
	private JTextField textFieldLastName;
	private JTextField textFieldUsername;
	private JTextField textFieldEmail;
	private JTextField textFieldPhone;
    private JTextField textFieldShippingAddress;
    private JPasswordField oldPasswordField;
	private JPasswordField newPasswordField;
	private JPasswordField confirmPasswordField;
    private MainView mainWindow ;

    /**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					EditProfileScreen frame = new EditProfileScreen(null);
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public EditProfileScreen( MainView mainWindow ) {
	    this.mainWindow=mainWindow;
		setBounds(100, 100, 741, 484);
		setBackground(new Color(64, 224, 208));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] {60, 135, 30, 135, 60};
		gbl_contentPane.rowHeights = new int[] {30, 50, 30, 0, 35, 30, 35, 30, 35, 30, 0, 35, 30, 30, 30, 30, 35, 30, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0};
		gbl_contentPane.rowWeights = new double[]{1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gbl_contentPane);



		JLabel lblEditProfile = new JLabel("Edit Profile");
		lblEditProfile.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditProfile.setForeground(new Color(0, 128, 0));
		lblEditProfile.setFont(new Font("Brush Script MT", Font.BOLD | Font.ITALIC, 60));
		GridBagConstraints gbc_lblEditProfile = new GridBagConstraints();
		gbc_lblEditProfile.anchor = GridBagConstraints.SOUTH;
		gbc_lblEditProfile.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblEditProfile.insets = new Insets(0, 0, 5, 5);
		gbc_lblEditProfile.gridx = 1;
		gbc_lblEditProfile.gridy = 1;
		gbc_lblEditProfile.gridwidth = 3;
		add(lblEditProfile, gbc_lblEditProfile);
		
		JLabel lblErrorMessage = new JLabel("");
		lblErrorMessage.setForeground(new Color(255, 0, 0));
		lblErrorMessage.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_lblErrorMessage = new GridBagConstraints();
		gbc_lblErrorMessage.fill = GridBagConstraints.VERTICAL;
		gbc_lblErrorMessage.anchor = GridBagConstraints.WEST;
		gbc_lblErrorMessage.insets = new Insets(0, 0, 5, 5);
		gbc_lblErrorMessage.gridx = 1;
		gbc_lblErrorMessage.gridy = 3;
		gbc_lblErrorMessage.gridwidth = 3;
		add(lblErrorMessage, gbc_lblErrorMessage);

		JLabel lblFirstName = new JLabel("First Name:");
		lblFirstName.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		GridBagConstraints gbc_lblFirstName = new GridBagConstraints();
		gbc_lblFirstName.anchor = GridBagConstraints.WEST;
		gbc_lblFirstName.insets = new Insets(0, 0, 5, 5);
		gbc_lblFirstName.gridx = 1;
		gbc_lblFirstName.gridy = 4;
		add(lblFirstName, gbc_lblFirstName);

		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		GridBagConstraints gbc_lblLastName = new GridBagConstraints();
		gbc_lblLastName.anchor = GridBagConstraints.WEST;
		gbc_lblLastName.insets = new Insets(0, 0, 5, 5);
		gbc_lblLastName.gridx = 3;
		gbc_lblLastName.gridy = 4;
		add(lblLastName, gbc_lblLastName);

		textFieldFirstName = new JTextField();
		textFieldFirstName.setFont(new Font("Calibri", Font.PLAIN, 14));
		textFieldFirstName.setColumns(10);
		textFieldFirstName.setBorder(new LineBorder(Color.BLACK, 1, true));
		GridBagConstraints gbc_textFieldFirstName = new GridBagConstraints();
		gbc_textFieldFirstName.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldFirstName.fill = GridBagConstraints.BOTH;
		gbc_textFieldFirstName.gridx = 1;
		gbc_textFieldFirstName.gridy = 5;

		textFieldLastName = new JTextField();
		textFieldLastName.setFont(new Font("Calibri", Font.PLAIN, 14));
		textFieldLastName.setColumns(10);
		textFieldLastName.setBorder(new LineBorder(Color.BLACK, 1, true));
		GridBagConstraints gbc_textFieldLastName = new GridBagConstraints();
		gbc_textFieldLastName.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldLastName.fill = GridBagConstraints.BOTH;
		gbc_textFieldLastName.gridx = 3;
		gbc_textFieldLastName.gridy = 5;
		add(textFieldLastName, gbc_textFieldLastName);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.anchor = GridBagConstraints.WEST;
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.gridx = 1;
		gbc_lblUsername.gridy = 6;
		add(lblUsername, gbc_lblUsername);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.WEST;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 3;
		gbc_lblEmail.gridy = 6;
		add(lblEmail, gbc_lblEmail);

		textFieldUsername = new JTextField();
		textFieldUsername.setFont(new Font("Calibri", Font.PLAIN, 14));
		textFieldUsername.setColumns(10);
		textFieldUsername.setBorder(new LineBorder(Color.BLACK, 1, true));
		GridBagConstraints gbc_textFieldUsername = new GridBagConstraints();
		gbc_textFieldUsername.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldUsername.fill = GridBagConstraints.BOTH;
		gbc_textFieldUsername.gridx = 1;
		gbc_textFieldUsername.gridy = 7;
		add(textFieldUsername, gbc_textFieldUsername);

		textFieldEmail = new JTextField();
		textFieldEmail.setFont(new Font("Calibri", Font.PLAIN, 14));
		textFieldEmail.setColumns(10);
		textFieldEmail.setBorder(new LineBorder(Color.BLACK, 1, true));
		GridBagConstraints gbc_textFieldEmail = new GridBagConstraints();
		gbc_textFieldEmail.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldEmail.fill = GridBagConstraints.BOTH;
		gbc_textFieldEmail.gridx = 3;
		gbc_textFieldEmail.gridy = 7;
		add(textFieldEmail, gbc_textFieldEmail);

		JLabel lblPhone = new JLabel("Phone:");
		lblPhone.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		GridBagConstraints gbc_lblPhone = new GridBagConstraints();
		gbc_lblPhone.anchor = GridBagConstraints.WEST;
		gbc_lblPhone.insets = new Insets(0, 0, 5, 5);
		gbc_lblPhone.gridx = 1;
		gbc_lblPhone.gridy = 8;
		add(lblPhone, gbc_lblPhone);

		JLabel lblShippingAddress = new JLabel("Shipping Address:");
		lblShippingAddress.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		GridBagConstraints gbc_lblShippingAddress = new GridBagConstraints();
		gbc_lblShippingAddress.fill = GridBagConstraints.BOTH;
		gbc_lblShippingAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblShippingAddress.gridx = 3;
		gbc_lblShippingAddress.gridy = 8;
		add(lblShippingAddress, gbc_lblShippingAddress);

		textFieldPhone = new JTextField();
		textFieldPhone.setFont(new Font("Calibri", Font.PLAIN, 14));
		textFieldPhone.setColumns(10);
		textFieldPhone.setBorder(new LineBorder(Color.BLACK, 1, true));
		GridBagConstraints gbc_textFieldPhone = new GridBagConstraints();
		gbc_textFieldPhone.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldPhone.fill = GridBagConstraints.BOTH;
		gbc_textFieldPhone.gridx = 1;
		gbc_textFieldPhone.gridy = 9;
		add(textFieldPhone, gbc_textFieldPhone);

		textFieldShippingAddress = new JTextField();
		textFieldShippingAddress.setFont(new Font("Calibri", Font.PLAIN, 14));
		textFieldShippingAddress.setColumns(10);
		textFieldShippingAddress.setBorder(new LineBorder(Color.BLACK, 1, true));
		GridBagConstraints gbc_textFieldShippingAddress = new GridBagConstraints();
		gbc_textFieldShippingAddress.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldShippingAddress.fill = GridBagConstraints.BOTH;
		gbc_textFieldShippingAddress.gridx = 3;
		gbc_textFieldShippingAddress.gridy = 9;
		add(textFieldShippingAddress, gbc_textFieldShippingAddress);

		JButton btnChangePassword = new JButton("Change Password");
		btnChangePassword.setRequestFocusEnabled(false);
		btnChangePassword.setFont(new Font("Calibri", Font.BOLD, 14));
		btnChangePassword.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnChangePassword.setBackground(new Color(210, 105, 30));
		GridBagConstraints gbc_btnChangePassword = new GridBagConstraints();
		gbc_btnChangePassword.fill = GridBagConstraints.BOTH;
		gbc_btnChangePassword.insets = new Insets(0, 0, 5, 5);
		gbc_btnChangePassword.gridx = 2;
		gbc_btnChangePassword.gridy = 11;
		add(btnChangePassword, gbc_btnChangePassword);

		JLabel lblOldPassword = new JLabel("Old Password:");
		lblOldPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblOldPassword.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		GridBagConstraints gbc_lblOldPassword = new GridBagConstraints();
		gbc_lblOldPassword.fill = GridBagConstraints.BOTH;
		gbc_lblOldPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblOldPassword.gridx = 1;
		gbc_lblOldPassword.gridy = 12;
		add(lblOldPassword, gbc_lblOldPassword);
		lblOldPassword.setVisible(false);

		oldPasswordField = new JPasswordField();
		oldPasswordField.setBorder(new LineBorder(Color.BLACK, 1, true));
		GridBagConstraints gbc_oldPasswordField = new GridBagConstraints();
		gbc_oldPasswordField.insets = new Insets(0, 0, 5, 5);
		gbc_oldPasswordField.fill = GridBagConstraints.BOTH;
		gbc_oldPasswordField.gridx = 2;
		gbc_oldPasswordField.gridy = 12;
		add(oldPasswordField, gbc_oldPasswordField);
		oldPasswordField.setVisible(false);

		JLabel lblNewPassword = new JLabel("New Password:");
		lblNewPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewPassword.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		GridBagConstraints gbc_lblNewPassword = new GridBagConstraints();
		gbc_lblNewPassword.fill = GridBagConstraints.BOTH;
		gbc_lblNewPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewPassword.gridx = 1;
		gbc_lblNewPassword.gridy = 13;
		add(lblNewPassword, gbc_lblNewPassword);
		lblNewPassword.setVisible(false);

		newPasswordField = new JPasswordField();
		newPasswordField.setBorder(new LineBorder(Color.BLACK, 1, true));
		GridBagConstraints gbc_newPasswordField = new GridBagConstraints();
		gbc_newPasswordField.insets = new Insets(0, 0, 5, 5);
		gbc_newPasswordField.fill = GridBagConstraints.BOTH;
		gbc_newPasswordField.gridx = 2;
		gbc_newPasswordField.gridy = 13;
		add(newPasswordField, gbc_newPasswordField);
		newPasswordField.setVisible(false);

		JLabel lblConfirmPassword = new JLabel("Confirm Password:");
		lblConfirmPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblConfirmPassword.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		GridBagConstraints gbc_lblConfirmPassword = new GridBagConstraints();
		gbc_lblConfirmPassword.fill = GridBagConstraints.BOTH;
		gbc_lblConfirmPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblConfirmPassword.gridx = 1;
		gbc_lblConfirmPassword.gridy = 14;
		add(lblConfirmPassword, gbc_lblConfirmPassword);
		lblConfirmPassword.setVisible(false);

		confirmPasswordField = new JPasswordField();
		confirmPasswordField.setBorder(new LineBorder(Color.BLACK, 1, true));
		GridBagConstraints gbc_confirmPasswordField = new GridBagConstraints();
		gbc_confirmPasswordField.insets = new Insets(0, 0, 5, 5);
		gbc_confirmPasswordField.fill = GridBagConstraints.BOTH;
		gbc_confirmPasswordField.gridx = 2;
		gbc_confirmPasswordField.gridy = 14;
		add(confirmPasswordField, gbc_confirmPasswordField);
		confirmPasswordField.setVisible(false);

		JButton btnSubmit = new JButton("Edit Profile");
		btnSubmit.setRequestFocusEnabled(false);
		btnSubmit.setFont(new Font("Calibri", Font.BOLD, 14));
		btnSubmit.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnSubmit.setBackground(new Color(210, 105, 30));
		GridBagConstraints gbc_btnSubmit = new GridBagConstraints();
		gbc_btnSubmit.fill = GridBagConstraints.BOTH;
		gbc_btnSubmit.insets = new Insets(0, 0, 5, 5);
		gbc_btnSubmit.gridx = 1;
		gbc_btnSubmit.gridy = 16;
		add(btnSubmit, gbc_btnSubmit);

		JButton btnBack = new JButton("Back");
		btnBack.setRequestFocusEnabled(false);
		btnBack.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnBack.setBackground(new Color(210, 105, 30));
		btnBack.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_btnBack = new GridBagConstraints();
		gbc_btnBack.insets = new Insets(0, 0, 5, 5);
		gbc_btnBack.fill = GridBagConstraints.BOTH;
		gbc_btnBack.gridx = 3;
		gbc_btnBack.gridy = 16;
		add(btnBack, gbc_btnBack);
        add(textFieldFirstName, gbc_textFieldFirstName);
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	lblOldPassword.setVisible(false);
                oldPasswordField.setVisible(false);
                lblNewPassword.setVisible(false);
                newPasswordField.setVisible(false);
                lblConfirmPassword.setVisible(false);
                confirmPasswordField.setVisible(false);
                setVisible(false);
                mainWindow.natavigateToPanel(mainWindow.bookSearchScreen);
            }
        });
        btnChangePassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblOldPassword.setVisible(true);
                oldPasswordField.setVisible(true);
                lblNewPassword.setVisible(true);
                newPasswordField.setVisible(true);
                lblConfirmPassword.setVisible(true);
                confirmPasswordField.setVisible(true);
            }
        });
        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String editedFirstName = textFieldFirstName.getText().trim();
                String editedLastName = textFieldLastName.getText().trim();
                String editeduserName = textFieldUsername.getText().trim();
                String editedEmail = textFieldEmail.getText().trim();
                String editedPhone = textFieldPhone.getText().trim();
                String editedShippingAddress = textFieldShippingAddress.getText().trim();
                boolean isPasswordValid = true;
                boolean isDataValid = true;
                boolean passwordChange = false;
                String oldPassword="";
                String editedPassword="";
                String confrimEditedpassword="";
                if(btnChangePassword.getModel().isPressed()){
                    passwordChange=true;
                     oldPassword = String.valueOf(oldPasswordField.getPassword());
                     editedPassword = String.valueOf(newPasswordField.getPassword());
                     confrimEditedpassword = String.valueOf(confirmPasswordField.getPassword());
                    if (StringUtils.isEmptyOrWhitespaceOnly(oldPassword)||StringUtils.isEmptyOrWhitespaceOnly(editedPassword)
                            ||StringUtils.isEmptyOrWhitespaceOnly(confrimEditedpassword)){
                        //error "You must fill out required entries!"
                        isPasswordValid = false;
                    }
                    else if (!oldPassword.equals(mainWindow.userSignedIn.getPassword())){
                        //error "Wrong old password try again"
                        isPasswordValid = false;
                    }
                    else if (!editedPassword.equals(confrimEditedpassword)){
                        //error "new written passwords mismatch try again"
                        isPasswordValid = false;
                    }
                }

                if (StringUtils.isEmptyOrWhitespaceOnly(editedFirstName) || StringUtils.isEmptyOrWhitespaceOnly(editedLastName) ||
                        StringUtils.isEmptyOrWhitespaceOnly(editedEmail) || StringUtils.isEmptyOrWhitespaceOnly(editeduserName) ||
                        StringUtils.isEmptyOrWhitespaceOnly(editedPhone) || StringUtils.isEmptyOrWhitespaceOnly(editedShippingAddress)) {
                    // error "** You must fill out required entries!"
                    isDataValid = false;
                } else {
                    if (!isValidEmail(editedEmail)) {
                        // error message "You must enter a vaild email format!"
                        isDataValid = false;
                    }
                    if (!isValidPhoneNo(editedPhone)) {
                        // error message "You must enter a valid 10 digits phone number!"
                        isDataValid = false;
                    }
                }
                if(isDataValid && isPasswordValid){
                    mainWindow.userSignedIn.setPassword(editedPassword);
                    mainWindow.userSignedIn.setUsername(editeduserName);
                    mainWindow.userSignedIn.setPhone(editedPhone);
                    mainWindow.userSignedIn.setLastName(editedLastName);
                    mainWindow.userSignedIn.setFirstName(editedFirstName);
                    mainWindow.userSignedIn.setEmail(editedEmail);
                    mainWindow.userSignedIn.setAddress(editedShippingAddress);
                    UserDataDAO.updateUser(mainWindow.userSignedIn);
                    JOptionPane.showMessageDialog(null, "The information is updated!");
                }

            }
        });
	}
    public void initialUserInfo (){
        if(mainWindow.userSignedIn !=null) {
            textFieldFirstName.setText(mainWindow.userSignedIn.getFirstName());
            textFieldLastName.setText(mainWindow.userSignedIn.getLastName());
            textFieldUsername.setText(mainWindow.userSignedIn.getUsername());
            textFieldEmail.setText(mainWindow.userSignedIn.getEmail());
            textFieldPhone.setText(mainWindow.userSignedIn.getPhone());
            textFieldShippingAddress.setText(mainWindow.userSignedIn.getAddress());
        }
    }
    public   boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(email);

        if(mat.matches()){
            return true;
        }else{
            return  false;
        }
    }
    public  boolean isValidPhoneNo(String phoneNum) {
        if(StringUtils.isStrictlyNumeric(phoneNum) && phoneNum.length()>10){
            return true;
        }else{
            return  false;
        }
    }
}
