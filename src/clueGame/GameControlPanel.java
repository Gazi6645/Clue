package clueGame;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel {
	private JTextField GuessText;
	private JTextField GuessResult;
	private JTextField rollText;
	private JTextField TurnText;
	private ClueGame currGame;
	private ArrayList<Card> deck;
	private ArrayList<Card> Locals;
	private ArrayList<Card> Weapons;
	private ArrayList<Card> People;
	private Card localCard;
	private Card weapCard;
	private Card PersonCard;

	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public GameControlPanel( ClueGame game)  {
		currGame = game;
		deck = currGame.getBoard().getDeck();
		Locals = new ArrayList<Card>();
		Weapons= new ArrayList<Card>();
		People = new ArrayList<Card>();
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
		setLayout(new GridLayout(2,0));
		JPanel NEXTANDROLLPANEL= new JPanel(new GridLayout(1,4));
		JPanel fullGuessPanel = new JPanel(new GridLayout(0,2));
		JPanel namePanel = currentNamePannel();
		NEXTANDROLLPANEL.add(namePanel);
		
		JPanel DicePanel = rollPannel();
		NEXTANDROLLPANEL.add(DicePanel);
	
		JPanel GuessPanel = GuessPannel();
		fullGuessPanel.add(GuessPanel);
		
		JPanel GuessingPannel = GuessResultPannel();
		fullGuessPanel.add(GuessingPannel);

		JPanel NextPannel = createButtonPanel();
		NEXTANDROLLPANEL.add(NextPannel);
		
		add(NEXTANDROLLPANEL);
		add(fullGuessPanel);
		
		
		


	}
	private class nextListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			currGame.gotoNextPlayer();
			
		}
		
	}

	private class accusationListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			player Player = currGame.currPlay;
			if(!currGame.getBoard().getRoom(currGame.getBoard().getCell(Player.row,Player.col)).getName().equals("Walkway")) {
				ArrayList<Card> Accuse = accusePanel();	
			}
			else {
				JOptionPane.showMessageDialog(null, "You cant accuse unless you are in a room");
			}
			
			
			
		}

		private ArrayList<Card> accusePanel() {
			// TODO Auto-generated method stub
			JFrame frame = new JFrame();
			final JDialog accuseDialog = new JDialog(frame, "Accusation Dialog", Dialog.ModalityType.APPLICATION_MODAL);
			accuseDialog.setTitle("Accusation Dialog");
			accuseDialog.setLayout(new GridLayout(4,2));
			String[] locals = new String[1];
			for(int i=0; i<Locals.size(); i++) {
				player Player = currGame.currPlay;
				System.out.println(currGame.getBoard().getRoom(currGame.getBoard().getCell(Player.row,Player.col)).getName());
				if(currGame.getBoard().getRoom(currGame.getBoard().getCell(Player.row,Player.col)).getName().equals(Locals.get(i).cardName)) {
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
					
					// TODO Auto-generated method stub
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
						}

					}
					ArrayList<Card> AccuseCards = new ArrayList<Card>();
					AccuseCards.add(localCard);
					AccuseCards.add(PersonCard);
					AccuseCards.add(weapCard);
					if(currGame.getBoard().checkAccusation(AccuseCards)) {
						currGame.win();
					}
					else {
						currGame.lose();
					}
						
					
					accuseDialog.dispose();
					currGame.close();
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
			return Locals;
			
			
			
		}
		
	}
	
	public void set_Guess(String guess) {
		GuessText.setText(guess);
	}
	public void set_GuessResult(String guess) {
		GuessResult.setText(guess);
	}
	public void set_rollText(String guess) {
		rollText.setText(guess);
	}
	public void set_TurnText(String guess) {
		TurnText.setText(guess);
	}
	
	private JPanel createButtonPanel() {
		JButton MakeA = new JButton("Make Accusation");
		JButton next = new JButton("NEXT!");
		next.addActionListener(new nextListener());
		MakeA.addActionListener(new accusationListener());
		JPanel panel = new JPanel();
		panel.setLayout((new GridLayout(1,2)));
		panel.add(MakeA);
		panel.add(next);
		return panel;
	}

	private JPanel GuessResultPannel() {
		JPanel panel = new JPanel();
		// Use a grid layout, 1 row, 2 elements (label, text)
		panel.setLayout(new GridLayout(2,1));
		JLabel nameLabel = new JLabel("");
		GuessResult = new JTextField(20);
		panel.add(nameLabel);
		panel.add(GuessResult);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		return panel;
	}
	public void updateGuessResult(String result) {
		GuessResult.setText(result);
	}
	public void updateGuess(String guess) {
		GuessText.setText(guess);
	}

	private JPanel GuessPannel() {
		JPanel panel = new JPanel();
		// Use a grid layout, 1 row, 2 elements (label, text)
		panel.setLayout(new GridLayout(2,1));
		JLabel nameLabel = new JLabel("");
		GuessText = new JTextField(20);
		panel.add(nameLabel);
		panel.add(GuessText);
		//panel.add(name);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		return panel;
	}

	private JPanel rollPannel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
		JLabel nameLabel = new JLabel(" Roll");
		rollText = new JTextField(5);
		panel.add(nameLabel);
		panel.add(rollText);
		return panel;
	}

	private JPanel currentNamePannel() {
		JPanel panel = new JPanel();
		// Use a grid layout, 1 row, 2 elements (label, text)
		panel.setLayout(new GridLayout(2,1));
		JLabel nameLabel = new JLabel("Whose turn");
		TurnText = new JTextField(20);
		panel.add(nameLabel);
		panel.add(TurnText);
		return panel;
		}


	/*
	 * Time for human guessing
	 * Woot, woot.
	 * step 1 create suggestion pannel, three drop down menus with all relevent cards
	 * room should be auto chosen tho.
	 * Then we must ask all other players to disprove, if so add to known cards, if else tell player
	 * it was not disproven. This is probably just a radio whatever
	 * if they make the accusation if its right they win, else they lose.
	 * this is it.
	 * this will be done by:
	 */

	}

