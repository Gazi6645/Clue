package clueGame;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

//TODO add comments to make the code legible
public class Board extends JPanel {
	private int numColumns, numRows;
	private BoardCell[][] grid;
	private String layoutConfigFile, setupConfigFile;
	public Map<Character,Room> rooms;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private Set<HumanPlayer> HumanPlayers;
	private Set<ComputerPlayer> ComputerPlayers;
	//change to array list
	private ArrayList<Card> deck;
	private ArrayList<Card> solution;
	private ClueGame currGame;

    /*
    * variable and methods used for singleton pattern
    */
    private static Board theInstance = new Board();
    // constructor is private to ensure only one can be created
    Board() {
           super() ;
    }
    // this method returns the only Board
    public static Board getInstance() {
           return theInstance;
    }
    /*
     * initialize the board (since we are using singleton pattern)
     */
    public void initialize() throws BadConfigFormatException
    {
    	resetSets();
    	rooms = new HashMap<Character,Room>();
    	HumanPlayers = new HashSet<HumanPlayer>();
    	ComputerPlayers = new HashSet<ComputerPlayer>();
    	deck = new ArrayList<Card>();
    	solution = new ArrayList<Card>();

    	numRows = 0;
    	FileReader readShell;
    	BufferedReader reader;
    	try {
    		//readShell opens the layout config file
			 readShell = new FileReader(layoutConfigFile);
			//buffered reader used to parse full lines of the text, this allows us to find the height of the board
			 reader = new BufferedReader(readShell);
    	

		try {
			//set the line of the file that is being read
			String currLine = reader.readLine();
			//split string by (,)s
			String[] numChars = currLine.split(",",0);
			//since every line has the same number of characters we can just check once
			numColumns = numChars.length;

			//here we start counting rows
			while(currLine != null) {
				//ensure each row has same number of columns
				numChars = currLine.split(",",0);
				if(numColumns != numChars.length) {
					throw new BadConfigFormatException("rows have differing lengths check to ensure each cell is filled with a character ie ensure no cells look like X,X,,X,X");
				}
				//if there are still lines increase the number of rows
				numRows++;
				//go to next line
				currLine = reader.readLine();
			}
				
				
		//handle file errors
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			//close readers
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			readShell.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
			//repeated to reset couter to top of file
    		//readShell opens the layout config file

		try {
			//attempt to read the setup config file, if this dosent work something went bad
			readShell = new FileReader(setupConfigFile);
			//buffered reader used to parse full lines of the text, this allows us to find the height of the board
			reader = new BufferedReader(readShell);	
			try {
				//get the first string from the config file
				String roomSetUpString = reader.readLine();
				//read next string then loop
					while(roomSetUpString != null) {
						//begin to loop through the config file
						
						
						//split the line read by commas
						String [] roomParams = roomSetUpString.split(",",0);
						//do not attempt to configure a comment
						//and ensure the first line contains room or space to ensure that the config file was not put in wrong.
						if((roomParams[0].contains("Room") || roomParams[0].contains("Space"))&&!roomParams[0].contains("//")) {
							//CLEAN UP WHITESPACE FROM THE INPUTS index 1 and 2
							if(roomParams[1].charAt(0)== ' ') {
								roomParams[1]=roomParams[1].substring(1);
							}
							if(roomParams[2].charAt(0)== ' ') {
								roomParams[2]=roomParams[2].substring(1);
							}
							
							
							//when reading will find rooms thus create an entry in the map with the character on the second string
							// as it starts with a " "
							//then create room with the first string as the title and the character as its inital
							rooms.put(roomParams[2].charAt(0), new Room(roomParams[1],roomParams[2].charAt(0)));
							if(roomParams[0].contains("Room")) {
								deck.add(rooms.get(roomParams[2].charAt(0)).createCard());
							}
						}
						//set up players
						if((roomParams[0].contains("Player")&&!roomParams[0].contains("//"))) {
							if(roomParams[1].charAt(0)== ' ') {
								roomParams[1]=roomParams[1].substring(1);
							}
							if(roomParams[2].charAt(0)== ' ') {
								roomParams[2]=roomParams[2].substring(1);
							}
							if(roomParams[3].charAt(0) == ' '){
								roomParams[3]=roomParams[3].substring(1);
							}
							if(roomParams[4].charAt(0) == ' '){
								roomParams[4]=roomParams[4].substring(1);
							}
							if(roomParams[5].charAt(0) == ' '){
								roomParams[5]=roomParams[5].substring(1);
							}
							if(roomParams[1].equals("Human")) {
								System.out.println("NOTICE ME");
								HumanPlayer tempPlay = new HumanPlayer(roomParams[2],roomParams[3],Integer.parseInt(roomParams[4]),Integer.parseInt(roomParams[5]), this); 
								HumanPlayers.add(tempPlay);
								deck.add(tempPlay.createCard());
							}
							else {
								ComputerPlayer tempPlay = new ComputerPlayer(roomParams[2],roomParams[3],Integer.parseInt(roomParams[4]),Integer.parseInt(roomParams[5]), this); 
								ComputerPlayers.add(tempPlay);
								deck.add(tempPlay.createCard());
							}
						}
						//set up weapons
						if((roomParams[0].contains("Card")&&!roomParams[0].contains("//"))) {
							if(roomParams[1].charAt(0)== ' ') {
								roomParams[1]=roomParams[1].substring(1);
							}
							if(roomParams[2].charAt(0)== ' ') {
								roomParams[2]=roomParams[2].substring(1);
							}
							deck.add(new Card(roomParams[2], cardType.WEAPON));
						}
						
						//progress the file reader
						roomSetUpString = reader.readLine();
				}
				
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			//close the readers
			try {
				reader.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				readShell.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (IOException e) {
			
		}
	
			//set up the grid to have the dimensions we need
			grid = new BoardCell[numRows][numColumns];
			try {
			//open the files
			readShell = new FileReader(layoutConfigFile);
			//buffered reader used to parse full lines of the text, this allows us to find the height of the board
			reader = new BufferedReader(readShell);	
				String currLine = reader.readLine();
				// each room has a character, the room hashmap can turn that into a room object
				//the room has room things
				for(int row=0; row<numRows;row++) {
					//read the first line then split according to the ,s
					String[] lineContents = currLine.split(",",0);
					for(int column=0; column<numColumns; column++) {
						//create a board cell with the coordinates and a character and set it to grid (coordinates)
						grid[row][column] = new BoardCell(row,column,lineContents[column].charAt(0));
						if(!rooms.containsKey(lineContents[column].charAt(0))) {
							//if there is not a room for the character throw an exception
							throw new BadConfigFormatException(lineContents[column]);
						}
						//set a working room variable to use later for things like centers and labels
						Room workingRoom = rooms.get(lineContents[column].charAt(0));
						
						//read the character at index 1 of each string and set the cell to a door/center/label based on the result
						//if there is no character at index 1 just dont do anything
						//im pretty sure there isnt a simple way to check if there is an index 1 but if there is we can replace the
						//try catch with an if.
						try {
							char CharOfSpecial = lineContents[column].charAt(1);
							if(CharOfSpecial == '#') {
								workingRoom.setlabelCell(grid[row][column]);
								grid[row][column].setLabel(true);
							}
							else if(CharOfSpecial == '*') {
								workingRoom.setCenterCell(grid[row][column]);
								grid[row][column].setRoomCenter(true);
								
							}
							else if(CharOfSpecial == '^') {
								grid[row][column].setDoorway(true);
								grid[row][column].setDoordir(DoorDirection.UP);
							}
							else if(CharOfSpecial == 'v' || CharOfSpecial == 'V') {
								grid[row][column].setDoorway(true);
								grid[row][column].setDoordir(DoorDirection.DOWN);
							}
							else if(CharOfSpecial == '<') {
								grid[row][column].setDoorway(true);
								grid[row][column].setDoordir(DoorDirection.LEFT);
							}
							else if(CharOfSpecial == '>') {
								grid[row][column].setDoorway(true);
								grid[row][column].setDoordir(DoorDirection.RIGHT);
							}
							else {
								grid[row][column].setSecretPassage(CharOfSpecial);
							}
						}
						catch (StringIndexOutOfBoundsException e) {
							
						}
						
					}
					//progress the file parser
					currLine=reader.readLine();
				}}
			
			 catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}catch (IOException e) {
	       		throw new BadConfigFormatException("files not found ensure that you told the program what files to look for ie check for typs and the such. Also ensure files are in data folder");
	    	}
    	
    	
    	//TODO make this a simple function to resue all this code
    	shuffleDeck();
    	dealCards();
    	for(int row=0; row<numRows; row++) {
    		for(int column=0; column<numColumns; column++) {
    			//perform secret passage aquisition
    			if(grid[row][column].getSecretPassage() != ' ') {
    				rooms.get(grid[row][column].getChar()).getCenterCell().addAdj(rooms.get(grid[row][column].getSecretPassage()).getCenterCell());
    			}
    			
    			
    			else if(grid[row][column].getChar() == 'W') {
    				if ((row-1) >= 0) {
    					setAdjs(row, column, -1,0,DoorDirection.UP);
    					
    				}
    				//make this a simple command such that we can resuse the code
    				if ((column-1)>=0) {
    					setAdjs(row, column, 0,-1,DoorDirection.LEFT);	
    				}
    				// row+i col+j
    				if ((row+1) < numRows) {
    					setAdjs(row, column, 1,0,DoorDirection.DOWN);
    					
    				}
    				if ((column+1)< numColumns) {
    					setAdjs(row, column, 0,1,DoorDirection.RIGHT);		
    				}
    				}
    			}
    		}
		addMouseListener(new boardListener());
    		
    	}
    
			
			
			



	public void setConfigFiles(String layoutConfig, String setupConfig) {
		//boy I sure hope the order is correct in the inpus.
		
		//find data folder will guide the string to the data folder
		String findDataFolder = "data/";

		//by adding the input to find data folder we can find the correct file
		layoutConfigFile = findDataFolder + layoutConfig;
		setupConfigFile = findDataFolder + setupConfig;

		
	}

	public void loadSetupConfig() throws BadConfigFormatException {
		// TODO Auto-generated method stub
		//a minor edit of the set up allows us to simply set up the board when calling these
		try {
			this.initialize();
		} catch (BadConfigFormatException e) {
			// TODO Auto-generated catch block
			throw e;
		}
		
	}

	public void loadLayoutConfig() throws BadConfigFormatException {
		// TODO Auto-generated method stub
		//see above
		try {
			this.initialize();
		} catch (BadConfigFormatException e) {
			// TODO Auto-generated catch block
			throw e;
		}
		
	}

	//getters go brrrrrrrr
	public Room getRoom(char c) {
				
		return rooms.get(c);
	}
	public int getNumRows() {
		return this.numRows;
	}
	public int getNumColumns() {
		return this.numColumns;
	}
	public BoardCell getCell(int row, int column) {
		// TODO Auto-generated method stub
		BoardCell m= grid[row][column];
		return m;
	}
	public Room getRoom(BoardCell cell) {
		// TODO Auto-generated method stub
		return rooms.get(cell.getChar());
	}
	public Set<BoardCell> getAdjList(int row, int column) {
		// TODO Auto-generated method stub
		return grid[row][column].getAdjList();
		}
	public Set<BoardCell> getTargets() {
		// TODO Auto-generated method stub
		
		return targets;
	}
	private void setAdjs(int row, int column, int rowMod, int colMod, DoorDirection doorDir) {
		
		char charOfCell = grid[row+rowMod][column+colMod].getChar();
		if(charOfCell != 'X') {
			if(charOfCell != 'W' && grid[row][column].isDoorway() && grid[row][column].getDoorDirection() == doorDir) {
				grid[row][column].addAdj(rooms.get(charOfCell).getCenterCell());
				//set room center to add this cell to its adj list.
				rooms.get(charOfCell).getCenterCell().addAdj(grid[row][column]);
			}
			else if(charOfCell == 'W'){
				grid[row][column].addAdj(grid[row+rowMod][column+colMod]);
			}
		}
			
		
	}
	public void calcTargets( BoardCell startCell, int pathlength) {
    	resetSets(); //an abstract method that reset's all the List
		visited.add(startCell);
		findAllTargets(startCell, pathlength);
	}
	private void resetSets() {  //an abstract method that reset's all the List
		targets = new HashSet<BoardCell>();
    	visited = new HashSet<BoardCell>();

	}


	private void findAllTargets(BoardCell startCell, int pathlength) {


		// TODO Auto-generated method stub
		Iterator<BoardCell> adjCells = startCell.getAdjList().iterator();
		while(adjCells.hasNext()) {
			
			visited.add(startCell);

			BoardCell nextCell = adjCells.next();
			/*
			 * nextCell != room
			 * nextCell!= occupied
			 */

			if(visited.contains(nextCell)) {

				continue;
			}


			else if(nextCell.isRoomCenter()) {

				targets.add(nextCell);

			}
			else if (nextCell.getOccupied()) {
				continue;
			}
			else if(pathlength == 1){

				targets.add(nextCell);
			}
			else {
				
				findAllTargets(nextCell, pathlength-1);
			}



		}
		visited.remove(startCell);


}
	public int getNumPlayer() {
		// TODO Auto-generated method stub
		return HumanPlayers.size()+ComputerPlayers.size();
	}
	public int getNumHumans() {
		// TODO Auto-generated method stub
		return HumanPlayers.size();
	}
	public int getNumComputers() {
		// TODO Auto-generated method stub
		return ComputerPlayers.size();
	}
	public int getDeckSize() {
		// TODO Auto-generated method stub
		return deck.size();
	}
	private void shuffleDeck() {
		// TODO Auto-generated method stub
		Iterator<Card> cardsToRandom = deck.iterator();
		Random generator = new Random(new Date().getTime());
		ArrayList<Card> set1 = new ArrayList<Card>();
		ArrayList<Card> set2 = new ArrayList<Card>();
		ArrayList<Card> set3 = new ArrayList<Card>();
		
		
		while(cardsToRandom.hasNext()) {
			// this function will shuffle the deck of cards into a random order
			int setToAddTo = Math.abs(generator.nextInt()%3);
			//set up the random number generator
			Card cardToAdd = cardsToRandom.next();
			//get iterator
			
			//based on random number do something
			if(setToAddTo == 0) {
				set1.add(cardToAdd);
			}
			if(setToAddTo == 1) {
				set2.add(cardToAdd);
			}
			if(setToAddTo == 2) {
				set3.add(cardToAdd);
			}
		}
		//clear deck
		deck = new ArrayList<Card>();
		//combine subdecks into deck.
		deck.addAll(set1);
		deck.addAll(set2);
		deck.addAll(set3);
		
	}
	private void dealCards() {
		HashSet<player> players = new HashSet<player>();

		players.addAll(HumanPlayers);
		players.addAll(ComputerPlayers);
		Iterator<player> nxtPlayer = players.iterator();
		boolean Weapon = false;
		boolean Room = false;
		boolean Player = false;
		Collections.shuffle(deck);
		Iterator<Card> cardToDeal = deck.iterator();
		int i = 1;
		while(cardToDeal.hasNext()) {
			Card wrkCard = cardToDeal.next();
			if(!Weapon && wrkCard.getType() == cardType.WEAPON) {
				solution.add(wrkCard);
				Weapon = true;
			}
			else if(!Room && wrkCard.getType() == cardType.LOCATION) {
				solution.add(wrkCard);
				Room = true;
			}
			else if(!Player && wrkCard.getType() == cardType.PLAYER) {
				solution.add(wrkCard);
				Player = true;
			}
			else {
				
				if(nxtPlayer.hasNext()) {
					i++;
					player play = nxtPlayer.next();
					play.updteHand(wrkCard);
				}
				else {
					nxtPlayer = players.iterator();
					if(nxtPlayer.hasNext()) {
					player play = nxtPlayer.next();
					play.updteHand(wrkCard);
					}
				}
			}
		}
		Collections.shuffle(deck);

}
	public ArrayList<Card> getSolution() {
		return solution;
	}
	public boolean checkAccusation(ArrayList<Card> possibleSol) {
		System.out.println(possibleSol);

		boolean isTrue = true;
		if(possibleSol.size()==3) {
			for(Card card : solution) {
				System.out.println(card);
				System.out.println(possibleSol);
				if(possibleSol.contains(card)) {
				}
				else {
					isTrue = false;
				}
			}
		}
		else {
			System.out.println(possibleSol);
			System.out.println("how did you get something other than 3 cards into your accusation ya weirdo?");
			return false;
		}
		return isTrue;
	}
	public Card checkSuggestion(ArrayList<Card> suggestion, player Player) {
		// TODO Auto-generated method stub
		HashSet<player> players = new HashSet<player>();
		ArrayList<Card> possibleSols = new ArrayList<Card>();
		Random rn = new Random();
		players.addAll(ComputerPlayers);
		players.addAll(HumanPlayers);
		players.remove(Player);
		for(player play : players) {
			Iterator<Card> cards= play.getHand().iterator();
			while(cards.hasNext()) {
				//hold the next card for rest of actions
				Card temp = cards.next();
				if(suggestion.contains(temp)) {
					possibleSols.add(temp);
				}
			}
			
			
		}
		if(possibleSols.size() != 0) {
			int index = rn.nextInt()%possibleSols.size();
			return possibleSols.get(Math.abs(index));
		}
		return null;
	}
	public ArrayList<player> getPlayers() {
		// TODO Auto-generated method stub
		ArrayList<player> players = new ArrayList<player>();
		players.addAll(ComputerPlayers);
		players.addAll(HumanPlayers);
		return players;
	}
	public HumanPlayer getHumanPlayer() {
		return HumanPlayers.iterator().next();
	}
	public ArrayList<Card> getDeck() {
		return deck;
	}
	public Set<ComputerPlayer> getComputerPlayers(){
		return ComputerPlayers;
	}

	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int cellWidth = getWidth()/numColumns;
		int cellHeight = getHeight()/numRows;
		for(int row=0; row<numRows; row++ ) {
			for(int col=0; col<numColumns; col++) {
				int xOffset = cellWidth * col;
				int yOffset = cellHeight * row;
				grid[row][col].Draw(g,cellWidth,cellHeight,xOffset,yOffset,rooms.containsKey(grid[row][col].getChar()));
			}
		}
		Iterator<Character> mapChars = rooms.keySet().iterator();
		while(mapChars.hasNext()) {
			Character rm = mapChars.next();
			Room room = rooms.get(rm);
			room.Draw(g,cellWidth,cellHeight);
		}
		ArrayList<player> players = new ArrayList<player>();
		players.addAll(ComputerPlayers);
		players.addAll(HumanPlayers);
		for(BoardCell cell : targets) {
			int xOffset = cellWidth * cell.getCol();
			int yOffset = cellHeight * cell.getRow();
			cell.DrawTarget(g, cellWidth, cellHeight, xOffset, yOffset);
		}
		for(player Player : players) {
			Player.Draw(g,cellWidth,cellHeight);
		}

		
		
	
		
	}
	public void paintTargetCells(Graphics g) {
		super.paintComponent(g);
	}
	public void setClueGame(ClueGame game) {
		currGame = game;
		
	}
	private class boardListener implements MouseListener{
		int cellWidth;  
		int cellHeight;  
		@Override
		public void mouseClicked(MouseEvent e) {
			cellWidth = getWidth()/numColumns;
			cellHeight = getHeight()/numRows;
			if(targets.contains(grid[e.getY()/cellHeight][e.getX()/cellWidth])) {
				currGame.move(grid[e.getY()/cellHeight][e.getX()/cellWidth]);
				if(((getCell(currGame.currPlay.row,currGame.currPlay.col).getChar()))!= 'W')
				{
					currGame.makeSuggestion();
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "Bud, you gotta select a valid target.");
			}
			
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
		
	}
}