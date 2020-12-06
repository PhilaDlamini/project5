package europeanpieces;
import chess.ChessGame;
import graphics.ChessBoard;
import piecetypes.DiagonallyMovingPiece;
import piecetypes.HorizontallyMovingPiece;
import piecetypes.SingleDirectionPiece;
import piecetypes.VerticallyMovingPiece;


/**
 * The Queen in the game
 * 
 * @author Phila Dlamini
 *
 */
public class QueenPiece extends SingleDirectionPiece
		implements HorizontallyMovingPiece, VerticallyMovingPiece, DiagonallyMovingPiece {

	/**
	 * Constructs a QueenPiece instance
	 * @param side          the side the Queen piece is on
	 * @param icon          the graphics data for the queen
	 * @param row           the row the queen is placed on
	 * @param column        the column the queen is place on
	 * @param numberOfMoves
	 */
	public QueenPiece(ChessBoard chessBoard, ChessGame.Side side, Object icon) {
		super(chessBoard, "Q", side, icon, SingleDirectionPiece.UNLIMITED_STEPS_IN_DIRECTION);
	}

}

