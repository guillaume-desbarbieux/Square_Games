package model.move;

import model.MoveStrategy;

import java.io.Serial;
import java.io.Serializable;

public class ComplexMove implements MoveStrategy, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final int playerId;
    private final Coordinates startCoordinates;
    private final Coordinates endCoordinates;

    public ComplexMove(int playerId, Coordinates startCoordinates, Coordinates endCoordinates) {
        this.playerId = playerId;
        this.startCoordinates = startCoordinates;
        this.endCoordinates = endCoordinates;

    }


    @Override
    public int getPlayerId() {
        return this.playerId;
    }

    @Override
    public int getRow() {
        return endCoordinates.getRow();
    }

    @Override
    public int getCol() {
        return endCoordinates.getCol();
    }

    public Coordinates getStart() {
        return startCoordinates;
    }

    public Coordinates getEnd() {
        return endCoordinates;
    }

    @Override
    public String toString() {
        return "{start=" + startCoordinates.toString() + ", end=" + endCoordinates.toString() + '}';
    }
}