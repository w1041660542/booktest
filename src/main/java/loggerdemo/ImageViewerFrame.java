package loggerdemo;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class ImageViewerFrame extends JFrame {
	
	
	/**
	 *  The frame that shows the image 
	 */
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_WIDTH = 300;
	private static final int DEFAULT_HEIGHT = 400;
	
	private JLabel lable;
	private static Logger logger = Logger.getLogger("loggerdemo");

	
	public ImageViewerFrame() {
	
		logger.entering("ImageViewerFrame", "<init>");
		setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
		
		//set up menu bar
		JMenuBar menuBar = new JMenuBar();
	    setJMenuBar(menuBar);
		
		JMenu menu = new JMenu("File");
		menuBar.add(menu);
		
		JMenuItem openItem = new JMenuItem("open");
		menu.add(openItem);
		
		openItem.addActionListener(new FileOpenListener());
		JMenuItem exitItem = new JMenuItem("Exit");
		menu.add(exitItem);
		exitItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.fine("Exiting..");
				System.exit(0);
				
			}
		});
		
		//use label to dispaly the images
		lable  =  new JLabel();
		add(lable);
		logger.exiting("ImageViewerFrame", "<init>");
		
		
	}
	
	private class FileOpenListener implements ActionListener {
		
		
		@Override
		public void actionPerformed(ActionEvent e) {
		logger.entering("ImageViewerFrame.FileOpenListener","actionPerformed",e);
		
		//set up file chooser
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("."));
		
		//accept all files ending with .gif
		chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {

			@Override
			public boolean accept(File f) {
				
				return f.getName().toLowerCase().endsWith(".gif") || f.isDirectory();
			}

			@Override
			public String getDescription() {
				
				return "GIF Images";
			}
			
		});
		
		//show File chooser dialog
		int r  = chooser.showOpenDialog(new ImageViewerFrame());
		
		//if image file accepted,set it  as icon of the lable
		if (r==JFileChooser.APPROVE_OPTION) {
			String name = chooser.getSelectedFile().getPath();
			logger.log(Level.FINE, "Reading file{0}", name);
			lable.setIcon(new ImageIcon(name));
			
		}else {
			logger.fine("File open dialog canceled");
		}
		logger.entering("ImageViewerFrame.FileOpenLisenter", "actionPerformed");
		
		
		
		
		
		

		}

	}

}


