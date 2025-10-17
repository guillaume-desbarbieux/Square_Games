package move;

public abstract class Move {
    protected final int playerId;

    public Move(int playerId) {
        this.playerId = playerId;
    }

    public int getPlayerId() {
        return playerId;
    }
}
