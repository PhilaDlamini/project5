package europeanpieces;
import graphics.ChessBoard;
import graphics.ChessGame;
import piecetypes.DiagonallyMovingPiece;
import piecetypes.HorizontallyMovingPiece;
import piecetypes.SingleDirectionPiece;
import piecetypes.VerticallyMovingPiece;

/**
 * A King in the game
 * @author Phila Dlamini
 *
 */
public class KingPiece extends SingleDirectionPiece implements HorizontallyMovingPiece, VerticallyMovingPiece, DiagonallyMovingPiece {

	/**
	 * Constructs the KingPiece
	 * @param board 		the chessBoard this King is on
	 * @param side			the player this king belongs to
	 * @param icon			the graphics data for displaying the King
	 */
	public KingPiece(ChessBoard board, ChessGame.Side side, Object icon) {
		super(board, "K", side, icon, 1); //The King piece mostly moves one step at a time
	}
	
	/**
	 * Returns true if this is a legal move for the King to make
	 * The King moves one space in any direction, except in the castle move,
	 * 
	 * @param toRow 		the row the King is moving to 
	 * @param toColum 		the column the King is moving to
	 */
	@Override
	public boolean isLegalMove(int toRow, int toColumn) {
		
		//If this is a castle move, it is a legal move for a King to make
		if(isCastleMove()) {
			return true;
		}
		
		return super.isLegalMove(toRow, toColumn);
	}
	
	/**
	 * Returns true if this is a castle move for the king to make 
	 * A castle move is legal if the king has never moved, there is a rook belonging to the same player in the corner 
	 * the king is moving toward and the rook has never moved, 
	 * there are no pieces between the king and the rook, 
	 * and none of the squares between the king and the rook are threatened
	 * @return
	 */
	private boolean isCastleMove() {
		return false;
		//TODO: Implement method 
	}
}
