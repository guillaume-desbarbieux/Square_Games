package player.ai;

import board.Board;
import move.Move;

public interface ArtificialIntelligence {
    Move getNextMove(Board board);
}
