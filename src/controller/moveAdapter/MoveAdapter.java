package controller.moveAdapter;

import model.Board;
import model.Rulable;
import model.Move;
import model.player.Player;
import model.player.ai.ableToPlayAlone;
import java.util.List;

/**
 * The MoveAdapter interface defines the contract for retrieving moves
 * for both human and AI players in a game. Implementing classes adapt
 * the method of obtaining these moves based on their specific requirements
 * for user interaction or AI logic.
 */
public interface MoveAdapter {
    /**
     * Retrieves the next move from a human player based on the current state of the game board.
     * This method is responsible for interpreting the human player's input to generate a valid game move.
     * @param board the current state of the game board, which provides the layout and cells
     *              available for the human player to make a move.
     * @param player the human player making the move.
     * @return the move made by the human player, represented as an instance of the {@code Move} class.
     */
    Move getMoveFromHumanPlayer(Board board, Player player);

    /**
     * Retrieves the next move for a player using the provided AI system, based on the current state of the board,
     * the rules of the game, and the states of all participants.
     * @param board   the current state of the game board, representing the layout of the game at this moment.
     * @param rule    the rules of the game that define valid moves, game-ending conditions, and move constraints.
     * @param player  the player for whom the move is being determined.
     * @param players the list of all players currently participating in the game.
     * @param ai      the Artificial Intelligence system used to calculate the next move for the given player.
     * @return the computed move for the specified player, based on AI logic, game rules, and the current state of the game.
     */
    Move getMoveFromAI(Board board, Rulable rule, Player player, List<Player> players, ableToPlayAlone ai);
}