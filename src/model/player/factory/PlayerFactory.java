package model.player.factory;

import model.player.representation.Color;
import model.player.representation.Representation;
import model.player.ai.ArtificialPlayer;
import model.player.HumanPlayer;
import model.player.Player;
import model.player.ai.ableToPlayAlone;
import model.player.ai.MakeAlignementAI;
import model.player.representation.RepresentationFactory;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Factory class for creating instances of various types of players, including human players
 * and artificial intelligence-controlled players. The PlayerFactory leverages a
 * RepresentationFactory to assign unique visual representations to each player.
 */
public class PlayerFactory implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final RepresentationFactory representationFactory;

    /**
     * Constructs a new PlayerFactory used to create players with unique visual representations.
     * This constructor initializes the RepresentationFactory that assigns colors and symbols
     * to players, with a predefined set of symbols and a list of colors obtained from the Color enum.
     */
    public PlayerFactory(){
        List<Character> symbols = new ArrayList<>(List.of('●'));
        this.representationFactory = new RepresentationFactory(Color.getList(), symbols);
    }

    /**
     * Creates and returns a new instance of a human player.
     *
     * @param id              the unique identifier for the human player.
     * @param representation  the visual representation associated with the human player.
     * @return a new instance of HumanPlayer configured with the specified ID and representation.
     */
    private Player createHumanPlayer(int id, Representation representation) {
        return new HumanPlayer(id, representation);
    }

    /**
     * Creates and returns a new instance of an artificial player.
     *
     * @param id              the unique identifier for the artificial player.
     * @param representation  the visual or symbolic representation associated with the artificial player.
     * @param ai              the artificial intelligence system responsible for decision-making during the game.
     * @return a new instance of ArtificialPlayer configured with the specified ID, representation, and AI system.
     */
    private Player createArtificialPlayer(int id, Representation representation, ableToPlayAlone ai) {
        return new ArtificialPlayer(id, representation, ai);
    }

    /**
     * Creates and returns a list of players, consisting of the specified number of human players
     * and artificial players. Each player is assigned a unique representation with a visual
     * symbol and color. Artificial players are equipped with an AI system for decision-making.
     *
     * @param nbHumanPlayers       the number of human players to create.
     * @param nbArtificialPlayers  the number of artificial players to create.
     * @return a list of players, including both human and artificial players, each with a unique representation.
     */
    public List<Player> createPlayers(int nbHumanPlayers, int nbArtificialPlayers) {
        List<Player> players = new ArrayList<>();
        List<Representation> representations = representationFactory.getRepresentations(nbHumanPlayers + nbArtificialPlayers);
        ableToPlayAlone ai = new MakeAlignementAI();

        for (int i = 0; i < nbHumanPlayers; i++) {
            players.add(createHumanPlayer(i, representations.get(i)));
        }

        for (int i = nbHumanPlayers; i < nbArtificialPlayers + nbHumanPlayers; i++) {
            players.add(createArtificialPlayer(i, representations.get(i), ai));
        }

        return players;
    }
}