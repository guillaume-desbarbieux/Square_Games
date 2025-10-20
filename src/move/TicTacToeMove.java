package move;

public class TicTacToeMove extends Move {
    private final int row;
    private final int col;

    public TicTacToeMove(int row, int col) {
        super();
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }
}
