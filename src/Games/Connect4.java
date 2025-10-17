package Games;

import Board.Connect4Board;
import Player.Player;

public class Connect4 extends SquareGame {
    private int winningLength = 4;

    public Connect4() {
        super();
    }

    @Override
    public void start() {
        view.displayTitle("Puissance 4");
        int choice = interact.getChoice("Bienvenue !", new String[]{"Partie Rapide", "Menu"});
        switch (choice) {
            case 1 -> initGame(6, 7, 1, 1);
            default -> menu();
        }
        play();
    }

    @Override
    protected void menu() {
        view.displayTitle("Menu Principal");
        int nbHumanPlayers = interact.getInt("nb Joueurs Humains", 0, 2);
        int nbArtificialPlayers = 2 - nbHumanPlayers;
        int choice = interact.getChoice("Affichage du plateau", new String[]{"Petit", "Grand"});
        view.setMaximize(choice == 2);
        initGame(6, 7, nbHumanPlayers, nbArtificialPlayers);
        play();
    }

    @Override
    protected void initGame(int height, int width, int nbHumanPlayers, int nbArtificialPlayers) {
        this.board = new Connect4Board(height, width);
        this.winningLength = clamp(winningLength, 3, Math.max(width, height));
        nbHumanPlayers = clamp(nbHumanPlayers, 0, 2);
        nbArtificialPlayers = 2 - nbHumanPlayers;
        this.players = playerFactory.createPlayers(nbHumanPlayers, nbArtificialPlayers);

    }

    @Override
    protected void play() {
        view.display(String.format("""
                Puissance sur grille %dx%d pour %d joueurs.
                Alignez %d jetons pour gagner...
                %50s""", board.height(), board.width(), players.size(), winningLength, "Bonne chance !"));

        Player currentPlayer = players.getFirst();
        view.displayBoard(board);
        Player winner = null;

        while (!board.isFull() && winner == null) {

            view.display("=== Joueur " + currentPlayer.getRepresentation() + " ===");

            int[] move = currentPlayer.getNextMove(board);
            while (!board.isPlayable(move)) {
                view.displayError("Cette case n'est pas jouable.");
                view.display(move[0] + "," + move[1]);
                move = currentPlayer.getNextMove(board);
            }
            board.playMove(move[0], move[1], currentPlayer);
            if (isWinning(move[0], move[1])) {
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
    protected boolean isWinning(int row, int col) {
        return makeAlignment(row, col, winningLength);
    }
}