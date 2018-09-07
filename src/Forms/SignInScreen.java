package Forms;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Dimension;

import com.mysql.cj.util.StringUtils;
import dao.UserDataDAO;
import models.User;
import dao.UserDataDAO;
public class SignInScreen extends JPanel {

	private JPasswordField passwordField;
	private JButton btnSignUp;
	private JButton btnLogin;
	private JLabel lblWelcomeLogin;
	private JLabel lblUsername;
	private JTextField textUsernameField;
	private JLabel lblPassword;
	private JLabel lblErrorMessage;

	/**
	 * Launch the application.
	 */
	private MainView mainWindow ;
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					SignInScreen frame = new SignInScreen();
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
	public SignInScreen(MainView mainWindow) {
	    this.mainWindow = mainWindow;
		setBounds(100, 100, 741, 484);
		setFont(new Font("Calibri", Font.ITALIC, 13));
		setBackground(new Color(64, 224, 208));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] {150, 150, 150};
		gbl_contentPane.rowHeights = new int[] {65, 0, 38, 35, 38, 35, 29, 40, 13, 37};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 0.0};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		setLayout(gbl_contentPane);
		
		lblWelcomeLogin = new JLabel("Welcome");
		lblWelcomeLogin.setForeground(new Color(0, 128, 0));
		lblWelcomeLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcomeLogin.setFont(new Font("Brush Script MT", Font.BOLD | Font.ITALIC, 60));
		GridBagConstraints gbc_lblWelcomeLogin = new GridBagConstraints();
		gbc_lblWelcomeLogin.fill = GridBagConstraints.BOTH;
		gbc_lblWelcomeLogin.insets = new Insets(0, 0, 5, 5);
		gbc_lblWelcomeLogin.gridx = 1;
		gbc_lblWelcomeLogin.gridy = 0;
		add(lblWelcomeLogin, gbc_lblWelcomeLogin);
		
		lblErrorMessage = new JLabel("");
		lblErrorMessage.setForeground(new Color(255, 0, 0));
		lblErrorMessage.setFont(new Font("Calibri", Font.BOLD, 18));
		GridBagConstraints gbc_lblErrorMessage = new GridBagConstraints();
		gbc_lblErrorMessage.fill = GridBagConstraints.BOTH;
		gbc_lblErrorMessage.insets = new Insets(0, 0, 5, 5);
		gbc_lblErrorMessage.gridx = 1;
		gbc_lblErrorMessage.gridy = 1;
		add(lblErrorMessage, gbc_lblErrorMessage);
		
		lblUsername = new JLabel("Username:");
		lblUsername.setMaximumSize(new Dimension(100, 50));
		lblUsername.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.fill = GridBagConstraints.BOTH;
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.gridx = 1;
		gbc_lblUsername.gridy = 2;
		add(lblUsername, gbc_lblUsername);
		
		textUsernameField = new JTextField();
		textUsernameField.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		textUsernameField.setFont(new Font("Cambria", Font.PLAIN, 14));
		GridBagConstraints gbc_textUsernameField = new GridBagConstraints();
		gbc_textUsernameField.insets = new Insets(0, 0, 5, 5);
		gbc_textUsernameField.fill = GridBagConstraints.BOTH;
		gbc_textUsernameField.gridx = 1;
		gbc_textUsernameField.gridy = 3;
		add(textUsernameField, gbc_textUsernameField);
		textUsernameField.setColumns(10);
		
		lblPassword = new JLabel("Password:");
		lblPassword.setMaximumSize(new Dimension(100, 50));
		lblPassword.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.fill = GridBagConstraints.BOTH;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 1;
		gbc_lblPassword.gridy = 4;
		add(lblPassword, gbc_lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.BOTH;
		gbc_passwordField.gridx = 1;
		gbc_passwordField.gridy = 5;
		add(passwordField, gbc_passwordField);
		
		btnLogin = new JButton("LogIn");
		btnLogin.setRequestFocusEnabled(false);
		btnLogin.setBackground(new Color(210, 105, 30));
		btnLogin.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			  String userNameSignIn =	textUsernameField.getText().trim();
			  String passwordSignIn = String.valueOf(passwordField.getPassword());
			  if( StringUtils.isEmptyOrWhitespaceOnly(passwordSignIn)||StringUtils.isEmptyOrWhitespaceOnly(userNameSignIn)){
			      // error message "You must fill out password entries!"
				  lblErrorMessage.setText("** You must fill out password entries!");
              }
              else {
				   mainWindow.userSignedIn = UserDataDAO.findUser(userNameSignIn,passwordSignIn);
                  if(mainWindow.userSignedIn ==null){
                      lblErrorMessage.setText("** Wrong username or password try again!"); //error message "Wrong username or password try again!"
                      textUsernameField.setText("");
                      passwordField.setText("");
                  }else {
                      //go to view
					  lblErrorMessage.setText("");
					  textUsernameField.setText("");
					  passwordField.setText("");
                      setVisible(false);
                      mainWindow.bookSearchScreen = new BookSearchScreen(mainWindow);
                      mainWindow.natavigateToPanel(mainWindow.bookSearchScreen);
                  }
              }

			}
		});
		btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnLogin.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_btnLogin = new GridBagConstraints();
		gbc_btnLogin.fill = GridBagConstraints.BOTH;
		gbc_btnLogin.insets = new Insets(0, 0, 5, 5);
		gbc_btnLogin.gridx = 1;
		gbc_btnLogin.gridy = 7;
		add(btnLogin, gbc_btnLogin);
		
		btnSignUp = new JButton("SignUp");
		btnSignUp.setRequestFocusEnabled(false);
		btnSignUp.setBackground(new Color(210, 105, 30));
		btnSignUp.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		btnSignUp.setFont(new Font("Calibri", Font.BOLD, 14));
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//			    setVisible(false);
//			    SignUpScreen signup = new SignUpScreen(mainWindow);
//			    signup.setVisible(true);
                lblErrorMessage.setText("");
				setVisible(false);
                mainWindow.natavigateToPanel(mainWindow.signUpScreen);
			}
		});
		GridBagConstraints gbc_btnSignUp = new GridBagConstraints();
		gbc_btnSignUp.insets = new Insets(0, 0, 0, 5);
		gbc_btnSignUp.fill = GridBagConstraints.BOTH;
		gbc_btnSignUp.gridx = 1;
		gbc_btnSignUp.gridy = 9;
		add(btnSignUp, gbc_btnSignUp);
	}
}
