package Forms;

import com.mysql.cj.util.StringUtils;
import dao.AuthorDataDAO;
import dao.BookDataDAO;
import dao.CategoryDataDAO;
import dao.OrderDataDAO;
import dao.PublisherDataDAO;
import models.*;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.sql.rowset.spi.SyncResolver;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.DropMode;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DateFormatter;
import javax.swing.JTextPane;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.table.TableCellRenderer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class BookSearchScreen extends JPanel  {

	private JTextField textFieldCategory;
	private JTextField textFieldTitle;
	private JTextField textFieldMinPrice;
	private JTextField textFieldISBN;
	private JTextField textFieldPublisher;
	private JTextArea textAreaAuthors;
	private JTable tableOfModels;
	private JScrollPane scrollPane;
	private JButton btnSearch;
	private JLabel lblBookstore;
	private JComboBox comboBoxModelSelection;
	private JButton btnAdd;
	private JButton btnNext;
	private JButton btnPrev;
	private JLabel textPane;
	private JButton btnGoToChart;
	private JButton btnEditProfile;
	private JScrollPane scrollPaneTable;
	private JLabel lblErrorMessage;
	private JButton btnLogOut;
	private MainView mainWindow ;
	private JSpinner spinnerPublicationDate;
	private GridBagConstraints gbc_scrollPaneTable;
	private JLabel lblMinPrice;
	private JLabel lblMaxPrice;
	private JLabel lblPages;
	private JLabel lblNumOutOfAll;
	private JTextField textFieldMaxPrice;
	private JButton btnUpgradeUser;
	private int limit = 10;
	private int offset = 0;
	private int page = 1;
	private int numOfPages = 1;
	private JTableModel searchTableModel;
	private TableCellRenderer buttonRenderer = new JTableButtonRenderer();;
	private List <Book> searchBookOutput;
	private AtomicInteger count = new AtomicInteger();
	private boolean isManager = false;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					BookSearchScreen frame = new BookSearchScreen(null);
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
    //Todo
    Date  searchedPubDate;

    Float minPriceNum = null;
    Float maxPriceNum = null;
    List<Category> cids;
    List<Publisher > pids;
    List<List<Author>> authors;
    Book searchedBook = new Book(null,null,null,null,null,null,null,null);
    private JButton btnOrder;
    private JButton btnReports;
	/**
	 * Create the frame.
	 */
	public BookSearchScreen( MainView mainWindow ) {
		this.mainWindow = mainWindow;
		this.isManager = mainWindow.userSignedIn.isManager();
		mainWindow.userCart = new Cart();
		setBackground(new Color(64, 224, 208));
		setBounds(100, 100, 741, 484);
		setBackground(new Color(64, 224, 208));
		setFont(new Font("Calibri", Font.BOLD, 14));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] {30, 54, 54, 80, 108, 90, 80, 90, 30};
		gbl_contentPane.rowHeights = new int[] {0, 89, 35, 10, 0, 14, 35, 35, 70, 30, 0, 30};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gbl_contentPane);

		lblBookstore = new JLabel("Online Book Store");
		lblBookstore.setForeground(new Color(0, 128, 0));
		lblBookstore.setHorizontalAlignment(SwingConstants.CENTER);
		lblBookstore.setFont(new Font("Brush Script MT", Font.BOLD | Font.ITALIC, 60));
		GridBagConstraints gbc_lblBookstore = new GridBagConstraints();
		gbc_lblBookstore.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblBookstore.insets = new Insets(0, 0, 5, 5);
		gbc_lblBookstore.gridx = 3;
		gbc_lblBookstore.gridy = 1;
		gbc_lblBookstore.gridwidth = 4;
		add(lblBookstore, gbc_lblBookstore);

		btnEditProfile = new JButton("Edit Profile");
		btnEditProfile.setFocusTraversalKeysEnabled(false);
		btnEditProfile.setFocusPainted(false);
		btnEditProfile.setFocusable(false);
		btnEditProfile.setRequestFocusEnabled(false);
		btnEditProfile.setFont(new Font("Calibri", Font.BOLD, 14));
		btnEditProfile.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnEditProfile.setBackground(new Color(210, 105, 30));
		GridBagConstraints gbc_btnEditProfile = new GridBagConstraints();
		gbc_btnEditProfile.fill = GridBagConstraints.BOTH;
		gbc_btnEditProfile.insets = new Insets(0, 0, 5, 5);
		gbc_btnEditProfile.gridx = 1;
		gbc_btnEditProfile.gridy = 2;
		gbc_btnEditProfile.gridwidth = 2;
		add(btnEditProfile, gbc_btnEditProfile);
		btnEditProfile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				mainWindow.natavigateToPanel(mainWindow.editProfileScreen);
			}
		});
		btnGoToChart = new JButton("Cart");
		btnGoToChart.setFocusPainted(false);
		btnGoToChart.setFocusTraversalKeysEnabled(false);
		btnGoToChart.setFocusable(false);
		btnGoToChart.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnGoToChart.setRequestFocusEnabled(false);
		btnGoToChart.setFont(new Font("Calibri", Font.BOLD, 14));
		btnGoToChart.setBackground(new Color(210, 105, 30));
		GridBagConstraints gbc_btnGoToChart = new GridBagConstraints();
		gbc_btnGoToChart.fill = GridBagConstraints.BOTH;
		gbc_btnGoToChart.insets = new Insets(0, 0, 5, 5);
		gbc_btnGoToChart.gridx = 3;
		gbc_btnGoToChart.gridy = 2;
		add(btnGoToChart, gbc_btnGoToChart);

		btnUpgradeUser = new JButton("Promote User");
		btnUpgradeUser.setFocusTraversalKeysEnabled(false);
		btnUpgradeUser.setFocusPainted(false);
		btnUpgradeUser.setRequestFocusEnabled(false);
		btnUpgradeUser.setFont(new Font("Calibri", Font.BOLD, 14));
		btnUpgradeUser.setFocusable(false);
		btnUpgradeUser.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnUpgradeUser.setBackground(new Color(210, 105, 30));
		GridBagConstraints gbc_btnUpgradeUser = new GridBagConstraints();
		gbc_btnUpgradeUser.fill = GridBagConstraints.BOTH;
		gbc_btnUpgradeUser.insets = new Insets(0, 0, 5, 5);
		gbc_btnUpgradeUser.gridx = 4;
		gbc_btnUpgradeUser.gridy = 2;
		btnUpgradeUser.setVisible(false);
		if(mainWindow.userSignedIn!=null && isManager)
		    btnUpgradeUser.setVisible(true);
		add(btnUpgradeUser, gbc_btnUpgradeUser);
		
		
		
		btnReports = new JButton("Reports");
		btnReports.setFocusable(false);
		btnReports.setFocusTraversalKeysEnabled(false);
		btnReports.setRequestFocusEnabled(false);
		btnReports.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
                lblErrorMessage.setText("");
                mainWindow.reportsScreen = new ReportsScreen(mainWindow);
                mainWindow.natavigateToPanel(mainWindow.reportsScreen);
			}
		});
		btnReports.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnReports.setBackground(new Color(210, 105, 30));
		btnReports.setFont(new Font("Calibri", Font.BOLD, 14));
		btnReports.setVisible(false);
		GridBagConstraints gbc_btnReports = new GridBagConstraints();
		gbc_btnReports.fill = GridBagConstraints.BOTH;
		gbc_btnReports.insets = new Insets(0, 0, 5, 5);
		gbc_btnReports.gridx = 5;
		gbc_btnReports.gridy = 2;
		add(btnReports, gbc_btnReports);
		if(mainWindow.userSignedIn!=null && isManager)
			 btnReports.setVisible(true);
		
		
		
		
		btnOrder = new JButton("Orders");
		btnOrder.setFocusable(false);
		btnOrder.setFocusTraversalKeysEnabled(false);
		btnOrder.setRequestFocusEnabled(false);
		btnOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
                lblErrorMessage.setText("");
                mainWindow.natavigateToPanel(mainWindow.confirmOrderScreen);
			}
		});
		btnOrder.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnOrder.setBackground(new Color(210, 105, 30));
		btnOrder.setFont(new Font("Calibri", Font.BOLD, 14));
		btnOrder.setVisible(false);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 6;
		gbc_btnNewButton.gridy = 2;
		add(btnOrder, gbc_btnNewButton);
		if(mainWindow.userSignedIn!=null && isManager)
			 btnOrder.setVisible(true);
		
		
		
		
		
		btnLogOut = new JButton("Log out");
		btnLogOut.setFocusTraversalKeysEnabled(false);
		btnLogOut.setFocusPainted(false);
		btnLogOut.setFocusable(false);
		btnLogOut.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnLogOut.setBackground(new Color(210, 105, 30));
		btnLogOut.setFont(new Font("Calibri", Font.BOLD, 14));
		btnLogOut.setRequestFocusEnabled(false);
		GridBagConstraints gbc_btnLogOut = new GridBagConstraints();
		gbc_btnLogOut.fill = GridBagConstraints.BOTH;
		gbc_btnLogOut.insets = new Insets(0, 0, 5, 5);
		gbc_btnLogOut.gridx = 7;
		gbc_btnLogOut.gridy = 2;
		add(btnLogOut, gbc_btnLogOut);

		lblErrorMessage = new JLabel("");
		lblErrorMessage.setFont(new Font("Calibri", Font.BOLD, 14));
		lblErrorMessage.setForeground(new Color(255, 0, 0));
		GridBagConstraints gbc_lblErrorMessage = new GridBagConstraints();
		gbc_lblErrorMessage.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblErrorMessage.insets = new Insets(0, 0, 5, 5);
		gbc_lblErrorMessage.gridx = 1;
		gbc_lblErrorMessage.gridy = 3;
		gbc_lblErrorMessage.gridwidth = 7;
		add(lblErrorMessage, gbc_lblErrorMessage);

		JLabel lblPrice = new JLabel("Price");
		lblPrice.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrice.setRequestFocusEnabled(false);
		lblPrice.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_lblPrice = new GridBagConstraints();
		gbc_lblPrice.fill = GridBagConstraints.BOTH;
		gbc_lblPrice.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrice.gridx = 1;
		gbc_lblPrice.gridy = 5;
		gbc_lblPrice.gridwidth = 2;
		add(lblPrice, gbc_lblPrice);

		JLabel lblTitle = new JLabel("Title:");
		lblTitle.setRequestFocusEnabled(false);
		lblTitle.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.fill = GridBagConstraints.BOTH;
		gbc_lblTitle.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitle.gridx = 3;
		gbc_lblTitle.gridy = 5;
		add(lblTitle, gbc_lblTitle);

		JLabel lblCategory = new JLabel("Category:");
		lblCategory.setRequestFocusEnabled(false);
		lblCategory.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_lblCategory = new GridBagConstraints();
		gbc_lblCategory.fill = GridBagConstraints.BOTH;
		gbc_lblCategory.insets = new Insets(0, 0, 5, 5);
		gbc_lblCategory.gridx = 4;
		gbc_lblCategory.gridy = 5;
		add(lblCategory, gbc_lblCategory);

		JLabel lblIsbn = new JLabel("ISBN:");
		lblIsbn.setRequestFocusEnabled(false);
		lblIsbn.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_lblIsbn = new GridBagConstraints();
		gbc_lblIsbn.fill = GridBagConstraints.BOTH;
		gbc_lblIsbn.insets = new Insets(0, 0, 5, 5);
		gbc_lblIsbn.gridx = 5;
		gbc_lblIsbn.gridy = 5;
		add(lblIsbn, gbc_lblIsbn);
		
		JCheckBox checkBoxPublicationDate = new JCheckBox("Publication Date:");
		checkBoxPublicationDate.setFocusable(false);
		checkBoxPublicationDate.setFocusPainted(false);
		checkBoxPublicationDate.setFocusTraversalKeysEnabled(false);
		checkBoxPublicationDate.setBackground(new Color(64, 224, 208));
		checkBoxPublicationDate.setRequestFocusEnabled(false);
		checkBoxPublicationDate.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_lblPublicationDate = new GridBagConstraints();
		gbc_lblPublicationDate.fill = GridBagConstraints.BOTH;
		gbc_lblPublicationDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblPublicationDate.gridx = 6;
		gbc_lblPublicationDate.gridy = 5;
		add(checkBoxPublicationDate, gbc_lblPublicationDate);

		JLabel lblPublisher = new JLabel("Publisher:");
		lblPublisher.setRequestFocusEnabled(false);
		lblPublisher.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_lblPublisher = new GridBagConstraints();
		gbc_lblPublisher.fill = GridBagConstraints.BOTH;
		gbc_lblPublisher.insets = new Insets(0, 0, 5, 5);
		gbc_lblPublisher.gridx = 7;
		gbc_lblPublisher.gridy = 5;
		add(lblPublisher, gbc_lblPublisher);

		lblMinPrice = new JLabel("Min:");
		lblMinPrice.setFont(new Font("Calibri", Font.BOLD, 14));
		lblMinPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblMinPrice = new GridBagConstraints();
		gbc_lblMinPrice.fill = GridBagConstraints.BOTH;
		gbc_lblMinPrice.insets = new Insets(0, 0, 5, 5);
		gbc_lblMinPrice.gridx = 1;
		gbc_lblMinPrice.gridy = 6;
		add(lblMinPrice, gbc_lblMinPrice);

		textFieldMinPrice = new JTextField();
		textFieldMinPrice.setBorder(new LineBorder(Color.BLACK, 1, true));
		GridBagConstraints gbc_textFieldMinPrice = new GridBagConstraints();
		gbc_textFieldMinPrice.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldMinPrice.fill = GridBagConstraints.BOTH;
		gbc_textFieldMinPrice.gridx = 2;
		gbc_textFieldMinPrice.gridy = 6;
		add(textFieldMinPrice, gbc_textFieldMinPrice);
		textFieldMinPrice.setColumns(10);

		textFieldTitle = new JTextField();
		textFieldTitle.setBorder(new LineBorder(Color.BLACK, 1, true));
		GridBagConstraints gbc_textFieldTitle = new GridBagConstraints();
		gbc_textFieldTitle.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldTitle.fill = GridBagConstraints.BOTH;
		gbc_textFieldTitle.gridx = 3;
		gbc_textFieldTitle.gridy = 6;
		add(textFieldTitle, gbc_textFieldTitle);
		textFieldTitle.setColumns(10);

		textFieldCategory = new JTextField();
		textFieldCategory.setBorder(new LineBorder(Color.BLACK, 1, true));
		GridBagConstraints gbc_textFieldCategory = new GridBagConstraints();
		gbc_textFieldCategory.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldCategory.fill = GridBagConstraints.BOTH;
		gbc_textFieldCategory.gridx = 4;
		gbc_textFieldCategory.gridy = 6;
		add(textFieldCategory, gbc_textFieldCategory);
		textFieldCategory.setColumns(10);

		textFieldISBN = new JTextField();
		textFieldISBN.setBorder(new LineBorder(Color.BLACK, 1, true));
		GridBagConstraints gbc_textFieldISBN = new GridBagConstraints();
		gbc_textFieldISBN.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldISBN.fill = GridBagConstraints.BOTH;
		gbc_textFieldISBN.gridx = 5;
		gbc_textFieldISBN.gridy = 6;
		add(textFieldISBN, gbc_textFieldISBN);
		textFieldISBN.setColumns(10);	
		
		
		JSpinner spinnerPublicationDate = new JSpinner();
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		spinnerPublicationDate.setModel(new SpinnerDateModel(today.getTime(), null, today.getTime(), Calendar.DAY_OF_MONTH));
		JSpinner.DateEditor editor = new JSpinner.DateEditor(spinnerPublicationDate, "dd/MM/yyyy");
		spinnerPublicationDate.setEditor(editor);
		GridBagConstraints gbc_spinnerPublicationDate = new GridBagConstraints();
		gbc_spinnerPublicationDate.fill = GridBagConstraints.BOTH;
		gbc_spinnerPublicationDate.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerPublicationDate.gridx = 6;
		gbc_spinnerPublicationDate.gridy = 6;

		add(spinnerPublicationDate, gbc_spinnerPublicationDate);

		textFieldPublisher = new JTextField();
		textFieldPublisher.setBorder(new LineBorder(Color.BLACK, 1, true));
		GridBagConstraints gbc_textFieldPublisher = new GridBagConstraints();
		gbc_textFieldPublisher.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldPublisher.fill = GridBagConstraints.BOTH;
		gbc_textFieldPublisher.gridx = 7;
		gbc_textFieldPublisher.gridy = 6;
		add(textFieldPublisher, gbc_textFieldPublisher);
		textFieldPublisher.setColumns(10);

		lblMaxPrice = new JLabel("Max:");
		lblMaxPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMaxPrice.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_lblMaxPrice = new GridBagConstraints();
		gbc_lblMaxPrice.fill = GridBagConstraints.BOTH;
		gbc_lblMaxPrice.insets = new Insets(0, 0, 5, 5);
		gbc_lblMaxPrice.gridx = 1;
		gbc_lblMaxPrice.gridy = 7;
		add(lblMaxPrice, gbc_lblMaxPrice);

		textFieldMaxPrice = new JTextField();
		textFieldMaxPrice.setColumns(10);
		textFieldMaxPrice.setBorder(new LineBorder(Color.BLACK, 1, true));
		GridBagConstraints gbc_textFieldMaxPrice = new GridBagConstraints();
		gbc_textFieldMaxPrice.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldMaxPrice.fill = GridBagConstraints.BOTH;
		gbc_textFieldMaxPrice.gridx = 2;
		gbc_textFieldMaxPrice.gridy = 7;
		add(textFieldMaxPrice, gbc_textFieldMaxPrice);

		JLabel lblAuthors = new JLabel("Authors:");
		lblAuthors.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAuthors.setRequestFocusEnabled(false);
		lblAuthors.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_lblAuthors = new GridBagConstraints();
		gbc_lblAuthors.fill = GridBagConstraints.BOTH;
		gbc_lblAuthors.insets = new Insets(0, 0, 5, 5);
		gbc_lblAuthors.gridx = 3;
		gbc_lblAuthors.gridy = 7;
		add(lblAuthors, gbc_lblAuthors);

		scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(Color.BLACK, 1, true));
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 4;
		gbc_scrollPane.gridy = 7;
		add(scrollPane, gbc_scrollPane);

		textAreaAuthors = new JTextArea();
		scrollPane.setViewportView(textAreaAuthors);

		
		comboBoxModelSelection = new JComboBox();
		comboBoxModelSelection.setRequestFocusEnabled(false);
		comboBoxModelSelection.setAutoscrolls(true);
		comboBoxModelSelection.setBorder(null);
		comboBoxModelSelection.setBackground(Color.WHITE);
		comboBoxModelSelection.setFont(new Font("Calibri", Font.BOLD, 14));
		comboBoxModelSelection.setModel(new DefaultComboBoxModel(new String[] {"Author", "Publisher", "Book", "Order"}));
		GridBagConstraints gbc_comboBoxModelSelection = new GridBagConstraints();
		gbc_comboBoxModelSelection.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxModelSelection.fill = GridBagConstraints.BOTH;
		gbc_comboBoxModelSelection.gridx = 5;
		gbc_comboBoxModelSelection.gridy = 7;

        comboBoxModelSelection.setVisible(false);
        if(mainWindow.userSignedIn!=null && isManager)
            comboBoxModelSelection.setVisible(true);
		add(comboBoxModelSelection, gbc_comboBoxModelSelection);

		btnAdd = new JButton("Add");
		btnAdd.setFocusPainted(false);
		btnAdd.setFocusTraversalKeysEnabled(false);
		btnAdd.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnAdd.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAdd.setRequestFocusEnabled(false);
		btnAdd.setBackground(new Color(210, 105, 30));
		btnAdd.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.fill = GridBagConstraints.BOTH;
		gbc_btnAdd.insets = new Insets(0, 0, 5, 5);
		gbc_btnAdd.gridx = 6;
		gbc_btnAdd.gridy = 7;
        btnAdd.setVisible(false);
        if(mainWindow.userSignedIn != null && isManager)
            btnAdd.setVisible(true);
		add(btnAdd, gbc_btnAdd);

		btnSearch = new JButton("Search");
		btnSearch.setFocusTraversalKeysEnabled(false);
		btnSearch.setFocusPainted(false);
		btnSearch.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnSearch.setRequestFocusEnabled(false);
		btnSearch.setBackground(new Color(210, 105, 30));
		btnSearch.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.fill = GridBagConstraints.BOTH;
		gbc_btnSearch.insets = new Insets(0, 0, 5, 5);
		gbc_btnSearch.gridx = 7;
		gbc_btnSearch.gridy = 7;
		add(btnSearch, gbc_btnSearch);

		scrollPaneTable = new JScrollPane();
		scrollPaneTable.setFont(new Font("Calibri", Font.PLAIN, 14));
		scrollPaneTable.setBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPaneTable.setBackground(new Color(64, 224, 208));
		gbc_scrollPaneTable = new GridBagConstraints();
		gbc_scrollPaneTable.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPaneTable.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneTable.gridx = 1;
		gbc_scrollPaneTable.gridy = 8;
		gbc_scrollPaneTable.gridwidth = 7;
		

		tableOfModels = new JTable();
		tableOfModels.setBackground(new Color(64, 224, 208));
		tableOfModels.setShowHorizontalLines(false);
		tableOfModels.setFont(new Font("Calibri", Font.PLAIN, 14));
		tableOfModels.setGridColor(new Color(0, 128, 0));
		tableOfModels.setBorder(new LineBorder(new Color(0, 0, 0)));
		tableOfModels.setRowHeight(30);
		tableOfModels.setCellSelectionEnabled(true);
		searchTableModel = new JTableModel(new ArrayList<Book>(), mainWindow);
		tableOfModels.setModel(searchTableModel);
		if (mainWindow.userSignedIn.isManager())
        tableOfModels.getColumn("Modify").setCellRenderer(buttonRenderer);
		tableOfModels.getColumn("Add to Cart").setCellRenderer(buttonRenderer);
		JTableButtonMouseListener mouseClickedListener = new JTableButtonMouseListener(tableOfModels,mainWindow);
        tableOfModels.addMouseListener(mouseClickedListener);
		scrollPaneTable.setViewportView(tableOfModels);
    	add(scrollPaneTable, gbc_scrollPaneTable);
		

		
		lblPages = new JLabel("Page:");
		lblPages.setHorizontalAlignment(SwingConstants.CENTER);
		lblPages.setRequestFocusEnabled(false);
		lblPages.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_lblPages = new GridBagConstraints();
		gbc_lblPages.fill = GridBagConstraints.BOTH;
		gbc_lblPages.insets = new Insets(0, 0, 5, 5);
		gbc_lblPages.gridx = 1;
		gbc_lblPages.gridy = 9;
		add(lblPages, gbc_lblPages);
		
		
		lblNumOutOfAll = new JLabel("1 Of 1");
		lblNumOutOfAll.setHorizontalAlignment(SwingConstants.LEFT);
		lblNumOutOfAll.setRequestFocusEnabled(false);
		lblNumOutOfAll.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_lblNumOutOfAll = new GridBagConstraints();
		gbc_lblNumOutOfAll.fill = GridBagConstraints.BOTH;
		gbc_lblNumOutOfAll.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumOutOfAll.gridx = 2;
		gbc_lblNumOutOfAll.gridy = 9;
		add(lblNumOutOfAll, gbc_lblNumOutOfAll);
		
		btnPrev = new JButton("<");
		btnPrev.setFocusPainted(false);
		btnPrev.setFocusTraversalKeysEnabled(false);
		btnPrev.setEnabled(false);
		btnPrev.setFont(new Font("Calibri", Font.BOLD, 14));
		btnPrev.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnPrev.setBackground(new Color(210, 105, 30));
		btnPrev.setRequestFocusEnabled(false);
		GridBagConstraints gbc_btnPrev = new GridBagConstraints();
		gbc_btnPrev.fill = GridBagConstraints.BOTH;
		gbc_btnPrev.insets = new Insets(0, 0, 5, 5);
		gbc_btnPrev.gridx = 5;
		gbc_btnPrev.gridy = 9;
		add(btnPrev, gbc_btnPrev);

		textPane = new JLabel("0/0");
		textPane.setFont(new Font("Calibri", Font.BOLD, 14));
		textPane.setBackground(new Color(64, 224, 208));
		
		GridBagConstraints gbc_textPane = new GridBagConstraints();
		gbc_textPane.insets = new Insets(0, 0, 5, 5);
		gbc_textPane.gridx = 6;
		gbc_textPane.gridy = 9;
		add(textPane, gbc_textPane);

		btnNext = new JButton(">");
		btnNext.setFocusTraversalKeysEnabled(false);
		btnNext.setFocusPainted(false);
		btnNext.setEnabled(false);
		btnNext.setFont(new Font("Calibri", Font.BOLD, 14));
		btnNext.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnNext.setBackground(new Color(210, 105, 30));
		btnNext.setRequestFocusEnabled(false);
		GridBagConstraints gbc_btnNext = new GridBagConstraints();
		gbc_btnNext.fill = GridBagConstraints.BOTH;
		gbc_btnNext.insets = new Insets(0, 0, 5, 5);
		gbc_btnNext.gridx = 7;
		gbc_btnNext.gridy = 9;
		btnNext.setEnabled(false);
		add(btnNext, gbc_btnNext);
		
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO needs To be Checked.
				boolean isDataValid = true;
				String searchedCategoryText = textFieldCategory.getText().trim();
				String searchedTitle    = textFieldTitle.getText().trim();
				String searchedMinPrice   = textFieldMinPrice.getText().trim();
				String searchedMaxPrice   = textFieldMaxPrice.getText().trim();
				String searchedISBN =       textFieldISBN.getText().trim();
                String searchedPublisherText=textFieldPublisher.getText().trim();
                String searchedAuthorsText=textAreaAuthors.getText().trim();
                String [] authosSearched = searchedAuthorsText.split("\\r?\\n");
                authors = new ArrayList<>();
                offset = 0;
                page = 1;
        		SpinnerDateModel dateTemp = (SpinnerDateModel)spinnerPublicationDate.getModel();
        		java.sql.Date searchedDate = new java.sql.Date(dateTemp.getDate().getTime());
				boolean notAvailable = false;
        		// if there is categories
				if (StringUtils.isEmptyOrWhitespaceOnly(searchedCategoryText)){
					cids=null;
				} else{
					Category searchedCategory = new Category(null,searchedCategoryText);
					cids =CategoryDataDAO.searchCategory(searchedCategory);
					if (cids.isEmpty()) {
						notAvailable = true;
					}
				}

				// if there is authors
				if (StringUtils.isEmptyOrWhitespaceOnly(searchedAuthorsText)){
					authors=null;
				} else{

					for(int i=0;i<authosSearched.length;i++){
						Author searchedAuthor = new Author(null,authosSearched[i]);
						authors.add(AuthorDataDAO.searchAuthor(searchedAuthor));
					}
					if (authors.isEmpty()) {
						notAvailable = true;
					}
				}

				// if there is publishers
				if (StringUtils.isEmptyOrWhitespaceOnly(searchedPublisherText)){
					pids=null;
				} else{
					Publisher searchedPublisher = new Publisher(null,searchedPublisherText,null,null);
					pids = PublisherDataDAO.searchPublisher(searchedPublisher);
					if (pids.isEmpty()) {
						notAvailable = true;
					}
				}
				// check publication date.
				if (checkBoxPublicationDate.isSelected()) {
					searchedBook.setPublicationDate(searchedDate);
				} else {
					searchedBook.setPublicationDate(null);
				}
				
				// if there is a title
				if (!StringUtils.isEmptyOrWhitespaceOnly(searchedTitle)){
					searchedBook.setTitle(searchedTitle);
				} else {
					searchedBook.setTitle(null);
				}

				// if there are max and min prices and are correct
				if (!StringUtils.isEmptyOrWhitespaceOnly(searchedMaxPrice)){
					boolean correct = searchedMaxPrice.matches("\\d*\\.?\\d*");
					if(correct){
						maxPriceNum = Float.valueOf(searchedMaxPrice);
					}
					else {
						// error You must enter a float number
						lblErrorMessage.setText("** Invalid Price!");
						isDataValid = false;
					}
				} else {
					maxPriceNum = null;
				}
				if (!StringUtils.isEmptyOrWhitespaceOnly(searchedMinPrice)){
					boolean correct = searchedMinPrice.matches("\\d*\\.?\\d*");
					if(correct){
						
						minPriceNum = Float.valueOf(searchedMinPrice);
					}
					else {
						lblErrorMessage.setText("** Invalid Price!");
						// error You must enter a float number
						isDataValid = false;
					}
				} else {
					minPriceNum = null;
				}

				// if there is a ISBN
				if (!StringUtils.isEmptyOrWhitespaceOnly(searchedISBN)) {
					if (StringUtils.isStrictlyNumeric(searchedISBN)){
						searchedBook.setIsbn(Long.valueOf(searchedISBN));
					}else {
						//error You must enter a number in the  ISBN field
						lblErrorMessage.setText("** Invalid ISBN!");
						isDataValid = false;
					}
				} else {
					searchedBook.setIsbn(null);
				}
				btnPrev.setEnabled(false);
				if(!isDataValid){
					searchBookOutput = new ArrayList<Book>();
					searchTableModel = new JTableModel(searchBookOutput, mainWindow);
					tableOfModels.setModel(searchTableModel);
					if (mainWindow.userSignedIn.isManager())
					tableOfModels.getColumn("Modify").setCellRenderer(buttonRenderer);
					tableOfModels.getColumn("Add to Cart").setCellRenderer(buttonRenderer);
					textPane.setText("0/0");
					lblNumOutOfAll.setText("1 Of 1");
					btnNext.setEnabled(false);
					scrollPaneTable.setViewportView(tableOfModels);
				} else if (notAvailable){
					lblErrorMessage.setText("** No Results to Show!");
					searchBookOutput = new ArrayList<Book>();
					searchTableModel = new JTableModel(searchBookOutput, mainWindow);
					tableOfModels.setModel(searchTableModel);
					if (mainWindow.userSignedIn.isManager())
					tableOfModels.getColumn("Modify").setCellRenderer(buttonRenderer);
					tableOfModels.getColumn("Add to Cart").setCellRenderer(buttonRenderer);
					textPane.setText("0/0");
					lblNumOutOfAll.setText("1 Of 1");
					btnNext.setEnabled(false);
					scrollPaneTable.setViewportView(tableOfModels);
				} else {
					searchBookOutput = BookDataDAO.selectBookQuery(searchedBook,minPriceNum,maxPriceNum,
							authors,cids,pids,limit,offset,count,null,true);
					searchTableModel = new JTableModel(searchBookOutput, mainWindow);
					tableOfModels.setModel(searchTableModel);
					if (mainWindow.userSignedIn.isManager())
					tableOfModels.getColumn("Modify").setCellRenderer(buttonRenderer);
					tableOfModels.getColumn("Add to Cart").setCellRenderer(buttonRenderer);
                    
					textPane.setText(String.valueOf(offset + searchBookOutput.size()) + "/" + String.valueOf(count));
					if (count.get() == 0 ) {
						lblNumOutOfAll.setText("1 Of 1");
					} else {
						lblNumOutOfAll.setText(String.valueOf(page) + " Of " + String.valueOf((int)Math.ceil(count.get() / (float)limit)));
						btnNext.setEnabled(true);
					}
					scrollPaneTable.setViewportView(tableOfModels);
					add(scrollPaneTable, gbc_scrollPaneTable);
					 if ((offset + searchBookOutput.size()) >= count.get()) 
		                	btnNext.setEnabled(false);
					 if (searchBookOutput.size() == 0) {
						 lblErrorMessage.setText("No results to show!");
					 } else {
						 lblErrorMessage.setText("");
					 }
				}
			}
		});
        btnNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	offset+=searchBookOutput.size();
            	page++;
                searchBookOutput = BookDataDAO.selectBookQuery(searchedBook,minPriceNum,maxPriceNum,
                       authors,cids,pids,limit,offset,count,null,true);
                textPane.setText(String.valueOf(offset + searchBookOutput.size()) + "/" + String.valueOf(count));
                lblNumOutOfAll.setText(String.valueOf(page) + " Of " + String.valueOf((int)(Math.ceil(count.get() / (float)limit))));
                searchTableModel = new JTableModel(searchBookOutput, mainWindow);
				tableOfModels.setModel(searchTableModel);	
				if (mainWindow.userSignedIn.isManager())
                tableOfModels.getColumn("Modify").setCellRenderer(buttonRenderer);
				tableOfModels.getColumn("Add to Cart").setCellRenderer(buttonRenderer);
				scrollPaneTable.setViewportView(tableOfModels);
				add(scrollPaneTable, gbc_scrollPaneTable);
                btnPrev.setEnabled(true);
                if ((offset + searchBookOutput.size()) >= count.get()) 
                	btnNext.setEnabled(false);
                
            }
        });
        btnPrev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                offset-= limit;
                page--;
                searchBookOutput = BookDataDAO.selectBookQuery(searchedBook,minPriceNum,maxPriceNum,
                          authors,cids,pids,limit,offset,count,null,true);
                textPane.setText(String.valueOf(offset + searchBookOutput.size()) + "/" + String.valueOf(count));
                lblNumOutOfAll.setText(String.valueOf(page) + " Of " + String.valueOf((int)Math.ceil(count.get() / (float)limit)));
                searchTableModel = new JTableModel(searchBookOutput, mainWindow);
    			tableOfModels.setModel(searchTableModel);
    			if (mainWindow.userSignedIn.isManager())
                tableOfModels.getColumn("Modify").setCellRenderer(buttonRenderer);
    			tableOfModels.getColumn("Add to Cart").setCellRenderer(buttonRenderer);
    			scrollPaneTable.setViewportView(tableOfModels);
    			add(scrollPaneTable, gbc_scrollPaneTable);
    			if ((offset) <= 0 ) {
    				btnPrev.setEnabled(false);
    			}
    			btnNext.setEnabled(true);
            }
        });
        btnLogOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	offset = 0;
            	page = 0;
            	count = new AtomicInteger();
            	searchBookOutput = new ArrayList<Book>();
            	textPane.setText("0/0");
                lblNumOutOfAll.setText("1 Of 1");
            	searchTableModel = new JTableModel(searchBookOutput, mainWindow);
     			tableOfModels.setModel(searchTableModel);
     			if (mainWindow.userSignedIn.isManager())
                tableOfModels.getColumn("Modify").setCellRenderer(buttonRenderer);
     			tableOfModels.getColumn("Add to Cart").setCellRenderer(buttonRenderer);
            	scrollPaneTable.setViewportView(tableOfModels);
            	add(scrollPaneTable, gbc_scrollPaneTable);
            	mainWindow.userCart = null;
            	mainWindow.userSignedIn = null;
                setVisible(false);
                mainWindow.natavigateToPanel(mainWindow.signInScreen);

            }
        });
        btnGoToChart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                lblErrorMessage.setText("");
				mainWindow.myCartScreen = new MyCartScreen(mainWindow);
                mainWindow.natavigateToPanel(mainWindow.myCartScreen);
            }
        });
        btnUpgradeUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	lblErrorMessage.setText("");
            	setVisible(false);
                mainWindow.natavigateToPanel(mainWindow.upgradeUserScreen);
            }
        });
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	lblErrorMessage.setText("");
            	setVisible(false);
                String selectedModify = comboBoxModelSelection.getSelectedItem().toString();
                if(selectedModify.equals("Author"))
                  mainWindow.natavigateToPanel(mainWindow.addAuthorScreen);
                else  if (selectedModify.equals("Publisher"))
                  mainWindow.natavigateToPanel(mainWindow.addPublisherScreen);
                else if (selectedModify.equals("Book"))
                  mainWindow.natavigateToPanel(mainWindow.addBookScreen);
                else
                  mainWindow.natavigateToPanel(mainWindow.addOrderScreen);
            }
        });

	}
	
	public void start() {
		searchBookOutput = BookDataDAO.selectBookQuery(searchedBook,minPriceNum,maxPriceNum,
                authors,cids,pids,limit,offset,count,null,true);
		searchTableModel = new JTableModel(searchBookOutput, mainWindow);
			tableOfModels.setModel(searchTableModel);
			if (mainWindow.userSignedIn.isManager())
        tableOfModels.getColumn("Modify").setCellRenderer(buttonRenderer);
			tableOfModels.getColumn("Add to Cart").setCellRenderer(buttonRenderer);
    	scrollPaneTable.setViewportView(tableOfModels);
    	add(scrollPaneTable, gbc_scrollPaneTable);
    	 
		textPane.setText(String.valueOf(offset + searchBookOutput.size()) + "/" + String.valueOf(count));
		if (count.get() == 0 ) {
			lblNumOutOfAll.setText("1 Of 1");
		} else {
			lblNumOutOfAll.setText(String.valueOf(page) + " Of " + String.valueOf((int)Math.ceil(count.get() / (float)limit)));
			btnNext.setEnabled(true);
		}
		 if ((offset + searchBookOutput.size()) >= count.get()) 
            	btnNext.setEnabled(false);
		 if (searchBookOutput.size() == 0) {
			 lblErrorMessage.setText("No results to show!");
		 } else {
			 lblErrorMessage.setText("");
		 }
        
	}
}
