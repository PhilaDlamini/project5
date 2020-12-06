package xiangqipieces;

import chess.ChessGame;
import chess.ChessGame.Side;
import graphics.ChessBoard;
import piecetypes.ChessPiece;
import piecetypes.HorizontallyMovingPiece;
import piecetypes.SingleDirectionPiece;
import piecetypes.VerticallyMovingPiece;

/**
 * A SolderPiece in XiangQi chess
 * @author phila
 *
 */
public class SoldierPiece extends SingleDirectionPiece implements VerticallyMovingPiece, HorizontallyMovingPiece {

	/**
	 * Constructs the SoldierPiece
	 * @param board			the chess board this piece is on 
	 * @param side			the player this piece belongs to 
	 * @param icon			the graphics display data for the piece 
	 */
	public SoldierPiece(ChessBoard board, Side side, Object icon) {
		super(board, "S", side, icon, 1); //A SoldierPiece moves one step at a time
	}
	
	/**
	 * Returns true if this is a legal move for a SoldierPiece to make
	 * @param toRow 		the row that this piece is moving to 
	 * @param toColumn 		the column that this piece is moving to 
	 * @return true if this is a legal move for a SoldierPiece to make
	 */
	@Override
	public boolean isLegalMove(int toRow, int toColumn) {
		
		// The distance the piece is moving horizontally
		int horizontalDistance = Math.abs(toColumn - getColumn());
		
		//The piece's vertical displacement
		int verticalDisplacement = getSide() == ChessGame.Side.SOUTH ? getRow() - toRow : toRow - getRow();

		//If the piece attempts to move backwards, this move is illegal
		if(verticalDisplacement < 0) 
			return false;
		
		//If the piece is moving horizontally, ensure it is has crossed the river 
		if(horizontalDistance > 0 && verticalDisplacement == 0) {
			if(crossedRiver()) return super.isLegalMove(toRow, toColumn);
			
			//Else, this is illegal 
			return false;
		}
		
		//Else, ensure the piece moves the allowed steps 
		return super.isLegalMove(toRow, toColumn);
	}
	
	/**
	 * Returns true if chess piece is above the river, based on its side and current column and row 
	 * @return true if chess piece is above the river, based on its side and current column and row 
	 */
	private boolean crossedRiver() {
				
		//The row below the river
		int rowBelowRiver = (int) (getChessBoard().getGameRules().getNumRows() / 2);
		
		//The row above the river
		int rowAboveRiver = rowBelowRiver - 1;
		
		return getSide() == ChessGame.Side.SOUTH ? getRow() <= rowAboveRiver :
			getRow() >= rowBelowRiver;
	}

}
