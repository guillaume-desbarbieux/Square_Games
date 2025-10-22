package player.ai;

import board.Board;
import game.Rule;
import move.Move;
import player.Player;

import java.util.*;

public class MakeAlignementAI implements ArtificialIntelligence {
    private static final int MAX_DEPTH = 11;


    @Override
    public Move getNextMove(Board board, Rule rule, Player player, List<Player> players) {
        Player opponent = getOpponent(player, players);
        List<Move> validMoves = rule.getValidMoves(board, player);

        List<Integer> scores = new ArrayList<>();

        for (Move move : validMoves) {
            Board clonedBoard = board.copy();
            rule.playMove(clonedBoard, move);
            scores.add(evaluateMove(clonedBoard, rule, move, player, opponent, MAX_DEPTH - 1, false, Integer.MIN_VALUE, Integer.MAX_VALUE));
        }
        int bestScore = Collections.max(scores);

        List<Move> bestMoves = new ArrayList<>();
        for (int i = 0; i < scores.size(); i++) {
            if (scores.get(i) == bestScore) {
                bestMoves.add(validMoves.get(i));
            }
        }
        return bestMoves.get(new Random().nextInt(bestMoves.size()));
    }

    private int evaluateMove(Board board, Rule rule, Move lastMove, Player player, Player opponent, int depth, boolean isPlayerTurn, int alpha, int beta) {
        if (rule.isMoveWinning(board, lastMove))
            return isPlayerTurn ? -1000 - depth : +1000 + depth;

        if (rule.isBoardFull(board) || depth == 0)
            return evaluateBoard(board, rule, player, opponent, isPlayerTurn);

        Player current = isPlayerTurn ? player : opponent;
        List<Move> moves = rule.getValidMoves(board, current);

        if (moves.isEmpty())
            return 0;

        int bestScore = isPlayerTurn ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (Move move : moves) {
            Board clonedBoard = board.copy();
            rule.playMove(clonedBoard, move);

            if (rule.isMoveWinning(clonedBoard, move))
                return isPlayerTurn ? +900 + depth : -900 - depth;


            int score = evaluateMove(clonedBoard, rule, move, player, opponent, depth - 1, isPlayerTurn, alpha, beta);

            if (isPlayerTurn) {
                bestScore = Math.max(bestScore, score);
                alpha = Math.max(alpha, score);
            } else {
                bestScore = Math.min(bestScore, score);
                beta = Math.min(beta, score);
            }
            if (alpha >= beta)
                return bestScore;
        }
        return bestScore;
    }

    private int evaluateBoard(Board board, Rule rule, Player player, Player opponent, boolean isPlayerTurn) {
        return 0;
    }

    private Player getOpponent(Player player, List<Player> players) {
        for (Player p : players)
            if (p.getId() != player.getId())
                return p;
        return null;
    }
}