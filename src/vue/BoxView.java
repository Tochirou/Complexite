package vue;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Box;

public class BoxView extends JPanel {
	
	public BoxView(Box b,int nb) {
		
		setLayout(new BorderLayout());
		
		JLabel lab = new JLabel("Box n° "+nb);
		add(lab,BorderLayout.NORTH);
		JPanel pan = new JPanel();
		pan.setLayout(new GridLayout(b.getWidth(), b.getHeight(),2,2));
		
		for (int i=0;i<b.getWidth();i++) {
			for (int j=0; j<b.getHeight();j++)
				pan.add(new Case(b.getContent()[i][j]));
		}
		setSize(300, 300);
		pan.setSize(300, 300);
		
		add(pan,BorderLayout.CENTER);
		
		setVisible(true);
	}
}
