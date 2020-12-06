package chesstype;

import java.util.LinkedHashMap;
import java.util.Map;

import europeanpieces.RookPiece;
import graphics.ChessBoard;
import graphics.ChessGame;
import piecetypes.ChessPiece;
import piecetypes.VerticallyMovingPiece;
import xiangqipieces.CannonPiece;
import xiangqipieces.ElephantPiece;
import xiangqipieces.GuardPiece;
import xiangqipieces.HorsePiece;
import xiangqipieces.SoldierPiece;
import xiangqipieces.XiangqiKingPiece;

/**
 * The XiangQi chess game
 * 
 * @author Phila Dlamini
 *
 */
//TODO: Your code is redundant. Simplify it (for the kings facing each other situation)
public class Xiangqi implements ChessGame {

	// Whose turn it is to play
	private ChessGame.Side currentTurn;

	/**
	 * Constructs the Xiangqi chess game
	 */
	public Xiangqi() {
		// by default the south side starts playing first
		currentTurn = ChessGame.Side.SOUTH;
	}

	/**
	 * Returns true if the user can change their piece selection once selected
	 * 
	 * @param piece the piece the user selected
	 */
	@Override
	public boolean canChangeSelection(ChessPiece piece) {
		return true; // by default, players can change their piece selection
	}

	/**
	 * Moves the piece to the specified row and column, if this is a legal move
	 * 
	 * @param piece    the piece to move
	 * @param toRow    the row to move to
	 * @param toColumn the column to move to
	 * @return true if the move was made, false if the move was not made
	 */
	@Override
	public boolean makeMove(ChessPiece piece, int toRow, int toColumn) {

		// If this piece is moving to a different column, ensure the kings won't face each other
		if (legalPieceToPlay(piece, toRow, toColumn) && piece.getColumn() != toColumn) {
				
			// If this piece is a king, then it shouldn't face the other king in the column moving to
			if (piece instanceof XiangqiKingPiece) {

				// If the king will face the other king, this move is not allowed
				if (willFaceOtherKing(piece, toRow, toColumn))
					return false;
				else
					return ChessGame.super.makeMove(piece, toRow, toColumn); // Ensure the move is legal
			}

			// If it is a regular piece, ensure it won't leave the kings facing each other on the column it is leaving
			if (willLeaveKingsFacing(piece))
				return false;
			else
				return ChessGame.super.makeMove(piece, toRow, toColumn); // Ensure the move is legal
			
		} else if (legalPieceToPlay(piece, toRow, toColumn) && piece.getColumn() == toColumn) {
			//Else if the piece is staying on this column
			
			//If the piece is a king capturing another on this column, ensure the two kings won't face each other after the capture
			if(piece instanceof XiangqiKingPiece && piece.getChessBoard().hasPiece(toRow, toColumn)) {
				
				// If the king will face the other king, this move is not allowed
				if (kingsWillFaceAfterCapture(piece))
					return false;
				else
					return ChessGame.super.makeMove(piece, toRow, toColumn); // Ensure the move is legal
			} else 														//Else the piece is not a king
				return ChessGame.super.makeMove(piece, toRow, toColumn); 
		}

		// Else, ensure the move is legal
		return ChessGame.super.makeMove(piece, toRow, toColumn);
	}

	/**
	 * Returns true if this king will face the other king when it enters it's new
	 * column
	 * 
	 * @param king     the king piece moving to a new column
	 * @param toColumn the column this piece is moving to
	 * @return if this king will face the other king when it enters it's new column
	 */
	private boolean willFaceOtherKing(ChessPiece king, int toRow, int toColumn) {
		
		//Analyze this column 
		Map<String, Object> columnData = getColumnData(king.getChessBoard(), toColumn);
		
		//Extract the data 
		boolean northKingOnColumn = (boolean) columnData.get("northKingOnColumn");
		boolean southKingOnColumn = (boolean) columnData.get("southKingOnColumn");
		int northKingRow = (int) columnData.get("northKingRow");
		int southKingRow = (int) columnData.get("southKingRow");


		// If the king is from the south enters this new column, and the north king is on this column with no other pieces
		//in-between, they will face each other
		if (king.getSide() == ChessGame.Side.SOUTH) {

			if (northKingOnColumn) {
				return VerticallyMovingPiece.piecesInRange(king.getChessBoard(), toColumn, northKingRow + 1, toRow - 1) == 0;
			}
		}

		// If the king is from the north, and the south king is on this column, they
		// will face each other if there are no pieces in-between
		if (king.getSide() == ChessGame.Side.NORTH) {

			if (southKingOnColumn) {
				return VerticallyMovingPiece.piecesInRange(king.getChessBoard(), toColumn, toRow + 1, southKingRow - 1) == 0;
			}
		}

		return false;
	}

	/**
	 * Returns true if the specified piece will leave the kings facing each other
	 * when it leaves it's current column
	 * 
	 * @param piece the chess piece that is leaving this column
	 * @return true if this piece leaves the kings facing each other when it leaves
	 *         it's current column
	 */
	private boolean willLeaveKingsFacing(ChessPiece piece) {
		
		//Analyze this column 
		Map<String, Object> columnData = getColumnData(piece.getChessBoard(), piece.getColumn());
		
		//Extract the data 
		boolean northKingOnColumn = (boolean) columnData.get("northKingOnColumn");
		boolean southKingOnColumn = (boolean) columnData.get("southKingOnColumn");
		int northKingRow = (int) columnData.get("northKingRow");
		int southKingRow = (int) columnData.get("southKingRow");

		// If the kings are on the column and this piece is the only other one on the
		// column, the kings will be exposed when it leaves
		if (northKingOnColumn && southKingOnColumn) {
			return VerticallyMovingPiece.piecesInRange(piece.getChessBoard(), 
					piece.getColumn(), northKingRow + 1, southKingRow - 1) == 1;
		}
		return false;
	}
	
	/**
	 * Returns true if the two kings will be left facing each other one of them captures a piece on this column
	 * @param piece			the king piece performing the capture
	 * @return true if the two kings will be left facing each other one of them captures a piece on this column
	 */
	public boolean kingsWillFaceAfterCapture(ChessPiece piece) {
		
		//Analyze this column and find out if both kings are on the column 
		Map<String, Object> columnData = getColumnData(piece.getChessBoard(), piece.getColumn());
		
		//Extract the data 
		boolean northKingOnColumn = (boolean) columnData.get("northKingOnColumn");
		boolean southKingOnColumn = (boolean) columnData.get("southKingOnColumn");
		int northKingRow = (int) columnData.get("northKingRow");
		int southKingRow = (int) columnData.get("southKingRow");
		
		//If both kings are on this column and there is only one other piece on this column, 
		//the kings will face each other after one of them captures the piece
		if (northKingOnColumn && southKingOnColumn) {
			return VerticallyMovingPiece.piecesInRange(piece.getChessBoard(), 
					piece.getColumn(), northKingRow + 1, southKingRow - 1) == 1;
		}

		
		return false;
	}
	
	/**
	 * Analyzes the specified column to see if either of the kings are on this column and if so, at what squares
	 * @param board			the board to search on 
	 * @param column		the column to analyze
	 * @return information about whether or not the kings are on this column  
	 */
	public Map<String, Object> getColumnData(ChessBoard board, int column) {
		
		//A map to hold the data
		LinkedHashMap<String, Object> columnData = new LinkedHashMap<String, Object>();
		
		// If the north king is on this column
		boolean northKingOnColumn = false;

		// If the south king is on this column
		boolean southKingOnColumn = false;

		// The row of the northKing on this column
		int northKingRow = -1;

		// The row of the southKing on this column
		int southKingRow = -1;

		// Check if the kings are on this column
		for (int i = 0; i < 3; i++) {

			// If the north king is on this column, save its row
			if (board.hasPiece(i, column) && board.getPiece(i, column) instanceof XiangqiKingPiece) {
				northKingOnColumn = true;
				northKingRow = i;
			}

			// If the south king is on this column, save its row
			int rowFromBottom = (getNumRows() - 1) - i;
			if (board.hasPiece(rowFromBottom, column) && board.getPiece(rowFromBottom, column) instanceof XiangqiKingPiece) {
				southKingOnColumn = true;
				southKingRow = rowFromBottom;
			}
		}
		
		//Save the data and return it
		columnData.put("northKingOnColumn", northKingOnColumn);
		columnData.put("southKingOnColumn", southKingOnColumn);
		columnData.put("northKingRow", northKingRow);
		columnData.put("southKingRow", southKingRow);
		
		return columnData;
	}

	/**
	 * Returns the number of rows on the board
	 * 
	 * @return the number of rows on the board (10)
	 */
	@Override
	public int getNumRows() {
		return 10;
	}

	/**
	 * Returns the number of columns on the board
	 * 
	 * @return the number of columns on the board (9)
	 */
	@Override
	public int getNumColumns() {
		return 9;
	}

	/**
	 * Returns whose turn it is to play
	 * 
	 * @return whose turn it is to play
	 */
	@Override
	public ChessGame.Side getCurrentTurn() {
		return currentTurn;
	}

	/**
	 * Sets whose turn it is to play
	 * 
	 * @param currentTurn whose turn it is to play
	 */
	@Override
	public void setCurrentTurn(ChessGame.Side currentTurn) {
		this.currentTurn = currentTurn;

	}

	/**
	 * Places all chess pieces on the given board and starts the game
	 * 
	 * @param board the board to places the pieces on
	 */
	@Override
	public void startGame(ChessBoard board) {

		// Loop through and put the pieces on the board
		for (int i = 0; i < board.getGameRules().getNumColumns() / 2; i++) {
			
			// Add the soldier pieces
			if (i % 2 == 0) {
				board.addPiece(new SoldierPiece(board, ChessGame.Side.NORTH, null), 3, i);
				board.addPiece(new SoldierPiece(board, ChessGame.Side.SOUTH, null), 6, i);
				board.addPiece(new SoldierPiece(board, ChessGame.Side.NORTH, null), 3, i + 4);
				board.addPiece(new SoldierPiece(board, ChessGame.Side.SOUTH, null), 6, i + 4);
			}

			// Add the rest of the pieces
			if (i == 0) {        // Add the rook pieces
				board.addPiece(new RookPiece(board, ChessGame.Side.NORTH, null), 0, i);
				board.addPiece(new RookPiece(board, ChessGame.Side.SOUTH, null), 9, i);
				board.addPiece(new RookPiece(board, ChessGame.Side.NORTH, null), 0, 8 - i);
				board.addPiece(new RookPiece(board, ChessGame.Side.SOUTH, null), 9, 8 - i);
			} else if (i == 1) { // Add horse pieces
				board.addPiece(new HorsePiece(board, ChessGame.Side.NORTH, null), 0, i);
				board.addPiece(new HorsePiece(board, ChessGame.Side.SOUTH, null), 9, i);
				board.addPiece(new HorsePiece(board, ChessGame.Side.NORTH, null), 0, 8 - i);
				board.addPiece(new HorsePiece(board, ChessGame.Side.SOUTH, null), 9, 8 - i);
				
				//Also add the cannon pieces
				board.addPiece(new CannonPiece(board, ChessGame.Side.NORTH, null), 2, i);
				board.addPiece(new CannonPiece(board, ChessGame.Side.SOUTH, null), 7, i);
				board.addPiece(new CannonPiece(board, ChessGame.Side.NORTH, null), 2, 8 - i);
				board.addPiece(new CannonPiece(board, ChessGame.Side.SOUTH, null), 7, 8 - i);
				
			} else if (i == 2) { // Add elephant pieces 
				board.addPiece(new ElephantPiece(board, ChessGame.Side.NORTH, null), 0, i);
				board.addPiece(new ElephantPiece(board, ChessGame.Side.SOUTH, null), 9, i);
				board.addPiece(new ElephantPiece(board, ChessGame.Side.NORTH, null), 0, 8 - i);
				board.addPiece(new ElephantPiece(board, ChessGame.Side.SOUTH, null), 9, 8 - i);
			} else if (i == 3) {// add the guard pieces and the king pieces
				board.addPiece(new GuardPiece(board, ChessGame.Side.NORTH, null), 0, i);
				board.addPiece(new GuardPiece(board, ChessGame.Side.SOUTH, null), 9, i);
				board.addPiece(new GuardPiece(board, ChessGame.Side.NORTH, null), 0, 8 - i);
				board.addPiece(new GuardPiece(board, ChessGame.Side.SOUTH, null), 9, 8 - i);
				
				//Add the kings
				board.addPiece(new XiangqiKingPiece(board, ChessGame.Side.NORTH, null), 0, i + 1);
				board.addPiece(new XiangqiKingPiece(board, ChessGame.Side.SOUTH, null), 9, i + 1);
				
				//And the soldier pieces
				board.addPiece(new SoldierPiece(board, ChessGame.Side.NORTH, null), 3, i + 5);
				board.addPiece(new SoldierPiece(board, ChessGame.Side.SOUTH, null), 6, i + 5);
			}

		}

	}

}
