package model.player;

import model.player.representation.Representation;

/**
 * The HumanPlayer class represents a human-controlled player in the game.
 * It is a concrete subclass of the abstract Player class and inherits its
 * properties and methods.
 * <p>
 * This class allows for the instantiation of a player that is controlled by
 * a human through interaction with the game's user interface or input system.
 */
public class HumanPlayer extends Player {


    /**
     * Constructs a new HumanPlayer object with the specified unique identifier
     * and visual representation. This constructor initializes a human-controlled
     * player with the given parameters by invoking the superclass constructor.
     *
     * @param id the unique identifier for the human player
     * @param representation the visual representation associated with the human player
     */
    public HumanPlayer(int id, Representation representation) {
        super(id, representation );
    }
}