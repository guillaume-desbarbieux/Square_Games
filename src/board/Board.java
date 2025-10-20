package board;
import player.factory.RepresentationFactory;


public class Board {
    protected final Cell[][] cells;

    public Board(int height, int width, RepresentationFactory factory) {
        this.cells = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells[i][j] = new Cell(factory.getRepresentation());
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

    public boolean isNotFull() {
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (cell.isEmpty())
                    return true;
            }
        }
        return false;
    }
}