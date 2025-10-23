package model.rule;

import model.Board;
import model.Move;
import model.player.Player;

import java.util.List;

/**
 * Represents the general rules and behavior for a board game.
 * This abstract class defines the structure for implementing specific game rules,
 * such as initializing a board, validating moves, determining game state,
 * and handling player turns.
 */
public abstract class Rule {
    protected final int height;
    protected final int width;
    protected final int defaultNbPlayers;

    /**
     * Constructs a Rule object with specified board dimensions and a default number of players.
     *
     * @param height the height of the game board
     * @param width the width of the game board
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
     * @param move the move to be played, containing the player making the move and
     *             the row and column where the piece is to be placed
     */
    public abstract void playMove(Board board, Move move);

    /**
     * Determines whether the game has ended based on the current board state and the last move played.
     * This method evaluates the game's rules to check if there is a winning condition,
     * a full board, or any other condition that signals the end of the game.
     *
     * @param board the current state of the game board
     * @param lastMove the most recent move played, containing the player and the position of the move
     * @return true if the game is over, false otherwise
     */
    public abstract boolean isGameOver(Board board, Move lastMove);

    /**
     * Determines and retrieves the list of valid moves that can be performed
     * by the specified player on the given game board, based on the rules
     * of the game. The returned moves should comply with the game's rules
     * and constraints, ensuring they are legal within the current board state.
     *
     * @param board the current state of the game board; used as the basis for determining valid moves
     * @param player the player for whom valid moves are being determined; affects which moves are legal
     * @return a list of {@code Move} objects representing all valid moves the specified player can make
     */
    public abstract List<Move> getValidMoves(Board board, Player player);

    /**
     * Determines whether the specified move is valid, according to the game's rules.
     * A move is considered valid if it adheres to the constraints defined by the game logic,
     * such as being within the bounds of the board, targeting a valid position, and following
     * the game's specific requirements for move placement.
     *
     * @param board the current state of the game board, represented as a {@code Board} object
     * @param move the move to validate, represented as a {@code Move} object containing
     *             the player, row, and column of the intended action
     * @return true if the move is valid based on the game's rules, false otherwise
     */
    public abstract boolean isMoveValid(Board board, Move move);

    /**
     * Determines whether the current game board is full, meaning that every cell
     * is occupied and no further moves can be made.
     *
     * @param board the current state of the game board to check for fullness
     * @return true if every cell on the board is occupied, false otherwise
     */
    public abstract boolean isBoardFull(Board board);

    /**
     * Determines whether the last move played on the given board results in a winning condition.
     * The specific winning condition is defined by the rules of the game and may depend on factors
     * such as aligning a certain number of pieces, configurations on the board, or other game-specific criteria.
     *
     * @param board the current state of the game board, represented as a {@code Board} object
     * @param lastMove the most recent move played, represented as a {@code Move} object;
     *                 contains the player and position (row and column) of the move
     * @return true if the last move results in a win based on the game's rules, false otherwise
     */
    public abstract boolean isMoveWinning(Board board, Move lastMove);

    /**
     * Determines the next player in the sequence based on the current player
     * and the list of players. This method typically ensures cyclic navigation
     * through the players, adhering to the game rules.
     *
     * @param player the current player whose successor is to be determined
     * @param players the list of all players participating in the game
     * @return the next player in the sequence, determined by the game's rules
     */
    public abstract Player getNextPlayer(Player player, List<Player> players);

    /**
     * Retrieves the first player from the provided list of players.
     * This method identifies and returns the player who is set as
     * the starting player based on the game's rules or initialization logic.
     *
     * @param players the list of players participating in the game;
     *                must not be null or empty
     * @return the {@code Player} object designated as the first player
     */
    public abstract Player getFirstPlayer(List<Player> players);
}