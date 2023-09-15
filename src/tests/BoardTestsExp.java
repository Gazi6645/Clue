package tests;
import java.util.Set;

import org.junit.*;
import org.junit.jupiter.api.Test;

import Expirement.*;

public class BoardTestsExp {

	/**
	 * testAdjacancy00(): test's if the point's (0,1) and (1,0) is in 
	 * Adjacency of point(0,0)
	 */
	@Test
	public void testAdjacency00() {
		TestBoard board = new TestBoard();
		TestBoardCell cell = board.getCell(0,0);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(0, 1)));
		Assert.assertTrue(testList.contains(board.getCell(1, 0)));
		Assert.assertEquals(2, testList.size());
	}

	/**
	 * testAdjacency11(): test's if the point's (0,1) and (1,0) is in 
	 * Adjacency of point(1,1)
	 */
	@Test
	public void testAdjacency11() {
		TestBoard board = new TestBoard();
		TestBoardCell cell = board.getCell(1,1);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(0, 1)));
		Assert.assertTrue(testList.contains(board.getCell(1, 0)));
		Assert.assertTrue(testList.contains(board.getCell(2, 1)));
		Assert.assertTrue(testList.contains(board.getCell(1, 2)));
		Assert.assertEquals(4, testList.size());
	}

	/**
	 * testTargets(): test targets with several rolls with starting location(0,0)
	 */
	@Test
	public void testTargetsNormal() {
		TestBoard board = new TestBoard();
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell,4);
		Set<TestBoardCell> testList = board.getTargets();
		Assert.assertTrue(testList.contains(board.getCell(0, 2)));
		Assert.assertTrue(testList.contains(board.getCell(1, 1)));
		Assert.assertTrue(testList.contains(board.getCell(1, 3)));
		Assert.assertTrue(testList.contains(board.getCell(2, 0)));
		Assert.assertTrue(testList.contains(board.getCell(2, 2)));
		Assert.assertTrue(testList.contains(board.getCell(3, 1)));
		Assert.assertEquals(6, testList.size());
	}
	/**
	 * testTargetsRoom(): test targets with several rolls with starting location(3,3)
	 */
	@Test
	public void testTargetsRoom() {
		TestBoard board = new TestBoard();
		board.getCell(3, 2).setRoom(true);
		board.getCell(2, 3).setRoom(true);
		TestBoardCell cell=board.getCell(3, 3);
		board.calcTargets(cell,5);
		Set<TestBoardCell> testList = board.getTargets();
		Assert.assertTrue(testList.contains(board.getCell(3, 2)));
		Assert.assertTrue(testList.contains(board.getCell(2, 3)));
		Assert.assertEquals(2, testList.size());
	}

	/**
	 * testTargetsOccupied(): test targets with several rolls with starting location(0,3)
	 */
	@Test
	public void testTargetsOccupied() {
		TestBoard board = new TestBoard();
		board.getCell(0,1).setOccupied(true);
		board.getCell(1, 3).setOccupied(true);
		TestBoardCell cell=board.getCell(0, 3);
		board.calcTargets(cell, 2);
		Set<TestBoardCell> testList = board.getTargets();
		Assert.assertTrue(testList.contains(board.getCell(1, 2)));
		Assert.assertEquals(1, testList.size());
	}
	/**
	 * testTargetsMixed(): more test target with starting location (0,0)
	 */
	@Test
	public void testTargetsMixed() {
		TestBoard board = new TestBoard();

		board.getCell(0, 2).setRoom(true);
		board.getCell(1, 0).setOccupied(true); 
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell,3);
		Set<TestBoardCell> testList = board.getTargets();
		//make sure intended targets are present
		Assert.assertTrue(testList.contains(board.getCell(0, 2)));
		Assert.assertTrue(testList.contains(board.getCell(1,2)));
		Assert.assertTrue(testList.contains(board.getCell(2,1)));
 	}	

}
