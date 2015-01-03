package vue;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Box;

public class BoxView extends JPanel {
	private Box box;
	
	public BoxView(Box b,int nb) {
		box=b;
		
		setLayout(new BorderLayout());
		
		JLabel lab = new JLabel("Boite n° "+nb);
		add(lab,BorderLayout.NORTH);
		JPanel pan = new JPanel();
		pan.setLayout(new GridLayout(b.getWidth(), b.getHeight(),2,2));
		
		for (int i=0;i<b.getWidth();i++) {
			for (int j=0; j<b.getHeight();j++)
				pan.add(new Case(b.getContent()[i][j]));
		}
		setSize(200, 200);
		pan.setSize(200, 200);
		
		add(pan,BorderLayout.CENTER);
		
		setVisible(true);
	}
	

}
