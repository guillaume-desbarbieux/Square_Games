package game.rule;

import board.Board;
import move.Move;
import player.Player;

import java.util.List;

public abstract class Rule {
    protected final String name;
    protected final int height;
    protected final int width;
    protected final int defaultNbPlayers;
    protected final Move moveFormat;

    public Rule(String name, int height, int width, int defaultNbPlayers, Move moveFormat) {
        this.name = name;
        this.height = height;
        this.width = width;
        this.defaultNbPlayers = defaultNbPlayers;
        this.moveFormat = moveFormat;
    }

    public String getName() {
        return this.name;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public int getDefaultNbPlayers() {
        return this.defaultNbPlayers;
    }
    public abstract Move getMoveFormat();

    public abstract Board getInitialBoard();

    public abstract Board getBoardAfterMove(Board board, Move move);

    public abstract boolean isGameOver(Board board);

    public abstract List<Move> getValidMoves(Board board);

    public abstract boolean isMoveValid(Move move);

    public abstract boolean isMoveWinning(Move move);

    public abstract Player getNextPlayer(Player player);


}