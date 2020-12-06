package europeanpieces;

import graphics.ChessBoard;
import graphics.ChessGame;
import piecetypes.TwoDirectionPiece;

/**
 * A Knight in the game
 * @author Phila Dlamini
 *
 */
public class KnightPiece extends TwoDirectionPiece {
	
	/**
	 * Constructs a KingtPiece 
	 * @param board 	the chess board this piece is on
	 * @param side		the player this piece belongs to 
	 * @param icon		the graphics data for displaying this piece
	 */
	public KnightPiece(ChessBoard board, ChessGame.Side side, Object icon) {
		super(board, "N", side, icon, 2); //The distance ratio for a KnightPiece is 2 
	}
}

