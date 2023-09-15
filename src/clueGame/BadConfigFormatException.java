package clueGame;

public class BadConfigFormatException extends Exception {

	public BadConfigFormatException() {
		super("File was not constructed correctly in a way not expected by the program, attempt to ensure all file are correct, if they seem so contact creators at cjohnson@mines.edu / GAZI EMAIL.TXT");
		// TODO Auto-generated constructor stub
	}

	public BadConfigFormatException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
}
