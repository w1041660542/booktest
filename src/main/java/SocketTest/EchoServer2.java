package SocketTest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class EchoServer2 {

	public static void main(String[] args) throws IOException {
		int i = 1;
		ServerSocket s = new ServerSocket(8189);
		
		try {
		
			
			while(true) {
				
				Socket incming = s.accept();
				
				System.out.println("Spamning" + i);
				
				Runnable r = new TheadEchoHander(incming);
				
				Thread t = new Thread(r);
				
				t.start();
				i++;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		  
		
		

	}

}
class TheadEchoHander implements Runnable{
	
	private Socket incoming;
	
	public TheadEchoHander(Socket incoming) {
		this.incoming = incoming;
	}

	@Override
	public void run() {
		
		 
		try {
			try {
				
				  InputStream inStream = incoming.getInputStream();
				  OutputStream outStream = incoming.getOutputStream();
				   try(Scanner in = new Scanner(inStream)){
					    PrintWriter out = new PrintWriter(outStream, true);
					    out.println("Hello! send BYE to exit!");
					    
					    boolean done = false;
					    while (!done && in.hasNextLine()) {
							String line = in.nextLine();
							out.println("Echo: " + line);
							if (line.trim().equals("BYE")) {
								done = true;
							}
						}
				   }
				
				
				
			} finally {
				incoming.close();
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}		 
		
		
		
		
		
	}
	
}

