package model.player;

import model.Board;
import controller.Rule;
import model.Move;
import controller.moveAdapter.MoveAdapter;
import controller.ai.ArtificialIntelligence;

import java.util.List;

public class ArtificialPlayer extends Player {
    private final ArtificialIntelligence ai;

    public ArtificialPlayer(int id, Representation representation, ArtificialIntelligence ai) {
        super(id, representation);
        this.ai = ai;
    }

    @Override
    public Move getNextMove(Board board, Rule rule, MoveAdapter adapter, List<Player> players) {
        return adapter.getMoveFromAI(board, rule, this, players, ai);
    }
}