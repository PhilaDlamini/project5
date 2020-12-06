package xiangqipieces;

import graphics.ChessBoard;
import graphics.ChessGame.Side;
import piecetypes.TwoDirectionPiece;

/**
 * The HorsePiece in XiangQi chess
 * @author Phila Dlamini
 *
 */
public class HorsePiece extends TwoDirectionPiece{

	/**
	 * Constructs the HorsePiece
	 * @param chessBoard
	 * @param label
	 * @param side
	 * @param icon
	 * @param distanceRatio
	 */
	public HorsePiece(ChessBoard chessBoard, Side side, Object icon) {
		super(chessBoard, "H", side, icon, 2); //A HorsePiece has a distance ratio of two
	}
	
	/**
	 * Returns true if this is a legal move for a HorsePiece to make 
	 * @param toRow 	the row moving to 
	 * @param toColumn  the column moving to 
	 * @return true if this is a legal move for the piece to make
	 */
	@Override
	public boolean isLegalMove(int toRow, int toColumn) {
		
		//Check if the piece is moving the allowed distance ratio
		if(super.isLegalMove(toRow, toColumn)) {
			
			/* If it is, ensure that the first square does not have a piece on it */
			if (toColumn - getColumn() == 2) 										//The square to check is to the right
				return !getChessBoard().hasPiece(getRow(), getColumn() + 1);
			else if (getColumn() - toColumn == 2)									//The square to check is to the left
				return !getChessBoard().hasPiece(getRow(), getColumn() - 1);
			else if (toRow - getRow() == 2) 										//The square to check is down
				return !getChessBoard().hasPiece(getRow() + 1, getColumn());
			else 																	//The square to check is up
				return !getChessBoard().hasPiece(getRow() - 1, getColumn());
		}
		
		return false;
	}

}
