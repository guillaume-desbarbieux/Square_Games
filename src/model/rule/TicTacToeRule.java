package model.rule;

import controller.moveAdapter.RowColInputAdapter;

public class TicTacToeRule extends AlignementGameRule {
    public TicTacToeRule() {
        super("Tic Tac Toe", 3, 3, 2, 3, new RowColInputAdapter());
    }
}
