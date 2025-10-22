package model.ai;

import model.Board;
import model.rule.Rule;
import model.Move;
import model.player.Player;
import java.util.List;

public interface ArtificialIntelligence {
    Move getNextMove(Board board, Rule rule, Player player, List<Player> players);
}
