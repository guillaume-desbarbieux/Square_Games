package TicTacToe;

public class HumanPlayer extends Player {
    public HumanPlayer(int id, char symbol, Color color) {
        super(id, symbol, color);
    }

    @Override
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
        if (board.getCell(x-1, y-1).isEmpty()) {
            return new int[]{x - 1, y - 1};
        } else {
            return getMove(scanner, board);
        }
    }
}
