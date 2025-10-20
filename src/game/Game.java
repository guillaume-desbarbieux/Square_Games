package game;

import board.Board;
import move.Move;
import move.factory.MoveInputAdapter;
import ui.InteractionUser;
import ui.View;
import player.Player;
import player.factory.PlayerFactory;

import java.util.List;

public abstract class Game {
    protected final View view;
    protected final InteractionUser interact;
    protected final PlayerFactory playerFactory;
    protected Board board;
    protected List<Player> players;
    protected Player currentPlayer;
    protected String name;
    protected int defaultHeight;
    protected int defaultWidth;
    protected final MoveInputAdapter moveInputAdapter;

    public Game(String name, int defaultHeight, int defaultWidth, MoveInputAdapter moveInputAdapter) {
        this.name = name;
        this.defaultHeight = defaultHeight;
        this.defaultWidth = defaultWidth;
        this.view = View.getInstance();
        this.interact = InteractionUser.getInstance();
        this.playerFactory = new PlayerFactory(this.getClass());
        this.moveInputAdapter = moveInputAdapter;

    }

    public void start() {
        initBoard(defaultHeight, defaultWidth);
        view.displayTitle(this.name);
        int choice = interact.getChoice("Bienvenue !", new String[]{"Partie Rapide", "Paramètres avancés"});
        if (choice == 1) {
            initPlayers(1, 1);
        } else {
            menu();
        }
        play();
    }

    protected void menu() {
        view.displayTitle("Menu Principal");
        int nbHumanPlayers = interact.getInt("nb Joueurs Humains", 0, 2);
        int nbArtificialPlayers = 2 - nbHumanPlayers;
        int choice = interact.getChoice("Affichage du plateau", new String[]{"Petit", "Grand"});
        view.setMaximize(choice == 2);
        initPlayers(nbHumanPlayers, nbArtificialPlayers);
        play();
    }

    protected abstract void initBoard(int height, int width);

    protected void initPlayers(int nbHumanPlayers, int nbArtificialPlayers) {
        nbHumanPlayers = clamp(nbHumanPlayers, 0, 7);
        nbArtificialPlayers = clamp(nbArtificialPlayers, (nbHumanPlayers == 0) ? 1 : 0, 7 - nbHumanPlayers);
        this.players = playerFactory.createPlayers(nbHumanPlayers, nbArtificialPlayers);
    }

    protected abstract void displayRules();

    protected void play() {
        displayRules();

        currentPlayer = players.getFirst();
        view.displayBoard(board);
        Player winner = null;

        while (board.isNotFull() && winner == null) {
            view.display("=== Joueur " + currentPlayer.getRepresentation() + " ===");
            boolean isTurnInProgress = true;
            while (isTurnInProgress) {

                Move move = currentPlayer.getNextMove(board, moveInputAdapter);

                if (isPlayable(move)) {
                    isTurnInProgress = playMove(move);

                    if (isWinning(move)) {
                        winner = currentPlayer;
                        isTurnInProgress = false;
                    }
                } else {
                    view.displayError("Coup impossible");
                }
                view.displayBoard(board);
            }
            getNextPlayer();
        }

        if (winner == null) {
            view.display("Match Nul");
        } else {
            view.display("Victoire du joueur " + winner.getRepresentation());
        }
    }

    protected abstract boolean isPlayable(Move move);

    protected abstract boolean playMove(Move move);

    protected abstract void getNextPlayer();

    protected abstract boolean isWinning(Move move);

    protected boolean makeAlignment(int row, int col, int winningLength) {
        int playerId = board.getCell(row, col).getOwner().getId();
        if (playerId == -1) return false;

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
            if (count >= winningLength) return true;
        }
        return false;
    }

    protected int countInDirection(int row, int col, int dRow, int dCol, int playerId) {
        int count = 0;
        int r = row + dRow;
        int c = col + dCol;
        while (r >= 0 && r < board.height()
                && c >= 0 && c < board.width()
                && !board.getCell(r, c).isEmpty()
                && board.getCell(r, c).getOwner().getId() == playerId) {
            count++;
            r += dRow;
            c += dCol;
        }
        return count;
    }

    protected int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}