package game.tictactoe;

import board.TicTacToeBoard;
import game.Game;
import move.Move;
import move.TicTacToeMove;
import move.factory.MoveInputAdapter;
import move.factory.TicTacToeInputAdapter;
import player.Player;

public class TicTacToe extends Game {
    private int winningLength = 3;
    protected final MoveInputAdapter adapter = new TicTacToeInputAdapter(interact);

    public TicTacToe() {
        super();
    }

    @Override
    public void start() {
        view.displayTitle("TicTacToe");
        int choice = interact.getChoice("Bienvenue !", new String[]{"Partie Rapide", "Menu"});
        switch (choice) {
            case 1 -> initGame(3, 3, 1, 1);
            default -> menu();
        }
        play();
    }

    @Override
    protected void menu() {
        view.displayTitle("Menu Principal");
        int height = interact.getInt("Hauteur plateau ?", 3, 20);
        int width = interact.getInt("Largeur plateau ?", 3, 20);
        int nbHumanPlayers = interact.getInt("nb Joueurs Humains", 0, 7);
        int nbArtificialPlayers = interact.getInt("nb Joueurs Artificiels", 0, 7 - nbHumanPlayers);
        int maxWinningLength = Math.max(width, height);
        if (maxWinningLength > 3)
            this.winningLength = interact.getInt("Longueur de la ligne pour gagner ?", 3, maxWinningLength);
        int choice = interact.getChoice("Affichage du plateau", new String[]{"Petit", "Grand"});
        view.setMaximize(choice == 2);

        initGame(height, width, nbHumanPlayers, nbArtificialPlayers);
        play();
    }

    protected void initGame(int height, int width, int nbHumanPlayers, int nbArtificialPlayers) {
        height = clamp(height, 3, 20);
        width = clamp(width, 3, 20);
        this.board = new TicTacToeBoard(height, width);
        this.winningLength = clamp(winningLength, 3, Math.max(width, height));
        nbHumanPlayers = clamp(nbHumanPlayers, 0, 7);
        nbArtificialPlayers = clamp(nbArtificialPlayers, (nbHumanPlayers == 0) ? 1 : 0, 7 - nbHumanPlayers);
        this.players = playerFactory.createPlayers(nbHumanPlayers, nbArtificialPlayers);
    }

    @Override
    protected void play() {
        view.display(String.format("""
                TicTacToe sur grille %dx%d pour %d joueurs.
                Alignez %d jetons pour gagner...
                %50s""", board.height(), board.width(), players.size(), winningLength, "Bonne chance !"));

        Player currentPlayer = players.getFirst();
        view.displayBoard(board);
        Player winner = null;

        while (!board.isFull() && winner == null) {

            view.display("=== Joueur " + currentPlayer.getRepresentation() + " ===");

            Move move = currentPlayer.getNextMove(board, adapter);
            while (!board.isPlayable(move)) {
                view.displayError("Cette case est déjà occupée.");
                move = currentPlayer.getNextMove(board, adapter);
            }
            board.playMove(move, currentPlayer);
            if (isWinning(move)) {
                winner = currentPlayer;
            } else {
                currentPlayer = getNextPlayer(currentPlayer);
            }
            view.displayBoard(board);
        }

        if (winner == null) {
            view.display("Match Nul");
        } else {
            view.display("Victoire du joueur " + winner.getRepresentation());
        }
    }

    @Override
    protected boolean isWinning(Move move) {
        if (move instanceof TicTacToeMove ticTacToeMove)
            return makeAlignment(ticTacToeMove.getRow(), ticTacToeMove.getCol(), winningLength);
        else
            return false;
    }
}