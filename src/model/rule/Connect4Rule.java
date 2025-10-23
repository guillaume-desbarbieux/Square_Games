package model.rule;

import model.Board;
import model.Move;

/**
 * This class defines the rules for the Connect Four game. It extends the generic
 * rules provided by the {@code AlignementGameRule} class, setting up the game with
 * a default grid of 6 rows and 7 columns, expecting 2 players, and requiring a sequence
 * of 4 consecutive pieces to win.
 */
public class Connect4Rule extends AlignementGameRule {
    /**
     * Constructs a new Connect4Rule object with predefined parameters specific to the game
     * of Connect Four. The game is played on a 6x7 board, with 2 players competing to align
     * 4 consecutive pieces either horizontally, vertically, or diagonally to achieve victory.
     * <p>
     * This constructor initializes the game using the base class AlignementGameRule, providing
     * the default number of players, board dimensions, and the alignment requirement to win.
     */
    public Connect4Rule() {
        super(6, 7, 2, 4);
    }

    /**
     * Validates whether a move is permissible within the rules of the game.
     *
     * @param board the current state of the game board
     * @param move the move being validated, containing the target row and column, as well as the player making the move
     * @return true if the move is valid, false otherwise
     */
    @Override
    public boolean isMoveValid(Board board, Move move) {
        int row = move.getRow();
        int col = move.getCol();

        if (row < 0
                || row >= board.height()
                || col < 0
                || col >= board.width()) {
            return false;
        }

        if (row == board.height() - 1)
            return board.getCell(row, col).isEmpty();
        else
            return board.getCell(row, col).isEmpty() && !board.getCell(row + 1, col).isEmpty();
    }
}
