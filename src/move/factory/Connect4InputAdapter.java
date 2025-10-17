package move.factory;

import board.Board;
import move.Connect4Move;
import move.Move;
import player.HumanPlayer;
import ui.InteractionUser;

public class Connect4InputAdapter implements MoveInputAdapter {
    private final InteractionUser interact;

    public Connect4InputAdapter(InteractionUser interact) {
        this.interact = interact;
    }

    @Override
    public Move getMoveFromHumanPlayer(HumanPlayer player, Board board) {
        int col = interact.getInt("colonne ?", 1, board.width()) - 1;
        return new Connect4Move(player.getId(), col);
    }
}
