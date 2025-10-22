package model.player.ai;

import model.player.Player;
import model.player.representation.Representation;

public class ArtificialPlayer extends Player {
    private final ArtificialIntelligence ai;

    public ArtificialPlayer(int id, Representation representation, ArtificialIntelligence ai) {
        super(id, representation);
        this.ai = ai;
    }

    public ArtificialIntelligence getAi() {
        return this.ai;
    }
}