package model;

import java.util.List;

public interface RulableStrategy {

    int getDefaultNbPlayers();

    Board getInitialBoard();

    void playMove(Board board, MoveStrategy move);

    List<MoveStrategy> getValidMoves(Board board, int playerId);

    boolean isMoveValid(Board board, MoveStrategy move);

    boolean isGameDraw(Board board);

    boolean isMoveWinning(Board board, MoveStrategy lastMove);

    int getNextPlayerId(Board board, MoveStrategy lastMove, List<Integer> playersId);

    int getFirstPlayerId(List<Integer> listIds);

    int getHeight();

    int getWidth();
}
