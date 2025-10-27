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
    private boolean isTurnFinish;
    private ComplexMove lastMove;


    public CheckersRule() {
        this.playerIdGoingDown = -1;
        this.playerIdGoingUp = -1;
        this.defaultHeight = 10;
        this.defaultWidth = 10;
        this.isTurnFinish = true;
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
        if (lastMove!= null && lastMove.getPlayerId() != move.getPlayerId())
            lastMove = null;


        if (move instanceof ComplexMove cMove) {
            int startRow = cMove.getStart().getRow();
            int startCol = cMove.getStart().getCol();
            int endRow = cMove.getEnd().getRow();
            int endCol = cMove.getEnd().getCol();

            board.getCell(startRow, startCol).setEmpty();
            if (endCol == startCol + 2 || endCol == startCol - 2) {
                board.getCell((startRow + endRow) / 2, (startCol + endCol) / 2).setEmpty();
                lastMove = cMove;
                isTurnFinish = !canBeContinue(board, cMove);
            }

            board.getCell(endRow, endCol).setOwnerId(move.getPlayerId());
        }
    }

    private boolean canBeContinue(Board board, ComplexMove move) {
        if (move.getPlayerId() == playerIdGoingUp) {
            if (move.getEnd().getRow() - 2 > 0) {
                if (move.getEnd().getCol() > 1){
                    Cell eatenCell = board.getCell(move.getEnd().getRow()-1, move.getEnd().getCol()-1);
                    if (!eatenCell.isEmpty() && eatenCell.getOwnerId() != move.getPlayerId()){
                        Cell arrivalCell = board.getCell(move.getEnd().getRow()-2, move.getEnd().getCol()-2);
                        if (arrivalCell.isEmpty())
                            return true;
                    }
                }
                if (move.getEnd().getCol() < defaultWidth -2){
                    Cell eatenCell = board.getCell(move.getEnd().getRow()-1, move.getEnd().getCol()+1);
                    if (!eatenCell.isEmpty() && eatenCell.getOwnerId() != move.getPlayerId()){
                        Cell arrivalCell = board.getCell(move.getEnd().getRow()-2, move.getEnd().getCol()+2);
                        if (arrivalCell.isEmpty())
                            return true;
                    }
                }
            }
        }
        if (move.getPlayerId() == playerIdGoingDown) {
            if (move.getEnd().getRow() + 2 < defaultHeight) {
                if (move.getEnd().getCol() > 1){
                    Cell eatenCell = board.getCell(move.getEnd().getRow()+1, move.getEnd().getCol()-1);
                    if (!eatenCell.isEmpty() && eatenCell.getOwnerId() != move.getPlayerId()){
                        Cell arrivalCell = board.getCell(move.getEnd().getRow()+2, move.getEnd().getCol()-2);
                        if (arrivalCell.isEmpty())
                            return true;
                    }
                }
                if (move.getEnd().getCol() < defaultWidth -2){
                    Cell eatenCell = board.getCell(move.getEnd().getRow()+1, move.getEnd().getCol()+1);
                    if (!eatenCell.isEmpty() && eatenCell.getOwnerId() != move.getPlayerId()){
                        Cell arrivalCell = board.getCell(move.getEnd().getRow()+2, move.getEnd().getCol()+2);
                        return arrivalCell.isEmpty();
                    }
                }
            }
        }
        return false;
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

            if (!isMoveInBoard(board, cMove))
                return false;

            if (lastMove != null && lastMove.getPlayerId() == move.getPlayerId()) {
                return isNewMoveValid(board, cMove) && isFollowingMoveValid(cMove);
            } else
                return isNewMoveValid(board, cMove);
        } else
            return false;
    }

    private boolean isMoveInBoard(Board board, ComplexMove move) {
        int startRow = move.getStart().getRow();
        int startCol = move.getStart().getCol();
        int endRow = move.getEnd().getRow();
        int endCol = move.getEnd().getCol();

        return !(startRow < 0 || startRow >= board.getHeight()
                || startCol < 0 || startCol >= board.getWidth()
                || endRow < 0 || endRow >= board.getHeight()
                || endCol < 0 || endCol >= board.getWidth());
    }

    private boolean isFollowingMoveValid(ComplexMove move) {
        if (lastMove.getEnd().getRow() == move.getStart().getRow()
                && lastMove.getEnd().getCol() == move.getStart().getCol()) {
            return Math.abs(move.getStart().getCol() - move.getEnd().getCol()) == 2;
        }
        return false;
    }

    private boolean isNewMoveValid(Board board, ComplexMove move) {
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

    @Override
    public int getNextPlayerId(int playerId, List<Integer> playersId) {
        if (!isTurnFinish)
            return playerId;

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