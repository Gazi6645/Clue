package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JPanel;

import Expirement.TestBoardCell;

public class BoardCell extends JPanel {
	private int row, col;
	private char initial, secretPassage;
	private DoorDirection doordir;
	private boolean roomLabel,roomCenter,Doorway,isOccupied;
	private Set<BoardCell> adjList;

	public BoardCell(int row, int col, char init) {
		initial = init;
		this.row = row;
		this.col = col;
		Doorway = false;
		adjList = new HashSet<BoardCell>();
		secretPassage = ' ';
		isOccupied = false;
		
		
	}
	public boolean isDoorway() {
		// TODO Auto-generated method stub
		return Doorway;
	}
	public Object getDoorDirection() {
		// TODO Auto-generated method stub
		return doordir;
	}
	public char getChar() {
		// TODO Auto-generated method stub
		return initial;
	}
	public boolean isLabel() {
		// TODO Auto-generated method stub
		return roomLabel;
	}
	public boolean isRoomCenter() {
		// TODO Auto-generated method stub
		return roomCenter;
	}
	public char getSecretPassage() {
		// TODO Auto-generated method stub
		return secretPassage;
	}
	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", col=" + col + "]";
	}
	public void setLabel(boolean b) {
		// TODO Auto-generated method stub
		roomLabel=b;
		

	}
	public void setSecretPassage(char secretPassage) {
		this.secretPassage = secretPassage;
	}
	public void setDoordir(DoorDirection doordir) {
		this.doordir = doordir;
	}
	public void setRoomLabel(boolean roomLabel) {
		this.roomLabel = roomLabel;
	}
	public void setRoomCenter(boolean roomCenter) {
		this.roomCenter = roomCenter;
	}
	public void setDoorway(boolean doorway) {
		Doorway = doorway;
	}
	public void setOccupied(boolean b) {
		isOccupied = b;
		// TODO Auto-generated method stub
		
	}
	public boolean getOccupied() {
		return isOccupied;
		// TODO Auto-generated method stub
		
	}
	public void addAdj(BoardCell boardCell) {
		adjList.add(boardCell);
		
	}
	public Set<BoardCell> getAdjList() {
		// TODO Auto-generated method stub
		return adjList;
	}
	public int getRow() {
		// TODO Auto-generated method stub
		return row;
	}
	public int getCol() {
		// TODO Auto-generated method stub
		return col;
	}

	public void Draw(Graphics g, int cellWidth, int cellHeight, int xOffset, int yOffset, boolean isRoom) {
		// TODO Auto-generated method stub
		if(initial == 'X') {
			g.setColor(Color.BLACK);
			g.fillRect(xOffset, yOffset, cellWidth, cellHeight);
		}
		
		else if(initial == 'W') {
			g.setColor(Color.BLACK);
			g.drawRect(xOffset, yOffset, cellWidth, cellHeight);
			g.setColor(Color.BLUE);
			g.fillRect(xOffset+1, yOffset+1, cellWidth-1, cellHeight-1);
		}

		else {
			g.setColor(Color.BLACK);
			g.drawRect(xOffset, yOffset, cellWidth, cellHeight);
			g.setColor(Color.RED);
			g.fillRect(xOffset+1, yOffset+1, cellWidth-1, cellHeight-1);
		}
		if(Doorway) {
			g.setColor(Color.cyan);
			if(doordir == DoorDirection.DOWN) {
				g.drawLine(xOffset, yOffset+cellHeight-1, xOffset+cellWidth,yOffset+cellHeight-1);
			}
			if(doordir == DoorDirection.UP) {
				g.drawLine(xOffset, yOffset, xOffset+cellWidth,yOffset);
			}
			if(doordir == DoorDirection.LEFT) {
				g.drawLine(xOffset, yOffset, xOffset,yOffset+cellHeight);
			}
			if(doordir == DoorDirection.RIGHT) {
				g.drawLine(xOffset+cellWidth-1, yOffset, xOffset+cellWidth-1,yOffset+cellHeight);
			}
		}
		
		
	}
	public void Draw(Graphics g, int cellWidth, int cellHeight, int xOffset, int yOffset, String name) {
		// TODO Auto-generated method stub
		g.drawString(name, xOffset+cellWidth/2, yOffset+cellHeight/2);
		
	}
	public void DrawTarget(Graphics g, int cellWidth, int cellHeight, int xOffset, int yOffset) {
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		g.fillRect(xOffset, yOffset, cellWidth, cellHeight);
		
	}

	

	}
	
