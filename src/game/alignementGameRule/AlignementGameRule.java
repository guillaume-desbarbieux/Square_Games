package game.alignementGameRule;

import board.Board;
import game.Rule;
import move.Move;
import move.factory.MoveAdapter;
import player.Player;
import player.factory.Color;
import player.factory.RepresentationFactory;

import java.util.ArrayList;
import java.util.List;

public class AlignementGameRule extends Rule {
    protected final int winningLength;

    public AlignementGameRule(String name, int height, int width, int defaultNbPlayers, int winningLength, MoveAdapter adapter) {
        super(name, height, width, defaultNbPlayers, adapter);
        this.winningLength = winningLength;
    }

    @Override
    public Board getInitialBoard() {
        return new Board(height, width, new RepresentationFactory(List.of(Color.WHITE), List.of('·')));
    }

    @Override
    public void playMove(Board board, Move move) {
        board.getCell(move.getRow(), move.getCol()).setOwner(move.getPlayer());
    }

    @Override
    public String toString() {
        return String.format("""
                %s sur grille %dx%d pour %d joueurs.
                Alignez %d jetons pour gagner...
                %50s""", name, height, width, defaultNbPlayers, winningLength, "Bonne chance !");
    }

    @Override
    public boolean isGameOver(Board board, Move lastMove) {
        if (isMoveWinning(board, lastMove))
            return true;

        for (int row = 0; row < height; row++)
            for (int col = 0; col < width; col++)
                if (board.getCell(row, col).isEmpty())
                    return false;

        return true;
    }

    @Override
    public List<Move> getValidMoves(Board board, Player player) {
        List<Move> listValidMoves = new ArrayList<>();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Move move = new Move(player, row, col);
                if (isMoveValid(board, move))
                    listValidMoves.add(move);
            }
        }
        return listValidMoves;
    }

    @Override
    public Player getNextPlayer(Board board, Player currentPlayer, List<Player> players) {
        return players.get((currentPlayer.getId() + 1) % players.size());
    }

    @Override
    public Player getFirstPlayer(List<Player> players) {
        return players.getFirst();
    }

    @Override
    public boolean isMoveValid(Board board, Move move) {
        int row = move.getRow();
        int col = move.getCol();
        return row >= 0 && row < board.height()
                && col >= 0 && col < board.width()
                && board.getCell(row, col).isEmpty();
    }

    @Override
    public boolean isMoveWinning(Board board, Move move) {
        return makeAlignment(board, move);
    }


    protected boolean makeAlignment(Board board, Move move) {
        int row = move.getRow();
        int col = move.getCol();
        int playerId = move.getPlayer().getId();

        int[][] directions = {
                {0, 1}, // horizontally
                {1, 0}, // vertically
                {1, 1}, // diagonally ↘
                {1, -1} // diagonally ↙
        };

        for (int[] dir : directions) {
            int count = 1;
            count += countInDirection(board, row, col, dir[0], dir[1], playerId);
            count += countInDirection(board, row, col, -dir[0], -dir[1], playerId);
            if (count >= winningLength) return true;
        }
        return false;
    }

    protected int countInDirection(Board board, int row, int col, int dRow, int dCol, int playerId) {
        int count = 0;
        int r = row + dRow;
        int c = col + dCol;
        while (r >= 0 && r < board.height()
                && c >= 0 && c < board.width()
                && !board.getCell(r, c).isEmpty()
                && board.getCell(r, c).getOwner().getId() == playerId) {
            count++;
            r += dRow;
            c += dCol;
        }
        return count;
    }
}

/*

Heuristique d'évaluation :
si profondeur max atteinte, moduler score selon plus grand alignement trouvés.



alpha beta prunning
couper les branches qui ne seront jamais mieux que celles déjà explorées.
 */