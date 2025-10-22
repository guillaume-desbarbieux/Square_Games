package controller.moveAdapter;

import model.Board;
import controller.Rule;
import model.Move;
import model.player.Player;
import controller.ai.ArtificialIntelligence;
import java.util.List;

public interface MoveAdapter {
    Move getMoveFromHumanPlayer(Board board, Player player);
    Move getMoveFromAI(Board board, Rule rule, Player player, List<Player> players, ArtificialIntelligence ai);
}