package model.move;

import model.MoveStrategy;

import java.io.Serial;
import java.io.Serializable;

/**
 * The Move class represents a single action taken by a player in a game.
 * A move comprises the player making the move, and the row and column
 * on the game board.
 * <p>
 * This class is immutable, ensuring that the properties of a move cannot
 * be modified once the object is created.
 */
public class SimpleMove implements MoveStrategy, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final int playerId;
    private final Coordinates coordinates;


    public SimpleMove(int playerId, Coordinates coordinates){
        this.playerId = playerId;
        this.coordinates = coordinates;
    }

    /**
     * Retrieves the player associated with this move.
     *
     * @return the {@code Player} who made this move
     */
    public int getPlayerId() {
        return this.playerId;
    }

    /**
     * Retrieves the column index of the game board associated with this move.
     *
     * @return the column index as an integer
     */
    public int getCol() {
        return coordinates.getCol();
    }

    /**
     * Retrieves the row index of the game board where the move is recorded.
     *
     * @return the row index as an integer
     */
    public int getRow() {
        return coordinates.getRow();
    }

    @Override
    public String toString(){
        return "{" + coordinates.toString() + "}";
    }
}
