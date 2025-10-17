package player;

import board.Board;
import move.Move;
import move.factory.MoveInputAdapter;
import player.ai.ArtificialIntelligence;
import player.factory.Color;

public class ArtificialPlayer extends Player {
    private final ArtificialIntelligence ai;

    public ArtificialPlayer(int id, char symbol, Color color, ArtificialIntelligence ai) {
        super(id, symbol, color);
        this.ai = ai;
    }

    @Override
    public Move getNextMove(Board board, MoveInputAdapter adapter) {
        return ai.getNextMove(board);
    }
}
