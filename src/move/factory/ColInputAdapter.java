package move.factory;

import board.Board;
import game.Rule;
import move.Move;
import player.Player;
import player.ai.ArtificialIntelligence;
import ui.InteractionUser;
import java.util.List;

public class ColInputAdapter implements MoveAdapter {
    private final InteractionUser interact;

    public ColInputAdapter() {
        this.interact = InteractionUser.getInstance();
    }

    @Override
    public Move getMoveFromHumanPlayer(Board board, Player player) {
        int col = interact.getInt("colonne ?", 1, board.width()) - 1;
        int row = getRowPlaying(board, col);
        return new Move(player, row, col);
    }

    public int getRowPlaying(Board board, int col) {
        int row = -1;
        while (row + 1 < board.height() && board.getCell(row + 1, col).isEmpty())
            row++;
        return row;
    }

    @Override
    public Move getMoveFromAI(Board board, Rule rule, Player player, List<Player> players, ArtificialIntelligence ai) {
       return ai.getNextMove(board, rule, player, players);
    }
}