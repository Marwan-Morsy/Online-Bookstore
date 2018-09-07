package Forms;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CartButtonMouseListener extends MouseAdapter {
    private final JTable table;
    private MainView mainView;
    public CartButtonMouseListener(JTable table ,MainView mainView) {
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
                CartTableModel model=(CartTableModel) table.getModel();
                mainView.userCart.changeQuantity((Long) table.getValueAt(row, 0), 0);
                model.deleteCartItem(row);
                
                
            }
        }
    }
}
