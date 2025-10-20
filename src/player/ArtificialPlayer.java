package player;

import board.Board;
import move.Move;
import move.factory.MoveInputAdapter;
import player.ai.ArtificialIntelligence;

public class ArtificialPlayer extends Player {
    private final ArtificialIntelligence ai;

    public ArtificialPlayer(int id, Representation representation, ArtificialIntelligence ai) {
        super(id, representation);
        this.ai = ai;
    }

    @Override
    public Move getNextMove(Board board, MoveInputAdapter adapter) {
        return ai.getNextMove(board);
    }
}
