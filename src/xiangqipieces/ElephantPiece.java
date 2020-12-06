package xiangqipieces;
import graphics.ChessBoard;
import graphics.ChessGame;
import piecetypes.DiagonallyMovingPiece;
import piecetypes.FortressConfinedPiece;
import piecetypes.RiverConfinedPiece;
import piecetypes.SingleDirectionPiece;

/**
 * The Elephant piece in XiangQi chess 
 * @author Phila Dlamini
 *
 */
public class ElephantPiece extends SingleDirectionPiece implements DiagonallyMovingPiece, RiverConfinedPiece {

	/**
	 * Constructs an ElephantPiece 
	 * @param board		the board this piece is played on 
	 * @param side		the player this piece belongs to 
	 * @param icon		the graphics data for displaying this piece
	 */
	public ElephantPiece(ChessBoard board, ChessGame.Side side, Object icon) {
		super(board, "E", side, icon, 2); //The piece should move two steps at time
	}
	
	/**
	 * Returns true if moving to this row and column is legal for this piece
	 * @param toRow		the row the piece is moving to 
	 * @param toColumn	the column the piece is moving to 
	 * @return	true if it is legal for this piece to move to this row and column
	 */
	@Override
	public boolean isLegalMove(int toRow, int toColumn) {
		
		//If the piece did not take exactly two diagonal steps, this move is not legal
		if(Math.abs(toColumn - getColumn()) != getNumberOfAllowedSteps()) return false;
	
		//Otherwise, ensure the piece is performing a legal move
		return super.isLegalMove(toRow, toColumn);
	}
	
}