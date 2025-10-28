package controller.persistence;

import model.GameSave;
import model.player.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GameSerialization implements Persistence, Serializable {
    private static final String PLAYERS_FILE = "saves/players.ser";
    private static final String SAVES_FILE = "saves/saves.ser";

    public GameSerialization() {
        new File("saves");
    }

    @Override
    public boolean savePlayer(Player player) {
        List<Player> players = getPlayers();
        players.removeIf(p -> p.getId() == player.getId());
        players.add(player);
        return savePlayers(players);
    }

    private boolean savePlayers(List<Player> players) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(PLAYERS_FILE))) {
            out.writeObject(players);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Player> getPlayers() {
        File file = new File(PLAYERS_FILE);
        if (!file.exists()) return new ArrayList<>();

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Player>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean saveGame(GameSave save) {
        List<GameSave> saves = getSaves();
        boolean updated = false;
        for (int i = 0; i < saves.size(); i++)
            if (saves.get(i).id().equals(save.id())) {
                saves.set(i, save);
                updated = true;
                break;
            }
        if (!updated)
            saves.add(save);

        return saveGames(saves);
    }

    private boolean saveGames(List<GameSave> saves) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SAVES_FILE))) {
            out.writeObject(saves);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<GameSave> getSaves() {
        File file = new File(SAVES_FILE);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (List<GameSave>) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}