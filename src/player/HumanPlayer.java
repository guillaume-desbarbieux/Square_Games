package player;

import board.Board;
import move.Move;
import move.factory.MoveInputAdapter;
import ui.InteractionUser;
import player.factory.Color;

public class HumanPlayer extends Player {
    private final InteractionUser interact;

    public HumanPlayer(int id, char symbol, Color color, InteractionUser interact) {
        super(id, symbol, color);
        this.interact = interact;
    }

    @Override
    public Move getNextMove(Board board, MoveInputAdapter adapter) {
        return adapter.getMoveFromHumanPlayer(this, board);
    }
}