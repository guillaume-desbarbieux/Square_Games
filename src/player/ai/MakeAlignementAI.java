package player.ai;

import board.Board;
import move.Move;
import java.util.List;
import java.util.Random;

public class MakeAlignementAI implements ArtificialIntelligence {
    @Override
    public Move getNextMove(Board board, List<Move> listPlayableMoves) {
        Random random = new Random();
        return listPlayableMoves.get(random.nextInt(0, listPlayableMoves.size()));
    }
}
