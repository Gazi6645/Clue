package clueGame;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.lang.Thread;
@SuppressWarnings("serial")

public class ClueGame extends JFrame {
	
	private ArrayList<Card> deck;
	private ArrayList<Card> Locals;
	private ArrayList<Card> Weapons;
	private ArrayList<Card> People;
	private Card localCard;
	private Card weapCard;
	private Card PersonCard;
	private cardGUI gui;
	GameControlPanel controlls;
	player currPlay;
	boolean hasMoved = false;
	Random rn = new Random(System.currentTimeMillis());
	ArrayList<ComputerPlayer> compPlayers = new ArrayList<ComputerPlayer>();
	Board board;
	public ClueGame(Board board){
		super();
		this.board = board;
		setSize(1000,1000);
		Locals = new ArrayList<Card>();
		Weapons= new ArrayList<Card>();
		People = new ArrayList<Card>();
		deck = board.getDeck();
		for(Card card : deck) {
			if(card.getType() == cardType.LOCATION) {
				Locals.add(card);
			}
			if(card.getType() == cardType.WEAPON) {
				Weapons.add(card);
			}
			if(card.getType() == cardType.PLAYER) {
				People.add(card);
			}
		}
	}
	public void setPlay(player play) {
		currPlay = play;
	}
	public static void main(String[] args) {

	    Board board = Board.getInstance();

		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
			// Initialize will load config files 
			try {
				board.initialize();
			} catch (BadConfigFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		ClueGame game = new ClueGame(board);
	    game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    game.board = board;
		board.setClueGame(game);
		cardGUI gui = new cardGUI(board.getHumanPlayer());
		game.add(gui, BorderLayout.EAST);
	    GameControlPanel controlls = new GameControlPanel(game);
	    game.gui = gui;
	    game.add(controlls, BorderLayout.SOUTH);
	    game.setControlls(controlls);
	    game.add(board, BorderLayout.CENTER);
	    game.setVisible(true);
	    //get player array to loop through when human is done
	    game.compPlayers.addAll(board.getComputerPlayers());
	    //get the human
	    HumanPlayer humPlay = board.getHumanPlayer();
	    controlls.set_TurnText(humPlay.name);
	    //give game human player
	    game.setPlay(humPlay);
	    //spash screen
	    JOptionPane.showMessageDialog(null, "You are, "+humPlay.getName()+ " Can you figure out the murder? probably not lol.");
	    //get first random value
		Integer roll = Math.abs(game.rn.nextInt());
		roll = roll%6;
		roll += 1;
		//ROLL NEEDS TO BE CHANGED TO BE RANDOM AGAIN, CHNAGED FOR TESTING
	    controlls.set_rollText(roll.toString());
	    System.out.println(board.getSolution());
	    board.calcTargets(board.getCell(humPlay.row, humPlay.col),99);
	    game.repaint();
	    
	    
	    
	    
	    
	    
	}
	private void setControlls(GameControlPanel controlls2) {
		controlls = controlls2;
	}
	public void gotoNextPlayer() {
		
		Integer roll = 0;
		if(hasMoved) {
			

			for(ComputerPlayer plays : compPlayers) {
				controlls.set_TurnText(plays.name);
				if(plays.isAccuse()== true) {
					if(plays.accuse()==true) {
						dispose();
					}else {
						//plays=null;
					}
				}
				else {
					System.out.println("check");
					plays.canSuggest = false;
				}
				roll = Math.abs(rn.nextInt());
				roll = roll%6;
				roll += 1;
				plays.selectTargets(roll);
				if(plays.canSuggest) {
					controlls.updateGuess(plays.name+ " is guessing");
					Card suggestCard= plays.createSuggestion();
					//null means no disprove
					if(suggestCard == null) {
						controlls.updateGuessResult("guess was disproven");
						//tell player to make accusation at start of next turn
						plays.setAccuse(true);
					}
					else {
						//whatever
						controlls.updateGuessResult("guess was NOT disproven");

					}
					//card = card that was disproved
				}

				this.repaint();
				JOptionPane.showMessageDialog(null, "Comp finished. proceed to next comp?");

				
			}
			this.repaint();
			hasMoved =false;
			roll = Math.abs(rn.nextInt());
			roll = roll%6;
			roll += 1;
		    controlls.set_rollText(roll.toString());
		    board.calcTargets(board.getCell(currPlay.row, currPlay.col),roll);
		    System.out.println(board.getSolution());
		    this.repaint();
		    boolean next = false;
		    controlls.set_TurnText(currPlay.name);


		}
		else {
			JOptionPane.showMessageDialog(null, "Bud, you gotta move.");
		}
		
		
		
	}
	void makeSuggestion() {
		JFrame frame = new JFrame();
		final JDialog accuseDialog = new JDialog(frame, "Accusation Dialog", Dialog.ModalityType.APPLICATION_MODAL);
		accuseDialog.setTitle("Accusation Dialog");
		accuseDialog.setLayout(new GridLayout(4,2));
		String[] locals = new String[1];
		for(int i=0; i<Locals.size(); i++) {
			player Player = currPlay;
			System.out.println(getBoard().getRoom(getBoard().getCell(Player.row,Player.col)).getName());
			if(getBoard().getRoom(getBoard().getCell(Player.row,Player.col)).getName().equals(Locals.get(i).cardName)) {
				System.out.println("targetLocked");
				locals[0] = Locals.get(i).cardName;
			}
			
		}
		String[] weapons = new String[Weapons.size()];
		for(int i=0; i<Weapons.size(); i++) {
			weapons[i] = Weapons.get(i).cardName;
		}
		String[] people = new String[People.size()];
		for(int i=0; i<People.size(); i++) {
			people[i] = People.get(i).cardName;
		}
		JComboBox<String> areas = new JComboBox<String>(locals);
		accuseDialog.add(new JLabel("Location"));
		accuseDialog.add(areas);
		JComboBox<String> deaths = new JComboBox<String>(weapons);
		JComboBox<String> dudes = new JComboBox<String>(people);
		accuseDialog.add(new JLabel("Weapons"));
		accuseDialog.add(deaths);
		accuseDialog.add(new JLabel("People"));
		accuseDialog.add(dudes);
		JButton end = new JButton("End");
		end.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String realm = areas.getSelectedItem().toString();
				String atck = deaths.getSelectedItem().toString(); 
				String Dud = dudes.getSelectedItem().toString();
				for(Card card : deck) {
					/*
					 * 	private Card localCard;
					 *	private Card weapCard;
					 *	private Card PersonCard;
					 */
					if(card.cardName.equals(realm)) {
						localCard =card;
					}
					if(card.cardName.equals(atck)) {
						weapCard = card;
					}
					if(card.cardName.equals(Dud)) {
						PersonCard = card;
						for(player play : board.getPlayers()) {
							if(play.getName().equals(card.cardName)) {
								System.out.println(play.getName());
								play.row = currPlay.row;
								play.col = currPlay.col;
							}
						}
					}

				}
				ArrayList<Card> AccuseCards = new ArrayList<Card>();
				AccuseCards.add(localCard);
				AccuseCards.add(PersonCard);
				AccuseCards.add(weapCard);
				controlls.updateGuess("room "+localCard.cardName+"  weapon "+weapCard.cardName+" person "+PersonCard.cardName);
				Card returnCard = board.checkSuggestion(AccuseCards, currPlay);
				if(returnCard == null) {
					JOptionPane.showMessageDialog(null, "Your suggestion was not disproved");
					controlls.updateGuessResult("not disproved");
				}
				else {
					JOptionPane.showMessageDialog(null, "Your suggestion was disproved by " + returnCard.cardName+".");
					controlls.updateGuessResult("disproved by "+returnCard.cardName);
					currPlay.updateKnown(returnCard);
					gui.refresh();
					accuseDialog.dispose();
				}
				
			}
			
		});
		JButton Cancel = new JButton("Cancel");
		Cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				accuseDialog.dispose();

			}
			
		});
		accuseDialog.add(Cancel);
		accuseDialog.add(end);
		accuseDialog.setSize(new Dimension(200,200));
		accuseDialog.setVisible(true);		
	}
	public void move(BoardCell boardCell) {
		currPlay.setLocation(boardCell.getCol(), boardCell.getRow());
		hasMoved = true;
		board.calcTargets(board.getCell(0, 0), 1);
		this.repaint();
		
	}
	public Board getBoard() {
		// TODO Auto-generated method stub
		return board;
	}
	public void win() {
		JOptionPane.showMessageDialog(null, "Congrats, you win");
		
	}
	public void lose() {
		JOptionPane.showMessageDialog(null, "Darn, you lose");		
	}
	public void close() {
		this.dispose();
		
	}

}

