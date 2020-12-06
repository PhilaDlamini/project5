package graphics;
import piecetypes.ChessPiece;
import javafx.scene.control.Button;

/**
 * Indicates how the JavaFXChessBoard should display its squares
 * @author Phila Dlamini
 *
 */
public interface JavaFXChessBoardDisplay {

	/**
	 * Displays the empty square at the specified row and column
	 * @param button 		the square to display
	 * @param row			the row of the square
	 * @param column		the column of the square
	 */
	void displayEmptySquare(Button button, int row, int column);
	
	/**
	 * Displays the square at the specified row and column with the chess piece on it 
	 * @param button 		the square to display
	 * @param row			the row of the square
	 * @param column		the column of the square
	 * @param piece 		the piece on this square
	 */
	void displayFilledSquare(Button button, int row, int column, ChessPiece piece);
	
	/**
	 * Adds or removes a highlight from a the specified square
	 * @param highlight		whether to highlight this square or not
	 * @param button		the square to add/remove highlight from
	 * @param row			the row of the square on the board
	 * @param column		the column of the square on the board
	 * @param piece			the piece on the square
	 */
	void highlightSquare(boolean highlight, Button button, int row, int column, ChessPiece piece);
}
