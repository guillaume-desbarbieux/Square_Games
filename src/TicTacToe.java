import java.awt.*;
import java.util.Arrays;
import java.util.Random;
import java.util.List;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TicTacToe {
    private final int height;
    private final int width;
    private Cell[][] cells;
    private Player[] players;
    private final int winningLength;

    public TicTacToe() {
        this(3,3,3,2);
    }

    public TicTacToe(int height, int width, int winningLength, int nbPlayers){
        this.height = Math.max(2,Math.min(20,height));
        this.width = Math.max(2,Math.min(20,width));
        this.winningLength = Math.max(2,Math.min(winningLength, Math.max(width, height)));
        nbPlayers = Math.max(1,Math.min(7,nbPlayers));
        initCells();
        initPlayers(nbPlayers);
    }

    private void initPlayers(int nb_players) {

        this.players = new Player[nb_players];
        List<Color> possibleColors = Arrays.asList(Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE, Color.PURPLE, Color.CYAN, Color.WHITE);
        Random random = new Random();

        for (int i = 0; i < nb_players; i++) {
            Color randomColor = possibleColors.get(random.nextInt(possibleColors.size()));
            this.players[i] = new Player(i, '●', randomColor);
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
        System.out.println("-".repeat(width * 4 + 1));
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                System.out.print("|" + cell.getRepresentation());
            }
            System.out.println("|");
            System.out.println("-".repeat(width * 4 + 1));
        }
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
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Ceci n'est pas un entier.");
                scanner.nextLine();
            }
        }
    }

    public void play() {
        Player currentPlayer = players[0];
        int freeCells = height * width;
        display();
        Player winner = null;

        while (freeCells > 0 && winner == null) {
            currentPlayer = getNextPlayer(currentPlayer);

            System.out.println("=== Joueur " + currentPlayer.getId() + " ===");

            int[] move = getMoveFromPlayer();
            Cell playedCell = cells[move[0]][move[1]];

            if (playedCell.isEmpty()) {
                playedCell.setOwner(currentPlayer);
                freeCells--;
                if (isWinning(move[0],move[1]))
                    winner = currentPlayer;
            } else {
                System.out.println("Cette case est déjà occupée.");
            }
            this.display();
        }
        if (winner == null)
            System.out.println("Match Nul");
        else
            System.out.println("Victoire du joueur " + winner.getId());
    }

    private Player getNextPlayer(Player player) {
        int currentId = player.getId();
        int nextId = (currentId + 1) % players.length;
        return players[nextId];
    }


    private boolean isWinning(int row, int col) {
        int playerId = cells[row][col].getOwnerId();
        if (playerId == -1)
            return false;

        int[][] directions = {
                {0, 1}, // horizontally
                {1, 0}, // vertically
                {1, 1}, // diagonally ↘
                {1, -1} // diagonally ↙
        };

        for (int[] dir : directions) {
            int count = 1;
            count += countInDirection(row, col, dir[0], dir[1], playerId);
            count += countInDirection(row, col, -dir[0], -dir[1], playerId);

            if (count >= winningLength)
                return true;

        }
        return false;
    }

    private int countInDirection(int row, int col, int dRow, int dCol, int playerId) {
        int count = 0;

        int r = row + dRow;
        int c = col + dCol;
        while (r >= 0 && r < width
                && c >= 0 && c < height
                && cells[r][c].getOwnerId() == playerId) {
            count++;
            r += dRow;
            c += dCol;
        }
        return count;
    }
}