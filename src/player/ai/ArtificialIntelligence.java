package player.ai;

import board.Board;
import game.Rule;
import move.Move;
import player.Player;
import java.util.List;

public interface ArtificialIntelligence {
    Move getNextMove(Board board, Rule rule, Player player, List<Player> players);
}
