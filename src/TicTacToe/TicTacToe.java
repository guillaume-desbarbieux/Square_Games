package TicTacToe;

import Board.TicTacToeBoard;
import Interact.InteractionUser;
import Interact.View;
import Player.Player;
import Player.ArticicialPlayerTicTacToe;
import Player.HumanPlayer;
import Player.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TicTacToe {
    private int height;
    private int width;
    private int winningLength;
    private final View view;
    private final InteractionUser interact;
    private TicTacToeBoard board;
    private Player[] players;


    public TicTacToe() {
        this.view = new View();
        this.interact = new InteractionUser(view);
    }

    public void start() {
        view.displayTitle("TicTacToe");
        int choice = interact.getChoice("Bienvenue !", new String[]{"Partie Rapide", "Menu"});
        switch (choice) {
            case 1 -> initGame(3, 3, 3, 1, 1);
            default -> menu();
        }
        play();
    }

    private void menu() {
        view.displayTitle("Menu Principal");
        int height = interact.getInt("Hauteur plateau ?", 3, 20);
        int width = interact.getInt("Largeur plateau ?", 3, 20);
        int nbHumanPlayers = interact.getInt("nb Joueurs Humains", 0, 7);
        int nbArtificialPlayers = interact.getInt("nb Joueurs Artificiels", 0, 7 - nbHumanPlayers);
        int maxWinningLength = Math.max(width, height);
        if (maxWinningLength > 3)
            winningLength = interact.getInt("Longueur de la ligne pour gagner ?", 3, maxWinningLength);
        int choice = interact.getChoice("Affichage du plateau", new String[]{"Petit", "Grand"});
        view.setMaximize(choice == 2);

        initGame(height, width, winningLength, nbHumanPlayers, nbArtificialPlayers);
        play();
    }

    private void initGame(int height, int width, int winningLength, int nbHumanPlayers, int nbArtificialPlayers) {
        this.height = clamp(height, 3, 20);
        this.width = clamp(width, 3, 20);
        this.winningLength = clamp(winningLength, 3, Math.max(width, height));
        this.board = new TicTacToeBoard(height, width);
        nbHumanPlayers = clamp(nbHumanPlayers, 0, 7);
        nbArtificialPlayers = clamp(nbArtificialPlayers, (nbHumanPlayers == 0) ? 1 : 0, 7 - nbHumanPlayers);
        initPlayers(nbHumanPlayers, nbArtificialPlayers);
    }

    private int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    private void initPlayers(int nbHumanPlayers, int nbArtificialPlayers) {

        this.players = new Player[nbHumanPlayers + nbArtificialPlayers];
        List<Color> possibleColors = new ArrayList<>(Arrays.asList(Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE, Color.PURPLE, Color.CYAN, Color.WHITE));
        Collections.shuffle(possibleColors);

        for (int i = 0; i < nbHumanPlayers; i++) {
            this.players[i] = new HumanPlayer(i, '●', possibleColors.get(i % possibleColors.size()));
        }
        for (int i = nbHumanPlayers; i < nbHumanPlayers + nbArtificialPlayers; i++) {
            this.players[i] = new ArticicialPlayerTicTacToe(i, '●', possibleColors.get(i % possibleColors.size()));
        }
    }

    private void play() {
        view.display(String.format("""
                TicTacToe sur grille %dx%d pour %d joueurs.
                Alignez %d jetons pour gagner...
                %50s""", height, width, players.length, winningLength, "Bonne chance !"));

        Player currentPlayer = players[0];
        view.displayBoard(board);
        Player winner = null;

        while (!board.isFull() && winner == null) {

            view.display("=== Joueur " + currentPlayer.getRepresentation() + " ===");

            int[] move = currentPlayer.getMove(interact, view, board);
            while (!board.isPlayable(move)){
                view.displayError("Cette case est déjà occupée.");
                view.display(move[0] + "," + move[1]);
                move = currentPlayer.getMove(interact, view, board);
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

    private Player getNextPlayer(Player player) {
        int currentId = player.getId();
        int nextId = (currentId + 1) % players.length;
        return players[nextId];
    }


    private boolean isWinning(int row, int col) {
        int playerId = board.getCell(row, col).getOwner().getId();
        if (playerId == -1) return false;

        int[][] directions = {{0, 1}, // horizontally
                {1, 0}, // vertically
                {1, 1}, // diagonally ↘
                {1, -1} // diagonally ↙
        };

        for (int[] dir : directions) {
            int count = 1;
            count += countInDirection(row, col, dir[0], dir[1], playerId);
            count += countInDirection(row, col, -dir[0], -dir[1], playerId);

            if (count >= winningLength) return true;

        }
        return false;
    }

    private int countInDirection(int row, int col, int dRow, int dCol, int playerId) {
        int count = 0;
        int r = row + dRow;
        int c = col + dCol;
        while (r >= 0 && r < height
                && c >= 0 && c < width
                && !board.getCell(r, c).isEmpty()
                && board.getCell(r, c).getOwner().getId() == playerId) {
            count++;
            r += dRow;
            c += dCol;
        }
        return count;
    }
}