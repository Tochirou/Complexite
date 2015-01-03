package vue;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JPanel;

import model.Box;

public class BoxView extends JPanel {
	private Box box;
	
	public BoxView(Box b) {
		box=b;
		setLayout(new GridLayout(b.getWidth(), b.getHeight()));
		
	}
	
	public void paint() {
		
	}

}
