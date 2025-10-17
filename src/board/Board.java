package board;

import move.Move;
import player.Player;
import java.util.List;

public abstract class Board {
    protected final Cell[][] cells;

    public Board(int height, int width) {
        this.cells = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    public Cell getCell(int i, int j) {
        return cells[i][j];
    }

    public int height() {
        return this.cells.length;
    }

    public int width() {
        return this.cells[0].length;
    }

    public abstract void playMove(Move move, Player player);

    public void setCell(int row, int col, Player player) {
        getCell(row, col).setOwner(player);
    }

    public boolean isFull() {
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (cell.isEmpty())
                    return false;
            }
        }
        return true;
    }

    public abstract List<Move> getPlayableMoves();

    public abstract boolean isPlayable(Move move);
}