package Board;

import Player.Player;

public class Connect4Board extends Board {

    public Connect4Board(int height, int width) {
        super(height, width);
    }

    @java.lang.Override
    public void playMove(int row, int col, Player player) {
        if (isPlayable(new int[]{row, col}))
            setCell(row, col, player);
    }

    @java.lang.Override
    public boolean isPlayable(int[] cell) {
        int row = cell[0];
        int col = cell[1];

        if (row < 0 || row >= this.height() || col < 0 || col >= this.width())
            return false;
        if (!getCell(row,col).isEmpty())
            return false;
        if (row == this.height() - 1)
            return true;
        return (!getCell(row + 1, col).isEmpty());
    }

    public int playCol(int col) {
        for (int row = 0; row < this.height(); row++) {
            if (cells[row][col].isEmpty())
                return row;
        }
        return -1;
    }
}
