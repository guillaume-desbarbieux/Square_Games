package Board;

import Player.Player;

import java.util.ArrayList;
import java.util.List;

public class TicTacToeBoard extends Board {

    public TicTacToeBoard(int height, int width) {
        super(height, width);
    }

    public void playMove(int row, int col, Player player) {
        if (getCell(row, col).isEmpty())
            setCell(row, col, player);
    }

    public List<int[]> getPlayableCells() {
        List<int[]> listPlayableCells = new ArrayList<>();

        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                if (getCell(i, j).isEmpty()) {
                    listPlayableCells.add(new int[]{i, j});
                }
            }
        }
        return listPlayableCells;
    }

    @Override
    public boolean isPlayable(int[] move) {
        return getCell(move[0], move[1]).isEmpty();
    }
}