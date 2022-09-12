import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class ArrayListGUI extends JPanel{
	/*
	 * If Cancel is clicked or Frame is closed, list is empty
	 * If Add is clicked text is saved on the list
	 * If OK is clicked reading is finished
	 */
	private ArrayList<String> list = new ArrayList<String>(1);
	private String label, title;
	
	public ArrayListGUI(String label, String title) {
		this.label = label;
		this.title = title;
		
		readArrayList();
	}
	
	public void readArrayList(){
		Frame frame = new Frame();
		
		String text;
		Object[] options1 = { "Add", "OK", "Cancel" };
		
		JPanel panel = new JPanel();
		
		GridBagLayout layout = new GridBagLayout();
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		panel.setLayout(layout);
		
		JLabel jlabel = new JLabel(label);
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(10, 10, 0, 10);
		panel.add(jlabel, gbc);
		
		JTextField textField = new JTextField(20);
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(0, 10, 0, 10);
		panel.add(textField, gbc);
		
		frame.add(panel);
		
		frame.pack();
		
		int choose = JOptionPane.showOptionDialog(frame, panel, title,
		        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
		        null, options1, null);
		
		if(choose == 0){
			text = textField.getText();
			
			if(!text.isEmpty()){
				list.add(text);
			}
			
			readArrayList();
		}
		else if(choose == 1){
			if(list.isEmpty()){
				readArrayList();
			}
		}
		else{
			list = null;
		}
	}
	
	public ArrayList<String> getList(){
		return list;
	}
}
