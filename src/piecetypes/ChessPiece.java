package piecetypes;
import graphics.ChessBoard;
import graphics.ChessGame;
import graphics.ChessGame.Side;

/**
 * A ChessPiece in the game 
 * @author: Phila Dlamini
 */
public abstract class ChessPiece {
	
	//The label for this piece
	private String label;
	
	//The player this piece belongs to 
	private ChessGame.Side side;
	
	//The graphics data for this piece
	private Object icon;
	
	//Holds the row this piece is on 
	private int row;
	
	//Holds the column this piece is on 
	private int column;
	
	//Holds the board this chess piece is on 
	private ChessBoard chessBoard;
	
	/**
	 * Constructs the chess piece
	 * @param chessBoard 	the chessBoard this piece is on
	 * @param label     	the label for the piece 
	 * @param side			the "player" this piece belongs to 
	 * @param icon 			the graphics data for this piece
	 * @param row 			the current row this piece is on
	 * @param column 		the current column this piece is on
	 */
	public ChessPiece(ChessBoard chessBoard, String label, ChessGame.Side side, Object icon) {
		this.chessBoard = chessBoard;
		this.label = label;
		this.side = side;
		this.icon = icon;
	}

	/**
	 * Returns the "player" this piece belongs to 
	 * @return side		the "player" this piece belongs to  
	 */
	public ChessGame.Side getSide() {
		return side;
	}
	
	/**
	 * Returns the label for this chess piece
	 * @return label 	the label for this chess piece
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * Returns the icon for this chess piece
	 * @return icon		the graphics data for this chess piece
	 */
	public Object getIcon() {
		return icon;
	}
	
	/** 
	 * Sets the location of the piece on the board
	 * @param row 		the row to place the piece at
	 * @param column	the column to place the piece at
	 */
	public void setLocation(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	/** 
	 * Returns true is it is legal to move this piece to this row and column 
	 * @param toRow		the row to move to 
	 * @param toColumn	the column to move to 
	 * @return true if it is legal to move this piece to this row and column
	 */
	public abstract boolean isLegalMove(int toRow, int toColumn); 
	
	/**
	 * Returns the chess board this piece is on 
	 * @return the chess board this piece is on
	 */
	public ChessBoard getChessBoard() {
		return chessBoard;
	}
	
	/**
	 * Returns the current row this piece is on 
	 * @return
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Returns the current column this piece is on
	 * @return the current column this piece is on
	 */
	public int getColumn() {
		return column;
	}
	
	/**
	 * Returns true if it is legal to move this piece to the indicated position to capture an opponent
	 * @param row		the row to move the piece to 
	 * @param column	the column to move the piece to 
	 * @return true if it is legal to move this piece to the indicated position to capture an opponent		
	 */
	public boolean isLegalCaptureMove(int row, int column) {
		return isLegalMove(row, column) && getChessBoard().hasPiece(row, column);
	}
	
	/**
	 * Returns true if it is legal to move this piece to the indicated position (which is empty)
	 * @param row 		the row to move the piece to 
	 * @param column	the column to move the piece to 
	 * @return true if it legal to move this piece to the indicated position (which is empty)
	 */
	public boolean isLegalNonCaptureMove(int row, int column) {
		return isLegalMove(row, column) && !(getChessBoard().hasPiece(row, column));
	}
	
	/**
	 * Handles the processing that is needed once the move is done 
	 */
	public void moveDone() {
		//Only a few select ChessPiece(s) need processing after a move is done
		//They shouldn't be forced to override this
		return; 
	}
	
	/**
	 * Sets the label for this piece 
	 * @param label 	the label for this piece 
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	
	/**
	 * Sets the icon for this piece 
	 * @param icon		the icon for this piece 
	 */
	public void setIcon(Object icon) {
		this.icon = icon;
	}
}
