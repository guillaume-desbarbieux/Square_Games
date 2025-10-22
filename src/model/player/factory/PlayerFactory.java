package model.player.factory;

import model.player.Representation;
import model.player.ArtificialPlayer;
import model.player.HumanPlayer;
import model.player.Player;
import controller.ai.ArtificialIntelligence;
import controller.ai.MakeAlignementAI;

import java.util.ArrayList;
import java.util.List;

public class PlayerFactory {
    final RepresentationFactory representationFactory;

    public PlayerFactory(){
        List<Character> symbols = new ArrayList<>(List.of('●'));
        this.representationFactory = new RepresentationFactory(Color.getList(), symbols);
    }

    public Player createHumanPlayer(int id, Representation representation) {
        return new HumanPlayer(id, representation);
    }

    public Player createArtificialPlayer(int id, Representation representation, ArtificialIntelligence ai) {
        return new ArtificialPlayer(id, representation, ai);
    }

    public List<Player> createPlayers(int nbHumanPlayers, int nbArtificialPlayers) {
        List<Player> players = new ArrayList<>();
        List<Representation> representations = representationFactory.getRepresentations(nbHumanPlayers + nbArtificialPlayers);
        ArtificialIntelligence ai = new MakeAlignementAI();

        for (int i = 0; i < nbHumanPlayers; i++) {
            players.add(createHumanPlayer(i, representations.get(i)));
        }

        for (int i = nbHumanPlayers; i < nbArtificialPlayers + nbHumanPlayers; i++) {
            players.add(createArtificialPlayer(i, representations.get(i), ai));
        }

        return players;
    }
}