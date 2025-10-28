package model.player;

import model.player.representation.Representation;

import java.io.Serial;
import java.io.Serializable;

/**
 * The Player class is an abstract representation of a game player. It provides
 * the foundational structure for identifying and visually representing a player
 * in the game. Extending classes must implement specific functionalities
 * pertaining to the type of player.
 * <p>
 * This class encapsulates a player's unique identifier and their visual representation.
 * It also provides methods for retrieving these properties and rendering the player's
 * visual appearance within the game.
 */
public abstract class Player implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final int id;
    private String name;
    private final Representation representation;

    /**
     * Constructs a Player instance with the specified unique identifier and visual representation.
     * This constructor is intended to be called by subclasses of Player to initialize the base
     * properties of a player.
     *
     * @param id the unique identifier for the player
     * @param representation the visual representation associated with the player
     */
    public Player(int id, Representation representation) {
        this.id = id;
        this.representation = representation;
    }

    /**
     * Retrieves the visual representation associated with this object.
     *
     * @return the visual representation, encapsulated in a {@code Representation} object
     */
    public Representation getRepresentation() {
        return this.representation;
    }

    /**
     * Renders the player's visual representation as a string.
     * The method delegates the rendering logic to the underlying representation
     * of the player without highlighting the representation.
     *
     * @return a string representation of the player, as defined by the underlying visual representation
     */
    public String render() {
        return this.representation.render(false);
    }

    /**
     * Retrieves the unique identifier associated with the player.
     *
     * @return the unique identifier of the player
     */
    public int getId() {
        return this.id;
    }
}