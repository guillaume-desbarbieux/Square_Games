package player.ai;

import board.Board;
import move.Move;

import java.util.List;
import java.util.Random;

public class TicTacToeAI implements ArtificialIntelligence {

    @Override
    public Move getNextMove(Board board) {
        Random random = new Random();
        List<Move> listPlayableCells = board.getPlayableMoves();
        Move randomMove = listPlayableCells.get(random.nextInt(0, listPlayableCells.size()));
        return randomMove;
    }
}