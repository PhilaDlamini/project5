package testers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import chesstype.Xiangqi;
import europeanpieces.PawnPiece;
import europeanpieces.RookPiece;
import graphics.ChessGame;
import graphics.SwingChessBoard;
import graphics.SwingEuropeanChessDisplay;
import xiangqipieces.CannonPiece;
import xiangqipieces.ElephantPiece;
import xiangqipieces.GuardPiece;
import xiangqipieces.HorsePiece;
import xiangqipieces.SoldierPiece;
import xiangqipieces.XiangqiKingPiece;

/**
 * Tests Xiangqi pieces for capture moves 
 * @author Phila Dlamini
 *
 */
class XiangqiCaptureTest {
	
	/**
	 * Tests if this is a legal capture move for the XaingQi pieces in the game 
	 */
	@Test
	void isLegalCaptureMove() {
		
		SwingChessBoard board = new SwingChessBoard(new SwingEuropeanChessDisplay(), new Xiangqi());
		CannonPiece cannon1 = new CannonPiece(board, ChessGame.Side.SOUTH, null);
		RookPiece rook1 = new RookPiece(board, ChessGame.Side.SOUTH, null);
		RookPiece rook2 = new RookPiece(board, ChessGame.Side.SOUTH, null);
		RookPiece rook3 = new RookPiece(board, ChessGame.Side.SOUTH, null);
		XiangqiKingPiece king1 = new XiangqiKingPiece(board, ChessGame.Side.NORTH, null);
		XiangqiKingPiece king2 = new XiangqiKingPiece(board, ChessGame.Side.NORTH, null);
		XiangqiKingPiece king3 = new XiangqiKingPiece(board, ChessGame.Side.NORTH, null);
		ElephantPiece elephant1 = new ElephantPiece(board, ChessGame.Side.NORTH, null);
		ElephantPiece elephant2 = new ElephantPiece(board, ChessGame.Side.SOUTH, null);
		ElephantPiece elephant3 = new ElephantPiece(board, ChessGame.Side.SOUTH, null);
		HorsePiece horse1 = new HorsePiece(board, ChessGame.Side.NORTH, null);
		GuardPiece guard1 = new GuardPiece(board, ChessGame.Side.SOUTH, null);
		GuardPiece guard2 = new GuardPiece(board, ChessGame.Side.SOUTH, null);
		GuardPiece guard3 = new GuardPiece(board, ChessGame.Side.NORTH, null);
		PawnPiece pawn1 = new PawnPiece(board, ChessGame.Side.NORTH, null);
		PawnPiece pawn2 = new PawnPiece(board, ChessGame.Side.NORTH, null);
		HorsePiece horse2 = new HorsePiece(board, ChessGame.Side.SOUTH, null);
		HorsePiece horse3 = new HorsePiece(board, ChessGame.Side.SOUTH, null);
		HorsePiece horse4 = new HorsePiece(board, ChessGame.Side.NORTH, null);
		SoldierPiece soldier1 = new SoldierPiece(board, ChessGame.Side.SOUTH, null);
		SoldierPiece soldier2 = new SoldierPiece(board, ChessGame.Side.SOUTH, null);
		SoldierPiece soldier3 = new SoldierPiece(board, ChessGame.Side.SOUTH, null);
		board.addPiece(cannon1, 5, 3);
		board.addPiece(rook1, 5, 4);
		board.addPiece(rook2, 5, 5);
		board.addPiece(rook3, 5, 2);
		board.addPiece(king1, 5, 0);
		board.addPiece(king2, 1, 3);
		board.addPiece(king3, 1, 4);
		board.addPiece(elephant1, 3, 3);
		board.addPiece(elephant2, 7, 2);
		board.addPiece(elephant3, 5, 6);
		board.addPiece(horse1, 3, 4);
		board.addPiece(horse2, 6, 7);
		board.addPiece(horse3, 2, 2);
		board.addPiece(horse4, 4, 2);
		board.addPiece(guard1, 9, 5);
		board.addPiece(guard2, 8, 3);
		board.addPiece(guard3, 0, 4);
		board.addPiece(pawn1, 8, 4);
		board.addPiece(pawn2, 8, 6);
		board.addPiece(soldier1, 3, 2); //crossed the river
		board.addPiece(soldier2, 1, 5); 
		board.addPiece(soldier3, 2, 5); 


		
		/** Tests for the CannonPiece **/
		
		//1. When the piece moves into an empty space
		assertEquals(cannon1.isLegalCaptureMove(4, 3), false);
		//There is no piece at this location. Not a capture move
		
		//2. When the piece tries to capture a piece of the same player 
		assertEquals(cannon1.isLegalCaptureMove(5, 5), false);
		//The piece at the landing square belongs to the same player. Illegal 
		
		//3. When the piece tries to capture an opponent piece 
		assertEquals(cannon1.isLegalCaptureMove(5, 0), true);
		//Skips exactly one piece to capture this one at (5, 0) 
		
		//4. When the piece tries to capture an opponent piece illegally
		assertEquals(cannon1.isLegalCaptureMove(3, 3), false);
		//Does not skip any pieces to capture this one. Illegal for a CannonPiece
		
		/** Tests for the ElephantPiece **/
		
		//1. When the piece moves into an empty space
		assertEquals(elephant2.isLegalCaptureMove(9, 0), false);
		//There is no piece at this location. Not a capture move
		
		//2. When the piece tries to capture a piece of the same player 
		assertEquals(elephant2.isLegalCaptureMove(5, 4), false);
		//The piece at the landing square belongs to the same player. Illegal 
		
		//3. When the piece tries to capture an opponent piece 
		assertEquals(elephant2.isLegalCaptureMove(5, 0), true);
		//Moves exactly two diagonal squares to capture this piece at (5, 0) 
		
		//4. When the piece tries to capture an opponent piece illegally
		assertEquals(elephant3.isLegalCaptureMove(3, 4), false);
		//Even though the elephant is taking two diagonal steps, it cannot cross the river
		
		/** Test for the GuardPiece */
		
		//1. When the piece moves into an empty space
		assertEquals(guard2.isLegalCaptureMove(7, 4), false);
		//There is no piece at this location. Not a capture move
		
		//2. When the piece tries to capture a piece of the same player 
		assertEquals(guard3.isLegalCaptureMove(1, 3), false);
		//The piece at the landing square belongs to the same player. Illegal 
		
		//3. When the piece tries to capture an opponent piece 
		assertEquals(guard1.isLegalCaptureMove(8, 4), true);
		//Moves exactly one diagonal square to capture this piece
		
		//4. When the piece tries to capture an opponent piece illegally
		assertEquals(guard1.isLegalCaptureMove(8, 6), false);
		//Even though the guard is taking one diagonal step, it cannot leave the fortress so this is illegal
		
		/** Test for the HorsePiece */
		
		//1. When the piece moves into an empty space
		assertEquals(horse2.isLegalCaptureMove(8, 8), false);
		//There is no piece at this location. Not a capture move
		
		//2. When the piece tries to capture a piece of the same player 
		assertEquals(horse2.isLegalCaptureMove(5, 5), false);
		//The piece at the landing square belongs to the same player. Illegal 
		
		//3. When the piece tries to capture an opponent piece 
		assertEquals(horse2.isLegalCaptureMove(8, 6), true);
		//Moves one step down and then then one step diagonally left
		
		//4. When the piece tries to capture an opponent piece illegally
		board.addPiece(new RookPiece(board, ChessGame.Side.SOUTH, null), 7, 7);
		assertEquals(horse2.isLegalCaptureMove(8, 6), false);
		//There is now a piece blocking the horse from capturing the piece at (8, 6). It is illegal to skip it
		board.removePiece(7, 7);
		
		/** Test for the SoldierPiece */
		
		//1. When the piece moves into an empty space
		assertEquals(soldier1.isLegalCaptureMove(3, 1), false);
		//There is no piece at this location. Not a capture move
		
		//2. When the piece tries to capture a piece of the same player 
		assertEquals(soldier1.isLegalCaptureMove(2, 2), false);
		//The piece at the landing square belongs to the same player. Illegal 
		
		//3. When the piece tries to capture an opponent piece 
		assertEquals(soldier1.isLegalCaptureMove(3, 3), true);
		//Moves one step horizontally left (allowed since the piece has crossed the river)
		
		//4. When the piece tries to capture an opponent piece illegally
		assertEquals(soldier1.isLegalCaptureMove(4, 2), false);
		//A soldier piece cannot move backwards
		
		/** Test for the XiangqiKingPiece */
		
		//1. When the piece moves into an empty space
		assertEquals(king3.isLegalCaptureMove(2, 4), false);
		//There is no piece at this location. Not a capture move
		
		//2. When the piece tries to capture a piece of the same player 
		assertEquals(king3.isLegalCaptureMove(1, 3), false);
		//The piece at the landing square belongs to the same player. Illegal 
		
		//3. When the piece tries to capture an opponent piece 
		assertEquals(king3.isLegalCaptureMove(1, 5), true);
		//Moves one step horizontally left (allowed since the piece has crossed the river)
		
		//4. When the piece tries to capture an opponent piece illegally
		assertEquals(king3.isLegalCaptureMove(2, 5), false);
		//The King does not move diagonally
	}


	/**
	 * Tests if this is a legal capture move for the XaingQi pieces in the game 
	 */
	@Test
	void isLegalNonCaptureMove() {
		
		SwingChessBoard board = new SwingChessBoard(new SwingEuropeanChessDisplay(), new Xiangqi());
		CannonPiece cannon1 = new CannonPiece(board, ChessGame.Side.SOUTH, null);
		RookPiece rook1 = new RookPiece(board, ChessGame.Side.SOUTH, null);
		RookPiece rook2 = new RookPiece(board, ChessGame.Side.SOUTH, null);
		RookPiece rook3 = new RookPiece(board, ChessGame.Side.SOUTH, null);
		XiangqiKingPiece king1 = new XiangqiKingPiece(board, ChessGame.Side.NORTH, null);
		XiangqiKingPiece king2 = new XiangqiKingPiece(board, ChessGame.Side.NORTH, null);
		XiangqiKingPiece king3 = new XiangqiKingPiece(board, ChessGame.Side.NORTH, null);
		ElephantPiece elephant1 = new ElephantPiece(board, ChessGame.Side.NORTH, null);
		ElephantPiece elephant2 = new ElephantPiece(board, ChessGame.Side.SOUTH, null);
		ElephantPiece elephant3 = new ElephantPiece(board, ChessGame.Side.SOUTH, null);
		HorsePiece horse1 = new HorsePiece(board, ChessGame.Side.NORTH, null);
		GuardPiece guard1 = new GuardPiece(board, ChessGame.Side.SOUTH, null);
		GuardPiece guard2 = new GuardPiece(board, ChessGame.Side.SOUTH, null);
		GuardPiece guard3 = new GuardPiece(board, ChessGame.Side.NORTH, null);
		PawnPiece pawn1 = new PawnPiece(board, ChessGame.Side.NORTH, null);
		PawnPiece pawn2 = new PawnPiece(board, ChessGame.Side.NORTH, null);
		HorsePiece horse2 = new HorsePiece(board, ChessGame.Side.SOUTH, null);
		HorsePiece horse3 = new HorsePiece(board, ChessGame.Side.SOUTH, null);
		HorsePiece horse4 = new HorsePiece(board, ChessGame.Side.NORTH, null);
		SoldierPiece soldier1 = new SoldierPiece(board, ChessGame.Side.SOUTH, null);
		SoldierPiece soldier2 = new SoldierPiece(board, ChessGame.Side.SOUTH, null);
		SoldierPiece soldier3 = new SoldierPiece(board, ChessGame.Side.SOUTH, null);
		board.addPiece(cannon1, 5, 3);
		board.addPiece(rook1, 5, 4);
		board.addPiece(rook2, 5, 5);
		board.addPiece(rook3, 5, 2);
		board.addPiece(king1, 5, 0);
		board.addPiece(king2, 1, 3);
		board.addPiece(king3, 1, 4);
		board.addPiece(elephant1, 3, 3);
		board.addPiece(elephant2, 7, 2);
		board.addPiece(elephant3, 5, 6);
		board.addPiece(horse1, 3, 4);
		board.addPiece(horse2, 6, 7);
		board.addPiece(horse3, 2, 2);
		board.addPiece(horse4, 4, 2);
		board.addPiece(guard1, 9, 5);
		board.addPiece(guard2, 8, 3);
		board.addPiece(guard3, 0, 4);
		board.addPiece(pawn1, 8, 4);
		board.addPiece(pawn2, 8, 6);
		board.addPiece(soldier1, 3, 2); //crossed the river
		board.addPiece(soldier2, 1, 5); 
		board.addPiece(soldier3, 2, 5); 


		
		/** Tests for the CannonPiece **/
		
		//1. When the piece moves into an empty space
		assertEquals(cannon1.isLegalNonCaptureMove(4, 3), true);
		//There is no piece at this location. Not a capture move
		
		//2. When the piece tries to capture a piece of the same player 
		assertEquals(cannon1.isLegalNonCaptureMove(5, 5), false);
		//There is a piece at this location. Non a non-capture move 
		
		//3. When the piece tries to capture an opponent piece 
		assertEquals(cannon1.isLegalNonCaptureMove(5, 0), false);
		//There is a piece at this location. Non a non-capture move 
	
		//4. When this is not a legal move for the piece 
		assertEquals(cannon1.isLegalNonCaptureMove(5, 1), false);
		//Although there is no piece at the landing square, the cannon piece attempts to skip another piece. 
		
		
		/** Tests for the ElephantPiece **/
		
		//1. When the piece moves into an empty space
		assertEquals(elephant2.isLegalNonCaptureMove(9, 0), true);
		//There is no piece at this location. Not a capture move
		
		//2. When the piece tries to capture a piece of the same player 
		assertEquals(elephant2.isLegalNonCaptureMove(5, 4), false);
		//There is a piece at this location. Not a non-capture move
		
		//3. When the piece tries to capture an opponent piece 
		assertEquals(elephant2.isLegalNonCaptureMove(5, 0), false);
		//There is a piece at this location. Non a non-capture move 
		
		//4. When this is not a legal move for the piece 
		assertEquals(elephant2.isLegalCaptureMove(7, 0), false);
		//Although there is no piece at the landing square, this is not a legal move for an elephant piece
		
		/** Test for the GuardPiece */
		
		//1. When the piece moves into an empty space
		assertEquals(guard2.isLegalNonCaptureMove(7, 4), true);
		//There is no piece at this location. Not a capture move
		
		//2. When the piece tries to capture a piece of the same player 
		assertEquals(guard3.isLegalNonCaptureMove(1, 3), false);
		//There is a piece at this location. Not a non-capture move
		
		//3. When the piece tries to capture an opponent piece 
		assertEquals(guard1.isLegalNonCaptureMove(8, 4), false);
		//There is a piece at this location. Not a non-capture move
		
		//4. When this is not a legal move for the piece 
		assertEquals(guard1.isLegalNonCaptureMove(8, 5), false);
		//Although there is no piece at the landing square, this is not a legal move for a guard piece
		
		/** Test for the HorsePiece */
		
		//1. When the piece moves into an empty space
		assertEquals(horse2.isLegalNonCaptureMove(8, 8), true);
		//There is no piece at this location. Not a capture move
		
		//2. When the piece tries to capture a piece of the same player 
		assertEquals(horse2.isLegalNonCaptureMove(5, 5), false);
		//There is a piece at this location. Not a non-capture move
		
		//3. When the piece tries to capture an opponent piece 
		assertEquals(horse2.isLegalNonCaptureMove(8, 6), false);
		//There is a piece at this location. Not a non-capture move
		
		//4. When this is not a legal move for the piece 
		assertEquals(horse2.isLegalNonCaptureMove(6, 5), false);
		//Although there is no piece at the landing square, this is not a legal move for a horse piece
		
		/** Test for the SoldierPiece */
		
		//1. When the piece moves into an empty space
		assertEquals(soldier1.isLegalNonCaptureMove(3, 1), true);
		//There is no piece at this location. Not a capture move
		
		//2. When the piece tries to capture a piece of the same player 
		assertEquals(soldier1.isLegalNonCaptureMove(2, 2), false);
		//There is a piece at this location. Not a non-capture move
		
		//3. When the piece tries to capture an opponent piece 
		assertEquals(soldier1.isLegalNonCaptureMove(3, 3), false);
		//There is a piece at this location. Not a non-capture move
		
		//4. When this is not a legal move for the piece 
		assertEquals(soldier1.isLegalNonCaptureMove(4, 1), false);
		//Although there is no piece at the landing square, this is not a legal move for a horse piece
		
		/** Test for the XiangqiKingPiece */
		
		//1. When the piece moves into an empty space
		assertEquals(king3.isLegalNonCaptureMove(2, 4), true);
		//There is no piece at this location. Not a capture move
		
		//2. When the piece tries to capture a piece of the same player 
		assertEquals(king3.isLegalNonCaptureMove(1, 3), false);
		//There is a piece at this location. Not a non-capture move
		
		//3. When the piece tries to capture an opponent piece 
		assertEquals(king3.isLegalNonCaptureMove(1, 5), false);
		//There is a piece at this location. Not a non-capture move
		
		//4. When this is not a legal move for the piece 
		assertEquals(king3.isLegalNonCaptureMove(2, 3), false);
		//Although there is no piece at the landing square, this is not a legal move for a horse piece
	}
}
