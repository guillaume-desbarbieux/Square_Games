package move;

public class ColMove extends Move {
    private final int col;

    public ColMove(int col) {
        super();
        this.col = col;
    }
    public int getCol() {
        return col;
    }
}