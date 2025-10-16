package Power4;

public class Board {
    private final Cell[][] cells;

    public Board(int height, int width) {
        this.cells = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    public Cell getCell(int i, int j){
        return cells[i][j];
    }

    public int height() {
        return this.cells.length;
    }

    public int width() {
        return this.cells[0].length;
    }

    public int playCol(int col){
        for (int row = 0 ; row < this.height() ; row++){
            if (cells[row][col].isEmpty())
                    return row;
        }
        return -1;
    }
}
