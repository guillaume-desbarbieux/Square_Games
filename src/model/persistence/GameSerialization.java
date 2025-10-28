package model.persistence;

import controller.GameMaster;
import controller.GameState;
import model.MoveStrategy;
import model.Persistence;
import model.player.Player;
import view.dictionary.GameTitle;

import java.io.Serializable;
import java.util.List;

public class GameSerialization implements Persistence, Serializable {

    @Override
    public boolean createPlayer(Player player) {
        return false;
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
    public boolean updatePlayer(int id, Player player) {
        return false;
    }

    @Override
    public boolean createGame(GameTitle title, GameState state, int player1, int player2, List<MoveStrategy> moveHistory) {
        return false;
    }

    @Override
    public GameMaster getGame(int id) {
        return null;
    }

    @Override
    public List<GameMaster> getGames() {
        return List.of();
    }

    @Override
    public boolean updateGame(int id, GameState state, List<MoveStrategy> moveHistory) {
        return false;
    }
}
