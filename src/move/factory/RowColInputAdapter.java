package move.factory;

import board.Board;
import move.Move;
import move.RowColMove;
import ui.InteractionUser;

public class RowColInputAdapter implements MoveInputAdapter{
    private final InteractionUser interact;

    public RowColInputAdapter() {
        this.interact = InteractionUser.getInstance();
    }

    @Override
    public Move getMoveFromHumanPlayer(Board board) {
        int row = interact.getInt("ligne ?", 1, board.height()) - 1;
        int col = interact.getInt("colonne ?", 1, board.width()) - 1;
        return new RowColMove(row, col);
    }
}
