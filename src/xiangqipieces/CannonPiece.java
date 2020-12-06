package xiangqipieces;

import graphics.ChessBoard;
import graphics.ChessGame.Side;
import piecetypes.HorizontallyMovingPiece;
import piecetypes.SingleDirectionPiece;
import piecetypes.VerticallyMovingPiece;

/**
 * A CannonPiece in XiangQi chess
 * @author phila
 *
 */
public class CannonPiece extends SingleDirectionPiece implements HorizontallyMovingPiece, VerticallyMovingPiece {

	/**
	 * Constructs the CannonPiece
	 * @param chessBoard 		the board that this piece is placed on 
	 * @param side				the player this piece belongs to 
	 * @param icon				the graphics data for displaying this icon
	 */
	public CannonPiece(ChessBoard chessBoard, Side side, Object icon) {
		super(chessBoard, "C", side, icon, SingleDirectionPiece.UNLIMITED_STEPS_IN_DIRECTION); //A cannon piece has unlimited steps 
	}

	/**
	 * Returns true if this is a legal move for a CannonPiece to make
	 * @param toRow 			the row moving to 
	 * @param toColumn 			the column moving to 
	 * @return true if this is a legal move for the piece to make 
	 */
	@Override
	public boolean isLegalMove(int toRow, int toColumn) {
		
		//If the piece is capturing another, ensure it is jumping over exactly one other piece 
		if(getChessBoard().hasPiece(toRow, toColumn) && getChessBoard().getPiece(toRow, toColumn).getSide() != getSide()) {
			
			//Get the distance the piece is moving horizontally
			int horizontalDistance = Math.abs(toColumn - getColumn());

			//Get the distance the piece is moving vertically
			int verticalDistance = Math.abs(toRow - getRow());
			
		    if (horizontalDistance > 0 && verticalDistance == 0)  				//The piece is moving horizontally
				return HorizontallyMovingPiece.piecesInRange(this, toColumn) == 1;
		    else if(horizontalDistance == 0 && verticalDistance > 0) 			//The piece is moving vertically
				return VerticallyMovingPiece.piecesInRange(this, toRow) == 1;													
			
			//Else, this piece is not performing a legal move
			return false;
		} 
		
		//If not capturing, this piece should not skip others 
		return super.isLegalMove(toRow, toColumn);
	}

}
