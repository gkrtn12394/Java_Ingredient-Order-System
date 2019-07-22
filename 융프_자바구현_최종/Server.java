package temp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	public static void main(String[] args) {
		ServerController sc = new ServerController();
		ServerSocket sock = null;
		Socket client = new Socket();
		
		try {
			sock = new ServerSocket(8000); // PORT 8000 서버를 개통.
			System.out.println("Server Start");
			System.out.println("클라이언트 접속 대기 ");
			
			while(true) { // 기능 구현	
				client = sock.accept();
				
				InputStreamReader in = new InputStreamReader(client.getInputStream());
				OutputStreamWriter out = new OutputStreamWriter(client.getOutputStream());
				
				BufferedReader reader = new BufferedReader(in);
				BufferedWriter writer = new BufferedWriter(out);
				
				// 요청 받기
				String rcvMsg;
				rcvMsg = reader.readLine();

				System.out.println("msg : " + rcvMsg);
				
				// 요청에 대한 응답
				String msg = "OK";
				
				writer.write(msg + '\r' + '\n');
				writer.flush();
				
				// (헤더 보고) 기능 수행
				if(rcvMsg.contains("LOGIN")) {
					sc.loginServer(sock, client, reader, writer);
				} else if(rcvMsg.contains("RECIPELOAD")) {
					sc.recipeLoadServer(client, client.getInputStream());
				} else if(rcvMsg.contains("SCHEDULELOAD")) {
					sc.scheduleLoadServer(client, client.getInputStream());
				} else if(rcvMsg.contains("REGISTERMENUBYDATE")) {
					sc.registerMenuByDateServer(client, client.getInputStream());
				} else if(rcvMsg.contains("SHOWMENUBYDATE")) {
					sc.showMenuByDateServer(client, reader, writer);
				} else if(rcvMsg.contains("SHOWAPPLICANTLIST")) {
					sc.showApplicantListServer(client, reader, writer);
				} else if(rcvMsg.contains("SHOWREQUIREDAMOUNTLIST")) {
					sc.showRequiredAmountListServer(client, reader, writer);
				} else if(rcvMsg.contains("REGISTERTRANSFERLIST")) {
					sc.registerTransferListServer(client, reader, writer);
				} else if(rcvMsg.contains("APPLYINGREDIENTS")) {
					sc.applyIngredientsServer(client, reader, writer);
				} else if(rcvMsg.contains("CANCLEINGREDIENTS")) {
					sc.cancleIngredientsServer(client, reader, writer);
				} 
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

