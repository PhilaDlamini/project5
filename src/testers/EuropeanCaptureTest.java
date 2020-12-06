package testers;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import chess.ChessGame;
import chess.EuropeanChess;
import europeanpieces.BishopPiece;
import europeanpieces.KingPiece;
import europeanpieces.KnightPiece;
import europeanpieces.PawnPiece;
import europeanpieces.QueenPiece;
import europeanpieces.RookPiece;
import graphics.SwingChessBoard;
import graphics.SwingEuropeanChessDisplay;

/**
 * Tests European pieces for correct capture and non-capture moves
 * @author Phila Dlamini
 *
 */
class EuropeanCaptureTest {

	/**
	 * Tests if this is a legal capture move for a piece in the game
	 */
	@Test
	void testIsLegalCaptureMove() {
		/* First create a chess board that has 8 row and columns and put the indicated pieces on it
		 */
		SwingChessBoard board = new SwingChessBoard(new SwingEuropeanChessDisplay(), new EuropeanChess());
		QueenPiece queenSouth = new QueenPiece(board, ChessGame.Side.SOUTH, null);
		RookPiece rookSouth1 = new RookPiece(board, ChessGame.Side.SOUTH, null);
		RookPiece rookSouth2 = new RookPiece(board, ChessGame.Side.SOUTH, null);
		BishopPiece bishopNorth1 = new BishopPiece(board, ChessGame.Side.NORTH, null);
		BishopPiece bishopNorth2 = new BishopPiece(board, ChessGame.Side.NORTH, null);
		KingPiece kingSouth1 = new KingPiece(board, ChessGame.Side.SOUTH, null);
		KingPiece kingSouth2 = new KingPiece(board, ChessGame.Side.SOUTH, null);
		PawnPiece pawnSouth1 = new PawnPiece(board, ChessGame.Side.SOUTH, null);
		PawnPiece pawnSouth2 = new PawnPiece(board, ChessGame.Side.SOUTH, null);
		PawnPiece pawnNorth1 = new PawnPiece(board, ChessGame.Side.NORTH, null);
		PawnPiece pawnNorth2 = new PawnPiece(board, ChessGame.Side.NORTH, null);
		PawnPiece pawnNorth3 = new PawnPiece(board, ChessGame.Side.NORTH, null);
		KnightPiece knightSouth = new KnightPiece(board, ChessGame.Side.SOUTH, null);
		KnightPiece knightSouth2 = new KnightPiece(board, ChessGame.Side.SOUTH, null);
		board.addPiece(queenSouth, 3, 0);
		board.addPiece(rookSouth1, 1, 2);
		board.addPiece(rookSouth2, 5, 6);
		board.addPiece(bishopNorth1, 1, 6);
		board.addPiece(bishopNorth2, 3, 4);
		board.addPiece(kingSouth1, 7, 6);
		board.addPiece(kingSouth2, 2, 4);
		board.addPiece(pawnSouth1, 6, 2);
		board.addPiece(pawnSouth2, 6, 6);
		board.addPiece(pawnNorth1, 5, 2);
		board.addPiece(pawnNorth2, 5, 3);
		board.addPiece(pawnNorth3, 6, 4);
		board.addPiece(knightSouth, 7, 1);
		board.addPiece(knightSouth2, 6, 3);
		
		/* Test cases for the QueenPiece */
		
		//1. Where the QueenPiece moves into an empty space 
		assertEquals(queenSouth.isLegalCaptureMove(0, 0), false); //There is nothing at this position
		
		//2. Where the QueenPiece moves into a space containing a piece of the same player
		assertEquals(queenSouth.isLegalCaptureMove(1, 2), false); 
		//The queen tries to capture rookSouth1 at (1, 2), which belongs to the same player. Not allowed
		
		//3. Where the QueenPiece moves into a square containing a piece of the other player
		assertEquals(queenSouth.isLegalCaptureMove(5, 2), true); 
		//The queen captures pawnNorth1 at(5, 2). Legal as it does not belong to the same player 

		//4. When this is not a legal move for a QueenPiece to start with 
		assertEquals(queenSouth.isLegalCaptureMove(1, 6), false); 
		//The queen tries to capture bishopNorth1 at (1, 6), which belongs to the other player, but this is not a legal move
		
		
		/* Test case for the RookPiece */
		
		//1. Where the RookPiece moves into an empty space 
		assertEquals(rookSouth1.isLegalCaptureMove(1, 0), false); //There is nothing at this position
		
		//2. Where the RookPiece moves into a space containing a piece of the same player
		assertEquals(rookSouth2.isLegalCaptureMove(7, 6), false); 
		//The rook tries to capture kingSouth1 at (7, 6), which belongs to the same player. Not allowed
		
		//3. Where the RookPiece moves into a square containing a piece of the other player
		assertEquals(rookSouth2.isLegalCaptureMove(1, 6), true); 
		//The rook captures bishopNorth1 at (1, 6). Legal as it does not belong to the same player

		//4. When this is not a legal move for a RookPiece to start with 
		assertEquals(queenSouth.isLegalCaptureMove(1, 6), false); 
		//The queen tries to capture bishopNorth2 at (3, 4), which belongs to the other player, but a rook piece does not move diagonally
		

		/* Test case for the BishopPiece */
		
		//1. Where the BishopPiece moves into an empty space 
		assertEquals(bishopNorth1.isLegalCaptureMove(0, 7), false); //There is nothing at this position
		
		//2. Where the BishopPiece moves into a space containing a piece of the same player
		assertEquals(bishopNorth1.isLegalCaptureMove(3, 4), false); 
		//The rook tries to capture bishopNorth2 at (3, 4), which belongs to the same player. Not allowed
		
		//3. Where the BishopPiece moves into a square containing a piece of the other player
		assertEquals(bishopNorth2.isLegalCaptureMove(5, 6), true); 
		assertEquals(bishopNorth2.isLegalCaptureMove(1, 2), true); 
		//bishopNorth2 captures rookSouth2 at (5, 6) and rookSouth1 at (1, 2). Legal as the pieces do not belong to the same player

		//4. When this is not a legal move for a BishopPiece to start with 
		assertEquals(bishopNorth1.isLegalCaptureMove(7, 6), false); 
		//bishopNorth1 tries to capture kingSouth1 at (7, 6), which belongs to the other player, but a bishop piece does not move vertically
		
		
		/* Test case for the KingPiece */
		
		//1. Where the KingPiece moves into an empty space 
		assertEquals(kingSouth1.isLegalCaptureMove(7, 7), false); //There is nothing at this position
		
		//2. Where the KingPiece moves into a space containing a piece of the same player
		assertEquals(kingSouth1.isLegalCaptureMove(6, 6), false); 
		//The rook tries to capture pawnSouth2 at (6, 6), which belongs to the same player. Not allowed
		
		//3. Where the KingPiece moves into a square containing a piece of the other player
		assertEquals(kingSouth2.isLegalCaptureMove(3, 4), true); 
		//kingSouth2 captures bishopNorth2 at (3, 4). Legal as it does not belong to the same player

		//4. When this is not a legal move for a KingPiece to start with 
		assertEquals(kingSouth2.isLegalCaptureMove(5, 2), false); 
		//kingSouth2 tries to capture pawnNorth1 at (5, 2), which belongs to the other player, but this is not a
		//a legal move for a king to make 
		
		/* Test case for the KnightPiece */
		
		//1. Where the KnightPiece moves into an empty space 
		assertEquals(knightSouth.isLegalCaptureMove(5, 0), false); //There is nothing at this position
		
		//2. Where the KnightPiece moves into a space containing a piece of the same player
		assertEquals(knightSouth.isLegalCaptureMove(6, 3), false); 
		//knightSouth tries to capture knightSouth1 at (6, 3), which belongs to the same player. Not allowed
		
		//3. Where the KnightPiece moves into a square containing a piece of the other player
		assertEquals(knightSouth.isLegalCaptureMove(5, 2), true); 
		//knightSouth captures pawnNorth1 at (5, 2). Legal as it does not belong to the same player

		//4. When this is not a legal move for a KnightPiece to start with 
		assertEquals(knightSouth.isLegalCaptureMove(5, 3), false); 
		//knightSouth tries to capture pawnNorth2 at (5, 3), which belongs to the other player, but this is not a
		//a legal move for a Knight to make 
		

		/* Test case for the PawnPiece */
		
		//1. Where the PawnPiece moves into an empty space 
		assertEquals(pawnNorth1.isLegalCaptureMove(4,  0), false); 
		//There is nothing at this position (which also means this is an illegal move for a pawn to make)
		
		//2. Where the PawnPiece moves into a space containing a piece of the same player
		assertEquals(pawnNorth2.isLegalCaptureMove(6, 4), false); 
		//pawnNorth2 tries to capture pawnNorth3 at (6, 4), which belongs to the same player. Not allowed
		
		//3. Where the PawnPiece moves into a square containing a piece of the other player
		assertEquals(pawnSouth1.isLegalCaptureMove(5, 3), true); 
		//pawnSouth1 captures pawnNorth2 at (5, 3). Legal as it does not belong to the same player

		//4. When this is not a legal move for a PawnPiece to start with 
		assertEquals(pawnNorth2.isLegalCaptureMove(6, 3), false); 
		//pawnNorth2 tries to capture knightSouth2 at (6, 3), which belongs to the other player, but this is not a
		//a legal move for a PawnPiece to make (a vertical move)

	}

	
	/**
	 * Tests if this is legal non capture move a piece in the game 

	 */
	@Test
	void testIsLegalNonCaptureMove() {
		
		/* First create a chess board that has 8 row and columns and put the indicated pieces on it
		 */
		SwingChessBoard board = new SwingChessBoard(new SwingEuropeanChessDisplay(), new EuropeanChess());
		QueenPiece queenSouth = new QueenPiece(board, ChessGame.Side.SOUTH, null);
		RookPiece rookSouth1 = new RookPiece(board, ChessGame.Side.SOUTH, null);
		RookPiece rookSouth2 = new RookPiece(board, ChessGame.Side.SOUTH, null);
		BishopPiece bishopNorth1 = new BishopPiece(board, ChessGame.Side.NORTH, null);
		BishopPiece bishopNorth2 = new BishopPiece(board, ChessGame.Side.NORTH, null);
		KingPiece kingSouth1 = new KingPiece(board, ChessGame.Side.SOUTH, null);
		KingPiece kingSouth2 = new KingPiece(board, ChessGame.Side.SOUTH, null);
		PawnPiece pawnSouth1 = new PawnPiece(board, ChessGame.Side.SOUTH, null);
		PawnPiece pawnSouth2 = new PawnPiece(board, ChessGame.Side.SOUTH, null);
		PawnPiece pawnNorth1 = new PawnPiece(board, ChessGame.Side.NORTH, null);
		PawnPiece pawnNorth2 = new PawnPiece(board, ChessGame.Side.NORTH, null);
		PawnPiece pawnNorth3 = new PawnPiece(board, ChessGame.Side.NORTH, null);
		KnightPiece knightSouth = new KnightPiece(board, ChessGame.Side.SOUTH, null);
		KnightPiece knightSouth2 = new KnightPiece(board, ChessGame.Side.SOUTH, null);
		board.addPiece(queenSouth, 3, 0);
		board.addPiece(rookSouth1, 1, 2);
		board.addPiece(rookSouth2, 5, 6);
		board.addPiece(bishopNorth1, 1, 6);
		board.addPiece(bishopNorth2, 3, 4);
		board.addPiece(kingSouth1, 7, 6);
		board.addPiece(kingSouth2, 2, 4);
		board.addPiece(pawnSouth1, 6, 2);
		board.addPiece(pawnSouth2, 6, 6);
		board.addPiece(pawnNorth1, 5, 2);
		board.addPiece(pawnNorth2, 5, 3);
		board.addPiece(pawnNorth3, 6, 4);
		board.addPiece(knightSouth, 7, 1);
		board.addPiece(knightSouth2, 6, 3);
		
		
		/* Test cases for the QueenPiece */
		
		//1. Where the QueenPiece moves into an empty space 
		assertEquals(queenSouth.isLegalNonCaptureMove(0, 0), true); 
		//Legal as there is no piece here
		
		//2. Where the QueenPiece moves into a space containing a piece of the same player
		assertEquals(queenSouth.isLegalNonCaptureMove(1, 2), false); 
		//There is a piece at this location. Not allowed
		
		//3. Where the QueenPiece moves into a square containing a piece of the other player
		assertEquals(queenSouth.isLegalNonCaptureMove(5, 2), false); 
		//There is a piece at this location. Not allowed

		//4. When this is not a legal move for a QueenPiece to start with 
		assertEquals(queenSouth.isLegalNonCaptureMove(6, 1), false); 
		//Although there is no piece here, this is an illegal move

		
		/* Test case for the RookPiece */
		
		//1. Where the RookPiece moves into an empty space 
		assertEquals(rookSouth1.isLegalNonCaptureMove(1, 0), true); 
		//Legal as there is no piece here
		
		//2. Where the RookPiece moves into a space containing a piece of the same player
		assertEquals(rookSouth2.isLegalNonCaptureMove(6, 6), false); 
		//There is a piece at this location. Not allowed
		
		//3. Where the RookPiece moves into a square containing a piece of the other player
		assertEquals(rookSouth2.isLegalNonCaptureMove(1, 6), false); 
		//There is a piece at this location. Not allowed

		//4. When this is not a legal move for a RookPiece to start with 
		assertEquals(rookSouth1.isLegalNonCaptureMove(4, 5), false); 
		//Although there is no piece here, this is an illegal move
		

		/* Test case for the BishopPiece */
		
		//1. Where the BishopPiece moves into an empty space 
		assertEquals(bishopNorth1.isLegalNonCaptureMove(0, 7), true); 
		//Legal as there is no piece here
		
		//2. Where the BishopPiece moves into a space containing a piece of the same player
		assertEquals(bishopNorth1.isLegalNonCaptureMove(3, 4), false); 
		//There is a piece at this location. Not allowed
		
		//3. Where the BishopPiece moves into a square containing a piece of the other player
		assertEquals(bishopNorth2.isLegalNonCaptureMove(5, 6), false); 
		assertEquals(bishopNorth2.isLegalNonCaptureMove(1, 2), false); 
		//There are pieces at these locations. Not allowed

		//4. When this is not a legal move for a BishopPiece to start with 
		assertEquals(bishopNorth1.isLegalNonCaptureMove(5, 5), false); 
		//Although there is no piece here, this is an illegal move
		
		
		/* Test case for the KingPiece */
		
		//1. Where the KingPiece moves into an empty space 
		assertEquals(kingSouth1.isLegalNonCaptureMove(7, 7), true); 
		//Legal as there is no piece here
		
		//2. Where the KingPiece moves into a space containing a piece of the same player
		assertEquals(kingSouth1.isLegalNonCaptureMove(6, 6), false); 
		//There is a piece at this location. Not allowed

		//3. Where the KingPiece moves into a square containing a piece of the other player
		assertEquals(kingSouth2.isLegalNonCaptureMove(3, 4), false); 
		//There is a piece at this location. Not allowed

		//4. When this is not a legal move for a KingPiece to start with 
		assertEquals(kingSouth2.isLegalNonCaptureMove(2, 0), false); 
		//Although there is no piece here, this is an illegal move

		/* Test case for the KnightPiece */
		
		//1. Where the KnightPiece moves into an empty space 
		assertEquals(knightSouth.isLegalNonCaptureMove(5, 0), true); 
		//Legal as there is no piece here

		//2. Where the KnightPiece moves into a space containing a piece of the same player
		assertEquals(knightSouth.isLegalNonCaptureMove(6, 3), false); 
		//There is a piece at this location. Not allowed
		
		//3. Where the KnightPiece moves into a square containing a piece of the other player
		assertEquals(knightSouth.isLegalNonCaptureMove(5, 2), false); 
		//There is a piece at this location. Not allowed

		//4. When this is not a legal move for a KnightPiece to start with 
		assertEquals(knightSouth.isLegalNonCaptureMove(4, 0), false); 
		//Although there is no piece here, this is an illegal move

		/* Test case for the PawnPiece */
		
		//1. Where the PawnPiece moves into an empty space 
		assertEquals(pawnNorth3.isLegalNonCaptureMove(7, 4), true); 
		//Legal as there is no piece here
		
		//2. Where the PawnPiece moves into a space containing a piece of the same player
		assertEquals(pawnNorth2.isLegalNonCaptureMove(6, 4), false); 
		//There is a piece at this location. Not allowed
		
		//3. Where the PawnPiece moves into a square containing a piece of the other player
		assertEquals(pawnSouth1.isLegalNonCaptureMove(5, 3), false); 
		//There is a piece at this location. Not allowed

		//4. When this is not a legal move for a PawnPiece to start with 
		assertEquals(pawnNorth2.isLegalNonCaptureMove(7, 5), false); 
		//Although there is no piece here, this is an illegal move

	}
}
