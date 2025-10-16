package Power4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Power4 {
    private final int height;
    private final int width;
    private final int winningLength;
    private final GameScanner scanner;
    private final Board board;
    private Player[] players;


    public Power4() {
        this(6, 7, 4, 2);
    }

    public Power4(int height, int width, int winningLength, int nbPlayers) {
        this.height = clamp(height, 2, 20);
        this.width = clamp(width, 2, 20);
        this.winningLength = clamp(winningLength, 2, Math.max(width, height));
        this.scanner = new GameScanner();
        this.board = new Board(height, width);
        initPlayers(clamp(nbPlayers, 1, 7));
    }

    private int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    private void initPlayers(int nb_players) {

        this.players = new Player[nb_players];
        List<Color> possibleColors = new ArrayList<>(Arrays.asList(Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE, Color.PURPLE, Color.CYAN, Color.WHITE));
        Collections.shuffle(possibleColors);

        for (int i = 0; i < nb_players; i++) {
            this.players[i] = new Player(i, '●', possibleColors.get(i % possibleColors.size()));
        }
    }


    public void display() {
        int cellWidth = 3;

        String separator = "-".repeat((cellWidth + 1) * width + 1);

        System.out.print(" ");
        for (int j = 0; j < width; j++) {
            System.out.printf("%" + (cellWidth-1) + "d  ", j + 1);
        }
        System.out.println();

        System.out.println(separator);

        for (int i = height - 1; i >= 0; i--) {
            System.out.print("|");
            for (int j = 0; j < width; j++) {
                System.out.printf("%" + cellWidth + "s", board.getCell(i, j).getRepresentation());
                System.out.print("|");
            }
            System.out.println();
        }
        System.out.println(separator);
    }

    public void play() {
        int widthMessage = 50;
        System.out.println();
        System.out.println("-".repeat(widthMessage));
        System.out.printf("""
                Puissance 4 sur grille %dx%d pour %d joueurs.
                Alignez %d jetons pour gagner...
                %""" + widthMessage + "s%n", height, width, players.length, winningLength, "Bonne chance !");
        System.out.println("-".repeat(widthMessage));
        System.out.println();

        Player currentPlayer = players[0];
        int freeCells = height * width;
        display();
        Player winner = null;

        while (freeCells > 0 && winner == null) {


            System.out.println("=== Joueur " + currentPlayer.getId() + currentPlayer.getRepresentation() + " ===");

            int col = currentPlayer.getMove(this.scanner, this.board);
            int row = board.playCol(col);

            if (row == -1) {
                System.out.println("Cette colonne est pleine.");
            } else {
                Cell playedCell = board.getCell(row, col);
                playedCell.setOwner(currentPlayer);
                freeCells--;
                if (isWinning(row, col)) {
                    winner = currentPlayer;
                } else {
                    currentPlayer = getNextPlayer(currentPlayer);
                }
            }
            this.display();
        }
        if (winner == null) System.out.println("Match Nul");
        else System.out.println("Victoire du joueur " + winner.getId());
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