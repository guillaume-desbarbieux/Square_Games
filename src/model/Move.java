package model;

/**
 * The Move class represents a single action taken by a player in a game.
 * A move comprises the player making the move, and the row and column
 * on the game board.
 * <p>
 * This class is immutable, ensuring that the properties of a move cannot
 * be modified once the object is created.
 */
public class Move {
    private final int playerId;
    private final int row;
    private final int col;

    /**
     * Constructs a Move instance representing a player's action on the game board.
     * Each Move specifies the player making the move and the row and column
     * indices of the board where the move is applied.
     *
     * @param playerId the unique identifier of the player making this move
     * @param row the row index on the game board where the move is made
     * @param col the column index on the game board where the move is made
     */
    public Move (int playerId, int row, int col){
        this.playerId = playerId;
        this.row = row;
        this.col = col;
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
        return col;
    }

    /**
     * Retrieves the row index of the game board where the move is recorded.
     *
     * @return the row index as an integer
     */
    public int getRow() {
        return row;
    }
}
