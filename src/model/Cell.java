package model;

import java.io.Serializable;

/**
 * The Cell class represents a single, interactive unit within a game grid or board.
 * Each Cell maintains a visual representation, ownership information, and its highlighted state.
 * The class provides methods to manipulate and query the state of the cell.
 */
public class Cell implements Serializable {
    private boolean isHighlighted;
    private int ownerId;

    public Cell() {
        this.ownerId = -1;
        this.isHighlighted = false;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * Retrieves the owner of the cell. The owner is represented by a {@code Player} object,
     * which indicates the current player in control of this cell. If the cell is unowned,
     * this method returns {@code null}.
     *
     * @return the {@code Player} object representing the owner of the cell, or {@code null}
     *         if the cell is unowned
     */
    public int getOwnerId() {
        return this.ownerId;
    }

    /**
     * Determines whether the cell is empty. A cell is considered empty if it
     * does not have an owner assigned.
     *
     * @return {@code true} if the cell is unowned, otherwise {@code false}
     */
    public boolean isEmpty() {
        return this.ownerId == -1;
    }

    public void setEmpty() {
        ownerId = -1;
        isHighlighted = false;
    }

    /**
     * Marks the current cell as highlighted by setting its highlighted state to true.
     * A highlighted cell is typically used to emphasize its importance or distinction,
     * such as being part of a winning sequence in a game.
     */
    public void setHighlighted(boolean highlighted) {
        this.isHighlighted = highlighted;
    }

    public boolean isHighlighted() {
        return isHighlighted;
    }
}
