package temp;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

public class MenuRegister extends JFrame {
	private JPanel contentPane;
	private JTextField textField;
	private int flag;
	private ClientController cc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuRegister frame = new MenuRegister(3);
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
	public MenuRegister(int flag) {
		this.flag = flag;
		cc = new ClientController();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(49, 98, 342, 77);
		contentPane.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNewLabel = new JLabel("\uD30C\uC77C\uACBD\uB85C");
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("\uD30C\uC77C\uC120\uD0DD");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc=new JFileChooser();	//객체 생성
				FileNameExtensionFilter defaultFilter;	//디폴트 필터 지정위한 
				jfc.addChoosableFileFilter(defaultFilter=new FileNameExtensionFilter("엑셀 파일(*.xlsx)","xlsx"));
				jfc.setFileFilter(defaultFilter);;	//setFileFilter메서드로 디폴트필터 지정
				int res=jfc.showOpenDialog(null);
				//대화상자 출력
				if(res==JFileChooser.APPROVE_OPTION) {	//파일 선택 후 열기버튼 누른 경우
					File f=jfc.getSelectedFile();
					textField.setText(f.getPath());
				}
				if(res==JFileChooser.CANCEL_OPTION||res==JFileChooser.ERROR_OPTION) {	
					//취소||선택없이 대화상자 닫기or오류
					JOptionPane.showMessageDialog(null,"파일을 선택하지 않았습니다.");
					return;
				}
			}
		});
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("upload");
		panel.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {		
			public void actionPerformed(ActionEvent e) {
				String filePath = textField.getText();	
				
				System.out.println("filePath: " + filePath);
				
				if(flag == 1) {
					cc.recipeLoadClient(filePath);
				} else if(flag == 2) {
					cc.scheduleLoadClient(filePath);
				} else if(flag == 3) {
					cc.registerMenuByDateClient(filePath);
				}
			}		
		});
	}
}
