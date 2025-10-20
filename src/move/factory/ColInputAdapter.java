package move.factory;

import board.Board;
import move.ColMove;
import move.Move;
import ui.InteractionUser;

public class ColInputAdapter implements MoveInputAdapter {
    private final InteractionUser interact;

    public ColInputAdapter() {
        this.interact = InteractionUser.getInstance();
    }

    @Override
    public Move getMoveFromHumanPlayer(Board board) {
        int col = interact.getInt("colonne ?", 1, board.width()) - 1;
        return new ColMove(col);
    }
}
