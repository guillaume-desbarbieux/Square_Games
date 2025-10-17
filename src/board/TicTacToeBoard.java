package board;

import move.Move;
import move.TicTacToeMove;
import player.Player;

import java.util.ArrayList;
import java.util.List;

public class TicTacToeBoard extends Board {

    public TicTacToeBoard(int height, int width) {
        super(height, width);
    }

    public void playMove(Move move, Player player) {
        if (!(move instanceof TicTacToeMove ticTacToeMove))
            throw new IllegalArgumentException("Le move doit être de type TicTacToeMove");

        int row = ticTacToeMove.getRow();
        int col = ticTacToeMove.getCol();

        if (row < 0 || row >= this.height() || col < 0 || col >= this.width())
            throw new IllegalArgumentException("La case n'existe pas");

        if (isPlayable(ticTacToeMove))
            getCell(row, col).setOwner(player);
    }

    @Override
    public List<Move> getPlayableMoves() {
        List<Move> moves = new ArrayList<>();
        for (int col = 0; col < this.width(); col++) {
            for (int row = 0; row < this.height(); row++) {
                Move move = new TicTacToeMove(0, row, col);
                if (isPlayable(move))
                    moves.add(move);
            }
        }
        return moves;
    }

    @Override
    public boolean isPlayable(Move move) {
        if (!(move instanceof TicTacToeMove ticTacToeMove))
            return false;

        int col = ticTacToeMove.getCol();
        int row = ticTacToeMove.getRow();

        if (row < 0 || row >= this.height() || col < 0 || col >= this.width())
            return false;

        return getCell(row, col).isEmpty();
    }
}