import java.util.InputMismatchException;
import java.util.Scanner;

public class TicTacToe {
    private final int size = 6;
    private final int numberOfPlayers = 1;
    Cell[][] cells;
    Player[] players = new Player[numberOfPlayers];

    public TicTacToe() {
        initBoard();
        char[] symbols = new char[]{'0', 'X', '=', '#'};
        for (int i = 0; i < numberOfPlayers; i++) {
            this.players[i] = new Player(i, symbols[i]);
        }
    }

    private void initBoard() {
        this.cells = new Cell[this.size][this.size];
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    public void display() {
        System.out.println("-".repeat(this.size * 4 + 1));
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                System.out.print("|" + cell.getRepresentation());
            }
            System.out.println("|");
            System.out.println("-".repeat(this.size * 4 + 1));
        }
    }

    public int[] getMoveFromPlayer() {
        int x = 0;
        int y = 0;

        while (x > size || x < 1) {
            System.out.println("ligne ?");
            x = this.getIntFromUser();
        }

        while (y > size || y < 1) {
            System.out.println("colonne ?");
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

    private void setOwner(int x, int y, Player player) {
        cells[x][y].setOwner(player);
    }

    public void play() {
        int currentPlayer = 0;
        while (!this.isOver()) {
            System.out.println("====== Joueur " + currentPlayer + " ======");
            int[] move = getMoveFromPlayer();
            if (cells[move[0]][move[1]].getOwnerId() == -1) {
                cells[move[0]][move[1]].setOwner(players[currentPlayer]);
                currentPlayer = (currentPlayer + 1) % players.length;
            } else {
                System.out.println("Cette case est déjà occupée.");
            }
            this.display();
        }
        if (isWon())
            System.out.println("Victoire du joueur " + ((currentPlayer + 1) % players.length));
        else
            System.out.println("Match Nul");
    }

    private boolean isOver() {
        return isWon() || isFull();
    }

    private boolean isWon() {
        for (int x = 0; x < this.size; x++) {
            for (int y = 0; y < this.size - 2; y++) {
                if (cells[x][y].getOwnerId() != -1
                        && cells[x][y].getOwnerId() == cells[x][y + 1].getOwnerId()
                        && cells[x][y].getOwnerId() == cells[x][y + 2].getOwnerId())
                    return true;
            }
        }

        for (int y = 0; y < this.size; y++) {
            for (int x = 0; x < this.size - 2; x++) {
                if (cells[x][y].getOwnerId() != -1
                        && cells[x][y].getOwnerId() == cells[x + 1][y].getOwnerId()
                        && cells[x][y].getOwnerId() == cells[x + 2][y].getOwnerId())
                    return true;
            }
        }

        for (int x = 0; x < this.size - 2; x++) {
            for (int y = 0; y < this.size - 2; y++) {
                if (cells[x][y].getOwnerId() != -1
                        && cells[x][y].getOwnerId() == cells[x + 1][y + 1].getOwnerId()
                        && cells[x][y].getOwnerId() == cells[x + 2][y + 2].getOwnerId())
                    return true;
            }
        }

        for (int x = 0; x < this.size - 2; x++) {
            for (int y = 2; y < this.size; y++) {
                if (cells[x][y].getOwnerId() != -1
                        && cells[x][y].getOwnerId() == cells[x + 1][y - 1].getOwnerId()
                        && cells[x][y].getOwnerId() == cells[x + 2][y - 2].getOwnerId())
                    return true;
            }
        }
        return false;
    }

    private boolean isFull() {
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (cell.getOwnerId() == -1)
                    return false;
            }
        }
        return true;
    }

}
