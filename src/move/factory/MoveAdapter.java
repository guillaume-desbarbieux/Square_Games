package move.factory;

import board.Board;
import game.Rule;
import move.Move;
import player.Player;
import player.ai.ArtificialIntelligence;
import java.util.List;

public interface MoveAdapter {
    Move getMoveFromHumanPlayer(Board board, Player player);
    Move getMoveFromAI(Board board, Rule rule, Player player, List<Player> players, ArtificialIntelligence ai);
}