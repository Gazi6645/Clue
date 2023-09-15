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
import clueGame.cardType;
public class PlayerInitTest {
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
	@Test
	public void testPlayerInint() {
		assertTrue(board.getNumPlayer() == 6);
	}
	@Test
	public void testHumansAndComputers() {
		assertTrue(board.getNumHumans() == 1);
		assertTrue(board.getNumComputers() == 5);
	}
	@Test
	public void testDeckSize() {
		System.out.println(board.getDeckSize());
		assertTrue(board.getDeckSize() == 21);
	}
	@Test
	public void solutionSize() {
		System.out.println(board.getSolution().size());
		assertTrue(board.getSolution().size() == 3);
	}
	@Test
	//check to ensure all players have same number of cards
	public void playerHandSizes() {
		HashSet<player> players = new HashSet<player>();
		Iterator<player> playersToLoopThru = players.iterator();
		int cardsInHand = 0;
		while(playersToLoopThru.hasNext()) {
			player currPlay = playersToLoopThru.next();
			if(cardsInHand == 0) {
				cardsInHand = currPlay.getHand().size();
			}
			assertEquals(cardsInHand, currPlay.getHand().size());
		}
	}
	@Test
	public void SolutionHasAllTypes() {
		ArrayList<Card>cards = board.getSolution();
		Iterator<Card> cardToLoopThrough = cards.iterator();
		boolean local = false;
		boolean weap = false;
		boolean play = false;
		while(cardToLoopThrough.hasNext()) {
			Card currCard = cardToLoopThrough.next();
			if(currCard.type == cardType.LOCATION) {
				local = true;
			}
			if(currCard.type == cardType.PLAYER) {
				weap = true;
			}
			if(currCard.type == cardType.WEAPON) {
				play = true;
			}
		}
		assertTrue(local);
		assertTrue(weap);
		assertTrue(play);
	}
	@Test
	public void SolutionIsUnique() {
		ArrayList<Card>cards = board.getSolution();
		ArrayList<player> players = board.getPlayers();
		Boolean cardsRepeat = false;
		for(player Player : players) {
			Set<Card> cardsToCheck = Player.getHand();
			for(Card card : cardsToCheck) {
				if(cards.contains(card)) {
					System.out.println(card);
					cardsRepeat = true;
					
				}
			}
		}
		assertFalse(cardsRepeat);
	}
	
}
