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

    public int[] getMove(GameScanner scanner, Board board) {
        int x = 0;
        int y = 0;

        while (x < 1 || x > board.height()) {
            System.out.println("ligne ? [1.." + board.height() + "]");
            x = scanner.getIntFromUser();

        }
        while (y < 1 || y > board.width()) {
            System.out.println("colonne ? [1.." + board.width() + "]");
            y = scanner.getIntFromUser();
        }
        return new int[]{x - 1, y - 1};
    }
}
