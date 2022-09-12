import javax.swing.*;

import java.awt.*;
import java.awt.event.WindowEvent;

public class ImageGUI {
	/*
	 * choose = -1 when frame is closed
	 * choose = 0 when Rent button is clicked
	 */
	private int choose = -1;
	
	ImageGUI(JLabel label, String title, String path, boolean avCopies){		
		Frame frame = new Frame();
		
		JPanel panel = new JPanel();
		
		GridBagLayout layout = new GridBagLayout();
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		panel.setLayout(layout);
		
		ImageIcon gameIcon = new ImageIcon(path);
		Image img = gameIcon.getImage();
		Image newimg = img.getScaledInstance(230, 300,  java.awt.Image.SCALE_SMOOTH);
		gameIcon = new ImageIcon(newimg);
		
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.ipadx = 15;
		gbc.gridheight = 2;
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(new JLabel(gameIcon), gbc);
		
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.insets = new Insets(10, 0, 10, 10);
		gbc.weightx = 1.0;
		gbc.gridx = 1;
		gbc.gridy = 0;
		panel.add(label, gbc);
		
		frame.add(panel);
		
		frame.pack();

		Object[] buttons = {"Rent"};
		
		if(avCopies){
			choose = JOptionPane.showOptionDialog(frame, panel, title,
			        JOptionPane.CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
			        null, buttons, null);
		}
		else{
			frame.setVisible(true);
			
			frame.addWindowListener(new java.awt.event.WindowAdapter() {
			    @Override
			    public void windowClosing(WindowEvent e) {
			    	choose = -1;
			    }
			});
			
		}
	}
	
	public int getChoose(){
		return choose;
	}
}
