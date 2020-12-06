package graphics;
import java.awt.Color;
import javax.swing.Icon;
import javax.swing.JButton;
import piecetypes.ChessPiece;

/**
 * The Swing display information for a European chess game
 * @author Phila Dlamini
 *
 */
public class SwingEuropeanChessDisplay implements SwingChessBoardDisplay{
	
	//The color of the NORTH player
	private final Color northPlayerColor = Color.cyan;
		
	//The color of the SOUTH player
	private final Color southPlayerColor = Color.green;
		
	//The color of the EAST player
	private final Color eastPlayerColor = Color.pink;
		
	//The color of the WEST player
	private final Color westPlayerColor = Color.green;
	
	//The highlight color 
	private final Color highlightColor = Color.yellow;

	/**
	 * Displays a square that has no piece on it 
	 * @param button 		the button that is this square 
	 * @param row			the row of the button	
	 * @param column 		the column of this button
	 */
	@Override
	public void displayEmptySquare(JButton button, int row, int column) {
		button.setBackground((row + column) % 2 == 0 ? Color.white : Color.gray); //Alternate square colors
		button.setText(null);
		button.setIcon(null);
	}

	/**
	 * Displays a square that has a piece on it
	 * @param button 		the button that is this square
	 * @param column 		the column of this square
	 * @param row 			the row of this square
	 * @param piece 		the piece on this square
	 * 
	 */
	@Override
	public void displayFilledSquare(JButton button, int row, int column, ChessPiece piece) {
		
		//Each square's background is the color of that player's side
		Color squareColor;
		
		switch(piece.getSide()) {
		
		case NORTH:
			squareColor = northPlayerColor;
			break;
			
		case SOUTH:
			squareColor = southPlayerColor;
			break;
			
		case WEST:
			squareColor = westPlayerColor;
			break;
			
		case EAST:
			squareColor = eastPlayerColor;
			break;
			
		default:
			squareColor = southPlayerColor;

		}
		
		button.setBackground(squareColor);
		button.setText(piece.getLabel());
		button.setIcon((Icon)piece.getIcon());
	}

	/**
	 *  Highlights the square on the board.
	 *  @param highlight  whether or not this square should be highlighted
	 *  @param button     the button that is the square
	 *  @param row        the row of this square
	 *  @param column     the column of this square
	 *  @param piece      the piece (if any) that is on this square
	 */  
	public void highlightSquare(boolean highlight, JButton button, int row, int column, ChessPiece piece) {
		if(highlight) 
			button.setBackground(highlightColor);
		else if (piece == null) 
			displayEmptySquare(button, row, column);
		else 
			displayFilledSquare(button, row, column, piece);
	}

}
