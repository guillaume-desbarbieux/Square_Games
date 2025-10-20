package game.connect4;

import board.Connect4Board;
import move.Connect4Move;
import move.Move;
import move.factory.Connect4InputAdapter;
import game.Game;

public class Connect4 extends Game {
    private final int winningLength = 4;

    public Connect4() {
        super("Puissance 4", 6, 7, new Connect4InputAdapter());
    }

    protected void displayRules() {
        view.display(String.format("""
                %s sur grille %dx%d pour %d joueurs.
                Alignez %d jetons pour gagner...
                %50s""", this.name, board.height(), board.width(), players.size(), winningLength, "Bonne chance !"));
    }

    @Override
    protected void initBoard(int height, int width) {
        this.board = new Connect4Board(height, width);
    }

    @Override
    protected boolean isWinning(Move move) {
        if (move instanceof Connect4Move c4Move) {
            int col = c4Move.getCol();
            Connect4Board c4Board = (Connect4Board) board;
            int row = c4Board.getRow(col) + 1;
            return makeAlignment(row, col, winningLength);
        } else
            return false;
    }
}