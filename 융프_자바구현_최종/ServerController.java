package temp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class ServerController {
	private static int serialNum = 0;

	public void loginServer(ServerSocket sock, Socket client, BufferedReader reader, BufferedWriter writer) { // 0
		try {
			System.out.println("client accept!");
			System.out.println("Ŭ���̾�Ʈ ���� IP[" + sock.getInetAddress() + "]");
			System.out.println("Ŭ���̾�Ʈ ���� PORT[" + sock.getLocalPort() + "]");
			System.out.println("IP[" + sock.getInetAddress() + "]");

			//// ��û ��� ////
			boolean isReqd = false;
			String str = "";

			str = reader.readLine();

			System.out.println("Server: ��û���");

			if (str.contains("LOGIN")) {
				System.out.println("str: " + str);
				isReqd = true;
				System.out.println("�α��� ��û ����");
			}

			//// ��û�� ���� ���� ���� ////
			if (!isReqd) {
				str = "0x0A/0x10/-1//";
				System.out.println("��û ����...");
			} else {
				String[] result = str.split("/");
				String ID = result[1];
				String PWD = result[2];

				System.out.println("ID: " + ID + " PWD: " + PWD);

				if (ID.equals("M1234") && PWD.equals("5678")) {
					str = "1";
				} else if (ID.equals("I1234") && PWD.equals("5678")) {
					str = "2";
				} else if (ID.equals("S1234") && PWD.equals("5678")) {
					str = "3";
				} else {
					System.out.println("���̵� or ��й�ȣ�� Ʋ���ϴ�.");
					str = "-1";
				}
			}

			writer.write(str + '\r' + '\n');
			System.out.println("str: " + str);
			writer.flush();

			client.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void recipeLoadServer(Socket client, InputStream is) { // 1.1 
		final int DEFAULT_BUFFER_SIZE = 10000;

		String filename = "recipe.csv"; // ������ ���� �̸�

		try {
			System.out.println("This server is listening... (Port: 8000)");
			InetSocketAddress isaClient = (InetSocketAddress) client.getRemoteSocketAddress();

			System.out.println("A client(" + isaClient.getAddress().getHostAddress() + " is connected. (Port: "
					+ isaClient.getPort() + ")");

			FileOutputStream fos = new FileOutputStream(filename);

			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			int readBytes;
			while ((readBytes = is.read(buffer)) != -1) {
				fos.write(buffer, 0, readBytes);
			}

			System.out.println("���� ���ε� �Ϸ�!");

			is.close();
			fos.close();
			client.close();
			readRecipefile();
			System.out.println("��� ���ε� �Ϸ�!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void readRecipefile() {

		Connection conn = null;
		Statement stmt = null;
		String query = null;
		Scanner sc = null;
		try {
			sc = new Scanner(new File("C:\\Users\\gkrtn\\eclipse-workspace\\����\\recipe.csv"));
		} catch (FileNotFoundException e1) {
			System.out.println("��� �� ���� ����");
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String[] token = null;
		String tmp = "";
		while (sc.hasNextLine()) {
			tmp = sc.nextLine();
			token = tmp.split("[,]+");

			for (int i = 0; i < token.length; i++) {
				if (token[i] == "")
					return;
				else
					token[i].trim();
			}

			query = "INSERT INTO recipe " + "VALUE ('" + token[0] + "', '" + token[1] + "', " + token[2] + ");";
			/// �����ͺ��̽� ����
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/test?autoReconnect=true&cacheServerConfiguration=true&elideSetAutoCommits=true&useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC",
						"root", "root");
				stmt = conn.createStatement(); // �����ͺ��̽��� query���� �ѱ� Statement�� ������ݴϴ�.
				stmt.executeUpdate(query);

				stmt.close();
				conn.close();
			} catch (ClassNotFoundException e) {
				System.out.println("Class Not Found Exection");
			} catch (SQLException e) {
				System.out.println("SQL Exception : " + e.getMessage());
			}
		}
		sc.close();
	}

	public void scheduleLoadServer(Socket client, InputStream is) { // 1.2
		final int DEFAULT_BUFFER_SIZE = 10000;

		String filename = "schedule.csv"; // ������ ���� �̸�

		try {
			System.out.println("This server is listening... (Port: 8000)");
			InetSocketAddress isaClient = (InetSocketAddress) client.getRemoteSocketAddress();

			System.out.println("A client(" + isaClient.getAddress().getHostAddress() + " is connected. (Port: "
					+ isaClient.getPort() + ")");

			FileOutputStream fos = new FileOutputStream(filename);

			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			int readBytes;
			while ((readBytes = is.read(buffer)) != -1) {
				fos.write(buffer, 0, readBytes);
			}

			System.out.println("���� ���ε� �Ϸ�!");

			is.close();
			fos.close();
			client.close();
			readSchedulefile();
			System.out.println("��� ���ε� �Ϸ�!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void readSchedulefile() throws FileNotFoundException {

		Connection conn = null;
		Statement stmt = null;
		String query = null;
		Scanner sc = new Scanner(new File("C:\\Users\\gkrtn\\eclipse-workspace\\����\\schedule.csv"));
		String[] token = null;
		String tmp = "";
		while (sc.hasNextLine()) {
			tmp = sc.nextLine();
			token = tmp.split("[,]+");

			for (int i = 0; i < token.length; i++) {
				if (token[i] == "")
					return;
				else
					token[i].trim();
			}

			query = "INSERT INTO lectureschedule " + "VALUE ('" + token[0] + "', '" + token[1] + "', '" + token[2]
					+ "', '" + token[3] + "', '" + token[4] + "', '" + token[5] + "');";

			/// �����ͺ��̽� ����
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/test?autoReconnect=true&cacheServerConfiguration=true&elideSetAutoCommits=true&useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC",
						"root", "root");
				stmt = conn.createStatement(); // �����ͺ��̽��� query���� �ѱ� Statement�� ������ݴϴ�.
				stmt.executeUpdate(query);

				stmt.close();
				conn.close();
			} catch (ClassNotFoundException e) {
				System.out.println("Class Not Found Exection");
			} catch (SQLException e) {
				System.out.println("SQL Exception : " + e.getMessage());
			}
		}
		sc.close();
	}

	public void registerMenuByDateServer(Socket client, InputStream is) { // 1.3
		final int DEFAULT_BUFFER_SIZE = 10000;

		String filename = "menuByDate.csv"; // ������ ���� �̸�

		try {
			System.out.println("This server is listening... (Port: 8000)");
			InetSocketAddress isaClient = (InetSocketAddress) client.getRemoteSocketAddress();

			System.out.println("A client(" + isaClient.getAddress().getHostAddress() + " is connected. (Port: "
					+ isaClient.getPort() + ")");

			FileOutputStream fos = new FileOutputStream(filename);

			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			int readBytes;
			while ((readBytes = is.read(buffer)) != -1) {
				fos.write(buffer, 0, readBytes);
			}

			System.out.println("���� ���ε� �Ϸ�!");

			is.close();
			fos.close();
			client.close();
			readMenuByDatefile();
			System.out.println("��� ���ε� �Ϸ�!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void readMenuByDatefile() {

		Connection conn = null;
		Statement stmt = null;
		String query = null;
		Scanner sc = null;
		try {
			sc = new Scanner(new File("C:\\Users\\gkrtn\\eclipse-workspace\\����\\menuByDate.csv"));
		} catch (FileNotFoundException e1) {
			System.out.println("��� �� ���� ����");
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String[] token = null;
		String tmp = "";
		while (sc.hasNextLine()) {
			tmp = sc.nextLine();
			token = tmp.split("[,]+");

			for (int i = 0; i < token.length; i++) {
				if (token[i] == "")
					return;
				else
					token[i].trim();
			}

			query = "INSERT INTO lecturedatemenu " + "VALUE ('" + token[0] + "', '" + token[1] + "', '" + token[2] + "');";
			
			/// �����ͺ��̽� ����
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/test?autoReconnect=true&cacheServerConfiguration=true&elideSetAutoCommits=true&useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC",
						"root", "root");
				stmt = conn.createStatement(); // �����ͺ��̽��� query���� �ѱ� Statement�� ������ݴϴ�.
				stmt.executeUpdate(query);

				stmt.close();
				conn.close();
			} catch (ClassNotFoundException e) {
				System.out.println("Class Not Found Exection");
			} catch (SQLException e) {
				System.out.println("SQL Exception : " + e.getMessage());
			}
		}
		sc.close();
	}

	public void showMenuByDateServer(Socket client, BufferedReader reader, BufferedWriter writer) { // 2.1, 3.1
	      String buf;

	      try {
	         //// ��û ��� ////

	         boolean isReqd = false;
	         String str = "";

	         while ((str = reader.readLine()) != null) {
	            System.out.println("Server: ��û���");
	            if (str.contains("0x09/0x00") || str.contains("0x09/0x01")) {
	               isReqd = true;

	               System.out.println("���ں� �ǽ��޴� ��ȸ ��û����");

	               break;
	            }
	         }

	         //// ��û�� ���� ���� ���� ////

	         int idx1 = str.indexOf('?');

	         String body = str.substring(idx1 + 1);

	         String MenuByDateInfo[] = body.split("[/]+");

	         //// DB insert ////
	         try {
	            // Class.forName("com.mysql.cj.jdbc.Driver");
	            Connection conn = DriverManager.getConnection(
	                  "jdbc:mysql://localhost:3306/test?autoReconnect=true&cacheServerConfiguration=true&elideSetAutoCommits=true&useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC",
	                  "root", "root");

	            String sql1 = "SELECT idLectureDateMenu from lecturedatemenu WHERE placeLecture = ? and dateLecture = ?";
	            PreparedStatement stmt1 = conn.prepareStatement(sql1);
	            stmt1.setString(1, MenuByDateInfo[0]); // �������
	            stmt1.setString(2, MenuByDateInfo[1]); // �����ð�

	            ResultSet rs = stmt1.executeQuery();

	            if (rs.next() == false)
	               isReqd = false;
	            else
	               isReqd = true;

	            if (!isReqd) {
	               str = "0x0A/0x00?";
	               System.out.println("��ȸ ����...");
	               writer.write(str + '\r' + '\n');
	               writer.flush();
	            }

	            
	            
	            else {
	               if (str.contains("0x09/0x01")) {
	                  java.util.Calendar cal = java.util.Calendar.getInstance();
	                  java.text.DateFormat format = new java.text.SimpleDateFormat("yyyyyMMdd");

	                  cal.add(cal.DATE, +5); // 5���� ����
	                  String dateStr = format.format(cal.getTime());

	                  String limitedDate = dateStr.substring(1);

	                  int limit = Integer.parseInt(limitedDate);

	                  String currentTime[] = MenuByDateInfo[1].split("[-]+");
	                  int current = Integer.parseInt(currentTime[0]+currentTime[1]+currentTime[2]);

	                  
	                  if(limit > current) {
	                     str = "0x0A/0x02?";
	                     System.out.println("��û���� �ƴ�");
	                     writer.write(str + '\r' + '\n');
	                     writer.flush();
	                  }
	               }
	               
	               rs.last();
	               int count = rs.getRow();
	               rs.first();

	               String[] menuCost = new String[count];
	               String[] menuName = new String[count];
	               for (int i = 0; i < count; i++) {
	                  String sql2 = "SELECT nameMenu, costMenu from menu WHERE idMenu = " + rs.getString(1);
	                  rs.next();

	                  java.sql.Statement stmt = conn.createStatement();

	                  ResultSet rs2 = stmt.executeQuery(sql2);
	                  rs2.next();
	                  menuName[i] = rs2.getString(1);
	                  menuCost[i] = rs2.getString(2);
	               }

	               conn.close();

	               buf = "";

	               buf = buf + "0x0A/0x01?" + MenuByDateInfo[1];

	               for (int i = 0; i < count; i++) {
	                  buf = buf + '/' + menuName[i] + '/' + menuCost[i];
	               }

	               writer.write(buf + '\r' + '\n');
	               writer.flush();

	               System.out.println("��ȸ ��� ���� �Ϸ�...");

	               str = "0x0A/0x01/0//";
	               System.out.println("��� ����...");
	            }
	         } catch (SQLException e) {
	            e.printStackTrace();
	         }
	         writer.write(str + '\r' + '\n');
	         writer.flush();

	         writer.flush();
	         writer.close();

	         reader.close();

	         client.close();
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	   }

	public void showApplicantListServer(Socket client, BufferedReader reader, BufferedWriter writer) { // 2.2
		try {
			//// ��û ��� ////
			boolean isReqd = false;
			String str = "";

			while ((str = reader.readLine()) != null) {
				System.out.println("Server: ��û���");
				if (str.contains("0x09/0x10")) {
					System.out.println("str: " + str);
					isReqd = true;
					System.out.println("����� ��û�� ��� ��ȸ ��û ����");
					break;
				}
			}

			//// ��û�� ���� ���� ���� ////
			if (!isReqd) {
				str = "0x0A/0x10/0//";
				System.out.println("��û ����...");
			} else {
				String[] result = null;
				result = str.split("/");

				String place = "\"" + result[3] + "\"";
				String date = "\"" + result[4] + "\"";

				String[] applyNum = null;
				String[] applicantName = null;
				String[] dateDeposit = null;

				try {
					// Class.forName("com.mysql.jdbc.Driver");
					Connection conn = DriverManager.getConnection(
							"jdbc:mysql://localhost:3306/test?autoReconnect=true&cacheServerConfiguration=true&elideSetAutoCommits=true&useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC",
							"root", "root");
					Statement st = conn.createStatement();
					ResultSet rs = null;
					int cnt = 0;
					String query = "select applyNum, applicantName, dateDeposit from ingredientapply where placeLecture="
							+ place + "and dateLecture=" + date + "and dateCancel = '0000-00-00' ";
					rs = st.executeQuery(query);
					while (rs.next()) {
						cnt++;
					}
					applyNum = new String[cnt];
					applicantName = new String[cnt];
					dateDeposit = new String[cnt];

					int i = 0;
					st = conn.createStatement();
					rs = st.executeQuery(query);
					while (rs.next()) {
						applyNum[i] = rs.getString("applyNum");
						applicantName[i] = rs.getString("applicantName");
						dateDeposit[i] = rs.getString("dateDeposit");
						i++;
					}

					str = "0x0A/0x11/";
					str = str + Integer.toString(cnt) + "/";

					for (int j = 0; j < cnt; j++) {
						str = str + applyNum[j] + "/" + applicantName[j] + "/" + dateDeposit[j] + "/";
					}
					conn.close();

				} catch (SQLException e) {
					e.printStackTrace();
				}

				System.out.println("��û ����...");
			}

			writer.write(str + '\r' + '\n');
			writer.flush();

			//// ���� ���� ////

			writer.flush();
			writer.close();

			reader.close();

			client.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showRequiredAmountListServer(Socket client, BufferedReader reader, BufferedWriter writer) { // 2.3
		try {
			//// ���� �ޱ� ////
			String str = "";
			String fromDate = "";
			String toDate = "";
			String body = "";
			String[] bodyInfo = null;

			boolean isReqd = false;

			while ((str = reader.readLine()) != null) {
				System.out.println("Server: ���ڹޱ�");
				System.out.println("str: " + str);

				if (str.contains("0x09/0x20")) {
					isReqd = true;

					int idx = str.indexOf("?");

					body = str.substring(idx + 1, str.length());
					bodyInfo = body.split("/");
					
					fromDate = bodyInfo[1] + "-" + bodyInfo[2] + "-" + bodyInfo[3];
					toDate = "-1";
					
					System.out.println("���ں� �ҿ䷮ ��ȸ ��û ����...");
					
					break;
				} else if (str.contains("0x09/0x21")) {
					isReqd = true;

					int idx = str.indexOf("?");

					body = str.substring(idx + 1, str.length());
					bodyInfo = body.split("/");
					
					fromDate = bodyInfo[1] + "-" + bodyInfo[2] + "-" + bodyInfo[3];
					toDate = bodyInfo[4] + "-" + bodyInfo[5] + "-" + bodyInfo[6];
				
					System.out.println("�Ⱓ�� �ҿ䷮ ��ȸ ��û ����...");
					
					break;
				}
			}

			//// �ҿ䷮ ��� ////
			ArrayList<String> menus = new ArrayList<String>();
			ArrayList<String> places = new ArrayList<String>();
			ArrayList<String> ingredientIDs = new ArrayList<String>();
			ArrayList<String> ingredientNames = new ArrayList<String>();
			ArrayList<String> amounts = new ArrayList<String>();
			ArrayList<String> units = new ArrayList<String>();
			ArrayList<String> dates = new ArrayList<String>();
			ArrayList<String> menuIds = new ArrayList<String>();
			int[] totalAmounts = new int[100];
			
			for(int i = 0; i < 100; i++) {
				totalAmounts[i] = 0;
			}
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/test?autoReconnect=true&cacheServerConfiguration=true&elideSetAutoCommits=true&useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC",
					"root", "root");

			Statement stmt = conn.createStatement();
			ResultSet rs = null;

			if(toDate.equals("-1")) { // ���ں�
				rs = stmt.executeQuery("Select dateLecture From lecturedatemenu;");
				
				while(rs.next()) {
					String curDate = rs.getString("dateLecture");
					
					boolean isExist = false;
					
					for(int i = 0; i < dates.size(); i++) {
						if(dates.get(i).equals(curDate)) {
							isExist = true;
							break;
						}
					}
					
					if(!isExist) {
						dates.add(curDate);
					}
				}
				
				for(int i = 0; i < dates.size(); i++) { // �� �غ�
					String curDate = dates.get(i);
					
					String[] dateInfo = curDate.split("-");
					
					String sCurDate = dateInfo[0] + dateInfo[1] + dateInfo[2];
					
					dates.set(i, sCurDate);
					
					System.out.println(sCurDate + " set...");
				}
				
				int lowIndex = 0;
				
				String[] dateInfo = fromDate.split("-");
				
				String sFromDate = dateInfo[0] + dateInfo[1] + dateInfo[2];
				int iFromDate = Integer.parseInt(sFromDate);
				
				dateInfo = toDate.split("-");
				
				//String sToDate = dateInfo[0] + dateInfo[1] + dateInfo[2];
				//int iToDate = Integer.parseInt(sToDate);
				
				dates.sort(null);	
				
				int count = 0;

				for(int j = 0; j < dates.size(); j++) {
					if(iFromDate <= Integer.parseInt(dates.get(j)) && (iFromDate >= Integer.parseInt(dates.get(j)))) {
						if(count == 0)
							lowIndex = j;
						
						count++;
					}
				} // ����, ����
				
				System.out.println("count : " + count);
				
				for(int i = 0; i < dates.size(); i++) {
					String curDate = dates.get(i);
					
					String year = curDate.substring(0, 4);
					String month = curDate.substring(4, 6);
					String day = curDate.substring(6, 8);
					
					curDate = year + "-" + month + "-" + day;
					
					dates.set(i, curDate);
					
					System.out.println(curDate + " ����...");
				} // ���󺹱�
					
				for(int k = lowIndex; k < lowIndex + count; k++) {
					menus = new ArrayList<String>();
					places = new ArrayList<String>();
					
					rs = stmt.executeQuery("Select * From lecturedatemenu Where dateLecture='" + dates.get(k) + "';");

					while (rs.next()) {
						menus.add(rs.getString("idLectureDateMenu"));
						places.add(rs.getString("placeLecture"));
						
						System.out.println("menu : " + rs.getString("idLectureDateMenu"));
					}

					for (int i = 0; i < 3; i++) {
						String curMenu = menus.get(i);
						
						System.out.println("curMenu :" + curMenu);

						// 0�� �� �κ� �� ���
						rs = stmt.executeQuery("SELECT idMenu,amountApply,applyNum from ingredientdetails\r\n" + 
								"WHERE applyNum IN (\r\n" + 
								"	SELECT applyNum\r\n" + 
								"	from ingredientapply\r\n" + 
								"    WHERE dateCancel = '9999-12-31'and dateLecture='" + dates.get(k) +  "'\r\n" + 
								"    )");

						
						
						while(rs.next()) { // �κ� �� ���
							String curID = rs.getString("idMenu"); // 00010

							int index = -1;

							for(int m = 0; m < menus.size(); m++) { // 3��
								if(curID.equals(menus.get(m))) {
									index = m;
									break;
								}
							}

							//�� �κ�
							totalAmounts[index] =  totalAmounts[index] + Integer.parseInt(rs.getString("amountApply"));
							
						}
					
						
						rs = stmt.executeQuery("Select idRecipeForIngredient, totalQuantityIngredient From recipe Where idRecipeForMenu ='" + curMenu + "';");
						
						int aIndex = 0;
						while (rs.next()) { // �޴� �� ����� ��
							String curIngredientID = rs.getString("idRecipeForIngredient");
							String curAmount = rs.getString("totalQuantityIngredient");

							System.out.println("ingredientID: " + curIngredientID + ", amount: " + curAmount);

							boolean isDup = false;
							int index = 0;
							
							if(totalAmounts[aIndex] == 0) {
								aIndex++;
							}
							
							else {
								// �ߺ����� Ȯ��
								for (index = 0; index < ingredientIDs.size(); index++) {
									if (ingredientIDs.get(index).equals(curIngredientID)) {
										isDup = true;
										break;
									}
								}

								double iCurAmount = Double.parseDouble(curAmount) * totalAmounts[i]; // ���� amount
								
								// �ߺ��̸� +
								if (isDup) {
									String sPreAmount = amounts.get(index);
									double iPreAmount = Double.parseDouble(sPreAmount); // ������ amount
									
									amounts.set(index, Double.toString(iPreAmount + iCurAmount)); // ������ ����

									System.out.println(curIngredientID + "�� amount�� " + amounts.get(index) + "����");
								}

								// �ƴϸ� �׳� ���

								else {
									ingredientIDs.add(curIngredientID);
									amounts.add(Double.toString(iCurAmount));
									menuIds.add(curMenu);
								}
								
							
							}
						}

						for (int j = ingredientNames.size(); j < ingredientIDs.size(); j++) { // �̸�, ���� �߰�
							
							rs = stmt.executeQuery("Select nameIngredient, measure From ingredient Where idIngredient='"
									+ ingredientIDs.get(j) + "';");
							
							System.out.println("���� �˻� ���� ����� : " + ingredientIDs.get(j));

							while (rs.next()) {
								String curIngredientName = rs.getString("nameIngredient");
								String curUnit = rs.getString("measure");

								ingredientNames.add(curIngredientName);
								units.add(curUnit);

								System.out.println("ingredientName: " + curIngredientName + ", unit: " + curUnit);
							}
						}
						
						for(int j = 0; j < 100; j++) {
							totalAmounts[j] = 0;
						}
						
						/*for(int a = 0; a < menus.size(); a++) { // amount ���
							System.out.println((a+1) + "��° �޴��� �κ� �� " + totalAmounts[a]);
							
							// a+1��° �޴��� ��ῡ �κа��ϱ�
							for(int b = 0; b < ingredientIDs.size(); b++) {
								if(menuIds.get(b).equals(menus.get(a))) {
									amounts.set(a, Double.toString(Double.parseDouble(amounts.get(a)) * totalAmounts[a]));
								}
							}
						}*/
					}
					
				} // ������ �ҿ䷮ ���
				
				conn.close();
			} else { // �Ⱓ��	
				rs = stmt.executeQuery("Select dateLecture From lecturedatemenu;");
				
				while(rs.next()) {
					String curDate = rs.getString("dateLecture");
					
					boolean isExist = false;
					
					for(int i = 0; i < dates.size(); i++) {
						if(dates.get(i).equals(curDate)) {
							isExist = true;
							break;
						}
					}
					
					if(!isExist) {
						dates.add(curDate);
					}
				}
				
				for(int i = 0; i < dates.size(); i++) { // �� �غ�
					String curDate = dates.get(i);
					
					String[] dateInfo = curDate.split("-");
					
					String sCurDate = dateInfo[0] + dateInfo[1] + dateInfo[2];
					
					dates.set(i, sCurDate);
					
					System.out.println(sCurDate + " set...");
				}
				
				int lowIndex = 0;
				
				String[] dateInfo = fromDate.split("-");
				
				String sFromDate = dateInfo[0] + dateInfo[1] + dateInfo[2];
				int iFromDate = Integer.parseInt(sFromDate);
				
				dateInfo = toDate.split("-");
				
				String sToDate = dateInfo[0] + dateInfo[1] + dateInfo[2];
				int iToDate = Integer.parseInt(sToDate);
				
				dates.sort(null);	
				
				int count = 0;

				for(int j = 0; j < dates.size(); j++) {
					if(iFromDate <= Integer.parseInt(dates.get(j)) && (iToDate >= Integer.parseInt(dates.get(j)))) {
						if(count == 0)
							lowIndex = j;
						
						count++;
					}
				} // ����, ����
				
				System.out.println("count : " + count);
				
				for(int i = 0; i < dates.size(); i++) {
					String curDate = dates.get(i);
					
					String year = curDate.substring(0, 4);
					String month = curDate.substring(4, 6);
					String day = curDate.substring(6, 8);
					
					curDate = year + "-" + month + "-" + day;
					
					dates.set(i, curDate);
					
					System.out.println(curDate + " ����...");
				} // ���󺹱�
					
				for(int k = lowIndex; k < lowIndex + count; k++) {
					menus = new ArrayList<String>();
					places = new ArrayList<String>();
					
					rs = stmt.executeQuery("Select * From lecturedatemenu Where dateLecture='" + dates.get(k) + "';");

					while (rs.next()) {
						menus.add(rs.getString("idLectureDateMenu"));
						places.add(rs.getString("placeLecture"));
						
						System.out.println("menu : " + rs.getString("idLectureDateMenu"));
					}

					for (int i = 0; i < 3; i++) {
						String curMenu = menus.get(i);
						
						System.out.println("curMenu :" + curMenu);

						// 0�� �� �κ� �� ���
						rs = stmt.executeQuery("SELECT idMenu,amountApply,applyNum from ingredientdetails\r\n" + 
								"WHERE applyNum IN (\r\n" + 
								"	SELECT applyNum\r\n" + 
								"	from ingredientapply\r\n" + 
								"    WHERE dateCancel = '9999-12-31'and dateLecture='" + dates.get(k) +  "'\r\n" + 
								"    )");

						
						
						while(rs.next()) { // �κ� �� ���
							String curID = rs.getString("idMenu"); // 00010

							int index = -1;

							for(int m = 0; m < menus.size(); m++) { // 3��
								if(curID.equals(menus.get(m))) {
									index = m;
									break;
								}
							}

							//�� �κ�
							totalAmounts[index] =  totalAmounts[index] + Integer.parseInt(rs.getString("amountApply"));
							
						}
					
						
						rs = stmt.executeQuery("Select idRecipeForIngredient, totalQuantityIngredient From recipe Where idRecipeForMenu ='" + curMenu + "';");
						
						int aIndex = 0;
						while (rs.next()) { // �޴� �� ����� ��
							String curIngredientID = rs.getString("idRecipeForIngredient");
							String curAmount = rs.getString("totalQuantityIngredient");

							System.out.println("ingredientID: " + curIngredientID + ", amount: " + curAmount);

							boolean isDup = false;
							int index = 0;
							
							if(totalAmounts[aIndex] == 0) {
								aIndex++;
							}
							
							else {
								// �ߺ����� Ȯ��
								for (index = 0; index < ingredientIDs.size(); index++) {
									if (ingredientIDs.get(index).equals(curIngredientID)) {
										isDup = true;
										break;
									}
								}

								double iCurAmount = Double.parseDouble(curAmount) * totalAmounts[i]; // ���� amount
								
								// �ߺ��̸� +
								if (isDup) {
									String sPreAmount = amounts.get(index);
									double iPreAmount = Double.parseDouble(sPreAmount); // ������ amount
									
									amounts.set(index, Double.toString(iPreAmount + iCurAmount)); // ������ ����

									System.out.println(curIngredientID + "�� amount�� " + amounts.get(index) + "����");
								}

								// �ƴϸ� �׳� ���

								else {
									ingredientIDs.add(curIngredientID);
									amounts.add(Double.toString(iCurAmount));
									menuIds.add(curMenu);
								}
								
							
							}
						}

						for (int j = ingredientNames.size(); j < ingredientIDs.size(); j++) { // �̸�, ���� �߰�
							
							rs = stmt.executeQuery("Select nameIngredient, measure From ingredient Where idIngredient='"
									+ ingredientIDs.get(j) + "';");
							
							System.out.println("���� �˻� ���� ����� : " + ingredientIDs.get(j));

							while (rs.next()) {
								String curIngredientName = rs.getString("nameIngredient");
								String curUnit = rs.getString("measure");

								ingredientNames.add(curIngredientName);
								units.add(curUnit);

								System.out.println("ingredientName: " + curIngredientName + ", unit: " + curUnit);
							}
						}
						
						for(int j = 0; j < 100; j++) {
							totalAmounts[j] = 0;
						}
						
						/*for(int a = 0; a < menus.size(); a++) { // amount ���
							System.out.println((a+1) + "��° �޴��� �κ� �� " + totalAmounts[a]);
							
							// a+1��° �޴��� ��ῡ �κа��ϱ�
							for(int b = 0; b < ingredientIDs.size(); b++) {
								if(menuIds.get(b).equals(menus.get(a))) {
									amounts.set(a, Double.toString(Double.parseDouble(amounts.get(a)) * totalAmounts[a]));
								}
							}
						}*/
					}
					
				} // ������ �ҿ䷮ ���
				
				conn.close();
			}
			

			//// �ҿ䷮ ���� ////

			String head = "";
			body = "";

			if (!isReqd) {
				head = "0x0A/0x20/0?";
				body = "";
				System.out.println("���ں� �ҿ䷮ ��ȸ ����...");
			} else {
				head = "0x0A/0x21/" + ingredientIDs.size() + "?";
				System.out.println("���ں� �ҿ䷮ ��ȸ ����...");

				for (int i = 0; i < ingredientIDs.size(); i++) {
					body = body + ingredientNames.get(i) + "/" + amounts.get(i) + units.get(i) + "/";
				}
			}

			writer.write(head + body + '\r' + '\n');
			writer.flush();

			System.out.println("body : " + body);

			//// ���� ���� ////

			writer.flush();
			writer.close();

			reader.close();

			client.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void registerTransferListServer(Socket client, BufferedReader reader, BufferedWriter writer) { // 2.4
	      try {
	         //// ��û ��� ////
	         boolean isReqd = false;
	         String str = "";
	         String[] result = new String[6];
	         String applyNumber = null;
	         String depositDate = null;
	         String depositAmount = null;

	         while ((str = reader.readLine()) != null) {
	            System.out.println("Server: ��û���");
	            if (str.contains("0x0B/0x00")) {
	               isReqd = true;
	               System.out.println("�Աݳ������� ��û����");
	               result = str.split("/");
	               applyNumber = "\"" + result[3] + "\"";
	               depositDate = "\"" + result[4] + "\"";
	               depositAmount = result[5];
	               break;
	            }
	         }

	         //// ��û�� ���� ���� ���� ////
	         if (!isReqd) {
	            str = "0x0C/0x00/0//";
	            System.out.println("��û ����...");
	         } 
	         
	         else {
	            try {
	               String sql = "UPDATE ingredientapply SET dateDeposit=" + depositDate + ", accountDeposit="
	                     + depositAmount + " WHERE applyNum=" + applyNumber;
	               System.out.println(sql);
	               System.out.println("��û ����...");

	               Class.forName("com.mysql.cj.jdbc.Driver");
	               Connection conn = DriverManager.getConnection(
	                     "jdbc:mysql://localhost:3306/menupractice?autoReconnect=true&cacheServerConfiguration=true&elideSetAutoCommits=true&useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC",
	                     "root", "root");
	               Statement sm = conn.createStatement();
	               int i = sm.executeUpdate(sql);

	               if(i == 0) {
	                  str = "0x0C/0x00?";
	               }
	               
	               else {
	                  System.out.println("��� ������Ʈ �Ϸ�...");
	                  str = "0x0C/0x01/0//";
	               }
	               conn.close();

	            } catch (SQLException e) {
	               e.printStackTrace();
	            } catch (ClassNotFoundException e) {
	               e.printStackTrace();
	            }
	         }

	         writer.write(str + '\r' + '\n');
	         writer.flush();

	         writer.flush();
	         writer.close();

	         reader.close();

	         client.close();

	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	   }

	public void applyIngredientsServer(Socket client, BufferedReader reader, BufferedWriter writer) { // 3.2
	      try {
	         boolean isReqd;
	         String str = "";
	         String buf = "";
	         String[] info = null;
	         ResultSet rs = null; // �޴� id
	         ResultSet rs2 = null; // �޴� �̸�, �޴� ����
	         String[] menuCost = null;
	         String[] menuName;
	         Class.forName("com.mysql.cj.jdbc.Driver");
	         Connection conn = DriverManager.getConnection(
	               "jdbc:mysql://localhost:3306/test?autoReconnect=true&cacheServerConfiguration=true&elideSetAutoCommits=true&useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC",
	               "root", "root");

	         //// ��û ��� ////
	         str = reader.readLine();
	         
	         isReqd = false;

	         System.out.println("Server: ��û���");

	         // ��û ����
	         if (str.contains("0x0B/0x10")) {
	            isReqd = true;

	            System.out.println("����� ��û ��û����");

	            //// ��û�� ���� ���� ���� ////

	            int idx1 = str.indexOf('?');

	            String body = str.substring(idx1 + 1);
	            
	            System.out.println("body:" + body);

	            info = body.split("[/]+");
	            
	            String sql1 = "SELECT idLectureDateMenu from lecturedatemenu WHERE placeLecture = "
	                  + "'" + info[0] +"'" + " and  dateLecture = '" + info[1] + "-" + info[2] + "-" + info[3] +"'";
	            
	            Statement stmt1 = conn.createStatement();
	            
	            rs = stmt1.executeQuery(sql1); // menu id
	            
	            rs.last();
	            int count = rs.getRow();
	            rs.first();
	            
	            
	            System.out.println("@@@@@" + count);
	            
	            menuCost = new String[count];
	            
	            for(int i = 0; i < count; i++) {
	               String sql2 = "SELECT nameMenu, costMenu from menu WHERE idMenu = " + rs.getString(1);
	               
	               rs.next();
	               
	               java.sql.Statement stmt = conn.createStatement();
	               
	               rs2 = stmt.executeQuery(sql2); // �޴� �̸�, �޴� ����
	               rs2.next();
	               
	               menuCost[i] = rs2.getString("costMenu");
	            }

	            //// DB insert ////
	            try {
	               if (serialNum == 0) {
	                  String sql5 = "SELECT * from ingredientdetails ORDER BY ingredientdetails.applyNum DESC LIMIT 1";

	                  Statement stmt = conn.createStatement();

	                  ResultSet rs5 = stmt.executeQuery(sql5);

	                  rs5.first();

	                  String storedApplyNum = rs5.getString(2);

	                  String storedSN = storedApplyNum.substring(4);

	                  serialNum = Integer.parseInt(storedSN);
	                   
	                  serialNum++;
	               }

	               // ��û��ȣ �߱�
	               String sn = Integer.toString(serialNum);

	               serialNum++;

	               int count1 = 4 - sn.length();
	               for (int i = 0; i < count1; i++) {
	                  sn = "0" + sn;
	               }

	               String lectureYear = info[1].substring(0, 4);
	               String applyNum = lectureYear + sn;

	               String sql3 = "INSERT INTO ingredientapply VALUES" + "(" + applyNum + ",'" + info[0]
	                     + "','" + info[1] + "-" + info[2] + "-" + info[3] + "','" + info[4] + "','" + info[5]
	                     + "', 99991231, 0, 99991231, 99991231, 0)";

	               java.sql.Statement stmt3 = conn.createStatement();

	               stmt3.executeUpdate(sql3);

	               rs.first();
	               int ammountCount = 6;
	               for (int i = 0; i < menuCost.length; i++) {
	                  if(info[i+6].equals("0") == false) {
	                     String sql4 = "INSERT INTO ingredientdetails (idMenu, applyNum, "
	                           + "amountApply, costIngredient) VALUES" + "('" + rs.getString(1) + "'," + applyNum
	                           + ",'" + info[ammountCount] + "','" + menuCost[i] + "')";
	   
	                     rs.next();
	   
	                     java.sql.Statement stmt4 = conn.createStatement();
	   
	                     stmt4.executeUpdate(sql4);
	   
	                     ammountCount++;
	                  }
	               }

	               if (rs.first() == false)
	                  isReqd = false;
	               else
	                  isReqd = true;

	               if (!isReqd) {
	                  str = "0x0C/0x10/0//";
	                  System.out.println("���� ����...");

	                  writer.write(str + '\r' + '\n');
	                  writer.flush();
	               }

	               else {
	                  buf = "";

	                  // �� �Ա��ؾߵ� �ݾ� ���
	                  int result = 0;
	                  for (int i = 0; i < menuCost.length; i++) {
	                     result = result + Integer.parseInt(menuCost[i]) * Integer.parseInt(info[i + 6]);
	                     System.out.println("cost: " + menuCost[i] + " ���� : " + info[i + 6]);
	                  }

	                  buf = buf + "0x0C/0x11?" + applyNum + "/" + result;

	                  writer.write(buf + '\r' + '\n');
	                  writer.flush();

	                  System.out.println("���� ��� ���� �Ϸ�...");
	               }
	               // ���� ��
	            } catch (SQLException e) {
	               e.printStackTrace();
	            }
	         } // else if�� ��

	      } catch (Exception e) {
	         e.printStackTrace();
	      } finally {
	      }
	   }

	   public void cancleIngredientsServer(Socket client, BufferedReader reader, BufferedWriter writer) { // 3.3
	      String buf;

	      try {
	         //// ��û ��� ////

	         boolean isReqd = false;
	         String str = "";

	         while ((str = reader.readLine()) != null) {
	            System.out.println("Server: ��û���");
	            if (str.contains("0x0B/0x20")) {

	               isReqd = true;

	               System.out.println("����� ��� ��û����");

	               break;
	            }
	         }

	         //// ��û�� ���� ���� ���� ////

	         int idx1 = str.indexOf('?');

	         String body = str.substring(idx1 + 1);

	         String applicantInfo[] = body.split("[/]+");

	         //// DB insert ////
	         SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	         Date currentTime = new Date();

	         String formattedTime = format.format(currentTime);

	         try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            Connection conn = DriverManager.getConnection(
	                  "jdbc:mysql://localhost:3306/test?autoReconnect=true&cacheServerConfiguration=true&elideSetAutoCommits=true&useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC",
	                  "root", "root");

	            String sql1 = "SELECT applyNum, dateLecture from ingredientapply WHERE applyNum = ? and applicantName = ? and applicantPhoneNum = ?";
	            PreparedStatement stmt1 = conn.prepareStatement(sql1);
	            stmt1.setString(1, applicantInfo[0]); // ��û��ȣ
	            stmt1.setString(2, applicantInfo[1]); // ��û���̸�
	            stmt1.setString(3, applicantInfo[2]); // ��û����ȭ��ȣ

	            ResultSet rs = stmt1.executeQuery();

	            if (rs.next() == false)
	               isReqd = false;
	            else
	               isReqd = true;

	            if (!isReqd) {
	               str = "0x0C/0x20?";
	               System.out.println("��� ����...");
	               writer.write(str + '\r' + '\n');
	               writer.flush();
	            }

	            else {
	               String lectureDate = rs.getString(2);   
	               
	               java.util.Calendar cal = java.util.Calendar.getInstance();
	               //java.text.DateFormat format = new java.text.SimpleDateFormat("yyyyMMdd");

	               cal.add(cal.DATE, +2); // ���� ��¥�� 2���� ���Ѵ�
	               String currentTime2 = format.format(cal.getTime());
	               
	               String cTime[] = currentTime2.split("[-]+");
	               int current = Integer.parseInt(cTime[0]+cTime[1]+cTime[2]);
	               
	               String lTime[] = lectureDate.split("[-]+");
	               int lecture = Integer.parseInt(lTime[0]+lTime[1]+lTime[2]);
	               
	               System.out.println(current);
	               System.out.println(lecture);
	               
	               // ��� �Ұ�
	               if(current > lecture) {
	                  System.out.println("��� �Ұ�");
	                  str = "0x0C/0x22?";
	                  writer.write(str + '\r' + '\n');
	                  writer.flush();
	               }
	               
	               else {
	                  String sql2 = "UPDATE ingredientapply SET dateCancel = ? WHERE applyNum = ?";
	                  PreparedStatement stmt2 = conn.prepareStatement(sql2);
	                  stmt2.setString(1, formattedTime);
	                  stmt2.setString(2, applicantInfo[0]);

	                  stmt2.executeUpdate();

	                  System.out.println("��� ������Ʈ �Ϸ�...");

	                  conn.close();

	                  str = "0x0C/0x21?";
	                  System.out.println("��� ����...");
	               }
	            }
	         } catch (SQLException e) {
	            e.printStackTrace();
	         } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	         }
	         writer.write(str + '\r' + '\n');
	         writer.flush();

	         writer.flush();
	         writer.close();

	         reader.close();

	         client.close();
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	   }
	}
