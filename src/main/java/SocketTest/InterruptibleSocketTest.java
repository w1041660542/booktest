package SocketTest;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class InterruptibleSocketTest {

	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				JFrame frame  = new InterruptibleSocketFrame();
				frame.setTitle("InterruptibleSocketTest");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				
			}
		});

	}

}
class InterruptibleSocketFrame extends JFrame{
	
	public static final int TEXT_ROWS = 20;
	public static final int TEXT_COLUMNS = 60;
	
	private Scanner in ;
	private JButton interruptibleButton;
	private JButton blockingButton;
	private JButton cancelButton;
	private JTextArea messages;
	private TestServer server;
	private Thread connectThread;
	
	
	public InterruptibleSocketFrame() {
		JPanel northJPanel = new JPanel();
		add(northJPanel,BorderLayout.NORTH);
		
		messages = new JTextArea(TEXT_ROWS,TEXT_COLUMNS);
		add(new JScrollPane(messages));
		
		interruptibleButton = new JButton("Interruptible");
		blockingButton = new JButton("Blocking");
		northJPanel.add(interruptibleButton);
		northJPanel.add(blockingButton);
		
		interruptibleButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				interruptibleButton.setEnabled(false);
				blockingButton.setEnabled(false);
				cancelButton.setEnabled(true);
				connectThread = new Thread(new Runnable() {
					
					@Override
					public void run() {
						
						try {
							connectInterruptibly();
							
						}catch (Exception e) {
							messages.append("\nInterruptibleSocketTest.connectInterruptibly:" + e);
						}
						
					}

					
				});
				connectThread.start();
				
			}
		});
		
		blockingButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				interruptibleButton.setEnabled(false);
				blockingButton.setEnabled(false);
				cancelButton.setEnabled(true);
				connectThread = new Thread(new Runnable() {
					
					@Override
					public void run() {
						
						try {
							connectBlocking();
							
						} catch (Exception e) {
							messages.append("\n\\nInterruptibleSocketTest.connectBlocking"+ e);
						}
					}

					
				});
				connectThread.start();
				
			}
		});
		cancelButton = new JButton("Cannel");
		cancelButton.setEnabled(false);
		northJPanel.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				connectThread.interrupt();
				cancelButton.setEnabled(false);
				
			}
		});
		server = new TestServer();
		new Thread(server).start();
		pack();
	}
	private void connectInterruptibly() throws IOException{
		messages.append("Interruptible:\n");
		try (SocketChannel channel = SocketChannel.open(new InetSocketAddress("localhost",8189))){
			in = new Scanner(channel);
			while(!Thread.currentThread().isInterrupted()) {
				messages.append("Reading ");
				if (in.hasNextLine()) {
					String line = in.nextLine();
					messages.append(line);
					messages.append("\n");
				}
			}
		} finally {
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					messages.append("Channel closed\n");
					interruptibleButton.setEnabled(true);
					blockingButton.setEnabled(true);
				}
			});
		}
		
	}
	
	
	private void connectBlocking() throws IOException{
		
		messages.append("Blocking:\n");
		try (Socket socket = new Socket("localhost",8189)){
			
			in = new Scanner(socket.getInputStream());
			while(!Thread.currentThread().isInterrupted()) {
				messages.append("Reading ");
				if (in.hasNextLine()) {
					String line = in.nextLine();
					messages.append(line);
					messages.append("\n");
				}
			}
		} finally {
			
			EventQueue.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					messages.append("Socket closed\n");
					interruptibleButton.setEnabled(true);
					blockingButton.setEnabled(true);
				}
			});
		}
		
		
	}
	
	
	
	class TestServer implements Runnable{

		@Override
		public void run() {
			try {
				ServerSocket  s = new ServerSocket(8189);
				
				while(true) {
					Socket incoming = s.accept();
					Runnable r = new TestServerHander(incoming);
					Thread t = new Thread(r);
					t.start();
				}
				
			} catch (IOException e) {
				messages.append("\nTestServer.run:" +e);
			}
			
		}
		
	}
	class  TestServerHander implements Runnable{
		
		private Socket incoming;
		private int counter;
		
		
		public TestServerHander(Socket i) {
			this.incoming = i;
		}


		@Override
		public void run() {
			
			
			try {
				try {
					OutputStream outputStream = incoming.getOutputStream();
					PrintWriter out = new PrintWriter(outputStream,true);
					while (counter < 100) {
						counter ++ ;
						if (counter <= 10) {
							out.println(counter);
							
						}
						Thread.sleep(100);
					}
				} finally {
				   incoming.close();
				   messages.append("Closing server\n");
				}
				
				
				
			} catch (Exception e) {
				messages.append("\nTestServerHandler.run :" +e);
			}
		}
		
	}
}
