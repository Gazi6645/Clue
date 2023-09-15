package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;
import clueGame.HumanPlayer;
import clueGame.Room;
import clueGame.player;
import clueGame.Card;
import clueGame.cardType;
public class GameSolutionTest {
	//handle card processing, check accusation, disprove suggestion, handleSuggestion()
	private static Board board;
	@BeforeAll

	public static void setUp() throws BadConfigFormatException {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load BOTH config files
		//this is edited so that load config files dont have to have messy copy pastes of previous code.
		try {
			board.initialize();
		} catch (BadConfigFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	* testAccusationCorrect: solution that is correct
	*/
	@Test
	public void testAccusationCorrect() {
		assertTrue(board.checkAccusation(board.getSolution()));
	}
	
	/**
	* testAccusationWrongPerson: solution with wrong person
	*/
	
	@Test
	public void testAccusationWrongPerson() {
		ArrayList<Card> Solution = board.getSolution();
		Iterator<Card> solCards = Solution.iterator();
		ArrayList<Card> incorrectSol = new ArrayList<Card>();
		while(solCards.hasNext()) {
			Card solCardNextCard = solCards.next();
			if(!(solCardNextCard.type == cardType.PLAYER)) {
				incorrectSol.add(solCardNextCard);
			}
			
		}
		incorrectSol.add(new Card("kzzt", cardType.PLAYER));
		System.out.println(incorrectSol);
		System.out.println(board.getSolution());
		assertFalse(board.checkAccusation(incorrectSol));
	}
	
	/**
	* testAccusationWrongWeapon: solution with wrong weapon
	*/
	@Test
	public void testAccusationWrongWeapon() {
		ArrayList<Card> Solution = board.getSolution();
		Iterator<Card> solCards = Solution.iterator();
		ArrayList<Card> incorrectSol = new ArrayList<Card>();
		while(solCards.hasNext()) {
			Card solCardNextCard = solCards.next();
			if(!(solCardNextCard.type == cardType.WEAPON)) {
				incorrectSol.add(solCardNextCard);
			}
			
		}
		incorrectSol.add(new Card("kzzt", cardType.WEAPON));
		System.out.println(incorrectSol);
		System.out.println(board.getSolution());
		assertFalse(board.checkAccusation(incorrectSol));
	
	}
	/**
	* testAccusationWrongWeapon: solution with wrong Room
	*/
	
	@Test
	public void testAccusationWrongRoom() {
		ArrayList<Card> Solution = board.getSolution();
		Iterator<Card> solCards = Solution.iterator();
		ArrayList<Card> incorrectSol = new ArrayList<Card>();
		while(solCards.hasNext()) {
			Card solCardNextCard = solCards.next();
			if(!(solCardNextCard.type == cardType.LOCATION)) {
				incorrectSol.add(solCardNextCard);
			}
			
		}
		incorrectSol.add(new Card("kzzt", cardType.LOCATION));
		System.out.println(incorrectSol);
		System.out.println(board.getSolution());
		assertFalse(board.checkAccusation(incorrectSol));
	}
	
	/**
	* playerDisprovesTest: If player has only one matching card it should be returned
	*/
	
	@Test
	public void playerDisprovesTest() {
		//get solution
		Card wrongCard;
		ArrayList<Card> Solution = board.getSolution();
		Iterator<Card> solCards = Solution.iterator();
		ArrayList<Card> incorrectSol = new ArrayList<Card>();
		boolean isBrick = false;
		while(solCards.hasNext()) {
			Card solCardNextCard = solCards.next();
			if(!(solCardNextCard.type == cardType.WEAPON)) {
				incorrectSol.add(solCardNextCard);
			}
			else if(solCardNextCard.cardName.equals("Brick")) {
				isBrick = true;
			}
			
		}
		if(isBrick) {
			wrongCard = new Card("Sword", cardType.WEAPON);
			incorrectSol.add(wrongCard);
		}
		else {
			wrongCard = new Card("Brick", cardType.WEAPON);
			incorrectSol.add(wrongCard);
		}
		//create new player with no cards such that when we check for conflicts we dont ignore a player that COULD conflict.
		player play =  new HumanPlayer(null, "", 0, 0, board);
		Card returnCard = board.checkSuggestion(incorrectSol, play);
		assertTrue(returnCard.equals(wrongCard));
	}
	/**
	* playerDisprovesTest2Card: If players has >1 matching card, returned card should be chosen randomly
	*/
	@Test
	public void playerDisprovesTest2Card() {
		//get solution
		ArrayList<Card> Solution = board.getSolution();
		Iterator<Card> solCards = Solution.iterator();
		ArrayList<Card> incorrectSol = new ArrayList<Card>();
		incorrectSol.addAll(board.getPlayers().get(2).getHand());
		for (Card card : board.getPlayers().get(2).getHand()) {
			if(card.getType() == cardType.WEAPON) {
				incorrectSol.remove(card);
			}
		}
		while(solCards.hasNext()) {
			Card solCardNextCard = solCards.next();
			if((solCardNextCard.type == cardType.WEAPON)) {
				incorrectSol.add(solCardNextCard);
			
			}
			
		}
		Card returnCard = board.checkSuggestion(incorrectSol, board.getHumanPlayer());
		assertTrue(returnCard.equals(incorrectSol.get(0)) || returnCard.equals(incorrectSol.get(1)) || returnCard.equals(incorrectSol.get(2)));
	}	
	/**
	* noDisprovals: If player has no matching cards, null is returned
	*/
	@Test
	public void noDisprovals() {
		//get solution
		ArrayList<Card> Solution = board.getSolution();
		player play = new HumanPlayer(null, "", 0, 0, board);
		Card returnCard = board.checkSuggestion(Solution, play);
		assertTrue(returnCard == null);
	}	
}
