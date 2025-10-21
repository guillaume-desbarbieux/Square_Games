package game.alignementGameRule;

import move.factory.RowColInputAdapter;

public class GomokuRule extends AlignementGameRule {
    public GomokuRule() {
        super("Gomoku", 15, 15, 2, 5, new RowColInputAdapter());
    }
}