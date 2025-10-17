package player;

import board.Board;
import move.Move;
import move.factory.MoveInputAdapter;
import player.factory.Color;

public class HumanPlayer extends Player {

    public HumanPlayer(int id, char symbol, Color color) {
        super(id, symbol, color);
    }

    @Override
    public Move getNextMove(Board board, MoveInputAdapter adapter) {
        return adapter.getMoveFromHumanPlayer(this, board);
    }
}