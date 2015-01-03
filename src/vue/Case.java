package vue;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class Case extends JPanel{
	private int nb=-1;
	static Color[] color = {Color.red,Color.black,Color.green,Color.pink,Color.yellow,Color.gray,Color.magenta,Color.cyan,Color.orange};
	public Case(int n) {
		nb=n;
		if (nb!=-1 && nb!=0) setBackground(color[n%color.length]);
		else setBackground(Color.white);
		setSize(30, 30);
	}

}
