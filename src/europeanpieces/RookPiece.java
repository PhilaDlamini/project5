package europeanpieces;
import chess.ChessGame;
import graphics.ChessBoard;
import piecetypes.HorizontallyMovingPiece;
import piecetypes.SingleDirectionPiece;
import piecetypes.VerticallyMovingPiece;

/** A Rook in the game 
 * @author Phila Dlamini
 *
 */
public class RookPiece extends SingleDirectionPiece implements HorizontallyMovingPiece, VerticallyMovingPiece{
	
	/**
	 * Constructs the RookPiece
	 * @param board		the board this piece is on 
	 * @param side		the player that this piece belongs to 
	 * @param icon		the graphics data for the piece
	 */
	public RookPiece(ChessBoard board, ChessGame.Side side, Object icon) {
		super(board, "R", side, icon, SingleDirectionPiece.UNLIMITED_STEPS_IN_DIRECTION);
	}
}
