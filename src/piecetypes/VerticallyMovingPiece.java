package piecetypes;

import graphics.ChessBoard;

/**
 * Defines a piece that moves vertically
 * @author Phila Dlamini
 *
 */
public interface VerticallyMovingPiece { 

	/**
	 * Returns true if there are no pieces in the vertical direction between the piece's current position and the one moving to 
	 * We start at the next vertical position and end right before the one moving to 
	 * @param piece		the piece moving in this vertical position
	 * @param toRow 	the row the piece is moving to
	 * @return true if there are pieces in the vertical direction between the piece's current position and the one moving to 
	 */
	public static boolean hasNoPiecesInBetween(ChessPiece piece, int toRow) {
		
		//The vertical distance from the current position to the intended position
		int verticalDistance = Math.abs(piece.getRow() - toRow);
		
		//Loop from the bottom to the top vertically, starting at the next vertical square 
		int currentRow = piece.getRow() > toRow ? piece.getRow()  - 1 : toRow  - 1; //Holds the current row 
		
		//If any square contains a piece, return false
		for(int i = 0; i < verticalDistance - 1; i++) { //Controls the number of times to loop
			if (piece.getChessBoard().hasPiece(currentRow, piece.getColumn())) return false;
			currentRow--;
		}
		
		//Otherwise, no square contains a piece
		return true;
	}
	
	/**
	 * Returns the number of pieces between this chess piece and the vertical square it is moving to 
	 * @param piece			the piece moving in this vertical direction
	 * @param toRow 		the row we are moving this piece to
	 * @return the number of pieces in the vertical direction between the piece's current position and the one moving to 
	 */
	public static int piecesInRange(ChessPiece piece, int toRow) {
		
		//The start row 
		int startRow = piece.getRow() < toRow ? piece.getRow() + 1 : toRow  + 1;
		
		//The end row
		int endRow = startRow + Math.abs(piece.getRow() - toRow) - 2;
		
		return piecesInRange(piece.getChessBoard(), piece.getColumn(), startRow, endRow);
	}
	
	/**
	 * Returns the number of pieces on this column, from the start row to end end row, inclusive
	 * @param board 		the board the pieces are on 
	 * @param column		the column to search on 
	 * @param startRow		the row to start searching on 
	 * @param endRow		the row to end searching on 
	 * @return	the number of pieces in found in on this column given the start and end rows 
	 */
	public static int piecesInRange(ChessBoard board, int column, int startRow, int endRow) {
		
		//The number of pieces encountered so far
		int piecesSoFar = 0;
		
		//If any square contains a piece, return false
		for(int i = startRow; i <= endRow; i++) { 
			if (board.hasPiece(i, column)) piecesSoFar++;
		}
		
		//Return the number of pieces
		return piecesSoFar;
	}
}
