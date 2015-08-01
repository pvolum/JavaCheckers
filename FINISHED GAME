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
for which it can be moved to, as well as adds those points to that slots colValid and rowValid arrayLists
10- this all returns to setValid which activates that slot if its isValid boolean value is true 
11- activate() sets the button to clickable and highlights it 
12- process continues when a valid piece is clicked
13- pieceHandler class handles piece clicked and prints valid moves for that piece
	-print valid move enables and highlights valid slots for that move disenabling all others
14- those slots are handled by class slotHandler which does the actual moving
	-atm slotHandler moves pieces with equals() method in class slot 
	-equals just trades data between the slot that is to be entered with the piece that is going to enter it
15- once slot handler moves the piece the loop begins again as it calls setValid() for the other persons turn

Things to add:
	- jump 
	
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
	

	//oldRow/oldCol piece thats moving
	//row/col is possible places above piece can move
	public  void validPoint(Slot p, int col, int row, int oldCol, int oldRow){
		if((row > 7 | col >7) | (row < 0 | col < 0)){
			p.setValid(false);
			return;
		}	
		
		//check if that slot holds a piece
		if(boardArray[row][col].isPiece){
			//handle situation in which there is a valid move that entails a jump
			if ((boardArray[row][col]).turn != p.turn){
				if ((boardArray[row][col]).turn == 'b'){
					//handle a red piece trying to jump over a black piece 
					if (row > oldRow){
						if ((oldRow + 2) <= 7 && (oldCol -2 >= 0)){
							if (!boardArray[oldRow+2][oldCol-2].isPiece){
								//TODO: remove piece at location boardArray[row][col] and decrement piece counter for black
								//if place you would jump to is not a piece then set valid 
								System.out.println("JUMP: old (row,col)= (" + oldRow + "," + oldCol +")" + "new (row,col) being added as valid= (" + (oldRow-2) + "," + (oldCol+2) +")");
								p.addValid(oldRow+2, oldCol-2);
								p.setValid(true);
							}
							else {
								p.setValid(false);
							}
						}
					}
					else if (row < oldRow){
						if ((oldRow - 2) >= 0 && (oldCol-2 >= 0)){
							if (!boardArray[oldRow-2][oldCol-2].isPiece){
								//TODO: remove piece at location boardArray[row][col] and decrement piece counter for black
								//if place you would jump to is not a piece then set valid 
								p.addValid(oldRow - 2, oldCol-2);
								System.out.println("JUMP: old (row,col)= (" + oldRow + "," + oldCol +")" + "new (row,col) being added as valid= (" + (oldRow-2) + "," + (oldCol-2) +")");
								p.setValid(true);
							}
							else {
								p.setValid(false);
							}
						}
					}
					else {
						System.out.println("SHOULD NEVER PRINT");
					}	
				}
				else if ((boardArray[row][col]).turn =='r'){
					//handle a black piece trying to jump over a red piece 
					if (row > oldRow){
						if ((oldRow + 2) <= 7 && (oldCol+2 <= 7)){
							if (boardArray[oldRow+2][oldCol+2].isPiece == false){
								//if place you would jump to is not a piece then set valid 
								//TODO: remove piece at location boardArray[row][col] and decrement piece counter for red
								p.addValid(oldRow + 2, oldCol + 2);
								p.setValid(true);
							}
							else{
								p.setValid(false);
							}
						}
					}
					else if (row < oldRow){
						if ((oldRow - 2) >= 0 && (oldCol+2 <= 7)){
							if (boardArray[oldRow-2][oldCol+2].isPiece == false){
								//if place you would jump to is not a piece then set valid 
								//TODO: remove piece at location boardArray[row][col] and decrement piece counter for red
								p.addValid(oldRow-2, oldCol+2);
								p.setValid(true);
							}
							else {
								p.setValid(false);
							}
						}					
					}	
				}
				
			else{
				System.out.print("Problem Encountered 1");
			}
		}
			//handle situation in which piece is players own piece 
			else{
				p.setValid(false);
			}
		}
		else{	//else being that the position is empty/contains a slot not a piece and hence is a valid move making this a valid piece
			p.addValid((row), (col));
			p.setValid(true);
		}
	}
	

	//returns if there are any possible moves for a slot and if there are activates them and ads them to the validMoves array
	public boolean isValid(Slot p, int y, int x){
		
		if(p.isKing){
			validKingPoint(p);
			return p.getValid();
		}

		if (p.turn == 'b'){
			//Black is its own case because black goes left to right 
			//check cases y=0 and y=7 to avoid array out of bounds exception
			if (y == 0){					//[x+1][y+1] is only valid poisiton a black piece can move to from (x,y) if y = 0
				validPoint(p, (x+1), (y+1), x , y);
			}
			
			else if (y == 7){
				//check [x+1][y-1]
				//check if that slot holds a piece
				validPoint(p, (x+1), (y-1), x , y);
			}
			else {
				boolean temp = false;
				validPoint(p, (x+1), (y+1), x, y);
				if (p.valid)
					temp = true;
				validPoint(p, (x+1), (y-1), x, y);
				if (temp)
					p.setValid(true);
			}
		}
		
		else if (p.turn == 'r'){
			//r is a different circumstance because red player goes right to left
			//check cases y=0 and y=7 to avoid array out of bounds exception
			if (y == 0){
				validPoint(p, (x-1), (y+1), x , y);
			}
			else if (y ==  7){
				validPoint(p, (x-1), (y-1), x , y);
			}
			else {
				boolean temp = false;
				validPoint(p, (x-1), (y-1), x , y);
				if (p.valid)
					temp = true;
				validPoint(p, (x-1), (y+1), x , y);
				if (temp)
					p.setValid(true);
			}
		}
		return p.getValid();
	}
	
	private void validKingPoint(Slot p) {
		// TODO Auto-generated method stub
		
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
			
			//empty valid arrays for all pieces of that turn that are valid, do this to instantiate the arrays with updated variables later
			for (int row = 0; row <NBOX ; row++){
				for (int col = 0; col <NBOX; col++){
					if ((boardArray[row][col].isPiece && boardArray[row][col].getTurn() == prevPiece.getTurn() )){
						if(boardArray[row][col].valid)
							clearValid(boardArray[row][col]);
					}
				}
			}
			
		
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
			
			//remove action listeners from both slot and piece 
		    for( ActionListener al : slot.b.getActionListeners() ) 
		        slot.b.removeActionListener( al );
			
		    for( ActionListener al : prevPiece.b.getActionListeners() )
		        prevPiece.b.removeActionListener( al );
		    
		    slot.b.addActionListener(new PieceHandler(this.slot));
		    prevPiece.b.addActionListener(new SlotHandler(prevPiece));
		    
		    //transfer all data over so prevpiece has empty slot data and new slot had prevPiece data
		    Slot temp = new Slot();
		    temp.equals(this.slot);
		    slot.equals(prevPiece);
		    prevPiece.equals(temp);
	
			System.out.println("new Piece slots turn: " + slot.turn);
			System.out.println("old slots turn where piece used to be: " + prevPiece.turn);

		    
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
		if(!piece.colValid.isEmpty() && !piece.rowValid.isEmpty()){
		piece.rowValid.clear();
		piece.colValid.clear();
		}

	}
	
	public void printValidMoves(Slot piece){
		deActivateBoard();
		for (int i = 0; i< piece.colValid.size(); i++){
			System.out.println("ROW: " +piece.rowValid.get(i) + " COL: " +  piece.colValid.get(i));
			boardArray[piece.rowValid.get(i)][ piece.colValid.get(i)].activate();
		}	
		System.out.print("\n");
	}
	

	public static void main (String[] args){
		GamePanel p = new GamePanel();
	}


}
