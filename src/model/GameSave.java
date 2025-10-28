package model;

import controller.GameState;
import model.player.Player;
import view.dictionary.GameTitle;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public record GameSave(String id,
                       GameTitle title,
                       GameState gameState,
                       List<Player> players,
                       int currentPlayerId,
                       List<MoveStrategy> moveHistory,
                       MoveStrategy currentMove)
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public String toString(){
        return (title + " - " + gameState);
    }
}