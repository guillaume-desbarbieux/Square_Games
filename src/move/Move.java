package move;

import player.Player;

public class Move {
    protected final Player player;
    protected final int row;
    protected final int col;

    public Move (Player player, int row, int col){
        this.player = player;
        this.row = row;
        this.col = col;
    }

    public Player getPlayer() {
        return this.player;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }
}
