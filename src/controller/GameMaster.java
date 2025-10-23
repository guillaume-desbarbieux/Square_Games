package controller;

import controller.moveAdapter.ColInputAdapter;
import controller.moveAdapter.MoveAdapter;
import controller.moveAdapter.RowColInputAdapter;
import model.player.ai.ArtificialPlayer;
import model.player.HumanPlayer;
import model.rule.Connect4Rule;
import model.rule.GomokuRule;
import model.rule.Rule;
import model.Board;
import model.Move;
import model.rule.TicTacToeRule;
import view.*;
import model.player.Player;
import model.player.factory.PlayerFactory;
import view.dictionary.GameChoice;
import view.dictionary.GameError;
import view.dictionary.GameMessage;
import view.dictionary.GameTitle;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the execution of a game by handling game rules, players, moves, and interactions with the display view.
 * The GameMaster class serves as the central controller for the game session, coordinating all components.
 */
public class GameMaster {
    protected final Rule rule;
    protected final View view;
    protected final PlayerFactory playerFactory;
    protected final MoveAdapter adapter;
    protected final GameTitle title;

    protected final Board board;
    protected List<Player> players;
    protected Player currentPlayer;
    protected final List<Move> movesHistory;

    /**
     * Constructs a new GameMaster instance that manages the game's execution.
     *
     * @param rule the ruleset that dictates the game logic, including valid moves and victory conditions
     * @param view the view used for displaying messages, boards, and receiving user input
     * @param title the title of the game being played
     */
    public GameMaster(Rule rule, View view, GameTitle title) {
        this.rule = rule;
        this.title = title;
        this.view = view;
        this.playerFactory = new PlayerFactory();
        this.adapter = createAdapterForRule(rule);
        this.board = rule.getInitialBoard();
        this.movesHistory = new ArrayList<>();
    }

    /**
     * Creates a specific {@link MoveAdapter} instance based on the type of the provided {@link Rule}.
     *
     * @param rule the game's rule, which determines the logic for valid moves and gameplay behavior
     * @return a {@link MoveAdapter} object appropriate for the given rule;
     *         returns {@link ColInputAdapter} if the rule is an instance of {@link Connect4Rule},
     *         and {@link RowColInputAdapter} for {@link GomokuRule} or {@link TicTacToeRule},
     *         or by default a {@link RowColInputAdapter}
     */
    private MoveAdapter createAdapterForRule(Rule rule) {
        if (rule instanceof Connect4Rule) {
            return new ColInputAdapter(view);
        } else if (rule instanceof GomokuRule || rule instanceof TicTacToeRule) {
            return new RowColInputAdapter(view);
        }
        // Par défaut
        return new RowColInputAdapter(view);
    }

    /**
     * Initiates the game sequence for the GameMaster.
     * Displays the game title and provides the player with an initial choice
     * to either start the game quickly with default settings or to open the settings menu.
     * <p>
     * Once the player's choice is processed, the main gameplay execution is handed
     * over to the play method.
     */
    public void start() {
        view.display(title);
        GameChoice choice = view.getChoice(GameMessage.WELCOME, List.of(GameChoice.QUICK_START, GameChoice.SETTINGS));
        switch (choice) {
            case QUICK_START -> initPlayers(1, rule.getDefaultNbPlayers() - 1);
            case SETTINGS -> menu();
        }
        play();
    }

    /**
     * Configures the game settings and initializes players.
     * <p>
     * This method allows the user to configure the game before it starts. It performs the following steps:
     * 1. Displays the settings menu in the view.
     * 2. Prompts the user to input the number of human players, with a minimum of 0 and maximum defined by the rule's default number of players.
     * 3. Computes the number of artificial players based on the default number of players and the number of human players.
     * 4. Allows the user to choose the board size by selecting an option from a predefined list (LITTLE or BIG).
     * 5. Configures the board size in the view based on the chosen option.
     * 6. Initializes players, including both human and artificial players.
     */
    protected void menu() {
        view.display(GameTitle.SETTINGS);
        int nbHumanPlayers = view.getInt(GameMessage.GET_NB_HUMAN_PLAYERS, 0, rule.getDefaultNbPlayers());
        int nbArtificialPlayers = rule.getDefaultNbPlayers() - nbHumanPlayers;
        GameChoice choice = view.getChoice(GameMessage.GET_BOARD_SIZE, List.of(GameChoice.LITTLE, GameChoice.BIG));
        view.setSize(choice);
        initPlayers(nbHumanPlayers, nbArtificialPlayers);
    }

    /**
     * Initializes the players for the game, creating both human and artificial players.
     * <p>
     * This method uses the player factory to generate the required number of players
     * based on the specified counts of human and artificial players.
     *
     * @param nbHumanPlayers      the number of human players to be initialized
     * @param nbArtificialPlayers the number of artificial players to be initialized
     */
    protected void initPlayers(int nbHumanPlayers, int nbArtificialPlayers) {
        this.players = playerFactory.createPlayers(nbHumanPlayers, nbArtificialPlayers);
    }

    /**
     * Executes the main gameplay loop for the game.
     * <p>
     * This method initiates the game by displaying the rules, determining the first player,
     * and initializing the gameplay loop. Within the loop, it handles player turns by
     * getting moves, validating them, and applying them to the board if valid.
     * If the move is invalid, an appropriate error message is displayed.
     * <p>
     * The loop continues until a game-over condition is detected. Upon completion,
     * it checks if the last move is a winning move or if the game ends in a draw.
     * The final game state, including the board and outcome message, is displayed.
     */
    protected void play() {
        view.display(rule.toString());
        currentPlayer = rule.getFirstPlayer(players);

        do {
            view.display(board);
            view.display(GameMessage.PLAYER_TURN, currentPlayer.render());
            Move move = getNextMove(currentPlayer);
            if (rule.isMoveValid(board, move)) {
                rule.playMove(board, move);
                movesHistory.add(move);
                currentPlayer = rule.getNextPlayer(currentPlayer, players);
            } else {
                view.display(GameError.INVALID_MOVE);
            }

        } while (!rule.isGameOver(board, movesHistory.getLast()));

        Move lastMove = movesHistory.getLast();
        if (rule.isMoveWinning(board, lastMove)) {
            displayWinningBoard();
            view.display(board);
            view.display(GameMessage.GAME_OVER_WIN, lastMove.getPlayer().render());
        } else {
            view.display(board);
            view.display(GameMessage.GAME_OVER_DRAW);
        }
    }

    /**
     * Determines the next move for the given player based on their type.
     * <p>
     * If the player is a human player, it fetches the move via the adapter from the human player.
     * If the player is an artificial player, it calculates the move using the AI associated
     * with the artificial player.
     *
     * @param player the player for whom the next move needs to be determined; it could be
     *               either a human player or an artificial player
     * @return a {@link Move} object containing the details of the player's next move;
     *         null if the player type is unsupported
     */
    private Move getNextMove(Player player) {
        if (player instanceof HumanPlayer) {
            return adapter.getMoveFromHumanPlayer(board, player);
        } else if (player instanceof ArtificialPlayer aiPlayer) {
            return adapter.getMoveFromAI(board, rule, player, players, aiPlayer.getAi());
        }
        return null;
    }

    /**
     * Highlights the cells on the board that are part of a winning sequence.
     * <p>
     * This method iterates through the game's move history and checks each move
     * to determine if it is part of the winning sequence, based on the game's rule set.
     * If a move is determined to be part of a winning sequence, the corresponding
     * board cell is highlighted.
     * <p>
     * The method relies on the following components:
     * - The move history, which contains all moves made during the game.
     * - The rule set, which determines whether a specific move constitutes a winning move.
     * - The board, which provides the ability to access and manipulate cells
     *   based on move coordinates.
     */
    private void displayWinningBoard() {
        for (Move move : movesHistory) {
            if (rule.isMoveWinning(board, move)) {
                board.getCell(move.getRow(), move.getCol()).highlight();
            }
        }
    }
}