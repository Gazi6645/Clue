package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class cardGUI extends JPanel {
	private JLabel name;
	JPanel returnPanel;
	player Player;
	public cardGUI(player play)
	{
	// Create a layout with 2 rows
		Player = play;
		Set<Card> handTemp = play.getHand();
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.addAll(handTemp);
		Set<Card> knownTemp = play.getKnown();
		ArrayList<Card> known = new ArrayList<Card>();
		known.addAll(knownTemp);
		setLayout(new GridLayout(3,0));
		
		JPanel panel = createCardPanel(cardType.PLAYER, play);
		add(panel);
		panel = createCardPanel(cardType.WEAPON, play);
		add(panel);
		panel = createCardPanel(cardType.LOCATION, play);
		add(panel);
	}
	private JPanel createCardPanel(cardType weapon, player play) {
		// TODO Auto-generated method stub
		returnPanel = new JPanel();
		// Use a grid layout, 1 row, 2 elements (label, text)
		returnPanel.setLayout(new GridLayout(4,1));


		if(weapon.equals(cardType.WEAPON)) {	
			returnPanel.setBorder(new TitledBorder (new EtchedBorder(), "Weapon"));
			ArrayList<Card> weapsToDisp = new ArrayList<Card>();
			for(Card card : play.getHand()) {
				if(card.getType().equals(cardType.WEAPON)) {
					weapsToDisp.add(card);
				}
			}
			JLabel nameLabel = new JLabel("In Hand");
			returnPanel.add(nameLabel);
			if(weapsToDisp.size() == 0) {
				name = new JLabel("None");
				returnPanel.add(name);
			}
			else {
				JPanel cardOut = new JPanel();
				cardOut.setLayout(new GridLayout(weapsToDisp.size(),1));
				for(Card card : weapsToDisp) {
					JLabel cardName = new JLabel(card.cardName);
					cardName.setBackground(Color.blue);
					cardOut.add(cardName);
					
				}
				returnPanel.add(cardOut);
			}

			weapsToDisp = new ArrayList<Card>();
			for(Card card : play.getKnown()) {
				if(card.getType().equals(cardType.WEAPON)) {
					weapsToDisp.add(card);
				}
			}
			nameLabel = new JLabel("Known");
			returnPanel.add(nameLabel);
			if(weapsToDisp.size() == 0) {
				name = new JLabel("None");
				returnPanel.add(name);
			}
			else {
				JPanel cardOut = new JPanel();
				cardOut.setLayout(new GridLayout(weapsToDisp.size(),1));
				for(Card card : weapsToDisp) {
					JLabel cardName = new JLabel(card.cardName);
					cardName.setBackground(Color.blue);
					cardOut.add(cardName);
					
				}
				returnPanel.add(cardOut);
			}
		}
		else if(weapon.equals(cardType.LOCATION)) {
			returnPanel.setBorder(new TitledBorder (new EtchedBorder(), "Location"));
			ArrayList<Card> weapsToDisp = new ArrayList<Card>();
			for(Card card : play.getHand()) {
				if(card.getType().equals(cardType.LOCATION)) {
					weapsToDisp.add(card);
				}
			}
			JLabel nameLabel = new JLabel("In Hand");
			returnPanel.add(nameLabel);
			if(weapsToDisp.size() == 0) {
				name = new JLabel("None");
				returnPanel.add(name);
			}
			else {
				JPanel cardOut = new JPanel();
				cardOut.setLayout(new GridLayout(weapsToDisp.size(),1));
				for(Card card : weapsToDisp) {
					JLabel cardName = new JLabel(card.cardName);
					cardName.setBackground(Color.blue);
					cardOut.add(cardName);
					
				}
				returnPanel.add(cardOut);
			}

			weapsToDisp = new ArrayList<Card>();
			for(Card card : play.getKnown()) {
				if(card.getType().equals(cardType.LOCATION)) {
					weapsToDisp.add(card);
				}
			}
			nameLabel = new JLabel("Known");
			returnPanel.add(nameLabel);
			if(weapsToDisp.size() == 0) {
				name = new JLabel("None");
				returnPanel.add(name);
			}
			else {
				JPanel cardOut = new JPanel();
				cardOut.setLayout(new GridLayout(weapsToDisp.size(),1));
				for(Card card : weapsToDisp) {
					JLabel cardName = new JLabel(card.cardName);
					cardName.setBackground(Color.blue);
					cardOut.add(cardName);
					
				}
				returnPanel.add(cardOut);
			}
		}
		else {
			returnPanel.setBorder(new TitledBorder(new EtchedBorder(), "People"));
			ArrayList<Card> weapsToDisp = new ArrayList<Card>();
			for(Card card : play.getHand()) {
				if(card.getType().equals(cardType.PLAYER)) {
					weapsToDisp.add(card);
				}
			}
			JLabel nameLabel = new JLabel("In Hand");
			returnPanel.add(nameLabel);
			if(weapsToDisp.size() == 0) {
				name = new JLabel("None");
				returnPanel.add(name);
			}
			else {
				JPanel cardOut = new JPanel();
				cardOut.setLayout(new GridLayout(weapsToDisp.size(),1));
				for(Card card : weapsToDisp) {
					JLabel cardName = new JLabel(card.cardName);
					cardName.setBackground(Color.blue);
					cardOut.add(cardName);
					
				}
				returnPanel.add(cardOut);
			}

			weapsToDisp = new ArrayList<Card>();
			for(Card card : play.getKnown()) {
				if(card.getType().equals(cardType.PLAYER)) {
					weapsToDisp.add(card);
				}
			}
			nameLabel = new JLabel("Known");
			returnPanel.add(nameLabel);
			if(weapsToDisp.size() == 0) {
				name = new JLabel("None");
				returnPanel.add(name);
			}
			else {
				JPanel cardOut = new JPanel();
				cardOut.setLayout(new GridLayout(weapsToDisp.size(),1));
				for(Card card : weapsToDisp) {
					JLabel cardName = new JLabel(card.cardName);
					cardName.setBackground(Color.blue);
					cardOut.add(cardName);
					
				}
				returnPanel.add(cardOut);
			}
		}
		return returnPanel;
	}


	public void refresh() {
		this.removeAll();
		JPanel panel = createCardPanel(cardType.PLAYER, Player);
		add(panel);
		panel = createCardPanel(cardType.WEAPON, Player);
		add(panel);
		panel = createCardPanel(cardType.LOCATION, Player);
		add(panel);
		this.revalidate();
		
	}
	public static void main(String[] args) {
		// Create a JFrame with all the normal functionality
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("GUI Example");
		frame.setSize(250, 150);
		// Create the JPanel and add it to the JFrame
		Board board = Board.getInstance();

		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		try {
			board.initialize();
		} catch (BadConfigFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(board.getHumanPlayer().getHand());
		cardGUI gui = new cardGUI(board.getHumanPlayer());
		frame.add(gui, BorderLayout.CENTER);
		// Now let's view it
		frame.setVisible(true);
		board.getHumanPlayer().updteHand(new Card("Ham Sandwich", cardType.WEAPON));
		try {
			TimeUnit.SECONDS.sleep(10);
			System.out.println("waiting");

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("err");
			e.printStackTrace();
		}

		gui.refresh();
		frame.revalidate();
		}
	}