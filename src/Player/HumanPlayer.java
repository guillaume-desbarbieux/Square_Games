package Player;

import Board.TicTacToeBoard;
import Interact.InteractionUser;
import Interact.View;


public class HumanPlayer extends Player {
    public HumanPlayer(int id, char symbol, Color color) {
        super(id, symbol, color);
    }

    @Override
    public int[] getMove(InteractionUser interact, View view, TicTacToeBoard board) {
        int[] move = new int[2];
        move[0] = interact.getInt("ligne ?", 1, board.height()) - 1;
        move[1] = interact.getInt("colonne ?", 1, board.width()) - 1;
        return move;
    }
}
