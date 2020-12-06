package graphics;
import javax.swing.SwingUtilities;

import piecetypes.ChessPiece;

/**
 * An arbitrary chess board on which the game of chess is played
 * @author Phila Dlamini
 *
 */
public abstract class ChessBoard {
	
	//The pieces on the board
	private ChessPiece[][] pieces;
	
	//The chess game being played on this board
	private ChessGame gameRules;
	
	/**
	 * Constructs the ChessBoard
	 * @param gameRules 	the chess game being played on this board
	 */
	public ChessBoard(ChessGame gameRules) {
		pieces = new ChessPiece[gameRules.getNumRows()][gameRules.getNumColumns()];
		this.gameRules = gameRules;
	}

	/**
	 * Adds the specified piece onto the specified row and column
	 * @param piece		the chess piece to place on the board
	 * @param row		the row to place this piece on 
	 * @param column	the column to place this piece on 
	 */
	public void addPiece(ChessPiece piece, int row, int column) {
		pieces[row][column] = piece;
		piece.setLocation(row, column);
	}
	
	/**
	 * Removes the piece at the specified row and column on the board
	 * @param row		the row from which the piece is removed
	 * @param column	the column from which the piece is removed
	 * @return the piece removed 
	 */
	public ChessPiece removePiece(int row, int column) {
		ChessPiece piece = getPiece(row, column);
		pieces[row][column] = null;
		return piece;
	}
	
	/**
	 * Returns true if there is a piece at the specified row and column
	 * @param row		the row to look at 
	 * @param column	the column to look at 
	 * @return true if there is a piece at the specified row and column
	 */
	public boolean hasPiece(int row, int column) {
		return pieces[row][column] != null;
	}
	
	/**
	 * Returns the piece at the specified row and column
	 * @param row		the row to look at
	 * @param column	the column to look at 
	 * @return	the piece at the specified row and column
	 */
	public ChessPiece getPiece(int row, int column) {
		return pieces[row][column];
	}
	
	/**
	 * Returns true if a piece of the opponent player can make a legal move and
	 * capture the piece the specified piece
	 * 
	 * @param row    the row this piece is on
	 * @param column the column this piece is on
	 * @param piece  the piece at this row and column
	 * @return true if an opponent piece can move to this row and column and capture the piece here
	 */
	public abstract boolean squareThreated(int row, int column, ChessPiece piece);
	
	/**
	 * Returns the ChessGame being displayed on this board
	 * @return the ChessGame being displayed on this board 
	 */
	public ChessGame getGameRules() {
		return gameRules;
	}
	
	
	
 }
