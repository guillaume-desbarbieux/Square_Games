package model.rule;

import model.Board;
import model.Cell;
import model.Move;
import model.Rule;
import model.player.Player;
import model.player.representation.Color;
import model.player.representation.RepresentationFactory;

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
        return new Board(getHeight(), getWidth(), new RepresentationFactory(List.of(Color.WHITE), List.of('·')));
    }

    /**
     * Plays a move by updating the ownership of the specified cell on the board
     * based on the move's row, column, and player.
     *
     * @param board the game board on which the move is played
     * @param move  the move containing the player and the row and column of the cell being updated
     */
    @Override
    public void playMove(Board board, Move move) {
        Player player = move.getPlayer();
        board.getCell(move.getRow(), move.getCol()).setOwner(player.getId(), player.getRepresentation());
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
     * Determines whether the game is over based on the state of the board
     * and the last move played. The game is considered over if the last move
     * results in a winning alignment or if the board is completely full.
     *
     * @param board    the game board to check for game-over conditions
     * @param lastMove the move most recently played that may have caused the game to end
     * @return true if the game is over, either due to a win or a full board, false otherwise
     */
    @Override
    public boolean isGameOver(Board board, Move lastMove) {
        return (isMoveWinning(board, lastMove) || isBoardFull(board));
    }

    /**
     * Checks if the game board is entirely filled with non-empty cells.
     *
     * @param board the game board to evaluate for fullness
     * @return true if all cells on the board are occupied, false otherwise
     */
    @Override
    public boolean isBoardFull(Board board) {
        for (int row = 0; row < getHeight(); row++)
            for (int col = 0; col < getWidth(); col++)
                if (board.getCell(row, col).isEmpty())
                    return false;

        return true;
    }

    /**
     * Retrieves all valid moves for a given player on the specified game board.
     * A move is considered valid if it resides within the boundaries of the board
     * and the targeted cell is empty.
     *
     * @param board  the game board on which the validity of moves is determined
     * @param player the player for whom the valid moves are being calculated
     * @return a list of valid moves the player can make on the given board
     */
    @Override
    public List<Move> getValidMoves(Board board, Player player) {
        List<Move> listValidMoves = new ArrayList<>();
        for (int row = 0; row < getHeight(); row++) {
            for (int col = 0; col < getWidth(); col++) {
                Move move = new Move(player, row, col);
                if (isMoveValid(board, move))
                    listValidMoves.add(move);
            }
        }
        return listValidMoves;
    }

    /**
     * Determines and retrieves the next player in the sequence based on the current player.
     * The method assumes a circular order in the list of players and computes the next player
     * by advancing the index of the current player.
     *
     * @param currentPlayer the player whose turn is currently active
     * @param players       the list of all players participating in the game
     * @return the next player in the sequence
     */
    @Override
    public Player getNextPlayer(Player currentPlayer, List<Player> players) {
        return players.get((currentPlayer.getId() + 1) % players.size());
    }

    /**
     * Retrieves the first player from the provided list of players.
     *
     * @param players the list of players participating in the game
     * @return the first player in the provided list
     */
    @Override
    public Player getFirstPlayer(List<Player> players) {
        return players.getFirst();
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
    public boolean isMoveValid(Board board, Move move) {
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
    public boolean isMoveWinning(Board board, Move lastMove) {
        return makeAlignment(board, lastMove);
    }

    /**
     * Evaluates if a move results in a winning alignment on the game board. A winning alignment
     * occurs when a specified number of consecutive tokens belong to the same player in any direction:
     * horizontal, vertical, or diagonal.
     *
     * @param board the game board on which the move was made
     * @param move  the move to evaluate, containing the player and the position of the move
     * @return true if the move creates a winning alignment, false otherwise
     */
    private boolean makeAlignment(Board board, Move move) {
        int row = move.getRow();
        int col = move.getCol();
        int playerId = move.getPlayer().getId();

        int[][] directions = {
                {0, 1}, // horizontally
                {1, 0}, // vertically
                {1, 1}, // diagonally ↘
                {1, -1} // diagonally ↙
        };

        for (int[] dir : directions) {
            int count = 1;
            count += countInDirection(board, row, col, dir[0], dir[1], playerId);
            count += countInDirection(board, row, col, -dir[0], -dir[1], playerId);
            if (count >= winningLength) return true;
        }
        return false;
    }

    /**
     * Counts the number of consecutive cells in a given direction from a starting cell
     * on the board that are owned by the same player.
     *
     * @param board    the game board to evaluate
     * @param row      the starting row position of the cell
     * @param col      the starting column position of the cell
     * @param dRow     the row direction to move for the count (e.g., -1, 0, 1)
     * @param dCol     the column direction to move for the count (e.g., -1, 0, 1)
     * @param playerId the ID of the player whose tokens are being counted
     * @return the number of consecutive cells owned by the specified player in the given direction
     */
    private int countInDirection(Board board, int row, int col, int dRow, int dCol, int playerId) {
        int count = 0;
        int r = row + dRow;
        int c = col + dCol;
        while (r >= 0 && r < board.getHeight()
                && c >= 0 && c < board.getWidth()
                && !board.getCell(r, c).isEmpty()
                && board.getCell(r, c).getOwnerId() == playerId) {
            count++;
            r += dRow;
            c += dCol;
        }
        return count;
    }

    public List<Cell> getWinningCells(List<Move> movesHistory, Board board) {
        List<Cell> winningCells = new ArrayList<>();
        for (Move move : movesHistory)
            if (isMoveWinning(board, move))
                winningCells.add(board.getCell(move.getRow(), move.getCol()));
        return winningCells;
    }
}