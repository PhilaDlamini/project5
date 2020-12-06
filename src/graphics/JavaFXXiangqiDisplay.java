package graphics;
import chesstype.Xiangqi;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import piecetypes.ChessPiece;
import piecetypes.FortressConfinedPiece;

/**
 * The JavaFX display information for a Xiangqi chess game
 * @author Phila Dlamini
 *
 */

//Review class
public class JavaFXXiangqiDisplay implements JavaFXChessBoardDisplay{
	
	//The primary color of the board
	private final Color primaryDark = Color.DARKGRAY;
	
	//The primary light color for the board 
	private final Color primaryLight = Color.LIGHTGRAY;
	
	//The south side player color
	private final Color southPlayerColor = Color.TAN;
	
	//The north side player color 
	private final Color northPlayerColor = Color.TURQUOISE;

	//The west side player color
	private final Color westPlayerColor = Color.PERU;
	
	//The east side player color
	private final Color eastPlayerColor = Color.MOCCASIN;

	//The highlight color 
	private final Color highlightColor = Color.HOTPINK;
	
	/**
	 * Displays the empty square at the specified row and column
	 * @param button 		the square to display
	 * @param row			the row of the square
	 * @param column		the column of the square
	 */
	@Override
	public void displayEmptySquare(Button button, int row, int column) {
		BackgroundFill fill = new BackgroundFill(getBackgroundColor(row, column), CornerRadii.EMPTY, new Insets(1));
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
		
		//The square's background color
		BackgroundFill backgroundFill  = new BackgroundFill(getBackgroundColor(row, column), CornerRadii.EMPTY, new Insets(1));
		
		//The piece's  color
		BackgroundFill pieceColor = new BackgroundFill(color, new CornerRadii(button.getWidth() / 2), new Insets(5));
		button.setBackground(new Background(backgroundFill, pieceColor));  
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
		
		//The square's background color
		BackgroundFill backgroundFill = new BackgroundFill(getBackgroundColor(row, column), CornerRadii.EMPTY, Insets.EMPTY);
		
		//The  piece's color
		BackgroundFill pieceColor = new BackgroundFill(highlightColor, new CornerRadii(button.getWidth() / 2), new Insets(5));
		
		if(highlight) 
			button.setBackground(new Background(backgroundFill, pieceColor));
		else if (piece == null) 
			displayEmptySquare(button, row, column);
		else 
			displayFilledSquare(button, row, column, piece);
	}
	
	/**
	 * Returns either the light or dark background for the square at the row and column,
	 * depending on whether it is in the fortress or not
	 * @return
	 */
	private Color getBackgroundColor(int row, int column) {
	
		//The chess game => provides information about the number of rows and columns
		Xiangqi xiangqi = new Xiangqi();
		
		//The background color for this square
		Color backgroundColor;
		
		//Use the dark color for the fortress and the light color for the rest
		if(FortressConfinedPiece.isInFortress(xiangqi, ChessGame.Side.SOUTH, row, column) || 
				FortressConfinedPiece.isInFortress(xiangqi, ChessGame.Side.NORTH, row, column))
			backgroundColor = primaryDark;
		else
			backgroundColor = primaryLight;
		
		return backgroundColor;
	}
}
