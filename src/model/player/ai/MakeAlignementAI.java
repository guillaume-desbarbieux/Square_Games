package model.player.ai;

import model.Board;
import model.Move;
import model.player.Player;
import model.Rule;

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
    private static final int MAX_DEPTH = 5;

    /**
     * Determines the next move for the given player based on the current board state, rule set, and list of players.
     *
     * @param board the current state of the game board
     * @param rule the rules of the game influencing allowed moves and outcomes
     * @param player the player for whom the next move is being determined
     * @param players the list of all players participating in the game
     * @return the next move to be played for the given player
     */
    @Override
    public Move getNextMove(Board board, Rule rule, Player player, List<Player> players) {
        Player opponent = getOpponent(player, players);
        List<Move> validMoves = rule.getValidMoves(board, player.getId());

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

    /**
     * Evaluates the outcome of a move within the context of a game. The method
     * uses a recursive approach to determine the optimal move based on the current
     * game state, rules, and turns, incorporating the minimax algorithm with
     * alpha-beta pruning and some
     *
     * @param board the current state of the game board
     * @param rule the set of rules defining gameplay and valid moves
     * @param lastMove the move that was last played in the game
     * @param player the player whose move is to be evaluated
     * @param opponent the opposing player
     * @param depth the remaining depth to explore in the game tree
     * @param isPlayerTurn true if it is the turn of the player, false otherwise
     * @param alpha the best score that the maximizing player can guarantee
     * @param beta the best score that the minimizing player can guarantee
     * @return an integer representing the evaluation score of the move
     */
    private int evaluateMove(Board board, Rule rule, Move lastMove, Player player, Player opponent, int depth, boolean isPlayerTurn, int alpha, int beta) {
        if (rule.isMoveWinning(board, lastMove))
            return isPlayerTurn ? 1000 + depth : - 1000 - depth;

        if (rule.isBoardFull(board) || depth == 0)
            return 0;
        //TODO
        // évaluer plutôt que retour = 0
        // return evaluateBoard(_old.board, rule, model.player, opponent, isPlayerTurn);

        List<Move> moves = rule.getValidMoves(board, isPlayerTurn ? player.getId() : opponent.getId());

        if (moves.isEmpty())
            return 0;

        int bestEval = isPlayerTurn ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (Move move : moves) {
            Board clonedBoard = board.copy();
            rule.playMove(clonedBoard, move);

            if (rule.isMoveWinning(clonedBoard, move))
                return isPlayerTurn ? 900 + depth : -900 - depth;

            List<Move> nextMoves = rule.getValidMoves(clonedBoard, isPlayerTurn ? opponent.getId() : player.getId());
            boolean losingMove = false;
            for (Move nextMove : nextMoves) {
                Board clonedBoard2 = clonedBoard.copy();
                rule.playMove(clonedBoard2, nextMove);
                if (rule.isMoveWinning(clonedBoard2, nextMove)) {
                    losingMove = true;
                    break;
                }
            }
            if (losingMove)
                continue;

            int eval = evaluateMove(clonedBoard, rule, move, player, opponent, depth - 1, !isPlayerTurn, alpha, beta);

            if (isPlayerTurn) {
                bestEval = Math.max(bestEval, eval);
                alpha = Math.max(alpha, eval);
            } else {
                bestEval = Math.min(bestEval, eval);
                beta = Math.min(beta, eval);
            }
            if (alpha >= beta)
                return bestEval;
        }
        return bestEval;
    }

    //TODO
    // Heuristique d'évaluation des boards après avoir atteint MAX_DEPTH :
    // private int evaluateBoard(Board _old.board, Rule rule, Player model.player, Player opponent, boolean isPlayerTurn) {}
    //


    /**
     * Retrieves the opponent of the specified player from a list of players.
     *
     * @param player the player whose opponent is to be found
     * @param players the list of all players participating in the game
     * @return the opponent of the specified player, or null if no opponent is found
     */
    private Player getOpponent(Player player, List<Player> players) {
        for (Player p : players)
            if (p.getId() != player.getId())
                return p;
        return null;
    }
}