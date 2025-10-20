package player;

import board.Board;
import move.Move;
import move.factory.MoveInputAdapter;

public abstract class Player {
    private final int id;
    private final Representation representation;

    public Player(int id, Representation representation) {
        this.id = id;
        this.representation = representation;
    }

    public Representation getRepresentation() {
        return this.representation;
    }

    public int getId() {
        return this.id;
    }

    public abstract Move getNextMove(Board board, MoveInputAdapter adapter);
}
