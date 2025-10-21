package board;

import player.Player;
import player.Representation;

public class Cell {
    private Representation representation;
    private boolean isHighlighted;
    private Player owner;


    public Cell(Representation emptyRepresentation) {
        this.representation = emptyRepresentation;
        this.owner = null;
        this.isHighlighted = false;
    }

    public String render(){
        return representation.render(isHighlighted);
    }

    public void setOwner(Player player) {
        this.representation = player.getRepresentation();
        this.owner = player;
    }

    public Player getOwner() {
        return this.owner;
    }

    public boolean isEmpty() {
        return this.owner == null;
    }

    public void highlight() {
        this.isHighlighted = true;
    }

    public Representation getRepresentation() {
        return this.representation;
    }
}
