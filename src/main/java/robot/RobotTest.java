package robot;

import java.awt.EventQueue;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;


import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class RobotTest {
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				
				//make frame with a button panel
				ButtonFrame frame = new ButtonFrame();
				frame.setTitle("ButtonTest");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);

			}
		});
		//attach a robot to the screen device
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice screen = environment.getDefaultScreenDevice();
		
		try {
			final Robot robot = new Robot(screen);
			//等待直到当前队列的事件都被处理过了
			robot.waitForIdle();
			new Thread() {
				public void run() {
					runTest(robot);
				};
			}.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void runTest(Robot robot) {
		//simulate a space bar press 
		robot.delay(2000);
		//模拟按下键和释放键
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_TAB);
		robot.keyPress(' ');
		robot.keyRelease(' ');
		
		//simulate a mouse click over the rightmost button
		robot.delay(2000);
		//模拟鼠标按下键和释放键
		robot.mouseMove(220, 40);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		
		//capture the screen and show the resulting image
		robot.delay(2000);
		BufferedImage image =  robot.createScreenCapture(new Rectangle(0, 0, 400, 300));
		
		ImageFrame frame = new ImageFrame(image);
		frame.setVisible(true);
		
		
		
	}
	

}
class ImageFrame extends JFrame{
	private static final int DEFAULT_WIDTH = 450;
	private static final int DEFAULT_HEIGHT = 350;
	
	public ImageFrame(Image image) {
		setTitle("Capture");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		
		JLabel label = new JLabel(new ImageIcon(image));
		add(label);
	}
}
