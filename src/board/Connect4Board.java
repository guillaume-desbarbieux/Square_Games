package board;

import move.Connect4Move;
import move.Move;
import player.Player;

import java.util.ArrayList;
import java.util.List;

public class Connect4Board extends Board {

    public Connect4Board(int height, int width) {
        super(height, width);
    }

    @java.lang.Override
    public void playMove(Move move, Player player) {
        if (!(move instanceof Connect4Move connect4Move))
            throw new IllegalArgumentException("Le move doit être de type Connect4Move");

        if (isPlayable(connect4Move)) {
            int col = connect4Move.getCol();
            int row = getRow(col);
            getCell(row, col).setOwner(player);
        }
    }

    @Override
    public List<Move> getPlayableMoves() {
        List<Move> moves = new ArrayList<>();
        for (int col = 0; col < this.width(); col++) {
            Move move = new Connect4Move(0, col);
            if (isPlayable(move))
                moves.add(move);
        }
        return moves;
    }

    @java.lang.Override
    public boolean isPlayable(Move move) {
        if (!(move instanceof Connect4Move connect4Move))
            return false;

        int col = connect4Move.getCol();

        if (col < 0 || col >= this.width())
            return false;

        return getCell(0, col).isEmpty();
    }

    public int getRow(int col) {
        int row = -1;
        while (row + 1 < this.height() && getCell(row + 1, col).isEmpty()) {
            row++;
        }
        return row;
    }
}