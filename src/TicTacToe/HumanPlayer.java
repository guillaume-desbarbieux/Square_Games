package TicTacToe;

public class HumanPlayer extends Player {
    public HumanPlayer(int id, char symbol, Color color) {
        super(id, symbol, color);
    }

    @Override
    public int[] getMove(InteractionUser interact, View view, Board board) {
        int x = 0;
        int y = 0;

        while (x < 1 || x > board.height()) {
            view.displayMessage("ligne ? [1.." + board.height() + "]");
            x = interact.getIntFromUser();

        }
        while (y < 1 || y > board.width()) {
            view.displayMessage("colonne ? [1.." + board.width() + "]");
            y = interact.getIntFromUser();
        }
        if (board.getCell(x-1, y-1).isEmpty()) {
            return new int[]{x - 1, y - 1};
        } else {
            return getMove(interact, view, board);
        }
    }
}
