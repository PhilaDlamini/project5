package piecetypes;
import graphics.ChessGame;
import graphics.ChessGame.Side;

/**
 * A piece that is constrained to the fortress in the game 
 * A FortressConfinedPiece cannot leave the fortress: the center three columns and bottom three rows, or
 * the center three columns and the top three rows.  
 * @author <em> Phila Dlamini </em>
 *
 */
public interface FortressConfinedPiece {
	
	/**
	 * Returns true if the row and column this piece is moving to is within the piece's constraints
	 * @return true if the row and column this piece is moving to is within the piece's constraints
	 */
	public static boolean withinConfinement(ChessPiece piece, int toRow, int toColumn) {
		return isInFortress(piece.getChessBoard().getGameRules(), piece.getSide(), toRow, toColumn);
	}
	
	/**
	 * Returns true if the square at the specified row and column is within the fortress 
	 * @param chessGame		the chess game being played
	 * @param side 			which fortress
	 * @param row			the row of the square
	 * @param column 		the column of the square 
	 * @return true if the specified row and column is within the fortress 
	 */
	public static boolean isInFortress(ChessGame chessGame, ChessGame.Side side, int row, int column) {

		//The lower row bound of the fortress
		int rowStart = side == ChessGame.Side.NORTH ? 0 : chessGame.getNumRows() - 3;	
		
		//The upper row bound of the fortress
		int rowEnd = side == ChessGame.Side.NORTH ? 2 : chessGame.getNumRows() - 1;
		
		//The lower column bound of the fortress 
		int columnStart = ((int) (chessGame.getNumColumns() / 2)) - 1;
				
		//The upper column bound of the fortress
		int columnEnd = columnStart + 2;
		
		return (row >= rowStart && row <= rowEnd) 
				&& (column >= columnStart && column <= columnEnd);
	}
}
