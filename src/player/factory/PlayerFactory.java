package player.factory;

import game.connect4.Connect4;
import game.Game;
import game.tictactoe.TicTacToe;
import ui.InteractionUser;
import player.ArtificialPlayer;
import player.HumanPlayer;
import player.Player;
import player.ai.ArtificialIntelligence;
import player.ai.Connect4AI;
import player.ai.TicTacToeAI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerFactory {
    InteractionUser interact;
    Class<? extends Game> gameClass;
    List<Color> possibleColors = new ArrayList<>(Arrays.asList(Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE, Color.PURPLE, Color.CYAN, Color.WHITE));

    public PlayerFactory(InteractionUser interact, Class<? extends Game> gameClass) {
        this.interact = interact;
        this.gameClass = gameClass;
    }

    public Player createHumanPlayer(int id, char symbol, Color color, InteractionUser interact) {
        return new HumanPlayer(id, symbol, color, interact);
    }

    public Player createArtificialPlayer(int id, char symbol, Color color, ArtificialIntelligence ai) {
        return new ArtificialPlayer(id, symbol, color, ai);
    }

    public List<Player> createPlayers(int nbHumanPlayers, int nbArtificialPlayers) {
        List<Player> players = new ArrayList<>();

        for (int i = 0; i < nbHumanPlayers; i++) {
            Color color = possibleColors.get(i % possibleColors.size());
            players.add(createHumanPlayer(i, '●', color, interact));
        }

        for (int i = nbHumanPlayers; i < nbArtificialPlayers + nbHumanPlayers; i++) {
            Color color = possibleColors.get(i % possibleColors.size());
            ArtificialIntelligence ai = createAI();
            players.add(createArtificialPlayer(i, '●', color, ai));
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