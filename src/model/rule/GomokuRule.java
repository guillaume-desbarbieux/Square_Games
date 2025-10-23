package model.rule;

/**
 * The GomokuRule class defines the specific game rules for the Gomoku game,
 * a variant of alignment-based board games. The game is played on a 15x15 grid
 * with two players attempting to align five consecutive marks to win.
 * <p>
 * This class is a specialization of the AlignementGameRule base class, pre-setting
 * the board dimensions, number of players, and the win condition specific to Gomoku.
 */
public class GomokuRule extends AlignementGameRule {
    /**
     * Constructs a new GomokuRule with predefined parameters for the Gomoku game.
     * The game is played on a 15x15 board with 2 players attempting to align 5 consecutive marks
     * horizontally, vertically, or diagonally to win.
     * <p>
     * This constructor initializes the Gomoku game using the base class AlignementGameRule,
     * setting the board dimensions, the number of players, and the required winning alignment.
     */
    public GomokuRule() {
        super(15, 15, 2, 5);
    }
}