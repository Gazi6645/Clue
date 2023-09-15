package clueGame;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.color.*;

public abstract class player extends JPanel{
	public String name;
	private Color color;
	public int row;
	public int col;
	protected Set<Card> hand;
	protected Set<Card> known;
	private boolean isComp;
	
	
	public player(String name, String color, int row, int col) {
		super();
		this.name = name;
		this.color = parseColor(color);
		//figure out where players start
		this.row = row;
		this.col = col;
		hand = new HashSet<Card>();
		known = new HashSet<Card>();
	}


	private Color parseColor(String color) {
		// TODO Auto-generated method stub
		if(color.equals("Blue")) {
			return Color.BLUE;
		}

		if(color.equals("Green")) {
			return Color.GREEN;
		}
		if(color.equals("Yellow")) {
			return Color.YELLOW;
		}
		if(color.equals("Red")) {
			return Color.RED;
		}

		if(color.equals("Purple")) {
			return Color.MAGENTA;
		}
		if(color.equals("Orange")) {
			return Color.ORANGE;
		}
		return Color.BLACK;
	}


	public void updteHand(Card Card) {
		hand.add(Card);
	}
	public void updateKnown(Card Card) {
		known.add(Card);
	}
	public Card createCard() {
		Card card = new Card(name, cardType.PLAYER);
		return card;
	}


	public Set<Card> getHand() {
		// TODO Auto-generated method stub
		return hand;
	}


	public boolean isComp() {
		return isComp;
	}


	public void setComp(boolean isComp) {
		this.isComp = isComp;
	}





	public Card createSuggestion() {
		return null;
		// TODO Auto-generated method stub
		
	}


	public void selectTargets(int i) {
		
		
	}


	public abstract void roomSeenAdd(char c);


	public abstract void setLocation(int row, int col);


	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}


	public Set<Card> getKnown() {
		return known;
		
	}


	public void Draw(Graphics g, int cellWidth, int cellHeight) {
		// TODO Auto-generated method stub
		g.setColor(color);
		g.fillOval(col*cellWidth, row*cellHeight, cellWidth, cellHeight);
		
		
	}
	protected void moveToNewLocation(Graphics g, int cellWidth, int cellHeight) {
		int currx, curry, newx, newy;
		currx = this.getX();
		curry = this.getY();
		newx = col*cellWidth;
		newy = row*cellHeight;
		int changeX, changeY;
		changeX = newx-currx;
		changeY = newy-curry;
		int numFrames = 10;
		//determine how many updates to move to new location
		changeX = changeX/numFrames;
		changeY = changeY/numFrames;
		for(int i=0; i<numFrames; i++) {
			try {
				Point pToMove = new Point();
				pToMove.setLocation(this.getX()+changeX, this.getY()+changeY);
				this.setLocation(pToMove);
				this.repaint();
				TimeUnit.SECONDS.sleep((long) 0.1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
		}
		
		// TODO Auto-generated method stub
		
	}


	public void makeSuggestion() {
		// TODO Auto-generated method stub
		
	}
	
}
