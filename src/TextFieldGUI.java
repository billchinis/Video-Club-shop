import javax.swing.*;

public class TextFieldGUI {
	/*
	 * If Cancel is clicked or Frame is closed, text = ""
	 * If OK is clicked and text is empty, it will read again.
	 */
	
	private String text;
	
	TextFieldGUI(String label, String title) {
		JFrame frame = new JFrame();
		
		text = (String)JOptionPane.showInputDialog(
                frame,
                label,
                title,
                -1);
		
		if(text!=null){
			if(text.isEmpty()){
				new TextFieldGUI(label, title);
			}
		}
		else{
			text = "";
		}
	}
	
	public String getText(){
		return text;
	}
}
