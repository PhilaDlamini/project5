package graphics;

import piecetypes.ChessPiece;

import java.util.ArrayList;
import java.util.List;

import chess.ChessGame;
import chess.EuropeanChess;
import chess.Xiangqi;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * A ChessBoard displayed with JavaFX, on which a European or Xiangqi game can be played
 * @author Phila Dlamini
 *
 */

public class JavaFXChessBoard extends Application {

	// Indicates how the chess board should be display
	private JavaFXChessBoardDisplay chessBoardDisplay;

	// The chess being played on the
	private ChessGame chessGame;

	/**
	 * Displays the board and starts the game of chess
	 * @param primaryStage the primary display stage for the board
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {

		// Get the command line arguments
		List<String> args = getParameters().getRaw();
		args = new ArrayList<>();
		args.add("chess");

		// If no arguments were given, tell the user to input them
		if (args.size() != 0) {

			// Choose the desired game
			switch (args.get(0)) {

			case "chess":
				chessBoardDisplay = new JavaFXEuropeanChessDisplay();
				chessGame = new EuropeanChess();
				primaryStage.setTitle("Chess");
				break;

			case "xiangqi":
				chessBoardDisplay = new JavaFXXiangqiDisplay();
				chessGame = new Xiangqi();
				primaryStage.setTitle("Xiangqi");
				break;

			default:
				
				Platform.exit();
				System.out.print("This version of chess is not supported");
				break;

			}

			// Display the board
			Board board = new Board(primaryStage);
			chessGame.startGame(board);

		} else {
			System.out.println("Specify which game of chess you want to play: \"chess\" or \"xiangqi\"");
			Platform.exit();
		}
	}

	/**
	 * The ChessBoard being displayed with JavaFX
	 */
	private class Board extends ChessBoard {

		// The squares for the board
		private Button[][] squares;

		/**
		 * Constructs the Board
		 * 
		 * @param primaryStage      the primary stage for display
		 * @param chessBoardDisplay information for how the chess board should be
		 *                          displayed
		 * @param chessGame         the chess game being played on this board
		 */
		public Board(Stage primaryStage) {
			super(chessGame);
			squares = new Button[chessGame.getNumRows()][chessGame.getNumColumns()];

			// Create a grid pane to put the buttons in
			GridPane pane = new GridPane();

			// Listens for click events
			EventHandler<ActionEvent> listener = new ClickListener();

			// Add each square to the grid
			for (int i = 0; i < chessGame.getNumRows(); i++) {
				for (int x = 0; x < chessGame.getNumColumns(); x++) {
					squares[i][x] = new Button();
					squares[i][x].setOnAction(listener);
					squares[i][x].setMinSize(70, 70);
					chessBoardDisplay.displayEmptySquare(squares[i][x], i, x);
					pane.add(squares[i][x], x, i);
				}
			}

			// Create the scene and show it
			Scene scene = new Scene(pane);
			primaryStage.setScene(scene);
			primaryStage.show();
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
			chessBoardDisplay.displayFilledSquare(squares[row][column], row, column, piece);

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

		/**
		 * The listener for when a user clicks on the board
		 *
		 */
		private class ClickListener implements EventHandler<ActionEvent> {

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
			public void handle(ActionEvent e) {
				Button b = (Button) e.getSource();
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
	}

	/**
	 * The entry point of our program
	 * @param args the command line arguments passed in
	 */
	public static void main(String[] args) {
		try {
		Application.launch(args);
		} catch (Exception e) {
			System.out.print("An error occured. The version of chess requested is most likely unavailable");
		}
	}
}
