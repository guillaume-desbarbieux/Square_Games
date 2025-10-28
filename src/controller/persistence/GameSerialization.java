package controller.persistence;

import model.player.Player;

import java.io.*;
import java.util.List;

public class GameSerialization implements Persistence, Serializable {
    private static final String PLAYERS_FILE = "saves/players.ser";
    private static final String GAMES_FILE = "saves/games.ser";
    public GameSerialization() {
    }


    @Override
    public boolean savePlayer(Player player) {
        try {
            FileOutputStream fileOut = new FileOutputStream(PLAYERS_FILE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(player);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Player getPlayer(int id) {
        return null;
    }

    @Override
    public List<Player> getPlayers() {
        return List.of();
    }

    @Override
    public boolean saveGame(Object gameMaster) {
        try {
            FileOutputStream fileOut = new FileOutputStream(GAMES_FILE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(gameMaster);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Object getGame(int id) {
        return null;
    }

    @Override
    public List<Object> getGames() {
        return List.of();
    }
}
