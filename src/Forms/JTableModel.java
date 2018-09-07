package Forms;

import models.Book;
import dao.*;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.EventObject;
import java.util.List;

public class JTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 1L;
    private static final String[] COLUMN_NAMES = new String[] {"ISBN", "Title", "Category","PublicationDate",  "Price", "Publisher","Modify","Add to Cart"};
    private static final Class<?>[] COLUMN_TYPES = new Class<?>[] {Integer.class, String.class,String.class,Date.class,Float.class,String.class, JButton.class,  JButton.class};
    private List<Book> bookList;
    private MainView mainView;
    public JTableModel(List<Book> bookList, MainView mainView) {
        this.bookList=bookList;
        this.mainView = mainView;
    }
    @Override public int getColumnCount() {
        if (mainView.userSignedIn.isManager()) 
        	return COLUMN_NAMES.length;
        return COLUMN_NAMES.length - 1;
        
    }

    @Override public int getRowCount() {
        return bookList.size();
    }

    @Override public String getColumnName(int columnIndex) {
    	 if (!mainView.userSignedIn.isManager() && columnIndex == 6) 
    		 return COLUMN_NAMES[columnIndex + 1];
    	 return COLUMN_NAMES[columnIndex];
    }

    @Override public Class<?> getColumnClass(int columnIndex) {
        return COLUMN_TYPES[columnIndex];
    }

    @Override public Object getValueAt(final int rowIndex, final int columnIndex) {
        /*Adding components*/
    	int count = columnIndex;
        switch (columnIndex) {
            case 0: return bookList.get(rowIndex).getIsbn();
            case 1: return bookList.get(rowIndex).getTitle();
            case 2: return CategoryDataDAO.getCategoryName(bookList.get(rowIndex).getCid());
            case 3: return bookList.get(rowIndex).getPublicationDate();
            case 4: return bookList.get(rowIndex).getPrice();
            case 5: return PublisherDataDAO.getPublisherName(bookList.get(rowIndex).getPid());
            case 6: final JButton buttonModify = new JButton(COLUMN_NAMES[columnIndex]);
                buttonModify.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent arg0) {
                        System.out.println("clicked");
                    }
                });
                if (mainView.userSignedIn.isManager())
                	return buttonModify;
                else 
                	count++; 
                
            case 7:
            	final JButton buttonAddCart;
            	if (mainView.userSignedIn.isManager()) {
            		buttonAddCart = new JButton(COLUMN_NAMES[columnIndex]);
            	} else {
            		buttonAddCart = new JButton(COLUMN_NAMES[count]);
            	}
                buttonAddCart.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent arg0) {
                        System.out.println("clicked");
                    }
                });
                return buttonAddCart;
            default: return "Error";
        }
    }
}
