package chess;

import europeanpieces.BishopPiece;
import europeanpieces.KingPiece;
import europeanpieces.KnightPiece;
import europeanpieces.PawnPiece;
import europeanpieces.QueenPiece;
import europeanpieces.RookPiece;
import graphics.ChessBoard;
import graphics.SwingChessBoard;
import graphics.SwingEuropeanChessDisplay;
import piecetypes.ChessPiece;

/**
 * A European chess game
 * 
 * @author Phila Dlamini
 *
 */
public class EuropeanChess implements ChessGame {

	//Whose turn it is to play
	private ChessGame.Side currentTurn;

	/**
	 * Constructs the EuroopeanChess
	 */
	public EuropeanChess() {
		currentTurn = ChessGame.Side.SOUTH; // by default, the south player starts first ** what if EAST-WEST
	}

	/**
	 * Returns true if the user can change their piece selection once selected
	 * @param piece the piece the user selected
	 */
	@Override
	public boolean canChangeSelection(ChessPiece piece) {
		return true; // by default, users are allowed to change selection
	}

	/**
	 * Returns the number of rows of the board
	 * @return the number of rows of the board
	 */
	@Override
	public int getNumRows() {
		return 8;
	}

	/**
	 * Returns the number of columns of the board
	 * @return the number of columns of the board
	 */
	@Override
	public int getNumColumns() {
		return 8;
	}
	
	/**
	 * Returns whose turn it is 
	 * @return whose turn it is to play 
	 */
	@Override
	public ChessGame.Side getCurrentTurn() {
		return currentTurn;
	}
	
	/**
	 * Sets whose turn it is to play
	 */
	@Override
	public void setCurrentTurn(ChessGame.Side currentTurn) {
		this.currentTurn = currentTurn;
	}

	/**
	 * Places all chess pieces on the given board and starts the game
	 * @param board the board to places the pieces on
	 */
	@Override
	public void startGame(ChessBoard board) {
		
		// Loop from start to the middle column and add pieces on the board
		for (int column = 0; column < board.getGameRules().getNumColumns() / 2; column++) {

			// Add pawns on both sides
			board.addPiece(new PawnPiece(board, ChessGame.Side.NORTH, null), 1, column);
			board.addPiece(new PawnPiece(board, ChessGame.Side.NORTH, null), 1, column + 4);
			board.addPiece(new PawnPiece(board, ChessGame.Side.SOUTH, null), 6, column);
			board.addPiece(new PawnPiece(board, ChessGame.Side.SOUTH, null), 6, column + 4);

			// Add all other pieces
			if (column == 0) { //Add rook pieces on the corners
				board.addPiece(new RookPiece(board, ChessGame.Side.NORTH, null), 0, column);
				board.addPiece(new RookPiece(board, ChessGame.Side.SOUTH, null), 7, column);
				board.addPiece(new RookPiece(board, ChessGame.Side.NORTH, null), 0, 7 - column);
				board.addPiece(new RookPiece(board, ChessGame.Side.SOUTH, null), 7, 7 - column);
			} else if (column == 1) { //Add knight pieces after rook pieces
				board.addPiece(new KnightPiece(board, ChessGame.Side.NORTH, null), 0, column);
				board.addPiece(new KnightPiece(board, ChessGame.Side.SOUTH, null), 7, column);
				board.addPiece(new KnightPiece(board, ChessGame.Side.NORTH, null), 0, 7 - column);
				board.addPiece(new KnightPiece(board, ChessGame.Side.SOUTH, null), 7, 7 - column);
			} else if (column == 2) { //Add bishop pieces after the knight pieces
				board.addPiece(new BishopPiece(board, ChessGame.Side.NORTH, null), 0, column);
				board.addPiece(new BishopPiece(board, ChessGame.Side.SOUTH, null), 7, column);
				board.addPiece(new BishopPiece(board, ChessGame.Side.NORTH, null), 0, 7 - column);
				board.addPiece(new BishopPiece(board, ChessGame.Side.SOUTH, null), 7, 7 - column);
			} else if (column == 3) {//add the king and queen after the night pieces
				board.addPiece(new KingPiece(board, ChessGame.Side.NORTH, null), 0, column);
				board.addPiece(new KingPiece(board, ChessGame.Side.SOUTH, null), 7, column);
				board.addPiece(new QueenPiece(board, ChessGame.Side.NORTH, null), 0, 7 - column);
				board.addPiece(new QueenPiece(board, ChessGame.Side.SOUTH, null), 7, 7 - column);
			}

		}

	}


}
