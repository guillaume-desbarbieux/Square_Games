package model.player.ai;

import model.Board;
import model.Move;
import model.player.Player;
import model.Rule;
import model.rule.AlignementGameRule;

import java.util.*;

/**
 * The MakeAlignementAI class implements the ArtificialIntelligence interface,
 * using a minimax algorithm with alpha-beta pruning to determine the best possible move
 * in a turn-based game. This AI focuses on alignment-based strategy games and optimizes
 * decisions by evaluating the game state recursively up to a maximum depth.
 * <p>
 * This AI evaluates potential moves to maximize the chances of winning while minimizing
 * losses. It also avoids immediate losing moves by simulating opponent responses.
 */
public class MakeAlignementAI implements Playable {
    private static final int MAX_DEPTH = 9;

    /**
     * Determines the next move for the given player based on the current board state, rule set, and list of players.
     *
     * @param board   the current state of the game board
     * @param rule    the rules of the game influencing allowed moves and outcomes
     * @param player  the player for whom the next move is being determined
     * @param players the list of all players participating in the game
     * @return the next move to be played for the given player
     */
    @Override
    public Move getNextMove(Board board, Rule rule, Player player, List<Player> players) {
        Player opponent = getOpponent(player, players);
        List<Move> validMoves = rule.getValidMoves(board, player.getId());
        if (validMoves.isEmpty())
            return null;
        if (validMoves.size() == 1)
            return validMoves.get(0);

        for (Move move : validMoves)
            if (rule.isMoveWinning(board, move))
                return move;

        List<Integer> moveScores = new ArrayList<>();

        for (Move move : validMoves) {
            Board clonedBoard = board.copy();
            rule.playMove(clonedBoard, move);

            moveScores.add(evaluateMove(clonedBoard, rule, player, opponent, MAX_DEPTH - 1, true, Integer.MIN_VALUE, Integer.MAX_VALUE));
        }

        int bestScore = Collections.max(moveScores);

        List<Move> bestMoves = new ArrayList<>();
        for (int i = 0; i < moveScores.size(); i++) {
            if (moveScores.get(i) == bestScore) {
                bestMoves.add(validMoves.get(i));
            }
        }
        return bestMoves.get(new Random().nextInt(bestMoves.size()));
    }

    private int evaluateMove(Board board, Rule rule, Player player, Player opponent, int depth, boolean wasPlayerMove, int alpha, int beta) {
        if (rule.isBoardFull(board) || depth == 0)
            return evaluateBoard(board, rule, player, opponent);

        List<Move> moves = rule.getValidMoves(board, wasPlayerMove ? opponent.getId() : player.getId());

        if (moves.isEmpty())
            return 0;

        int bestEval = wasPlayerMove ? Integer.MAX_VALUE : Integer.MIN_VALUE;

        for (Move move : moves) {
            Board clonedBoard = board.copy();
            rule.playMove(clonedBoard, move);

            if (rule.isMoveWinning(clonedBoard, move))
                return wasPlayerMove ? -1000 - depth : +1000 + depth;

            List<Move> nextMoves = rule.getValidMoves(clonedBoard, wasPlayerMove ? player.getId() : opponent.getId());
            boolean losingMove = false;
            for (Move nextMove : nextMoves) {
                Board clonedBoard2 = clonedBoard.copy();
                rule.playMove(clonedBoard2, nextMove);
                if (rule.isMoveWinning(clonedBoard2, nextMove)) {
                    losingMove = true;
                    break;
                }
            }
            int eval;
            if (losingMove)
                eval = wasPlayerMove ? (900 + depth) : (-900 - depth);
            else
                eval = evaluateMove(clonedBoard, rule, player, opponent, depth - 1, !wasPlayerMove, alpha, beta);

            if (wasPlayerMove) {
                bestEval = Math.min(bestEval, eval);
                beta = Math.min(beta, eval);
            } else {
                bestEval = Math.max(bestEval, eval);
                alpha = Math.max(alpha, eval);
            }
            if (alpha >= beta)
                break;
        }
        return bestEval;
    }

    private int evaluateBoard(Board board, Rule rule, Player player, Player opponent) {
        int playerScore = 0;
        for (Move move : rule.getValidMoves(board, player.getId())) {
            List<Integer> alignements = ((AlignementGameRule) rule).countAlignement(board, move, true);
            playerScore += alignements.get(0) + alignements.get(1) + alignements.get(2) + alignements.get(3);
        }

        int opponentScore = 0;
        for (Move move : rule.getValidMoves(board, opponent.getId())) {
            List<Integer> alignements = ((AlignementGameRule) rule).countAlignement(board, move, true);
            opponentScore += alignements.get(0) + alignements.get(1) + alignements.get(2) + alignements.get(3);
        }

        return playerScore - opponentScore;
    }


    private Player getOpponent(Player player, List<Player> players) {
        for (Player p : players)
            if (p.getId() != player.getId())
                return p;
        return null;
    }
}