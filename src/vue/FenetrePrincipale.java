package vue;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Box;

public class FenetrePrincipale extends JFrame{

	public FenetrePrincipale(List<Box> boxes, int nbbox) {
		setLayout(new BorderLayout());
		
		setSize(1500, 800);
		
		JLabel lab = new JLabel("Best solution with "+nbbox+" boxe(s)");
		
		add(lab,BorderLayout.NORTH);
		
		JPanel pan = new JPanel();
		pan.setLayout(new FlowLayout());
		for (int i=0; i<boxes.size();i++) {
			pan.add(new BoxView(boxes.get(i),i+1));
		}
		pan.setSize(1500,800);
		pan.setVisible(true);
		
		add(pan,BorderLayout.CENTER);
		
		setVisible(true);
		
		
	}

}
