package game.alignementGameRule;

import board.Board;
import move.Move;
import move.factory.ColInputAdapter;

public class Connect4Rule extends AlignementGameRule {
    public Connect4Rule() {
        super("Puissance 4", 6, 7, 2, 4, new ColInputAdapter());
    }

    @Override
    public boolean isMoveValid(Board board, Move move) {
        int row = move.getRow();
        int col = move.getCol();

        if (row < 0
                || row >= board.height()
                || col < 0
                || col >= board.width()) {
            return false;
        }

        if (row == board.height() - 1)
            return board.getCell(row, col).isEmpty();
        else
            return board.getCell(row, col).isEmpty() && !board.getCell(row + 1, col).isEmpty();
    }
}
