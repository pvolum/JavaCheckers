package checkers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;


//gameBoard Panel
public class GamePanel extends JPanel {

	private static final int NBOX = 8;	//number of boxes
	private static final int LBOX = 60;	//box length
	private static final int MAXXY = NBOX * LBOX;	//max panel size for x and y
	private static final Color Black = new Color(0x726363);
	private static final Color White = new Color(0xffffff);
	private static final Color Red = new Color(0xF70000);
	private static final Color DBlack = new Color(0x000000);
    private final Rectangle2D.Double bounds = new Rectangle2D.Double();
    private final Ellipse2D.Double selected = new Ellipse2D.Double();
    private char boardArray[][];
    private Piece pieceArray [][];
    private char turn; 
    
	public GamePanel(){
		pieceArray = new Piece [NBOX][NBOX];
		boardArray = new char [NBOX][NBOX];
		//set game board to initial game state
		for (int row = 0; row <NBOX ; row++){
			for (int col = 0; col <NBOX; col++){
				if ((row + col) % 2 == 0){
					if (col < 3)
						boardArray[row][col] = 'b';
					else if (col > 4)
						boardArray[row][col] = 'r';
					else
						boardArray[row][col] = '$';
				}
				else{
					boardArray[row][col] = '$';
				}
			}
		}
		//for programmers sake, prints board to system.out
		printBoard();
	}
	
	
	
	private void printBoard() {//prints array version of board to system.out
		for (int row = 0; row <NBOX ; row++){
			for (int col = 0; col <NBOX; col++){
				//if (pieceArray[row][col] != null) Joey activate and try and fix the piece array if you want
					System.out.print(boardArray[row][col] + " ");
				//else System.out.print('$' + " ");
			}
			System.out.print("\n");
			}		
	}



	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
        g2d.scale(
                (double) this.getWidth() / MAXXY,
                (double) this.getHeight() / MAXXY);
		for (int row = 0; row <NBOX ; row++){
			for (int col = 0; col <NBOX; col++){
				if ((row + col) % 2 == 0){
					g2d.setColor(White);
					g2d.fillRect(col * LBOX, row * LBOX, LBOX, LBOX);
						if ((col < 3)){
							pieceArray[row][col] = new Piece('b',col * LBOX,row * LBOX ); //add to piece array
							addPiece(g2d, DBlack,col * LBOX,row * LBOX );
						}
						if (col >4){
							addPiece(g2d, Red,col * LBOX,row * LBOX );
							pieceArray[row][col] = new Piece('r',col * LBOX, row * LBOX ); //add to piece array
						}
				}
				else{
					g2d.setColor(Black);
					g2d.fillRect(col * LBOX, row * LBOX, LBOX, LBOX);
				}
			}
		}
	}
	public static void addPiece(Graphics g, Color c, int x, int y){
		g.setColor(c);
		g.fillOval(x + 5, y + 5, LBOX - 10, LBOX - 10);
	}
	
	public class Piece{
		boolean isValid;
		char color;
	    private char validArray[][];
	    int x, y;	//x and y coordinates of piece
		
		public Piece(char c, int x1, int y1) {
			color = c;
			x = x1;
			y = y1;
		}

		public String toString(){
			return Character.toString(color);
		}
		public void pieceValid(){
			//function that sets isValid to true or false if the piece is movable
			
		}
		
		public void validMoves(){
			//if the piece is movable run this function to fill validArray with valid moves it can make
		}
	}
	
	
	
	
    private class MouseHandler extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {

        }

		@Override
        public void mouseReleased(MouseEvent e) {

        }
    }
	
    private double alterX(int x) {
		// TODO Auto-generated method stub
		return 0;
	}

	public static void main (String[] args){
		JFrame frame = new JFrame("CheckerBoard");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GamePanel panel = new GamePanel();
		frame.setSize(500, 500);
		frame.setLayout(new BorderLayout());
		frame.add(panel, BorderLayout.CENTER);
		JLabel whosTurn = new JLabel("Black starts");
		frame.add(whosTurn, BorderLayout.SOUTH);
		JMenu checkerMenu = new JMenu("File");
		checkerMenu.setMnemonic('F');
		JMenuBar bar = new JMenuBar();
		bar.add(checkerMenu);
		frame.setJMenuBar(bar);
		frame.setVisible(true);
	}
}
