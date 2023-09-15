package clueGame;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

//Order them in the way they were called
public class Room extends JPanel {
	private String name;
	private BoardCell CenterCell;
	private BoardCell labelCell;
	private char label;
	public Room(String name, char label) {
		this.name = name;
		this.label = label;
	}
	public String getName() {
		return name;
	}
	
	public BoardCell getCenterCell() {
		return CenterCell;
	}
	
	public BoardCell getLabelCell() {
		return labelCell;
	}
	public void setCenterCell(BoardCell cell) {
		CenterCell = cell;
	}
	public void setlabelCell(BoardCell cell) {
		labelCell = cell;
	}
	@Override
	public String toString() {
		return "Room [name=" + name + ", label=" + label + "]";
	}
	public Card createCard() {
		// TODO Auto-generated method stub
		Card card = new Card(name, cardType.LOCATION);
		return card;
	}
	public void Draw(Graphics g, int cellWidth, int cellHeight) {
		if(labelCell != null) {
			g.setColor(Color.white);
		labelCell.Draw(g, cellWidth, cellHeight, labelCell.getCol()*cellWidth, labelCell.getRow()*cellHeight,name);
		}
		
	}
	
}