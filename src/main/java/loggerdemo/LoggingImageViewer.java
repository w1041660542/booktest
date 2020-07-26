package loggerdemo;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;

public class LoggingImageViewer {

	public static void main(String[] args) {
		if (System.getProperty("java.util.logging.config.class")==null 
		&& System.getProperty("java.util.logging.config.file") ==null) {
			try {
				Logger.getLogger("loggerdemo").setLevel(Level.ALL);
				final int LOG_ROTATION = 10;
				//%h是系统属性user.home的值
				Handler handler = new FileHandler("%h/LoggingImageViewer.log",0,LOG_ROTATION);
				Logger.getLogger("loggerdemo").addHandler(handler);
				
			} catch (Exception e) {
				Logger.getLogger("loggerdemo").log(Level.SEVERE, "Can not create log file handler",e);
			}
			
		}
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				Handler windowHandler = new WindowHandler();
				windowHandler.setLevel(Level.ALL);
				Logger.getLogger("loggerdemo").addHandler(windowHandler);
				
				JFrame frame =  new ImageViewerFrame();
				frame.setTitle("LoggerImageViewer");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				Logger.getLogger("loggerdemo").fine("Showing frame");
				frame.setVisible(true);
				
			}
		});
		

	}
	
	

}


