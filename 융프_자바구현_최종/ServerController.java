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
			System.out.println("클라이언트 접속 IP[" + sock.getInetAddress() + "]");
			System.out.println("클라이언트 접속 PORT[" + sock.getLocalPort() + "]");
			System.out.println("IP[" + sock.getInetAddress() + "]");

			//// 요청 듣기 ////
			boolean isReqd = false;
			String str = "";

			str = reader.readLine();

			System.out.println("Server: 요청듣기");

			if (str.contains("LOGIN")) {
				System.out.println("str: " + str);
				isReqd = true;
				System.out.println("로그인 요청 받음");
			}

			//// 요청에 대한 응답 전송 ////
			if (!isReqd) {
				str = "0x0A/0x10/-1//";
				System.out.println("요청 거절...");
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
					System.out.println("아이디 or 비밀번호가 틀립니다.");
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

		String filename = "recipe.csv"; // 저장할 파일 이름

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

			System.out.println("파일 업로드 완료!");

			is.close();
			fos.close();
			client.close();
			readRecipefile();
			System.out.println("디비 업로드 완료!");
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
			sc = new Scanner(new File("C:\\Users\\gkrtn\\eclipse-workspace\\융프\\recipe.csv"));
		} catch (FileNotFoundException e1) {
			System.out.println("디비에 들어갈 파일 없음");
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
			/// 데이터베이스 접속
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/test?autoReconnect=true&cacheServerConfiguration=true&elideSetAutoCommits=true&useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC",
						"root", "root");
				stmt = conn.createStatement(); // 데이터베이스에 query문을 넘길 Statement를 만들어줍니다.
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

		String filename = "schedule.csv"; // 저장할 파일 이름

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

			System.out.println("파일 업로드 완료!");

			is.close();
			fos.close();
			client.close();
			readSchedulefile();
			System.out.println("디비 업로드 완료!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void readSchedulefile() throws FileNotFoundException {

		Connection conn = null;
		Statement stmt = null;
		String query = null;
		Scanner sc = new Scanner(new File("C:\\Users\\gkrtn\\eclipse-workspace\\융프\\schedule.csv"));
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

			/// 데이터베이스 접속
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/test?autoReconnect=true&cacheServerConfiguration=true&elideSetAutoCommits=true&useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC",
						"root", "root");
				stmt = conn.createStatement(); // 데이터베이스에 query문을 넘길 Statement를 만들어줍니다.
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

		String filename = "menuByDate.csv"; // 저장할 파일 이름

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

			System.out.println("파일 업로드 완료!");

			is.close();
			fos.close();
			client.close();
			readMenuByDatefile();
			System.out.println("디비 업로드 완료!");
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
			sc = new Scanner(new File("C:\\Users\\gkrtn\\eclipse-workspace\\융프\\menuByDate.csv"));
		} catch (FileNotFoundException e1) {
			System.out.println("디비에 들어갈 파일 없음");
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
			
			/// 데이터베이스 접속
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/test?autoReconnect=true&cacheServerConfiguration=true&elideSetAutoCommits=true&useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC",
						"root", "root");
				stmt = conn.createStatement(); // 데이터베이스에 query문을 넘길 Statement를 만들어줍니다.
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
	         //// 요청 듣기 ////

	         boolean isReqd = false;
	         String str = "";

	         while ((str = reader.readLine()) != null) {
	            System.out.println("Server: 요청듣기");
	            if (str.contains("0x09/0x00") || str.contains("0x09/0x01")) {
	               isReqd = true;

	               System.out.println("일자별 실습메뉴 조회 요청받음");

	               break;
	            }
	         }

	         //// 요청에 대한 응답 전송 ////

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
	            stmt1.setString(1, MenuByDateInfo[0]); // 수강장소
	            stmt1.setString(2, MenuByDateInfo[1]); // 수강시간

	            ResultSet rs = stmt1.executeQuery();

	            if (rs.next() == false)
	               isReqd = false;
	            else
	               isReqd = true;

	            if (!isReqd) {
	               str = "0x0A/0x00?";
	               System.out.println("조회 거절...");
	               writer.write(str + '\r' + '\n');
	               writer.flush();
	            }

	            
	            
	            else {
	               if (str.contains("0x09/0x01")) {
	                  java.util.Calendar cal = java.util.Calendar.getInstance();
	                  java.text.DateFormat format = new java.text.SimpleDateFormat("yyyyyMMdd");

	                  cal.add(cal.DATE, +5); // 5일을 뺀다
	                  String dateStr = format.format(cal.getTime());

	                  String limitedDate = dateStr.substring(1);

	                  int limit = Integer.parseInt(limitedDate);

	                  String currentTime[] = MenuByDateInfo[1].split("[-]+");
	                  int current = Integer.parseInt(currentTime[0]+currentTime[1]+currentTime[2]);

	                  
	                  if(limit > current) {
	                     str = "0x0A/0x02?";
	                     System.out.println("신청기한 아님");
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

	               System.out.println("조회 결과 전송 완료...");

	               str = "0x0A/0x01/0//";
	               System.out.println("취소 승인...");
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
			//// 요청 듣기 ////
			boolean isReqd = false;
			String str = "";

			while ((str = reader.readLine()) != null) {
				System.out.println("Server: 요청듣기");
				if (str.contains("0x09/0x10")) {
					System.out.println("str: " + str);
					isReqd = true;
					System.out.println("식재료 신청자 명단 조회 요청 받음");
					break;
				}
			}

			//// 요청에 대한 응답 전송 ////
			if (!isReqd) {
				str = "0x0A/0x10/0//";
				System.out.println("요청 거절...");
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

				System.out.println("요청 승인...");
			}

			writer.write(str + '\r' + '\n');
			writer.flush();

			//// 연결 종료 ////

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
			//// 일자 받기 ////
			String str = "";
			String fromDate = "";
			String toDate = "";
			String body = "";
			String[] bodyInfo = null;

			boolean isReqd = false;

			while ((str = reader.readLine()) != null) {
				System.out.println("Server: 일자받기");
				System.out.println("str: " + str);

				if (str.contains("0x09/0x20")) {
					isReqd = true;

					int idx = str.indexOf("?");

					body = str.substring(idx + 1, str.length());
					bodyInfo = body.split("/");
					
					fromDate = bodyInfo[1] + "-" + bodyInfo[2] + "-" + bodyInfo[3];
					toDate = "-1";
					
					System.out.println("일자별 소요량 조회 요청 받음...");
					
					break;
				} else if (str.contains("0x09/0x21")) {
					isReqd = true;

					int idx = str.indexOf("?");

					body = str.substring(idx + 1, str.length());
					bodyInfo = body.split("/");
					
					fromDate = bodyInfo[1] + "-" + bodyInfo[2] + "-" + bodyInfo[3];
					toDate = bodyInfo[4] + "-" + bodyInfo[5] + "-" + bodyInfo[6];
				
					System.out.println("기간별 소요량 조회 요청 받음...");
					
					break;
				}
			}

			//// 소요량 계산 ////
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

			if(toDate.equals("-1")) { // 일자별
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
				
				for(int i = 0; i < dates.size(); i++) { // 비교 준비
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
				} // 하한, 상한
				
				System.out.println("count : " + count);
				
				for(int i = 0; i < dates.size(); i++) {
					String curDate = dates.get(i);
					
					String year = curDate.substring(0, 4);
					String month = curDate.substring(4, 6);
					String day = curDate.substring(6, 8);
					
					curDate = year + "-" + month + "-" + day;
					
					dates.set(i, curDate);
					
					System.out.println(curDate + " 복구...");
				} // 원상복구
					
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

						// 0일 때 인분 수 계산
						rs = stmt.executeQuery("SELECT idMenu,amountApply,applyNum from ingredientdetails\r\n" + 
								"WHERE applyNum IN (\r\n" + 
								"	SELECT applyNum\r\n" + 
								"	from ingredientapply\r\n" + 
								"    WHERE dateCancel = '9999-12-31'and dateLecture='" + dates.get(k) +  "'\r\n" + 
								"    )");

						
						
						while(rs.next()) { // 인분 수 계산
							String curID = rs.getString("idMenu"); // 00010

							int index = -1;

							for(int m = 0; m < menus.size(); m++) { // 3개
								if(curID.equals(menus.get(m))) {
									index = m;
									break;
								}
							}

							//총 인분
							totalAmounts[index] =  totalAmounts[index] + Integer.parseInt(rs.getString("amountApply"));
							
						}
					
						
						rs = stmt.executeQuery("Select idRecipeForIngredient, totalQuantityIngredient From recipe Where idRecipeForMenu ='" + curMenu + "';");
						
						int aIndex = 0;
						while (rs.next()) { // 메뉴 당 식재료 양
							String curIngredientID = rs.getString("idRecipeForIngredient");
							String curAmount = rs.getString("totalQuantityIngredient");

							System.out.println("ingredientID: " + curIngredientID + ", amount: " + curAmount);

							boolean isDup = false;
							int index = 0;
							
							if(totalAmounts[aIndex] == 0) {
								aIndex++;
							}
							
							else {
								// 중복인지 확인
								for (index = 0; index < ingredientIDs.size(); index++) {
									if (ingredientIDs.get(index).equals(curIngredientID)) {
										isDup = true;
										break;
									}
								}

								double iCurAmount = Double.parseDouble(curAmount) * totalAmounts[i]; // 현재 amount
								
								// 중복이면 +
								if (isDup) {
									String sPreAmount = amounts.get(index);
									double iPreAmount = Double.parseDouble(sPreAmount); // 기존의 amount
									
									amounts.set(index, Double.toString(iPreAmount + iCurAmount)); // 합으로 저장

									System.out.println(curIngredientID + "의 amount가 " + amounts.get(index) + "변경");
								}

								// 아니면 그냥 등록

								else {
									ingredientIDs.add(curIngredientID);
									amounts.add(Double.toString(iCurAmount));
									menuIds.add(curMenu);
								}
								
							
							}
						}

						for (int j = ingredientNames.size(); j < ingredientIDs.size(); j++) { // 이름, 단위 추가
							
							rs = stmt.executeQuery("Select nameIngredient, measure From ingredient Where idIngredient='"
									+ ingredientIDs.get(j) + "';");
							
							System.out.println("현재 검색 중인 식재료 : " + ingredientIDs.get(j));

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
						
						/*for(int a = 0; a < menus.size(); a++) { // amount 계산
							System.out.println((a+1) + "번째 메뉴의 인분 수 " + totalAmounts[a]);
							
							// a+1번째 메뉴의 재료에 인분곱하기
							for(int b = 0; b < ingredientIDs.size(); b++) {
								if(menuIds.get(b).equals(menus.get(a))) {
									amounts.set(a, Double.toString(Double.parseDouble(amounts.get(a)) * totalAmounts[a]));
								}
							}
						}*/
					}
					
				} // 각자의 소요량 계산
				
				conn.close();
			} else { // 기간별	
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
				
				for(int i = 0; i < dates.size(); i++) { // 비교 준비
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
				} // 하한, 상한
				
				System.out.println("count : " + count);
				
				for(int i = 0; i < dates.size(); i++) {
					String curDate = dates.get(i);
					
					String year = curDate.substring(0, 4);
					String month = curDate.substring(4, 6);
					String day = curDate.substring(6, 8);
					
					curDate = year + "-" + month + "-" + day;
					
					dates.set(i, curDate);
					
					System.out.println(curDate + " 복구...");
				} // 원상복구
					
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

						// 0일 때 인분 수 계산
						rs = stmt.executeQuery("SELECT idMenu,amountApply,applyNum from ingredientdetails\r\n" + 
								"WHERE applyNum IN (\r\n" + 
								"	SELECT applyNum\r\n" + 
								"	from ingredientapply\r\n" + 
								"    WHERE dateCancel = '9999-12-31'and dateLecture='" + dates.get(k) +  "'\r\n" + 
								"    )");

						
						
						while(rs.next()) { // 인분 수 계산
							String curID = rs.getString("idMenu"); // 00010

							int index = -1;

							for(int m = 0; m < menus.size(); m++) { // 3개
								if(curID.equals(menus.get(m))) {
									index = m;
									break;
								}
							}

							//총 인분
							totalAmounts[index] =  totalAmounts[index] + Integer.parseInt(rs.getString("amountApply"));
							
						}
					
						
						rs = stmt.executeQuery("Select idRecipeForIngredient, totalQuantityIngredient From recipe Where idRecipeForMenu ='" + curMenu + "';");
						
						int aIndex = 0;
						while (rs.next()) { // 메뉴 당 식재료 양
							String curIngredientID = rs.getString("idRecipeForIngredient");
							String curAmount = rs.getString("totalQuantityIngredient");

							System.out.println("ingredientID: " + curIngredientID + ", amount: " + curAmount);

							boolean isDup = false;
							int index = 0;
							
							if(totalAmounts[aIndex] == 0) {
								aIndex++;
							}
							
							else {
								// 중복인지 확인
								for (index = 0; index < ingredientIDs.size(); index++) {
									if (ingredientIDs.get(index).equals(curIngredientID)) {
										isDup = true;
										break;
									}
								}

								double iCurAmount = Double.parseDouble(curAmount) * totalAmounts[i]; // 현재 amount
								
								// 중복이면 +
								if (isDup) {
									String sPreAmount = amounts.get(index);
									double iPreAmount = Double.parseDouble(sPreAmount); // 기존의 amount
									
									amounts.set(index, Double.toString(iPreAmount + iCurAmount)); // 합으로 저장

									System.out.println(curIngredientID + "의 amount가 " + amounts.get(index) + "변경");
								}

								// 아니면 그냥 등록

								else {
									ingredientIDs.add(curIngredientID);
									amounts.add(Double.toString(iCurAmount));
									menuIds.add(curMenu);
								}
								
							
							}
						}

						for (int j = ingredientNames.size(); j < ingredientIDs.size(); j++) { // 이름, 단위 추가
							
							rs = stmt.executeQuery("Select nameIngredient, measure From ingredient Where idIngredient='"
									+ ingredientIDs.get(j) + "';");
							
							System.out.println("현재 검색 중인 식재료 : " + ingredientIDs.get(j));

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
						
						/*for(int a = 0; a < menus.size(); a++) { // amount 계산
							System.out.println((a+1) + "번째 메뉴의 인분 수 " + totalAmounts[a]);
							
							// a+1번째 메뉴의 재료에 인분곱하기
							for(int b = 0; b < ingredientIDs.size(); b++) {
								if(menuIds.get(b).equals(menus.get(a))) {
									amounts.set(a, Double.toString(Double.parseDouble(amounts.get(a)) * totalAmounts[a]));
								}
							}
						}*/
					}
					
				} // 각자의 소요량 계산
				
				conn.close();
			}
			

			//// 소요량 전송 ////

			String head = "";
			body = "";

			if (!isReqd) {
				head = "0x0A/0x20/0?";
				body = "";
				System.out.println("일자별 소요량 조회 거절...");
			} else {
				head = "0x0A/0x21/" + ingredientIDs.size() + "?";
				System.out.println("일자별 소요량 조회 승인...");

				for (int i = 0; i < ingredientIDs.size(); i++) {
					body = body + ingredientNames.get(i) + "/" + amounts.get(i) + units.get(i) + "/";
				}
			}

			writer.write(head + body + '\r' + '\n');
			writer.flush();

			System.out.println("body : " + body);

			//// 연결 종료 ////

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
	         //// 요청 듣기 ////
	         boolean isReqd = false;
	         String str = "";
	         String[] result = new String[6];
	         String applyNumber = null;
	         String depositDate = null;
	         String depositAmount = null;

	         while ((str = reader.readLine()) != null) {
	            System.out.println("Server: 요청듣기");
	            if (str.contains("0x0B/0x00")) {
	               isReqd = true;
	               System.out.println("입금내역갱신 요청받음");
	               result = str.split("/");
	               applyNumber = "\"" + result[3] + "\"";
	               depositDate = "\"" + result[4] + "\"";
	               depositAmount = result[5];
	               break;
	            }
	         }

	         //// 요청에 대한 응답 전송 ////
	         if (!isReqd) {
	            str = "0x0C/0x00/0//";
	            System.out.println("요청 거절...");
	         } 
	         
	         else {
	            try {
	               String sql = "UPDATE ingredientapply SET dateDeposit=" + depositDate + ", accountDeposit="
	                     + depositAmount + " WHERE applyNum=" + applyNumber;
	               System.out.println(sql);
	               System.out.println("요청 승인...");

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
	                  System.out.println("디비 업데이트 완료...");
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
	         ResultSet rs = null; // 메뉴 id
	         ResultSet rs2 = null; // 메뉴 이름, 메뉴 가격
	         String[] menuCost = null;
	         String[] menuName;
	         Class.forName("com.mysql.cj.jdbc.Driver");
	         Connection conn = DriverManager.getConnection(
	               "jdbc:mysql://localhost:3306/test?autoReconnect=true&cacheServerConfiguration=true&elideSetAutoCommits=true&useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC",
	               "root", "root");

	         //// 요청 듣기 ////
	         str = reader.readLine();
	         
	         isReqd = false;

	         System.out.println("Server: 요청듣기");

	         // 신청 시작
	         if (str.contains("0x0B/0x10")) {
	            isReqd = true;

	            System.out.println("식재료 신청 요청받음");

	            //// 요청에 대한 응답 전송 ////

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
	               
	               rs2 = stmt.executeQuery(sql2); // 메뉴 이름, 메뉴 가격
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

	               // 신청번호 발급
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
	                  System.out.println("갱신 거절...");

	                  writer.write(str + '\r' + '\n');
	                  writer.flush();
	               }

	               else {
	                  buf = "";

	                  // 총 입급해야될 금액 계산
	                  int result = 0;
	                  for (int i = 0; i < menuCost.length; i++) {
	                     result = result + Integer.parseInt(menuCost[i]) * Integer.parseInt(info[i + 6]);
	                     System.out.println("cost: " + menuCost[i] + " 갯수 : " + info[i + 6]);
	                  }

	                  buf = buf + "0x0C/0x11?" + applyNum + "/" + result;

	                  writer.write(buf + '\r' + '\n');
	                  writer.flush();

	                  System.out.println("갱신 결과 전송 완료...");
	               }
	               // 갱신 끝
	            } catch (SQLException e) {
	               e.printStackTrace();
	            }
	         } // else if문 끝

	      } catch (Exception e) {
	         e.printStackTrace();
	      } finally {
	      }
	   }

	   public void cancleIngredientsServer(Socket client, BufferedReader reader, BufferedWriter writer) { // 3.3
	      String buf;

	      try {
	         //// 요청 듣기 ////

	         boolean isReqd = false;
	         String str = "";

	         while ((str = reader.readLine()) != null) {
	            System.out.println("Server: 요청듣기");
	            if (str.contains("0x0B/0x20")) {

	               isReqd = true;

	               System.out.println("식재료 취소 요청받음");

	               break;
	            }
	         }

	         //// 요청에 대한 응답 전송 ////

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
	            stmt1.setString(1, applicantInfo[0]); // 신청번호
	            stmt1.setString(2, applicantInfo[1]); // 신청자이름
	            stmt1.setString(3, applicantInfo[2]); // 신청자전화번호

	            ResultSet rs = stmt1.executeQuery();

	            if (rs.next() == false)
	               isReqd = false;
	            else
	               isReqd = true;

	            if (!isReqd) {
	               str = "0x0C/0x20?";
	               System.out.println("취소 거절...");
	               writer.write(str + '\r' + '\n');
	               writer.flush();
	            }

	            else {
	               String lectureDate = rs.getString(2);   
	               
	               java.util.Calendar cal = java.util.Calendar.getInstance();
	               //java.text.DateFormat format = new java.text.SimpleDateFormat("yyyyMMdd");

	               cal.add(cal.DATE, +2); // 현재 날짜에 2일을 더한다
	               String currentTime2 = format.format(cal.getTime());
	               
	               String cTime[] = currentTime2.split("[-]+");
	               int current = Integer.parseInt(cTime[0]+cTime[1]+cTime[2]);
	               
	               String lTime[] = lectureDate.split("[-]+");
	               int lecture = Integer.parseInt(lTime[0]+lTime[1]+lTime[2]);
	               
	               System.out.println(current);
	               System.out.println(lecture);
	               
	               // 취소 불가
	               if(current > lecture) {
	                  System.out.println("취소 불가");
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

	                  System.out.println("디비 업데이트 완료...");

	                  conn.close();

	                  str = "0x0C/0x21?";
	                  System.out.println("취소 승인...");
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
