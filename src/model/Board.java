package model;

import model.player.factory.RepresentationFactory;


public class Board {
    protected final Cell[][] cells;
    private boolean maximize = false;
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

    public String toString() {
        int indexWidth = String.valueOf(this.height()).length();
        String horizontalSeparator = " ";
        String verticalSeparator = "";
        int cellWidth = horizontalSeparator.length() + 1;

        if (maximize) {
            horizontalSeparator = " | ";
            cellWidth = horizontalSeparator.length() + 1;
            verticalSeparator = " ".repeat(indexWidth + cellWidth/2) + "-".repeat(cellWidth * this.width()) + "\n";
        }

        StringBuilder message = new StringBuilder();
        message.append(" ".repeat(indexWidth));

        for (int j = 0; j < this.width(); j++) {
            message.append(String.format("%" + cellWidth + "d", j + 1));
        }
        message.append("\n").append(verticalSeparator);

        for (int i = 0; i < this.height(); i++) {
            message.append(String.format("%" + indexWidth + "d", i + 1)).append(horizontalSeparator);
            for (int j = 0; j < this.width(); j++) {
                message.append(this.getCell(i, j).render()).append(horizontalSeparator);
            }
            message.append("\n").append(verticalSeparator);
        }
        return (message.toString());
    }

    public void setMaximize(boolean maximize) {
        this.maximize = maximize;
    }
}