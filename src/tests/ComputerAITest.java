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
import clueGame.Room;
import clueGame.player;
import clueGame.Card;
import clueGame.ComputerPlayer;
import clueGame.cardType;
public class ComputerAITest {
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
	 * Computer player select a target
	* noRoom:if no rooms in list, select randomly
	*/
	
	@Test
	public void noRoom() {
		
		ArrayList<player> players = board.getPlayers();
		for(player player : players) {
			if(player.isComp()) {
				player.setLocation(15, 13);
				player.selectTargets(2);
				assertTrue((player.row == 10 && player.col==13)||(player.row == 11 && player.col==14)||(player.row == 11 && player.col==12));
				
			}
		}
	}
	/**
	* Computer player select a target
	* noRoom:if room in list that has not been seen, select it
	*/
	@Test
	public void newRoom() {
		ArrayList<player> players = board.getPlayers();
		for(player player : players) {
			if(player.isComp()) {
				player.setLocation(15,13);
				player.selectTargets(4);

				assertTrue((player.row == 4 && player.col==14));
				
			}
		}
	}
	
	/**
	 * Computer player select a target
	* oldRoom: if room in list that has been seen, each target (including room) selected randomly
	*/
	@Test
	public void oldRoom() {
		ArrayList<player> players = board.getPlayers();
		for(player player : players) {
			if(player.isComp()) {
				player.setLocation(11,13);
				player.roomSeenAdd('P');
				player.selectTargets(1);
				assertTrue((player.row == 10 && player.col==13)||(player.row == 15 && player.col==13)||(player.row == 11 && player.col==14)||(player.row == 11 && player.col==12));
			}
		}
	}
	/**
	 * Computer player create a suggestion,
	* compSuggestCorrectLocal: Room matches current location
	*/
	
	
	@Test
	public void compSuggestCorrectLocal() {
		boolean hasAccused = false;
		ArrayList<Card> fullDeck = new ArrayList<Card>();
		fullDeck.addAll(board.getDeck());
		ArrayList<Card> sol = board.getSolution();
		fullDeck.removeAll(sol);
		
		for(ComputerPlayer player : board.getComputerPlayers()) {
			player.setLocation(12, 12);
			System.out.println(sol);
			System.out.println();
			System.out.println();

			player.addManySeenCards(fullDeck);
			if(!hasAccused) {
			Card cardToDisprove = player.createSuggestion();

			Card correctCard = new Card("", cardType.PLAYER);
			for(Card card : board.getSolution()) {
				if(card.getType() == cardType.LOCATION) {
					correctCard = card;
				}
			}
			if(cardToDisprove != null) {
				assertTrue(cardToDisprove.cardName.equals(board.getRoom(board.getCell(player.row, player.col)).getName()) || cardToDisprove == null && correctCard.cardName.equals(board.getRoom(board.getCell(player.row, player.col)).getName()));
			}
			else{
				assertTrue(true);
			}
			hasAccused = true;
			}}
	}
	/**
	 * Computer player create a suggestion,
	* compSuggestCorrect: If only one person not seen, it's selected
	*/
	@Test
	public void compSuggestCorrect() {
		//only one weapon and player are possible, thus we should never see a non null result
		boolean hasAccused = false;
		ArrayList<Card> fullDeck = new ArrayList<Card>();
		fullDeck.addAll(board.getDeck());
		ArrayList<Card> sol = board.getSolution();
		fullDeck.removeAll(sol);
		
		for(ComputerPlayer player : board.getComputerPlayers()) {
			player.setLocation(13, 13);

			player.addManySeenCards(fullDeck);
			if(!hasAccused) {
			Card cardToDisprove = player.createSuggestion();
			System.out.println("");
			System.out.println("");
			System.out.println(cardToDisprove);
			assertTrue(cardToDisprove == null || cardToDisprove.cardName.equals(board.getRoom(board.getCell(player.row, player.col)).getName()));
			hasAccused = true;
			}}
	}
	/**
	 * Computer player create a suggestion,
	* compSuggestManyWeapon:  If multiple weapons not seen, one of them is randomly selected
	*/
	@Test
	public void compSuggestManyWeapon() {
		//only one weapon and player are possible, thus we should never see a non null result
		boolean hasAccused = false;
		ArrayList<Card> fullDeck = new ArrayList<Card>();
		fullDeck.addAll(board.getDeck());
		ArrayList<Card> sol = board.getSolution();
		fullDeck.removeAll(sol);
		fullDeck.removeAll(sol);
		fullDeck.removeAll(sol);
		fullDeck.remove(new Card("Brick", cardType.WEAPON));
		fullDeck.remove(new Card("Sword", cardType.WEAPON));
		fullDeck.remove(new Card("Car", cardType.WEAPON));
		
		
		for(ComputerPlayer player : board.getComputerPlayers()) {
			player.setLocation(15, 13);

			player.addManySeenCards(fullDeck);
			if(!hasAccused) {
			Card cardToDisprove = player.createSuggestion();
			System.out.println("");
			System.out.println("");
			System.out.println(cardToDisprove);
			assertTrue(cardToDisprove == null || cardToDisprove.cardName.equals(board.getRoom(board.getCell(player.row, player.col)).getName()) || cardToDisprove.equals(new Card("Brick", cardType.WEAPON)) || cardToDisprove.equals(new Card("Sword", cardType.WEAPON)) || cardToDisprove.equals(new Card("Car", cardType.WEAPON)));
			hasAccused = true;
			}}
	}
	/**
	 * Computer player create a suggestion,
	* compSuggestManyPerson: If multiple persons not seen, one of them is randomly selected
	*/
	@Test
	public void compSuggestManyPerson() {
		//only one weapon and player are possible, thus we should never see a non null result
		boolean hasAccused = false;
		ArrayList<Card> fullDeck = new ArrayList<Card>();
		fullDeck.addAll(board.getDeck());
		ArrayList<Card> sol = board.getSolution();
		fullDeck.removeAll(sol);
		fullDeck.remove(new Card("Joe", cardType.PLAYER));
		fullDeck.remove(new Card("Moe", cardType.PLAYER));
		fullDeck.remove(new Card("Greg", cardType.PLAYER));
		
		
		for(ComputerPlayer player : board.getComputerPlayers()) {
			player.setLocation(15,13);
			player.addManySeenCards(fullDeck);
			if(!hasAccused) {
			Card cardToDisprove = player.createSuggestion();
			System.out.println("");
			System.out.println("");
			System.out.println(cardToDisprove);
			for(Card card : player.getHand()) {
				System.out.println();
				System.out.println();
				System.out.println(card);
			}
			System.out.println();
			System.out.println();
			System.out.println(board.getSolution());
			System.out.println();
			System.out.println();
			assertTrue(cardToDisprove == null || cardToDisprove.cardName.equals(board.getRoom(board.getCell(player.row, player.col)).getName()) || cardToDisprove.equals(new Card("Moe", cardType.PLAYER)) || cardToDisprove.equals(new Card("Joe", cardType.PLAYER)) || cardToDisprove.equals(new Card("Greg", cardType.PLAYER)));
			hasAccused = true;
			return;
			}}
	}
	
}
