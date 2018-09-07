package Forms;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import dao.AuthorDataDAO;
import models.Author;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddAuthorScreen extends JPanel {
	private JTextField textFieldAuthorName;
	private MainView mainView;

	/**
	 * Create the panel.
	 */
	public AddAuthorScreen(MainView mainView) {
		this.mainView = mainView;
		setBackground(new Color(64, 224, 208));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {30, 100, 150, 150, 100, 30, 0};
		gridBagLayout.rowHeights = new int[] {70, 0, 0, 35, 30, 35, 30, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblNewLabel = new JLabel("Add Author");
		lblNewLabel.setForeground(new Color(0, 128, 0));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Brush Script MT", Font.BOLD | Font.ITALIC, 60));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
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
		gbc_lblErrorMessage.gridy = 1;
		gbc_lblErrorMessage.gridwidth = 2;
		add(lblErrorMessage, gbc_lblErrorMessage);
		
		JLabel lblAuthorNameHead = new JLabel("Author Name");
		lblAuthorNameHead.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_lblAuthorNameHead = new GridBagConstraints();
		gbc_lblAuthorNameHead.fill = GridBagConstraints.BOTH;
		gbc_lblAuthorNameHead.insets = new Insets(0, 0, 5, 5);
		gbc_lblAuthorNameHead.gridx = 2;
		gbc_lblAuthorNameHead.gridy = 3;
		add(lblAuthorNameHead, gbc_lblAuthorNameHead);
		
		textFieldAuthorName = new JTextField();
		textFieldAuthorName.setFont(new Font("Calibri", Font.PLAIN, 14));
		textFieldAuthorName.setColumns(10);
		textFieldAuthorName.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_textFieldAuthorName = new GridBagConstraints();
		gbc_textFieldAuthorName.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldAuthorName.fill = GridBagConstraints.BOTH;
		gbc_textFieldAuthorName.gridx = 3;
		gbc_textFieldAuthorName.gridy = 3;
		add(textFieldAuthorName, gbc_textFieldAuthorName);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String authorName = textFieldAuthorName.getText().trim();
				Author author = new Author(null, authorName);
				String error = AuthorDataDAO.addAuthor(author);
				if (error != null) {
					lblErrorMessage.setText("** " + error + "!");
				} else {
					lblErrorMessage.setText("");
					textFieldAuthorName.setText("");
					setVisible(false);
					mainView.natavigateToPanel(mainView.bookSearchScreen);
					JOptionPane.showMessageDialog(null, "Author added Successfully!");
				}
			}
		});
		btnAdd.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnAdd.setBackground(new Color(210, 105, 30));
		btnAdd.setFont(new Font("Calibri", Font.BOLD, 14));
		btnAdd.setRequestFocusEnabled(false);
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.fill = GridBagConstraints.BOTH;
		gbc_btnAdd.insets = new Insets(0, 0, 5, 5);
		gbc_btnAdd.gridx = 1;
		gbc_btnAdd.gridy = 5;
		add(btnAdd, gbc_btnAdd);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblErrorMessage.setText("");
				textFieldAuthorName.setText("");
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
		gbc_btnCancel.gridy = 5;
		add(btnCancel, gbc_btnCancel);

	}

}
