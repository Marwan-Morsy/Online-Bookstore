package Forms;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.LineBorder;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import provider.ConnectionProvider;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;

public class ReportsScreen extends JPanel {

	private static final String[] reports = {
			"Report_1-1", "Report_1-2", "Report_2-1", "Report_2-2", "Report_3-1", "Report_3-2"
	};
	/**
	 * Create the panel.
	 */
	public ReportsScreen(MainView mainView) {
		setRequestFocusEnabled(false);
		setBackground(new Color(64, 224, 208));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[] {30, 40, 30, 40, 30, 40, 30, 40, 30, 40, 30, 40, 30, 40, 30, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JButton btnReport1 = new JButton("Total book Sales last month");
		btnReport1.setRequestFocusEnabled(false);
		btnReport1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String temp = generateReportInPDF("Total book Sales last month", 0);
				if (temp != null) {
					JOptionPane.showMessageDialog(null, "Result is generated Successfully in Results folder!");
				} else {
					JOptionPane.showMessageDialog(null, "Unknown Error Occurs!");
				}
			}
		});
		btnReport1.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnReport1.setBackground(new Color(210, 105, 30));
		GridBagConstraints gbc_btnReport1 = new GridBagConstraints();
		gbc_btnReport1.fill = GridBagConstraints.BOTH;
		gbc_btnReport1.insets = new Insets(0, 0, 5, 5);
		gbc_btnReport1.gridx = 1;
		gbc_btnReport1.gridy = 1;
		add(btnReport1, gbc_btnReport1);
		
		JButton buttonReport2 = new JButton("Indivdual Book Sales Last month");
		buttonReport2.setRequestFocusEnabled(false);
		buttonReport2.setBorder(new LineBorder(new Color(0, 0, 0)));
		buttonReport2.setBackground(new Color(210, 105, 30));
		buttonReport2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String temp = generateReportInPDF("Indivdual Book Sales Last month", 1);
				if (temp != null) {
					JOptionPane.showMessageDialog(null, "Result is generated Successfully in Results folder!");
				} else {
					JOptionPane.showMessageDialog(null, "Unknown Error Occurs!");
				}
			}
		});
		GridBagConstraints gbc_buttonReport2 = new GridBagConstraints();
		gbc_buttonReport2.fill = GridBagConstraints.BOTH;
		gbc_buttonReport2.insets = new Insets(0, 0, 5, 5);
		gbc_buttonReport2.gridx = 1;
		gbc_buttonReport2.gridy = 3;
		add(buttonReport2, gbc_buttonReport2);
		
		JButton buttonReport3 = new JButton("Top 5 Users (mony spent) last 3 months");
		buttonReport3.setRequestFocusEnabled(false);
		buttonReport3.setBorder(new LineBorder(new Color(0, 0, 0)));
		buttonReport3.setBackground(new Color(210, 105, 30));
		buttonReport3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String temp = generateReportInPDF("Top Users mony spent last Three months", 2);
				if (temp != null) {
					JOptionPane.showMessageDialog(null, "Result is generated Successfully in Results folder!");
				} else {
					JOptionPane.showMessageDialog(null, "Unknown Error Occurs!");
				}
			}
		});
		GridBagConstraints gbc_buttonReport3 = new GridBagConstraints();
		gbc_buttonReport3.fill = GridBagConstraints.BOTH;
		gbc_buttonReport3.insets = new Insets(0, 0, 5, 5);
		gbc_buttonReport3.gridx = 1;
		gbc_buttonReport3.gridy = 5;
		add(buttonReport3, gbc_buttonReport3);
		
		JButton buttonReport4 = new JButton("Top 5 Users (book bought) last 3 months");
		buttonReport4.setRequestFocusEnabled(false);
		buttonReport4.setBorder(new LineBorder(new Color(0, 0, 0)));
		buttonReport4.setBackground(new Color(210, 105, 30));
		buttonReport4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String temp = generateReportInPDF("Top Users book bought last Three months", 3);
				if (temp != null) {
					JOptionPane.showMessageDialog(null, "Result is generated Successfully in Results folder!");
				} else {
					JOptionPane.showMessageDialog(null, "Unknown Error Occurs!");
				}
			}
		});
		GridBagConstraints gbc_buttonReport4 = new GridBagConstraints();
		gbc_buttonReport4.fill = GridBagConstraints.BOTH;
		gbc_buttonReport4.insets = new Insets(0, 0, 5, 5);
		gbc_buttonReport4.gridx = 1;
		gbc_buttonReport4.gridy = 7;
		add(buttonReport4, gbc_buttonReport4);
		
		JButton buttonReport5 = new JButton("Top 10 Books (Money Gain) last 3 months");
		buttonReport5.setRequestFocusEnabled(false);
		buttonReport5.setBorder(new LineBorder(new Color(0, 0, 0)));
		buttonReport5.setBackground(new Color(210, 105, 30));
		buttonReport5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String temp = generateReportInPDF("Top Ten Books money gain last Three months", 4);
				if (temp != null) {
					JOptionPane.showMessageDialog(null, "Result is generated Successfully in Results folder!");
				} else {
					JOptionPane.showMessageDialog(null, "Unknown Error Occurs!");
				}
			}
		});
		GridBagConstraints gbc_buttonReport5 = new GridBagConstraints();
		gbc_buttonReport5.fill = GridBagConstraints.BOTH;
		gbc_buttonReport5.insets = new Insets(0, 0, 5, 5);
		gbc_buttonReport5.gridx = 1;
		gbc_buttonReport5.gridy = 9;
		add(buttonReport5, gbc_buttonReport5);
		
		JButton buttonReport6 = new JButton("Top 10 Books (Books Sold) last 3 months");
		buttonReport6.setRequestFocusEnabled(false);
		buttonReport6.setBorder(new LineBorder(new Color(0, 0, 0)));
		buttonReport6.setBackground(new Color(210, 105, 30));
		buttonReport6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String temp =  generateReportInPDF("Top Ten Books Sold last Three months", 5);
				if (temp != null) {
					JOptionPane.showMessageDialog(null, "Result is generated Successfully in Results folder!");
				} else {
					JOptionPane.showMessageDialog(null, "Unknown Error Occurs!");
				}
			}
		});
		GridBagConstraints gbc_buttonReport6 = new GridBagConstraints();
		gbc_buttonReport6.fill = GridBagConstraints.BOTH;
		gbc_buttonReport6.insets = new Insets(0, 0, 5, 5);
		gbc_buttonReport6.gridx = 1;
		gbc_buttonReport6.gridy = 11;
		add(buttonReport6, gbc_buttonReport6);
		
		JButton buttonBack = new JButton("Back");
		buttonBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				mainView.natavigateToPanel(mainView.bookSearchScreen);
			}
		});
		buttonBack.setRequestFocusEnabled(false);
		buttonBack.setBorder(new LineBorder(new Color(0, 0, 0)));
		buttonBack.setBackground(new Color(210, 105, 30));
		GridBagConstraints gbc_buttonBack = new GridBagConstraints();
		gbc_buttonBack.fill = GridBagConstraints.BOTH;
		gbc_buttonBack.insets = new Insets(0, 0, 5, 5);
		gbc_buttonBack.gridx = 1;
		gbc_buttonBack.gridy = 13;
		add(buttonBack, gbc_buttonBack);

	}
	
	public String generateReportInPDF(String fileName, int i) {
		try {
		File currentDirFile = new File("");	
		StringBuilder path = new StringBuilder(currentDirFile.getAbsolutePath());
		path.append(File.separator + "jrxml" + File.separator);
		Map<String, Object> parameters = new HashMap<String,Object>();
		parameters.put("basePath", path.toString());
		path.append(reports[i] +".jrxml");
		JasperDesign jasperDesign;
		jasperDesign = JRXmlLoader.load(path.toString());
		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		/** PDF **/
		
		byte[] byteStream = JasperRunManager.runReportToPdf(jasperReport, parameters,
				ConnectionProvider.getConnection());
		String path1 = currentDirFile.getAbsolutePath();
		String resultName= path1 +File.separator + "results";
		File outFile = new File(resultName);
		outFile.mkdirs();
		resultName = resultName + File.separator + fileName + ".pdf";
		try (FileOutputStream fos = new FileOutputStream(resultName)) {
			   fos.write(byteStream);
			   return "";
		}
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
