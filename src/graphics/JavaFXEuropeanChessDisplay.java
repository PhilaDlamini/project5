package graphics;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import piecetypes.ChessPiece;

/**
 * The JavaFX display for a European chess game
 * @author Phila Dlamini
 *
 */

//Review class
public class JavaFXEuropeanChessDisplay implements JavaFXChessBoardDisplay {
	
	//The primary dark color of the board
	private final Color primaryDark = Color.web("#A1887F");
	
	//The primary light color for the board 
	private final Color primaryLight = Color.FLORALWHITE;
	
	//The south side player color
	private final Color southPlayerColor = Color.ORCHID;
	
	//The north side player color 
	private final Color northPlayerColor = Color.CYAN;

	//The west side player color
	private final Color westPlayerColor = Color.PERU;
	
	//The east side player color
	private final Color eastPlayerColor = Color.MOCCASIN;

	//The highlight color 
	private final Color highlightColor = Color.YELLOW;

	
	/**
	 * Displays the empty square at the specified row and column
	 * @param button 		the square to display
	 * @param row			the row of the square
	 * @param column		the column of the square
	 */
	@Override
	public void displayEmptySquare(Button button, int row, int column) {
		BackgroundFill fill = new BackgroundFill((column + row) % 2 == 0 ? primaryDark : primaryLight, CornerRadii.EMPTY, Insets.EMPTY);
		button.setBackground(new Background(fill));
		button.setText(null);
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
	public void displayFilledSquare(Button button, int row, int column, ChessPiece piece) {

		//Use the player's color to display the square 
		Color color;
		
		switch(piece.getSide()) {
		
		case NORTH:
			color = northPlayerColor;
			break;
			
		case SOUTH:
			color = southPlayerColor;
			break;
			
		case WEST:
			color = westPlayerColor;
			break;
			
		case EAST:
			color = eastPlayerColor;
			break;
			
			default:
				color = southPlayerColor;
		}
		
		BackgroundFill background = new BackgroundFill((column + row) % 2 == 0 ? primaryDark : primaryLight, CornerRadii.EMPTY, Insets.EMPTY);
		BackgroundFill pieceColor = new BackgroundFill(color, new CornerRadii(button.getWidth() / 2), new Insets(5));
		button.setBackground(new Background(background, pieceColor));
		button.setText(piece.getLabel());
	}
	
	/**
	 *  Highlights the square on the board.
	 *  @param highlight  whether or not this square should be highlighted
	 *  @param button     the button that is the square
	 *  @param row        the row of this square
	 *  @param column     the column of this square
	 *  @param piece      the piece (if any) that is on this square
	 */ 
	@Override
	public void highlightSquare(boolean highlight, Button button, int row, int column, ChessPiece piece) {
		BackgroundFill background = new BackgroundFill((column + row) % 2 == 0 ? primaryDark : primaryLight, CornerRadii.EMPTY, Insets.EMPTY);
		BackgroundFill pieceColor = new BackgroundFill(highlightColor, new CornerRadii(button.getWidth() / 2), new Insets(5));
		if(highlight) 
			button.setBackground(new Background(background, pieceColor));
		else if (piece == null) 
			displayEmptySquare(button, row, column);
		else 
			displayFilledSquare(button, row, column, piece);
	}
}
