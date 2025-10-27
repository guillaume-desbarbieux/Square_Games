package model;

import java.util.List;

/**
 * The Board class represents a grid or board consisting of cells.
 * Each cell within the board is initialized with a representation
 * provided by a RepresentationFactory. The board supports operations
 * to retrieve individual cells, retrieve its dimensions, and create
 * a copy of itself.
 */
public class Board {
    /**
     * A two-dimensional array representing the grid of cells in the board.
     * Each element in the array is a {@code Cell} object, which corresponds to
     * a specific position on the board. The array structure reflects the rows
     * and columns of the board, allowing interaction with individual cells
     * based on their indices.
     * This field is marked as {@code protected} to allow access from subclasses,
     * and {@code final} to ensure the grid structure itself cannot be reassigned
     * after initialization.
     */
    private final Cell[][] cells;

    public Board(int height, int width) {
        this.cells = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    /**
     * Retrieves the cell at the specified row and column indices on the board.
     *
     * @param i the zero-based index of the row where the cell is located
     * @param j the zero-based index of the column where the cell is located
     * @return the cell at the specified row and column
     */
    public Cell getCell(int i, int j) {
        return cells[i][j];
    }

    /**
     * Retrieves the height of the board, which is the number of rows
     * in the grid.
     *
     * @return the number of rows in the board
     */
    public int getHeight() {
        return this.cells.length;
    }

    /**
     * Retrieves the width of the board, which is the number of columns
     * in the grid.
     *
     * @return the number of columns in the board
     */
    public int getWidth() {
        return this.cells[0].length;
    }

    /**
     * Constructs a Board instance with the specified grid of cells.
     * This private constructor is used to initialize the board using an already
     * defined two-dimensional array of cells, typically for cloning or internal purposes.
     *
     * @param cells a two-dimensional array representing the grid of cells
     */
    private Board(Cell[][] cells) {
        this.cells = cells;
    }

    /**
     * Creates and returns a deep copy of the current Board instance.
     * The copied board will have the same dimensions, and each cell
     * within the board is individually cloned with its representation
     * and owner (if applicable).
     *
     * @return a new Board instance that is a deep copy of the current Board
     */
    public Board copy() {
        Cell[][] clonedCells = new Cell[this.getHeight()][this.getWidth()];

        for (int row = 0; row < this.getHeight(); row++) {
            for (int col = 0; col < this.getWidth(); col++) {
                Cell original = this.cells[row][col];
                Cell copied = new Cell();
                if (!original.isEmpty())
                    copied.setOwnerId(original.getOwnerId());
                clonedCells[row][col] = copied;
            }
        }
        return new Board(clonedCells);
    }

    public void highlight(List<Cell> winningCells) {
        for (Cell cell : winningCells)
            highlight(cell,true);
    }

    public void highlight(Cell cell, boolean highlighted) {
        cell.setHighlighted(highlighted);
    }
}