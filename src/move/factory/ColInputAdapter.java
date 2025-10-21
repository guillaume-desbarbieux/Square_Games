package move.factory;

import board.Board;
import move.ColMove;
import move.Move;
import player.ai.ArtificialIntelligence;
import ui.InteractionUser;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public Move getMoveFromAI(Board board, ArtificialIntelligence ai) {
        List<Move> listPlayableMoves = new ArrayList<>();
        for (int col = 0; col < board.width(); col++) {
            if (board.getCell(0, col).isEmpty()) {
                listPlayableMoves.add(new ColMove(col));
            }
        }
        return  ai.getNextMove(board, listPlayableMoves);
    }
}
