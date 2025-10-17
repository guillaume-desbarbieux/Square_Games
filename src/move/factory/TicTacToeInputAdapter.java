package move.factory;

import board.Board;
import move.Move;
import move.TicTacToeMove;
import player.HumanPlayer;
import ui.InteractionUser;

public class TicTacToeInputAdapter implements MoveInputAdapter{
    private final InteractionUser interact;

    public TicTacToeInputAdapter(InteractionUser interact) {
        this.interact = interact;
    }

    @Override
    public Move getMoveFromHumanPlayer(HumanPlayer player, Board board) {
        int row = interact.getInt("ligne ?", 1, board.height()) - 1;
        int col = interact.getInt("colonne ?", 1, board.width()) - 1;
        return new TicTacToeMove(player.getId(), row, col);
    }
}
