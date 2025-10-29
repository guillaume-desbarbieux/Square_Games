package model.rule;

import java.io.Serializable;

/**
 * The TicTacToeRule class defines the specific rules for the Tic-Tac-Toe game.
 * It extends the AlignementGameRule class, inheriting its general alignment game behavior.
 * This class is configured specifically for a 3x3 board, requiring 3 marks
 * aligned in a row, column, or diagonal to determine the winner.
 */
public class TicTacToeRule extends AlignementGameRule implements Serializable {
    /**
     * Constructs a new TicTacToeRule object specifically tailored to implement
     * the rules of the Tic-Tac-Toe game. The game is played on a 3x3 grid, with
     * 2 players competing to align 3 consecutive marks either horizontally,
     * vertically, or diagonally to win the game.
     * <p>
     * This constructor initializes the Tic-Tac-Toe game by invoking the parent
     * AlignementGameRule class constructor, providing the board dimensions,
     * default number of players, and the alignment requirement to determine victory.
     * <p>
     * The specified parameters for the game are:
     * - Board dimensions: 3x3
     * - Default number of players: 2
     * - Winning alignment length: 3
     */
    public TicTacToeRule() {
        super(3, 3, 2, 3);
    }
}
