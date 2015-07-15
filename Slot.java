package checkers;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class Slot {
	Color background;
	JButton b;
	Border border;
	Border border1;
	//if isPiece the following are initialized 
	boolean isPiece;
	char turn;
	boolean isKing;
	boolean valid;
	ArrayList<Integer> xValid;
	ArrayList<Integer> yValid;

	
	//constructor for slot w/o a piece
	public Slot(){
		this(false, 's');
	}
	
	public Slot(boolean isP, char t){
		if(isP){
			isPiece = true;
			turn = t;
			isKing = false;
		}
		xValid = new <Integer> ArrayList();
		yValid = new <Integer> ArrayList();
		//code that both slot and piece will run
		b = new JButton();
		border= new LineBorder(Color.YELLOW, 5);
		border1= new LineBorder(Color.YELLOW, 0);
		b.setBorder(border1);
		b.setEnabled(false);
	}
			
	public void equals(Slot s){
		//copy everything but background, valid and validMOves
		//valid characteristics are updated prior to next turn
		//this.b = s.b;
		this.b.setText(s.b.getText());
		this.isPiece = s.isPiece;
		this.turn = s.turn;
		this.isKing = s.isKing;
		this.xValid = s.xValid;
		this.yValid = s.yValid;

		
	}
	//Piece methods
	public void setBackground(Color c){
		background = c;
		b.setBackground(background);
	}

	public void activate() {
		b.setEnabled(true);
		b.setBorder(border);
	}
	
	public void setTurn(char c){
		turn = c;
	}
	
	public void setValid(boolean b){
		valid = b;
	}
	
	public void addValid(int y, int x){
		xValid.add(x);
		yValid.add(y);
	}
	
	public boolean getValid(){
		return valid;
	}
	
	public char getTurn(){
		return turn;
	}
}

