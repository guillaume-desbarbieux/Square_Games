package game.alignementGame;

import board.Board;
import game.Game;
import move.ColMove;
import move.Move;
import move.RowColMove;
import move.factory.MoveInputAdapter;
import player.factory.Color;
import player.factory.RepresentationFactory;

import java.util.List;

public class AlignementGame extends Game {
    private final int winningLength;

    public AlignementGame(String name, int height, int width, int winningLength, MoveInputAdapter moveInputAdapter) {
        super(name, height, width, moveInputAdapter);
        this.winningLength = winningLength;
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
        if (move instanceof ColMove colMove) {
            int col = colMove.getCol();
            return board.getCell(0, col).isEmpty();
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
        if (move instanceof ColMove colMove) {
            int col = colMove.getCol();
            int row = getRowPlaying(col);
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
        int row, col;
        if (move instanceof RowColMove rowColMoveMove) {
            row = rowColMoveMove.getRow();
            col = rowColMoveMove.getCol();
            return makeAlignment(row, col, winningLength);
        }
        if (move instanceof ColMove colMove) {
            col = colMove.getCol();
            row = getRowPlaying(col) + 1;
            return makeAlignment(row, col, winningLength);
        }
        return false;
    }
    protected int getRowPlaying(int col) {
        int row = -1;
        while (row + 1 < board.height() && board.getCell(row + 1, col).isEmpty()) {
            row++;
        }
        return row;
    }
}