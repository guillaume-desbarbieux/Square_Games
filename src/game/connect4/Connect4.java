package game.connect4;

import board.Connect4Board;
import move.Connect4Move;
import move.Move;
import move.factory.Connect4InputAdapter;
import move.factory.MoveInputAdapter;
import player.Player;
import game.Game;

public class Connect4 extends Game {
    private int winningLength = 4;
    protected final MoveInputAdapter adapter = new Connect4InputAdapter(interact);


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

            Move move = currentPlayer.getNextMove(board,adapter );
            while (!board.isPlayable(move)) {
                view.displayError("Cette case n'est pas jouable.");
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
        if (move instanceof Connect4Move connect4Move) {
            int col = connect4Move.getCol();
            Connect4Board C4Board = (Connect4Board) board;
            int row = C4Board.getRow(col) + 1;
            return makeAlignment(row,col, winningLength);
        } else
            return false;
    }
}