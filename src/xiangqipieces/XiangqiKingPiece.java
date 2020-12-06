package xiangqipieces;

import chess.ChessGame;
import graphics.ChessBoard;
import piecetypes.FortressConfinedPiece;
import piecetypes.HorizontallyMovingPiece;
import piecetypes.SingleDirectionPiece;
import piecetypes.VerticallyMovingPiece;

/**
 * The king piece in XiangQi chess
 * @author Phila Dlamini
 *
 */
public class XiangqiKingPiece extends SingleDirectionPiece implements HorizontallyMovingPiece, VerticallyMovingPiece, FortressConfinedPiece {
	
	/**
	 * Constructs the XiangqiKingPiece
	 * @param board		the board this piece is played on 
	 * @param side		the player this piece belongs to 
	 * @param icon		the graphics data for displaying this piece
	 */
	public XiangqiKingPiece(ChessBoard board, ChessGame.Side side, Object icon) {
		super(board, "X", side, icon, 1); //The piece is allowed to move only one step at time
	}
}
