package Forms;

import dao.CategoryDataDAO;
import dao.PublisherDataDAO;
import models.Book;
import models.Cart;
import models.CartItem;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;

public class CartTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 1L;
    private static final String[] COLUMN_NAMES = new String[]{"ISBN", "Title", "Price", "In Stock", "Copies","Total item Price", "remove from Cart"};
    private static final Class<?>[] COLUMN_TYPES = new Class<?>[]{Long.class, String.class, Float.class, Integer.class, Integer.class,Float.class, JButton.class};
    private Cart cart;
    private int totalOrders ;
    public CartTableModel(Cart cart) {
        this.cart = cart;
    }

    public void deleteCartItem(int rowIndex) {
        if (this.cart.getItems().remove(this.cart.getItems().get(rowIndex))) {
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        /*Adding components*/
        switch (columnIndex) {
            case 0: return cart.getItems().get(rowIndex).getBook().getIsbn();
            case 1: return cart.getItems().get(rowIndex).getBook().getTitle();
            case 2: return cart.getItems().get(rowIndex).getBook().getPrice();
            case 3: return cart.getItems().get(rowIndex).getBook().getCopiesNumber();
            case 4: return cart.getItems().get(rowIndex).getQuantity();
            case 5: return cart.getItems().get(rowIndex).getQuantity()*cart.getItems().get(rowIndex).getBook().getPrice();
            case 6: final JButton buttonRemoveItem = new JButton(COLUMN_NAMES[columnIndex]);
                buttonRemoveItem.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent arg0) {
                        System.out.println("clicked");
                    }
                });
                return buttonRemoveItem;
            default: return "Error";
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        return COLUMN_NAMES[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return COLUMN_TYPES[columnIndex];
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public int getRowCount() {
        return cart.getItems().size();
    }
}