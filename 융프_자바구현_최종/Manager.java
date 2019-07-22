package temp;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;

public class Manager extends JFrame implements ActionListener {

	private JPanel contentPane;
	
	private JButton btnNewButton;
	private JButton btnLoad;
	private  JButton button_1;
	private ClientController cc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Manager frame = new Manager();
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
	public Manager() {
		cc = new ClientController();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("1. Recipe Load");
		lblNewLabel.setBounds(65, 48, 164, 38);
		contentPane.add(lblNewLabel);
		
		JLabel lblLoad = new JLabel("2. \uAC15\uC758\uC77C\uC815\uD45C Load");
		lblLoad.setBounds(65, 114, 164, 38);
		contentPane.add(lblLoad);
		
		JLabel label_1 = new JLabel("3. \uAC15\uC758\uC77C\uC790\uBCC4\uC2E4\uC2B5\uBA54\uB274 \uB4F1\uB85D");
		label_1.setBounds(65, 183, 170, 38);
		contentPane.add(label_1);
		
		btnNewButton = new JButton("load");
		btnNewButton.setBounds(296, 56, 97, 23);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(this);
		
		btnLoad = new JButton("load");
		btnLoad.setBounds(296, 122, 97, 23);
		contentPane.add(btnLoad);
		btnLoad.addActionListener(this);
		
		button_1 = new JButton("\uB4F1\uB85D");
		button_1.setBounds(296, 191, 97, 23);
		contentPane.add(button_1);
		button_1.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == btnNewButton) {
			MenuRegister menuRegFrame = new MenuRegister(1);
			this.setVisible(false);
			menuRegFrame.setVisible(true);
		} else if(event.getSource() == btnLoad) {
			MenuRegister menuRegFrame = new MenuRegister(2);
			this.setVisible(false);
			menuRegFrame.setVisible(true);
		} else if(event.getSource() == button_1) {
			MenuRegister menuRegFrame = new MenuRegister(3);
			this.setVisible(false);
			menuRegFrame.setVisible(true);
		}
	}
}
