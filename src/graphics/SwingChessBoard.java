package graphics;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import chesstype.EuropeanChess;
import chesstype.Xiangqi;
import piecetypes.ChessPiece;

/**
 * A ChessBoard displayed with Java Swing
 * 
 * @author Phila Dlamini
 *
 */

//Fully implement this class also 
public class SwingChessBoard extends ChessBoard {

	// The squares for the board
	private JButton[][] squares;

	// The board
	private JFrame board;

	// Indicates how the chess board should be display
	private SwingChessBoardDisplay chessBoardDisplay;

	public SwingChessBoard(SwingChessBoardDisplay chessBoardDisplay, ChessGame gameRules) {
		super(gameRules);
		this.chessBoardDisplay = chessBoardDisplay;
		squares = new JButton[gameRules.getNumRows()][gameRules.getNumColumns()];

		// Create the board visuals on the event dispatch thread
		try {
			SwingUtilities.invokeAndWait(new Thread() {
				@Override
				public void run() {

					// Create the grid on which the squares will placed
					JPanel panel = new JPanel(new GridLayout(gameRules.getNumRows(), gameRules.getNumColumns()));
					board = new JFrame();

					// The ActionListener for each button
					ActionListener listener = new ChessAction();

					// Add each square to the grid
					for (int i = 0; i < gameRules.getNumRows(); i++) {
						for (int x = 0; x < gameRules.getNumColumns(); x++) {
							squares[i][x] = new JButton();
							squares[i][x].addActionListener(listener);
							chessBoardDisplay.displayEmptySquare(squares[i][x], i, x);
							panel.add(squares[i][x]);
						}
					}

					// Put the squares on the board and display them
					board.add(panel);
					board.setSize(500, 500); //TODO: What should be the size exactly??
					board.setVisible(true);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * The listener for when a user clicks on the board
	 * 
	 * @author Phila Dlamini
	 *
	 */
	private class ChessAction implements ActionListener {

		// True if we are selecting a piece
		private boolean firstPick = true;

		// The row of the selected piece
		private int pieceRow;

		// The column of the selected piece
		private int pieceCol;

		/**
		 * What we do when the user chooses the piece to move.
		 * 
		 * @param row the row of the chosen piece
		 * @param col the column of the chosen piece
		 */
		private void processFirstSelection(int row, int col) {

			// If there is a piece here and it is legal to play it, highlight the square
			if (hasPiece(row, col) && getGameRules().legalPieceToPlay(getPiece(row, col), row, col)) {

				// Remember the row and column of the square
				pieceRow = row;
				pieceCol = col;
				chessBoardDisplay.highlightSquare(true, squares[row][col], row, col, getPiece(row, col));
				firstPick = false;
			}
		}

		/**
		 * What we do when the user chooses the square to move the piece to.
		 * 
		 * @param row the row the piece will move to
		 * @param col the column the piece will move to
		 */
		private void processSecondSelection(int row, int col) {

			// If the user selects the same row and column again, do nothing and wait for
			// them to choose another square
			if (row == pieceRow && col == pieceCol)
				return;

			// Holds whether or not the piece was moved
			boolean moveMade = getGameRules().makeMove(getPiece(pieceRow, pieceCol), row, col);

			// if the move was made or if it was not made and the user can select a new
			// piece, then reset to choose a new move
			if (moveMade || getGameRules().canChangeSelection(getPiece(pieceRow, pieceCol))) {
				chessBoardDisplay.highlightSquare(false, squares[pieceRow][pieceCol], pieceRow, pieceCol,
						getPiece(pieceRow, pieceCol));
				firstPick = true;
			}
		}

		/**
		 * Handle a button click. The method alternates between selecting a piece and
		 * selecting any square. After both are selected, the piece's legalMove is
		 * called, and if the move is legal, the piece is moved.
		 * 
		 * @param e the event that triggered the method
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();
			int col = -1;
			int row = -1;

			// first find which button (board square) was clicked.
			for (int i = 0; i < squares.length; i++) {
				for (int j = 0; j < squares[i].length; j++) {
					if (squares[i][j] == b) {
						row = i;
						col = j;
					}
				}
			}

			if (firstPick)
				processFirstSelection(row, col);
			else
				processSecondSelection(row, col);

		}

	}

	/**
	 * Adds the specified piece onto the specified row and column
	 * 
	 * @param piece  the chess piece to place on the board
	 * @param row    the row to place this piece on
	 * @param column the column to place this piece on
	 */
	@Override
	public void addPiece(ChessPiece piece, int row, int column) {
		super.addPiece(piece, row, column);

		Runnable addPiece = new Runnable() {
			public void run() {
				chessBoardDisplay.displayFilledSquare(squares[row][column], row, column, piece);
			}
		};

		// run the code to change the display on the event dispatch thread to avoid drawing errors
		if (SwingUtilities.isEventDispatchThread())
			addPiece.run();
		else {
			try {
				SwingUtilities.invokeAndWait(addPiece);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Returns true if a piece of the opponent player can make a legal move and
	 * capture the piece the specified piece
	 * 
	 * @param row    the row this piece is on
	 * @param column the column this piece is on
	 * @param piece  the piece at this row and column
	 * @return true if an opponent piece can move to this row and column and capture
	 *         the piece here
	 */
	@Override
	public boolean squareThreated(int row, int column, ChessPiece piece) {
		for (int i = 0; i < squares.length; i++) {
			for (int j = 0; j < squares[i].length; j++) {
				if (hasPiece(i, j) && getPiece(i, j).getSide() != piece.getSide()
						&& getPiece(i, j).isLegalCaptureMove(row, column))
					return true;
			}
		}
		return false;
	}
	
	/*
	public static void main(String[] args) {
		EuropeanChess chess = new EuropeanChess();
	SwingChessBoard board = new SwingChessBoard(new SwingEuropeanChessDisplay(), new EuropeanChess());
	chess.startGame(board);
	}*/
}
