package controller.persistence;

import model.GameSave;
import model.player.Player;
import java.util.List;

public interface Persistence {
    boolean savePlayer(Player player);
    List<Player> getPlayers();

    boolean saveGame(GameSave save);
    List<GameSave> getSaves();
}