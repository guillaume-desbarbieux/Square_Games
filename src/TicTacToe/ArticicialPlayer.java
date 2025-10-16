package TicTacToe;
import java.util.List;
import java.util.Random;

public class ArticicialPlayer extends Player{
    public ArticicialPlayer(int id, char symbol, Color color) {
        super(id, symbol, color);
    }

    @Override
    public int[] getMove(GameScanner scanner, Board board) {
        Random random = new Random();
        List<int[]> listFreeCells = board.getFreeCells();
        return listFreeCells.get(random.nextInt(0, listFreeCells.size()));
    }
}
