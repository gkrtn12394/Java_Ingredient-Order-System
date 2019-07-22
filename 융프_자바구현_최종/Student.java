package temp;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import javax.swing.JTextField;

public class Student extends JFrame {

   private JPanel contentPane;
   private JPanel inquiryMenuPanel;
   private JPanel applicationIngredientPanel;
   private JPanel cancelIngredientPanel;

   /**
    * Launch the application.
    */
   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               Student frame = new Student();
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
   public Student() {
      super("Student");
      initializeComponents();
   }

   private void initializeComponents() {
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(100, 100, 450, 300);
      contentPane = new JPanel();
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      contentPane.setLayout(new BorderLayout(0, 0));
      setContentPane(contentPane);

      JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
      contentPane.add(tabbedPane, BorderLayout.CENTER);

      inquiryMenuPanel = new InquiryMenuPanel();
      tabbedPane.addTab("실습 메뉴 조회", null, inquiryMenuPanel, null);

      applicationIngredientPanel = new ApplicationIngredientPanel();
      tabbedPane.addTab("식재료 신청", null, applicationIngredientPanel, null);

      cancelIngredientPanel = new CancelIngredientPanel();
      tabbedPane.addTab("식재료 취소", null, cancelIngredientPanel, null);
   }
}

// 메뉴 조회 Panel
class InquiryMenuPanel extends JPanel {
   private JLabel placeLabel, yearLabel, monthLabel, dayLabel;
   private JComboBox placeComboBox, yearComboBox, monthComboBox, dayComboBox;
   private JButton inquiryBtn;
   private ClientController cc = new ClientController();
   private String data, place, year, month, day;
   private JTextField TextField;
   private String body;

   public InquiryMenuPanel () {
      setLayout(null);

      placeLabel = new JLabel("강의 장소");
      placeLabel.setBounds(55, 10, 55, 15);
      add(placeLabel);

      yearLabel = new JLabel("년도");
      yearLabel.setBounds(161, 10, 50, 15);
      add(yearLabel);

      monthLabel = new JLabel("월");
      monthLabel.setBounds(230, 10, 50, 15);
      add(monthLabel);

      dayLabel = new JLabel("일");
      dayLabel.setBounds(292, 10, 50, 15);
      add(dayLabel);


      placeComboBox = new JComboBox();
      placeComboBox.setModel(new DefaultComboBoxModel(new String[] {"본점", "잠실점", "강남점"}));
      placeComboBox.setBounds(28, 31, 101, 19);
      add(placeComboBox);
      placeComboBox.setSelectedIndex(-1);
      placeComboBox.addItemListener(new ItemListener() {
         public void itemStateChanged(ItemEvent arg0) {
            place = placeComboBox.getSelectedItem().toString();
         }
      });

      yearComboBox = new JComboBox();
      yearComboBox.setModel(new DefaultComboBoxModel(new String[] {"2015", "2016", "2017", "2018", "2019"}));
      yearComboBox.setBounds(141, 31, 64, 19);
      add(yearComboBox);
      yearComboBox.setSelectedIndex(-1);
      yearComboBox.addItemListener(new ItemListener() {
         public void itemStateChanged(ItemEvent arg0) {
            year = yearComboBox.getSelectedItem().toString();
         }
      });

      monthComboBox = new JComboBox();
      monthComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
      monthComboBox.setBounds(219, 31, 44, 19);
      add(monthComboBox);
      monthComboBox.setSelectedIndex(-1);
      monthComboBox.addItemListener(new ItemListener() {
         public void itemStateChanged(ItemEvent arg0) {
            month = monthComboBox.getSelectedItem().toString();
         }
      });

      dayComboBox = new JComboBox(new MyDayModel());
      dayComboBox.addItemListener(new ItemListener() {
         public void itemStateChanged(ItemEvent arg0) {
            day = dayComboBox.getSelectedItem().toString();
         }
      });
      dayComboBox.setBounds(275, 31, 50, 19);
      add(dayComboBox);

      inquiryBtn = new JButton("조회");
      inquiryBtn.setBounds(339, 10, 70, 40);
      inquiryBtn.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            if(place == null || year == null || month == null || day == null) {
               JOptionPane.showMessageDialog(null, "선택안한 값이 있습니다.", null, JOptionPane.INFORMATION_MESSAGE);
            }

            else {
               body = cc.showMenuByDateClient(place, year, month, day, 0);
               if(body.contains("0x0A/0x00")) {
                  int idx1 = body.indexOf('?');               
                  body = body.substring(idx1+1);
               }

               TextField.setText(body);
            }
         }
      });
      add(inquiryBtn);

      TextField = new JTextField();
      TextField.setBounds(28, 70, 367, 144);
      add(TextField);
   }
}

class ApplicationIngredientPanel extends JPanel {
   private JLabel placeLabel, yearLabel, monthLabel, dayLabel;
   private JComboBox placeComboBox, yearComboBox, monthComboBox, dayComboBox;
   private JButton inquiryBtn;
   private JLabel menuLabel, amountLabel;
   private JLabel menu1Label, menu2Label, menu3Label;
   // 메뉴 이름 , menu숫자NameLabel에 메뉴를 보여주면됩니다
   // ex) menu1NameLabel.setText(첫번째 메뉴 이름)
   private JLabel menu1NameLabel, menu2NameLabel, menu3NameLabel; 
   // 수량 입력을 위한 TextField
   private JTextField menu1TextField, menu2TextField, menu3TextField;
   private JLabel nameLabel, phoneNumLabel;
   private JTextField nameTextField, phoneNumTextField;
   private JButton applicationBtn;
   private ClientController cc = new ClientController();
   //private ApplyIngredientClient AIC = new ApplyIngredientClient();
   private String data, place, year, month, day;
   private String body;

   public ApplicationIngredientPanel() {
      setLayout(null);

      placeLabel = new JLabel("강의 장소");
      placeLabel.setBounds(55, 10, 55, 15);
      add(placeLabel);

      yearLabel = new JLabel("년도");
      yearLabel.setBounds(161, 10, 50, 15);
      add(yearLabel);

      monthLabel = new JLabel("월");
      monthLabel.setBounds(230, 10, 50, 15);
      add(monthLabel);

      dayLabel = new JLabel("일");
      dayLabel.setBounds(292, 10, 50, 15);
      add(dayLabel);

      placeComboBox = new JComboBox();
      placeComboBox = new JComboBox();
      placeComboBox.setModel(new DefaultComboBoxModel(new String[] {"본점", "잠실점", "강남점"}));
      placeComboBox.setBounds(28, 31, 101, 19);
      add(placeComboBox);
      placeComboBox.setSelectedIndex(-1);
      placeComboBox.addItemListener(new ItemListener() {
         public void itemStateChanged(ItemEvent arg0) {
            place = placeComboBox.getSelectedItem().toString();
         }
      });

      yearComboBox = new JComboBox();
      yearComboBox.setModel(new DefaultComboBoxModel(new String[] {"2015", "2016", "2017", "2018", "2019"}));
      yearComboBox.setBounds(141, 31, 64, 19);
      add(yearComboBox);
      yearComboBox.setSelectedIndex(-1);
      yearComboBox.addItemListener(new ItemListener() {
         public void itemStateChanged(ItemEvent arg0) {
            year = yearComboBox.getSelectedItem().toString();
         }
      });

      monthComboBox = new JComboBox();
      monthComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
      monthComboBox.setBounds(219, 31, 44, 19);
      add(monthComboBox);
      monthComboBox.setSelectedIndex(-1);
      monthComboBox.addItemListener(new ItemListener() {
         public void itemStateChanged(ItemEvent arg0) {
            month = monthComboBox.getSelectedItem().toString();
         }
      });

      dayComboBox = new JComboBox(new MyDayModel());
      dayComboBox.addItemListener(new ItemListener() {
         public void itemStateChanged(ItemEvent arg0) {
            day = dayComboBox.getSelectedItem().toString();
         }
      });
      dayComboBox.setBounds(275, 31, 50, 19);
      add(dayComboBox);

      inquiryBtn = new JButton("조회");
      // 조회 버튼을 누르면 특정 날짜와 장소에 대한
      // 메뉴를  menu숫자NameLabel에 보여준다
      inquiryBtn.setBounds(339, 10, 70, 40);
      inquiryBtn.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            if(place == null || year == null || month == null || day == null) {
               JOptionPane.showMessageDialog(null, "선택안한 값이 있습니다.", null, JOptionPane.INFORMATION_MESSAGE);
            }

            
            else {
               body = cc.showMenuByDateClient(place, year, month, day, 1);

               if(body.contains("0x0A/0x00")) {
                  int idx1 = body.indexOf('?');               
                  body = body.substring(idx1+1);

                  JOptionPane.showMessageDialog(null, body, null, JOptionPane.INFORMATION_MESSAGE);
               }
               else if(body.contains("0x0A/0x02")) {
                  int idx1 = body.indexOf('?');               
                  body = body.substring(idx1+1);

                  JOptionPane.showMessageDialog(null, body, null, JOptionPane.INFORMATION_MESSAGE);
               }

               String menu1 = "", price1 = "";
               String menu2 = "", price2 = "";
               String menu3 = "", price3 = "";

               menu1NameLabel.setText(menu1);         
               menu2NameLabel.setText(menu2);            
               menu3NameLabel.setText(menu3);

               String inquiryInfo[] = body.split("[/]+");

               int count = inquiryInfo.length;

               for(int i = 1; i < count; i++, i++) {
                  if(i == 1)
                     menu1NameLabel.setText(inquiryInfo[i]);
                  else if(i == 3)
                     menu2NameLabel.setText(inquiryInfo[i]);
                  else if(i == 5)
                     menu3NameLabel.setText(inquiryInfo[i]);
               }
            }
         }
      });
      inquiryBtn.setBounds(339, 10, 70, 40);
      add(inquiryBtn);

      menuLabel = new JLabel("메뉴 이름");
      menuLabel.setBounds(100, 70, 70, 15);
      add(menuLabel);

      amountLabel = new JLabel("수량");
      amountLabel.setBounds(203, 70, 30, 15);
      add(amountLabel);

      menu1Label = new JLabel("메뉴 1 : ");
      menu1Label.setBounds(30, 100, 50, 15);
      add(menu1Label);

      menu1NameLabel = new JLabel();
      menu1NameLabel.setBounds(75, 100, 120, 15);
      add(menu1NameLabel);

      menu1TextField = new JTextField();
      menu1TextField.setBounds(200, 97, 35, 20);
      add(menu1TextField);

      menu2Label = new JLabel("메뉴 2 : ");
      menu2Label.setBounds(30, 135, 50, 15);
      add(menu2Label);

      menu2NameLabel = new JLabel();
      menu2NameLabel.setBounds(75, 135, 120, 15);
      add(menu2NameLabel);

      menu2TextField = new JTextField();
      menu2TextField.setBounds(200, 132, 35, 20);
      add(menu2TextField);

      menu3Label = new JLabel("메뉴 3 : ");
      menu3Label.setBounds(30, 170, 50, 15);
      add(menu3Label);

      menu3NameLabel = new JLabel();
      menu3NameLabel.setBounds(75, 170, 120, 15);
      add(menu3NameLabel);

      menu3TextField = new JTextField();
      menu3TextField.setBounds(200, 167, 35, 20);
      add(menu3TextField);

      nameLabel = new JLabel("이름");
      nameLabel.setBounds(255, 100, 35, 15);
      add(nameLabel);

      nameTextField = new JTextField();
      nameTextField.setBounds(315, 100, 100, 20);
      add(nameTextField);

      phoneNumLabel = new JLabel("전화번호");
      phoneNumLabel.setBounds(255, 130, 50, 15);
      add(phoneNumLabel);

      phoneNumTextField = new JTextField();
      phoneNumTextField.setBounds(315, 130, 100, 20);
      add(phoneNumTextField);

      applicationBtn = new JButton("신청");
      applicationBtn.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            String name = nameTextField.getText();
            String phoneNum = phoneNumTextField.getText();
            String menu1 = menu1TextField.getText();
            String menu2 = menu2TextField.getText();
            String menu3 = menu3TextField.getText();

            if(menu1.equals("") || menu2.equals("") || menu3.equals("") || name.equals("") || phoneNum.equals("")) {
               JOptionPane.showMessageDialog(null, "빈칸을 모두 채우시오. (신청을 원하지 않는 메뉴의 수량은 0으로 채우시오).", null, JOptionPane.INFORMATION_MESSAGE);
            }

            else {
               String dateInfo = place + "/" + year + "/" + month + "/" + day;
               String applyInfo = name + "/" + phoneNum + "/" + menu1 + "/" + menu2 + "/" + menu3;
               body = cc.applyIngredientsClient(dateInfo, applyInfo);

               JOptionPane.showMessageDialog(null, body, "신청번호/입금해야 할 금액", JOptionPane.INFORMATION_MESSAGE);
            }
         }
      });
      applicationBtn.setBounds(340, 170, 70, 40);
      add(applicationBtn);
   }
}

// 식재료 취소 Panel
class CancelIngredientPanel extends JPanel {
   private JLabel appNumLabel; // 신청번호 Label
   private JTextField appNumTextField; // 신청번호 TextField
   private JTextField nameTextField;   // 이름 TextField
   private JTextField phoneNumTextField; // 전화번호 TextField
   private ClientController cc = new ClientController();
   private String body;

   public CancelIngredientPanel() {
      setLayout(null);

      // 신청번호 Label
      appNumLabel = new JLabel("신청번호");
      appNumLabel.setBounds(46, 35, 50, 15);
      add(appNumLabel);

      // 신청번호 TextField
      appNumTextField = new JTextField();
      appNumTextField.setBounds(127, 32, 142, 21);
      add(appNumTextField);
      appNumTextField.setColumns(10);

      // 이름 Label
      JLabel nameLabel = new JLabel("이름");
      nameLabel.setBounds(46, 86, 50, 15);
      add(nameLabel);

      // 이름 TextField
      nameTextField = new JTextField();
      nameTextField.setBounds(127, 83, 142, 21);
      add(nameTextField);
      nameTextField.setColumns(10);

      // 전화번호 Label
      JLabel phoneNumLabel = new JLabel("전화번호");
      phoneNumLabel.setBounds(46, 134, 50, 15);
      add(phoneNumLabel);

      // 전화번호 TextField
      phoneNumTextField = new JTextField();
      phoneNumTextField.setBounds(127, 131, 142, 21);
      add(phoneNumTextField);
      phoneNumTextField.setColumns(10);

      // 취소 Button
      JButton cancelBtn = new JButton("취소");
      cancelBtn.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            String appNum = appNumTextField.getText();
            String name = nameTextField.getText();
            String phoneNum = phoneNumTextField.getText();

            if(appNum.equals("") || name.equals("") || phoneNum.equals("")) {
               JOptionPane.showMessageDialog(null, "빈칸을 모두 채우시오.", null, JOptionPane.INFORMATION_MESSAGE);
            }

            else {
               String s = "";
               s = appNum + "/" + name + "/" + phoneNum;
               body = cc.cancelIngredientsClient(s);

               if(body.contains("0x0C/0x20")) {
                  int idx1 = body.indexOf('?');               
                  body = body.substring(idx1+1);

                  JOptionPane.showMessageDialog(null, body, null, JOptionPane.INFORMATION_MESSAGE);
               }
               else if(body.contains("0x0C/0x21")) {
                  int idx1 = body.indexOf('?');               
                  body = body.substring(idx1+1);

                  JOptionPane.showMessageDialog(null, body, null, JOptionPane.INFORMATION_MESSAGE);
               }
               else if(body.contains("0x0C/0x22")) {
                  int idx1 = body.indexOf('?');               
                  body = body.substring(idx1+1);

                  JOptionPane.showMessageDialog(null, body, null, JOptionPane.INFORMATION_MESSAGE);
               }
            }
         }
      });
      cancelBtn.setBounds(298, 164, 85, 37);
      add(cancelBtn);
   }
}

class MyDayModel extends AbstractListModel implements ComboBoxModel {
   String[] days = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10"
         , "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"
         , "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"
   };

   String selection = null;

   public Object getElementAt(int index) {
      return days[index];
   }

   public int getSize() {
      return days.length;
   }

   public void setSelectedItem(Object anItem) {
      selection = (String) anItem; // to select and register an
   } // item from the pull-down list

   // Methods implemented from the interface ComboBoxModel
   public Object getSelectedItem() {
      return selection; // to add the selection to the combo box
   }
}