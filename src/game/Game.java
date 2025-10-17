package game;

import board.Board;
import move.Move;
import ui.InteractionUser;
import ui.View;
import player.Player;
import player.factory.PlayerFactory;

import java.util.List;

public abstract class Game {
    protected final View view;
    protected final InteractionUser interact;
    protected PlayerFactory playerFactory;
    protected Board board;
    protected List<Player> players;


    public Game() {
        this.view = new View();
        this.interact = new InteractionUser(view);
        this.playerFactory = new PlayerFactory(interact, this.getClass());

    }

    public abstract void start();

    protected abstract void menu();

    protected abstract void initGame(int height, int width, int nbHumanPlayers, int nbArtificialPlayers);

    protected abstract void play();

    protected Player getNextPlayer(Player player){
        int currentId = player.getId();
        int nextId = (currentId + 1) % players.size();
        return players.get(nextId);
    }

    protected abstract boolean isWinning(Move move);

    protected boolean makeAlignment(int row, int col, int winningLength) {
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