package model.move;

public class Coordinates {
    private final int row;
    private final int col;

    public Coordinates (int row, int col){
        this.col = col;
        this.row = row;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public String toString() {
        return "(" + (row + 1) + "," + (col + 1) +")";
    }
}
