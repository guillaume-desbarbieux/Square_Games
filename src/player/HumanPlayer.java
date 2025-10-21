package player;

import board.Board;
import game.Rule;
import move.Move;
import move.factory.MoveAdapter;

import java.util.List;

public class HumanPlayer extends Player {

    public HumanPlayer(int id, Representation representation) {
        super(id, representation );
    }

    @Override
    public Move getNextMove(Board board, Rule rule, MoveAdapter adapter, List<Player> players) {
        return adapter.getMoveFromHumanPlayer(board, this);
    }
}