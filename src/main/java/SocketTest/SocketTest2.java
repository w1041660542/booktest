package SocketTest;

import java.io.InputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Iterator;
import java.util.Scanner;

public class SocketTest2 {

	public static void main(String[] args) throws Exception{
		
		if (args.length > 0) {
			
			String host = args[0];
			InetAddress[] allByName = Inet4Address.getAllByName(host);
			for (InetAddress inetAddress : allByName) {
				System.out.println(inetAddress);
			}
			
		}else {
			
			InetAddress localHost = Inet4Address.getLocalHost();
			System.out.println(localHost);
		}
		
		
		
	}

}
