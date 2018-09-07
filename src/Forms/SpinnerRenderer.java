package Forms;

import java.awt.Component;

import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerModel;
import javax.swing.table.TableCellRenderer;

class SpinnerRenderer extends JSpinner implements TableCellRenderer {
	   public SpinnerRenderer() {
	      setOpaque(true);
	   }
	   
	   public Component getTableCellRendererComponent(JTable table, Object value,
	                    boolean isSelected, boolean hasFocus, int row, int column) {
	      setModel((SpinnerModel) value);
	   
	      return this;
	   }
	}