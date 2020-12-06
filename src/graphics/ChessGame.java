package graphics;
import piecetypes.ChessPiece;

/**
 * Rules for how a version of chess should be played
 * @author Phila Dlamini
 */

public interface ChessGame {
	
	/**
	 * The players in the game (uses campus points around the board)
	 */
	public enum Side {NORTH, SOUTH, EAST, WEST};
	
	/**
	 * Returns true if the user can change their piece selection once they have selected a piece
	 * @param piece  the piece the user selected
	 */
	public boolean canChangeSelection(ChessPiece piece);
	
	/**
	 * Returns the number of rows in the game 
	 * @return the number of rows in the game 
	 */
	public int getNumRows();
	
	/**
	 * Returns the number of columns in the game 
	 * @return the number of columns in the game 
	 */
	public int getNumColumns();
	
	/**
	 * Places all chess pieces on the given board and the game can begin 
	 * @param board the board to places the pieces on
	 */
	void startGame(ChessBoard board);
	
	/**
	 * Returns whose turn it is to play
	 * @return whose turn it is to play
	 */
	public ChessGame.Side getCurrentTurn();
	
	/**
	 * Sets whose turn it is to play 
	 * @param currentTurn whose turn it is to play
	 */
	public void setCurrentTurn(ChessGame.Side currentTurn);
	
	/**
	 * Returns true if it is this player's turn to play
	 * @param piece  the piece to be played
	 * @param row    the row of the square the piece is on
	 * @param column the column of the square the piece is on
	 * @return true if the piece is allowed to move on this turn
	 */
	public default boolean legalPieceToPlay(ChessPiece piece, int row, int column) {
		return piece.getSide() == getCurrentTurn();
	}

	/**
	 * Moves the piece to the specified row and column, if this is a legal move
	 * @param piece    the piece to move
	 * @param toRow    the row to move to
	 * @param toColumn the column to move to
	 * @return true if the move was made, false if the move was not made
	 */
	public default boolean makeMove(ChessPiece piece, int toRow, int toColumn) {

		// The chess board this piece is on
		ChessBoard board = piece.getChessBoard();

		// If this is a legal move for the piece, either the landing square is empty or
		// it contains an opponent piece
		if (legalPieceToPlay(piece, toRow, toColumn) && piece.isLegalMove(toRow, toColumn)) {

			// Remove the piece from its current position
			board.removePiece(piece.getRow(), piece.getColumn());

			// And put it where it's moving
			board.addPiece(piece, toRow, toColumn);
			/* If there was a piece on the landing square, then it is removed from the game*/

			// Handle post-move processing
			piece.moveDone();

			// Change whose turn it is to play
			setCurrentTurn(ChessGame.toggleTurn(getCurrentTurn()));

			// The move was made
			return true;
		}

		// Else, this move must have been illegal
		return false;
	}
	
	/**
	 * Toggles whose turn it is to play
	 * @param currentTurn	the player whose current turn it is
	 * @return the whose turn it is now
	 */
	public static ChessGame.Side toggleTurn(ChessGame.Side currentTurn) {
		switch(currentTurn) {
		
		case SOUTH:
			return ChessGame.Side.NORTH;
			
		case NORTH:
			return ChessGame.Side.SOUTH;
			
		case EAST:
			return ChessGame.Side.WEST;
			
		case WEST:
			return ChessGame.Side.EAST;
		
		default:
			return ChessGame.Side.SOUTH;
			
		}
		
	}
}