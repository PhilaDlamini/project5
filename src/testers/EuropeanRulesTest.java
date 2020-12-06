package testers;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import chess.ChessGame;
import chess.EuropeanChess;
import europeanpieces.PawnPiece;
import graphics.SwingChessBoard;
import graphics.SwingEuropeanChessDisplay;

/**
 * Tests EuropeanChess for enforcement of rules 
 * @author phila
 *
 */
class EuropeanRulesTest {

	/**
	 * Tests the European chess rules 
	 */
	@Test
	void testEuropeanRules() {
		
		//Create a chess board and place these pawns on it
		EuropeanChess chess = new EuropeanChess();
		SwingChessBoard board = new SwingChessBoard(new SwingEuropeanChessDisplay(), chess);
		PawnPiece pawnNorth = new PawnPiece(board, ChessGame.Side.NORTH, null);
		PawnPiece pawnNorth2 = new PawnPiece(board, ChessGame.Side.NORTH, null);
		PawnPiece pawnSouth = new PawnPiece(board, ChessGame.Side.SOUTH, null);
		board.addPiece(pawnNorth, 1, 3);
		board.addPiece(pawnNorth2, 1, 7);
		board.addPiece(pawnSouth, 6, 2);
		
		/** Players should be able to change their selection*/
		assertEquals(chess.canChangeSelection(pawnNorth), true);
		
		/** Test for legalPieceToPlay **/
		assertEquals(chess.legalPieceToPlay(pawnNorth, 2, 3), false);
		assertEquals(chess.legalPieceToPlay(pawnNorth2, 2, 7), false);
		assertEquals(chess.legalPieceToPlay(pawnSouth, 5, 2), true);
		//It is currently the south's side to play
		
		/** Test for change in turns */
		assertEquals(chess.makeMove(pawnNorth, 2, 3), false);
		//It is currently the SOUTH side's turn to move
	
		//The SOUTH pawn should be able to move 
		assertEquals(chess.makeMove(pawnSouth, 5, 2), true); 
		assertEquals(board.hasPiece(5, 2), true); //pawnSouth should have moved here 
		assertEquals(board.hasPiece(6, 2), false); //There should be no piece where pawnSouth was 
		//Legal

		//Now it should be the north's side's turn to move
		assertEquals(chess.getCurrentTurn(), ChessGame.Side.NORTH);
		
		//If the SOUTH tries to move again, it should not be allowed
		assertEquals(chess.makeMove(pawnSouth, 4, 2), false);
		
		//Let's verify many times 
		assertEquals(chess.makeMove(pawnNorth, 2, 3), true);
		assertEquals(chess.getCurrentTurn(), ChessGame.Side.SOUTH);
		assertEquals(chess.makeMove(pawnSouth, 4, 2), true);
		assertEquals(chess.getCurrentTurn(), ChessGame.Side.NORTH);
		assertEquals(chess.makeMove(pawnNorth, 3, 3), true);

		/** Test for removing eliminated pieces
		 * pawnSouth is now at (4, 2)
		 * pawnNorth should be at (3, 3)
		 * Since it's SOUTH's turn, pawnSouth is then able to eliminate pawnNorth by moving vertically into (3, 3)
		*/
		assertEquals(board.squareThreated(3, 3, pawnNorth), true); //panwNorth is threatened
		assertEquals(chess.makeMove(pawnSouth, 3, 3), true);
		assertEquals(board.getPiece(3, 3).getSide() == ChessGame.Side.SOUTH, true); //pawnSouth should now be here 
		assertEquals(board.hasPiece(4, 2), false); //There should be no piece here 
		//pawnNorth is now eliminated from the game 
		
		//None of the other squares should be threatened
		assertEquals(board.squareThreated(3, 3, pawnSouth), false); 
		assertEquals(board.squareThreated(2, 3, pawnNorth), false); 

		
		/** Test for illegal movements
		 * pawnSouth is at (3, 3).
		 * Move pawnNorth2 so the turn is south's again
		 * */
		assertEquals(chess.makeMove(pawnNorth2, 2, 7), true);
		
		//Taking two steps when not moving for the first time is illegal for a pawn. 
		assertEquals(chess.makeMove(pawnSouth, 1, 3), false);


	}

}
