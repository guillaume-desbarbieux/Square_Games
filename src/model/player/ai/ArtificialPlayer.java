package model.player.ai;

import model.player.Player;
import model.player.representation.Representation;

/**
 * Represents a player controlled by an artificial intelligence system. This class
 * extends the abstract Player class and incorporates an implementation of the
 * ArtificialIntelligence interface to automate decision-making during gameplay.
 */
public class ArtificialPlayer extends Player {
    private final Playable ai;

    /**
     * Constructs an instance of ArtificialPlayer, integrating a unique identifier,
     * a representation for visualization, and an ArtificialIntelligence system
     * responsible for automated decision-making during gameplay.
     *
     * @param id              the unique identifier for this artificial player.
     * @param representation  the graphical or symbolic representation of the player on the game board.
     * @param ai              the ArtificialIntelligence instance associated with this player
     *                        for determining its actions during the game.
     */
    public ArtificialPlayer(int id, Representation representation, Playable ai) {
        super(id, representation);
        this.ai = ai;
    }

    /**
     * Retrieves the Artificial Intelligence (AI) system associated with this artificial player.
     *
     * @return the {@link Playable} instance used for decision-making by this player
     */
    public Playable getAi() {
        return this.ai;
    }
}