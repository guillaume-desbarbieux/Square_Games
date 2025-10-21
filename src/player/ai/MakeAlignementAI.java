package player.ai;

import board.Board;
import game.Rule;
import move.Move;
import player.Player;

import java.util.*;

public class MakeAlignementAI implements ArtificialIntelligence {
    private static final int MAX_DEPTH = 3;


    @Override
    public Move getNextMove(Board board, Rule rule, Player player, List<Player> players) {
        Player opponent = getOpponent(player, players);
        List<Move> validMoves = rule.getValidMoves(board, player);

        List<Integer> scores = new ArrayList<>();

        for (Move move : validMoves) {
            Board clonedBoard = board.copy();
            scores.add(evaluateMove(clonedBoard, rule, move, player, opponent, MAX_DEPTH - 1, true));
        }
        int bestScore = Collections.max(scores);

        List<Move> bestMoves = new ArrayList<>();
        for (int i = 0 ; i < scores.size() ; i++){
            if (scores.get(i) == bestScore){
                bestMoves.add(validMoves.get(i));
            }
        }
        return bestMoves.get(new Random().nextInt(bestMoves.size()));
}

private int evaluateMove(Board board, Rule rule, Move move, Player player, Player opponent, int depth, boolean isPlayerTurn) {
    if (rule.isMoveWinning(board, move))
        return isPlayerTurn ? +depth : -depth;
    if (rule.isGameOver(board, move) || depth == 0)
        return 0;

    rule.playMove(board, move);
    isPlayerTurn = !isPlayerTurn;

    Player current = isPlayerTurn ? player : opponent;
    List<Move> moves = rule.getValidMoves(board, current);

    if (moves.isEmpty())
        return 0;

    int bestScore = isPlayerTurn ? Integer.MIN_VALUE : Integer.MAX_VALUE;

    for (Move nextMove : moves) {
        Board clonedBoard = board.copy();
        int score = evaluateMove(clonedBoard, rule, nextMove, player, opponent, depth - 1, isPlayerTurn);

        if (isPlayerTurn)
            bestScore = Math.max(bestScore, score);
        else
            bestScore = Math.min(bestScore, score);
    }
    return bestScore;
}

private Player getOpponent(Player player, List<Player> players) {
    for (Player p : players)
        if (p.getId() != player.getId())
            return p;
    return null;
}
}