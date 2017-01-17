package imageViewerUI;

import java.io.File;

import javax.swing.JButton;

public class CustomJButton  extends JButton {
	private File icon_file;
	public CustomJButton() {
		super();
	}
	public CustomJButton(File image_file) {
		super();
		this.icon_file = image_file;
	}
	public File getImagePath(){
		return this.icon_file;
	}
	void setImagePath(File image_file){
		this.icon_file = image_file;
	}
}
