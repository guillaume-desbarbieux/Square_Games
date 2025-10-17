package move.factory;

import board.Board;
import move.Move;
import player.HumanPlayer;

public interface MoveInputAdapter {
    Move getMoveFromHumanPlayer(HumanPlayer player, Board board);
}