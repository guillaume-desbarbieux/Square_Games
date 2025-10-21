package move.factory;

import board.Board;
import move.Move;
import move.RowColMove;
import player.ai.ArtificialIntelligence;
import ui.InteractionUser;

import java.util.ArrayList;
import java.util.List;

public class RowColInputAdapter implements MoveInputAdapter {
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

    @Override
    public Move getMoveFromAI(Board board, ArtificialIntelligence ai) {
        List<Move> listPlayableMoves = new ArrayList<>();
        for (int row = 0; row < board.height(); row++) {
            for (int col = 0; col < board.width(); col++) {
                if (board.getCell(row, col).isEmpty()) {
                    listPlayableMoves.add(new RowColMove(row, col));
                }
            }
        }
        return ai.getNextMove(board, listPlayableMoves);
    }
}
