package player;

import board.Board;
import move.Move;
import move.factory.MoveInputAdapter;
import player.factory.Color;

public abstract class Player {
    private final int id;
    private final String representation;

    public Player(int id, char symbol, Color color) {
        this.id = id;
        this.representation = "" + color + symbol + Color.RESET;
    }

    public String getRepresentation() {
        return this.representation;
    }

    public int getId() {
        return this.id;
    }

    public abstract Move getNextMove(Board board, MoveInputAdapter adapter);
}
