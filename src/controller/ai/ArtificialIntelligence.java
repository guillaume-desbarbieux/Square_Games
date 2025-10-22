package controller.ai;

import model.Board;
import controller.Rule;
import model.Move;
import model.player.Player;
import java.util.List;

public interface ArtificialIntelligence {
    Move getNextMove(Board board, Rule rule, Player player, List<Player> players);
}
