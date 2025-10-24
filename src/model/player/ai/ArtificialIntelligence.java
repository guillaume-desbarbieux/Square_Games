package model.player.ai;

import model.Board;
import model.player.Player;
import model.Rule;
import model.Move;

import java.util.List;

/**
 * Interface representing an Artificial Intelligence system for gameplay decision-making.
 * Implementations of this interface define the logic for determining the next move
 * of a player in a turn-based game.
 */
public interface ArtificialIntelligence {
    /**
     * Determines the next move for a player in a turn-based game based on the current
     * state of the board, applicable rules, and other players' states.
     *
     * @param board   the current state of the game board, representing the layout of the game at this moment.
     * @param rule    the rules of the game that define valid moves, game-ending conditions, and other constraints.
     * @param player  the player for whom the next move is being calculated.
     * @param players the list of all players in the game, which may include both opponents and allies.
     * @return the next move to be played for the specified player, taking into account the game rules
     *         and the state of the board.
     */
    Move getNextMove(Board board, Rule rule, Player player, List<Player> players);
}
