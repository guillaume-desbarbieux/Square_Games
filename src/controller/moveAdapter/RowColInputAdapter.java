package controller.moveAdapter;

import model.Board;
import model.rule.Rule;
import model.Move;
import model.player.Player;
import model.player.ai.ArtificialIntelligence;
import view.View;

import java.util.List;

public class RowColInputAdapter implements MoveAdapter {
    private final View view;

    public RowColInputAdapter() {
        this.view = View.getInstance();
    }

    @Override
    public Move getMoveFromHumanPlayer(Board board, Player player) {
        int row = view.getInt("ligne ?", 1, board.height()) - 1;
        int col = view.getInt("colonne ?", 1, board.width()) - 1;
        return new Move(player, row, col);
    }

    @Override
    public Move getMoveFromAI(Board board, Rule rule, Player player, List<Player> players, ArtificialIntelligence ai) {
        return ai.getNextMove(board, rule, player, players);
    }
}
