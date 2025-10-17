package board;

import player.Player;

public class Cell {
    private String representation;
    private Player owner;


    public Cell() {
        this.representation = "·";
        this.owner = null;
    }

    public String getRepresentation() {
        return this.representation;
    }

    public void setOwner(Player player) {
        this.representation = player.getRepresentation();
        this.owner = player;
    }

    public Player getOwner() {
            return this.owner;
    }

    public boolean isEmpty(){
        return this.owner == null;
    }
}
