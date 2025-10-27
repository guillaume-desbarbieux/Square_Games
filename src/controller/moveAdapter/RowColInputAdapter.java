package controller.moveAdapter;

import model.MoveStrategy;
import model.RulableStrategy;
import model.move.Coordinates;
import view.dictionary.GameMessage;
import model.Board;
import model.move.SimpleMove;
import model.player.Player;
import model.player.ai.ableToPlayAlone;
import view.Viewable;

import java.util.List;

/**
 * RowColInputAdapter is a concrete implementation of the MoveAdapter interface.
 * This class facilitates obtaining moves from both human players, using a row and
 * column input mechanism via the provided View, and from AI players via their
 * decision-making logic.
 * <p>
 * The adapter prompts human players to specify their move by selecting a row
 * and column within the bounds of the game board. For AI players, it delegates
 * move generation to the provided ArtificialIntelligence instance.
 */
public class RowColInputAdapter implements MoveAdapter {
    private final Viewable view;

    /**
     * Constructs a RowColInputAdapter with the specified View instance.
     * The View is used to facilitate user interaction for obtaining
     * row and column inputs from a human player during gameplay.
     *
     * @param view the View instance responsible for handling user input and display output
     */
    public RowColInputAdapter(Viewable view) {
        this.view = view;
    }

    /**
     * Retrieves a move from a human player by prompting them for row and column inputs.
     * The provided inputs correspond to the desired move location on the game board.
     *
     * @param board the current state of the game board
     * @param player the human player making the move
     * @return the move corresponding to the player's chosen row and column
     */
    @Override
    public MoveStrategy getMoveFromHumanPlayer(Board board, Player player) {
        int row = view.getInt(GameMessage.GET_ROW, 1, board.getHeight()) - 1;
        int col = view.getInt(GameMessage.GET_COL, 1, board.getWidth()) - 1;
        return new SimpleMove(player.getId(),new Coordinates(row, col));
    }

    /**
     * Retrieves the next move for an AI player by delegating the decision-making
     * process to the provided ArtificialIntelligence instance.
     *
     * @param board the current state of the game board
     * @param rule the rule set governing the game
     * @param player the AI player for whom the move is being retrieved
     * @param players the list of all players in the game
     * @param ai the ArtificialIntelligence instance responsible for generating the move
     * @return the move decided by the AI player
     */
    @Override
    public MoveStrategy getMoveFromAI(Board board, RulableStrategy rule, Player player, List<Player> players, ableToPlayAlone ai) {
        return ai.getNextMove(board, rule, player, players);
    }
}
