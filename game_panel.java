package checkers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;


//gameBoard Panel
public class GamePanel extends JPanel {

	private static final int NBOX = 8;	//number of boxes
	private static final int LBOX = 60;	//box length
	private static final int MAXY = NBOX * LBOX;	//max panel size for x and y
	private static final Color Black = new Color(0x726363);
	private static final Color White = new Color(0xffffff);
	
	public void GamePanel(){
		add(this);
	}
	
	public void paintComponent(Graphics g) {
		Graphics g2d = (Graphics2D) g;
		for (int row = 0; row <NBOX ; row++){
			for (int col = 0; col <NBOX; col++){
				if ((row + col) % 2 == 0)
					g2d.setColor(White);
				else g2d.setColor(Black);
				g2d.fillRect(col * LBOX, row * LBOX, LBOX, LBOX);
			}
		}
	}
	
	public static void main (String[] args){
		JFrame frame = new JFrame("CheckerBoard");
		GamePanel panel = new GamePanel();
		frame.setSize(500, 500);
		frame.add(panel);
		frame.setVisible(true);
	}
}
