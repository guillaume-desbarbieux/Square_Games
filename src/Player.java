public class Player {
    private final String representation;
    private final int id;


    public Player(int id, char symbol, Color color) {
        this.id = id;
        this.representation = " " + color + symbol + Color.RESET + " ";
    }

    public String getRepresentation() {
        return this.representation;
    }

    public int getId() {
        return this.id;
    }
}
