package Expirement;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class TestBoard {
	private Set<TestBoardCell> targets ;
	private Set<TestBoardCell> visited;
	private TestBoardCell [][] grid;
	final static int COLS = 4;
	final static int ROWS = 4;

	/**
	 * TestBoard:Constructor
	 */

	public TestBoard(){
		targets= new HashSet<TestBoardCell>();
		visited= new HashSet<TestBoardCell>();
		grid= new TestBoardCell[COLS][ROWS];
		for(int i=0;i<ROWS;i++) {
			for(int j=0;j<COLS;j++) {
				grid[i][j]= new TestBoardCell(i,j);
			}
		}


		for(int i=0;i<ROWS;i++) {
			for(int j=0;j<COLS;j++) {
				if ((i-1) >= 0) {
					grid[i][j].addAdj(grid[i-1][j]);
				}
				if ((j-1)>=0) {
					grid[i][j].addAdj(grid[i][j-1]);
				}

				if ((i+1) < ROWS) {
					grid[i][j].addAdj(grid[i+1][j]);
				}
				if ((j+1)< COLS) {
					grid[i][j].addAdj(grid[i][j+1]);
				}
			}
		}

	}


	/**
	 * calcTargets:calculates legal targets for a move from startCell of length pathlength.
	 * @return 
	 */

	public void calcTargets( TestBoardCell startCell, int pathlength) {
		visited.add(startCell);
		findAllTargets(startCell, pathlength);

	}


	private void findAllTargets(TestBoardCell startCell, int pathlength) {
		// TODO Auto-generated method stub
		Iterator<TestBoardCell> adjCells = startCell.getAdjList().iterator();
		while(adjCells.hasNext()) {
			TestBoardCell nextCell = adjCells.next();
			/*
			 * nextCell != room
			 * nextCell!= occupied
			 */
			if(visited.contains(nextCell)) {
				continue;
			}

			if(nextCell.isRoom()) {
				targets.add(nextCell);
			}
			else if (nextCell.getOccupied()) {
				continue;
			}
			else if(pathlength == 1){
				targets.add(nextCell);
				System.out.println(nextCell);
			}
			else {
				findAllTargets(nextCell, pathlength-1);
			}
			visited.add(startCell);
		}

	}


	/**
	 * getCell: returns the cell from the board at row, col.
	 */
	public TestBoardCell getCell(int row, int column) {
		// TODO Auto-generated method stub
		return grid[row][column];
	}
	/**
	 * getTargets: gets the targets last created by calcTargets()
	 */

	public Set<TestBoardCell> getTargets() {
		// TODO Auto-generated method stub
		return targets;
	}
}
