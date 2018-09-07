package Forms;



import javax.swing.*;

import dao.BookDataDAO;
import models.Book;
import models.CartItem;

import java.awt.event.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JTableButtonMouseListener extends MouseAdapter {
    private final JTable table;
    private MainView mainView;
    public JTableButtonMouseListener(JTable table ,MainView mainView) {
        this.table = table;
        this.mainView = mainView;
    }

    public void mouseClicked(MouseEvent e) {
        int column = table.getColumnModel().getColumnIndexAtX(e.getX()); // get the coloum of the button
        int row    = e.getY()/table.getRowHeight(); //get the row of the button

        /*Checking the row or column is valid or not*/
        if (row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0) {
            Object value = table.getValueAt(row, column);
            if (value instanceof JButton) {
                /*perform a click event*/
               	if(table.getColumnName(column)=="Modify"){
               		if(mainView.userSignedIn.isManager()) {
               			mainView.curBook = BookDataDAO.findBook(((long)table.getValueAt(row, 0)));
               			mainView.bookSearchScreen.setVisible(false);
               			mainView.natavigateToPanel(mainView.editBookScreen);
               		} else {
               			((JButton)value).setVisible(false);
               		}
               	} else {
               		boolean canAddBook = true;
               		boolean isaddedBefore = false;
               		JFrame frame = new JFrame();
               		Long addedBookISBN=(Long)table.getValueAt(row, 0);;
               		CartItem addedCartItem = new CartItem(null, 0);
               		//check if book has been added to cart or not
               		for (int i=0 ; i<mainView.userCart.getItems().size();i++) {
               			addedCartItem =mainView.userCart.getItems().get(i);
                			if(addedBookISBN.equals(addedCartItem.getBook().getIsbn())) {
                				isaddedBefore=true;
                				break;
                			}
               		}
               		if(isaddedBefore) {
  					  int reply = JOptionPane.showConfirmDialog(frame, "You've added "
               		+String.valueOf(addedCartItem.getQuantity())+ " Copies \n Do you want to change number of Copies ?", "Number Of Copies update", JOptionPane.YES_NO_OPTION);
	  					if (reply == JOptionPane.YES_OPTION) {
	  						canAddBook=true;
	  					}else {
	  						canAddBook=false;
	  					}
               		}
               		if(canAddBook) {
               		Object result = JOptionPane.showInputDialog(frame, "Enter Number of Copies :");
               		if (result != null) {
                		Pattern pattern = Pattern.compile("^[0-9]+$");
                		Matcher matcher = pattern.matcher(result.toString().trim());
    		        	if (matcher.matches()) {
    		        		int quantity = Integer.valueOf(result.toString().trim());
    		        		if (quantity <= BookDataDAO.findBook(((Long)table.getValueAt(row, 0))).getCopiesNumber() ) {
    		        			//TODO
    		        			if(isaddedBefore) {
                                	mainView.userCart.changeQuantity(addedCartItem.getBook().getIsbn(),quantity);
                                	JOptionPane.showMessageDialog(null, "Updated Book Copies sucessfully!");
    		        			}
    		        			else {
                            	CartItem  addeditem = new CartItem(BookDataDAO.findBook((Long) table.getValueAt(row, 0)),quantity);
                            	mainView.userCart.addToCart(addeditem.getBook());
                            	mainView.userCart.changeQuantity(addeditem.getBook().getIsbn(),addeditem.getQuantity());
    		        			JOptionPane.showMessageDialog(null, "Book added to Cart Successfully!");
    		        			}
    		        		} else {
    		        			JOptionPane.showMessageDialog(null, "Number of Books Needed is out of stock!");
    		        		}
    		        	} else {
    		        		JOptionPane.showMessageDialog(null, "Invalid number given!");
    		        	}
                	}
                }
               	}

           }
//                    clickedButton.addActionListener(new ActionListener() {
//                        @Override
//                        public void actionPerformed(ActionEvent e) {
//                            System.out.println("hiiiiiiii");
//                            mainView.bookSearchScreen.setVisible(false);
//                            mainView.natavigateToPanel(mainView.addOrderScreen);
//                        }
//                    });
                }
            }
        }
        
