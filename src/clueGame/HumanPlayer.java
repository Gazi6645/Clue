package clueGame;

public class HumanPlayer extends player {

	public HumanPlayer(String name, String color, int row, int col, Board board) {
		super(name, color, row, col);
		setComp(false);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void roomSeenAdd(char c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLocation(int column, int Row) {
		// TODO Auto-generated method stub
		col = column;
		row = Row;
		
	}

	

}
