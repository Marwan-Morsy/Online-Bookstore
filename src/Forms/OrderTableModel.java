package Forms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.List;

import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;


import models.Order;

public class OrderTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 1L;
    private static final String[] COLUMN_NAMES = new String[]{"ISBN", "TimeRequested", "Quantity", "Confirm Order"};
    private static final Class<?>[] COLUMN_TYPES = new Class<?>[]{Long.class, Timestamp.class, Integer.class, JButton.class};
    private int totalOrders ;
    private List<Order> orders;
    public OrderTableModel(List<Order> orders) {
        this.orders = orders;
    }

    public void deleteOrderItem(int rowIndex) {
    	orders.remove(rowIndex);
    	this.fireTableRowsDeleted(rowIndex, rowIndex);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        /*Adding components*/
        switch (columnIndex) {
            case 0: return orders.get(rowIndex).getIsbn();
            case 1: return orders.get(rowIndex).getTimeRequested();
            case 2: return orders.get(rowIndex).getQuantity();
            case 3: final JButton buttonConfirmItem = new JButton(COLUMN_NAMES[columnIndex]);
                buttonConfirmItem.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent arg0) {
                        System.out.println("clicked");
                    }
                });
                return buttonConfirmItem;
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
        return orders.size();
    }

}
