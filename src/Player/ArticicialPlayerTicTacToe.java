package Player;

import Board.TicTacToeBoard;
import Interact.InteractionUser;
import Interact.View;

import java.util.List;
import java.util.Random;

public class ArticicialPlayerTicTacToe extends Player {

    public ArticicialPlayerTicTacToe(int id, char symbol, Color color) {
        super(id, symbol, color);
    }

    @Override
    public int[] getMove(InteractionUser interact, View view, TicTacToeBoard board) {
        Random random = new Random();
        List<int[]> listPlayableCells = board.getPlayableCells();
        return listPlayableCells.get(random.nextInt(0, listPlayableCells.size()));
    }
}
