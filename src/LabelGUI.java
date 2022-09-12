import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class LabelGUI {
	/* 
	 * choose = -1 when frame is closed
	 * choose = 0 when 1st button is clicked
	 * choose = 1 when 2nd button is clicked etc.
	*/
	
	private int choose = -1;
	
	LabelGUI(String label, String title, String[] buttons){
		JFrame frame = new JFrame();
		
		choose = JOptionPane.showOptionDialog(
				frame, 
				label, 
				title,
		        JOptionPane.PLAIN_MESSAGE,
		        JOptionPane.PLAIN_MESSAGE,
		        null,
		        buttons,
		        null);
	}
	
	LabelGUI(String label, String title){
		JFrame frame = new JFrame();
		
		JOptionPane.showOptionDialog(
				frame,
				label,
				title,
				JOptionPane.PLAIN_MESSAGE,
				JOptionPane.PLAIN_MESSAGE, 
				null, 
				new Object[]{}, 
				null);
	}
	
	public int getChoose(){
		return choose;
	}
}
