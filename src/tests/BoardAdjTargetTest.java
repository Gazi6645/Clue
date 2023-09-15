package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTest {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;

	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		try {
			board.initialize();
		} catch (BadConfigFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Ensure that player does not move around within room
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesRooms()
	{
		// we want to test a couple of different rooms.
		// First, the study that only has a single door but a secret room
		Set<BoardCell> testList = board.getAdjList(16, 0);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(16, 5)));
		assertTrue(testList.contains(board.getCell(14, 2)));


		// one more room, the kitchen
		testList = board.getAdjList(4, 14);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(5, 12)));
		assertTrue(testList.contains(board.getCell(10, 14)));
		assertTrue(testList.contains(board.getCell(15, 17)));

	}


	// Ensure door locations include their rooms and also additional walkways
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacencyDoor()
	{
		Set<BoardCell> testList = board.getAdjList(6, 5);
		assertEquals(3, testList.size());

		assertTrue(testList.contains(board.getCell(3, 4)));
		assertTrue(testList.contains(board.getCell(6, 6)));
		assertTrue(testList.contains(board.getCell(7, 5)));
		
		
	}

	// Test a variety of walkway scenarios
	// These tests are Dark Orange on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on bottom edge of board, just one walkway piece
		Set<BoardCell> testList = board.getAdjList(10, 12);
		assertTrue(testList.contains(board.getCell(10, 13)));
		assertTrue(testList.contains(board.getCell(10, 11)));
		assertTrue(testList.contains(board.getCell(11, 12)));
		assertTrue(testList.contains(board.getCell(9, 12)));
		assertEquals(4,testList.size());	
		
	}
	@Test
	public void testAdjsOfHall() {
		Set<BoardCell>testList = board.getAdjList(0, 2);
		System.out.println(board.getAdjList(0, 2));
		assertTrue(testList.contains(board.getCell(1, 2)));
	}

	@Test
	public void testTargetsInDiningRoom() {
		// test a roll of 3
		board.calcTargets(board.getCell(5,0), 3);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(6, 2)));	}


	@Test
	public void testTargetsAtDoor() {
		// test a roll of 4, at door
		board.calcTargets(board.getCell(6, 5), 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(3, 4)));
		assertTrue(targets.contains(board.getCell(3, 6)));
		assertTrue(targets.contains(board.getCell(10, 5)));
		assertTrue(targets.contains(board.getCell(7, 8)));
		assertTrue(targets.contains(board.getCell(4, 10)));
	}


	@Test
	public void testTargetsInWalkway1() {
		// test a roll of 1
		board.calcTargets(board.getCell(10, 17), 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(11, 16)));
		assertTrue(targets.contains(board.getCell(11, 18)));
		assertTrue(targets.contains(board.getCell(5, 18)));
		assertTrue(targets.contains(board.getCell(10, 15)));	
		assertTrue(targets.contains(board.getCell(10, 19)));
	}

	@Test
	// test to make sure occupied locations do not cause problems
	public void testTargetsOccupied() {
		// test a roll of 1, at door
		board.getCell(1, 12).setOccupied(true);
		
		board.calcTargets(board.getCell(2, 12), 2);
		board.getCell(1, 12).setOccupied(false);
		
		Set<BoardCell> targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCell(4, 12)));


	}





}
