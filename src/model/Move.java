package model;

import model.player.Player;

/**
 * The Move class represents a single action taken by a player in a game.
 * A move comprises the player making the move, and the row and column
 * on the game board.
 * <p>
 * This class is immutable, ensuring that the properties of a move cannot
 * be modified once the object is created.
 */
public class Move {
    private final Player player;
    private final int row;
    private final int col;

    /**
     * Constructs a Move object to represent an action taken by a player on a specific
     * position on the game board.
     *
     * @param player the player making the move
     * @param row the row index of the game board where the move is made
     * @param col the column index of the game board where the move is made
     */
    public Move (Player player, int row, int col){
        this.player = player;
        this.row = row;
        this.col = col;
    }

    /**
     * Retrieves the player associated with this move.
     *
     * @return the {@code Player} who made this move
     */
    public Player getPlayer() {
        return this.player;
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
