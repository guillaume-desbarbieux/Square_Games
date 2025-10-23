package controller.moveAdapter;

import view.dictionary.GameMessage;
import model.Board;
import model.rule.Rule;
import model.Move;
import model.player.Player;
import model.player.ai.ArtificialIntelligence;
import view.View;

import java.util.List;

/**
 * The ColInputAdapter class implements the MoveAdapter interface and provides methods
 * to retrieve moves for both human and AI players in a game. This class specifically
 * adapts user inputs to determine the column of a move and calculates the corresponding
 * row based on the current state of the game board.
 * <p>
 * Responsibilities:
 * - Handles the interaction with a human player to determine their move by adapting
 *   input from a View instance.
 * - Calculates the row for a move by determining the lowest available cell in the specified column.
 * - Retrieves moves for an AI player by delegating the computation to an ArtificialIntelligence instance.
 */
public class ColInputAdapter implements MoveAdapter {
    private final View view;

    public ColInputAdapter(View view) {
        this.view = view;
    }

    /**
     * Retrieves a move for a human player by prompting the player to select a column
     * and determining the lowest available row in the specified column.
     *
     * @param board the current state of the game board
     * @param player the player making the move
     * @return a Move instance representing the player's move, including the player, row, and column
     */
    @Override
    public Move getMoveFromHumanPlayer(Board board, Player player) {
        int col = view.getInt(GameMessage.GET_COL, 1, board.width()) - 1;
        int row = getRowPlaying(board, col);
        return new Move(player, row, col);
    }

    /**
     * Determines the lowest available row in the specified column of the game board.
     *
     * @param board the current state of the game board
     * @param col the column index for which the lowest available row is to be found
     * @return the index of the lowest available row in the specified column, or -1 if the column is full
     */
    public int getRowPlaying(Board board, int col) {
        int row = -1;
        while (row + 1 < board.height() && board.getCell(row + 1, col).isEmpty())
            row++;
        return row;
    }

    /**
     * Retrieves the next move for an AI player by delegating the move computation
     * to the provided ArtificialIntelligence instance.
     *
     * @param board the current state of the game board
     * @param rule the set of rules governing the game
     * @param player the AI player for whom the move is being computed
     * @param players the list of all players currently participating in the game
     * @param ai the ArtificialIntelligence instance responsible for determining the move
     * @return a Move instance representing the AI player's computed move
     */
    @Override
    public Move getMoveFromAI(Board board, Rule rule, Player player, List<Player> players, ArtificialIntelligence ai) {
       return ai.getNextMove(board, rule, player, players);
    }
}