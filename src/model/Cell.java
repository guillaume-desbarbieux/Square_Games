package model;

import model.player.representation.Representation;

/**
 * The Cell class represents a single, interactive unit within a game grid or board.
 * Each Cell maintains a visual representation, ownership information, and its highlighted state.
 * The class provides methods to manipulate and query the state of the cell.
 */
public class Cell {
    private Representation representation;
    private boolean isHighlighted;
    private int ownerId;

    /**
     * Constructs a Cell instance with an initial representation and default parameters.
     * The cell is initialized as unowned and not highlighted, with its visual representation
     * set to the provided emptyRepresentation.
     *
     * @param emptyRepresentation the initial visual representation of the cell, typically
     *        representing an unoccupied or default state
     */
    public Cell(Representation emptyRepresentation) {
        this.representation = emptyRepresentation;
        this.ownerId = -1;
        this.isHighlighted = false;
    }

    /**
     * Generates the visual representation of the cell based on its current state.
     * If the cell is highlighted, its highlighted representation is returned.
     * Otherwise, the standard representation of the cell is returned.
     *
     * @return a string representing the visual appearance of the cell, either in its
     *         highlighted form or its standard form
     */
    public String render(){
        return representation.render(isHighlighted);
    }

    /**
     * Sets the owner of the cell and updates its visual representation.
     *
     * @param ownerId the unique identifier of the owner to be associated with the cell
     * @param representation the {@code Representation} object defining the updated visual state of the cell
     */
    public void setOwner(int ownerId, Representation representation) {
        this.representation = representation;
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

    /**
     * Marks the current cell as highlighted by setting its highlighted state to true.
     * A highlighted cell is typically used to emphasize its importance or distinction,
     * such as being part of a winning sequence in a game.
     */
    public void highlight() {
        this.isHighlighted = true;
    }

    /**
     * Retrieves the current visual representation of the cell.
     *
     * @return the {@code Representation} object associated with the cell, which defines its
     *         standard or highlighted visual format based on state.
     */
    public Representation getRepresentation() {
        return this.representation;
    }
}
