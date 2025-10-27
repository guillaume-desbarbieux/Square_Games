package model.rule;

import model.Board;
import model.Cell;
import model.MoveStrategy;
import model.RulableStrategy;
import model.move.ComplexMove;

import java.util.List;

public class CheckersRule implements RulableStrategy {
    private int playerIdGoingUp;
    private int playerIdGoingDown;
    private final int defaultHeight;
    private final int defaultWidth;


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
        for (int row = 0; row < 3; row += 2) {
            for (int col = 1; col < defaultWidth; col += 2) {
                board.getCell(row, col).setOwnerId(playerIdGoingDown);
                board.getCell(row + 1, col - 1).setOwnerId(playerIdGoingDown);
            }
        }

        for (int row = defaultHeight - 4; row < defaultHeight; row += 2) {
            for (int col = 1; col < defaultWidth; col += 2) {
                board.getCell(row, col).setOwnerId(playerIdGoingUp);
                board.getCell(row + 1, col - 1).setOwnerId(playerIdGoingUp);
            }
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

            board.getCell(startRow, startCol).setEmpty();

            if (endCol == startCol + 2 || endCol == startCol - 2)
                board.getCell((startRow + endRow) / 2, (startCol + endCol) / 2).setEmpty();

            board.getCell(endRow, endCol).setOwnerId(move.getPlayerId());
        }
    }

    @Override
    public List<MoveStrategy> getValidMoves(Board board, int playerId) {
        //TODO à faire pour l'IA
        return null;
    }

    @Override
    public boolean isMoveValid(Board board, MoveStrategy move) {
        if (playerIdGoingUp == -1 || playerIdGoingDown == -1)
            return false;

        if (move instanceof ComplexMove cMove) {
            int startRow = cMove.getStart().getRow();
            int startCol = cMove.getStart().getCol();
            int endRow = cMove.getEnd().getRow();
            int endCol = cMove.getEnd().getCol();

            if (startRow < 0 || startRow >= board.getHeight()
                    || startCol < 0 || startCol >= board.getWidth()
                    || endRow < 0 || endRow >= board.getHeight()
                    || endCol < 0 || endCol >= board.getWidth())
                return false;


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
                return (startRow == endRow -1 && Math.abs(endCol - startCol) == 1);
            }
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

    @Override
    public int getNextPlayerId(int playerId, List<Integer> playersId) {
        for (Integer pId : playersId) {
            if (pId != playerId)
                return pId;
        }
        return -1;
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