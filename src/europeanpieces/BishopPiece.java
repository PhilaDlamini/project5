package europeanpieces;
import piecetypes.DiagonallyMovingPiece;
import piecetypes.SingleDirectionPiece;
import graphics.ChessBoard;
import graphics.ChessGame;

/**
 * The BishopPiece in the game 
 * @author Phila Dlamini
 *
 */
public class BishopPiece extends SingleDirectionPiece implements DiagonallyMovingPiece{
	
	/**
	 * Constructs the BishopPiece
	 * @param board		the board this piece is on
	 * @param side		the player this piece belongs to 
	 * @param icon		the graphics data for displaying this piece 
	 */
	public BishopPiece(ChessBoard board, ChessGame.Side side, Object icon) {
		super(board, "B", side, icon, SingleDirectionPiece.UNLIMITED_STEPS_IN_DIRECTION);
	}

}
