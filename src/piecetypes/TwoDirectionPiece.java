package piecetypes;
import chess.ChessGame;
import chess.ChessGame.Side;
import graphics.ChessBoard;

/**
 * Defines a ChessPiece that may move in two directions at once
 *  A TwoDirectionPiece works on the concept of distance ratio. A distance ratio is
 * defined as the ratio of the greatest steps taken vertically to the smallest
 * steps taken horizontally. For example, if a piece is allowed to move either 2
 * steps vertically and 1 step horizontally, or 2 steps horizontally and 1 step
 * vertically, then it's distance ratio is greatest vertical steps / smallest
 * horizontal steps = 2 / 1 = 2 The reciprocal distance ratio is thus 1 /
 * distanceRatio
 * 
 * By default, a TwoDirectionPiece can skip over other pieces
 * 
 * @author Phila Dlamini
 *
 */
public abstract class TwoDirectionPiece extends ChessPiece {

	// Holds the distance ratio
	private double distanceRatio;

	/**
	 * Constructs a TwoDirectionPiece
	 * 
	 * @param chessBoard the chessBoard this piece is on
	 * @param label      the label for this piece
	 * @param side       the player thing piece belongs to
	 * @param icon       the graphics display data for this piece
	 */
	public TwoDirectionPiece(ChessBoard chessBoard, String label, ChessGame.Side side, Object icon, int distanceRatio) {
		super(chessBoard, label, side, icon);
		this.distanceRatio = distanceRatio;
	}

	/**
	 * Returns true if it is legal to move this piece to the specified row and column
	 * 
	 * @param toRow    the row this piece is being moved to
	 * @param toColumn the column this piece is being moved to
	 */
	@Override
	public boolean isLegalMove(int toRow, int toColumn) {

		// The distance the piece is moving horizontally
		double horizontalDistance = Math.abs(toColumn - getColumn());

		// The distance the piece is moving vertically
		double verticalDistance = Math.abs(toRow - getRow());
		// Represent the distances as double so in case = 0.0, we won't get an
		// ArithmeticException, but instead infinity

		//If the square moving to has a piece of the same side, this move is not legal
		if (getChessBoard().hasPiece(toRow, toColumn)
				&& getChessBoard().getPiece(toRow, toColumn).getSide() == this.getSide())
			return false;

		else {
			// If the path taken matches the defined distance ratio or it's reciprocal, this
			// could be a legal move 
			if (verticalDistance / horizontalDistance == getDistanceRatio()
					|| verticalDistance / horizontalDistance == 1 / getDistanceRatio()) {

				//Ensure the distance ratio came from the smallest numbers possible 
				return horizontalDistance <= getDistanceRatio() && verticalDistance <= getDistanceRatio();
			}

			return false;
		}
	}

	/**
	 * Returns the distance ratio
	 * @return the distance ratio
	 */
	public double getDistanceRatio() {
		return distanceRatio;
	}

	/**
	 * Sets the distance ratio
	 */
	public void setDistanceRatio(int distanceRatio) {
		this.distanceRatio = distanceRatio;
	}
}