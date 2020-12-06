package piecetypes;

/**
 * Defines a chess piece that moves horizontally
 * @author Phila Dlamini
 *
 */
public interface HorizontallyMovingPiece {
		
	/**
	 * Returns true if there are no pieces in the horizontal direction in-between the piece's current position and the one moving to
	 * Example: if a piece at (0,0) wants to move to (0,7), the method returns true if there is no piece starting at (0, 1) ending at (0,6)
	 * A piece could still exist at (0,7), but it does not affect the outcome. We only care about the squares in-between. 
	 * 
	 * @param piece			the piece moving in this horizontal direction
	 * @param toColumn 		the column we are moving this piece to
	 * @return true if there are no pieces in the horizontal direction between the piece's current position and the one moving to 
	 */
	public static boolean hasNoPiecesInBetween(ChessPiece piece, int toColumn) {
		
		//The horizontal distance from the current position to the one moving to 
		int horizontalDistance = Math.abs(piece.getColumn() - toColumn);
		
		//Loop from the left to the right horizontally, starting at the next horizontal square 
		int currentColumn = piece.getColumn() < toColumn ? piece.getColumn()  + 1 : toColumn + 1; //Holds the current column
		
		//If any square contains a piece, return false
		for(int i = 0; i < horizontalDistance - 1; i++) { //Controls the number of times to loop
			if (piece.getChessBoard().hasPiece(piece.getRow(), currentColumn)) return false;
			currentColumn++;
		}
		
		//Otherwise, no square contains a piece 
		return true;
	}
	
	/**
	 * Returns the number of pieces between this chess piece and the horizontal square it is moving to 
	 * @param piece			the piece moving in this horizontal direction
	 * @param toColumn 		the column we are moving this piece to
	 * @return the number of pieces in the horizontal direction between the piece's current position and the one moving to 
	 */
	public static int piecesInRange(ChessPiece piece, int toColumn) {
		
		//Keeps track of the pieces encountered so far
		int piecesSoFar = 0;
		
		//The horizontal distance from the current position to the one moving to 
		int horizontalDistance = Math.abs(piece.getColumn() - toColumn);
		
		//Loop from the left to the right horizontally, starting at the next horizontal square 
		int currentColumn = piece.getColumn() < toColumn ? piece.getColumn()  + 1 : toColumn + 1; //Holds the current column
		
		//If any square contains a piece, return false
		for(int i = 0; i < horizontalDistance - 1; i++) { //Controls the number of times to loop
			if (piece.getChessBoard().hasPiece(piece.getRow(), currentColumn)) piecesSoFar++;
			currentColumn++;
		}
		
		//Return the number of pieces 
		return piecesSoFar;
	}
}
