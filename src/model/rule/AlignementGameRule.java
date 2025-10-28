package model.rule;

import model.*;
import model.move.Coordinates;
import model.move.SimpleMove;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a rule for an alignment-based game where players must align a number
 * of their tokens (horizontally, vertically, or diagonally) to win.
 * This class extends {@code Rule} and provides specific implementations for alignment-style games.
 */
public class AlignementGameRule extends Rule {
    /**
     * The number of tokens that need to be aligned consecutively
     * (horizontally, vertically, or diagonally) for a player to win the game.
     * This value defines the winning condition of the alignment-based game.
     */
    private final int winningLength;

    /**
     * Constructs an alignment-based game rule with the specified board dimensions,
     * default number of players, and the required length of tokens in alignment to win.
     *
     * @param height           the height of the game board
     * @param width            the width of the game board
     * @param defaultNbPlayers the default number of players in the game
     * @param winningLength    the number of tokens that need to be aligned (horizontally, vertically, or diagonally) to win the game
     */
    public AlignementGameRule(int height, int width, int defaultNbPlayers, int winningLength) {
        super(height, width, defaultNbPlayers);
        this.winningLength = winningLength;
    }

    /**
     * Initializes and returns the starting configuration of the game board.
     * The board is created with the specified height and width and is filled
     * with default cell representations.
     *
     * @return the initial state of the game board, populated with default cell representations
     */
    @Override
    public Board getInitialBoard() {
        return new Board(getHeight(), getWidth());
    }

    /**
     * Plays a move by updating the ownership of the specified cell on the board
     * based on the move's row, column, and player.
     *
     * @param board the game board on which the move is played
     * @param move  the move containing the player and the row and column of the cell being updated
     */
    @Override
    public void playMove(Board board, MoveStrategy move) {
        int playerId = move.getPlayerId();
        board.getCell(move.getRow(), move.getCol()).setOwnerId(playerId);
    }

    /**
     * Returns a string representation of the game rule, indicating the number
     * of tokens required to be aligned for a win and providing a motivational message.
     *
     * @return a formatted string displaying the winning alignment requirement and a message
     */
    @Override
    public String toString() {
        return String.format("""
                Alignez %d jetons pour gagner...
                %50s""", winningLength, "Bonne chance !");
    }

    /**
     * Checks if the game board is entirely filled with non-empty cells.
     *
     * @param board the game board to evaluate for fullness
     * @return true if all cells on the board are occupied, false otherwise
     */
    @Override
    public boolean isGameDraw(Board board) {
        for (int row = 0; row < getHeight(); row++)
            for (int col = 0; col < getWidth(); col++)
                if (board.getCell(row, col).isEmpty())
                    return false;

        return true;
    }


    @Override
    public List<MoveStrategy> getValidMoves(Board board, int playerId) {
        List<MoveStrategy> listValidMoves = new ArrayList<>();
        for (int row = 0; row < getHeight(); row++) {
            for (int col = 0; col < getWidth(); col++) {
                SimpleMove move = new SimpleMove(playerId, new Coordinates(row, col));
                if (isMoveValid(board, move))
                    listValidMoves.add(move);
            }
        }
        return listValidMoves;
    }


    @Override
    public int getNextPlayerId(Board board, MoveStrategy lastMove, List<Integer> listId) {
        return listId.get(((lastMove.getPlayerId() + 1) % listId.size()));
    }

    @Override
    public int getFirstPlayerId(List<Integer> listId) {
        return listId.getFirst();
    }

    /**
     * Determines whether a given move is valid on the specified game board.
     * A move is considered valid if it is within the boundaries of the board
     * and the targeted cell is empty.
     *
     * @param board the game board on which the move is being evaluated
     * @param move  the move to be checked, including the row, column, and player information
     * @return true if the move is valid (within board boundaries and targeting an empty cell), false otherwise
     */
    @Override
    public boolean isMoveValid(Board board, MoveStrategy move) {
        int row = move.getRow();
        int col = move.getCol();
        return row >= 0 && row < board.getHeight()
                && col >= 0 && col < board.getWidth()
                && board.getCell(row, col).isEmpty();
    }

    /**
     * Determines if the provided move is a winning move on the given game board.
     * A move is considered winning if it results in an alignment of tokens
     * meeting the required winning length condition defined in the game rules.
     *
     * @param board    the game board on which the move was played
     * @param lastMove the most recent move to evaluate for a winning condition
     * @return true if the move results in a winning alignment, false otherwise
     */
    @Override
    public boolean isMoveWinning(Board board, MoveStrategy lastMove) {
        return makeAlignment(board, lastMove);
    }

    private boolean makeAlignment(Board board, MoveStrategy move) {
      List<Integer> alignements = countAlignement(board, move, false);
      for (int count : alignements) {
            if (count >= winningLength) return true;
        }
        return false;
    }

    /**
     * Counts the number of tokens in each alignment (horizontally, vertically, or diagonally) for a given move.
     * @param board the game board on which the move was played
     * @param move the move to be evaluated (containing the playerId and the row and column of the cell being updated)
     * @param countEmptyCells if true, also count empty cells
     * @return a list of alignment counts, orderer as follows: horizontally, vertically, diagonally ↘, diagonally ↙
     */
    public List<Integer> countAlignement(Board board, MoveStrategy move, boolean countEmptyCells) {
        int row = move.getRow();
        int col = move.getCol();
        int playerId = move.getPlayerId();
        List<Integer> alignements = new ArrayList<>();
        int[][] directions = {
                {0, 1}, // horizontally
                {1, 0}, // vertically
                {1, 1}, // diagonally ↘
                {1, -1} // diagonally ↙
        };

        for (int[] dir : directions) {
            int count = 1;
            count += countInDirection(board, row, col, dir[0], dir[1], playerId, countEmptyCells);
            count += countInDirection(board, row, col, -dir[0], -dir[1], playerId, countEmptyCells);
            alignements.add(count);
        }
        return alignements;
    }

    private int countInDirection(Board board, int row, int col, int dRow, int dCol, int playerId, boolean countEmptyCells) {
        int count = 0;
        int r = row + dRow;
        int c = col + dCol;
        while (r >= 0 && r < board.getHeight()
                && c >= 0 && c < board.getWidth()
                && (board.getCell(r, c).getOwnerId() == playerId
                || (countEmptyCells && board.getCell(r, c).isEmpty()))) {
            count++;
            r += dRow;
            c += dCol;
        }
        return count;
    }

    public List<Cell> getWinningCells(List<MoveStrategy> movesHistory, Board board) {
        List<Cell> winningCells = new ArrayList<>();
        for (MoveStrategy move : movesHistory)
            if (isMoveWinning(board, move))
                winningCells.add(board.getCell(move.getRow(), move.getCol()));
        return winningCells;
    }
}