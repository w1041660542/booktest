package imageio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImageIOFrame  extends JFrame{
	 private static final int DEFAULT_WIDTH = 400;
	 private static final int DEFAULT_HEIGHT = 400;
	 
	 private static Set<String> wrtiteFormats = getWriterFormats();
	 private BufferedImage[] images;
	 
	 public ImageIOFrame() {
		 setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
		 JMenu fileJMenu = new JMenu("File");
		 JMenuItem openItem = new JMenuItem("Open");
		 openItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				openFile();
			}

			
		});
		 fileJMenu.add(openItem);
		 
		 JMenu saveMenu = new JMenu("save");
		 fileJMenu.add(saveMenu);
		 Iterator<String> iter = wrtiteFormats.iterator();
		 while(iter.hasNext()) {
			 final String formatName = iter.next();
			 JMenuItem formatItem = new JMenuItem(formatName);
			 saveMenu.add(formatItem);
			 formatItem.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					saveFile(formatName);
					
				}
			});
		 }
		 JMenuItem exitItem = new JMenuItem("Exit");
		 exitItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}
		});
		 fileJMenu.add(exitItem);
		 
		 JMenuBar menuBar = new JMenuBar();
		 menuBar.add(fileJMenu);
		 setJMenuBar(menuBar); 
		 
	 }
	 public void openFile() {
		 JFileChooser chooser = new JFileChooser();
		 chooser.setCurrentDirectory(new File("."));
		 //获取读取器所支持的所有文件后缀
		 String[] extensions = ImageIO.getReaderFileSuffixes();
		 //设置文件选择器的过滤器
		 chooser.setFileFilter(new FileNameExtensionFilter("Image files", extensions));
		 int r = chooser.showOpenDialog(this);
		 if (r != JFileChooser.APPROVE_OPTION) {
			return ;
		}
		 File f = chooser.getSelectedFile();
		 Box box = Box.createVerticalBox();
		 try {
			//得到选择文件的名称
			String name = f.getName();
			//得到选择文件的后缀
			String suffix = name.substring(name.lastIndexOf('.')+ 1);
			//根据后缀获取读取器
			Iterator<ImageReader> iter = ImageIO.getImageReadersBySuffix(suffix);
			ImageReader reader = iter.next();
			ImageInputStream imageIn  = ImageIO.createImageInputStream(f);
			reader.setInput(imageIn);
			//获取读取器中图片的数目
			int count = reader.getNumImages(true);
			images = new BufferedImage[count];
			for(int i=0; i<count;i++) {
				images[i] =  reader.read(i);
				box.add(new JLabel(new ImageIcon(images[i])));
			}
			
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e);
		}
		 setContentPane(new JScrollPane(box));
		 validate();
	 }
	 
	 
	 
	 public void saveFile(final String formatName) {
		 if (images == null) {
			return ;
		}
		 Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName(formatName);
		 ImageWriter writer = iter.next();
		 JFileChooser chooser = new JFileChooser();
		 chooser.setCurrentDirectory(new File("."));
		 //获取该写入器的服务器提供者的后缀，并加到文件过滤器中
		 String[] extensions = writer.getOriginatingProvider().getFileSuffixes();
		 chooser.setFileFilter(new FileNameExtensionFilter("Image files", extensions));
		 
		 int r = chooser.showSaveDialog(this);
		 if (r != JFileChooser.APPROVE_OPTION) {
			return ;
		}
		 File f = chooser.getSelectedFile();
		 try {
			ImageOutputStream imageOut = ImageIO.createImageOutputStream(f);
			writer.setOutput(imageOut);
			writer.write(new IIOImage(images[0], null, null));
			for(int i = 1; i < images.length; i++) {
				IIOImage iioImage  = new IIOImage(images[i], null, null);
				if (writer.canInsertImage(i)) {
					writer.writeInsert(i, iioImage, null);
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e);
		}
		 
		 
		 
	 }
	 /**
	  * 查找与第一种格式相关的第一个写入器
	  * @return
	  */
	 public static Set<String> getWriterFormats(){
		 Set<String> writerFormats = new TreeSet<String>();
		 //获取写入器的所用格式名存入Set
		 Set<String> formatNames = new TreeSet<String>(Arrays.asList(ImageIO.getWriterFormatNames()));
		 //System.out.println(formatNames);
		 while(formatNames.size() > 0 ) {
			 String name = formatNames.iterator().next();
			 //通过格式名得到所需要的ImageWriter
			 Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName(name);
			 ImageWriter writer = iter.next();
			 //获取服务提供者接口得到格式的名字
			 String[] names = writer.getOriginatingProvider().getFormatNames();
			 String format = names[0];
			 if (format.equals(format.toLowerCase())) {
				format = format.toUpperCase();
			}
			 writerFormats.add(format);
			 formatNames.removeAll(Arrays.asList(names));
			 
		 }
		return writerFormats;
		 
	 }
	 
	 public static void main(String[] args) {
		ImageIOFrame iof = new ImageIOFrame();
		iof.setVisible(true);
	}
	 
}
