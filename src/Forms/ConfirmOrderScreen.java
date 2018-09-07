package Forms;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import com.mysql.cj.util.StringUtils;

import dao.OrderDataDAO;
import javafx.scene.control.Spinner;
import models.Cart;
import models.Order;

import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.JSpinner;
import javax.swing.JCheckBox;

public class ConfirmOrderScreen extends JPanel {
	private JTable ordersTable;
	private MainView mainView;
	private int page = 1;
	private int limit = 10;
	private int offset = 0;
	private JTextField textFieldISBN;
	private JCheckBox chckbxMinQuantity;
	private JCheckBox chckbxMaxQuantity;
	private JSpinner spinnerMinQuantity;
	private JSpinner spinnerMaxQuantity;
	private List<Order> orders;
	private JLabel lblErrorMessage;
	private OrderTableModel model;
	private JTableButtonRenderer buttonRenderer;
	private JScrollPane scrollPaneTable;
	private JLabel lblAmountOfRecordShown;
	private JLabel lblNewLabelPageNumOfAllPages;
	private GridBagConstraints gbc_scrollPane;
	private JButton btnNext;
	private JButton btnPrev;
	private AtomicInteger count = new AtomicInteger();
	private Long isbn;
	private Integer minQuantity;
	private Integer maxQuantity;
	/**
	 * Create the panel.
	 */
	public ConfirmOrderScreen(MainView mainView) {
		 this.mainView = mainView;
			setBackground(new Color(64, 224, 208));
			GridBagLayout gridBagLayout = new GridBagLayout();
			gridBagLayout.columnWidths = new int[] {30, 121, 88, 82, 71, 66, 76, 30, 0};
			gridBagLayout.rowHeights = new int[] {30, 30, 30, 30, 30, 30, 0, 30, 30, 0};
			gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
			gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
			setLayout(gridBagLayout);
			
			JLabel lblNewLabel = new JLabel("My Orders");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setForeground(new Color(0, 128, 0));
			lblNewLabel.setFont(new Font("Brush Script MT", Font.BOLD | Font.ITALIC, 60));
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.fill = GridBagConstraints.BOTH;
			gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
			gbc_lblNewLabel.gridx = 0;
			gbc_lblNewLabel.gridy = 1;
			gbc_lblNewLabel.gridwidth = 8;
			add(lblNewLabel, gbc_lblNewLabel);
			
			lblErrorMessage = new JLabel("");
			lblErrorMessage.setForeground(new Color(255, 0, 0));
			lblErrorMessage.setFont(new Font("Calibri", Font.BOLD, 14));
			GridBagConstraints gbc_lblErrorMessage = new GridBagConstraints();
			gbc_lblErrorMessage.fill = GridBagConstraints.BOTH;
			gbc_lblErrorMessage.insets = new Insets(0, 0, 5, 5);
			gbc_lblErrorMessage.gridx = 1;
			gbc_lblErrorMessage.gridy = 2;
			add(lblErrorMessage, gbc_lblErrorMessage);
			
			JButton btnClearAllOrders = new JButton("Confirm All Orders");
			btnClearAllOrders.setRequestFocusEnabled(false);
			btnClearAllOrders.setFont(new Font("Calibri", Font.BOLD, 14));
			btnClearAllOrders.setBorder(new LineBorder(new Color(0, 0, 0)));
			btnClearAllOrders.setBackground(new Color(210, 105, 30));
			GridBagConstraints gbc_btnClearAllOrders = new GridBagConstraints();
			gbc_btnClearAllOrders.fill = GridBagConstraints.BOTH;
			gbc_btnClearAllOrders.insets = new Insets(0, 0, 5, 5);
			gbc_btnClearAllOrders.gridx = 1;
			gbc_btnClearAllOrders.gridy = 3;
			//gbc_btnClearCart.gridwidth = 2;
			add(btnClearAllOrders, gbc_btnClearAllOrders);
			
			
			JButton btnBackToBookSearch = new JButton("Back");
			btnBackToBookSearch.setRequestFocusEnabled(false);
			btnBackToBookSearch.setFont(new Font("Calibri", Font.BOLD, 14));
			btnBackToBookSearch.setBorder(new LineBorder(new Color(0, 0, 0)));
			btnBackToBookSearch.setBackground(new Color(210, 105, 30));
			GridBagConstraints gbc_btnBackToBookSearch = new GridBagConstraints();
			gbc_btnBackToBookSearch.fill = GridBagConstraints.BOTH;
			gbc_btnBackToBookSearch.insets = new Insets(0, 0, 5, 5);
			gbc_btnBackToBookSearch.gridx = 6;
			gbc_btnBackToBookSearch.gridy = 3;
			//gbc_btnBackToBookSearch.gridwidth = 2;
			add(btnBackToBookSearch, gbc_btnBackToBookSearch);
			



			scrollPaneTable = new JScrollPane();
			scrollPaneTable.setRequestFocusEnabled(false);
			scrollPaneTable.setBorder(new LineBorder(new Color(0, 0, 0)));
			scrollPaneTable.setBackground(new Color(102, 205, 170));
			gbc_scrollPane = new GridBagConstraints();
			gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
			gbc_scrollPane.fill = GridBagConstraints.BOTH;
			gbc_scrollPane.gridx = 1;
			gbc_scrollPane.gridy = 6;
			gbc_scrollPane.gridwidth = 6;
			
			orders = new ArrayList<Order>();
			model = new OrderTableModel(orders);
			ordersTable = new JTable();
			ordersTable.setBackground(new Color(64, 224, 208));
			ordersTable.setShowHorizontalLines(false);
			buttonRenderer = new JTableButtonRenderer();
			ordersTable.setModel(model);
	        ordersTable.getColumn("Confirm Order").setCellRenderer(buttonRenderer);
	        ConfirmOrderMouseListener mouseClickedListener = new ConfirmOrderMouseListener(ordersTable,this);
	        ordersTable.addMouseListener(mouseClickedListener);
	        ordersTable.setRowHeight(20);
			scrollPaneTable.setViewportView(ordersTable);
	        add(scrollPaneTable, gbc_scrollPane);
	        
	        JLabel lblIsbn = new JLabel("ISBN:");
	        lblIsbn.setHorizontalAlignment(SwingConstants.LEFT);
	        lblIsbn.setFont(new Font("Calibri", Font.BOLD, 14));
	        GridBagConstraints gbc_lblIsbn = new GridBagConstraints();
	        gbc_lblIsbn.anchor = GridBagConstraints.WEST;
	        gbc_lblIsbn.fill = GridBagConstraints.VERTICAL;
	        gbc_lblIsbn.insets = new Insets(0, 0, 5, 5);
	        gbc_lblIsbn.gridx = 1;
	        gbc_lblIsbn.gridy = 4;
	        add(lblIsbn, gbc_lblIsbn);
	        
	        chckbxMinQuantity = new JCheckBox("Min Quantity");
	        chckbxMinQuantity.setRequestFocusEnabled(false);
	        chckbxMinQuantity.setBackground(new Color(64, 224, 208));
	        GridBagConstraints gbc_chckbxMinQuantity = new GridBagConstraints();
	        gbc_chckbxMinQuantity.fill = GridBagConstraints.BOTH;
	        gbc_chckbxMinQuantity.insets = new Insets(0, 0, 5, 5);
	        gbc_chckbxMinQuantity.gridx = 2;
	        gbc_chckbxMinQuantity.gridy = 4;
	        add(chckbxMinQuantity, gbc_chckbxMinQuantity);
	        
	        chckbxMaxQuantity = new JCheckBox("Max Quantity");
	        chckbxMaxQuantity.setRequestFocusEnabled(false);
	        chckbxMaxQuantity.setBackground(new Color(64, 224, 208));
	        GridBagConstraints gbc_chckbxMaxQuantity = new GridBagConstraints();
	        gbc_chckbxMaxQuantity.fill = GridBagConstraints.BOTH;
	        gbc_chckbxMaxQuantity.insets = new Insets(0, 0, 5, 5);
	        gbc_chckbxMaxQuantity.gridx = 3;
	        gbc_chckbxMaxQuantity.gridy = 4;
	        add(chckbxMaxQuantity, gbc_chckbxMaxQuantity);
	        
	        textFieldISBN = new JTextField();
	        textFieldISBN.setBorder(new LineBorder(new Color(0, 0, 0)));
	        textFieldISBN.setHorizontalAlignment(SwingConstants.CENTER);
	        textFieldISBN.setFont(new Font("Calibri", Font.PLAIN, 14));
	        GridBagConstraints gbc_textFieldISBN = new GridBagConstraints();
	        gbc_textFieldISBN.insets = new Insets(0, 0, 5, 5);
	        gbc_textFieldISBN.fill = GridBagConstraints.BOTH;
	        gbc_textFieldISBN.gridx = 1;
	        gbc_textFieldISBN.gridy = 5;
	        add(textFieldISBN, gbc_textFieldISBN);
	        textFieldISBN.setColumns(10);
	        
	        spinnerMinQuantity = new JSpinner();
	        spinnerMinQuantity.setBackground(new Color(64, 224, 208));
	        GridBagConstraints gbc_spinnerMinQuantity = new GridBagConstraints();
	        gbc_spinnerMinQuantity.fill = GridBagConstraints.BOTH;
	        gbc_spinnerMinQuantity.insets = new Insets(0, 0, 5, 5);
	        gbc_spinnerMinQuantity.gridx = 2;
	        gbc_spinnerMinQuantity.gridy = 5;
	        add(spinnerMinQuantity, gbc_spinnerMinQuantity);
	        
	        spinnerMaxQuantity = new JSpinner();
	        spinnerMaxQuantity.setBackground(new Color(64, 224, 208));
	        GridBagConstraints gbc_spinnerMaxQuantity = new GridBagConstraints();
	        gbc_spinnerMaxQuantity.fill = GridBagConstraints.BOTH;
	        gbc_spinnerMaxQuantity.insets = new Insets(0, 0, 5, 5);
	        gbc_spinnerMaxQuantity.gridx = 3;
	        gbc_spinnerMaxQuantity.gridy = 5;
	        add(spinnerMaxQuantity, gbc_spinnerMaxQuantity);
	        
	        JButton btnSearch = new JButton("Search");
	        
	        btnSearch.setRequestFocusEnabled(false);
	        btnSearch.setFont(new Font("Calibri", Font.BOLD, 14));
	        btnSearch.setBorder(new LineBorder(new Color(0, 0, 0)));
	        btnSearch.setBackground(new Color(210, 105, 30));
	        GridBagConstraints gbc_btnSearch = new GridBagConstraints();
	        gbc_btnSearch.fill = GridBagConstraints.BOTH;
	        gbc_btnSearch.insets = new Insets(0, 0, 5, 5);
	        gbc_btnSearch.gridx = 6;
	        gbc_btnSearch.gridy = 5;
	        add(btnSearch, gbc_btnSearch);
	        
			
			JLabel lblNewLabelpage = new JLabel("Page:");
			lblNewLabelpage.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabelpage.setFont(new Font("Calibri", Font.BOLD, 14));
			GridBagConstraints gbc_lblNewLabelpage = new GridBagConstraints();
			gbc_lblNewLabelpage.fill = GridBagConstraints.BOTH;
			gbc_lblNewLabelpage.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabelpage.gridx = 1;
			gbc_lblNewLabelpage.gridy = 7;
			add(lblNewLabelpage, gbc_lblNewLabelpage);
			
			lblNewLabelPageNumOfAllPages = new JLabel("");
			if (orders.size() == 0) {
				lblNewLabelPageNumOfAllPages.setText("1 Of 1");
			} else {
				lblNewLabelPageNumOfAllPages.setText(String.valueOf(page) +
					" Of " + String.valueOf((int) Math.ceil(orders.size() / (float) limit)));
			}
			lblNewLabelPageNumOfAllPages.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabelPageNumOfAllPages.setFont(new Font("Calibri", Font.PLAIN, 14));
			GridBagConstraints gbc_lblNewLabelPageNumOfAllPages = new GridBagConstraints();
			gbc_lblNewLabelPageNumOfAllPages.fill = GridBagConstraints.BOTH;
			gbc_lblNewLabelPageNumOfAllPages.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabelPageNumOfAllPages.gridx = 2;
			gbc_lblNewLabelPageNumOfAllPages.gridy = 7;
			add(lblNewLabelPageNumOfAllPages, gbc_lblNewLabelPageNumOfAllPages);
			
			
			btnPrev = new JButton("<");
			btnPrev.setRequestFocusEnabled(false);
			btnPrev.setFont(new Font("Calibri", Font.BOLD, 14));
			btnPrev.setEnabled(false);
			btnPrev.setBorder(new LineBorder(new Color(0, 0, 0)));
			btnPrev.setBackground(new Color(210, 105, 30));
			
			GridBagConstraints gbc_btnPrev = new GridBagConstraints();
			gbc_btnPrev.fill = GridBagConstraints.BOTH;
			gbc_btnPrev.insets = new Insets(0, 0, 5, 5);
			gbc_btnPrev.gridx = 4;
			gbc_btnPrev.gridy = 7;
			//gbc_button.gridwidth = 2;
			add(btnPrev, gbc_btnPrev);
			
			lblAmountOfRecordShown = new JLabel(offset 
					+ "/" + orders.size());
			lblAmountOfRecordShown.setHorizontalAlignment(SwingConstants.CENTER);
			GridBagConstraints gbc_lblAmountOfRecordShown = new GridBagConstraints();
			gbc_lblAmountOfRecordShown.fill = GridBagConstraints.BOTH;
			gbc_lblAmountOfRecordShown.insets = new Insets(0, 0, 5, 5);
			gbc_lblAmountOfRecordShown.gridx = 5;
			gbc_lblAmountOfRecordShown.gridy = 7;
			add(lblAmountOfRecordShown, gbc_lblAmountOfRecordShown);
			
			btnNext = new JButton(">");
			btnNext.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					offset += orders.size();
					page++;
					orders = OrderDataDAO.searchBookOrder(isbn, minQuantity,
							maxQuantity, null, null, limit, offset, null, count);
					model = new OrderTableModel(orders);
					ordersTable.setModel(model);
			        ordersTable.getColumn("Confirm Order").setCellRenderer(buttonRenderer);
			        ordersTable.setRowHeight(20);
					scrollPaneTable.setViewportView(ordersTable);
			        add(scrollPaneTable, gbc_scrollPane);
			        lblNewLabelPageNumOfAllPages.setText(String.valueOf(page) +
							" Of " + String.valueOf((int)Math.ceil(count.get() / (float) limit)));
			        lblAmountOfRecordShown.setText(String.valueOf(offset + orders.size()) 
			        		+ "/" + String.valueOf(count.get()) );
			        if (offset >= orders.size()) {
			        	btnNext.setEnabled(false);
			        }
			        btnPrev.setEnabled(true);
				}
			});
			btnNext.setRequestFocusEnabled(false);
			btnNext.setFont(new Font("Calibri", Font.BOLD, 14));
			btnNext.setBorder(new LineBorder(new Color(0, 0, 0)));
			btnNext.setBackground(new Color(210, 105, 30));
			GridBagConstraints gbc_btnNext = new GridBagConstraints();
			gbc_btnNext.fill = GridBagConstraints.BOTH;
			gbc_btnNext.insets = new Insets(0, 0, 5, 5);
			gbc_btnNext.gridx = 6;
			gbc_btnNext.gridy = 7;
			//gbc_button_1.gridwidth = 2;
			add(btnNext, gbc_btnNext);
			
			
			btnBackToBookSearch.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	textFieldISBN.setText("");
	            	spinnerMaxQuantity.resetKeyboardActions();
	            	spinnerMinQuantity.resetKeyboardActions();
	            	lblErrorMessage.setText("");
	                setVisible(false);
	                mainView.natavigateToPanel(mainView.bookSearchScreen);
	            }
	        });
			
			
			btnPrev.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					offset -= limit;
					page--;
					orders = OrderDataDAO.searchBookOrder(isbn, minQuantity,
							maxQuantity, null, null, limit, offset, null, count);
					model = new OrderTableModel(orders);
					ordersTable.setModel(model);
					ordersTable.getColumn("Confirm Order").setCellRenderer(buttonRenderer);
			        ordersTable.setRowHeight(20);
					scrollPaneTable.setViewportView(ordersTable);
			        add(scrollPaneTable, gbc_scrollPane);
			        lblNewLabelPageNumOfAllPages.setText(String.valueOf(page) +
							" Of " + String.valueOf((int)Math.ceil(count.get() / (float) limit)));
			        lblAmountOfRecordShown.setText(String.valueOf(offset + orders.size())
			        		+ "/" + String.valueOf(count));
			        if ((offset - orders.size()) <= 0 ) {
			        	btnPrev.setEnabled(false);
			        }
			        btnNext.setEnabled(true);
				}
			});
			

			btnClearAllOrders.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	textFieldISBN.setText("");
	            	orders = OrderDataDAO.searchBookOrder(null, null, null, null, null, limit, offset, null, count);
	            	for (int i = 0; i < orders.size(); i++) {
	            		OrderDataDAO.deleteBookOrder(orders.get(i).getIsbn(),
	            				orders.get(i).getTimeRequested());
	            	}
	            	orders.clear();
	            	model = new OrderTableModel(orders);
        			ordersTable.setModel(model);
        	        ordersTable.getColumn("Confirm Order").setCellRenderer(buttonRenderer);
        	        scrollPaneTable.setViewportView(ordersTable);
        	        add(scrollPaneTable, gbc_scrollPane);
	                lblAmountOfRecordShown.setText("0/0");
	                lblNewLabelPageNumOfAllPages.setText("1/1");
	                lblErrorMessage.setText("No Available Orders!");
	            }
	        });
			
			btnSearch.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent arg0) {
	        		boolean isDataValid = true;
	        		String searchedIsbn = textFieldISBN.getText().trim();
	        		
	        		Pattern pattern = Pattern.compile("^[0-9]+$");
			        Matcher matcher = pattern.matcher(searchedIsbn);
	        		
	        		
	        		offset = 0;
	        		page = 1;
	        		if (chckbxMinQuantity.isSelected()) {
	        			minQuantity = (Integer)spinnerMinQuantity.getModel().getValue();
	        		} else {
	        			minQuantity = null;
	        		}
	        		if (chckbxMaxQuantity.isSelected()) {
	        			maxQuantity = (Integer)spinnerMaxQuantity.getModel().getValue();
	        		} else {
	        			maxQuantity = null;
	        		}
	        		if (searchedIsbn.isEmpty()) {
	        			isbn = null;	
					} else if (!matcher.matches()) {
						lblErrorMessage.setText("** Invalid ISBN!");
						isDataValid = false;
					} else {
						isbn = Long.valueOf(searchedIsbn);
					}
	        		if (isDataValid) {
	        			orders = OrderDataDAO.searchBookOrder(isbn, minQuantity, maxQuantity,
	        					null, null, limit, offset, null, count);
	        			model = new OrderTableModel(orders);
	        			ordersTable.setModel(model);
	        	        ordersTable.getColumn("Confirm Order").setCellRenderer(buttonRenderer);
	        	        lblAmountOfRecordShown.setText(String.valueOf(offset + orders.size()) + "/" + String.valueOf(count));
						if (count.get() == 0 ) {
							lblNewLabelPageNumOfAllPages.setText("1 Of 1");
						} else {
							lblNewLabelPageNumOfAllPages.setText(String.valueOf(page) + " Of " + String.valueOf((int)Math.ceil(count.get() / (float)limit)));
						}
						btnNext.setEnabled(true);
	        			scrollPaneTable.setViewportView(ordersTable);
	        	        add(scrollPaneTable, gbc_scrollPane);
	        	        if ((offset + orders.size()) >= count.get()) 
		                	btnNext.setEnabled(false);
	        	        if (orders.size() == 0) {
	        	        	lblErrorMessage.setText("No results to show!");
	        	        } else {
	        	        	lblErrorMessage.setText("");
	        	        }
	        		} 
	        	}
	        });
			
		}
	public void showMessage(String error) {
		lblErrorMessage.setText("** " + error + "!");
		// TODO Auto-generated method stub
	}
	
	public void start() {
		offset = 0;
		page = 1;
		limit = 10;
		count = new AtomicInteger();
		orders = OrderDataDAO.searchBookOrder(null, null, null, null, null, limit, offset, null, count);
		model = new OrderTableModel(orders);
		ordersTable.setModel(model);
        ordersTable.getColumn("Confirm Order").setCellRenderer(buttonRenderer);
        ordersTable.setRowHeight(20);
		scrollPaneTable.setViewportView(ordersTable);
        add(scrollPaneTable, gbc_scrollPane);
        if (count.get() == 0 ) {
			lblNewLabelPageNumOfAllPages.setText("1 Of 1");
		} else {
			lblNewLabelPageNumOfAllPages.setText(String.valueOf(page) + " Of " + String.valueOf((int)Math.ceil(count.get() / (float)limit)));
		}
        lblAmountOfRecordShown.setText(String.valueOf(offset + orders.size()) + "/" + String.valueOf(count));
        btnNext.setEnabled(true);
        if ((offset + orders.size()) >= count.get()) 
        	btnNext.setEnabled(false);
        if (orders.size() == 0) {
        	lblErrorMessage.setText("No results to show!");
        } else {
        	lblErrorMessage.setText("");
        }
        
	}

}
