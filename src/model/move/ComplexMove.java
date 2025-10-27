package model.move;

import model.MoveStrategy;

public class ComplexMove implements MoveStrategy {
    private final int playerId;
    private final Coordinates startCoordinates;
    private final Coordinates endCoordinates;
    private final boolean isTurnFinish;

    public ComplexMove(int playerId, Coordinates startCoordinates, Coordinates endCoordinates, boolean isTurnFinish){
        this.playerId = playerId;
        this.startCoordinates = startCoordinates;
        this.endCoordinates = endCoordinates;
        this.isTurnFinish = isTurnFinish;

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

    public Coordinates getStart(){
        return startCoordinates;
    }

    public Coordinates getEnd(){
        return endCoordinates;
    }

    public boolean isTurnFinish(){
        return isTurnFinish;
    }
}
