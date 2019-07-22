package temp;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class Login extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private JButton btnNewButton;
	private JButton btnCancel;
	
	private ClientController cc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		cc = new ClientController();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("\uC18C\uC694\uB7C9\uC0B0\uCD9C\uC2DC\uC2A4\uD15C");
		contentPane.add(lblNewLabel, BorderLayout.NORTH);
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN,20));
		lblNewLabel.setHorizontalAlignment(JLabel.CENTER);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("ID");
		lblNewLabel_1.setBounds(45, 69, 55, 15);
		panel.add(lblNewLabel_1);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(45, 117, 79, 15);
		panel.add(lblPassword);
		
		textField = new JTextField();
		textField.setBounds(141, 66, 127, 21);
		panel.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(141, 114, 127, 21);
		panel.add(passwordField);
		
		btnNewButton = new JButton("OK");
		btnNewButton.setBounds(303, 65, 97, 23);
		panel.add(btnNewButton);
		btnNewButton.addActionListener(this);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(303, 113, 97, 23);
		panel.add(btnCancel);
		btnCancel.addActionListener(this);
		
		
	}
	
	public void actionPerformed(ActionEvent event) {		
		if (event.getSource() == btnNewButton) {
			String inputID = textField.getText();
			String inputPwd = String.valueOf(passwordField.getPassword());
			
			int result = cc.loginClient(inputID, inputPwd);
			
			if(result == 1) {
				Manager manaFrame = new Manager();
				this.setVisible(false);
				manaFrame.setVisible(true);
			} else if(result == 2) {
				Instructor instFrame = new Instructor();
				this.setVisible(false);
				instFrame.setVisible(true);
			} else if(result == 3) {
				Student studFrame = new Student();
				this.setVisible(false);
				studFrame.setVisible(true);
			} else {
				System.out.println("로그인 실패");
			}
		}
		else if (event.getSource() == btnCancel) {
			this.setVisible(false);
		}
	}
}
