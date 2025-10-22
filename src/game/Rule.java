package game;

import board.Board;
import move.Move;
import move.factory.MoveAdapter;
import player.Player;

import java.util.List;

public abstract class Rule {
    protected final String name;
    protected final int height;
    protected final int width;
    protected final int defaultNbPlayers;
    protected final MoveAdapter adapter;

    public Rule(String name, int height, int width, int defaultNbPlayers, MoveAdapter adapter) {
        this.name = name;
        this.height = height;
        this.width = width;
        this.defaultNbPlayers = defaultNbPlayers;
        this.adapter = adapter;
    }

    public String getName() {
        return this.name;
    }

    public int getDefaultNbPlayers() {
        return this.defaultNbPlayers;
    }

    public MoveAdapter getAdapter() {
        return adapter;

    }

    public abstract Board getInitialBoard();

    public abstract void playMove(Board board, Move move);

    public abstract boolean isGameOver(Board board, Move lastMove);

    public abstract List<Move> getValidMoves(Board board, Player player);

    public abstract boolean isMoveValid(Board board, Move move);

    public abstract boolean isBoardFull(Board board);

    public abstract boolean isMoveWinning(Board board, Move lastMove);

    public abstract Player getNextPlayer(Player player, List<Player> players);

    public abstract Player getFirstPlayer(List<Player> players);
}