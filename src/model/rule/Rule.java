package model.rule;

import model.Board;
import model.Move;
import model.player.Player;

import java.util.List;

public abstract class Rule {
    protected final int height;
    protected final int width;
    protected final int defaultNbPlayers;

    public Rule(int height, int width, int defaultNbPlayers) {
        this.height = height;
        this.width = width;
        this.defaultNbPlayers = defaultNbPlayers;
    }

    public int getDefaultNbPlayers() {
        return defaultNbPlayers;
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