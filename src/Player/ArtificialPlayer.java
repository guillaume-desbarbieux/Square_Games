package Player;

import Board.Board;

public class ArtificialPlayer extends Player {
    private final ArtificialIntelligence ai;

    public ArtificialPlayer(int id, char symbol, Color color, ArtificialIntelligence ai) {
        super(id, symbol, color);
        this.ai = ai;
    }

    @Override
    public int[] getNextMove(Board board) {
        return ai.getNextMove(board);
    }
}
