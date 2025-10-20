package move.factory;

import board.Board;
import move.Move;

public interface MoveInputAdapter {
    Move getMoveFromHumanPlayer(Board board);
}