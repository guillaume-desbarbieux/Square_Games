public class Cell {
    private String representation;
    private Player owner;


    public Cell() {
        this.representation = "   ";
        this.owner = null;
    }

    public String getRepresentation() {
        return this.representation;
    }

    public void setOwner(Player player) {
        this.representation = player.getRepresentation();
        this.owner = player;
    }

    public int getOwnerId() {
        if (this.owner == null)
            return -1;
        else
            return owner.getId();
    }

    public boolean isEmpty(){
        return this.owner == null;
    }
}
