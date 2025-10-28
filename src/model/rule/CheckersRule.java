package model.rule;

import model.Board;
import model.Cell;
import model.MoveStrategy;
import model.RulableStrategy;
import model.move.ComplexMove;
import model.move.Coordinates;

import java.util.ArrayList;
import java.util.List;

public class CheckersRule implements RulableStrategy {
    private int playerIdGoingUp;
    private int playerIdGoingDown;
    private final int defaultHeight;
    private final int defaultWidth;
    private ComplexMove lastMove;


    public CheckersRule() {
        this.playerIdGoingDown = -1;
        this.playerIdGoingUp = -1;
        this.defaultHeight = 10;
        this.defaultWidth = 10;
    }

    @Override
    public int getDefaultNbPlayers() {
        return 2;
    }

    @Override
    public Board getInitialBoard() {
        if (playerIdGoingDown == -1 || playerIdGoingUp == -1)
            throw new Error("Vous n'avez pas initialisé les joueurs avant !");

        Board board = new Board(defaultHeight, defaultWidth);
        for (int row = 0; row < defaultHeight; row++)
            for (int col = 0; col < defaultWidth; col++)
                if ((row + col) % 2 == 1) {
                    if (row < 4)
                        board.getCell(row, col).setOwnerId(playerIdGoingDown);
                    if (row >= defaultHeight - 4)
                        board.getCell(row, col).setOwnerId(playerIdGoingUp);
                }
        return board;
    }

    @Override
    public void playMove(Board board, MoveStrategy move) {
        if (move instanceof ComplexMove cMove) {
            int startRow = cMove.getStart().getRow();
            int startCol = cMove.getStart().getCol();
            int endRow = cMove.getEnd().getRow();
            int endCol = cMove.getEnd().getCol();

            lastMove = cMove;
            board.getCell(startRow, startCol).setEmpty();
            if (Math.abs(startRow - endRow) == 2)
                board.getCell((startRow + endRow) / 2, (startCol + endCol) / 2).setEmpty();
            board.getCell(endRow, endCol).setOwnerId(move.getPlayerId());
        }
    }

    private boolean canContinueEating(Board board, ComplexMove move) {
        List<MoveStrategy> possibleMoves = getValidMovesFromCell(board, move.getPlayerId(), move.getEnd().getRow(), move.getEnd().getCol(), true);
        return !possibleMoves.isEmpty();
    }

    @Override
    public List<MoveStrategy> getValidMoves(Board board, int playerId) {
        if (lastMove != null && lastMove.getPlayerId() == playerId)
            return getFollowingMoves(board);

        List<MoveStrategy> eatingMoves = getValidMovesFromBoard(board, playerId, true);

        if (eatingMoves.isEmpty())
            return getValidMovesFromBoard(board, playerId, false);
        else
            return eatingMoves;
    }

    private List<MoveStrategy> getFollowingMoves(Board board) {
        if (lastMove == null)
            return new ArrayList<>();
        List<MoveStrategy> moves = getValidMovesFromCell(board, lastMove.getPlayerId(), lastMove.getEnd().getRow(), lastMove.getEnd().getCol(), true);
        moves.removeIf(move -> !isFollowingMoveValid((ComplexMove) move));
        return moves;
    }

    private List<MoveStrategy> getValidMovesFromCell(Board board, int playerId, int row, int col, boolean onlyEating) {
        int dist = onlyEating ? 2 : 1;
        List<MoveStrategy> moves = new ArrayList<>(List.of(
                new ComplexMove(playerId, new Coordinates(row, col), new Coordinates(row + dist, col + dist)),
                new ComplexMove(playerId, new Coordinates(row, col), new Coordinates(row + dist, col - dist)),
                new ComplexMove(playerId, new Coordinates(row, col), new Coordinates(row - dist, col + dist)),
                new ComplexMove(playerId, new Coordinates(row, col), new Coordinates(row - dist, col - dist))));
        moves.removeIf(move -> !isMoveValid(board, move));
        return moves;
    }

    private List<MoveStrategy> getValidMovesFromBoard(Board board, int playerId, boolean onlyEating) {
        List<MoveStrategy> validMoves = new ArrayList<>();

        for (int row = 0; row < board.getHeight(); row++) {
            for (int col = 0; col < board.getWidth(); col++) {
                Cell cell = board.getCell(row, col);
                if (!cell.isEmpty() && cell.getOwnerId() == playerId) {
                    validMoves.addAll(getValidMovesFromCell(board, playerId, row, col, onlyEating));
                }
            }
        }
        return validMoves;
    }

    @Override
    public boolean isMoveValid(Board board, MoveStrategy move) {
        if (playerIdGoingUp == -1 || playerIdGoingDown == -1)
            return false;

        if (!(move instanceof ComplexMove cMove))
            return false;

        if (!isMoveInBoard(board, cMove))
            return false;

        if (!hasValidCoordinates(board, cMove))
            return false;


        if (isTurnFinish(board))
            if (canEat(board, cMove.getPlayerId()))
                return isEatingMove(cMove);
            else
                return true;
        else
            return isFollowingMoveValid(cMove);


    }

    private boolean canEat(Board board, int playerId) {
        return !getValidMovesFromBoard(board, playerId, true).isEmpty();
    }

    private boolean isMoveInBoard(Board board, ComplexMove move) {
        return isCoordinatesInBoard(board, move.getStart())
                && isCoordinatesInBoard(board, move.getEnd());
    }

    private boolean isCoordinatesInBoard(Board board, Coordinates coordinates) {
        return isCoordinatesInBoard(board, coordinates.getRow(), coordinates.getCol());
    }

    private boolean isCoordinatesInBoard(Board board, int row, int col) {
        return row >= 0 && row < board.getHeight() && col >= 0 && col < board.getWidth();
    }

    private boolean isFollowingMoveValid(ComplexMove move) {
        return lastMove.getEnd().getRow() == move.getStart().getRow()
                && lastMove.getEnd().getCol() == move.getStart().getCol()
                && Math.abs(move.getStart().getCol() - move.getEnd().getCol()) == 2;
    }

    private boolean hasValidCoordinates(Board board, ComplexMove move) {
        int startRow = move.getStart().getRow();
        int startCol = move.getStart().getCol();
        int endRow = move.getEnd().getRow();
        int endCol = move.getEnd().getCol();

        if (board.getCell(startRow, startCol).getOwnerId() != move.getPlayerId())
            return false;

        if (!board.getCell(endRow, endCol).isEmpty())
            return false;


        if (move.getPlayerId() == playerIdGoingUp) {
            if (startRow == endRow + 2 && Math.abs(startCol - endCol) == 2) {
                Cell eatenCell = board.getCell((startRow + endRow) / 2, (startCol + endCol) / 2);
                return (!eatenCell.isEmpty() && eatenCell.getOwnerId() != move.getPlayerId());
            }
            return (startRow == endRow + 1 && Math.abs(endCol - startCol) == 1);
        }

        if (move.getPlayerId() == playerIdGoingDown) {
            if (startRow == endRow - 2 && Math.abs(startCol - endCol) == 2) {
                Cell eatenCell = board.getCell((startRow + endRow) / 2, (startCol + endCol) / 2);
                return (!eatenCell.isEmpty() && eatenCell.getOwnerId() != move.getPlayerId());
            }
            return (startRow == endRow - 1 && Math.abs(endCol - startCol) == 1);
        }
        return false;
    }

    @Override
    public boolean isGameDraw(Board board) {
        return false;
    }

    @Override
    public boolean isMoveWinning(Board board, MoveStrategy lastMove) {
        for (int row = 0; row < board.getHeight(); row++) {
            for (int col = 0; col < board.getWidth(); col++) {
                Cell cell = board.getCell(row, col);
                if (!cell.isEmpty() && cell.getOwnerId() != lastMove.getPlayerId())
                    return false;
            }
        }
        return true;
    }

    private boolean isEatingMove(ComplexMove move) {
        return Math.abs(move.getEnd().getRow() - move.getStart().getRow()) == 2;
    }

    private boolean isTurnFinish(Board board) {
        return lastMove == null
                || !isEatingMove(lastMove)
                || !canContinueEating(board, lastMove);
    }

    @Override
    public int getNextPlayerId(Board board, MoveStrategy lastMove, List<Integer> playersId) {
        if (!isTurnFinish(board))
            return lastMove.getPlayerId();

        int idx = playersId.indexOf(lastMove.getPlayerId());
        return playersId.get((idx + 1) % playersId.size());
    }

    @Override
    public int getFirstPlayerId(List<Integer> listIds) {
        playerIdGoingUp = listIds.get(0);
        playerIdGoingDown = listIds.get(1);
        return playerIdGoingUp;
    }

    @Override
    public int getHeight() {
        return defaultHeight;
    }

    @Override
    public int getWidth() {
        return defaultWidth;
    }
}