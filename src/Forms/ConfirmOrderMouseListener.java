package Forms;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import dao.OrderDataDAO;

public class ConfirmOrderMouseListener extends MouseAdapter {
    private final JTable table;
    private ConfirmOrderScreen confirmOrderScreen;
    public ConfirmOrderMouseListener(JTable table, ConfirmOrderScreen confirmOrderScreen) {
        this.table = table;
        this.confirmOrderScreen = confirmOrderScreen;
    }

    public void mouseClicked(MouseEvent e) {
        int column = table.getColumnModel().getColumnIndexAtX(e.getX()); // get the coloum of the button
        int row    = e.getY()/table.getRowHeight(); //get the row of the button

        /*Checking the row or column is valid or not*/
        if (row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0) {
            Object value = table.getValueAt(row, column);
            if (value instanceof JButton) {
                /*perform a click event*/
                OrderTableModel model=(OrderTableModel) table.getModel();
                String error = OrderDataDAO.deleteBookOrder((Long)table.getValueAt(row, 0),
                		(Timestamp)table.getValueAt(row, 1));
                if (error != null) {
                	confirmOrderScreen.showMessage(error);
                } else {
                	model.deleteOrderItem(row);
                	JOptionPane.showMessageDialog(null, "Book Order confirmed Successfully!");
                }
                
            }
        }
    }
}
