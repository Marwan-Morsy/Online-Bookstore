package Forms;

import models.Cart;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MyCartScreen extends JPanel {
	private JTable table;
	private MainView mainView;
	private int page = 1;
	private int limit = 10;
	private int offset = 0;
	private Cart cart;
	private Cart tempCart;

	/**
	 * Create the panel.
	 */
	public MyCartScreen(MainView mainView) {
	    this.mainView = mainView;
	    this.cart = mainView.userCart;
	    this.tempCart = new Cart();
		setBackground(new Color(64, 224, 208));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {30, 40, 40, 40, 40, 40, 60, 68, 40, 30, 0};
		gridBagLayout.rowHeights = new int[] {30, 30, 30, 30, 30, 30, 0, 30, 30, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblNewLabel = new JLabel("My Cart");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(0, 128, 0));
		lblNewLabel.setFont(new Font("Brush Script MT", Font.BOLD | Font.ITALIC, 60));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		gbc_lblNewLabel.gridwidth = 10;
		add(lblNewLabel, gbc_lblNewLabel);
		
		JButton btnToCheckout = new JButton("To Checkout");
		btnToCheckout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
                mainView.checkoutScreen = new CheckoutScreen(mainView);
				mainView.natavigateToPanel(mainView.checkoutScreen);
			}
		});
		btnToCheckout.setRequestFocusEnabled(false);
		btnToCheckout.setFont(new Font("Calibri", Font.BOLD, 14));
		btnToCheckout.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnToCheckout.setBackground(new Color(210, 105, 30));
		GridBagConstraints gbc_btnToCheckout = new GridBagConstraints();
		gbc_btnToCheckout.fill = GridBagConstraints.BOTH;
		gbc_btnToCheckout.insets = new Insets(0, 0, 5, 5);
		gbc_btnToCheckout.gridx = 1;
		gbc_btnToCheckout.gridy = 3;
		//gbc_btnToCheckout.gridwidth = 2;
		add(btnToCheckout, gbc_btnToCheckout);
		
		JButton btnBackToBookSearch = new JButton("Back");
		btnBackToBookSearch.setRequestFocusEnabled(false);
		btnBackToBookSearch.setFont(new Font("Calibri", Font.BOLD, 14));
		btnBackToBookSearch.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnBackToBookSearch.setBackground(new Color(210, 105, 30));
		GridBagConstraints gbc_btnBackToBookSearch = new GridBagConstraints();
		gbc_btnBackToBookSearch.fill = GridBagConstraints.BOTH;
		gbc_btnBackToBookSearch.insets = new Insets(0, 0, 5, 5);
		gbc_btnBackToBookSearch.gridx = 8;
		gbc_btnBackToBookSearch.gridy = 3;
		//gbc_btnBackToBookSearch.gridwidth = 2;
		add(btnBackToBookSearch, gbc_btnBackToBookSearch);
		
		JLabel lblErrorMessage = new JLabel("");
		lblErrorMessage.setForeground(new Color(255, 0, 0));
		lblErrorMessage.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_lblErrorMessage = new GridBagConstraints();
		gbc_lblErrorMessage.fill = GridBagConstraints.BOTH;
		gbc_lblErrorMessage.insets = new Insets(0, 0, 5, 5);
		gbc_lblErrorMessage.gridx = 1;
		gbc_lblErrorMessage.gridy = 4;
		add(lblErrorMessage, gbc_lblErrorMessage);
		if (cart.getItems().size() == 0) {
			lblErrorMessage.setText("My Cart is Empty!");
		}
		
		JLabel lblTotalPriceTitle = new JLabel("Total Price:");
		lblTotalPriceTitle.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_lblTotalPriceTitle = new GridBagConstraints();
		gbc_lblTotalPriceTitle.anchor = GridBagConstraints.WEST;
		gbc_lblTotalPriceTitle.fill = GridBagConstraints.VERTICAL;
		gbc_lblTotalPriceTitle.insets = new Insets(0, 0, 5, 5);
		gbc_lblTotalPriceTitle.gridx = 1;
		gbc_lblTotalPriceTitle.gridy = 5;
		add(lblTotalPriceTitle, gbc_lblTotalPriceTitle);
		
		JLabel lblTotalPrice = new JLabel();
		
		lblTotalPrice.setText(String.valueOf(cart.getTotalPrice()));
		lblTotalPrice.setHorizontalAlignment(SwingConstants.LEFT);
		lblTotalPrice.setFont(new Font("Calibri", Font.PLAIN, 14));
		GridBagConstraints gbc_lblTotalPrice = new GridBagConstraints();
		gbc_lblTotalPrice.fill = GridBagConstraints.BOTH;
		gbc_lblTotalPrice.insets = new Insets(0, 0, 5, 5);
		gbc_lblTotalPrice.gridx = 2;
		gbc_lblTotalPrice.gridy = 5;
		add(lblTotalPrice, gbc_lblTotalPrice);
		
		JButton btnClearCart = new JButton("Clear Cart");
		btnClearCart.setRequestFocusEnabled(false);
		btnClearCart.setFont(new Font("Calibri", Font.BOLD, 14));
		btnClearCart.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnClearCart.setBackground(new Color(210, 105, 30));
		GridBagConstraints gbc_btnClearCart = new GridBagConstraints();
		gbc_btnClearCart.fill = GridBagConstraints.BOTH;
		gbc_btnClearCart.insets = new Insets(0, 0, 5, 5);
		gbc_btnClearCart.gridx = 8;
		gbc_btnClearCart.gridy = 5;
		//gbc_btnClearCart.gridwidth = 2;
		add(btnClearCart, gbc_btnClearCart);



		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane.setBackground(new Color(102, 205, 170));
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 6;
		gbc_scrollPane.gridwidth = 8;
		
		
		int prev = offset;
		for ( ;(offset < (prev + limit) ) && (offset < mainView.userCart.getItems().size()); offset++) {
			tempCart.addToCart(mainView.userCart.getItems().get(offset).getBook());
			tempCart.changeQuantity(mainView.userCart.getItems().get(offset).getBook().getIsbn(),
					mainView.userCart.getItems().get(offset).getQuantity());
		}
		table = new JTable(new CartTableModel(tempCart));
		JTableButtonRenderer buttonRenderer = new JTableButtonRenderer();
        table.getColumn("remove from Cart").setCellRenderer(buttonRenderer);
        CartButtonMouseListener mouseClickedListener = new CartButtonMouseListener(table,mainView);
        table.addMouseListener(mouseClickedListener);
        table.setRowHeight(20);
		scrollPane.setViewportView(table);
        add(scrollPane, gbc_scrollPane);
		
		JLabel lblNewLabelpage = new JLabel("Page:");
		lblNewLabelpage.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabelpage.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewLabelpage = new GridBagConstraints();
		gbc_lblNewLabelpage.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabelpage.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabelpage.gridx = 1;
		gbc_lblNewLabelpage.gridy = 7;
		add(lblNewLabelpage, gbc_lblNewLabelpage);
		
		JLabel lblNewLabelPageNumOfAllPages = new JLabel("");
		if (cart.getItems().size() == 0) {
			lblNewLabelPageNumOfAllPages.setText("1 Of 1");
		} else {
			lblNewLabelPageNumOfAllPages.setText(String.valueOf(page) +
				" Of " + String.valueOf((int) Math.ceil(cart.getItems().size() / (float) limit)));
		}
		lblNewLabelPageNumOfAllPages.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabelPageNumOfAllPages.setFont(new Font("Calibri", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabelPageNumOfAllPages = new GridBagConstraints();
		gbc_lblNewLabelPageNumOfAllPages.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabelPageNumOfAllPages.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabelPageNumOfAllPages.gridx = 2;
		gbc_lblNewLabelPageNumOfAllPages.gridy = 7;
		add(lblNewLabelPageNumOfAllPages, gbc_lblNewLabelPageNumOfAllPages);
		
		
		JButton btnPrev = new JButton("<");
		btnPrev.setRequestFocusEnabled(false);
		btnPrev.setFont(new Font("Calibri", Font.BOLD, 14));
		btnPrev.setEnabled(false);
		btnPrev.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnPrev.setBackground(new Color(210, 105, 30));
		
		GridBagConstraints gbc_btnPrev = new GridBagConstraints();
		gbc_btnPrev.fill = GridBagConstraints.BOTH;
		gbc_btnPrev.insets = new Insets(0, 0, 5, 5);
		gbc_btnPrev.gridx = 6;
		gbc_btnPrev.gridy = 7;
		//gbc_button.gridwidth = 2;
		add(btnPrev, gbc_btnPrev);
		
		JLabel lblAmountOfRecordShown = new JLabel(offset 
				+ "/" + cart.getItems().size());
		lblAmountOfRecordShown.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblAmountOfRecordShown = new GridBagConstraints();
		gbc_lblAmountOfRecordShown.fill = GridBagConstraints.BOTH;
		gbc_lblAmountOfRecordShown.insets = new Insets(0, 0, 5, 5);
		gbc_lblAmountOfRecordShown.gridx = 7;
		gbc_lblAmountOfRecordShown.gridy = 7;
		add(lblAmountOfRecordShown, gbc_lblAmountOfRecordShown);
		
		JButton btnNext = new JButton(">");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tempCart = new Cart();
				page++;
				int prev = offset;
				for ( ;(offset < (prev + limit) ) && (offset < mainView.userCart.getItems().size()); offset++) {
					tempCart.addToCart(mainView.userCart.getItems().get(offset).getBook());
					tempCart.changeQuantity(mainView.userCart.getItems().get(offset).getBook().getIsbn(),
						mainView.userCart.getItems().get(offset).getQuantity());
				}
				table = new JTable(new CartTableModel(tempCart));
				JTableButtonRenderer buttonRenderer = new JTableButtonRenderer();
		        table.getColumn("remove from Cart").setCellRenderer(buttonRenderer);
		        CartButtonMouseListener mouseClickedListener = new CartButtonMouseListener(table,mainView);
		        table.addMouseListener(mouseClickedListener);
		        table.setRowHeight(20);
				scrollPane.setViewportView(table);
		        add(scrollPane, gbc_scrollPane);
		        lblNewLabelPageNumOfAllPages.setText(String.valueOf(page) +
						" Of " + String.valueOf((int)Math.ceil(cart.getItems().size() / (float) limit)));
		        lblAmountOfRecordShown.setText(String.valueOf(offset) 
		        		+ "/" + String.valueOf(cart.getItems().size()) );
		        if (offset >= cart.getItems().size()) {
		        	btnNext.setEnabled(false);
		        }
		        btnPrev.setEnabled(true);
			}
		});
		btnNext.setRequestFocusEnabled(false);
		btnNext.setEnabled(offset < cart.getItems().size() );
		btnNext.setFont(new Font("Calibri", Font.BOLD, 14));
		btnNext.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnNext.setBackground(new Color(210, 105, 30));
		GridBagConstraints gbc_btnNext = new GridBagConstraints();
		gbc_btnNext.fill = GridBagConstraints.BOTH;
		gbc_btnNext.insets = new Insets(0, 0, 5, 5);
		gbc_btnNext.gridx = 8;
		gbc_btnNext.gridy = 7;
		//gbc_button_1.gridwidth = 2;
		add(btnNext, gbc_btnNext);
		
		
		btnBackToBookSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	lblErrorMessage.setText("");
                setVisible(false);
                mainView.natavigateToPanel(mainView.bookSearchScreen);
            }
        });
		
		btnClearCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainView.userCart = new Cart();
                table.setModel(new CartTableModel(mainView.userCart));
                lblAmountOfRecordShown.setText("0/0");
                lblNewLabelPageNumOfAllPages.setText("1/1");
                lblTotalPrice.setText("0.00");
                lblErrorMessage.setText("My Cart is Empty!");
            }
        });
		
		
		btnPrev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				offset -= tempCart.getItems().size();
				page--;
				tempCart = new Cart();
				int prev = offset;
				int start = prev - limit;
				for (offset = start ; (offset < (start + limit)) && (offset < cart.getItems().size()); offset++) {
					tempCart.addToCart(mainView.userCart.getItems().get(offset).getBook());
					tempCart.changeQuantity(mainView.userCart.getItems().get(offset).getBook().getIsbn(),
							mainView.userCart.getItems().get(offset).getQuantity());
				}
				table = new JTable(new CartTableModel(tempCart));
				JTableButtonRenderer buttonRenderer = new JTableButtonRenderer();
		        table.getColumn("remove from Cart").setCellRenderer(buttonRenderer);
		        CartButtonMouseListener mouseClickedListener = new CartButtonMouseListener(table,mainView);
		        table.addMouseListener(mouseClickedListener);
		        table.setRowHeight(20);
				scrollPane.setViewportView(table);
		        add(scrollPane, gbc_scrollPane);
		        lblNewLabelPageNumOfAllPages.setText(String.valueOf(page) +
						" Of " + String.valueOf((int)Math.ceil(cart.getItems().size() / (float) limit)));
		        lblAmountOfRecordShown.setText(String.valueOf(offset) 
		        		+ "/" + String.valueOf(cart.getItems().size()) );
		        if ((offset - tempCart.getItems().size()) <= 0 ) {
		        	btnPrev.setEnabled(false);
		        }
		        btnNext.setEnabled(true);
			}
		});
	}

}
