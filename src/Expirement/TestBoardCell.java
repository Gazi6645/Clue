package Expirement;
import java.util.HashSet;
import java.util.Set;

public class TestBoardCell {

	private int row, col;
	private Boolean isRoom, isOccupied ;
	Set<TestBoardCell> adjList;
	/**
	 * TestBoardCell:Constructor
	 */
	public TestBoardCell(int row, int column) {
		this.row=row;
		this.col=column;
		isRoom = false;
		isOccupied = false;
		adjList= new HashSet<TestBoardCell>();
	}

	/**
	 * addAdjacency:A setter to add a cell to this cells adjacency list
	 */

	void addAdj( TestBoardCell cell ){
		adjList.add(cell);
	}

	/**
	 * getAdjList:returns the adjacency list for the cell
	 */

	public Set<TestBoardCell> getAdjList() {
		// TODO Auto-generated method stub
		return adjList;
	}
	/**
	 * setRoom and isRoom:A setter and a getter for indicating a cell is part of a room
	 */
	public void setRoom(boolean room) {
		isRoom = room;
	}

	public boolean isRoom() {
		return isRoom;
	}

	/**
	 * setOccupied and getOccupied:A setter and perhaps a getter for indicating a cell is occupied by another player
	 */
	public void setOccupied(boolean status) {
		isOccupied = status;
	}

	boolean getOccupied() {
		return isOccupied;
	}

	@Override
	public String toString() {
		return "TestBoardCell [row=" + row + ", col=" + col + "]";
	}



}
