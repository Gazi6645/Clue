package clueGame;

public class Card {
	public Card(String cardName, cardType type) {
		super();
		this.cardName = cardName;
		this.type = type;
	}
	//instance vars for the card
	public String cardName;
	public cardType type;
	//check if two cards are equal
    @Override
	public boolean equals(Object obj) {
    	Card c=(Card) obj;
		if((c.cardName.equals(cardName) && (c.type == type))) {
			return true;
		}
		return false;
	}
	public cardType getType() {
		return type;
	}
	@Override
	public String toString() {
		return "Card [cardName=" + cardName + ", type=" + type + "]";
	}
	
}
