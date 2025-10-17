package Player;

import Board.Board;
import java.util.List;
import java.util.Random;

public class ConnectFourAI implements ArtificialIntelligence {
    @Override
    public int[] getNextMove(Board board) {
        Random random = new Random();
        List<int[]> listPlayableCells = board.getPlayableCells();
        int[] randomMove = listPlayableCells.get(random.nextInt(0, listPlayableCells.size()));
        return randomMove;
    }
}
