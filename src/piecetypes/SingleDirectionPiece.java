package piecetypes;
import chess.ChessGame;
import chess.ChessGame.Side;
import graphics.ChessBoard;

/**
 * Defines a ChessPiece that can only move in one direction at once i.e horizontally, vertically and diagonally
 * By default, a SingleDirectionPiece does not skip pieces. i.e. It cannot not move from point A to B 
 * if there is a chess piece between A and B
 * A SingleDirectionPiece, by default, can move a number of steps equal to or less than the allowedStepsInDirection
 * @author Phila Dlamini
 *
 */
public abstract class SingleDirectionPiece extends ChessPiece {

	// The number of steps this piece is allowed to take in the direction
	private int allowedStepsInDirection;

	// An unlimited number of steps in the direction
	public final static int UNLIMITED_STEPS_IN_DIRECTION = -1;

	/**
	 * Creates a SingleDirectionPiece
	 * 
	 * @param chessBoard the chess board this piece is on
	 * @param label      the label for this piece
	 * @param side       the "player" this piece belongs to
	 * @param icon       the graphics data for the piece
	 * @param row        the row this piece is on
	 * @param column     the column this piece is on
	 */
	public SingleDirectionPiece(ChessBoard chessBoard, String label, ChessGame.Side side, Object icon, int allowedStepsInDirection) {
		super(chessBoard, label, side, icon);
		this.allowedStepsInDirection = allowedStepsInDirection;
	}

	/**
	 * Returns true if it is legal to move this piece to the row and column
	 * 
	 * @param toRow    the row this piece is being moved to
	 * @param toColumn the column this piece is being moved to
	 */
	@Override
	public boolean isLegalMove(int toRow, int toColumn) {

		// The distance the piece is moving horizontally
		int horizontalDistance = Math.abs(toColumn - getColumn());

		// The distance the piece is moving vertically
		int verticalDistance = Math.abs(toRow - getRow());
		
		//Make sure that confined pieces remain in their constraints 
		if(this instanceof FortressConfinedPiece 
				&& !FortressConfinedPiece.withinConfinement(this, toRow, toColumn)) return false;
		
		if(this instanceof RiverConfinedPiece
				&& !RiverConfinedPiece.withinConfinement(this, toRow, toColumn)) return false;
		

		// If there is already a piece at the location that belongs to this player, this move is not allowed
		if (getChessBoard().hasPiece(toRow, toColumn)
				&& getChessBoard().getPiece(toRow, toColumn).getSide() == this.getSide())
			return false;

		//Else, this move could be allowed
		else {

			//Check which directions the piece is moving in 
			if (horizontalDistance > 0 && verticalDistance == 0) { // The piece is moving horizontally
				
				// This is a legal move if the piece is allowed to move horizontally, and it has
				// not moved more than the number of allowed steps
				if (this instanceof HorizontallyMovingPiece && movedAllowedSteps(horizontalDistance)) {
					
					// This move is legal if there are no pieces on a square in-between the start and
					// end positions
					return HorizontallyMovingPiece.hasNoPiecesInBetween(this, toColumn);
				}

				// Else, this piece is not allowed to move horizontally
				return false;

			} else if (horizontalDistance == 0 && verticalDistance > 0) { // The piece is only moving vertically

				// This is a legal move if the piece is allowed to move vertically, and it has
				// not moved more than the number of allowed steps
				if (this instanceof VerticallyMovingPiece && movedAllowedSteps(verticalDistance)) {

					// This move is legal if there are no pieces on a square in-between the start and
					// end positions
					return VerticallyMovingPiece.hasNoPiecesInBetween(this, toRow);
				
				}

				// Else, this piece is not allowed to move horizontally
				return false;

			} else if (horizontalDistance == verticalDistance && horizontalDistance >= 0) { // The piece is moving
																							// diagonally

				// This is a legal move if the piece is allowed to move diagonally, and it has
				// moved the number of allowed spaces
				// We use horizontalDistance for the diagonal steps because they are the same
				if (this instanceof DiagonallyMovingPiece && movedAllowedSteps(horizontalDistance)) {
					
					// This move is legal if there are no pieces on a square in-between the start and
					// end positions
					return DiagonallyMovingPiece.hasNoPiecesInBetween(this, toRow, toColumn);
				}

				// Else, this piece is not allowed to move diagonally
				return false;
			}

			// Else, the piece is moving in two directions at once. A SingleDirectionPiece is not allowed to do this
			return false;
		}

	}
	
	/**
	 * Returns true if the number of steps taken is within the range allowed, or unlimited
	 * @param stepsTaken	the number of steps the piece has moved
	 * @return true if the number of steps taken in within the range allowed, or unlimited
	 */
	private boolean movedAllowedSteps(int stepsTaken) {
		if (getNumberOfAllowedSteps() == SingleDirectionPiece.UNLIMITED_STEPS_IN_DIRECTION) return true;
		return stepsTaken <= getNumberOfAllowedSteps();
	}

	/**
	 * Returns the number of steps this piece is allowed to take in the direction
	 * @return the number of steps this piece is allowed to take in the direction
	 */
	public int getNumberOfAllowedSteps() {
		return allowedStepsInDirection;
	}

	
	/**
	 * Sets the number of allowed steps in a direction that this piece is allowed to take
	 */
	public void setAllowedStepsInDirection(int steps) {
		this.allowedStepsInDirection = steps;
	}
}
