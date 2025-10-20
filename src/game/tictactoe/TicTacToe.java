package game.tictactoe;

import board.Board;
import game.Game;
import move.Move;
import move.RowColMove;
import move.factory.RowColInputAdapter;
import player.factory.Color;
import player.factory.RepresentationFactory;

import java.util.List;

public class TicTacToe extends Game {
    private final int winningLength = 3;

    public TicTacToe() {
        super("TicTacToe", 3, 3, new RowColInputAdapter());
    }

    protected void displayRules() {
        view.display(String.format("""
                %s sur grille %dx%d pour %d joueurs.
                Alignez %d jetons pour gagner...
                %50s""", this.name, board.height(), board.width(), players.size(), winningLength, "Bonne chance !"));
    }

    @Override
    protected boolean isPlayable(Move move) {
        if (move instanceof RowColMove rowColMove) {
            int row = rowColMove.getRow();
            int col = rowColMove.getCol();
            return board.getCell(row, col).isEmpty();
        }
        return false;
    }

    @Override
    protected boolean playMove(Move move) {
        if (move instanceof RowColMove rowColMove) {
            int row = rowColMove.getRow();
            int col = rowColMove.getCol();
            board.getCell(row, col).setOwner(currentPlayer);
            return false;
        }
        return true;
    }

    @Override
    protected void getNextPlayer() {
        int currentId = currentPlayer.getId();
        int nextId = (currentId + 1) % players.size();
        this.currentPlayer = players.get(nextId);
    }

    @Override
    protected void initBoard(int height, int width) {
        this.board = new Board(height, width, new RepresentationFactory(List.of(Color.WHITE), List.of('·')));
    }

    @Override
    protected boolean isWinning(Move move) {
        if (move instanceof RowColMove ticTacToeMove)
            return makeAlignment(ticTacToeMove.getRow(), ticTacToeMove.getCol(), winningLength);
        else
            return false;
    }
}