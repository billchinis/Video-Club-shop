import javax.swing.*;

public class ComboBoxGUI {
	/*
	 * If Cancel is clicked or Frame is closed, text = ""
	 */
	
	private String text;
	
	ComboBoxGUI(String label, String title, Object[] possibilities) {
		JFrame frame = new JFrame();
		
		text = (String)JOptionPane.showInputDialog(
                frame,
                label,
                title,
                JOptionPane.PLAIN_MESSAGE,
                null,
                possibilities,
                possibilities[0]);
		
		if(text==null){
			text = "";
		}
	}
	
	public String getText(){
		return text;
	}
}
