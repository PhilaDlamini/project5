package piecetypes;

/**
 * Defines a piece that moves diagonally
 * @author Phila Dlamini
 *
 */
public interface DiagonallyMovingPiece {

	/**
	 * Returns true if there are no pieces in the diagonal direction between the
	 * piece's current position and the one moving to. We start on the next diagonal square
	 * and end before the one moving to. We do not care about squares ending/starting at
	 * 
	 * @param piece 		the piece moving in this diagonal position
	 * @return true if there are no pieces in the diagonal direction between thepiece's current position and the one moving to
	 */
	public static boolean hasNoPiecesInBetween(ChessPiece piece, int toRow, int toColumn) {

		// Find the diagonal distance: determined by subtracting the columns
		int diagonalDistance = Math.abs(piece.getColumn() - toColumn);

		// Find out whether we are going in the positive-gradient diagonal axis (i.e, from topRight to bottomLeft or bottomLeft to topRight)
		// or not
		boolean onPositiveGradientDiagonalAxis = (piece.getRow() > toRow && piece.getColumn() < toColumn)
				|| piece.getRow() < toRow && piece.getColumn() > toColumn;

		// Holds the current row and column
		int currentColumn;
		int currentRow;

		// If moving on the positive gradient axis, always start from the bottomLeft and move diagonally to the topRight
		if (onPositiveGradientDiagonalAxis) {
			currentColumn = piece.getColumn() < toColumn ? piece.getColumn() + 1 : toColumn + 1; 
		} else {
			// If moving on the negative gradient axis, always start from the bottomRight and move diagonally to the topLeft
			currentColumn = piece.getColumn() > toColumn ? piece.getColumn() - 1 : toColumn - 1; 
		}
		
		currentRow = piece.getRow() > toRow ? piece.getRow() - 1 : toRow - 1;

		// Loop from from bottom to top diagonally, starting at the next diagonal square. If any of the squares have a piece, return false
		// i keeps track of the number of times to loop
		for (int i = 0; i < diagonalDistance - 1; i++) {
			if (piece.getChessBoard().hasPiece(currentRow, currentColumn))
				return false;
			
			//Move on to the next diagonal square
			if (onPositiveGradientDiagonalAxis)
				currentColumn++;
			else
				currentColumn--;

			currentRow--;
		}

		return true;
	}

}
