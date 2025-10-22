package model.player.ai;

import model.Board;
import model.player.Player;
import model.rule.Rule;
import model.Move;

import java.util.List;

public interface ArtificialIntelligence {
    Move getNextMove(Board board, Rule rule, Player player, List<Player> players);
}
