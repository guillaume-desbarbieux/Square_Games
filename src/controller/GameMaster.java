package controller;

import controller.moveAdapter.ColInputAdapter;
import controller.moveAdapter.MoveAdapter;
import controller.moveAdapter.RowColInputAdapter;
import model.Cell;
import model.Rule;
import model.player.ai.ArtificialPlayer;
import model.player.HumanPlayer;
import model.rule.*;
import model.Board;
import model.Move;
import view.*;
import model.player.Player;
import model.player.factory.PlayerFactory;
import view.dictionary.GameChoice;
import view.dictionary.GameError;
import view.dictionary.GameMessage;
import view.dictionary.GameTitle;

import java.util.ArrayList;
import java.util.List;

import static controller.GameState.*;

/**
 * The GameMaster class manages the flow of a game using the provided rule set,
 * user interface view, and player configurations. It serves as the main controller
 * that orchestrates game initialization, player turns, and win/draw logic while ensuring
 * adherence to game rules.
 */
public class GameMaster {
    private final Rule rule;
    private final Viewable view;
    private final PlayerFactory playerFactory;
    private final MoveAdapter adapter;
    private final GameTitle title;
    private GameState gameState;

    private final Board board;
    private List<Player> players;
    private List<Integer> listIds;
    private Player currentPlayer;
    private Move currentMove;

    private final List<Move> movesHistory;
    private List<String> representations;
    private List<String> highlights;


    /**
     * Constructs a new GameMaster instance that manages the game's execution.
     *
     * @param rule  the ruleset that dictates the game logic, including valid moves and victory conditions
     * @param view  the view used for displaying messages, boards, and receiving user input
     * @param title the title of the game being played
     */
    public GameMaster(Rule rule, Viewable view, GameTitle title) {
        this.rule = rule;
        this.title = title;
        this.view = view;
        this.playerFactory = new PlayerFactory();
        this.adapter = createAdapterForRule(rule);
        this.board = rule.getInitialBoard();
        this.movesHistory = new ArrayList<>();
    }

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
     * Starts the game execution by initializing the game's state and invoking the state machine mechanism.
     */
    public void start() {
        this.gameState = GameState.WELCOME;
        stateMachine();
    }

    private void stateMachine() {
        switch (gameState) {
            case WELCOME -> welcome();
            case QUICK_START -> initPlayers(1, rule.getDefaultNbPlayers() - 1);
            case SETTINGS -> settings();
            case INIT_GAME -> initGame();
            case TURN -> askForMove();
            case PLAY_MOVE -> playMove();
            case CHECK_IF_ENDED -> checkIfEnded();
            case NEXT_PLAYER -> getNextPlayer();
            case WIN -> gameWon();
            case DRAW -> gameDraw();
            case QUIT -> quit();
        }
        if (gameState != QUIT)
            stateMachine();
    }

    private void quit() {
        view.display(GameMessage.SEE_YOU);
    }

    private void welcome() {
        view.display(title);
        GameChoice choice = view.getChoice(GameMessage.WELCOME, List.of(GameChoice.QUICK_START, GameChoice.SETTINGS));
        switch (choice) {
            case QUICK_START -> gameState = QUICK_START;
            case SETTINGS -> gameState = SETTINGS;
        }
    }

    private void settings() {
        view.display(GameTitle.SETTINGS);
        int nbHumanPlayers = view.getInt(GameMessage.GET_NB_HUMAN_PLAYERS, 0, rule.getDefaultNbPlayers());
        int nbArtificialPlayers = rule.getDefaultNbPlayers() - nbHumanPlayers;
        GameChoice choice = view.getChoice(GameMessage.GET_BOARD_SIZE, List.of(GameChoice.LITTLE, GameChoice.BIG));
        view.setSize(choice);
        initPlayers(nbHumanPlayers, nbArtificialPlayers);
        gameState = INIT_GAME;
    }

    private void initPlayers(int nbHumanPlayers, int nbArtificialPlayers) {
        players = playerFactory.createPlayers(nbHumanPlayers, nbArtificialPlayers);
        representations = new ArrayList<>();
        highlights = new ArrayList<>();
        listIds = new ArrayList<>();
        for (Player player : players) {
            representations.add(player.getRepresentation().render(false));
            highlights.add(player.getRepresentation().render(true));
            listIds.add(player.getId());
        }
        gameState = INIT_GAME;
    }

    private void initGame() {
        view.display(rule.toString());
        currentPlayer = players.get(rule.getFirstPlayerId(listIds));
        gameState = TURN;
    }

    private void askForMove() {
        view.display(board, representations, highlights);
        view.display(GameMessage.PLAYER_TURN, currentPlayer.render());
        Move move = getNextMove(currentPlayer);

        if (rule.isMoveValid(board, move)) {
            currentMove = move;
            gameState = PLAY_MOVE;
        } else
            view.display(GameError.INVALID_MOVE);
    }

    private void playMove() {
        rule.playMove(board, currentMove);
        if (!movesHistory.isEmpty()) {
            Move lastMove = movesHistory.getLast();
            board.highlight(board.getCell(lastMove.getRow(), lastMove.getCol()), false);
        }
        board.highlight(board.getCell(currentMove.getRow(), currentMove.getCol()), true);
        movesHistory.add(currentMove);
        gameState = CHECK_IF_ENDED;
    }

    private void checkIfEnded() {
        if (rule.isMoveWinning(board, currentMove))
            gameState = WIN;
        else if (rule.isBoardFull(board))
            gameState = DRAW;
        else
            gameState = NEXT_PLAYER;
    }

    private void getNextPlayer() {
        currentPlayer = players.get(rule.getNextPlayerId(currentPlayer.getId(), listIds));
        gameState = TURN;
    }


    private void gameDraw() {
        view.display(board, representations, highlights);
        view.display(GameMessage.GAME_OVER_DRAW);
        gameState = QUIT;
    }

    private void gameWon() {
        if (rule instanceof AlignementGameRule agRule) {
            List<Cell> winningCells = agRule.getWinningCells(movesHistory, board);
            board.highlight(winningCells);
        }
        view.display(board, representations, highlights);
        String representation = players.get(currentMove.getPlayerId()).render();
        view.display(GameMessage.GAME_OVER_WIN, representation);

        gameState = QUIT;
    }

    private Move getNextMove(Player player) {
        if (player instanceof HumanPlayer) {
            return adapter.getMoveFromHumanPlayer(board, player);
        } else if (player instanceof ArtificialPlayer aiPlayer) {
            return adapter.getMoveFromAI(board, rule, player, players, aiPlayer.getAi());
        }
        return null;
    }
}