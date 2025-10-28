package model;

import controller.GameMaster;
import controller.GameState;
import model.player.Player;
import view.dictionary.GameTitle;
import java.util.List;

public interface Persistence {
    boolean createPlayer(Player player);
    Player getPlayer(int id);
    List<Player> getPlayers();
    boolean updatePlayer(int id, Player player);

    boolean createGame(GameTitle title, GameState state, int player1, int player2, List<MoveStrategy> moveHistory);
    GameMaster getGame(int id);
    List<GameMaster> getGames();
    boolean updateGame(int id, GameState state, List<MoveStrategy> moveHistory);
}