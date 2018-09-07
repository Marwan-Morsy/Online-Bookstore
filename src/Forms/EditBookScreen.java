package Forms;


import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.lang.management.ManagementFactory;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.border.LineBorder;

import dao.AuthorDataDAO;
import dao.BookDataDAO;
import dao.CategoryDataDAO;
import dao.PublisherDataDAO;
import models.Author;
import models.Book;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JSpinner;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SpinnerNumberModel;

public class EditBookScreen extends JPanel {
	private JTextField textFieldPublisherName;
	private JTextField textFieldPrice;
	private JTextField textFieldCategory;
	private JTextField textFieldTitle;
	private JTextField textFieldISBN;
	private JTextArea textArea;
	private MainView mainView;
	private JSpinner spinnerPublicationDate;
	private JSpinner spinnerThreshold;
	private JSpinner spinnerNumOfCopies;
	private Book myBook;
	private List<Author> oldAuthors;
	/**
	 * Create the panel.
	 */
	public EditBookScreen(MainView mainView) {
		this.mainView = mainView;
		setBackground(new Color(64, 224, 208));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {30, 100, 100, 100, 100, 30, 0};
		gridBagLayout.rowHeights = new int[] {30, 70, 0, 0, 30, 30, 50, 30, 30, 30, 30, 30, 30, 30, 35, 30, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblNewLabel = new JLabel("Edit Book");
		lblNewLabel.setForeground(new Color(0, 128, 0));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Brush Script MT", Font.BOLD | Font.ITALIC, 60));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		gbc_lblNewLabel.gridwidth = 4;
		add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel lblErrorMessage = new JLabel("");
		lblErrorMessage.setForeground(new Color(255, 0, 0));
		lblErrorMessage.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_lblErrorMessage = new GridBagConstraints();
		gbc_lblErrorMessage.fill = GridBagConstraints.VERTICAL;
		gbc_lblErrorMessage.anchor = GridBagConstraints.WEST;
		gbc_lblErrorMessage.insets = new Insets(0, 0, 5, 5);
		gbc_lblErrorMessage.gridx = 2;
		gbc_lblErrorMessage.gridy = 2;
		gbc_lblErrorMessage.gridwidth = 2;
		add(lblErrorMessage, gbc_lblErrorMessage);
		
		JLabel lblISBN = new JLabel("ISBN:");
		lblISBN.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_lblISBN = new GridBagConstraints();
		gbc_lblISBN.fill = GridBagConstraints.BOTH;
		gbc_lblISBN.insets = new Insets(0, 0, 5, 5);
		gbc_lblISBN.gridx = 1;
		gbc_lblISBN.gridy = 4;
		add(lblISBN, gbc_lblISBN);
		
		textFieldISBN = new JTextField();
		textFieldISBN.setBorder(new LineBorder(new Color(0, 0, 0)));
		textFieldISBN.setFont(new Font("Calibri", Font.PLAIN, 14));
		
		GridBagConstraints gbc_textFieldISBN = new GridBagConstraints();
		gbc_textFieldISBN.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldISBN.fill = GridBagConstraints.BOTH;
		gbc_textFieldISBN.gridx = 2;
		gbc_textFieldISBN.gridy = 4;
		add(textFieldISBN, gbc_textFieldISBN);
		textFieldISBN.setColumns(10);
		
		JLabel lblTitle = new JLabel("Title:");
		lblTitle.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.anchor = GridBagConstraints.EAST;
		gbc_lblTitle.fill = GridBagConstraints.VERTICAL;
		gbc_lblTitle.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitle.gridx = 3;
		gbc_lblTitle.gridy = 4;
		add(lblTitle, gbc_lblTitle);
		
		textFieldTitle = new JTextField();
		textFieldTitle.setBorder(new LineBorder(new Color(0, 0, 0)));
		textFieldTitle.setFont(new Font("Calibri", Font.PLAIN, 14));
		
		GridBagConstraints gbc_textFieldTitle = new GridBagConstraints();
		gbc_textFieldTitle.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldTitle.fill = GridBagConstraints.BOTH;
		gbc_textFieldTitle.gridx = 4;
		gbc_textFieldTitle.gridy = 4;
		add(textFieldTitle, gbc_textFieldTitle);
		textFieldTitle.setColumns(10);
		
		JLabel lblAuthors = new JLabel("Authors:");
		lblAuthors.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_lblAuthors = new GridBagConstraints();
		gbc_lblAuthors.fill = GridBagConstraints.BOTH;
		gbc_lblAuthors.insets = new Insets(0, 0, 5, 5);
		gbc_lblAuthors.gridx = 1;
		gbc_lblAuthors.gridy = 6;
		add(lblAuthors, gbc_lblAuthors);
		
		JScrollPane scrollPaneAuthors = new JScrollPane();
		GridBagConstraints gbc_scrollPaneAuthors = new GridBagConstraints();
		gbc_scrollPaneAuthors.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPaneAuthors.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneAuthors.gridx = 2;
		gbc_scrollPaneAuthors.gridy = 6;
		add(scrollPaneAuthors, gbc_scrollPaneAuthors);
		
		textArea = new JTextArea();
		scrollPaneAuthors.setViewportView(textArea);
		textArea.setBorder(new LineBorder(new Color(0, 0, 0)));
		textArea.setFont(new Font("Calibri", Font.PLAIN, 14));
		
		JLabel lblPrice = new JLabel("Price:");
		lblPrice.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_lblPrice = new GridBagConstraints();
		gbc_lblPrice.anchor = GridBagConstraints.EAST;
		gbc_lblPrice.fill = GridBagConstraints.VERTICAL;
		gbc_lblPrice.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrice.gridx = 3;
		gbc_lblPrice.gridy = 6;
		add(lblPrice, gbc_lblPrice);
		
		textFieldPrice = new JTextField();
		textFieldPrice.setBorder(new LineBorder(new Color(0, 0, 0)));
		textFieldPrice.setFont(new Font("Calibri", Font.PLAIN, 14));
		GridBagConstraints gbc_textFieldPrice = new GridBagConstraints();
		gbc_textFieldPrice.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldPrice.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldPrice.gridx = 4;
		gbc_textFieldPrice.gridy = 6;
		add(textFieldPrice, gbc_textFieldPrice);
		textFieldPrice.setColumns(10);
		
		JLabel lblPublisherName = new JLabel("Publisher:");
		lblPublisherName.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_lblPublisherName = new GridBagConstraints();
		gbc_lblPublisherName.fill = GridBagConstraints.BOTH;
		gbc_lblPublisherName.insets = new Insets(0, 0, 5, 5);
		gbc_lblPublisherName.gridx = 1;
		gbc_lblPublisherName.gridy = 8;
		add(lblPublisherName, gbc_lblPublisherName);
		
		textFieldPublisherName = new JTextField();
		textFieldPublisherName.setBorder(new LineBorder(new Color(0, 0, 0)));
		textFieldPublisherName.setFont(new Font("Calibri", Font.PLAIN, 14));
		
		GridBagConstraints gbc_textFieldPublisherName = new GridBagConstraints();
		gbc_textFieldPublisherName.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldPublisherName.fill = GridBagConstraints.BOTH;
		gbc_textFieldPublisherName.gridx = 2;
		gbc_textFieldPublisherName.gridy = 8;
		add(textFieldPublisherName, gbc_textFieldPublisherName);
		textFieldPublisherName.setColumns(10);
		
		JLabel lblPublicationDate = new JLabel("Publication Date:");
		lblPublicationDate.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_lblPublicationDate = new GridBagConstraints();
		gbc_lblPublicationDate.anchor = GridBagConstraints.EAST;
		gbc_lblPublicationDate.fill = GridBagConstraints.VERTICAL;
		gbc_lblPublicationDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblPublicationDate.gridx = 3;
		gbc_lblPublicationDate.gridy = 8;
		add(lblPublicationDate, gbc_lblPublicationDate);
		
		spinnerPublicationDate = new JSpinner();
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		spinnerPublicationDate.setModel(new SpinnerDateModel(today.getTime(), null, today.getTime(), Calendar.DAY_OF_MONTH));
		JSpinner.DateEditor editor = new JSpinner.DateEditor(spinnerPublicationDate, "dd/MM/yyyy");
		
		spinnerPublicationDate.setEditor(editor);
		
		GridBagConstraints gbc_spinnerPublicationDate = new GridBagConstraints();
		gbc_spinnerPublicationDate.fill = GridBagConstraints.BOTH;
		gbc_spinnerPublicationDate.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerPublicationDate.gridx = 4;
		gbc_spinnerPublicationDate.gridy = 8;
		add(spinnerPublicationDate, gbc_spinnerPublicationDate);
		
		JLabel lblCategory = new JLabel("Category:");
		lblCategory.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_lblCategory = new GridBagConstraints();
		gbc_lblCategory.fill = GridBagConstraints.BOTH;
		gbc_lblCategory.insets = new Insets(0, 0, 5, 5);
		gbc_lblCategory.gridx = 1;
		gbc_lblCategory.gridy = 10;
		add(lblCategory, gbc_lblCategory);
		
		textFieldCategory = new JTextField();
		textFieldCategory.setBorder(new LineBorder(new Color(0, 0, 0)));
		textFieldCategory.setFont(new Font("Calibri", Font.PLAIN, 14));
		
		GridBagConstraints gbc_textFieldCategory = new GridBagConstraints();
		gbc_textFieldCategory.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldCategory.fill = GridBagConstraints.BOTH;
		gbc_textFieldCategory.gridx = 2;
		gbc_textFieldCategory.gridy = 10;
		add(textFieldCategory, gbc_textFieldCategory);
		textFieldCategory.setColumns(10);
		
		JLabel lblThreshold = new JLabel("Threshold");
		lblThreshold.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_lblThreshold = new GridBagConstraints();
		gbc_lblThreshold.anchor = GridBagConstraints.EAST;
		gbc_lblThreshold.fill = GridBagConstraints.VERTICAL;
		gbc_lblThreshold.insets = new Insets(0, 0, 5, 5);
		gbc_lblThreshold.gridx = 3;
		gbc_lblThreshold.gridy = 10;
		add(lblThreshold, gbc_lblThreshold);
		
		spinnerThreshold = new JSpinner();
		spinnerThreshold.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		
		GridBagConstraints gbc_spinnerThreshold = new GridBagConstraints();
		gbc_spinnerThreshold.fill = GridBagConstraints.BOTH;
		gbc_spinnerThreshold.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerThreshold.gridx = 4;
		gbc_spinnerThreshold.gridy = 10;
		add(spinnerThreshold, gbc_spinnerThreshold);
		
		JLabel lblNoOfCopies = new JLabel("No Of Copies");
		lblNoOfCopies.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_lblNoOfCopies = new GridBagConstraints();
		gbc_lblNoOfCopies.fill = GridBagConstraints.BOTH;
		gbc_lblNoOfCopies.insets = new Insets(0, 0, 5, 5);
		gbc_lblNoOfCopies.gridx = 1;
		gbc_lblNoOfCopies.gridy = 12;
		add(lblNoOfCopies, gbc_lblNoOfCopies);
		
		spinnerNumOfCopies = new JSpinner();
		spinnerNumOfCopies.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		
		GridBagConstraints gbc_spinnerNumOfCoipes = new GridBagConstraints();
		gbc_spinnerNumOfCoipes.fill = GridBagConstraints.BOTH;
		gbc_spinnerNumOfCoipes.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerNumOfCoipes.gridx = 2;
		gbc_spinnerNumOfCoipes.gridy = 12;
		add(spinnerNumOfCopies, gbc_spinnerNumOfCoipes);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String publisherName = textFieldPublisherName.getText().trim();
				String category = textFieldCategory.getText().trim();
				String isbn = textFieldISBN.getText().trim();
				String title = textFieldTitle.getText().trim();
				String price = textFieldPrice.getText().trim();
				String threshold = spinnerThreshold.getModel().getValue().toString();
				String copies = spinnerNumOfCopies.getModel().getValue().toString();
        		SpinnerDateModel temp = (SpinnerDateModel)spinnerPublicationDate.getModel();
        		java.sql.Date date = new java.sql.Date(temp.getDate().getTime());
        		String author = textArea.getText();
        		Integer pid = PublisherDataDAO.getPublisherId(publisherName);
        		Integer cid = CategoryDataDAO.getCategoryId(category);
        		Pattern pattern = Pattern.compile("^[0-9]+$");
		        Matcher matcher = pattern.matcher(isbn);
        		if (pid == null || cid == null || !matcher.matches()) {
        			lblErrorMessage.setText("** Invalid Publisher Name or Category or isbn!");
        		} else {
        			List<Author> authors = null;
        			if (author == null || author.trim().isEmpty()) {
            			authors = null;
            		} else {
            			boolean notExist = false;
            			authors = new ArrayList<Author> ();
            			String[] authorNames = author.split("\\r?\\n");
            			for (int i = 0; i < authorNames.length; i++) {
            				Author autTemp = new Author(AuthorDataDAO.getAuthorId(authorNames[i]), authorNames[i]);
            				authors.add(autTemp);
            				if (autTemp.getAid() == null) {
            					notExist = true;
            				}
            			}	
            			if (notExist) {
            				lblErrorMessage.setText("** Some authors names don't exist!");
            			} else {
            				Book book = new Book(Long.valueOf(isbn), title,
                					date, Integer.valueOf(threshold),
                					Float.valueOf(price),
                					Integer.valueOf(copies), cid, pid);
//            				for (int i = 0; i < authors.size(); i++) {
//            					//TODO
//            					String error = AuthorDataDAO.addAuthorToBook(authors.get(i).getAid(), Integer.valueOf(isbn));
//            					if (error != null) {
//            						lblErrorMessage.setText("** " + error + "!");
//            					}
//            				}
            				String bookError = null;
							try {
								bookError = BookDataDAO.updateBook(myBook, book, oldAuthors, authors);
							} catch (SQLException e1) {
								lblErrorMessage.setText("** Data Invalid Error!");
							}
            				if (bookError != null) {
            					lblErrorMessage.setText("**" + bookError + "!");
            				} else {
            					lblErrorMessage.setText("");
        						textFieldPublisherName.setText("");
        						textArea.setText("");
        						textFieldISBN.setText("");
        						textFieldCategory.setText("");
        						textFieldPrice.setText("");
        						textFieldTitle.setText("");
        						spinnerNumOfCopies.resetKeyboardActions();
        						spinnerThreshold.resetKeyboardActions();
        						spinnerPublicationDate.resetKeyboardActions();
        						setVisible(false);
        						mainView.bookSearchScreen.start();
        						mainView.natavigateToPanel(mainView.bookSearchScreen);
        						JOptionPane.showMessageDialog(null, "Book Edited Successfully!");
            				}
            			}
            		}			
        		}
			}
		});
		
		
		btnEdit.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnEdit.setBackground(new Color(210, 105, 30));
		btnEdit.setFont(new Font("Calibri", Font.BOLD, 14));
		btnEdit.setRequestFocusEnabled(false);
		GridBagConstraints gbc_btnEdit = new GridBagConstraints();
		gbc_btnEdit.fill = GridBagConstraints.BOTH;
		gbc_btnEdit.insets = new Insets(0, 0, 5, 5);
		gbc_btnEdit.gridx = 1;
		gbc_btnEdit.gridy = 14;
		add(btnEdit, gbc_btnEdit);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblErrorMessage.setText("");
				textFieldPublisherName.setText("");
				textArea.setText("");
				textFieldISBN.setText("");
				textFieldCategory.setText("");
				textFieldPrice.setText("");
				textFieldTitle.setText("");
				spinnerNumOfCopies.resetKeyboardActions();
				spinnerThreshold.resetKeyboardActions();
				spinnerPublicationDate.resetKeyboardActions();
				setVisible(false);
				mainView.natavigateToPanel(mainView.bookSearchScreen);
			}
		});
		btnCancel.setRequestFocusEnabled(false);
		btnCancel.setFont(new Font("Calibri", Font.BOLD, 14));
		btnCancel.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnCancel.setBackground(new Color(210, 105, 30));
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.fill = GridBagConstraints.BOTH;
		gbc_btnCancel.insets = new Insets(0, 0, 5, 5);
		gbc_btnCancel.gridx = 4;
		gbc_btnCancel.gridy = 14;
		add(btnCancel, gbc_btnCancel);

	}
	
	public void setCurBook(Book book) {
		this.myBook = book;
		textFieldISBN.setText(String.valueOf(myBook.getIsbn()));
		textFieldTitle.setText(myBook.getTitle());
		oldAuthors = AuthorDataDAO.getAuthorsOfBook(myBook.getIsbn());
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < oldAuthors.size(); i++) {
			sb.append(oldAuthors.get(i).getName());
			if ((i + 1) != oldAuthors.size()) {
				sb.append("\n");
			}
		}
		textArea.setText(sb.toString());
		textFieldPublisherName.setText(PublisherDataDAO.getPublisherName(myBook.getPid()));
		
		java.sql.Date date = myBook.getPublicationDate();
		Date tempDate = new Date(date.getTime());
		Date today= new Date();
		spinnerPublicationDate.setModel(new SpinnerDateModel(tempDate, null, today, Calendar.DAY_OF_MONTH));
		//spinnerPublicationDate.setValue(sdf.format(date).toString());
		JSpinner.DateEditor editor = new JSpinner.DateEditor(spinnerPublicationDate, "dd/MM/yyyy");
		spinnerPublicationDate.setEditor(editor);
		textFieldCategory.setText(CategoryDataDAO.getCategoryName(myBook.getCid()));
		spinnerThreshold.setValue(myBook.getThreshold());
		spinnerNumOfCopies.setValue(myBook.getCopiesNumber());
		textFieldPrice.setText(String.valueOf(myBook.getPrice()));
		
	}

}
