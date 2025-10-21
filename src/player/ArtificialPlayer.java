package player;

import board.Board;
import game.Rule;
import move.Move;
import move.factory.MoveAdapter;
import player.ai.ArtificialIntelligence;

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