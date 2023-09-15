package clueGame;

import java.awt.Point;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import javax.swing.JOptionPane;

public class ComputerPlayer extends player {
	private Board board;
	private ArrayList<Room> roomSeen;
	public boolean canSuggest;
	private static final Random rn = new Random(5);
	private ArrayList<Card> lastSuggest;
	private boolean accuse;
	public ComputerPlayer(String name, String color, int row, int col, Board board) {
		super(name, color, row, col);
		setAccuse(false);
		this.board = board;
		roomSeen = new ArrayList<Room>();
		lastSuggest = new ArrayList<Card>(); 
		setComp(true);
		canSuggest = false;
		roomSeen.add(null);
		

		// TODO Auto-generated constructor stub
	}

	public void selectTargets(int steps) {
		// TODO Auto-generated method stub
		System.out.println(row);
		System.out.println(col);
		System.out.println(board.getCell(row, col));
		board.calcTargets(board.getCell(row, col), steps);
		boolean hasMoved = false;
		Set<BoardCell> targets= board.getTargets();
		System.out.println(targets);
		//System.out.println(targets);

		ArrayList<BoardCell> cellsToMove = new ArrayList<BoardCell>();
		
		cellsToMove.addAll(targets);
		System.out.println(cellsToMove);
		for(BoardCell cell : cellsToMove) {
			if(!roomSeen.contains(board.getRoom(cell)) && cell.getChar() != 'W') {
				System.out.println(board.getRoom(cell));
				int rowToMoveTo = cell.getRow();
				int colToMoveTo = cell.getCol();
				int cellWidth = board.getWidth()/board.getNumColumns();
				int cellHeight = board.getHeight()/board.getNumRows();
				int xToMoveTo = rowToMoveTo * cellWidth;
				int yToMoveTo = colToMoveTo * cellHeight;
				int changeInX = xToMoveTo - this.getX();
				int changeInY = yToMoveTo - this.getY();
				int numOfMoves = 10;
				int chngXOverTime = changeInX/numOfMoves;
				int chngYOverTime = changeInY/numOfMoves;
				for(int i=0; i<numOfMoves; i++) {
					Point newLocation = new Point();
					newLocation.x = this.getX()+chngXOverTime;
					newLocation.y = this.getY() + chngYOverTime;
					this.setLocation(newLocation);
					this.repaint();
				}			
				row = cell.getRow();
				col = cell.getCol();
				roomSeen.add(board.getRoom(cell));
				canSuggest = true;
				hasMoved = true;
				ArrayList<Card> handTemp = new ArrayList<Card>();
				handTemp.addAll(hand);
				addManySeenCards(handTemp);

			}
		}
		if(!hasMoved) {
			int moveChoice = Math.abs(rn.nextInt()%cellsToMove.size());
			BoardCell cell = cellsToMove.get(moveChoice);
			System.out.println(moveChoice);
			int rowToMoveTo = cell.getRow();
			int colToMoveTo = cell.getCol();
			int cellWidth = board.getWidth()/board.getNumColumns();
			int cellHeight = board.getHeight()/board.getNumRows();
			int xToMoveTo = rowToMoveTo * cellWidth;
			int yToMoveTo = colToMoveTo * cellHeight;
			int changeInX = xToMoveTo - this.getX();
			int changeInY = yToMoveTo - this.getY();
			int numOfMoves = 10;
			int chngXOverTime = changeInX/numOfMoves;
			int chngYOverTime = changeInY/numOfMoves;
			for(int i=0; i<numOfMoves; i++) {
				Point newLocation = new Point();
				newLocation.x = this.getX()+chngXOverTime;
				newLocation.y = this.getY() + chngYOverTime;
				this.setLocation(newLocation);
				this.repaint();
			}
			row = cell.getRow();
			col = cell.getCol();
			System.out.println(roomSeen.contains(board.getRoom(cell)));
			System.out.println(cell.getChar());
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			if(cell.getChar() != 'W') {
				
				System.out.println("suggesting");
				System.out.println(cell);
				canSuggest = true;
			}	
			else {
				System.out.println("Pain");
				canSuggest = false;
			}		
		}
	}

	/*
	 * this needs 3 things, make sure everything works. then ensure that if the player gets a 
	 * suggestion with no return they make an accusation next turn from the same location
	 * ensure suggestion drags other to your location
	 * baddabing
	 * baddaboom
	 * this will be done by:
	 */
	public Card createSuggestion() {
		System.out.println();
		System.out.println();
		System.out.println("SUGG");
		System.out.println();
		System.out.println();
		ArrayList<Card> cardsToSuggest = new ArrayList<Card>();
		ArrayList<Card> weaponsNotSeen = new ArrayList<Card>();
		ArrayList<Card> peopleNotSeen = new ArrayList<Card>();
		ArrayList<Card> deck = board.getDeck();
		for(Card card : deck) {
			if(!known.contains(card) && !hand.contains(card)) {
				if(card.type == cardType.WEAPON) {
					weaponsNotSeen.add(card);
				}
				if(card.type == cardType.PLAYER) {
					peopleNotSeen.add(card);
				}
				
				
			}
		}
		for(Card card : deck) {
			
			if(card.type == cardType.LOCATION) {
				if(card.cardName == board.getRoom(board.getCell(row, col)).getName()) {
					cardsToSuggest.add(card);
				}
			}
		}
		int weaponToInqire = 0;
		int personToInquire = 0;
		if(weaponsNotSeen.size() == 0) {
			boolean hasAdded = false;
			for(Card card : deck) {
				if(card.type == cardType.WEAPON && !hasAdded) {
					cardsToSuggest.add(card);
					hasAdded = true;
				}
			}
		}
		else {
			weaponToInqire = Math.abs(rn.nextInt()%weaponsNotSeen.size());
			cardsToSuggest.add(weaponsNotSeen.get(weaponToInqire));
		}
		boolean chosen = false;
		if(peopleNotSeen.size() == 0) {
			System.out.println("new boi");
			for(Card card : deck) {
				if(card.type == cardType.PLAYER && chosen == false) {
					cardsToSuggest.add(card);
					for (player player : board.getPlayers()) {
						if(player.getName().equals(card.cardName)) {
							player.row = row;
							player.col = col;						}
					}
					chosen = true;
				}
			}
		}
		else {
			System.out.println("old boi");
			personToInquire = Math.abs(rn.nextInt()%peopleNotSeen.size());
			cardsToSuggest.add(peopleNotSeen.get(personToInquire));
			for(Card card : deck) {
				if(card.type == cardType.PLAYER) {
					for (player player : board.getPlayers()) {
						if(player.getName().equals(peopleNotSeen.get(personToInquire).cardName)) {
							if(player.isComp()) {
								if(!accuse) {
									player.row = row;
									player.col = col;
								}
							}
							else {
							player.row = row;
							player.col = col;
						}}
					}
			
		}

			}}
		lastSuggest = cardsToSuggest;
		
		Card result = board.checkSuggestion(cardsToSuggest, this);
		return result;
	}

	@Override
	public void roomSeenAdd(char c) {
		roomSeen.add(board.getRoom(c));
	}

	@Override
	public void setLocation(int Row, int Col) {
		row = Row;
		col = Col;
		
		
	}

	public void addManySeenCards(ArrayList<Card> fullDeck) {
		known.addAll(fullDeck);
		
	}

	public boolean isAccuse() {
		return accuse;
	}

	public void setAccuse(boolean accuse) {
		this.accuse = accuse;
	}

	public ArrayList<Card> getLastSuggest() {
		return lastSuggest;
	}

	public boolean accuse() {
		if(roomSeen.contains(board.getRoom(board.getCell(row, col))) && board.getRoom(board.getCell(row, col))!=null) {
			boolean isTrue = board.checkAccusation(lastSuggest);
			if(isTrue) {
				JOptionPane.showMessageDialog(null,"Computer player "+name+ " won!");
				return true;
			}
			else {
				JOptionPane.showMessageDialog(null,"Computer player "+name+ " Lost!");
				return false;
			
		}}
		else {
			accuse = false;
			return false;
		}
		
	}

}
