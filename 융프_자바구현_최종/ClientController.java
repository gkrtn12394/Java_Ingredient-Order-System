package temp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import javax.swing.JOptionPane;


public class ClientController {
   final int PORT = 8000;
   final String ServerIP = "192.168.215.161";

   public int loginClient(String inputID, String inputPwd) {
      Socket socket = null;
      InputStream is;
      BufferedReader reader;
      OutputStream os;
      BufferedWriter writer;

      try {
         socket = new Socket(ServerIP, PORT);
         System.out.println("Client Start!!");
         os = socket.getOutputStream();
         is = socket.getInputStream();

         reader = new BufferedReader(new InputStreamReader(is));
         writer = new BufferedWriter(new OutputStreamWriter(os));

         //// 서버 접근 요청 ////
         String head = "LOGIN";

         writer.write(head + '\r' + '\n');
         writer.flush();

         System.out.println("서버 접근 요청 중....");

         //// 접근 요청 응답 대기 ////
         String rcvMsg = reader.readLine();
         System.out.println("msg : " + rcvMsg);

         if (rcvMsg.contains("-1"))
            return -1;

         //// 로그인요청 ////
         head = "LOGIN";
         String ID = inputID;
         String PWD = inputPwd;

         writer.write(head + '/' + ID + '/' + PWD + '\r' + '\n');
         writer.flush();

         System.out.println("로그인 요청 중....");

         //// 로그인 요청에 대한 응답 ////
         rcvMsg = reader.readLine();
         System.out.println("msg : " + rcvMsg);

         if (rcvMsg.contains("-1"))
            return -1;
         else {
            System.out.println("로그인 성공!");
         }

         int iMsg = Integer.parseInt(rcvMsg);

         return iMsg;

      } catch (ConnectException exception) {
         System.out.println("서버 안 열림");
      } catch (Exception exception) {
         exception.printStackTrace();
      }

      return -1;
   }

   public void recipeLoadClient(String filePath) { // 1.1
      byte[] buffer = new byte[10000];
      int readBytes = 0;

      Socket socket = null;
      InputStream is;
      BufferedReader reader;
      OutputStream os;
      BufferedWriter writer;

      try {
         socket = new Socket(ServerIP, PORT);
         System.out.println("Client Start!!");
         os = socket.getOutputStream();
         is = socket.getInputStream();

         reader = new BufferedReader(new InputStreamReader(is));
         writer = new BufferedWriter(new OutputStreamWriter(os));

         //// 서버 접근 요청 ////
         String head = "RECIPELOAD";

         writer.write(head + '\r' + '\n');
         writer.flush();

         System.out.println("서버 접근 요청 중....");

         //// 접근 요청 응답 대기 ////
         String rcvMsg = reader.readLine();
         System.out.println("msg : " + rcvMsg);

         if (rcvMsg.contains("-1"))
            return;

         FileInputStream fis = new FileInputStream(filePath);

         if (!socket.isConnected()) {
            System.out.println("Socket Connect Error.");
            System.exit(0);
         }

         while ((readBytes = fis.read(buffer)) > 0) {
            os.write(buffer, 0, readBytes);
         }

         fis.close();
         os.close();
         socket.close();
      } catch (UnknownHostException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public void scheduleLoadClient(String filePath) { // 1.2
      byte[] buffer = new byte[10000];
      int readBytes = 0;

      Socket socket = null;
      InputStream is;
      BufferedReader reader;
      OutputStream os;
      BufferedWriter writer;

      try {
         socket = new Socket(ServerIP, PORT);
         System.out.println("Client Start!!");
         os = socket.getOutputStream();
         is = socket.getInputStream();

         reader = new BufferedReader(new InputStreamReader(is));
         writer = new BufferedWriter(new OutputStreamWriter(os));

         //// 서버 접근 요청 ////
         String head = "SCHEDULELOAD";

         writer.write(head + '\r' + '\n');
         writer.flush();

         System.out.println("서버 접근 요청 중....");

         //// 접근 요청 응답 대기 ////
         String rcvMsg = reader.readLine();
         System.out.println("msg : " + rcvMsg);

         if (rcvMsg.contains("-1"))
            return;

         //// 파일 전송 ////
         FileInputStream fis = new FileInputStream(filePath);

         if (!socket.isConnected()) {
            System.out.println("Socket Connect Error.");
            System.exit(0);
         }

         while ((readBytes = fis.read(buffer)) > 0) {
            os.write(buffer, 0, readBytes);
         }

         fis.close();
         os.close();
         socket.close();
      } catch (UnknownHostException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public void registerMenuByDateClient(String filePath) { // 1.3
      byte[] buffer = new byte[10000];
      int readBytes = 0;

      Socket socket = null;
      InputStream is;
      BufferedReader reader;
      OutputStream os;
      BufferedWriter writer;

      try {
         socket = new Socket(ServerIP, PORT);
         System.out.println("Client Start!!");
         os = socket.getOutputStream();
         is = socket.getInputStream();

         reader = new BufferedReader(new InputStreamReader(is));
         writer = new BufferedWriter(new OutputStreamWriter(os));

         //// 서버 접근 요청 ////
         String head = "REGISTERMENUBYDATE";

         writer.write(head + '\r' + '\n');
         writer.flush();

         System.out.println("서버 접근 요청 중....");

         //// 접근 요청 응답 대기 ////
         String rcvMsg = reader.readLine();
         System.out.println("msg : " + rcvMsg);

         if (rcvMsg.contains("-1"))
            return;

         FileInputStream fis = new FileInputStream(filePath);

         if (!socket.isConnected()) {
            System.out.println("Socket Connect Error.");
            System.exit(0);
         }

         while ((readBytes = fis.read(buffer)) > 0) {
            os.write(buffer, 0, readBytes);
         }

         fis.close();
         os.close();
         socket.close();
      } catch (UnknownHostException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

    public String showMenuByDateClient(String place, String year, String month, String day, int btn) { // 2.1, 3.1
         Socket socket = null;
         InputStream is;
         BufferedReader reader;
         OutputStream os;
         BufferedWriter writer;

         try {
            socket = new Socket(ServerIP, PORT);
            System.out.println("Client Start!!");

            os = socket.getOutputStream();
            is = socket.getInputStream();

            reader = new BufferedReader(new InputStreamReader(is));
            writer = new BufferedWriter(new OutputStreamWriter(os));

            String head, body;

            //// 서버 접근 요청 ////
            head = "SHOWMENUBYDATE";

            writer.write(head + '\r' + '\n');
            writer.flush();

            System.out.println("서버 접근 요청 중....");

            //// 접근 요청 응답 대기 ////
            String rcvMsg = reader.readLine();
            System.out.println("msg : " + rcvMsg);

            if (rcvMsg.contains("-1"))
               return null;

            //// 일자별 메뉴 조회 요청 ////
            if(btn == 0) {
               head = "0x09/0x00?";
               body = place + "/" + year + "-" + month + "-" + day;

               System.out.println("body:" + body);

               writer.write(head+body+'\r'+'\n');
               writer.flush();

               System.out.println("일자별 메뉴 조회 요청중....");

               rcvMsg = reader.readLine();

               System.out.println("msg : " + rcvMsg);

               if (rcvMsg.contains("0x0A/0x01")) {
                  System.out.println("전송 성공...");
                  int idx1 = rcvMsg.indexOf('?');               
                  body = rcvMsg.substring(idx1+1);
                  return body;
               } else if (rcvMsg.contains("0x0A/0x00")) {
                  System.out.println("전송 실패...");

                  body = rcvMsg + "수업이 없는 날짜입니다.";

                  return body;
               }
            }
            else if(btn == 1){
               head = "0x09/0x01?";
               body = place + "/" + year + "-" + month + "-" + day;

               System.out.println("body:" + body);

               writer.write(head+body+'\r'+'\n');
               writer.flush();

               System.out.println("일자별 메뉴 조회 요청중....");

               rcvMsg = reader.readLine();

               System.out.println("msg : " + rcvMsg);

               if (rcvMsg.contains("0x0A/0x01")) {
                  System.out.println("전송 성공...");
                  int idx1 = rcvMsg.indexOf('?');               
                  body = rcvMsg.substring(idx1+1);
                  return body;
               } else if (rcvMsg.contains("0x0A/0x00")) {
                  System.out.println("전송 실패...");

                  body = rcvMsg + "수업이 없는 날짜입니다.";

                  return body;
               } else if (rcvMsg.contains("0x0A/0x02")) {            
                  body = rcvMsg + "신청 기간이 아닙니다.";

                  return body;
               }  
            }
         } catch (ConnectException e) {
            System.out.println("서버 안 열림");
         } catch (Exception e) {
            e.printStackTrace();
         }
         return null;
      }
   public String showApplicantListClient(String place, String year, String month, String day) { // 2.2
      if (Integer.parseInt(month) >= 1 && (Integer.parseInt(month) <= 9))
         month = "0" + month;
      if (Integer.parseInt(day) >= 1 && (Integer.parseInt(day) <= 9))
         day = "0" + day;

      String date = year + "-" + month + "-" + day;

      Socket socket = null;
      InputStream is;
      BufferedReader reader;
      OutputStream os;
      BufferedWriter writer;

      try {
         socket = new Socket(ServerIP, PORT);
         System.out.println("Client Start!!");

         os = socket.getOutputStream();
         is = socket.getInputStream();

         reader = new BufferedReader(new InputStreamReader(is));
         writer = new BufferedWriter(new OutputStreamWriter(os));
         
         //// 서버 접근 요청 ////
         String head = "SHOWAPPLICANTLIST";

         writer.write(head + '\r' + '\n');
         writer.flush();

         System.out.println("서버 접근 요청 중....");

         //// 접근 요청 응답 대기 ////
         String rcvMsg = reader.readLine();
         System.out.println("msg : " + rcvMsg);

         if (rcvMsg.contains("-1"))
            return null;

         //// 식재료신청자명단조회요청 ////

         head = "0x09/0x10/17";
         String body = place + "/" + date;

         writer.write(head + "/" + body + '\r' + '\n');
         writer.flush();

         System.out.println("식재료 신청자 명단 조회 요청 중....");

         //// 식재료신청자명단조회요청에 대한 응답 ////
         rcvMsg = reader.readLine();
         System.out.println("msg : " + rcvMsg);
         
         String lineMerge = "";

         if (rcvMsg.contains("0x0A/0x10")) {
            System.out.println("요청 거절... 시스템 종료");
            return null;
         } else if (rcvMsg.contains("0x0A/0x11")) {
            String[] result = rcvMsg.split("/"); // 서버로부터 받은 메세지 /로 잘라서 넣음

            System.out.println("----------");

            for (int k = 0; k < result.length; k++) {
               System.out.println(k + " : " + result[k]);
            }

            int number = Integer.parseInt(result[2]); // result[2]에 신청자 수에 대한 정보있음

            int cnt = 3;

            String[][] information = new String[number][3];

            for (int i = 0; i < information.length; i++) {
               for (int j = 0; j < information[i].length; j++) {
                  if ((j == 2) && (result[cnt].equals("9999-12-31")))
                     information[i][j] = "NO";
                  else if ((j == 2) && (!result[cnt].equals("9999-12-31")))
                     information[i][j] = "YES";
                  else
                     information[i][j] = result[cnt];
                  cnt++;
               }
            }

            for (int i = 0; i < information.length; i++) {
               for (int j = 0; j < information[i].length; j++) {
                  lineMerge = lineMerge + information[i][j] + '/';
               }
               lineMerge = lineMerge + '\n';
            }
         }
         
         return lineMerge;
      } catch (ConnectException exception) {
         System.out.println("서버 안 열림");
      } catch (Exception exception) {
         exception.printStackTrace();
      }
      
      return null;
   }

   public String showRequiredAmountListClient(String place, String fromYear, String fromMonth, String fromDay, String toYear, String toMonth, String toDay) { // 2.3
      Socket socket = null;
      InputStream is;
      BufferedReader reader;
      OutputStream os;
      BufferedWriter writer;

      String buf;

      try {
         socket = new Socket(ServerIP, PORT);
         System.out.println("Client Start!!");

         os = socket.getOutputStream();
         is = socket.getInputStream();

         reader = new BufferedReader(new InputStreamReader(is));
         writer = new BufferedWriter(new OutputStreamWriter(os));

         //// 서버 접근 요청 ////
         String head = "SHOWREQUIREDAMOUNTLIST";

         writer.write(head + '\r' + '\n');
         writer.flush();

         System.out.println("서버 접근 요청 중....");

         //// 접근 요청 응답 대기 ////
         String rcvMsg = reader.readLine();
         System.out.println("msg : " + rcvMsg);

         if (rcvMsg.contains("-1"))
            return null;

         //// 파일 전송 요청 ////
         
         if(toYear.equals("선택")) {
            head = "0x09/0x20/0?";
   
            String body = place + "/" + fromYear + "/" + fromMonth + "/" + fromDay;
            System.out.println("일자 전송...");
            System.out.println("body: " + body);
   
            writer.write(head + body + '\r' + '\n');
            writer.flush();
         } else {         
            head = "0x09/0x21/0?";
   
            String body = place + "/" + fromYear + "/" + fromMonth + "/" + fromDay + "/" + toYear + "/" + toMonth + "/" + toDay;
            System.out.println("일자 전송...");
            System.out.println("body: " + body);
   
            writer.write(head + body + '\r' + '\n');
            writer.flush();
         }
         
         //// 소요량 받기 ////

         buf = "";
         String result = "";
         while ((buf = reader.readLine()) != null) {
            System.out.println("Server: 파일 받기");
            if (buf.contains("0x0A/0x21")) {

               System.out.println("소요량 확인 완료...");
               System.out.println(buf);

               int idx1 = buf.indexOf('/', 8);
               int idx2 = buf.indexOf('?', idx1);

               String sCount = buf.substring(idx1 + 1, idx2);
               int count = Integer.parseInt(sCount);

               for (int i = 0; i < count; i++) {
                  idx1 = idx2;
                  int temp = buf.indexOf('/', idx1 + 1);
                  idx2 = buf.indexOf('/', temp + 1);

                  String ingredientName = buf.substring(idx1 + 1, temp);
                  String amount = buf.substring(temp + 1, idx2);
                  
                  result = result + "ingredientName: " + ingredientName + ", amount: " + amount + "\n";

                  System.out.println("ingredientName: " + ingredientName + ", amount: " + amount);
               }
            } else {
               System.out.println("소요량 조회 거부...");
            }
         }
         
         //// 연결 종료 ////

         writer.flush();
         writer.close();

         reader.close();

         socket.close();
         
         return result;

      } catch (ConnectException e) {
         System.out.println("서버 안 열림");
      } catch (Exception e) {
         e.printStackTrace();
      }
      return null;
   }

   public void registerTransferListClient(String regNum, String date, String amount) { // 2.4
      Socket socket = null;
      InputStream is;
      BufferedReader reader;
      OutputStream os;
      BufferedWriter writer;
      try {
         socket = new Socket(ServerIP, PORT);
         System.out.println("Client Start!!");
         os = socket.getOutputStream();
         is = socket.getInputStream();

         reader = new BufferedReader(new InputStreamReader(is));
         writer = new BufferedWriter(new OutputStreamWriter(os));

         //// 서버 접근 요청 ////
         String head = "REGISTERTRANSFERLIST";

         writer.write(head + '\r' + '\n');
         writer.flush();

         System.out.println("서버 접근 요청 중....");

         //// 접근 요청 응답 대기 ////
         String rcvMsg = reader.readLine();
         System.out.println("msg : " + rcvMsg);

         if (rcvMsg.contains("-1"))
            return;

         //// 입금 내역 갱신 요청 ////
         head = "0x0B/0x00/25";
         String body = regNum + "/" + date + "/" + amount;

         writer.write(head + "/" + body + '\r' + '\n');
         writer.flush();

         System.out.println("입금 내역 갱신 요청 중....");

         //// 입급 내역 갱신 요청에 대한 응답 ////
         rcvMsg = reader.readLine();
         System.out.println("msg : " + rcvMsg);

         if (rcvMsg.contains("0x0C/0x00")) {
            System.out.println("요청 거절... 시스템 종료");
            return;
         } else if (rcvMsg.contains("0x0C/0x01")) {
            JOptionPane.showMessageDialog(null, "갱신되었습니다");
         }
      } catch (ConnectException exception) {
         System.out.println("서버 안 열림");
      } catch (Exception exception) {
         exception.printStackTrace();
      }
   }

   public String applyIngredientsClient(String dateInfo, String applyInfo) { // 3.2
         Socket socket = null;
         InputStream is;
         BufferedReader reader;
         OutputStream os;
         BufferedWriter writer;

         try {
            socket = new Socket(ServerIP, PORT);
            System.out.println("Client Start!!");

            os = socket.getOutputStream();
            is = socket.getInputStream();

            reader = new BufferedReader(new InputStreamReader(is));
            writer = new BufferedWriter(new OutputStreamWriter(os));

            String head, body;

            //// 서버 접근 요청 ////
            head = "APPLYINGREDIENTS";

            writer.write(head + '\r' + '\n');
            writer.flush();

            System.out.println("서버 접근 요청 중....");

            //// 접근 요청 응답 대기 ////
            String rcvMsg = reader.readLine();
            System.out.println("msg : " + rcvMsg);

            if (rcvMsg.contains("-1"))
               return null;

            //// 식재료 신청 ////
            head = "0x0B/0x10?";
            body = dateInfo + "/" + applyInfo;

            writer.write(head + body + '\r' + '\n');
            writer.flush();

            System.out.println("식재료 신청 요청중....");

            rcvMsg = reader.readLine();

            System.out.println("msg : " + rcvMsg);

            if (rcvMsg.contains("0x0C/0x10")) {
               System.out.println("전송 실패...");
            } else if (rcvMsg.contains("0x0C/0x11")) {
               System.out.println("전송 성공...");
               int idx1 = rcvMsg.indexOf('?');
               body = rcvMsg.substring(idx1 + 1);
               return body;
            }
         } catch (ConnectException e) {
            System.out.println("서버 안 열림");
         } catch (Exception e) {
            e.printStackTrace();
         }
         return null;
      }

      public String cancelIngredientsClient(String s) { // 3.3
         Socket socket = null;
         InputStream is;
         BufferedReader reader;
         OutputStream os;
         BufferedWriter writer;

         try {
            socket = new Socket(ServerIP, PORT);
            System.out.println("Client Start!!");

            os = socket.getOutputStream();
            is = socket.getInputStream();

            reader = new BufferedReader(new InputStreamReader(is));
            writer = new BufferedWriter(new OutputStreamWriter(os));

            //// 서버 접근 요청 ////
            String head = "CANCLEINGREDIENTS";

            writer.write(head + '\r' + '\n');
            writer.flush();

            System.out.println("서버 접근 요청 중....");

            //// 접근 요청 응답 대기 ////
            String rcvMsg = reader.readLine();
            System.out.println("msg : " + rcvMsg);

            if (rcvMsg.contains("-1"))
               return null;

            //// 식재료 취소 요청 ////
            head = "0x0B/0x20?";
            String body = s;

            writer.write(head + body + '\r' + '\n');
            writer.flush();

            System.out.println("식재료 취소 요청중....");

            rcvMsg = reader.readLine();

            System.out.println("msg : " + rcvMsg);

            if (rcvMsg.contains("0x0C/0x20")) {
               System.out.println("전송 실패...");

               body = rcvMsg + "일치하는 정보가 없습니다.";

               return body;            
            } else if (rcvMsg.contains("0x0C/0x21")) {
               System.out.println("전송 성공...");

               body = rcvMsg + "성공적으로 취소 되었습니다.";

               return body;
            } else if (rcvMsg.contains("0x0C/0x22")) {
               body = rcvMsg + "취소기간이 아닙니다.";

               return body;
            }
         } catch (ConnectException e) {
            System.out.println("서버 안 열림");
         } catch (Exception e) {
            e.printStackTrace();
         }
         return null;
      }
   }