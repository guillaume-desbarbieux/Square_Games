package model;

import java.util.List;

public interface Rulable {

    int getDefaultNbPlayers();

    Board getInitialBoard();

    void playMove(Board board, Move move);

    List<Move> getValidMoves(Board board, int playerId);

    boolean isMoveValid(Board board, Move move);

    boolean isBoardFull(Board board);

    boolean isMoveWinning(Board board, Move lastMove);

    int getNextPlayerId(int playerId, List<Integer> playersId);

    int getFirstPlayerId(List<Integer> listIds);

    int getHeight();

    int getWidth();
}
