package model;

import model.player.representation.RepresentationFactory;

/**
 * The Board class represents a grid or board consisting of cells.
 * Each cell within the board is initialized with a representation
 * provided by a RepresentationFactory. The board supports operations
 * to retrieve individual cells, retrieve its dimensions, and create
 * a copy of itself.
 */
public class Board {
    protected final Cell[][] cells;

    /**
     * Constructs a Board instance with the specified height, width, and cell representations.
     * Each cell in the board is initialized with a representation provided by the given
     * RepresentationFactory.
     *
     * @param height the number of rows in the board
     * @param width the number of columns in the board
     * @param factory the factory used to generate the initial representation for each cell
     */
    public Board(int height, int width, RepresentationFactory factory) {
        this.cells = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells[i][j] = new Cell(factory.getRepresentation());
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
    public int height() {
        return this.cells.length;
    }

    /**
     * Retrieves the width of the board, which is the number of columns
     * in the grid.
     *
     * @return the number of columns in the board
     */
    public int width() {
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