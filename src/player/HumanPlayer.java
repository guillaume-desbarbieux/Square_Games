package player;

import board.Board;
import move.Move;
import move.factory.MoveInputAdapter;

public class HumanPlayer extends Player {

    public HumanPlayer(int id, Representation representation) {
        super(id, representation );
    }

    @Override
    public Move getNextMove(Board board, MoveInputAdapter adapter) {
        return adapter.getMoveFromHumanPlayer(this, board);
    }
}