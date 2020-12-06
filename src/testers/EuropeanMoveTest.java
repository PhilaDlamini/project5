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
 * Tests European chess pieces for correct movements
 * @author Phila Dlamini
 *
 */

class EuropeanMoveTest {
	
	/**
	 * Tests the QueenPiece's isLegalMove() method
	 * A QueenPiece can move any number of spaces in all straight directions (vertical, horizontal and diagonal)
	 * The must be no piece in-between the start and end positions, and the landing square cannot contain a piece of the same player
	 */
	@Test
	void testQueenIsLegalMove() {
		
		
		/* First create a chess board that has 8 row and columns and put these pieces on it:
		 * A QueenPiece on this player's side on square (0,0)
		 * A QueenPiece on this player's side on square (0,7)
		 * A QueenPiece on this player's side on square (7,7)
		 * Another QueenPiece on this player's side on square (2, 4)
		 * A PawnPiece on the other player's side on square (4, 4)
		 * A RookPiece on this player's side on square (5, 0)
		 */
		SwingChessBoard board = new SwingChessBoard(new SwingEuropeanChessDisplay(), new EuropeanChess());
		QueenPiece queen1 = new QueenPiece(board, ChessGame.Side.SOUTH , null);
		QueenPiece queen2 = new QueenPiece(board, ChessGame.Side.SOUTH , null); 
		QueenPiece queen3 = new QueenPiece(board, ChessGame.Side.SOUTH , null); 
		QueenPiece queen4 = new QueenPiece(board, ChessGame.Side.SOUTH , null);
		PawnPiece pawn = new PawnPiece(board, ChessGame.Side.NORTH , null); 
		RookPiece rook = new RookPiece(board, ChessGame.Side.SOUTH , null);
		board.addPiece(queen1, 0, 0); //The top left square
		board.addPiece(queen2, 0, 7); //The top right square
		board.addPiece(queen3, 7, 7); //The bottom right square
		board.addPiece(queen4, 2, 4); 
		board.addPiece(pawn, 4, 4); //The square around the middle 
		board.addPiece(rook, 5, 0); 
				
		/*Test case where the Queen is moving horizontally*/
		
		//A. When the queen is taking one step forward
		assertEquals(queen1.isLegalMove(0, 1), true);
		//There is no piece on the path, and the steps for a queen are unlimited, the method returns true
		
		//When the queen is taking one step backwards
		assertEquals(queen2.isLegalMove(0, 6), true);
		//There is no piece on the path, and the steps for a queen are unlimited, the method returns true

		//b. When the queen is taking more than one step forward
		assertEquals(queen1.isLegalMove(0, 5), true);
		//There is no piece on the path, and the steps for a queen are unlimited. The method returns true

		//B. When the queen is taking more than one step backwards
		assertEquals(queen2.isLegalMove(0, 2), true);
		//There is no piece on the path, and the steps for a queen are unlimited. The method returns true
		
		/* Test case where the queen is moving vertically*/
		
		//A. When the piece is moving only one step down
		assertEquals(queen1.isLegalMove(1, 0), true);
		//There is no piece in the range of squares, and the queen moves unlimited steps
		
		//When the piece is moving only one step up
		assertEquals(queen3.isLegalMove(6, 7), true);
		//There is no piece in the range of squares, and the queen moves unlimited steps
				
		//B. When the piece is moving multiple steps down
		assertEquals(queen1.isLegalMove(6, 0), false);
		//The RookPiece is at (5, 0), and so the queen cannot move from (0, 0) to (6, 0)
		
		//When the piece is moving more than one step up
		assertEquals(queen3.isLegalMove(1, 7), true);
		//There is no piece in the range of squares (the only piece given is at (0, 7), and the queen moves unlimited steps
		
		/* Test case where the piece is moving diagonally:: the negative diagonal axis */
		
		//A. When the piece is moving only one step diagonally down
		assertEquals(queen1.isLegalMove(1, 1), true);
		//There is no other piece is this range 
		
		//When the piece is moving only one step diagonally up
		assertEquals(queen3.isLegalMove(6, 6), true);
		//There is no other piece is this range 
		
		//B. When the piece is moving more than one step diagonally down
		assertEquals(queen1.isLegalMove(6,6), false);
		assertEquals(queen3.isLegalMove(1, 1), false);
		//The PawnPiece is at (4, 4), so moving from (0, 0) to (6, 6), or from (7, 7) to (1, 1) for the queen is not allowed. 
		
		//C. We perform similar tests in the positive gradient diagonal axis. queen4 is currently at (2, 4)
		
		//i. When the piece moves one step diagonally up
		assertEquals(queen4.isLegalMove(1, 5), true);
		//The piece moves from (2, 4) to (1, 5), and there are no pieces in between
		
		//When the piece is moving one step diagonally down
		assertEquals(queen4.isLegalMove(3, 3), true);
		//The piece moves from (2, 4) to (3, 3), and there are no pieces in between
		
		//ii. When the piece moves more than one step diagonally up
		assertEquals(queen4.isLegalMove(0, 6), true);
		//The piece moves from (2, 4) to (0, 6), and there are no pieces in between
		
		//When the piece moves more then one step diagonally down
		assertEquals(queen4.isLegalMove(5, 7), true);
		//The piece moves from (2, 4) to (4, 2), and there are no pieces in between
	
		/* Test case where the queen tries to move not in a straight line */
		//The queen piece is only allowed to move in a straight line either horizontally, vertically or diagonally, and so not moving in  
		//a straight line is not allowed
		//Since this queen1 is currently at (0, 0), queen2 at (0, 7), and queen3 at (7, 7), all these moves are not legal
		assertEquals(queen1.isLegalMove(5, 3), false); //Not a straight line 
		assertEquals(queen2.isLegalMove(3, 1), false); //Not a straight line 
		assertEquals(queen3.isLegalMove(4, 1), false); //Not a straight line
		
		/* Tests cases where there is another piece at the destination*/
		
		//1. There queen cannot move into another square with a player of the same side
		assertEquals(queen1.isLegalMove(5, 0), false);
		//The RookPiece at (5, 0) belongs to the same player as this one
		
		assertEquals(queen1.isLegalMove(4, 4), true);
		//But since the PawnPiece at (4, 4) belongs to the other player, this is a legal move for the queen
		
	}

	/**
	 * Tests the RookPiece's isLegalMove() method
	 * A RookPiece can move any number of steps in the vertical and horizontal directions
	 * The must be no piece in-between the start and end positions, and the landing square cannot contain a piece of the same player
	 */
	@Test
	void testRookIsLegalMove() {
		
		/* First create a chess board that has 8 row and columns and put these pieces on it:
		 * A RookPiece on this player's side on square (0,0)
		 * A RookPiece on this player's side on square (0,7)
		 * A RookPiece on this player's side on square (7,7)
		 * Another RookPiece on this player's side on square (2, 4)
		 * A PawnPiece on the other player's side on square (4, 0)
		 * A QueenPiece on this player's side on square (5, 0)
		 */
		SwingChessBoard board = new SwingChessBoard(new SwingEuropeanChessDisplay(), new EuropeanChess());
		RookPiece rook1 = new RookPiece(board, ChessGame.Side.SOUTH , null);
		RookPiece rook2 = new RookPiece(board, ChessGame.Side.SOUTH , null); //For testing reverse directions
		RookPiece rook3 = new RookPiece(board, ChessGame.Side.SOUTH , null); //For testing reverse directions
		RookPiece rook4 = new RookPiece(board, ChessGame.Side.SOUTH , null);
		QueenPiece queen = new QueenPiece(board, ChessGame.Side.SOUTH , null);
		PawnPiece pawn = new PawnPiece(board, ChessGame.Side.NORTH , null); //belongs to the other player
		board.addPiece(rook1, 0, 0); //The topRight square
		board.addPiece(rook2,  0,  7); //The topLeft square
		board.addPiece(rook3,  7,  7); //The bottomRight square
		board.addPiece(rook4, 2, 4); 
		board.addPiece(pawn, 4, 0); //5th square down on the first row on the left
		board.addPiece(queen, 5, 0); //6th square down on first row on the left
				
		/*Test case where the RookPiece is moving horizontally*/
		
		//A. When the rook is taking one step forward
		assertEquals(rook1.isLegalMove(0, 1), true);
		//There is no piece on the path, and the rook can move any number of steps, the method returns true
		
		//When the rook is taking one step backwards
		assertEquals(rook2.isLegalMove(0, 6), true);
		//The the method returns true

		//b. When the rook is taking more than one step forward
		assertEquals(rook1.isLegalMove(0, 5), true);
		//There is no piece on the path, and the steps for a rook are unlimited. The method returns true

		//B. When the rook is taking more than one step backwards
		assertEquals(rook2.isLegalMove(0, 2), true);
		//There is no piece on the path, and the steps for a rook are unlimited. The method returns true
		
		/* Test case where the rook is moving vertically*/
		
		//A. When the piece is moving only one step down
		assertEquals(rook1.isLegalMove(1, 0), true);
		//There is no piece in the range of squares, and the rook moves unlimited steps
		
		//When the piece is moving only one step up
		assertEquals(rook3.isLegalMove(6, 7), true);
		//There is no piece in the range of squares, and the king moves unlimited steps
				
		//B. When the piece is moving multiple steps down
		assertEquals(rook1.isLegalMove(6, 0), false);
		//The QueenPiece is at (5, 0), and so the rook cannot move from (0, 0) to (6, 0)
		
		//When the piece is moving more than one step up
		assertEquals(rook3.isLegalMove(1, 7), true);
		//There is no piece in the range of squares (the only piece given is at (0, 7), and the rook moves unlimited steps
		
		/* Test case where the piece is moving diagonally
		 * Always false because since the RookPiece does not move diagonally
		 * We start with tests in the negative gradient diagonal axis 
		 */
		
		//A. When the piece is moving only one step diagonally down
		assertEquals(rook1.isLegalMove(1, 1), false);
		
		//When the piece is moving only one step diagonally up
		assertEquals(rook3.isLegalMove(6, 6), false);
		
		//B. When the piece is moving more than one step diagonally down
		assertEquals(rook1.isLegalMove(6,6), false);
		assertEquals(rook3.isLegalMove(1, 1), false);
		
		//C. We perform similar tests in the positive gradient diagonal axis. rook4 is currently at (2, 4)
		
		//i. When the piece moves one step diagonally up
		assertEquals(rook4.isLegalMove(1, 5), false);
		
		//When the piece is moving one step diagonally down
		assertEquals(rook4.isLegalMove(3, 3), false);
		
		//ii. When the piece moves more than one step diagonally up
		assertEquals(rook4.isLegalMove(0, 6), false);
		
		//When the piece moves more then one step diagonally down
		assertEquals(rook4.isLegalMove(5, 7), false);
	
		/* Test case where the queen tries to move not in a straight line */
		//The RookPiece is only allowed to move in a straight line either horizontally or vertically, and so not moving in a straight line is not allowed
		//Since rook1 is currently at (0, 0), rook2 at (0, 7), and rook3 at (7, 7), all these moves are not legal
		assertEquals(rook1.isLegalMove(5, 3), false); //Not a straight line 
		assertEquals(rook2.isLegalMove(3, 1), false); //Not a straight line 
		assertEquals(rook3.isLegalMove(4, 1), false); //Not a straight line
		
		/* Tests cases where there is another piece at the destination*/
		
		//1. The rook cannot move into another square with a player of the same side
		assertEquals(rook1.isLegalMove(5, 0), false);
		//The QueenPiece at (5, 0) belongs to the same player as this one
		
		assertEquals(rook1.isLegalMove(4, 0), true);
		//But since the PawnPiece at (4, 0) belongs to the other player, this is a legal move for the rook
		
	}

	/**
	 * Tests the BishopPiece's isLegalMove() method
	 * A BishopPiece can move any number of steps in the diagonal direction
	 * The must be no piece in-between the start and end positions, and the landing square cannot contain a piece of the same player
	 */
	@Test
	void testBishoIsLegalMove() {
		
		/* First create a chess board that has 8 row and columns and put these pieces on it:
		 * A BishopPiece on this player's side on square (0,0)
		 * A BishopPiece on this player's side on square (0,7)
		 * A BishopPiece on this player's side on square (7,7)
		 * Another BishopPiece on this player's side on square (4, 4)
		 * A PawnPiece on the other player's side on square (7, 0)
		 * A QueenPiece on this player's side on square (1, 7)
		 */
		SwingChessBoard board = new SwingChessBoard(new SwingEuropeanChessDisplay(), new EuropeanChess());
		BishopPiece bishop1 = new BishopPiece(board, ChessGame.Side.SOUTH , null);
		BishopPiece bishop2 = new BishopPiece(board, ChessGame.Side.SOUTH , null); //For testing reverse directions
		BishopPiece bishop3 = new BishopPiece(board, ChessGame.Side.SOUTH , null); //For testing reverse directions
		BishopPiece bishop4 = new BishopPiece(board, ChessGame.Side.SOUTH , null);
		QueenPiece queen = new QueenPiece(board, ChessGame.Side.SOUTH , null);
		RookPiece rook = new RookPiece(board, ChessGame.Side.NORTH , null); //belongs to the other player
		board.addPiece(bishop1, 0, 0); //The top right square
		board.addPiece(bishop2, 0, 7); //The top left square
		board.addPiece(bishop3, 7, 7); //The bottom right square
		board.addPiece(bishop4, 4, 4); 
		board.addPiece(rook, 7, 0); //8th square down on the first row on the left
		board.addPiece(queen, 1, 7); 
				
		/*Test case where the BishopPiece is moving horizontally
		 *Always returns false as a bishop piece does not move horizontally
		 */
		
		//A. When the bishop is taking one step forward
		assertEquals(bishop1.isLegalMove(0, 1), false);
		
		//When the bishop is taking one step backwards
		assertEquals(bishop2.isLegalMove(0, 6), false);

		//b. When the bishop is taking more than one step forward
		assertEquals(bishop1.isLegalMove(0, 5), false);

		//B. When the bishop is taking more than one step backwards
		assertEquals(bishop2.isLegalMove(0, 2), false);
		
		/* Test case where the bishop is moving vertically
		 * Always returns false as a bishop piece does not move vertically*/
		
		//A. When the piece is moving only one step down
		assertEquals(bishop1.isLegalMove(1, 0), false);
		
		//When the piece is moving only one step up
		assertEquals(bishop3.isLegalMove(6, 7), false);
				
		//B. When the piece is moving multiple steps down
		assertEquals(bishop1.isLegalMove(6, 0), false);
		
		//When the piece is moving more than one step up
		assertEquals(bishop3.isLegalMove(1, 7), false);
		
		/* Test case where the bishop piece is moving diagonally
		 * We start with tests in the negative gradient diagonal axis 
		 */
		
		//A. When the bishop piece is moving only one step diagonally down
		assertEquals(bishop1.isLegalMove(1, 1), true);
		//There is no other piece in the range of spaces moving and the bishop can move any number of steps 
		
		//When the piece is moving only one step diagonally up
		assertEquals(bishop3.isLegalMove(6, 6), true);
		//There is no other piece in the range of spaces moving and the bishop can move any number of steps 
		
		//B. When the piece is moving more than one step diagonally down
		assertEquals(bishop1.isLegalMove(6,6), false);
		//Since there is a piece at (4, 4), this bishop cannot move from (0, 0) to (6, 6)
		
		assertEquals(bishop4.isLegalMove(1, 1), true);
		//The bishop at (4, 4) can to (1, 1), however (as there are no other pieces in the range)

		
		//C. We perform similar tests in the positive gradient diagonal axis. bishop4 is currently at (4, 4)
		
		//i. When the piece moves one step diagonally up
		assertEquals(bishop4.isLegalMove(3, 5), true);
		
		//When the piece is moving one step diagonally down
		assertEquals(bishop4.isLegalMove(5, 3), true);
		
		//ii. When the piece moves more than one step diagonally up
		assertEquals(bishop4.isLegalMove(6, 2), true);
		
		//When the piece moves more then one step diagonally down
		assertEquals(bishop4.isLegalMove(2, 6), true);
	
		/* Test case where the Bishop tries to move not in a straight line */
		//The BishopPiece is only allowed to move in a straight line diagonally, and so not moving in a straight line is not allowed
		//Since bishop1 is currently at (0, 0), bishop2 at (0, 7), and bishop3 at (7, 7), all these moves are not legal
		assertEquals(bishop1.isLegalMove(5, 3), false); //Not a straight line 
		assertEquals(bishop2.isLegalMove(3, 1), false); //Not a straight line 
		assertEquals(bishop3.isLegalMove(4, 1), false); //Not a straight line
		
		/* Tests cases where there is another piece at the destination*/
		
		//1. The bishop cannot move into another square with a player of the same side
		assertEquals(bishop4.isLegalMove(1, 7), false);
		//The QueenPiece at (1, 7) belongs to the same player as this one. Not allowed
		
		assertEquals(bishop2.isLegalMove(7, 0), true);
		//But since the PawnPiece at (7, 0) belongs to the other player, this is a legal move for the bishop
	}

	/**
	 * Tests the KingPiece isLegalMove(int, int) method
	 * A KingPiece can only move one step in the horizontal, vertical and diagonal direction
	 * The must be no piece in-between the start and end positions, and the landing square cannot contain a piece of the same player
	 * A KingPiece can also perform the castle move: where ...
	 */
	@Test
	void testKingIsLegalMove() {
		/* First create a chess board that has 8 row and columns and put these pieces on it:
		 * A KingPiece on this player's side on square (0,0)
		 * A KingPiece on this player's side on square (0,7)
		 * A KingPiece on this player's side on square (7,7)
		 * Another KingPiece on this player's side on square (2, 4)
		 * A QueenPiece on this player's side on square (1, 7)
		 * 
		 * These multiple pieces help us test in both the forward and backwards directions
		 */
		SwingChessBoard board = new SwingChessBoard(new SwingEuropeanChessDisplay(), new EuropeanChess());
		KingPiece king1 = new KingPiece(board, ChessGame.Side.SOUTH , null);
		KingPiece king2 = new KingPiece(board, ChessGame.Side.SOUTH , null); 
		KingPiece king3 = new KingPiece(board, ChessGame.Side.SOUTH , null); 
		KingPiece king4 = new KingPiece(board, ChessGame.Side.SOUTH , null);
		QueenPiece queen = new QueenPiece(board, ChessGame.Side.SOUTH , null);
		board.addPiece(king1, 0, 0); //The top right square
		board.addPiece(king2, 0, 7); //The top left square
		board.addPiece(king3, 7, 7); //The bottom right square
		board.addPiece(king4, 4, 4); 
		board.addPiece(queen, 1, 7);
				
		/*Test case where the KingPiece is moving horizontally*/
		
		//A. When the bishop is taking one step forward
		assertEquals(king1.isLegalMove(0, 1), true);
		//The king is only moving one step, and there is no piece at this position. Allowed
		
		//When the king is taking one step backwards
		assertEquals(king2.isLegalMove(0, 6), true);
		//The king is only moving one step, and there is no piece at this position. Allowed

		//b. When the king is taking more than one step forward
		assertEquals(king1.isLegalMove(0, 5), false);
		//A king is only allowed to move one step. Returns false

		//B. When the king is taking more than one step backwards
		assertEquals(king2.isLegalMove(0, 2), false);
		//A king is only allowed to move one step. Returns false

		/* Test case where the king is moving vertically*/
		
		//A. When the king is moving only one step down
		assertEquals(king1.isLegalMove(1, 0), true);
		//The king is only moving one step, and there is no piece at this position. Allowed
		
		//When the king is moving only one step up
		assertEquals(king3.isLegalMove(6, 7), true);
		//The king is only moving one step, and there is no piece at this position. Allowed

		//B. When the king is moving multiple steps down
		assertEquals(king1.isLegalMove(6, 0), false);
		//A king can only move one step. Not allowed
		
		//When the king is moving more than one step up
		assertEquals(king3.isLegalMove(2, 7), false);
		//A king can only move one step. Not allowed

		
		/* Test case where the king piece is moving diagonally
		 * We start with tests in the negative gradient diagonal axis (top left ->/<- bottom right)
		 */
		
		//A. When the king piece is moving only one step diagonally down
		assertEquals(king1.isLegalMove(1, 1), true);
		//There is no other piece here and the king is moving only one step. Allowed
		
		//When the king is moving only one step diagonally up
		assertEquals(king3.isLegalMove(6, 6), true);
		//There is no other piece here and the king is moving only one step. Allowed
		
		//B. When the king is moving more than one step diagonally down
		assertEquals(king1.isLegalMove(6,6), false);
		//A king can only move one step. Not allowed 
		
		//When the king is moving more then one step diagonally up 
		assertEquals(king3.isLegalMove(1, 1), false);
		//A king can only move one step. Not allowed 
		
		//C. We perform similar tests in the positive gradient diagonal axis. king4 is currently at (4, 4)
		
		//i. When the king moves one step diagonally up
		assertEquals(king4.isLegalMove(3, 5), true);
		//There is no other piece here and the king is moving only one step. Allowed
		
		//When the piece is moving one step diagonally down
		assertEquals(king4.isLegalMove(5, 3), true);
		//There is no other piece here and the king is moving only one step. Allowed
		
		//ii. When the king moves more than one step diagonally down
		assertEquals(king4.isLegalMove(6, 2), false);
		//A king can only move one step. Not allowed 
		
		//When the king moves more then one step diagonally up
		assertEquals(king4.isLegalMove(2, 6), false);
		//A king can only move one step. Not allowed 
	
		/* Test case where the KingPiece tries to move not in a straight line */
		//The KingPiece is only allowed to move in a straight line in all the three directions
		// and so not moving in a straight line is not allowed
		//Since king1 is currently at (0, 0), king2 at (0, 7), and king3 at (7, 7), all these moves are not legal
		assertEquals(king1.isLegalMove(5, 3), false); //Not a straight line 
		assertEquals(king2.isLegalMove(3, 1), false); //Not a straight line 
		assertEquals(king3.isLegalMove(4, 1), false); //Not a straight line
		
		/* Tests cases where there is another piece at the destination*/
		
		//1. The king cannot move into another square with a player of the same side
		assertEquals(king2.isLegalMove(1, 7), false);
		//The QueenPiece at (1, 7) belongs to the same player as this one. Not allowed
		
		//But say we put a PawnPiece to the other player right above king4, which is at (4,4)
		PawnPiece pawn = new PawnPiece(board, ChessGame.Side.NORTH , null); //belongs to the other player
		board.addPiece(pawn, 4, 3);
		assertEquals(king4.isLegalMove(4, 3), true);
		//This pawn belongs to the other player, and since the kind is moving the allowed number of steps, it is a legal move
	}

	/**
	 * Tests the PawnPiece's isLegalMove() method
	 * A PawnPiece can move one step in the forward direction (or two steps if it is moving for the first time).
	 * The must be no piece in-between the start and end positions, and the landing square must be empty
	 * A PawnPiece can eliminate the other player's pieces by moving one step diagonally into that position
	 */

	@Test
	void testPawnIsLegalMove() {
		/* Create a chess board that has 8 row and columns and put these pieces on it:
		 *
		 */
		SwingChessBoard board = new SwingChessBoard(new SwingEuropeanChessDisplay(), new EuropeanChess());
		PawnPiece pawn1South = new PawnPiece(board, ChessGame.Side.SOUTH , null); //Belongs to the SOUTH player
		PawnPiece pawn1North = new PawnPiece(board, ChessGame.Side.NORTH , null); //Belongs to the SOUTH player
		board.addPiece(pawn1South, 7, 4);
		board.addPiece(pawn1North, 0, 3);

		
		/*Test case when the pawn is moving two steps for the first time*/
		
		//1. When the pawn moves two steps for the first time
		
		//A: moving forward from South (moving upwards)
		assertEquals(pawn1South.isLegalMove(5, 4), true);
		//Since this is pawn's first time, it is allowed to move two steps forward
	
		//The pawn can move to (5, 4). Let's move here and complete the move
		pawn1South.setLocation(5, 4);
		pawn1South.moveDone();
		
		//B: moving forward from North (moving downwards)
		assertEquals(pawn1North.isLegalMove(2, 3), true);
		//Since this is pawn's first time, it is allowed to move two steps forward
	
		//The pawn can move to (2, 3). Let's move here and complete the move
		pawn1North.setLocation(2, 3);
		pawn1North.moveDone();
		
		//C: Had the pawn moved one step before taking two, this would have not been allowed
		PawnPiece pawn = new PawnPiece(board, ChessGame.Side.NORTH, null);
		board.addPiece(pawn, 0, 0);
		assertEquals(pawn.isLegalMove(1, 0), true); //Moves only one step
		pawn.setLocation(1, 0);
		pawn.moveDone();
		assertEquals(pawn.isLegalMove(3, 0), false); //Then attempts to take two steps the second time around. Not allowed!
		
		
		//2. Test where the pawn tries to move two steps after having already moved once
		
		//A: Moving forward from South
		assertEquals(pawn1South.isLegalMove(3, 4), false);
		//The piece is at (5, 4), and has already moved so it is not allowed to take the two steps to (3, 4)

		//B: Moving forward from North
		assertEquals(pawn1North.isLegalMove(4, 3), false);
		//The piece is at (2, 3), and has already moved so it is not allowed to take the two steps to (4, 3)
	
		/*Test case where the PawnPiece is moving horizontally
		 * pawn1South is currently at (5, 4)
		 * pawn1North is currently at (2, 3)
		 *Not allowed and returns false always*/
		
		//A. When the pawn is taking one step horizontally left
		assertEquals(pawn1South.isLegalMove(5, 3), false);
		//The pawn cannot move horizontally. Returns false
		
		//When the king is taking one step horizontally right
		assertEquals(pawn1South.isLegalMove(5, 5), false);
		//The pawn cannot move horizontally. Returns false

		//B. When the pawn is taking more than one step horizontally left 
		assertEquals(pawn1South.isLegalMove(5, 0), false);
		//The pawn cannot move horizontally. Returns false

		//B. When the pawn is taking more than one step horizontally right
		assertEquals(pawn1South.isLegalMove(5, 7), false);
		//The pawn cannot move horizontally. Returns false
	
		/*Test case where the PawnPiece is moving vertically
		 * pawn1South is currently at (5, 4)
		 * pawn1North is currently at (2, 3)
		 * The pawn can move only move one step vertically, unless moving for the first time*/
		
		//1. Test case where the pawn is moving one stop vertically "up"
		assertEquals(pawn1South.isLegalMove(4, 4), true);
		assertEquals(pawn1North.isLegalMove(3, 3), true); //--> both are moving away from their starting position, and only one step. Allowed
		
		//2. Test case where the pawn is moving one stop vertically "down" (not allowed)
		assertEquals(pawn1South.isLegalMove(6, 4), false);
		assertEquals(pawn1North.isLegalMove(1, 3), false); //--> both are moving towards from their starting position. Even though they are moving
		//one step, this move is not allowed
		
		//3. Test case where the pawn is moving more than one step vertically "up" 
		//==> As shown above, this is only allowed if the pawn is moving for the first time
		PawnPiece pawn2South = new PawnPiece(board, ChessGame.Side.SOUTH, null);
		board.addPiece(pawn2South, 7, 6);
		assertEquals(pawn2South.isLegalMove(5, 6), true); //Legal as first time
		pawn2South.setLocation(5, 6);
		pawn2South.moveDone();
		assertEquals(pawn2South.isLegalMove(3, 6), false); //Illegal as second time time

		//4. Test case where the pawn is moving more than one step vertically "down"
		PawnPiece pawn2North = new PawnPiece(board, ChessGame.Side.NORTH, null);
		board.addPiece(pawn2North, 3, 6);
		assertEquals(pawn2North.isLegalMove(1, 6), false); //Illegal as the piece is moving "backwards"
	   //==> Even if this is the first time moving (and hence allowed two steps), the pawns still cannot move backwards
		
		//Similarly, pawn2South, which is at (5, 6), cannot move one step vertically down
		assertEquals(pawn2South.isLegalMove(6, 6), false);
		
		//5. Test case where there is another piece on this square moving to (not allowed)
		//pawn1South is at (5, 4), let's add a piece at belonging to the same player at (4, 4), 
		board.addPiece(new RookPiece(board, ChessGame.Side.SOUTH, null), 4, 4);
		assertEquals(pawn1South.isLegalMove(4, 4), false); //Illegal move. There is a piece here
		
		//Let's remove the rook and add one that belongs to the other player
		board.removePiece(4, 4);
		board.addPiece(new RookPiece(board, ChessGame.Side.NORTH, null), 4, 4);
		assertEquals(pawn1South.isLegalMove(4, 4), false); //Still an illegal move for this pawn. There is a piece here
		board.removePiece(4, 4); //Remove this piece 
		
		/*Test case where the pawn is moving diagonally
		 * Only allowed if this is an elimination move 
		 * pawn1South is currently at (5, 4)
		 * pawn1North is currently at (2, 3)
		 * pawn2South is currently at (5, 6)
		 * We add a new pawn3North piece at (1, 4)
		 */
		PawnPiece pawn3North = new PawnPiece(board, ChessGame.Side.NORTH, null);
		board.addPiece(pawn3North, 1, 4); //diagonally 'back' one step from (2, 3) from North's point of view
		
		//1. Test case where the pawn tries to move diagonally "backwards" (not allowed)
		assertEquals(pawn1North.isLegalMove(1, 4), false);
		
		//2. Test case when the pawn tries to move more than one step diagonally
		assertEquals(pawn1North.isLegalMove(4, 1), false);
		//Not allowed. The piece may only take one step. Plus there is no piece here it is eliminating

		//2. Test case where there is no piece in the square diagonally moving to 
		assertEquals(pawn1South.isLegalMove(4, 5), false);
		assertEquals(pawn1South.isLegalMove(4, 3), false);
		//Since there are no pieces on these squares to eliminate, the pawns cannot move here
		
		/*Test case where the piece is moving diagonally into a square with a piece on it*/
		
		//2. Test case where the piece belongs to the same player
		assertEquals(pawn3North.isLegalMove(2, 3), false);
		//pawn3North is at (1, 4) and so moving to (2, 3) is a legal move as it is in the forward diagonal direction. 
		//However, since pawn1North at(2, 3) is on the same side as this player (both are ChessGame.Side.NORTH), this is illegal
		
		//3. Test case where the piece belongs to another player
		board.addPiece(new PawnPiece(board, ChessGame.Side.NORTH, null), 4, 7);
		assertEquals(pawn2South.isLegalMove(4, 7), true);
		//pawn2South can eliminate this new pawn that was added at (4, 7), as it is at (5, 6) and they don't belong to the same player
		
		
		/* Lastly, we test when the pawn in trying to move not in a straight direction
		*/
		
		
		/* Test case where the PawnPiece tries to move not in a straight line */
		//The PawnPiece is only allowed to move in a straight line vertically/diagonally
		// and so not moving in a straight line is not allowed
		//Since pawn1South is currently at (5, 4), pawn2North at (2, 3), and pawn2South is currently at (5, 6)
		assertEquals(pawn1South.isLegalMove(1, 2), false); //Not a straight line 
		assertEquals(pawn1North.isLegalMove(3, 1), false); //Not a straight line 
		assertEquals(pawn2South.isLegalMove(4, 1), false); //Not a straight line
		
		/* When the piece is at the other end of the board */
	}


	/**
	 * Tests the KnightPiece's isLegalMove(int, int) method
	 * A KingtPiece moves in an L-shape: either two steps vertically and one horizontally, or one step vertically and two steps horizontally, 
	 * moving forwards or backwards
	 * The piece skips any pieces in-between the start and end positions, but the landing square must either be 
	 * empty or contain a piece belonging to the other player
	 */
	@Test
	void testKinghtIsLegalMove() {
		
		/* First let's a chess board that has 8 row and columns and put these pieces on it:
		 * A KnightPiece on this player's side on square (0,0)
		 */
		SwingChessBoard board = new SwingChessBoard(new SwingEuropeanChessDisplay(), new EuropeanChess());
		KnightPiece knight1 = new KnightPiece(board, ChessGame.Side.SOUTH , null);
		board.addPiece(knight1, 3, 3); //Place at the middle
	
				
		/*Test case where the Knight is moving only horizontally
		 * Not allowed
		 */
		
		//A. When the knight takes one step horizontally left
		assertEquals(knight1.isLegalMove(3, 2), false);
		//Horizontal only movements not allowed
		
		//When the knight takes one step horizontally right
		assertEquals(knight1.isLegalMove(3, 4), false);
		//Horizontal only movements not allowed

		//b. When the knight takes more than one step horizontally left
		assertEquals(knight1.isLegalMove(3, 0), false);
		//Horizontal only movements not allowed

		//B. When the knight takes more than one step horizontally right
		assertEquals(knight1.isLegalMove(3, 7), false);
		//Horizontal only movements not allowed
		
		/* Test case where the knight is moving only vertically
		 * Also not allowed
		 */
		
		//A. When the piece is moving only one step vertically down
		assertEquals(knight1.isLegalMove(4, 3), false);
		//Vertical only movements not allowed
		
		//When the piece is moving only one step vertically up
		assertEquals(knight1.isLegalMove(2, 3), false);
		//Vertical only movements not allowed
				
		//B. When the piece is moving multiple steps down
		assertEquals(knight1.isLegalMove(7, 3), false);
		//Vertical only movements not allowed

		//When the piece is moving more than one step up
		assertEquals(knight1.isLegalMove(0, 3), false);
		//Vertical only movements not allowed
		
		/* Test case where the piece is moving diagonally
		 * Also not allowed
		 */
		
		//A. When the piece is moving only one step diagonally down
		assertEquals(knight1.isLegalMove(4, 4), false);
		//Diagonal only moves are not allowed
		
		//When the piece is moving only one step diagonally up
		assertEquals(knight1.isLegalMove(2, 2), false);
		//Diagonal only moves are not allowed
		
		//B. When the piece is moving more than one step diagonally down
		assertEquals(knight1.isLegalMove(6,6), false);
		//Diagonal only moves are not allowed

		assertEquals(knight1.isLegalMove(1, 1), false);
		//Diagonal only moves are not allowed
		
		//C. We perform similar tests in the positive gradient diagonal axis
		
		//i. When the piece moves one step diagonally up
		assertEquals(knight1.isLegalMove(2, 4), false);
		//Diagonal only moves are not allowed

		//When the piece is moving one step diagonally down
		assertEquals(knight1.isLegalMove(4, 2), false);
		//Diagonal only moves are not allowed
		
		//ii. When the piece moves more than one step diagonally up
		assertEquals(knight1.isLegalMove(0, 6), false);
		//Diagonal only moves are not allowed
		
		//When the piece moves more then one step diagonally down
		assertEquals(knight1.isLegalMove(6, 0), false);
		//Diagonal only moves are not allowed
	
		/*Tests with the right distance ratio */
		
		//1. When the Knight moves 2 steps vertically up and 1 step horizontally right or left
		assertEquals(knight1.isLegalMove(1, 4), true);
		assertEquals(knight1.isLegalMove(1, 2), true);
		
		//2. When the Knight moves 2 steps vertically down and 1 step horizontally right or left
		assertEquals(knight1.isLegalMove(5, 4), true);
		assertEquals(knight1.isLegalMove(5, 2), true);

		//3. When the Knight moves 1 step vertically up and 2 steps horizontally right or left
		assertEquals(knight1.isLegalMove(2, 5), true);
		assertEquals(knight1.isLegalMove(2, 1), true);
				
		//4. When the Knight moves 1 step vertically down and 2 steps horizontally right or left
		assertEquals(knight1.isLegalMove(4, 5), true);
		assertEquals(knight1.isLegalMove(4, 1), true);
		
		/*Tests with the wrong distance ratio */
		
		//1. When the Knight moves 3 steps vertically up and 1 step horizontally right or left
		assertEquals(knight1.isLegalMove(0, 4), false);
		assertEquals(knight1.isLegalMove(0, 2), false);
		
		//2. When the Knight moves 3 steps vertically down and 1 step horizontally right or left
		assertEquals(knight1.isLegalMove(6, 4), false);
		assertEquals(knight1.isLegalMove(6, 2), false);

		//3. When the Knight moves 2 step vertically up and 3 steps horizontally right or left
		assertEquals(knight1.isLegalMove(1, 6), false);
		assertEquals(knight1.isLegalMove(1, 0), false);
				
		//4. When the Knight moves 2 step vertically down and 3 steps horizontally right or left
		assertEquals(knight1.isLegalMove(5, 6), false);
		assertEquals(knight1.isLegalMove(5, 0), false);
		
		/* Tests cases other pieces surround the knight*/
		//Surround the Knight with these Pawns 
		for(int i = 2; i <= 4; i++) {
			for(int x = 2; x <= 4; x++) {
				if (x == 3 && i == 3);
				else board.addPiece(new PawnPiece(board, ChessGame.Side.NORTH, null), i, x);
			}
		}
		
		assertEquals((board.getPiece(3, 3).getLabel()), "N");
		assertEquals((board.hasPiece(2, 2)), true);
		//Assert that the Knight is still at (3, 3) and there are pieces surrounding the Knight
		
		//The Knight should still be able to move to the position with the right distance ratio. We repeat the tests above
		//1. When the Knight moves 2 steps vertically up and 1 step horizontally right or left
		assertEquals(knight1.isLegalMove(1, 4), true);
		assertEquals(knight1.isLegalMove(1, 2), true);
		
		//2. When the Knight moves 2 steps vertically down and 1 step horizontally right or left
		assertEquals(knight1.isLegalMove(5, 4), true);
		assertEquals(knight1.isLegalMove(5, 2), true);

		//3. When the Knight moves 1 step vertically up and 2 steps horizontally right or left
		assertEquals(knight1.isLegalMove(2, 5), true);
		assertEquals(knight1.isLegalMove(2, 1), true);
				
		//4. When the Knight moves 1 step vertically down and 2 steps horizontally right or left
		assertEquals(knight1.isLegalMove(4, 5), true);
		assertEquals(knight1.isLegalMove(4, 1), true);
		
		/*Test cases where there is another piece on the square landing on
		 * kight1 is still on square (3, 3)
		 * We put a pawn belonging to the same player at (1, 4), and
		 * We put a Queen belonging to the other player at (4, 5), which all make for legal distance ratios
		 */
		
		//1. Test case where the piece belongs to the same player
		board.addPiece(new PawnPiece(board, ChessGame.Side.SOUTH, null), 1, 4);
		assertEquals(knight1.isLegalMove(1, 4), false); 
		//The from (3, 3) to (1, 4) follows the L-shape perfectly, but the piece at (1, 4) belongs to the same player. Not allowed
		
		//2. Test case where the piece belongs to a different player 
		board.addPiece(new QueenPiece(board, ChessGame.Side.NORTH, null), 4, 5);
		assertEquals(knight1.isLegalMove(4, 5), true);
		//The from (3, 3) to (4, 5) follows the L-shape perfectly, and since the piece at (4, 5) belongs to a different player, this
		//is allowed
	}
	

	/**Test the PawnPiece's upgrade piece method 
	 */
	@Test
	void testUpgradePiece() {
		
		//Create a chess board and put these pawns on it 
		SwingChessBoard board = new SwingChessBoard(new SwingEuropeanChessDisplay(), new EuropeanChess());
		PawnPiece pawnNorth = new PawnPiece(board, ChessGame.Side.NORTH , null);
		PawnPiece pawnSouth = new PawnPiece(board, ChessGame.Side.SOUTH , null);
		board.addPiece(pawnNorth, 6, 0); //Placed at next to last south row 
		board.addPiece(pawnSouth, 1, 1); //Placed at next to first north row 

		/*
		 * When pawnSouth moves to (0, 1), it should upgrade.
		 * If the user inputs 'Q', we place a QueenPiece at this position
		 * If the user input 'B', we place a BishopPiece at this position
		 * Uncomment the code below to verify this test (otherwise it interferes with other tests)
		 * # ------------------ #
		 * uncomment from here
		 * # -----------------= #
		 
		chess.makeMove(pawnSouth, 0, 1);
		assertEquals(board.getPiece(0, 1).getLabel(), "Q");
		
		//Now the user puts a 'B'
		chess.makeMove(pawnNorth, 7, 0);
		assertEquals(board.getPiece(7, 0).getLabel(), "B");*/
	}

}
