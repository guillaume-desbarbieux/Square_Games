package model.move;

import java.io.Serial;
import java.io.Serializable;

public class Coordinates implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

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
