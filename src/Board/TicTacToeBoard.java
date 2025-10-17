package Board;

import Player.Player;

public class TicTacToeBoard extends Board {

    public TicTacToeBoard(int height, int width) {
        super(height, width);
    }

    public void playMove(int row, int col, Player player) {
        if (getCell(row, col).isEmpty())
            setCell(row, col, player);
    }

    @Override
    public boolean isPlayable(int[] move) {
        return getCell(move[0], move[1]).isEmpty();
    }
}