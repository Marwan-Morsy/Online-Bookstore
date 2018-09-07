package Forms;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.mysql.cj.util.StringUtils;

import dao.UserDataDAO;
import models.User;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpScreen extends JPanel {

	private JTextField textFirstName;
	private JTextField textLastName;
	private JLabel lblUserNameReg;
	private JTextField textUserName;
	private JLabel lblNewPassword;
	private JLabel lblConfirmPassword;
	private JPasswordField passwordField;
	private JPasswordField confirmPasswordField;
	private JLabel lblEmail;
	private JTextField textEmail;
	private JLabel lblPhone;
	private JLabel lblShippingAddress;
	private JTextField textPhone;
	private JTextField textShippingAddress;
	private JButton btnBackToLogin;
	private JButton btnRegister;
	private JLabel lblErrorMessage;
    private MainView mainWindow ;
	/**
	 * Launch the application.
	 */
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
        if(StringUtils.isStrictlyNumeric(phoneNum) &&phoneNum.length()==10){
            return true;
        }else{
            return  false;
        }
    }
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					SignUpScreen frame = new SignUpScreen();
//					frame.setTitle("Online Bookstore");
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
	public SignUpScreen(MainView mainWindow ) {
	    this.mainWindow =mainWindow;
        setBounds(100, 100, 741, 484);
        setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
        setBackground(new Color(64, 224, 208));
        setBorder(new EmptyBorder(5, 5, 5, 5));
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{100, 150, 150, 100};
        gbl_contentPane.rowHeights = new int[] {30, 0, 0, 35, 30, 35, 30, 35, 30, 35, 30, 30, 40, 30, 30};
        gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0};
        gbl_contentPane.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        setLayout(gbl_contentPane);

        JLabel lblNewLabel = new JLabel("Registeration");
        lblNewLabel.setForeground(new Color(0, 128, 0));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Brush Script MT", Font.BOLD | Font.ITALIC, 60));
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.fill = GridBagConstraints.BOTH;
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel.gridx = 1;
        gbc_lblNewLabel.gridy = 0;
        gbc_lblNewLabel.gridwidth = 2;
        add(lblNewLabel, gbc_lblNewLabel);
        
        lblErrorMessage = new JLabel("");
        lblErrorMessage.setForeground(new Color(255, 0, 0));
        lblErrorMessage.setFont(new Font("Calibri", Font.BOLD, 14));
        GridBagConstraints gbc_lblErrorMessage = new GridBagConstraints();
        gbc_lblErrorMessage.fill = GridBagConstraints.BOTH;
        gbc_lblErrorMessage.insets = new Insets(0, 0, 5, 5);
        gbc_lblErrorMessage.gridx = 1;
        gbc_lblErrorMessage.gridy = 2;
        gbc_lblErrorMessage.gridwidth = 2;
        add(lblErrorMessage, gbc_lblErrorMessage);

        JLabel lblFirstName = new JLabel("*First Name");
        lblFirstName.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
        GridBagConstraints gbc_lblFirstName = new GridBagConstraints();
        gbc_lblFirstName.fill = GridBagConstraints.BOTH;
        gbc_lblFirstName.insets = new Insets(0, 0, 5, 5);
        gbc_lblFirstName.gridx = 1;
        gbc_lblFirstName.gridy = 3;
        add(lblFirstName, gbc_lblFirstName);

        JLabel lblLastName = new JLabel("*Last Name");
        lblLastName.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
        GridBagConstraints gbc_lblLastName = new GridBagConstraints();
        gbc_lblLastName.fill = GridBagConstraints.BOTH;
        gbc_lblLastName.insets = new Insets(0, 0, 5, 5);
        gbc_lblLastName.gridx = 2;
        gbc_lblLastName.gridy = 3;
        add(lblLastName, gbc_lblLastName);

        textFirstName = new JTextField();
        textFirstName.setBorder(new LineBorder(Color.BLACK, 1, true));
        textFirstName.setFont(new Font("Calibri", Font.PLAIN, 14));
        GridBagConstraints gbc_textFirstName = new GridBagConstraints();
        gbc_textFirstName.insets = new Insets(0, 0, 5, 5);
        gbc_textFirstName.fill = GridBagConstraints.BOTH;
        gbc_textFirstName.gridx = 1;
        gbc_textFirstName.gridy = 4;
        add(textFirstName, gbc_textFirstName);
        textFirstName.setColumns(10);

        textLastName = new JTextField();
        textLastName.setBorder(new LineBorder(Color.BLACK, 1, true));
        GridBagConstraints gbc_textLastName = new GridBagConstraints();
        gbc_textLastName.insets = new Insets(0, 0, 5, 5);
        gbc_textLastName.fill = GridBagConstraints.BOTH;
        gbc_textLastName.gridx = 2;
        gbc_textLastName.gridy = 4;
        add(textLastName, gbc_textLastName);
        textLastName.setColumns(10);

        lblUserNameReg = new JLabel("*Username");
        lblUserNameReg.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
        GridBagConstraints gbc_lblUserNameReg = new GridBagConstraints();
        gbc_lblUserNameReg.fill = GridBagConstraints.BOTH;
        gbc_lblUserNameReg.insets = new Insets(0, 0, 5, 5);
        gbc_lblUserNameReg.gridx = 1;
        gbc_lblUserNameReg.gridy = 5;
        add(lblUserNameReg, gbc_lblUserNameReg);

        lblEmail = new JLabel("*Email");
        lblEmail.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
        GridBagConstraints gbc_lblEmail = new GridBagConstraints();
        gbc_lblEmail.fill = GridBagConstraints.BOTH;
        gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
        gbc_lblEmail.gridx = 2;
        gbc_lblEmail.gridy = 5;
        add(lblEmail, gbc_lblEmail);

        textUserName = new JTextField();
        textUserName.setBorder(new LineBorder(Color.BLACK, 1, true));
        textUserName.setFont(new Font("Calibri", Font.PLAIN, 14));
        GridBagConstraints gbc_textUserName = new GridBagConstraints();
        gbc_textUserName.insets = new Insets(0, 0, 5, 5);
        gbc_textUserName.fill = GridBagConstraints.BOTH;
        gbc_textUserName.gridx = 1;
        gbc_textUserName.gridy = 6;
        add(textUserName, gbc_textUserName);
        textUserName.setColumns(10);

        textEmail = new JTextField();
        textEmail.setBorder(new LineBorder(Color.BLACK, 1, true));
        textEmail.setFont(new Font("Calibri", Font.PLAIN, 14));
        GridBagConstraints gbc_textEmail = new GridBagConstraints();
        gbc_textEmail.insets = new Insets(0, 0, 5, 5);
        gbc_textEmail.fill = GridBagConstraints.BOTH;
        gbc_textEmail.gridx = 2;
        gbc_textEmail.gridy = 6;
        add(textEmail, gbc_textEmail);
        textEmail.setColumns(10);

        lblNewPassword = new JLabel("*Password");
        lblNewPassword.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
        GridBagConstraints gbc_lblNewPassword = new GridBagConstraints();
        gbc_lblNewPassword.fill = GridBagConstraints.BOTH;
        gbc_lblNewPassword.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewPassword.gridx = 1;
        gbc_lblNewPassword.gridy = 7;
        add(lblNewPassword, gbc_lblNewPassword);

        lblConfirmPassword = new JLabel("*Confirm Password");
        lblConfirmPassword.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
        GridBagConstraints gbc_lblConfirmPassword = new GridBagConstraints();
        gbc_lblConfirmPassword.fill = GridBagConstraints.BOTH;
        gbc_lblConfirmPassword.insets = new Insets(0, 0, 5, 5);
        gbc_lblConfirmPassword.gridx = 2;
        gbc_lblConfirmPassword.gridy = 7;
        add(lblConfirmPassword, gbc_lblConfirmPassword);

        passwordField = new JPasswordField();
        passwordField.setBorder(new LineBorder(Color.BLACK, 1, true));
        GridBagConstraints gbc_passwordField = new GridBagConstraints();
        gbc_passwordField.insets = new Insets(0, 0, 5, 5);
        gbc_passwordField.fill = GridBagConstraints.BOTH;
        gbc_passwordField.gridx = 1;
        gbc_passwordField.gridy = 8;
        add(passwordField, gbc_passwordField);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBorder(new LineBorder(Color.BLACK, 1, true));
        GridBagConstraints gbc_confirmPasswordField = new GridBagConstraints();
        gbc_confirmPasswordField.insets = new Insets(0, 0, 5, 5);
        gbc_confirmPasswordField.fill = GridBagConstraints.BOTH;
        gbc_confirmPasswordField.gridx = 2;
        gbc_confirmPasswordField.gridy = 8;
        add(confirmPasswordField, gbc_confirmPasswordField);

        lblPhone = new JLabel("*Phone");
        lblPhone.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
        GridBagConstraints gbc_lblPhone = new GridBagConstraints();
        gbc_lblPhone.fill = GridBagConstraints.BOTH;
        gbc_lblPhone.insets = new Insets(0, 0, 5, 5);
        gbc_lblPhone.gridx = 1;
        gbc_lblPhone.gridy = 9;
        add(lblPhone, gbc_lblPhone);

        lblShippingAddress = new JLabel("*Shipping Address");
        lblShippingAddress.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
        GridBagConstraints gbc_lblShippingAddress = new GridBagConstraints();
        gbc_lblShippingAddress.fill = GridBagConstraints.BOTH;
        gbc_lblShippingAddress.insets = new Insets(0, 0, 5, 5);
        gbc_lblShippingAddress.gridx = 2;
        gbc_lblShippingAddress.gridy = 9;
        add(lblShippingAddress, gbc_lblShippingAddress);

        textPhone = new JTextField();
        textPhone.setBorder(new LineBorder(Color.BLACK, 1, true));
        textPhone.setFont(new Font("Calibri", Font.PLAIN, 14));
        GridBagConstraints gbc_textPhone = new GridBagConstraints();
        gbc_textPhone.insets = new Insets(0, 0, 5, 5);
        gbc_textPhone.fill = GridBagConstraints.BOTH;
        gbc_textPhone.gridx = 1;
        gbc_textPhone.gridy = 10;
        add(textPhone, gbc_textPhone);
        textPhone.setColumns(10);

        textShippingAddress = new JTextField();
        textShippingAddress.setBorder(new LineBorder(Color.BLACK, 1, true));
        textShippingAddress.setFont(new Font("Calibri", Font.PLAIN, 14));
        GridBagConstraints gbc_textShippingAddress = new GridBagConstraints();
        gbc_textShippingAddress.insets = new Insets(0, 0, 5, 5);
        gbc_textShippingAddress.fill = GridBagConstraints.BOTH;
        gbc_textShippingAddress.gridx = 2;
        gbc_textShippingAddress.gridy = 10;
        add(textShippingAddress, gbc_textShippingAddress);
        textShippingAddress.setColumns(10);

        btnBackToLogin = new JButton("Back");
        btnBackToLogin.setRequestFocusEnabled(false);
        btnBackToLogin.setBackground(new Color(210, 105, 30));
        btnBackToLogin.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        btnBackToLogin.setFont(new Font("Calibri", Font.BOLD, 14));
        GridBagConstraints gbc_btnBackToLogin = new GridBagConstraints();
        gbc_btnBackToLogin.fill = GridBagConstraints.BOTH;
        gbc_btnBackToLogin.insets = new Insets(0, 0, 5, 5);
        gbc_btnBackToLogin.gridx = 1;
        gbc_btnBackToLogin.gridy = 12;
        add(btnBackToLogin, gbc_btnBackToLogin);

        btnRegister = new JButton("Submit");
        btnRegister.setBackground(new Color(210, 105, 30));
        btnRegister.setRequestFocusEnabled(false);
        btnRegister.setBorder(new LineBorder(new Color(0, 0, 0)));
        btnRegister.setFont(new Font("Calibri", Font.BOLD, 14));
        GridBagConstraints gbc_btnRegister = new GridBagConstraints();
        gbc_btnRegister.fill = GridBagConstraints.BOTH;
        gbc_btnRegister.insets = new Insets(0, 0, 5, 5);
        gbc_btnRegister.gridx = 2;
        gbc_btnRegister.gridy = 12;
        add(btnRegister, gbc_btnRegister);

        btnBackToLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                setVisible(false);
//                SignInScreen signin = new SignInScreen(mainWindow);
//                signin.setVisible(true);
                setVisible(false);
                mainWindow.natavigateToPanel(mainWindow.signInScreen);
            }
        });
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	lblErrorMessage.setText("");
                String signedupFirstName = textFirstName.getText().trim();
                String signedupLastName = textLastName.getText().trim();
                String signedUpuserName = textUserName.getText().trim();
                String signedUpEmail = textEmail.getText().trim();
                String signedUppassword = String.valueOf(passwordField.getPassword());
                String signedUpConfrimPassword = String.valueOf(confirmPasswordField.getPassword());
                String signedUpPhone = textPhone.getText().trim();
                String signedUpShippingAddress = textShippingAddress.getText().trim();
                boolean isDataValid = true;

                if (StringUtils.isEmptyOrWhitespaceOnly(signedupFirstName) || StringUtils.isEmptyOrWhitespaceOnly(signedupLastName) ||
                        StringUtils.isEmptyOrWhitespaceOnly(signedUpEmail) || StringUtils.isEmptyOrWhitespaceOnly(signedUpuserName) ||
                        StringUtils.isEmptyOrWhitespaceOnly(signedUpPhone) || StringUtils.isEmptyOrWhitespaceOnly(signedUpShippingAddress) ||
                        StringUtils.isEmptyOrWhitespaceOnly(signedUppassword) || StringUtils.isEmptyOrWhitespaceOnly(signedUpConfrimPassword)) {
                	lblErrorMessage.setText("** You must fill out required entries!");// error message "You must fill out password entries!"
                } else{
                    if (!signedUpConfrimPassword.equals(signedUppassword)) {
                    	lblErrorMessage.setText("** Two passwords mismatch!");
                        // error message "The two passwords mismatch!"
                        System.out.println("w mismatch");

                        isDataValid = false;
                    }
                    if (!isValidEmail(signedUpEmail)) {
                    	lblErrorMessage.setText("** You must enter a valid email!");
                        // error message "You must enter a vaild email format!"
                        System.out.println("w email");
                        isDataValid = false;
                    }
                    if (!isValidPhoneNo(signedUpPhone)) {
                    	lblErrorMessage.setText("** You must enter a valid phone number!");
                    	// error message "You must enter a valid 10 digits phone number!"
                        System.out.println("w phone");
                        isDataValid = false;
                    }
                    if(isDataValid){
                        UserDataDAO userDataSignUp = new UserDataDAO();
                        SignInScreen signInScreen = new SignInScreen(mainWindow);
                        User signUpUser = new User(signedUpuserName,signedUppassword,signedUpEmail,33,false,signedupFirstName,signedupLastName
                        ,signedUpPhone,signedUpShippingAddress);
                        userDataSignUp.addUser(signUpUser);
                        textEmail.setText("");
                        textFirstName.setText("");
                        textLastName.setText("");
                        textPhone.setText("");
                        textShippingAddress.setText("");
                        textUserName.setText("");
                        setVisible(false);
                        mainWindow.natavigateToPanel(mainWindow.signInScreen);
                    }
                }

            }
        });

    }
}
