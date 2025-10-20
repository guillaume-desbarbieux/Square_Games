package player.factory;

import game.connect4.Connect4;
import game.Game;
import game.tictactoe.TicTacToe;
import player.Representation;
import player.ArtificialPlayer;
import player.HumanPlayer;
import player.Player;
import player.ai.ArtificialIntelligence;
import player.ai.Connect4AI;
import player.ai.TicTacToeAI;

import java.util.ArrayList;
import java.util.List;

public class PlayerFactory {
    final Class<? extends Game> gameClass;
    final RepresentationFactory representationFactory;

    public PlayerFactory(Class<? extends Game> gameClass) {
        this.gameClass = gameClass;
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
        ArtificialIntelligence ai = createAI();

        for (int i = 0; i < nbHumanPlayers; i++) {
            players.add(createHumanPlayer(i, representations.get(i)));
        }

        for (int i = nbHumanPlayers; i < nbArtificialPlayers + nbHumanPlayers; i++) {
            players.add(createArtificialPlayer(i, representations.get(i), ai));
        }

        return players;
    }

    private ArtificialIntelligence createAI() {
        if (gameClass.equals(TicTacToe.class))
            return new TicTacToeAI();
        if (gameClass.equals(Connect4.class))
            return new Connect4AI();
        throw new IllegalStateException("Type de jeu non supporté : " + gameClass.getName());
    }
}