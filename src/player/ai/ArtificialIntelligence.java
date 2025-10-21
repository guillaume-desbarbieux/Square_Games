package player.ai;

import board.Board;
import move.Move;
import java.util.List;

public interface ArtificialIntelligence {
    Move getNextMove(Board board, List<Move> listPlayableMoves);
}
