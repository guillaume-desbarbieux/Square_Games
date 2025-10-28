package controller.persistence;

import model.player.Player;
import java.util.List;

public interface Persistence {
    boolean savePlayer(Player player);
    Player getPlayer(int id);
    List<Player> getPlayers();

    boolean saveGame(Object gameMaster);
    Object getGame(int id);
    List<Object> getGames();
}