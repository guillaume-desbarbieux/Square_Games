package TicTacToe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TicTacToe {
    private int height;
    private int width;
    private int winningLength;
    private View view;
    private InteractionUser interact;
    private Board board;
    private Player[] players;


    public TicTacToe() {
        this.view = new View();
        this.interact = new InteractionUser(view);
        menu();
    }

    private void menu() {
        view.displayTitle("TicTacToe     -     Menu Principal");
        view.displayMessage("Hauteur plateau ? [3..20]");
        int height = interact.getIntFromUser();
        view.displayMessage("Largeur plateau ? [3..20]");
        int width = interact.getIntFromUser();
        view.displayMessage("nb Joueurs humains ? [0..7]");
        int nbHumanPlayers = interact.getIntFromUser();
        view.displayMessage("nb Joueurs artificiels ? [0..7]");
        int nbArtificialPlayers = interact.getIntFromUser();
        view.displayMessage("longueur gagnante ? [3..20]");
        int winningLength = interact.getIntFromUser();

        initGame(height, width, winningLength, nbHumanPlayers, nbArtificialPlayers);
    }

    private void initGame(int height, int width, int winningLength, int nbHumanPlayers, int nbArtificialPlayers) {
        this.height = clamp(height, 3, 20);
        this.width = clamp(width, 3, 20);
        this.winningLength = clamp(winningLength, 3, Math.max(width, height));
        this.board = new Board(height, width);
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
            this.players[i] = new ArticicialPlayer(i, '●', possibleColors.get(i % possibleColors.size()));
        }
    }

    public void play() {
        view.displayTitle(String.format("""
                TicTacToe sur grille %dx%d pour %d joueurs.
                Alignez %d jetons pour gagner...
                %50s""", height, width, players.length, winningLength, "Bonne chance !"));

        Player currentPlayer = players[0];
        view.displayBoard(board);
        Player winner = null;

        while (!board.isFull() && winner == null) {

            view.displayMessage("=== Joueur " + currentPlayer.getId() + currentPlayer.getRepresentation() + " ===");

            int[] move = currentPlayer.getMove(interact, view, board);
            board.playMove(move[0], move[1], currentPlayer);
            if (isWinning(move[0], move[1])) {
                winner = currentPlayer;
            } else {
                currentPlayer = getNextPlayer(currentPlayer);
            }
            view.displayBoard(board);
        }

        if (winner == null) {
            view.displayMessage("Match Nul");
        } else {
            view.displayMessage("Victoire du joueur " + winner.getId());
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