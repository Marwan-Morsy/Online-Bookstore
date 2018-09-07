package Forms;

import models.Book;
import models.Cart;
import models.Publisher;
import models.User;

import java.awt.Color;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView extends JFrame {
    public  SignInScreen signInScreen;
    public  SignUpScreen signUpScreen;
    public  BookSearchScreen bookSearchScreen;
    public  EditProfileScreen editProfileScreen;
    public  MyCartScreen myCartScreen;
    public  UpgradeUserScreen upgradeUserScreen;
    public  EditBookScreen editBookScreen;
    public  AddAuthorScreen addAuthorScreen;
    public  AddBookScreen addBookScreen;
    public  AddOrderScreen addOrderScreen;
    public  AddPublisherScreen addPublisherScreen;
    public  CheckoutScreen checkoutScreen;
    public  ConfirmOrderScreen confirmOrderScreen;
    public 	ReportsScreen reportsScreen;
    public User userSignedIn;
    public Cart userCart;
    public Book curBook;

    public MainView() {
        // setting up JFrame
        setTitle("Online Book Store");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        signInScreen = new SignInScreen(this);
        signUpScreen = new SignUpScreen(this);
        setBounds(100, 100, 941, 484);
        confirmOrderScreen = new ConfirmOrderScreen(this);
        editProfileScreen = new EditProfileScreen(this);
       // myCartScreen = new MyCartScreen(this);
        upgradeUserScreen = new UpgradeUserScreen(this);
        editBookScreen = new EditBookScreen(this);
        addAuthorScreen = new AddAuthorScreen(this);
        addBookScreen = new AddBookScreen(this);
        addOrderScreen = new AddOrderScreen(this);
        addPublisherScreen = new AddPublisherScreen(this);
       // checkoutScreen = new CheckoutScreen(this);
        userCart = new Cart();
        setVisible(true);
        natavigateToPanel(signInScreen);
    }


    public static void main(String[] arguments) {

    	
        MainView frame = new MainView();
    }
    public  void natavigateToPanel(JPanel screenPanel){
        if(screenPanel == signInScreen)
            this.signInScreen.setVisible(true);
        else if(screenPanel == signUpScreen)
            this.signUpScreen.setVisible(true);
        else if (screenPanel == editProfileScreen ){
            this.editProfileScreen.initialUserInfo();
            this.editProfileScreen.setVisible(true);
        }
        else if (screenPanel == bookSearchScreen) {
            this.bookSearchScreen.setVisible(true);
        }
        else if (screenPanel == myCartScreen)
            this.myCartScreen.setVisible(true);
        else if (screenPanel == upgradeUserScreen)
            this.upgradeUserScreen.setVisible(true);
        else if (screenPanel == editBookScreen) {
        	this.editBookScreen.setCurBook(curBook);
            this.editBookScreen.setVisible(true);
        }
        else if (screenPanel == addAuthorScreen)
            this.addAuthorScreen.setVisible(true);
        else if (screenPanel == addBookScreen)
            this.addBookScreen.setVisible(true);
        else if (screenPanel == addOrderScreen)
            this.addOrderScreen.setVisible(true);
        else if (screenPanel == addPublisherScreen)
            this.addPublisherScreen.setVisible(true);
        else if (screenPanel == checkoutScreen)
            this.checkoutScreen.setVisible(true);
        else if (screenPanel == confirmOrderScreen) {
        	this.confirmOrderScreen.setVisible(true);
        	this.confirmOrderScreen.start();
        }
        else if (screenPanel == reportsScreen) {
        	this.reportsScreen.setVisible(true);
        }
        setContentPane(screenPanel);
    }
}

