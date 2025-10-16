package Power4;

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

    public int getMove(GameScanner scanner, Board board) {
        int y = 0;

        while (y < 1 || y > board.width()) {
            System.out.println("colonne ? [1.." + board.width() + "]");
            y = scanner.getIntFromUser();
        }
        return y - 1;
    }
}
