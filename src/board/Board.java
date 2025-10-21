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

    private Board(Cell[][] cells) {
        this.cells = cells;
    }

    public Board copy() {
        Cell[][] clonedCells = new Cell[this.height()][this.width()];

        for (int row = 0; row < this.height(); row++) {
            for (int col = 0; col < this.width(); col++) {
                Cell original = this.cells[row][col];
                Cell copied = new Cell(original.getRepresentation());
                if (!original.isEmpty())
                    copied.setOwner(original.getOwner());
                clonedCells[row][col] = copied;
            }
        }
        return new Board(clonedCells);
    }
}