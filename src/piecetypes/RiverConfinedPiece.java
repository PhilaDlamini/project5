package piecetypes;

import graphics.ChessGame;

/**
 * A piece that cannot cross the river in the game 
 * A RiverConfinedPiece stays below the river on its side
 * @author Phila Dlamini
 *
 */
public interface RiverConfinedPiece {

	/**
	 * Returns true if the row and column this piece is moving to is within the piece's constraints
	 * @return true if the row and column this piece is moving to is within the piece's constraints
	 */
	public static boolean withinConfinement(ChessPiece piece, int toRow, int toColumn) {
				
		//The row below the river
		int rowBelowRiver = (int) (piece.getChessBoard().getGameRules().getNumRows() / 2);
		
		//The row above the river
		int rowAboveRiver = rowBelowRiver - 1;

		//The smallest row number the piece is allowed to move to
		int rowStart = piece.getSide() == ChessGame.Side.NORTH ? 
				0 : rowBelowRiver;
		
		//The biggest row number that the piece is allowed to move to 
		int rowEnd = piece.getSide() == ChessGame.Side.NORTH ? rowAboveRiver : piece.getChessBoard().getGameRules().getNumRows() - 1;
		
		return toRow >= rowStart && toRow <= rowEnd;
	}

}
