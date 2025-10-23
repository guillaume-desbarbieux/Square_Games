package model.rule;

import controller.GameTitle;

public class TicTacToeRule extends AlignementGameRule {
    public TicTacToeRule() {
        super(GameTitle.TIC_TAC_TOE, 3, 3, 2, 3);
    }
}
