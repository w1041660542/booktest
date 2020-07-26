package robot;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonActionListener;

public class ButtonFrame extends JFrame {

	private JPanel buttonPanel;
	private static final int DEFAULT_WIDTH = 300;
	private static final int DEFAULT_HEGIHT = 400;
	
	
	public ButtonFrame() {
		
		setSize(DEFAULT_WIDTH, DEFAULT_HEGIHT);
		
		JButton yellowButton = new JButton("Yellow");
		JButton blueButton = new JButton("Blue");
		JButton redButton = new JButton("Red");
		
		buttonPanel =  new JPanel();
		
		buttonPanel.add(yellowButton);
		buttonPanel.add(blueButton);
		buttonPanel.add(redButton);
		
		add(buttonPanel);
		
		//create button action 
		ColorAction yellowAction = new ColorAction(Color.YELLOW);
		ColorAction blueAction = new ColorAction(Color.BLUE);
		ColorAction redAction  =  new ColorAction(Color.RED);
		
		//associte(联系) action with button 
		yellowButton.addActionListener(yellowAction);
		blueButton.addActionListener(blueAction);
		redButton.addActionListener(redAction);
		
		
		
		
		
		
		
		
		
		
		
		
		

	}

	private class ColorAction implements ActionListener {
		
		private Color backgroundColor;
		

		public ColorAction(Color c) {
			this.backgroundColor = c;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			buttonPanel.setBackground(backgroundColor);
			
		}
		
	}

	
}
