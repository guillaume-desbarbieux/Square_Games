package controller.moveAdapter;

import model.Board;
import model.MoveStrategy;
import model.RulableStrategy;
import model.move.ComplexMove;
import model.move.Coordinates;
import model.player.Player;
import model.player.ai.ableToPlayAlone;
import view.Viewable;
import view.dictionary.GameMessage;

import java.util.List;

public class ComplexMoveAdapter implements MoveAdapter {
    private final Viewable view;
    public ComplexMoveAdapter(Viewable view) {
        this.view = view;
    }

    @Override
    public MoveStrategy getMoveFromHumanPlayer(Board board, Player player) {
        int startRow = view.getInt(GameMessage.GET_ROW, 1, board.getHeight()) - 1;
        int startCol = view.getInt(GameMessage.GET_COL, 1, board.getWidth()) - 1;
        int endRow = view.getInt(GameMessage.GET_ROW, 1, board.getHeight()) - 1;
        int endCol = view.getInt(GameMessage.GET_COL, 1, board.getWidth()) - 1;
        boolean isTurnFinish = true;
        if (Math.abs(startRow-endRow) == 2)
            isTurnFinish = view.getBool(GameMessage.IS_TURN_FINISH);

        return new ComplexMove(player.getId(), new Coordinates(startRow,startCol), new Coordinates(endRow,endCol), isTurnFinish);
    }

    @Override
    public MoveStrategy getMoveFromAI(Board board, RulableStrategy rule, Player player, List<Player> players, ableToPlayAlone ai) {
        return null;
    }
}
