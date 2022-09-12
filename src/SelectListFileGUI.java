import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SelectListFileGUI extends JPanel{
	private String path;
	
	SelectListFileGUI(String title){
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File(System.getProperty("java.class.path")));
		fc.setFileFilter(new FileNameExtensionFilter(title, "txt"));
		fc.setAcceptAllFileFilterUsed(true);
		fc.setMultiSelectionEnabled(false);	
		
		fc.setDialogTitle(title);
		
		int result = fc.showOpenDialog(null);
		fc.setApproveButtonText("OK");
		
		if (result == JFileChooser.APPROVE_OPTION) {
			path = fc.getSelectedFile().getAbsolutePath();
		}
	}
	
	public String getPath(){
		return path;
	}
}
	