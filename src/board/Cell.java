package board;

import player.Player;
import player.Representation;

public class Cell {
    private Representation representation;
    private Player owner;


    public Cell(Representation representation) {
        this.representation = representation;
        this.owner = null;
    }

    public Representation getRepresentation() {
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
