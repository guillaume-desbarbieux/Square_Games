package model.rule;

import model.Board;
import model.MoveStrategy;
import model.RulableStrategy;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Represents the general rules and behavior for a board game.
 * This abstract class defines the structure for implementing specific game rules,
 * such as initializing a board, validating moves, determining game state,
 * and handling player turns.
 */
public abstract class Rule implements RulableStrategy, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final int height;
    private final int width;
    private final int defaultNbPlayers;

    /**
     * Constructs a Rule object with specified board dimensions and a default number of players.
     *
     * @param height           the height of the game board
     * @param width            the width of the game board
     * @param defaultNbPlayers the default number of players in the game
     */
    public Rule(int height, int width, int defaultNbPlayers) {
        this.height = height;
        this.width = width;
        this.defaultNbPlayers = defaultNbPlayers;
    }

    /**
     * Retrieves the default number of players for the game.
     *
     * @return the default number of players
     */
    public int getDefaultNbPlayers() {
        return defaultNbPlayers;
    }

    /**
     * Initializes and retrieves the starting configuration of the game board based on game rules.
     * This method is responsible for creating a new {@code Board} object representing the initial state
     * of the game, typically with all cells empty or in their default configuration.
     *
     * @return a {@code Board} object representing the initial state of the game
     */
    public abstract Board getInitialBoard();

    /**
     * Executes a player's move on the given game board. This method implements the
     * rules for making a move, updates the board state by placing the player's piece
     * at the specified location, and ensures that the game state is modified according
     * to the rules of the specific game.
     *
     * @param board the current state of the game board, which will be updated with the move
     * @param move  the move to be played, containing the player making the move and
     *              the row and column where the piece is to be placed
     */
    public abstract void playMove(Board board, MoveStrategy move);

    public abstract List<MoveStrategy> getValidMoves(Board board, int playerId);

    /**
     * Determines whether the specified move is valid, according to the game's rules.
     * A move is considered valid if it adheres to the constraints defined by the game logic,
     * such as being within the bounds of the board, targeting a valid position, and following
     * the game's specific requirements for move placement.
     *
     * @param board the current state of the game board, represented as a {@code Board} object
     * @param move  the move to validate, represented as a {@code Move} object containing
     *              the player, row, and column of the intended action
     * @return true if the move is valid based on the game's rules, false otherwise
     */
    public abstract boolean isMoveValid(Board board, MoveStrategy move);

    /**
     * Determines whether the current game board is full, meaning that every cell
     * is occupied and no further moves can be made.
     *
     * @param board the current state of the game board to check for fullness
     * @return true if every cell on the board is occupied, false otherwise
     */
    public abstract boolean isGameDraw(Board board);

    /**
     * Determines whether the last move played on the given board results in a winning condition.
     * The specific winning condition is defined by the rules of the game and may depend on factors
     * such as aligning a certain number of pieces, configurations on the board, or other game-specific criteria.
     *
     * @param board    the current state of the game board, represented as a {@code Board} object
     * @param lastMove the most recent move played, represented as a {@code Move} object;
     *                 contains the player and position (row and column) of the move
     * @return true if the last move results in a win based on the game's rules, false otherwise
     */
    public abstract boolean isMoveWinning(Board board, MoveStrategy lastMove);


    public abstract int getNextPlayerId(Board board, MoveStrategy lastMove, List<Integer> playersId);

    public abstract int getFirstPlayerId(List<Integer> listIds);

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}