import java.awt.*;
import java.util.*;
import java.util.List;

public class TicTacToe {
    private final int height;
    private final int width;
    private final int winningLength;
    private final Scanner scanner;
    private Cell[][] cells;
    private Player[] players;


    public TicTacToe() {
        this(3, 3, 3, 2);
    }

    public TicTacToe(int height, int width, int winningLength, int nbPlayers) {
        this.height = clamp(height, 2, 20);
        this.width = clamp(width, 2, 20);
        this.winningLength = clamp(winningLength, 2, Math.max(width, height));
        this.scanner = new Scanner(System.in);
        nbPlayers = clamp(nbPlayers, 1, 7);
        initCells();
        initPlayers(nbPlayers);
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

    private void initCells() {
        this.cells = new Cell[this.height][this.width];
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    public void display() {
        int indexWidth = 2;
        int cellWidth = 3;

        String separator = " ".repeat(indexWidth + 1) + "-".repeat((cellWidth + 1) * width + 1);

        System.out.print(" ".repeat(indexWidth + 1));
        for (int j = 0; j < width; j++) {
            System.out.printf("%" + cellWidth + "d ", j + 1);
        }
        System.out.println();

        System.out.println(separator);

        for (int i = 0; i < height; i++) {
            System.out.printf("%" + indexWidth + "d |", i + 1);
            for (int j = 0; j < width; j++) {
                System.out.printf("%" + cellWidth + "s", cells[i][j].getRepresentation());
                System.out.print("|");
            }
            System.out.println();
            System.out.println(separator);
        }
        System.out.println();
    }

    public int[] getMoveFromPlayer() {
        int x = 0;
        int y = 0;

        while (x < 1 || x > height) {
            System.out.println("ligne ? [1.." + height + "]");
            x = this.getIntFromUser();
        }

        while (y < 1 || y > width) {
            System.out.println("colonne ? [1.." + width + "]");
            y = this.getIntFromUser();
        }

        return new int[]{x - 1, y - 1};
    }

    private int getIntFromUser() {
        while (true) {
            try {
                return this.scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Ceci n'est pas un entier.");
                this.scanner.nextLine();
            }
        }
    }

    public void play() {
        Player currentPlayer = players[0];
        int freeCells = height * width;
        display();
        Player winner = null;

        while (freeCells > 0 && winner == null) {


            System.out.println("=== Joueur " + currentPlayer.getId() + currentPlayer.getRepresentation() + " ===");

            int[] move = getMoveFromPlayer();
            Cell playedCell = cells[move[0]][move[1]];

            if (playedCell.isEmpty()) {
                playedCell.setOwner(currentPlayer);

                freeCells--;
                if (isWinning(move[0], move[1])) {
                    winner = currentPlayer;
                } else {
                    currentPlayer = getNextPlayer(currentPlayer);
                }
            } else {
                System.out.println("Cette case est déjà occupée.");
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
        int playerId = cells[row][col].getOwnerId();
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
                && cells[r][c].getOwnerId() == playerId) {
            count++;
            r += dRow;
            c += dCol;
        }
        return count;
    }
}