package Player;

import Board.Board;
import Interact.InteractionUser;

public class HumanPlayer extends Player {
    private final InteractionUser interact;

    public HumanPlayer(int id, char symbol, Color color, InteractionUser interact) {
        super(id, symbol, color);
        this.interact = interact;
    }

    @Override
    public int[] getNextMove(Board board) {
        int[] move = new int[2];
        move[0] = interact.getInt("ligne ?", 1, board.height()) - 1;
        move[1] = interact.getInt("colonne ?", 1, board.width()) - 1;
        return move;
    }
}