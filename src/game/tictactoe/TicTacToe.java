package game.tictactoe;

import board.TicTacToeBoard;
import game.Game;
import move.Move;
import move.TicTacToeMove;
import move.factory.TicTacToeInputAdapter;

public class TicTacToe extends Game {
    private final int winningLength = 3;

    public TicTacToe() {
        super("TicTacToe", 3, 3, new TicTacToeInputAdapter());
    }

    protected void displayRules() {
        view.display(String.format("""
                %s sur grille %dx%d pour %d joueurs.
                Alignez %d jetons pour gagner...
                %50s""", this.name, board.height(), board.width(), players.size(), winningLength, "Bonne chance !"));
    }

    @Override
    protected void initBoard(int height, int width) {
        this.board = new TicTacToeBoard(height, width);
    }

    @Override
    protected boolean isWinning(Move move) {
        if (move instanceof TicTacToeMove ticTacToeMove)
            return makeAlignment(ticTacToeMove.getRow(), ticTacToeMove.getCol(), winningLength);
        else
            return false;
    }
}