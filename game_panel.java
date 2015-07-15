package checkers;
/*1- main calls constructor by creating new gamePanel
2- Board is set up in constructor
3- Play button with respective handler/listener PlayHandler 
4-	PlayHandler calls setValid(char turn) turn can either be r or b 
	-Play handler starts the indefinite loop (have yet to implement end game circumstances)
5- setValid(char turn) highlights all pieces of whoevers turn it is that can be moved
6- setValid activates the valid turn if it meets conditions:
	-Slot contains a piece, the piece belongs to whoevers turn it is and the piece can be moved 
7- setValid checks if a piece can be moved by calling isValid(Piece, x, y) for that piece
8- isValid(Piece, x, y) returns true if there are possblie points that the piece can be moved to 
	-does this by calling validPoint(piece, x, y)
9- validPoints sets the slot at the desired location isValid's variable to true if there are valid points
for which it can be moved to, as well as adds those points to that slots xValid and yValid arrayLists
10- this all returns to setValid which activates that slot if its isValid boolean value is true 
11- activate() sets the button to clickable and highlights it 
12- process continues when a valid piece is clicked
13- pieceHandler class handles piece clicked and prints valid moves for that piece
	-print valid move enables and highlights valid slots for that move disenabling all others
14- those slots are handled by class slotHandler which does the actual moving
	-atm slotHandler moves pieces with equals() method in class slot 
	-equals just trades data between the slot that is to be entered with the piece that is going to enter it
15- once slot handler moves the piece the loop begins again as it calls setValid() for the other persons turn

Things to fix:
	-add jump slots to each slots validx and validy, a jump sitatuion is recognized in line 140 of the code but has yet to be handled
	-currently there are problems with equals. when you move a piece to a slot that used to have a piece problems occuer
	-King situation has not been handled yet. 
	-icon images on the jButtons that have pieces (right now they just have text) have not been added 
		*/
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;


//gameBoard Panel
public class GamePanel extends JFrame {

	private static final int NBOX = 8;	//number of boxes
	private static final int LBOX = 60;	//box length
	private static final int MAXXY = NBOX * LBOX;	//max panel size for x and y
	private static final Color Black = new Color(0x726363);
	private static final Color White = new Color(0xffffff);
	private static final Color Red = new Color(0xF70000);
	private static final Color DBlack = new Color(0x000000);
    private Slot[][] boardArray;
    private char turn; 
    JLabel playerTurn;
    JPanel p1;
    JButton play;
    Slot prevPiece;
    boolean handleJump;
    
   
    
    
	public GamePanel(){
        super("Checkers");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        p1 = new JPanel();
        p1.setSize(500, 500);
        p1.setLayout(new GridLayout(8,8));
		playerTurn = new JLabel("Click Play to begin");
		boardArray = new Slot[NBOX][NBOX];
		play = new JButton("Play");

		//set game board to initial game state
		for (int row = 0; row <NBOX ; row++){
			for (int col = 0; col <NBOX; col++){
				if ((row + col) % 2 == 0){
					if (col < 3){
						boardArray[row][col] = new Slot(true, 'b');
						(boardArray[row][col]).b.setText("Black");
						boardArray[row][col].b.addActionListener(new PieceHandler(boardArray[row][col]));

					}
					else if (col > 4){
						boardArray[row][col] = new Slot(true, 'r');
						(boardArray[row][col]).b.setText("Red");
						boardArray[row][col].b.addActionListener(new PieceHandler(boardArray[row][col]));

					}
					else{
						boardArray[row][col] = new Slot();
						boardArray[row][col].b.addActionListener(new SlotHandler(boardArray[row][col]));
					}
					boardArray[row][col].setBackground(Black);
					boardArray[row][col].b.setPreferredSize(new Dimension(50, 50));
					p1.add(boardArray[row][col].b);
					
				}
				else{
					boardArray[row][col] = new Slot();
					boardArray[row][col].setBackground(White);
					p1.add(boardArray[row][col].b);	
					boardArray[row][col].b.addActionListener(new SlotHandler(boardArray[row][col]));

				}
				boardArray[row][col].b.setOpaque(true);
				boardArray[row][col].b.setEnabled(false);
			}
		}
		PlayHandler phandler = new PlayHandler();
		play.addActionListener(phandler);
		add(p1);
		add(play, BorderLayout.NORTH);
        add(playerTurn, BorderLayout.SOUTH);
        JMenu checkerMenu = new JMenu("File");
        checkerMenu.setMnemonic('F');
        JMenuItem newGame = new JMenuItem("New");
        newGame.setMnemonic('N');
        checkerMenu.add(newGame);
        newGame.addActionListener(new ActionListener(){
        		@Override
                public void actionPerformed(ActionEvent e){
                   new GamePanel();      
                }
        });
        
        JMenuBar bar = new JMenuBar();
        bar.add(checkerMenu);
        this.setJMenuBar(bar);
        this.setVisible(true);
	}
	
	public void deActivateBoard(){
		for (int row = 0; row <NBOX ; row++){
			for (int col = 0; col <NBOX; col++){
				if (boardArray[col][row].b.isEnabled()){
					boardArray[col][row].b.setEnabled(false);
					boardArray[col][row].b.setBorder(boardArray[col][row].border1);
				}
			}
		}
	}
	

	
	public  void validPoint(Slot p, int y, int x){
		if(x > 7 | y >7){
			return;
		}	
		//check if that slot holds a piece
		if(boardArray[x][y].isPiece){
			//handle situation in which there is a valid move that entails a jump
			if ((boardArray[x][y]).turn != p.turn){
				handleJump = true;
				return;
			}
			//handle situation in which piece is players own piece 
			else{
				p.setValid(false);
			}
		}
		else{	//else being that the position is empty/contains a slot not a piece and hence is a valid move making this a valid piece
			//if (p.turn == 'r'){System.out.print("IN HERE");}
			p.addValid((x), (y));
			p.setValid(true);
		}
	}
	
	private void handleJump(Slot piece, int y, int x) {
		if (piece.getTurn() == 'r'){
			if (boardArray[x][y].isPiece && boardArray[x][y].getTurn() != 'r'){
				
			}			
		}
		else if (piece.getTurn() == 'b'){
			if (boardArray[x][y].isPiece && boardArray[x][y].getTurn() != 'b'){
				
			}
		}
		else{
			System.out.println("problem encountered");
		}		
	}

	//returns if there are any possible moves for a slot and if there are activates them and ads them to the validMoves array
	public boolean isValid(Slot p, int y, int x){

		if (p.turn == 'b'){
			//Black is its own case because black goes left to right 
			//check cases y=0 and y=7 to avoid array out of bounds exception
			if (y == 0){					//[x+1][y+1] is only valid poisiton a black piece can move to from (x,y) if y = 0
				validPoint(p, (x+1), (y+1));
			}
			
			else if (y == 7){
				//check [x+1][y-1]
				//check if that slot holds a piece
				validPoint(p, (x+1), (y-1));
			}
			else {
				validPoint(p, (x+1), (y+1));
				validPoint(p, (x+1), (y-1));
			}
		}
		
		else if (p.turn == 'r'){
			//r is a different circumstance because red player goes right to left
			//check cases y=0 and y=7 to avoid array out of bounds exception
			if (y == 0){
				validPoint(p, (x-1), (y+1));
			}
			else if (y ==  7){
				validPoint(p, (x-1), (y-1));
			}
			else {
				validPoint(p, (x-1), (y-1));
				validPoint(p, (x-1), (y+1));
			}
		}
		return p.getValid();
	}
	
	//sets valid checkers that player can touch 
	public void setValid(char t){
		for (int row = 0; row <NBOX ; row++){
			for (int col = 0; col <NBOX; col++){
				//if the slot contains a piece, the piece can be moved validly and the piece belongs to the current player then activate that valid piece
				if ((boardArray[row][col].isPiece && boardArray[row][col].getTurn() == t )){
					if (isValid((boardArray[row][col]), row , col)){
						boardArray[row][col].activate();
					}
				}
			}
		}
	}
	
	public class PlayHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			playerTurn.setText("Blacks turn");
			setValid('b');
		}
		
	}
	
	public class PieceHandler implements ActionListener{
		private final checkers.Slot piece;

		public PieceHandler(Slot p) {
			this.piece = p;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			//handle initial valid piece click and respond by highlighting valid places that piece can go
			printValidMoves(piece);
			prevPiece = this.piece;
		}
		
	}
	
	public class SlotHandler implements ActionListener{
		private checkers.Slot slot;
		
		public SlotHandler(Slot s){
			this.slot = s;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			//Move previous selected piece to whatever valid location is clicked
			Slot temp = new Slot();
			temp.equals(this.slot);
			this.slot.equals(prevPiece);
			prevPiece.equals(temp);
			deActivateBoard();
			clearValid(this.slot);	//clear pieces valid moves for next turn
			
			if (this.slot.turn=='b'){
				playerTurn.setText("Reds Turn");
				setValid('r');
				
			}
			else if (this.slot.turn=='r'){
				playerTurn.setText("Blacks Turn");
				setValid('b');			
			}
			else {
				System.out.println("ran into a problem");
			}
		
	}
		
	}
	
	public void clearValid(Slot piece) {
		if(!piece.xValid.isEmpty() && !piece.yValid.isEmpty()){
		piece.yValid.clear();
		piece.xValid.clear();
		}

	}
	
	public void printValidMoves(Slot piece){
		deActivateBoard();
		for (int i = 0; i< piece.xValid.size(); i++){
			boardArray[piece.yValid.get(i)][ piece.xValid.get(i)].activate();
		}	
	}
	

	public static void main (String[] args){
		GamePanel p = new GamePanel();
	}


}
