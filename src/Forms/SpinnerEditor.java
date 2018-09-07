package Forms;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

class SpinnerEditor extends AbstractCellEditor implements TableCellEditor {
	   
	   JSpinner spinner;
	   public SpinnerEditor() {
	      
	   }
	  
	   public Component getTableCellEditorComponent(JTable table, Object value,
	                    boolean isSelected, int row, int column) {
		  JSpinner spinner = (JSpinner) value;
	      return spinner;
	   }
	  
	   public Object getCellEditorValue() {
	      SpinnerModel sm = spinner.getModel();
	      return sm;
	   }
	}