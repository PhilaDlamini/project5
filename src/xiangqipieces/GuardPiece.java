package xiangqipieces;
import chess.ChessGame;
import graphics.ChessBoard;
import piecetypes.FortressConfinedPiece;
import piecetypes.DiagonallyMovingPiece;
import piecetypes.SingleDirectionPiece;

/**
 * The GuardPiece in XiangQi Chess
 * @author Phila Dlamini
 *
 */
public class GuardPiece extends SingleDirectionPiece implements DiagonallyMovingPiece, FortressConfinedPiece{

	/**
	 * Constructs the GuardPiece
	 * @param chessBoard	the chess board this piece is on 
	 * @param side			the player this piece belongs to 
	 * @param icon			the graphics info for displaying this piece 
	 */
	public GuardPiece(ChessBoard chessBoard, ChessGame.Side side, Object icon) {
		super(chessBoard, "G", side, icon, 1); //The piece moves one step at a time
	}
}
