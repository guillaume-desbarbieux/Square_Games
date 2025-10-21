package move.factory;

import board.Board;
import move.Move;
import player.ai.ArtificialIntelligence;

public interface MoveInputAdapter {
    Move getMoveFromHumanPlayer(Board board);
    Move getMoveFromAI(Board board, ArtificialIntelligence ai);
}