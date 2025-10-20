package move.factory;

import board.Board;
import move.Connect4Move;
import move.Move;
import ui.InteractionUser;

public class Connect4InputAdapter implements MoveInputAdapter {
    private final InteractionUser interact;

    public Connect4InputAdapter() {
        this.interact = InteractionUser.getInstance();
    }

    @Override
    public Move getMoveFromHumanPlayer(Board board) {
        int col = interact.getInt("colonne ?", 1, board.width()) - 1;
        return new Connect4Move(col);
    }
}
