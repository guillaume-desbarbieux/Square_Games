package model.rule;

import controller.GameTitle;

public class GomokuRule extends AlignementGameRule {
    public GomokuRule() {
        super(GameTitle.GOMOKU, 15, 15, 2, 5);
    }
}