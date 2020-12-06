package europeanpieces;
import javax.swing.JOptionPane;

import graphics.ChessBoard;
import graphics.ChessGame;
import piecetypes.ChessPiece;
import piecetypes.DiagonallyMovingPiece;
import piecetypes.SingleDirectionPiece;
import piecetypes.VerticallyMovingPiece;

/**
 * A Pawn in the game
 * @author Phila Dlamini
 *
 */
public class PawnPiece extends SingleDirectionPiece implements VerticallyMovingPiece{
	
	//Stores whether or not this is the first move for the pawn (true by default)
	private boolean firstMove;

	/**
	 * Constructs the PawnPiece in the game
	 * @param board		the chess board this piece is on 
	 * @param side		the player this piece belongs to 
	 * @param icon		the graphics data for displaying the piece 
	 */
	public PawnPiece(ChessBoard board, ChessGame.Side side, Object icon) {
		super(board, "P", side, icon, 2); //When moving for the first time, the pawn can take two steps forward
		this.firstMove = true;
	}
	
	/**
	 * Returns true if this is a legal move for a pawn to make
	 * A pawn is allowed once space forward (where there must be no any other piece), diagonal if to kill
	 * If at the last row, the pawn can be upgraded to any other piece except a King
	 */
	@Override
	
	public boolean isLegalMove(int toRow, int toColumn) {
				
		// The distance the piece is moving horizontally
		int horizontalDistance = Math.abs(toColumn - getColumn());
		
		// The displacement of the piece vertically
		int verticalDisplacement = getSide() == ChessGame.Side.SOUTH ? getRow() - toRow : toRow - getRow();
		
		if(horizontalDistance == 0 && verticalDisplacement > 0 && 
				verticalDisplacement <= this.getNumberOfAllowedSteps()) {  //The piece is moving vertically forward
			
			//This is a legal move if there is no piece at this position, and no pieces in between the squares
			return !(getChessBoard().hasPiece(toRow, toColumn)) && VerticallyMovingPiece.hasNoPiecesInBetween(this, toRow);
			
		} else if (verticalDisplacement == 1 && horizontalDistance == verticalDisplacement) { //The piece is moving diagonally
			
			//This is legal only if the piece is eliminating a piece of the other player at this position
			return getChessBoard().hasPiece(toRow, toColumn) && getChessBoard().getPiece(toRow, toColumn).getSide() != getSide();
		}
		
		//Else, this piece is moving in a direction not allowed
		return false;
	}
	
	/** 
	 * Sets the location of the PawnPiece on the board
	 * @param row 		the row to place the piece at
	 * @param column	the column to place the piece at
	 */
	@Override
	public void setLocation(int toRow, int toColumn) {
		super.setLocation(toRow, toColumn);
		
		//If this pawn is landing on the other side of the board, allow the user to upgrade it to another piece
		switch(getSide()) {
		
		case NORTH:
			if(toRow == getChessBoard().getGameRules().getNumRows() - 1) upgradePiece(toRow, toColumn);
			break;
		
		case SOUTH:
			if(toRow == 0) upgradePiece(toRow, toColumn);
			break;
			
		case WEST:
			if(toColumn == getChessBoard().getGameRules().getNumColumns() - 1) upgradePiece(toRow, toColumn); 
			break;
			
		case EAST:
			if(toColumn == 0) upgradePiece(toRow, toColumn);
			break;
		}
	}
	
	/**
	 * Returns true is this pawn has not moved before 
	 * @return true if this pawn has not moved before 
	 */
	public boolean isFirstMove() {
		return firstMove;
	}
	
	
	/**
	 * Upgrades this pawn to another of the users's choosing (except a King)
	 * @param row 		the row the was was at (the upgraded piece is placed on this row)
	 * @param column 	the column the pawn was at (the upgraded piece is placed on this column)

	 */
	private void upgradePiece(int row, int column) {
		//Ask the user which piece they want to upgrade to 
		char pieceWanted = JOptionPane.showInputDialog("Which piece do you want? You are not allowed to choose a King").charAt(0);
		
		//Holds the piece to upgrade
		ChessPiece piece;
		
		//Put the desired piece at this location
		switch (pieceWanted) {
		
		case 'Q':
			piece = new QueenPiece(getChessBoard(), getSide(), null);
			break;
			
		case 'R':
			piece = new RookPiece(getChessBoard(), getSide(), null);
			break;
			
		case 'B':
			piece = new BishopPiece(getChessBoard(), getSide(), null);
			break;
			
		case 'N':
			piece = new KnightPiece(getChessBoard(), getSide(), null);
			break;
			
		case 'P':
			piece = new PawnPiece(getChessBoard(), getSide(), null);
			break;
			
		default:
			piece = new PawnPiece(getChessBoard(), getSide(), null); //By default (e.g the user chooses a King, we put a Pawn)
		}
		
		getChessBoard().addPiece(piece, row, column);
		
	}
	
	/**
	 * Handles post-move for the pawn just moved for the first time
	 * If this was the pawn's first move, it updates the number of allowed steps to 1
	 */
	@Override
	public void moveDone() {
		if(isFirstMove()) { 
			firstMove = false;
			setAllowedStepsInDirection(1); //The pawn should now only move one step at a time
		}
	}
	
}

