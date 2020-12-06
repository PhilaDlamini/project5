package testers;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import chesstype.Xiangqi;
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
 * A tester class for the XiangQi chess pieces
 * @author Phila Dlamini
 */

//TODO: You currently use swing European chess display. Use the one for xiangqi once you code it :) 
class XiangqiMoveTest {

	/* Our board has 10 rows and 9 columns, and thus by indexes, largest row number is 9, column is 8
	 * The river goes in-between row index 4 and 5, and the fortress is the intersection of column 3 to 5 and, and
	 * row 7 to 9 (south), or row 0 to 2 (north )
	 */
	

	/**
	 * Tests the XiangqiPiece's isLegalmove() method 
	 * The XiangqiPiece can only move one step vertically and horizontally at a time, and cannot leave the fortress
	 */
	@Test
	void testXiangqiKingPiece() {
	
		/* Create a XiangqiKingPiece at (9, 4), the very middle bottom of the south fortress
		 * Another king piece on the same side is placed at (7, 5)
		 * And another king piece on the same side is placed at (7, 3)
		 */
		Xiangqi xiangqi = new Xiangqi();
		SwingChessBoard board = new SwingChessBoard(new SwingEuropeanChessDisplay(), xiangqi);
		XiangqiKingPiece kingPiece1 = new XiangqiKingPiece(board, ChessGame.Side.SOUTH, null);
		XiangqiKingPiece kingPiece2 = new XiangqiKingPiece(board, ChessGame.Side.SOUTH, null);
		XiangqiKingPiece kingPiece3 = new XiangqiKingPiece(board, ChessGame.Side.SOUTH, null);
		board.addPiece(kingPiece1, 9, 4);	
		board.addPiece(kingPiece2, 7, 5);
		board.addPiece(kingPiece3, 7, 3);
		
		/** Test case where the piece is moving horizontally **/
		
		//Taking one step horizontally right inside the fortress
		assertEquals(kingPiece1.isLegalMove(9, 5), true);
		//Allowed
		
		//Taking one step horizontally left in the fortress
		assertEquals(kingPiece1.isLegalMove(9, 3), true);
		//Allowed

		//Taking more than one step horizontally left in the fortress
		assertEquals(kingPiece1.isLegalMove(9, 2), false);
		//Not allowed => the king can only move one step (and here the piece goes outside the fortress too, which is illegal)
		
		//Taking more than one step horizontally right in the fortress
		assertEquals(kingPiece1.isLegalMove(9, 6), false);
		//Not allowed => the king can only move one step (and here the piece goes outside the fortress too, which is illegal)
		
		/** Test case where the piece is moving vertically **/
		
		//Taking one step vertically up inside the fortress
		assertEquals(kingPiece1.isLegalMove(8, 4), true);
		//Allowed
		
		//Taking one step vertically down in the fortress
		assertEquals(kingPiece2.isLegalMove(8, 5), true);
		//Allowed

		//Taking two steps vertically up in the fortress
		assertEquals(kingPiece1.isLegalMove(7, 4), false);
		//Not allowed => although the king stays within the fortress, it can only move one step at a time
		
		//Taking two steps vertically down in the fortress
		assertEquals(kingPiece2.isLegalMove(9, 5), false);
		//Not allowed => although the king stays within the fortress, it can only move one step at a time
		
		/** Test case where the piece is moving diagonally (illegal) **/
		
		//Taking one step diagonally up
		assertEquals(kingPiece1.isLegalMove(8, 5), false); //bottom left to top right
		assertEquals(kingPiece1.isLegalMove(8, 3), false); //bottom right to top left 
		//Not allowed => the king cannot move diagonally
		
		//Taking one step diagonally down
		assertEquals(kingPiece2.isLegalMove(8, 4), false); //top right to bottom left
		assertEquals(kingPiece3.isLegalMove(8, 4), false); //top left to bottom right 
		//Not allowed => the king cannot move diagonally

		//Taking more than one step diagonally up
		assertEquals(kingPiece1.isLegalMove(7, 2), false); //bottom left to top right
		assertEquals(kingPiece1.isLegalMove(7, 6), false); //bottom right to top left 
		//Not allowed => the king cannot move diagonally (the king leaves the fortress here as well)
		
		//Taking more than one step diagonally up 
		assertEquals(kingPiece2.isLegalMove(9, 3), false); //top right to bottom left
		assertEquals(kingPiece3.isLegalMove(9, 5), false); //top left to bottom right 
		//Not allowed => Even if the king stays in the fortress, moving diagonally is not allowed*/
		
		/** Test case where the piece tries to leave the fortress (illegal) **/
		
		//One step horizontally out of the fortress
		assertEquals(kingPiece3.isLegalMove(7, 2), false);
		assertEquals(kingPiece2.isLegalMove(7, 6), false);
		
		//One step vertically out of the fortress
		assertEquals(kingPiece3.isLegalMove(6, 3), false);
		assertEquals(kingPiece2.isLegalMove(6, 5), false);

		
		/* Remove the pieces and add them as north pieces to test confinement for the north side */
		board.removePiece(7, 3);
		board.removePiece(9, 4);
		board.removePiece(7, 5);
		XiangqiKingPiece northKing1 = new XiangqiKingPiece(board, ChessGame.Side.NORTH, null);
		XiangqiKingPiece northKing2 = new XiangqiKingPiece(board, ChessGame.Side.NORTH, null);
		board.addPiece(northKing1, 2, 3);
		board.addPiece(northKing2, 2, 5); //Add the pieces to the edge of the fortress
		
		//================== for north side ==============
		
		//One step horizontally out of the fortress
		assertEquals(northKing1.isLegalMove(2, 2), false);
		assertEquals(northKing2.isLegalMove(2, 6), false);
		
		//One step vertically out of the fortress
		assertEquals(northKing1.isLegalMove(3, 3), false);
		assertEquals(northKing2.isLegalMove(3, 5), false);

		/** Test case where there is a piece at the location moving to **/
		/* Surround northKing1 at (2, 3) with these pieces
		 * A piece belonging to the other player in front of it (thus outside fortress) at (3, 3)
		 * A piece belonging to the other player behind it (thus in the fortress) at (1, 3)
		 * A piece belonging to the other player diagonal from it at (1, 4)
		 * A piece belonging to the same player next to it at (2, 4)
		 */
		board.addPiece(new ElephantPiece(board, ChessGame.Side.SOUTH, null), 3, 3);
		board.addPiece(new ElephantPiece(board, ChessGame.Side.SOUTH, null), 1, 3);
		board.addPiece(new ElephantPiece(board, ChessGame.Side.SOUTH, null), 1, 4);
		board.addPiece(new ElephantPiece(board, ChessGame.Side.NORTH, null), 2, 4);
		
		//When the king illegally leaves the fortress to move onto a square with an opponent piece
		assertEquals(northKing1.isLegalMove(3, 3), false); 
		//=> the king cannot leave the fortress
		
		//When the king performs a legal move onto a square with an opponent piece
		assertEquals(northKing1.isLegalMove(1, 3), true); 
		//=> Legal (capture) move
		
		//When the king performs an illegal move (within fortress) onto a square with an opponent piece
		assertEquals(northKing1.isLegalMove(1, 4), false); 
		//=> The king cannot move diagonally
		
		//When the king performs a legal move onto a square with a player of the same side
		assertEquals(northKing1.isLegalMove(2, 4), false); 
		//=> Cannot move onto a square with a player of the same side 
		
	}

	/**
	 * Tests the GuardPiece's isLegalMove() method 
	 * A GuardPiece can only move one step diagonally at a time, and cannot leave the fortress
	 */
	@Test
	void testXiangqiGuardPiece() {
		
		/* Create a GuardPiece at (9, 4), the very middle bottom of the south fortress
		 * Another GuardPiece of the same player is placed at (7, 5)
		 * And another GuardPiece of the same player is placed at (7, 3)
		 */
		Xiangqi xiangqi = new Xiangqi();
		SwingChessBoard board = new SwingChessBoard(new SwingEuropeanChessDisplay(), xiangqi);
		GuardPiece guardPiece1 = new GuardPiece(board, ChessGame.Side.SOUTH, null);
		GuardPiece guardPiece2 = new GuardPiece(board, ChessGame.Side.SOUTH, null);
		GuardPiece guardPiece3 = new GuardPiece(board, ChessGame.Side.SOUTH, null);
		board.addPiece(guardPiece1, 9, 4);	
		board.addPiece(guardPiece2, 7, 5);
		board.addPiece(guardPiece3, 7, 3);
		
		/** Test case where the piece is moving horizontally (illegal) **/
		
		//Taking one step horizontally right inside the fortress
		assertEquals(guardPiece1.isLegalMove(9, 5), false);
		//Not allowed. A GuardPiece only moves diagonally
		
		//Taking one step horizontally left in the fortress
		assertEquals(guardPiece1.isLegalMove(9, 3), false);
		//Not allowed. A GuardPiece only moves diagonally

		//Taking more than one step horizontally left in the fortress
		assertEquals(guardPiece1.isLegalMove(9, 2), false);
		//Not allowed. A GuardPiece only moves diagonally
		
		//Taking more than one step horizontally right in the fortress
		assertEquals(guardPiece1.isLegalMove(9, 6), false);
		//Not allowed. A GuardPiece only moves diagonally
		
		/** Test case where the piece is moving vertically (illegal) **/
		
		//Taking one step vertically up inside the fortress
		assertEquals(guardPiece1.isLegalMove(8, 4), false);
		//Not allowed. A GuardPiece only moves diagonally
		
		//Taking one step vertically down in the fortress
		assertEquals(guardPiece2.isLegalMove(8, 5), false);
		//Not allowed. A GuardPiece only moves diagonally

		//Taking two steps vertically up in the fortress
		assertEquals(guardPiece1.isLegalMove(7, 4), false);
		//Not allowed. A GuardPiece only moves diagonally
		
		//Taking two steps vertically down in the fortress
		assertEquals(guardPiece2.isLegalMove(9, 5), false);
		//Not allowed. A GuardPiece only moves diagonally
		
		/** Test case where the piece is moving diagonally **/
		
		//Taking one step diagonally up
		assertEquals(guardPiece1.isLegalMove(8, 5), true); //bottom left to top right
		assertEquals(guardPiece1.isLegalMove(8, 3), true); //bottom right to top left 
		//Allowed
		
		//Taking one step diagonally down
		assertEquals(guardPiece2.isLegalMove(8, 4), true); //top right to bottom left
		assertEquals(guardPiece3.isLegalMove(8, 4), true); //top left to bottom right 
		//Allowed

		//Taking more than one step diagonally up
		assertEquals(guardPiece1.isLegalMove(7, 2), false); //bottom left to top right
		assertEquals(guardPiece1.isLegalMove(7, 6), false); //bottom right to top left 
		//Not allowed => the QuardPiece can only move one step at a time. Here it ends up being outside the 
		
		//Taking more than one step diagonally up 
		assertEquals(guardPiece2.isLegalMove(9, 3), false); //top right to bottom left
		assertEquals(guardPiece3.isLegalMove(9, 5), false); //top left to bottom right 
		//Not allowed => the QuardPiece can only move one step at a time  
		
		/** Test case where the piece tries to leave the fortress (illegal) **/
		
		//One step horizontally out of the fortress
		assertEquals(guardPiece3.isLegalMove(7, 2), false);
		assertEquals(guardPiece2.isLegalMove(7, 6), false);
		//Not allowed
		
		//One step vertically out of the fortress
		assertEquals(guardPiece3.isLegalMove(6, 3), false);
		assertEquals(guardPiece2.isLegalMove(6, 5), false);
		//Not allowed

		/* Remove the pieces and add them as north pieces to test confinement for the north side */
		board.removePiece(7, 3);
		board.removePiece(9, 4);
		board.removePiece(7, 5);
		GuardPiece northGuard1 = new GuardPiece(board, ChessGame.Side.NORTH, null);
		GuardPiece northGuard2 = new GuardPiece(board, ChessGame.Side.NORTH, null);
		board.addPiece(northGuard1, 2, 3);
		board.addPiece(northGuard2, 2, 5); //Add the pieces to the edge of the fortress
		
		//================== for north side ==============
		
		//One step horizontally out of the fortress
		assertEquals(northGuard1.isLegalMove(2, 2), false);
		assertEquals(northGuard2.isLegalMove(2, 6), false);
		//Not allowed
		
		//One step vertically out of the fortress
		assertEquals(northGuard1.isLegalMove(3, 3), false);
		assertEquals(northGuard2.isLegalMove(3, 5), false);
		//Not allowed

		/** Test case where there is a piece at the location moving to **/
		/* Surround northGuard1 at (2, 3) with these pieces
		 * A piece belonging to the other player diagonally in front of it (thus outside fortress) at (2, 3)
		 * A piece belonging to the other player behind it (thus in the fortress) at (1, 3)
		 * A piece belonging to the other player diagonally behind it (thus in the fortress) at (1, 5)
		 * When the piece at (1, 4) belongs to the same player 
		 */
		board.addPiece(new ElephantPiece(board, ChessGame.Side.SOUTH, null), 2, 3);
		board.addPiece(new ElephantPiece(board, ChessGame.Side.SOUTH, null), 1, 3);
		board.addPiece(new ElephantPiece(board, ChessGame.Side.SOUTH, null), 1, 4);
		
		//When the guard illegally leaves the fortress to move onto a square with an opponent piece
		assertEquals(northGuard1.isLegalMove(3, 2), false); 
		//=> the guard cannot leave the fortress
		
		//When the guard performs a legal move onto a square with an opponent piece
		assertEquals(northGuard1.isLegalMove(1, 4), true); 
		//=> Legal (capture) move
		
		//When the guard performs an illegal move (within fortress) onto a square with an opponent piece
		assertEquals(northGuard1.isLegalMove(1, 3), false); 
		//=> The guard cannot move vertically
		
		/*Remove the piece at (1, 4) and add another piece on it which belongs to the same side as this guard
		 *It should now be illegal for the guard to move here
		 */
		board.removePiece(1, 4);
		board.addPiece(new ElephantPiece(board, ChessGame.Side.NORTH, null), 1, 4);
		
		//Therefore, when the guard performs a legal move onto a square with a player of the same side
		assertEquals(northGuard1.isLegalMove(1, 4), false); 
		//=> Cannot move onto a square with a player of the same side
		
	}

	/**
	 * Tests the ElephantPiece's isLegalMove() method 
	 * An ElephantPiece should move two steps diagonally at a time, and cannot cross the river: 
	 * i.e if on side north, should stay on the top half of the board. 
	 * The river is in-between row (by index) 4, and 5. Therefore, going from 4 to 5 means crossing the river. 
	 */
	@Test
	void testXiangqiElephantPiece() {
		
		/* Create an ElephantPiece at (4, 3), 
		 * One ElephantPiece at (2, 6)		
		 * One ElephantPiece at (4, 8)
		 * Another ElephantPiece at (0, 0)
		 * (and then describe what other pieces you haver here)
		 */
		Xiangqi xiangqi = new Xiangqi();
		SwingChessBoard board = new SwingChessBoard(new SwingEuropeanChessDisplay(), xiangqi);
		ElephantPiece elephantPiece1 = new ElephantPiece(board, ChessGame.Side.NORTH, null);
		ElephantPiece elephantPiece2 = new ElephantPiece(board, ChessGame.Side.NORTH, null);
		ElephantPiece elephantPiece3 = new ElephantPiece(board, ChessGame.Side.NORTH, null);
		ElephantPiece elephantPiece4 = new ElephantPiece(board, ChessGame.Side.NORTH, null);
		board.addPiece(elephantPiece1, 4, 3);	
		board.addPiece(elephantPiece2, 2, 6);
		board.addPiece(elephantPiece3, 0, 0);
		board.addPiece(elephantPiece4, 4, 8);
		
		/** Test case where the piece is moving horizontally (illegal) **/
		
		//Taking one step horizontally right
		assertEquals(elephantPiece1.isLegalMove(4, 4), false);
		//Not allowed. An ElephantPiece must move 2 spaces diagonally at a time
		
		//Taking one step horizontally left 
		assertEquals(elephantPiece1.isLegalMove(4, 2), false);
		//Not allowed. An ElephantPiece must move 2 spaces diagonally at a time

		//Taking more than one step horizontally right
		assertEquals(elephantPiece1.isLegalMove(4, 5), false);
		//Not allowed. An ElephantPiece must move 2 spaces diagonally at a time

		//Taking more than one step horizontally left
		assertEquals(elephantPiece1.isLegalMove(4, 1), false);
		//Not allowed. An ElephantPiece must move 2 spaces diagonally at a time
		
		/** Test case where the piece is moving vertically (illegal) **/
		
		//Taking one step vertically up 
		assertEquals(elephantPiece1.isLegalMove(3, 3), false);
		//Not allowed. An ElephantPiece must move 2 spaces diagonally at a time
		
		//Taking one step vertically down 
		assertEquals(elephantPiece2.isLegalMove(3, 6), false);
		//Not allowed. An ElephantPiece must move 2 spaces diagonally at a time

		//Taking more than one step vertically up 
		assertEquals(elephantPiece1.isLegalMove(2, 3), false);
		//Not allowed. An ElephantPiece must move 2 spaces diagonally at a time
		
		//Taking more than one step vertically down
		assertEquals(elephantPiece2.isLegalMove(4, 6), false);
		//Not allowed. An ElephantPiece must move 2 spaces diagonally at a time

	
		/** Test case where the piece is moving diagonally **/
		
		//Taking one step diagonally up
		assertEquals(elephantPiece1.isLegalMove(3, 2), false); 
		assertEquals(elephantPiece1.isLegalMove(3, 4), false);  
		//Not allowed. An ElephantPiece must move 2 spaces diagonally at a time
		
		//Taking one step diagonally down
		assertEquals(elephantPiece2.isLegalMove(3, 5), false); 
		assertEquals(elephantPiece2.isLegalMove(3, 7), false); 
		//Not allowed. An ElephantPiece must move 2 spaces diagonally at a time

		//Taking two steps steps diagonally up
		assertEquals(elephantPiece2.isLegalMove(0 ,8), true); 
		assertEquals(elephantPiece1.isLegalMove(2, 1), true);
		//Allowed
		
		//Taking two steps diagonally down
		assertEquals(elephantPiece2.isLegalMove(4, 4), true);
		assertEquals(elephantPiece3.isLegalMove(2, 2), true);  
		//Allowed  
		
		//Taking more than two steps diagonally down (e.g 3, not allowed)
		assertEquals(elephantPiece3.isLegalMove(3, 3), false);
		//Moving from (0, 0) to (3, 3) takes 3 steps. Not allowed
		
		//Taking more than two steps diagonally up (e.g 3, not allowed)
		assertEquals(elephantPiece4.isLegalMove(1, 5), false);
		//Moving from (4, 8) to (1, 5) takes 3 steps. Not allowed
		
		/** If there is a piece in the diagonal direction the piece is taking, this move is not allowed*/
		//Put a piece at (3, 2). Now elephantPiece1 cannot go to (2, 1) because this piece is blocking the path
		board.addPiece(new ElephantPiece(board, ChessGame.Side.NORTH, null), 3, 2);
		assertEquals(elephantPiece1.isLegalMove(2, 1), false);
		
		//Likewise if we put another piece at (3, 5), elephantPiece2 cannot move to (4, 4)
		board.addPiece(new ElephantPiece(board, ChessGame.Side.NORTH, null), 3, 5);
		assertEquals(elephantPiece2.isLegalMove(4, 4), false);
		
		//Remove these pieces now
		board.removePiece(3,  5);
		board.removePiece(3, 2);
		
		/** Test case where the piece tries to cross the river (illegal) **/
		
		//elephantPiece1 takes two diagonal steps down and crosses the river
		assertEquals(elephantPiece1.isLegalMove(6, 1), false);
		assertEquals(elephantPiece1.isLegalMove(6, 5), false);
		//Even though the right number of steps were moved, the piece crossed the river. Not allowed

		//Similarly if a piece form the south tries to cross to north side of the river, this should
		ElephantPiece piece = new ElephantPiece(board, ChessGame.Side.SOUTH, null);
		board.addPiece(piece, 6, 0);
		assertEquals(piece.isLegalMove(4, 2), false);
		//Even if the piece moves two diagonal spaces, it crosses the river. Not allowed
		
		//But this piece is allowed to move two diagonal steps while remaining on the  the south
		assertEquals(piece.isLegalMove(8, 2), true); //Exactly two steps
		assertEquals(piece.isLegalMove(9, 3), false); //More than two 
		board.removePiece(6, 0);
		
		/** Test case where there is a piece at the location moving to **/
		/* The board currently has the following pieces
		 * elephantPiece1 at (3, 4)
		 * elephantPiece2 (2, 6)
		 * elephantPiece3 at (0, 0)
		 * elephantPiece4 at (4, 8)
		 * All belong to NORTH player
		 * 
		 * We add the following pieces
		 * A piece of a different player on (0, 2)
		 * A piece of a different player on (6, 1)
		 */
		
		//(Note that in a real game piece5 could never be here as it wouldn't cross the river)
		ElephantPiece piece5 = new ElephantPiece(board, ChessGame.Side.SOUTH, null); 
		ElephantPiece piece6 = new ElephantPiece(board, ChessGame.Side.SOUTH, null);
		ElephantPiece piece7 = new ElephantPiece(board, ChessGame.Side.SOUTH, null);
		board.addPiece(piece5, 0, 2); 
		board.addPiece(piece6, 6, 1);
		board.addPiece(piece7, 0, 4);
		
		//When the elephant performs an illegal move onto a square with an opponent piece
		assertEquals(elephantPiece1.isLegalMove(0, 2), false);
		//An ElephantPiece does not move horizontally. Illegal
		
		//When the elephant illegally crosses river to move onto a square with an opponent piece
		assertEquals(elephantPiece1.isLegalMove(6, 1), false);
		//An ElephantPiece cannot cross the river
		
		//When the elephant performs a legal move onto a square with an opponent piece
		assertEquals(elephantPiece2.isLegalMove(0, 4), true);
		//=> Legal (capture) move as the piece here belongs to another player
		
		// When the elephant performs a legal move onto a square with a player of the same side
		assertEquals(elephantPiece2.isLegalMove(4, 8), false); 
		//=> Illegal as moving onto a square with a piece of the same side is not allowed
	}
	

	/**
	 * Tests the HorsePiece isLegalMove() method 
	 * A HorsePiece moves exactly two moves: the first is a horizontal or vertical move, and the second a diagonal move
	 * The square moving to on the first vertical or horizontal move must be empty, and the landing square must not 
	 * contain a piece of the same player 
	 */ 
	@Test
	void testHorseIsLegalMove() {
		
		/*
		 * Place a HorsePiece on this player's side on square (3, 3)
		 */
		Xiangqi xiangqi = new Xiangqi();
		SwingChessBoard board = new SwingChessBoard(new SwingEuropeanChessDisplay(), xiangqi);
		HorsePiece horse = new HorsePiece(board, ChessGame.Side.SOUTH , null);
		board.addPiece(horse, 3, 3);
	
				
		/**
		 * Test case where the Knight is moving only horizontally
		 * Not allowed
		 */
		
		//A. When the horse takes one step horizontally left
		assertEquals(horse.isLegalMove(3, 2), false);
		//Horizontal movements are not allowed
		
		//When the horse takes one step horizontally right
		assertEquals(horse.isLegalMove(3, 4), false);
		//Horizontal movements are not  allowed

		//b. When the horse takes more than one step horizontally left
		assertEquals(horse.isLegalMove(3, 0), false);
		//Horizontal movements are not allowed

		//B. When the horse takes more than one step horizontally right
		assertEquals(horse.isLegalMove(3, 8), false);
		//Horizontal movements are not allowed
		
		/**
		 * Test case where the horse is moving only vertically
		 * Also not allowed
		 */
		
		//A. When the piece is moving only one step vertically down
		assertEquals(horse.isLegalMove(4, 3), false);
		//Vertical movements not allowed
		
		//When the piece is moving only one step vertically up
		assertEquals(horse.isLegalMove(2, 3), false);
		//Vertical movements not allowed
				
		//B. When the piece is moving multiple steps down
		assertEquals(horse.isLegalMove(9, 3), false);
		//Vertical movements not allowed

		//When the piece is moving more than one step up
		assertEquals(horse.isLegalMove(0, 3), false);
		//Vertical movements not allowed
		
		/**
		 *  Test case where the piece is moving diagonally
		 * Also not allowed
		 */
		
		//A. When the piece is moving only one step diagonally down
		assertEquals(horse.isLegalMove(4, 4), false);
		//Diagonal-only moves are not allowed
		
		//When the piece is moving only one step diagonally up
		assertEquals(horse.isLegalMove(2, 2), false);
		//Diagonal-only moves are not allowed
		
		//B. When the piece is moving more than one step diagonally down
		assertEquals(horse.isLegalMove(6,6), false);
		//Diagonal-only moves are not allowed

		assertEquals(horse.isLegalMove(1, 1), false);
		//Diagonal-only moves are not allowed
		
		//C. We perform similar tests in the positive gradient diagonal axis
		
		//i. When the piece moves one step diagonally up
		assertEquals(horse.isLegalMove(2, 4), false);
		//Diagonal-only moves are not allowed

		//When the piece is moving one step diagonally down
		assertEquals(horse.isLegalMove(4, 2), false);
		//Diagonal-only moves are not allowed
		
		//ii. When the piece moves more than one step diagonally up
		assertEquals(horse.isLegalMove(0, 6), false);
		//Diagonal-only moves are not allowed
		
		//When the piece moves more then one step diagonally down
		assertEquals(horse.isLegalMove(6, 0), false);
		//Diagonal-only moves are not allowed
	
		/** Tests with the right distance ratio */
		
		//1. When the HorsePiece moves 1 step vertically up and then 1 step diagonally right or left
		assertEquals(horse.isLegalMove(1, 4), true);
		assertEquals(horse.isLegalMove(1, 2), true);
		
		//2. When the HorsePiece moves 1 step vertically down and then 1 step diagonally right or left
		assertEquals(horse.isLegalMove(5, 4), true);
		assertEquals(horse.isLegalMove(5, 2), true);

		//3. When the HorsePiece moves 1 step horizontally right and then 1 step diagonally up or down
		assertEquals(horse.isLegalMove(2, 5), true);
		assertEquals(horse.isLegalMove(4, 5), true);
				
		//4. When the HorsePiece moves 1 step horizontally left and then 1 step diagonally up or down
		assertEquals(horse.isLegalMove(2, 1), true);
		assertEquals(horse.isLegalMove(4, 1), true);
		
		/**Tests with the wrong distance ratio */
		
		//1. When the HorsePiece moves 2 steps vertically up and then 1 step diagonally left or right
		assertEquals(horse.isLegalMove(0, 4), false);
		assertEquals(horse.isLegalMove(0, 2), false);
		
		//2. When the HorsePiece moves 2 steps vertically down and then 1 step diagonally left or right
		assertEquals(horse.isLegalMove(6, 4), false);
		assertEquals(horse.isLegalMove(6, 2), false);

		//3. When the HorsePiece moves 2 steps horizontally left and then 1 step diagonally left or right
		assertEquals(horse.isLegalMove(4, 0), false);
		assertEquals(horse.isLegalMove(2, 0), false);
				
		//4. When the HorsePiece moves 2 steps horizontally right and then 1 step diagonally left or right
		assertEquals(horse.isLegalMove(4, 6), false);
		assertEquals(horse.isLegalMove(2, 6), false);
		
		/* The distance ratio should not only be 2 or 1/2, but must also come from the smallest numbers in the ratio. 
		 * Thus, 4/2 or its reciprocal, 8/4 or its reciprocal, etc while are == 2, are not acceptable
		 * Repeat test cases above with correct distance ratios, but not the smallest numbers
		 */

		//1. When the HorsePiece moves 2 step vertically down and then 2 step diagonally right or left
		assertEquals(horse.isLegalMove(7, 5), false);
		assertEquals(horse.isLegalMove(7, 1), false);

		//2. When the HorsePiece moves 2 step horizontally right and then 1 step diagonally up or down
		assertEquals(horse.isLegalMove(5, 7), false);
		assertEquals(horse.isLegalMove(1, 7), false);
		
		/** 
		 * Test case when trying to skip other pieces 
		 * The first square the piece moves vertically or horizontally onto
		 * (before performing the diagonal move), must be empty
		 */
		
		/*
		 * The square at the "corner" of the L-shape (e.g square (3,5) can have a pieces when the horse is moving form (3, 3) to (2, 5))
		 * is allowed to have a piece since the path of the HorsePiece does pass here
		 */
		
		//Adding new pieces at the "corners" shouldn't affect the above tests. It does not matter which player they belong to 
		board.addPiece(new ElephantPiece(board, ChessGame.Side.NORTH, null), 3, 5);
		board.addPiece(new ElephantPiece(board, ChessGame.Side.NORTH, null), 3, 1);
		board.addPiece(new ElephantPiece(board, ChessGame.Side.NORTH, null), 5, 3);
		board.addPiece(new ElephantPiece(board, ChessGame.Side.NORTH, null), 1, 3);

		
		//The "tests with right distance ratios" seen above should still work
		assertEquals(horse.isLegalMove(1, 4), true);
		assertEquals(horse.isLegalMove(1, 2), true);
		
		assertEquals(horse.isLegalMove(5, 4), true);
		assertEquals(horse.isLegalMove(5, 2), true);

		assertEquals(horse.isLegalMove(2, 5), true);
		assertEquals(horse.isLegalMove(4, 5), true);
				
		assertEquals(horse.isLegalMove(2, 1), true);
		assertEquals(horse.isLegalMove(4, 1), true);
		
		board.removePiece(3, 5);
		board.removePiece(3, 1);
		board.removePiece(1, 3);
		board.removePiece(5, 3);

		
		/*
		 * However, when there is a piece on the first horizontal/vertical square this piece moves to 
		 * before its diagonal move, the move is not allowed
		 * Add pieces to these squares and the tests above should show that the moves are illegal. It does not matter which 
		 * player they belong to 
		 */
		board.addPiece(new ElephantPiece(board, ChessGame.Side.SOUTH, null), 2, 3);
		board.addPiece(new ElephantPiece(board, ChessGame.Side.NORTH, null), 3, 2);
		board.addPiece(new ElephantPiece(board, ChessGame.Side.NORTH, null), 4, 3);
		board.addPiece(new ElephantPiece(board, ChessGame.Side.SOUTH, null), 3, 4);


		//The "tests with right distance ratios" seen above should now be illegal, given the horse is trying to skip these new pieces
		assertEquals(horse.isLegalMove(1, 4), false);
		assertEquals(horse.isLegalMove(1, 2), false);
		
		assertEquals(horse.isLegalMove(5, 4), false);
		assertEquals(horse.isLegalMove(5, 2), false);

		assertEquals(horse.isLegalMove(2, 5), false);
		assertEquals(horse.isLegalMove(4, 5), false);
				
		assertEquals(horse.isLegalMove(2, 1), false);
		assertEquals(horse.isLegalMove(4, 1), false);
		
		board.removePiece(2, 3);	
		board.removePiece(3, 2);
		board.removePiece(4, 3);
		board.removePiece(3, 4);


		
		/**
		 * Test case where there is another piece on the landing square.
		 * Only a legal move if the piece does not belong to the same player
		 * Add a piece that belongs to the same player (1, 4)
		 * Add a piece that belongs to another player at (1, 2)
		 */
		board.addPiece(new ElephantPiece(board, ChessGame.Side.SOUTH, null), 1, 4);
		board.addPiece(new ElephantPiece(board, ChessGame.Side.NORTH, null), 1, 2);
		
		assertEquals(horse.isLegalMove(1, 4), false); //the horse and the piece here belong to the same player (SOUTH)
		assertEquals(horse.isLegalMove(1, 2), true); //the horse and the piece here belong to different players
		
	}
	
	/**
	 * Tests the RookPiece's isLegalMove() method
	 * A RookPiece can move any number of steps in the vertical and horizontal directions
	 * The must be no piece in-between the start and end positions, and the landing square cannot contain a piece of the same player
	 */
	@Test
	void testRookIsLegalMove() {
		
		/* First create a chess board and put these pieces on it:
		 * A RookPiece on this player's side on square (0,0)
		 * A RookPiece on this player's side on square (0,8)
		 * A RookPiece on this player's side on square (9,8)
		 * A RookPiece on this player's side on square (2, 4)
		 * Another RookPiece on this player's side on square (2,7)
		 * A XiangqiKingPIece on this player's side on square (4, 0)
		 * ==> although in reality the last two pieces could not be here as they can't cross river/leave fortress
		 */
		Xiangqi xiangqi = new Xiangqi();
		SwingChessBoard board = new SwingChessBoard(new SwingEuropeanChessDisplay(), xiangqi);
		RookPiece rook1 = new RookPiece(board, ChessGame.Side.NORTH , null);
		RookPiece rook2 = new RookPiece(board, ChessGame.Side.NORTH , null); 
		RookPiece rook3 = new RookPiece(board, ChessGame.Side.NORTH , null); 
		RookPiece rook4 = new RookPiece(board, ChessGame.Side.NORTH , null);
		RookPiece rook5 = new RookPiece(board, ChessGame.Side.NORTH , null);
		XiangqiKingPiece king = new XiangqiKingPiece(board, ChessGame.Side.NORTH , null); 
		XiangqiKingPiece king2 = new XiangqiKingPiece(board, ChessGame.Side.SOUTH , null); 
		board.addPiece(rook1, 0, 0); 
		board.addPiece(rook2, 0, 8); 
		board.addPiece(rook3, 9, 8); 
		board.addPiece(rook4, 2, 4);
		board.addPiece(rook5, 2, 7); 
		board.addPiece(king, 4, 0); 
		board.addPiece(king2, 3, 7); 
				
		/** Test case where the RookPiece is moving horizontally*/
		
		//A. When the rook is taking one step forward
		assertEquals(rook1.isLegalMove(0, 1), true);
		//There is no piece on the path, and the rook can move any number of steps, the method returns true
		
		//When the rook is taking one step backwards
		assertEquals(rook2.isLegalMove(0, 7), true);
		//The the method returns true

		//b. When the rook is taking more than one step forward
		assertEquals(rook1.isLegalMove(0, 5), true);
		//There is no piece on the path, and the steps for a rook are unlimited. The method returns true

		//B. When the rook is taking more than one step backwards
		assertEquals(rook2.isLegalMove(0, 2), true);
		//There is no piece on the path, and the steps for a rook are unlimited. The method returns true
		
		/** Test case where the rook is moving vertically*/
		
		//A. When the piece is moving only one step down
		assertEquals(rook1.isLegalMove(1, 0), true);
		//There is no piece in the range of squares, and the rook moves unlimited steps
		
		//When the piece is moving only one step up
		assertEquals(rook3.isLegalMove(8, 8), true);
		//There is no piece in the range of squares, and the rook moves unlimited steps
				
		//B. When the piece is moving multiple steps down
		assertEquals(rook1.isLegalMove(3, 0), true);
		//There is no piece in the range of squares, and the rook moves unlimited steps
		
		//When the piece is moving more than one step up
		assertEquals(rook3.isLegalMove(1, 8), true);
		//There is no piece in the range of squares and the rook moves unlimited steps
		
		/**
		 * Test case where the piece is moving diagonally
		 * Always false since the RookPiece does not move diagonally
		 * We start with tests in the negative gradient diagonal axis 
		 */
		
		//A. When the piece is moving only one step diagonally down
		assertEquals(rook1.isLegalMove(1, 1), false);
		
		//When the piece is moving only one step diagonally up
		assertEquals(rook3.isLegalMove(8, 7), false);
		
		//B. When the piece is moving more than one step diagonally down
		assertEquals(rook1.isLegalMove(6,6), false);
		assertEquals(rook3.isLegalMove(2, 1), false);
		
		//C. We perform similar tests in the positive gradient diagonal axis. rook4 is currently at (2, 4)
		
		//i. When the piece moves one step diagonally up
		assertEquals(rook4.isLegalMove(1, 5), false);
		
		//When the piece is moving one step diagonally down
		assertEquals(rook4.isLegalMove(3, 3), false);
		
		//ii. When the piece moves more than one step diagonally up
		assertEquals(rook4.isLegalMove(0, 6), false);
		
		//When the piece moves more then one step diagonally down
		assertEquals(rook4.isLegalMove(5, 1), false);
		
		/** If there are pieces in the range given, the move is illegal */
		assertEquals(rook1.isLegalMove(8, 0), false); 
		//There is a piece at (4, 0). Cannot move from (0, 0) to (8, 0)
		
		assertEquals(rook5.isLegalMove(2, 1), false);
		//Likewise, because there is a piece (2, 4) -- rook4 -- it is illegal to move from (2, 7) to (2, 1)
	
		/** Test case where the rook tries to move not in a straight line 
		 * The RookPiece is only allowed to move in a straight line either horizontally or vertically
		 * and so not moving in a straight line is not allowed
		 * Since rook1 is currently at (0, 0), rook2 at (0, 8), and rook3 at (9, 8), all these moves are not legal
		 */
		assertEquals(rook1.isLegalMove(5, 3), false); //Not a straight line 
		assertEquals(rook2.isLegalMove(3, 1), false); //Not a straight line 
		assertEquals(rook3.isLegalMove(4, 1), false); //Not a straight line
		
		/** Tests cases where there is another piece at the destination*/
		
		//1. The rook cannot move into another square with a player of the same side
		assertEquals(rook1.isLegalMove(4, 0), false);
		//The king at (4, 0) belongs to the same player as this one
		
		assertEquals(rook5.isLegalMove(3, 7), true);
		//The piece at (3, 7) belongs to the player. The rook at (2, 7) can move down to this square
		
	}

	/**
	 * Tests the CannonPiece's isLegalMove() method 
	 * A CannonPiece can move any number of steps in the vertical and horizontal directions
	 * If the landing square has a piece of the opponent, the CannonPiece must just over exactly one piece to land to this square
	 * Otherwise, there must be no other piece in-between. 
	 */
	@Test
	void testCannonPieceIsLegal() {
		/* First create a chess board that has 8 row and columns and put these pieces on it:
		 * A CannonPiece on this player's side on square (6, 4)
		 * A CannonPiece on this player's side on square (0, 0)
		 * A CannonPiece on this player's side on square (0, 8)
		 * A CannonPiece on this player's side on square (1, 0)
		 * A CannonPiece on this player's side on square (9, 0)
		 */
		Xiangqi xiangqi = new Xiangqi();
		SwingChessBoard board = new SwingChessBoard(new SwingEuropeanChessDisplay(), xiangqi);
		CannonPiece cannon1 = new CannonPiece(board, ChessGame.Side.NORTH, null);
		CannonPiece cannon2 = new CannonPiece(board, ChessGame.Side.NORTH, null);
		CannonPiece cannon3 = new CannonPiece(board, ChessGame.Side.NORTH, null);
		CannonPiece cannon4 = new CannonPiece(board, ChessGame.Side.NORTH, null);
		CannonPiece cannon5 = new CannonPiece(board, ChessGame.Side.NORTH, null);
		board.addPiece(cannon1, 6, 4);
		board.addPiece(cannon2, 0, 0);
		board.addPiece(cannon3, 0, 8);
		board.addPiece(cannon4, 1, 0);
		board.addPiece(cannon5, 9, 0);
				
		/** Test case where the RookPiece is moving horizontally (non-capture)*/
		
		//A. When the CannonPiece is taking one step to the right
		assertEquals(cannon1.isLegalMove(6, 5), true);
		//Not capturing, and no pieces in range. Legal 
		
		//When the CannonPiece is taking one step to the left
		assertEquals(cannon1.isLegalMove(6, 3), true);
		//Not capturing, and no pieces in range. Legal 

		//b. When the CannonPiece is taking more than one step to the right
		assertEquals(cannon1.isLegalMove(6, 8), true);
		//Not capturing, and no pieces in range. Legal 

		//B. When the CannonPiece is taking more than one step to the left
		assertEquals(cannon1.isLegalMove(6, 0), true);
		//Not capturing, and no pieces in range. Legal 
		
		/** Test case where the CannonPiece is moving vertically*/
		
		//A. When the piece is moving only one step down
		assertEquals(cannon1.isLegalMove(7, 4), true);
		//Not capturing, and no pieces in range. Legal 
		
		//When the piece is moving only one step up
		assertEquals(cannon1.isLegalMove(5, 4), true);
		//Not capturing, and no pieces in range. Legal 
				
		//B. When the piece is moving multiple steps down
		assertEquals(cannon1.isLegalMove(9, 4), true);
		//Not capturing, and no pieces in range. Legal 
		
		//When the piece is moving more than one step up
		assertEquals(cannon1.isLegalMove(0, 4), true);
		//Not capturing, and no pieces in range. Legal 
		
		/**
		 * Test case where the piece is moving diagonally
		 * Always false since the CannonPiece does not move diagonally
		 * We start with tests in the negative gradient diagonal axis 
		 */
		
		//A. When the piece is moving only one step diagonally down
		assertEquals(cannon1.isLegalMove(7, 5), false);
		//Illegal
		
		//When the piece is moving only one step diagonally up
		assertEquals(cannon1.isLegalMove(5, 3), false);
		//Illegal
		
		//B. When the piece is moving more than one step diagonally down and up
		assertEquals(cannon1.isLegalMove(9, 7), false);
		assertEquals(cannon1.isLegalMove(2, 0), false);
		//Illegal
		
		//C. We perform similar tests in the positive gradient diagonal axis. rook4 is currently at (2, 4)
		
		//i. When the piece moves one step diagonally up
		assertEquals(cannon1.isLegalMove(5, 5), false);
		//Illegal

		//When the piece is moving one step diagonally down
		assertEquals(cannon1.isLegalMove(7, 3), false);
		//Illegal

		//ii. When the piece moves more than one step diagonally up
		assertEquals(cannon1.isLegalMove(2, 8), false);
		//Illegal

		//When the piece moves more then one step diagonally down
		assertEquals(cannon1.isLegalMove(9, 1), false);
		//Illegal
		
		/**Test cases where the CannonPiece tries to skip a piece when not capturing another
		 * Put new pieces at (8, 4) and (6, 7). Now the cannonPiece at (6, 8) should not be able to
		 * make a horizontal move to (6, 8) and a vertical move to (9, 4) 
		 */
		board.addPiece(new ElephantPiece(board, ChessGame.Side.NORTH, null), 8, 4);
		board.addPiece(new RookPiece(board, ChessGame.Side.SOUTH, null), 6, 7);
		
		assertEquals(cannon1.isLegalMove(9, 4), false);
		assertEquals(cannon1.isLegalMove(6, 8), false);
		//Illegal as trying to skip a piece when not capturing another (there are no pieces at landing squares)
	
		board.removePiece(8, 4);
		board.removePiece(6, 7);
		
		/** When the CannonPiece captures a piece
		 */
		
		/* Tests on Horizontal direction
		 * Place pieces belonging to the other player on squares (0, 2), (0, 3) and (0, 5)
		 * cannon2 is at (0, 0) and cannon3 at (0, 8)
		 */
		board.addPiece(new ElephantPiece(board, ChessGame.Side.SOUTH, null), 0, 2);
		board.addPiece(new RookPiece(board, ChessGame.Side.SOUTH, null), 0, 3);
		board.addPiece(new HorsePiece(board, ChessGame.Side.SOUTH, null), 0, 5);
	
		//1. When trying to capture without skipping any pieces
		assertEquals(cannon2.isLegalMove(0, 2), false);
		assertEquals(cannon3.isLegalMove(0, 5), false);
		//Both pieces are not skipping other pieces to capture these ones. Thus illegal, even though 'capturing' other player's pieces
		
		//2. When it captures after jumping over exactly one piece
		assertEquals(cannon2.isLegalMove(0, 3), true);
		assertEquals(cannon3.isLegalMove(0, 3), true);
		//Both pieces skip over exactly one piece: (cannon2 skips piece at (0, 2), 
		//and cannon3 piece at (0, 5) to capture this one. Legal 
		
		//3. When the cannon jumps over more than one piece 
		assertEquals(cannon2.isLegalMove(0, 5), false);
		assertEquals(cannon3.isLegalMove(0, 2), false);
		//Both pieces attempt to skip over more than one piece to capture the desired one
		//cannon2 attempts to skip pieces at (0, 2) and (0, 3); cannon3 attempts to skip pieces at (0, 5) and (0, 3). Illegal
		
		//Remove the pieces
		board.removePiece(0, 2);
		board.removePiece(0, 3);
		board.removePiece(0, 5);
		
		
		/* Tests on Vertical direction
		 * Place pieces belonging to the other player on squares (2, 0), (5, 0) and (6, 0)
		 * cannon4 is at (1, 0) and cannon5 at (9, 0)
		 */
		board.addPiece(new ElephantPiece(board, ChessGame.Side.SOUTH, null), 2, 0);
		board.addPiece(new RookPiece(board, ChessGame.Side.SOUTH, null), 5, 0);
		board.addPiece(new HorsePiece(board, ChessGame.Side.SOUTH, null), 6, 0);
		
		//1. When trying to capture without skipping any pieces
		assertEquals(cannon4.isLegalMove(2, 0), false);
		assertEquals(cannon5.isLegalMove(6, 0), false);
		//Both pieces are not skipping other pieces to capture these ones. Thus illegal, even though 'capturing' other player's pieces
		
		//2. When it captures after jumping over exactly one piece
		assertEquals(cannon4.isLegalMove(5, 0), true);
		assertEquals(cannon5.isLegalMove(5, 0), true);
		//Both pieces skip over exactly one piece: cannon4 skips piece at (2, 0), 
		//and cannon5 piece at (6, 0) to capture this one. Legal 
		
		//3. When the cannon jumps over more than one piece 
		assertEquals(cannon4.isLegalMove(6, 0), false);
		assertEquals(cannon5.isLegalMove(2, 0), false);
		//Both pieces attempt to skip over more than one piece to capture the desired one
		//cannon4 attempts to skip pieces at (2, 0) and (5, 0); cannon5 attempts to skip pieces at (6, 0) and (5, 0). Illegal
		
		/* Tests on diagonal direction: illegal as this is an illegal direction
		 * Add pieces at (5, 5), (4, 6) and (2, 8)
		 */
		board.addPiece(new ElephantPiece(board, ChessGame.Side.SOUTH, null), 5, 5);
		board.addPiece(new RookPiece(board, ChessGame.Side.SOUTH, null), 4, 6);
		board.addPiece(new HorsePiece(board, ChessGame.Side.SOUTH, null), 2, 8);
		
		//1. When trying to capture without skipping
		assertEquals(cannon1.isLegalMove(5, 5), false);
		//Illegal diagonal move. Cannot capture without skipping
		
		//2. When capturing after skipping one piece
		assertEquals(cannon1.isLegalMove(4, 6), false);
		//Illegal diagonal move, even though correctly capturing

		//3. When capturing after skipping more than one piece 
		assertEquals(cannon1.isLegalMove(2, 8), false);
		//Illegal diagonal move. Cannot capture when skipping more than one piece


		/** Tests cases where the piece at the destination belongs to the same player*
		 * Let's repeat the horizontal test but now with pieces of the same player. 
		 * Trying to capture these should be illegal as they belong to the same player
		 */
		board.addPiece(new ElephantPiece(board, ChessGame.Side.NORTH, null), 0, 2);
		board.addPiece(new RookPiece(board, ChessGame.Side.NORTH, null), 0, 3);
		board.addPiece(new HorsePiece(board, ChessGame.Side.NORTH, null), 0, 5);
	
		//1. When trying to capture without skipping any pieces
		assertEquals(cannon2.isLegalMove(0, 2), false);
		assertEquals(cannon3.isLegalMove(0, 5), false);
		//Both pieces are not skipping other pieces to capture these ones, plus the pieces being captured
		//are the player's own pieces. Illegal
		
		//2. When it captures after jumping over exactly one piece
		assertEquals(cannon2.isLegalMove(0, 3), false);
		assertEquals(cannon3.isLegalMove(0, 3), false);
		//Both pieces skip over exactly one piece: (cannon2 skips piece at (0, 2), 
		//and cannon3 piece at (0, 5) to capture this one, but since they captures pieces of the same side, illegal :)  
		
		//3. When the cannon jumps over more than one piece 
		assertEquals(cannon2.isLegalMove(0, 5), false);
		assertEquals(cannon3.isLegalMove(0, 2), false);
		//Both pieces attempt to skip over more than one piece to capture the desired one (which also belongs to the same side as well). 
		//Illegal
		
	}

	/**
	 * Tests SoldierPiece's isLegalMove() method
	 * A SolderPiece can only move one step forward (away from starting position), and once the river is crossed, one step horizontally
	 * The landing square cannot contain a piece of the same player
	 */
	@Test
	void testSoldierPieceIsLegal() {
		
		/* First create a chess board and put these pieces on it:
		 * A SoldierPiece on this player's side on square (6, 4)
		 * A SoldierPiece on this player's side on square (3, 2)
		 */
		Xiangqi xiangqi = new Xiangqi();
		SwingChessBoard board = new SwingChessBoard(new SwingEuropeanChessDisplay(), xiangqi);
		SoldierPiece soldier1South = new SoldierPiece(board, ChessGame.Side.SOUTH , null); 
		SoldierPiece soldier1North = new SoldierPiece(board, ChessGame.Side.NORTH , null); 
		SoldierPiece soldier2South = new SoldierPiece(board, ChessGame.Side.SOUTH , null); 
		SoldierPiece soldier2North = new SoldierPiece(board, ChessGame.Side.NORTH , null); 
		board.addPiece(soldier1South, 6, 4);
		board.addPiece(soldier1North, 3, 2);
		board.addPiece(soldier2South, 2, 6);
		board.addPiece(soldier2North, 7, 2);
	
		
		/** Test case where the soldier piece is moving vertically **/
		
		//1. Test case where the piece is moving one step up
		assertEquals(soldier1South.isLegalMove(5, 4), true); 
		assertEquals(soldier1North.isLegalMove(4, 2), true); 
		//Allowed
		
		//2. Test case where the piece is moving one step down 
		assertEquals(soldier1South.isLegalMove(7, 4), false); 
		assertEquals(soldier1North.isLegalMove(2, 2), false); 
		//The soldier piece may not move backwards. Not allowed
		
		//3. Test case where the piece is moving more than one step up 
		assertEquals(soldier1South.isLegalMove(3, 4), false); 
		assertEquals(soldier1North.isLegalMove(6, 2), false); 
		//The soldier piece may only move one step at a time
		
		//4. Test case where the piece is moving more than one step down
		assertEquals(soldier1South.isLegalMove(9, 4), false); 
		assertEquals(soldier1North.isLegalMove(0, 2), false); 
		//The soldier piece may only move one step at a time, and not backwards
				
		/** Test case where the soldier is moving horizontally **/
		
		/* Attempting to move horizontally before crossing the river
		 * Currently, both soldier1North and soldier1South have not crossed the river
		 * It should be illegal to try and move horizontally
		 */
		
		//1. Moving one step horizontally left or right before crossing river
		assertEquals(soldier1North.isLegalMove(3, 1), false); 
		assertEquals(soldier1North.isLegalMove(3, 3), false); 
		assertEquals(soldier1South.isLegalMove(6, 3), false); 
		assertEquals(soldier1South.isLegalMove(6, 5), false); 
		//Since all the pieces haven't crossed the river, it is illegal to move horizontally
		
		//2. Move more than one step horizontally left or right before crossing river
		assertEquals(soldier1North.isLegalMove(3, 0), false);
		assertEquals(soldier1North.isLegalMove(3, 8), false); 
		assertEquals(soldier1South.isLegalMove(6, 0), false);
		assertEquals(soldier1South.isLegalMove(6, 8), false); 
		//Since all the pieces haven't crossed the river, it is illegal to move horizontally
		
		/* Moving horizontally after crossing the river
		 * Both soldier2North and soldier2South have crossed the river. It should be legal to move horizontally
		 */
		
		//1. Moving one step horizontally left or right after crossing river
		assertEquals(soldier2South.isLegalMove(2, 7), true); 
		assertEquals(soldier2South.isLegalMove(2, 5), true); 
		assertEquals(soldier2North.isLegalMove(7, 3), true); 
		assertEquals(soldier2North.isLegalMove(7, 1), true); 
		//All the pieces have crossed the river. Legal
		
		//2. Move more than one step horizontally left or right after crossing river
		assertEquals(soldier2North.isLegalMove(7, 0), false); 
		assertEquals(soldier2North.isLegalMove(7, 8), false); 
		assertEquals(soldier2South.isLegalMove(2, 0), false);
		assertEquals(soldier2South.isLegalMove(2, 8), false); 
		//All pieces have crossed the river, but a SoldierPiece can only move one step at a time 
		
		/** Test case when moving diagonally */
		
		//1. When moving one step diagonally up and down on both axes
		assertEquals(soldier1South.isLegalMove(7, 3), false);
		assertEquals(soldier1South.isLegalMove(5, 5), false);
		//positive gradient axis
		
		//negative gradient axis
		assertEquals(soldier1South.isLegalMove(7, 5), false);
		assertEquals(soldier1South.isLegalMove(5, 3), false);
		//==> All illegal. A soldier piece does not move diagonally
		
		
		//2. When moving more then one step diagonally up and down on both axes
		assertEquals(soldier1South.isLegalMove(9, 1), false);
		assertEquals(soldier1South.isLegalMove(2, 8), false);
		//positive gradient axis
		
		//negative gradient axis
		assertEquals(soldier1South.isLegalMove(9, 7), false);
		assertEquals(soldier1South.isLegalMove(2, 0), false);
		//==> All illegal. A soldier piece does not move diagonally

		/** Test case where there is a another piece on the square landing to */
		
		//1. When the piece belongs to the same player
		board.addPiece(new ElephantPiece(board, ChessGame.Side.NORTH, null), 5, 4);
		assertEquals(soldier1South.isLegalMove(5, 4), true );
		//The piece here belongs to a different player. Legal (capture) move
		
		//2. When the piece belongs to another player 
		board.addPiece(new ElephantPiece(board, ChessGame.Side.NORTH, null), 7, 1);
		assertEquals(soldier2North.isLegalMove(7, 1), false);
		//The piece here belongs to the same player 
	}
	

/*
 * TODO: Write the tests for isLegalCaptureMove() and isLegalNonCaptureMove() methods
 * You don't need to wait as you can really do it now 
 * Do I need to test squareThreatened()? But thsi belongs to the SwingChessBoard
 */
}

