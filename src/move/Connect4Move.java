package move;

public class Connect4Move extends Move {
    private final int col;

    public Connect4Move(int playerId, int col) {
        super(playerId);
        this.col = col;
    }

    public int getCol() {
        return col;
    }
}