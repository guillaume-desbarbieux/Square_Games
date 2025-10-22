package controller.moveAdapter;

import model.Board;
import controller.Rule;
import model.Move;
import model.player.Player;
import controller.ai.ArtificialIntelligence;
import view.InteractionUser;

import java.util.List;

public class RowColInputAdapter implements MoveAdapter {
    private final InteractionUser interact;

    public RowColInputAdapter() {
        this.interact = InteractionUser.getInstance();
    }

    @Override
    public Move getMoveFromHumanPlayer(Board board, Player player) {
        int row = interact.getInt("ligne ?", 1, board.height()) - 1;
        int col = interact.getInt("colonne ?", 1, board.width()) - 1;
        return new Move(player, row, col);
    }

    @Override
    public Move getMoveFromAI(Board board, Rule rule, Player player, List<Player> players, ArtificialIntelligence ai) {
        return ai.getNextMove(board, rule, player, players);
    }
}
