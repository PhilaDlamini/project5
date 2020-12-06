package testers;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import chesstype.Xiangqi;
import europeanpieces.RookPiece;
import graphics.ChessGame;
import graphics.SwingChessBoard;
import graphics.SwingEuropeanChessDisplay;
import xiangqipieces.ElephantPiece;
import xiangqipieces.GuardPiece;
import xiangqipieces.XiangqiKingPiece;

/**
 * A tester class for Xiangqi chess rules
 * @author Phila Dlamini
 *
 */
class XiangqiRulesTest {
	
	/**
	 * Tests the Xiangqi movement rules 
	 */
	@Test
	void testEuropeanRules() {
		
		//Create a chess board and place these pieces on it
		Xiangqi chess = new Xiangqi();
		SwingChessBoard board = new SwingChessBoard(new SwingEuropeanChessDisplay(), chess);
		XiangqiKingPiece kingNorth = new XiangqiKingPiece(board, ChessGame.Side.NORTH, null);
		GuardPiece guardNorth = new GuardPiece(board, ChessGame.Side.NORTH, null);
		RookPiece rookSouth = new RookPiece(board, ChessGame.Side.SOUTH, null);
		XiangqiKingPiece kingSouth = new XiangqiKingPiece(board, ChessGame.Side.SOUTH, null);
		board.addPiece(kingNorth, 0, 5);
		board.addPiece(guardNorth, 1, 3);
		board.addPiece(rookSouth, 5, 5);
		board.addPiece(kingSouth, 8, 5);
		
		/** Players should be able to change their selection*/
		assertEquals(chess.canChangeSelection(kingSouth), true);
		
		/** Test for legalPieceToPlay **/
		assertEquals(chess.legalPieceToPlay(kingNorth, 1, 5), false);
		assertEquals(chess.legalPieceToPlay(guardNorth, 2, 3), false);
		assertEquals(chess.legalPieceToPlay(kingSouth, 7, 5), true);
		//It is currently the south's side to play

		/** Test for change in turns */
		assertEquals(chess.makeMove(kingNorth, 1, 5), false);
		//It is currently the SOUTH side's turn to move
	
		//The south pieces thus should be able to move
		assertEquals(chess.makeMove(kingSouth, 7, 5), true); 
		assertEquals(board.hasPiece(7, 5), true); //kingSouth should have moved here 
		assertEquals(board.hasPiece(8, 5), false); //There should be no piece where kingSouth was 

		//Now it should be the north side's turn to move
		assertEquals(chess.getCurrentTurn(), ChessGame.Side.NORTH);
		
		//If the SOUTH tries to move again, it should not be allowed
		assertEquals(chess.makeMove(kingSouth, 7, 4), false);
		
		//Let's verify this two more times 
		assertEquals(chess.makeMove(kingNorth, 1, 5), true);
		assertEquals(chess.getCurrentTurn(), ChessGame.Side.SOUTH);
		assertEquals(chess.makeMove(kingSouth, 7, 4), true);
		assertEquals(chess.getCurrentTurn(), ChessGame.Side.NORTH);
		assertEquals(chess.makeMove(kingNorth, 2, 5), true);
		
		/** makeMove should not allow illegal movements 
		 * kingNorth is currently at (2, 5)
		 * kingSouth is currently at (7, 4)
		 */
		assertEquals(chess.makeMove(kingNorth, 3, 5), false); //The XiangqiKingPiece cannot leave the fortress
		assertEquals(chess.makeMove(kingSouth, 6, 3), false); //The XiangqiKingPiece does not move diagonally

		/** 
		 * Test for removing eliminated pieces
		 * kingNorth is currently at (2, 5)
		 * kingSouth is currently at (7, 4)
		 * It is SOUTH's turn, rookSouth is able to eliminate kingNorth by moving vertically into (2, 5)
		 */
		assertEquals(board.squareThreated(2, 5, kingNorth), true); //kingNorth is threatened
		assertEquals(chess.makeMove(rookSouth, 2, 5), true);
		assertEquals(board.getPiece(2, 5).getSide(), ChessGame.Side.SOUTH); //rookSouth should now be here 
		assertEquals(board.hasPiece(5, 5), false); //There should be no piece where rookSouth last was 
		//kingNorth is now eliminated from the game 
		
		//None of the remaining pieces are threatened
		assertEquals(board.squareThreated(1, 3, guardNorth), false); 
		assertEquals(board.squareThreated(2, 5, rookSouth), false); 
		assertEquals(board.squareThreated(7, 4, kingSouth), false); 
				
	}
	
	/**
	 * Tests the makeMove() method to ensure the two kings are never left facing each other 
	 * 
	 */
	@Test
	void testKingsFacing() {
		
		/*
		 * #######################################
		 * 
		 * TEST CASE WHERE A KING ENTERS A NEW COLUMN
		 * 
		 * #######################################
		 */
		
		
		//Create with these pieces on it 
		Xiangqi xiangqi = new Xiangqi();
		SwingChessBoard chessBoard = new SwingChessBoard(new SwingEuropeanChessDisplay(), xiangqi);
		XiangqiKingPiece kingNorth1 = new XiangqiKingPiece(chessBoard, ChessGame.Side.NORTH, null);
		XiangqiKingPiece kingSouth1 = new XiangqiKingPiece(chessBoard, ChessGame.Side.SOUTH, null);
		chessBoard.addPiece(kingNorth1, 0, 3);
		chessBoard.addPiece(kingSouth1, 9, 4);
		
	
		/** Test case where the king is moving within the same column */
		assertEquals(xiangqi.makeMove(kingSouth1, 8, 4), true);
		//It's the pieces's turn to move, and it will not face the other king
		
		/**
		 * Test case where the north king enters a new column and faces the south king
		 * It is the north king's turn to move
		 */
		assertEquals(xiangqi.makeMove(kingNorth1, 0, 4), false);
		//The king will face the south king
		
		/** Test case where there is a piece in-between */
		chessBoard.addPiece(new ElephantPiece(chessBoard, ChessGame.Side.SOUTH, null), 4, 4);
		assertEquals(xiangqi.makeMove(kingNorth1, 0, 4), true);
		//Because there is a piece in-between the kings and they will not face each other, the move is allowed

		/**
		 * Test case where the south king enters a column where it faces the north king
		 * It is the south's turn to move
		 * put a northKing at (2, 3). the south king shouldn't to this column here because the kings will face each other
		 */
		chessBoard.addPiece(new XiangqiKingPiece(chessBoard, ChessGame.Side.NORTH, null), 2, 3);
		assertEquals(xiangqi.makeMove(kingSouth1, 8, 3), false);
		//Not allowed as the kings face each other
		
		/*
		 * ########################################
		 * 
		 * CASE WHERE A PIECE LEAVES A COLUMN 
		 * 
		 * ########################################
		 */
		
		//Create with these pieces on it 
		Xiangqi chess = new Xiangqi();
		SwingChessBoard board = new SwingChessBoard(new SwingEuropeanChessDisplay(), chess);
		XiangqiKingPiece kingNorth = new XiangqiKingPiece(board, ChessGame.Side.NORTH, null);
		RookPiece rookSouth = new RookPiece(board, ChessGame.Side.SOUTH, null);
		XiangqiKingPiece kingSouth = new XiangqiKingPiece(board, ChessGame.Side.SOUTH, null);
		board.addPiece(kingNorth, 1, 5);
		board.addPiece(rookSouth, 5, 5); //Place the rook piece in-between the kings
		board.addPiece(kingSouth, 8, 5);
		
		/** Test case where the piece is moving within the same column */
		assertEquals(chess.makeMove(rookSouth, 4, 5), true);
		//The kings are not left facing each other 
		
		/** Test case where the kings are left facing each other **/
		chess.makeMove(kingNorth, 2, 5);//Move a north piece so it is south's turn to move again
		
		//It is illegal for the rook to leave the column
		assertEquals(chess.makeMove(rookSouth, 5, 4), false);
		//Moving to this square leaves the kings facing each other 
		
		/** Test case where the available pieces are "behind" the kings*/
		board.addPiece(new ElephantPiece(board, ChessGame.Side.NORTH, null), 0, 5);
		board.addPiece(new ElephantPiece(board, ChessGame.Side.SOUTH, null), 9, 5);

		/*
		 * Moving the piece rook away should still not be allowed:
		 * the newly added pieces are behind the kings, and the kings will still face each other
		 */
		assertEquals(chess.makeMove(rookSouth, 5, 4), false);
		board.removePiece(0, 5);
		board.removePiece(9, 5);
		
		/** 
		 * Test case where one other piece is left in-between these pieces
		 * It is still South's turn to move, and rookPiece is at (4, 5)
		 */
		board.addPiece(new ElephantPiece(board, ChessGame.Side.NORTH, null), 3, 5);
		board.addPiece(new ElephantPiece(board, ChessGame.Side.NORTH, null), 4, 5);
		assertEquals(chess.makeMove(rookSouth, 4, 4), true);
		//Moving this piece leaves one other piece in-between the kings
		

		/*###############################################
		 * 
		 * TEST CASE WHERE A KING CAPTURES A PIECE ON 
		 * THE COLUMN AND IS LEFT FACING THE OTHER KING
		 * 
		 * ##############################################
		 */
		
		/**
		 * If both kings are on the same column, and there is only one other piece separating them on this column
		 * the kings should not be allowed to capture this piece as then they will be left facing each other
		 * 
		 */
		Xiangqi chess2 = new Xiangqi();
		SwingChessBoard board2 = new SwingChessBoard(new SwingEuropeanChessDisplay(), chess2);
		XiangqiKingPiece kingNorth2 = new XiangqiKingPiece(board2, ChessGame.Side.NORTH, null);
		RookPiece rookSouth2 = new RookPiece(board2, ChessGame.Side.SOUTH, null);
		XiangqiKingPiece kingSouth2 = new XiangqiKingPiece(board2, ChessGame.Side.SOUTH, null);
		board2.addPiece(kingNorth2, 1, 4);
		board2.addPiece(rookSouth2, 2, 4); //Place the rook piece in-between the kings
		board2.addPiece(kingSouth2, 9, 4);
		
		/*Test case when the one of the kings capture the piece */
		
		//Move the south king so it is NORTH's side turn to move
		chess2.makeMove(kingSouth2, 8, 4);
		
		//The north king should be be able to capture the rook since the kings will be left facing
		assertEquals(chess2.makeMove(kingNorth2, 2, 4), false);
		
		//If there was another piece on the column, it would be able to, however
		board2.addPiece(new RookPiece(board2, ChessGame.Side.NORTH, null), 7, 4);
		assertEquals(chess2.makeMove(kingNorth2, 2, 4), true); //now legal!
		
		//Likewise, the south king should not be able to capture the just added elephant piece as
		//it is the only piece separating the kings
		assertEquals(chess2.makeMove(kingSouth2, 7, 4), false);
		
		//But if there was an additional piece on the column, this would be okay
		RookPiece rook = new RookPiece(board2, ChessGame.Side.SOUTH, null);
		board2.addPiece(rook, 4, 4);
		assertEquals(chess2.makeMove(kingSouth2, 7, 4), true); //Now legal!
		
		//2. The rook piece should be able to capture the north king, however, since after the capture the other king 
		//will be gone, and we will no longer have the facing king situation
		chess2.makeMove(kingNorth2, 1, 4); //Move the north king so it is south's turn to move again
		assertEquals(chess2.makeMove(rook, 1, 4), true); //Legal to catpure this king

	}
	
	

}
