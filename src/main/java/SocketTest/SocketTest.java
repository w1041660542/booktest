package SocketTest;

import java.io.InputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class SocketTest {

	public static void main(String[] args) throws Exception{
		
		
		
		try(Socket socket = new Socket("time-A.timefreq.bldrdoc.gov", 13)){
	
			InputStream inStream = socket.getInputStream();
			Scanner scanner = new Scanner(inStream);
			while(scanner.hasNext()) {
				String line = scanner.nextLine();
				System.out.println(line);
				
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
