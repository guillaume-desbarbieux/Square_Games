package model.player;

import model.Board;
import model.rule.Rule;
import model.Move;
import controller.MoveAdapter;

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