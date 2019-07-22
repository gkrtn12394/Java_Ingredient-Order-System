package temp;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;

public class Instructor extends JFrame {

	private JPanel contentPane;
	private JTextField applyNumber;
	private JTextField depositDate;
	private JTextField depositAmount;
	private ClientController cc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Instructor frame = new Instructor();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Instructor() {
		cc = new ClientController();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("실습 메뉴 조회", null, panel, null);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(12, 10, 399, 65);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\uAC15\uC758\uC7A5\uC18C");
		lblNewLabel.setBounds(28, 10, 54, 15);
		panel_1.add(lblNewLabel);
		
		JLabel label = new JLabel("\uB144\uB3C4");
		label.setBounds(117, 10, 36, 15);
		panel_1.add(label);
		
		JLabel label_1 = new JLabel("\uC6D4");
		label_1.setBounds(191, 10, 36, 15);
		panel_1.add(label_1);
		
		JLabel label_2 = new JLabel("\uC77C");
		label_2.setBounds(250, 10, 36, 15);
		panel_1.add(label_2);
		
		JComboBox placeBox1 = new JComboBox();
		placeBox1.setModel(new DefaultComboBoxModel(new String[] {"\uBCF8\uC810", "\uC7A0\uC2E4\uC810", "\uAC15\uB0A8\uC810"}));
		placeBox1.setBounds(0, 35, 96, 23);
		panel_1.add(placeBox1);
		
		JComboBox yearBox1 = new JComboBox();
		yearBox1.setModel(new DefaultComboBoxModel(new String[] {"2015", "2016", "2017", "2018", "2019"}));
		yearBox1.setBounds(101, 35, 63, 23);
		panel_1.add(yearBox1);
		
		JComboBox monthBox1 = new JComboBox();
		monthBox1.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		monthBox1.setBounds(176, 35, 48, 23);
		panel_1.add(monthBox1);
		
		JComboBox dayBox1 = new JComboBox();
		dayBox1.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		dayBox1.setBounds(236, 35, 47, 23);
		panel_1.add(dayBox1);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(12, 78, 399, 121);
		panel.add(scrollPane_2);
		
		JTextArea textArea = new JTextArea();
		scrollPane_2.setViewportView(textArea);
		
		JButton btn = new JButton("\uC870\uD68C");
	      btn.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	            String place = (String) placeBox1.getSelectedItem();
	            String year = (String) yearBox1.getSelectedItem();
	            String month = (String) monthBox1.getSelectedItem();
	            String day = (String) dayBox1.getSelectedItem();

	            String body = cc.showMenuByDateClient(place, year, month, day, 0);
	            if(body.contains("0x0A/0x00")) {
	               int idx1 = body.indexOf('?');               
	               body = body.substring(idx1+1);
	            }
	            String bodyInfo[] = body.split("[/]+");

	            textArea.setText(body);
	         }
	      });
		btn.setBounds(298, 6, 95, 49);
		panel_1.add(btn);
//		
//		JScrollBar scrollBar = new JScrollBar();
//		scrollBar.setBounds(390, 78, 21, 101);
//		panel.add(scrollBar);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("식재료 신청자 명단 조회", null, panel_2, null);
		panel_2.setLayout(null);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBounds(12, 10, 399, 67);
		panel_2.add(panel_5);
		panel_5.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("\uAC15\uC758 \uC7A5\uC18C");
		lblNewLabel_1.setBounds(25, 10, 54, 15);
		panel_5.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("\uB144\uB3C4");
		lblNewLabel_2.setBounds(121, 10, 32, 15);
		panel_5.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("\uC6D4");
		lblNewLabel_3.setBounds(193, 10, 32, 15);
		panel_5.add(lblNewLabel_3);
		
		JLabel label_3 = new JLabel("\uC77C");
		label_3.setBounds(244, 10, 32, 15);
		panel_5.add(label_3);
		
		JComboBox placeBox = new JComboBox();
		placeBox.setModel(new DefaultComboBoxModel(new String[] {"\uBCF8\uC810", "\uC7A0\uC2E4\uC810", "\uAC15\uB0A8\uC810"}));
		placeBox.setBounds(0, 34, 96, 23);
		panel_5.add(placeBox);
		
		JComboBox yearBox = new JComboBox();
		yearBox.setModel(new DefaultComboBoxModel(new String[] {"2015", "2016", "2017", "2018", "2019"}));
		yearBox.setBounds(108, 34, 59, 23);
		panel_5.add(yearBox);
		
		JComboBox monthBox = new JComboBox();
		monthBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		monthBox.setBounds(182, 34, 43, 23);
		panel_5.add(monthBox);
		
		JComboBox dayBox = new JComboBox();
		dayBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		dayBox.setBounds(236, 34, 40, 23);
		panel_5.add(dayBox);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(12, 75, 399, 134);
		panel_2.add(scrollPane_1);
		
		JTextArea textArea_1 = new JTextArea();
		scrollPane_1.setViewportView(textArea_1);
		
		JButton btnNewButton = new JButton("\uC870\uD68C");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String place = (String) placeBox.getSelectedItem();
				String year = (String) yearBox.getSelectedItem();
				String month = (String) monthBox.getSelectedItem();
				String day = (String) dayBox.getSelectedItem();
				
				String str = cc.showApplicantListClient(place, year, month, day);
				
				textArea_1.setText(str);
			}
		});
		btnNewButton.setBounds(292, 6, 95, 51);
		panel_5.add(btnNewButton);
		
		JPanel panel_4 = new JPanel();
		tabbedPane.addTab("계좌이체 내역 등재", null, panel_4, null);
		panel_4.setLayout(null);
		
		JLabel lblNewLabel_4 = new JLabel("\uC2E0\uCCAD\uBC88\uD638");
		lblNewLabel_4.setBounds(30, 49, 54, 15);
		panel_4.add(lblNewLabel_4);
		
		applyNumber = new JTextField();
		applyNumber.setBounds(129, 46, 143, 21);
		panel_4.add(applyNumber);
		applyNumber.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("\uC785\uAE08\uC77C");
		lblNewLabel_5.setBounds(30, 99, 54, 15);
		panel_4.add(lblNewLabel_5);
		
		depositDate = new JTextField();
		depositDate.setColumns(10);
		depositDate.setBounds(129, 96, 143, 21);
		panel_4.add(depositDate);
		
		depositAmount = new JTextField();
		depositAmount.setColumns(10);
		depositAmount.setBounds(129, 145, 143, 21);
		panel_4.add(depositAmount);
		
		JLabel lblNewLabel_6 = new JLabel("\uC785\uAE08\uC561");
		lblNewLabel_6.setBounds(30, 148, 54, 15);
		panel_4.add(lblNewLabel_6);
		
		JButton btnNewButton_1 = new JButton("OK");
	      btnNewButton_1.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	            String regNum = applyNumber.getText();
	            String date = depositDate.getText();
	            String amount = depositAmount.getText();
	            
	            if(regNum.equals("") || date.equals("") || amount.equals("")) {
	               JOptionPane.showMessageDialog(null, "빈칸을 모두 채우시오.", null, JOptionPane.INFORMATION_MESSAGE);
	            }
	            
	            else
	               cc.registerTransferListClient(regNum, date, amount);
	         }
	      });
	      
		btnNewButton_1.setBounds(304, 75, 95, 59);
		panel_4.add(btnNewButton_1);
		
		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("식재료 소요량 목록 조회", null, panel_3, null);
		panel_3.setLayout(null);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBounds(12, 10, 399, 99);
		panel_3.add(panel_6);
		panel_6.setLayout(null);
		
		JLabel lblNewLabel_7 = new JLabel("\uAC15\uC758 \uC7A5\uC18C");
		lblNewLabel_7.setBounds(26, 10, 54, 15);
		panel_6.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("\uB144\uB3C4");
		lblNewLabel_8.setBounds(126, 10, 30, 15);
		panel_6.add(lblNewLabel_8);
		
		JLabel lblNewLabel_9 = new JLabel("\uC6D4");
		lblNewLabel_9.setBounds(192, 10, 30, 15);
		panel_6.add(lblNewLabel_9);
		
		JLabel lblNewLabel_10 = new JLabel("\uC77C");
		lblNewLabel_10.setBounds(246, 10, 30, 15);
		panel_6.add(lblNewLabel_10);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"\uBCF8\uC810", "\uC7A0\uC2E4\uC810", "\uAC15\uB0A8\uC810"}));
		comboBox.setBounds(12, 35, 93, 23);
		panel_6.add(comboBox);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"2015", "2016", "2017", "2018", "2019"}));
		comboBox_1.setBounds(112, 35, 54, 23);
		panel_6.add(comboBox_1);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		comboBox_2.setBounds(178, 35, 44, 23);
		panel_6.add(comboBox_2);
		
		JComboBox comboBox_3 = new JComboBox();
		comboBox_3.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		comboBox_3.setBounds(234, 35, 42, 23);
		panel_6.add(comboBox_3);
		
		JComboBox comboBox_4 = new JComboBox();
		comboBox_4.setModel(new DefaultComboBoxModel(new String[] {"선택", "2015", "2016", "2017", "2018", "2019"}));
		comboBox_4.setBounds(112, 66, 54, 23);
		panel_6.add(comboBox_4);
		
		JComboBox comboBox_5 = new JComboBox();
		comboBox_5.setModel(new DefaultComboBoxModel(new String[] {"선택", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		comboBox_5.setBounds(178, 66, 44, 23);
		panel_6.add(comboBox_5);
		
		JComboBox comboBox_6 = new JComboBox();
		comboBox_6.setModel(new DefaultComboBoxModel(new String[] {"선택", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		comboBox_6.setBounds(234, 66, 42, 23);
		panel_6.add(comboBox_6);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 111, 399, 88);
		panel_3.add(scrollPane);
		
		JTextArea textArea_2 = new JTextArea();
		scrollPane.setViewportView(textArea_2);
		
		JButton btnNewButton_2 = new JButton("\uC870\uD68C");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//String place = (String) comboBox.getSelectedItem();
				String place = "";
				
				String fromYear = (String) comboBox_1.getSelectedItem();
				String fromMonth = (String) comboBox_2.getSelectedItem();
				String fromDay = (String) comboBox_3.getSelectedItem();
				
				String toYear = (String) comboBox_4.getSelectedItem();
				String toMonth = (String) comboBox_5.getSelectedItem();
				String toDay = (String) comboBox_6.getSelectedItem();

				String result = cc.showRequiredAmountListClient(place, fromYear, fromMonth, fromDay, toYear, toMonth, toDay);
			
				textArea_2.setText(result);
			}
		});
		btnNewButton_2.setBounds(312, 7, 75, 79);
		panel_6.add(btnNewButton_2);
	}
}