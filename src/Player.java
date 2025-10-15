public class Player {
    private final String representation;
    private final int id;


    public Player(int id, char symbol) {
        this.id = id;
        this.representation = " " + symbol + " ";
    }

    public String getRepresentation() {
        return this.representation;
    }

    public int getId() {
        return this.id;
    }
}
