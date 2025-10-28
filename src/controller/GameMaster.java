package controller;

import controller.moveAdapter.ColInputAdapter;
import controller.moveAdapter.ComplexMoveAdapter;
import controller.moveAdapter.MoveAdapter;
import controller.moveAdapter.RowColInputAdapter;
import controller.persistence.Persistence;
import model.*;
import model.move.ComplexMove;
import model.player.ai.ArtificialPlayer;
import model.player.HumanPlayer;
import model.rule.*;
import view.*;
import model.player.Player;
import model.player.factory.PlayerFactory;
import view.dictionary.GameChoice;
import view.dictionary.GameError;
import view.dictionary.GameMessage;
import view.dictionary.GameTitle;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static controller.GameState.*;

/**
 * The GameMaster class manages the flow of a game using the provided rule set,
 * user interface view, and player configurations. It serves as the main controller
 * that orchestrates game initialization, player turns, and win/draw logic while ensuring
 * adherence to game rules.
 */
public class GameMaster implements GameMasterStrategy, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String id;

    private final RulableStrategy rule;
    private transient Viewable view;
    private final PlayerFactory playerFactory;
    private transient MoveAdapter adapter;
    private final GameTitle title;
    private GameState gameState;

    private Board board;
    private List<Player> players;
    private List<Integer> listIds;
    private Player currentPlayer;
    private MoveStrategy currentMove;

    private final List<MoveStrategy> movesHistory;
    private List<String> representations;
    private List<String> highlights;
    private transient Persistence persist;


    /**
     * Constructs a new GameMaster instance that manages the game's execution.
     *
     * @param rule  the ruleset that dictates the game logic, including valid moves and victory conditions
     * @param view  the view used for displaying messages, boards, and receiving user input
     * @param title the title of the game being played
     */
    public GameMaster(RulableStrategy rule, Viewable view, GameTitle title, Persistence persist) {
        this.id = UUID.randomUUID().toString();
        this.rule = rule;
        this.title = title;
        this.view = view;
        this.playerFactory = new PlayerFactory();
        setAdapter();
        this.movesHistory = new ArrayList<>();
        this.persist = persist;
        this.gameState = null;
    }

    public GameMaster(Viewable view, Persistence persist, GameSave save) {
        this.id = save.id();
        this.title = save.title();
        this.players = save.players();
        representations = new ArrayList<>();
        highlights = new ArrayList<>();
        listIds = new ArrayList<>();
        for (Player player : players) {
            representations.add(player.getRepresentation().render(false));
            highlights.add(player.getRepresentation().render(true));
            listIds.add(player.getId());
        }
        this.movesHistory = save.moveHistory();
        this.rule = switch (title) {
            case CHECKERS -> new CheckersRule(listIds.get(0), listIds.get(1), (ComplexMove) movesHistory.getLast());
            case TIC_TAC_TOE -> new TicTacToeRule();
            case GOMOKU -> new GomokuRule();
            case CONNECT4 -> new Connect4Rule();
            default -> null;
        };
        this.view = view;
        this.playerFactory = new PlayerFactory();
        setAdapter();
        this.gameState = save.gameState();
        setBoard(movesHistory);
        this.currentPlayer = players.get(save.currentPlayerId());
        this.currentMove = save.currentMove();

        this.persist = persist;
    }

    private void setBoard(List<MoveStrategy> movesHistory) {
        this.board = rule.getInitialBoard();
        for (MoveStrategy move : movesHistory)
            rule.playMove(board, move);

    }

    public String getId() {
        return id;
    }

    public long getSerialVersionUID() {
        return serialVersionUID;
    }

    public void reload(Persistence persist, Viewable view) {
        this.persist = persist;
        this.view = view;
        setAdapter();
    }

    public void setAdapter() {
        if (rule instanceof Connect4Rule) {
            this.adapter = new ColInputAdapter(view);
        } else if (rule instanceof GomokuRule || rule instanceof TicTacToeRule) {
            this.adapter = new RowColInputAdapter(view);
        } else if (rule instanceof CheckersRule)
            this.adapter = new ComplexMoveAdapter(view);
    }

    /**
     * Starts the game execution by initializing the game's state and invoking the state machine mechanism.
     */
    public void start() {
        if (gameState == null)
            gameState = GameState.WELCOME;
        stateMachine();
    }

    public void stateMachine() {
        // if (persist.saveGame(this))
        //     view.display("Partie sauvegardée - " + gameState);

        if (saveGame())
            view.display("Partie sauvegardée - " + gameState);

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

    private boolean saveGame() {
        GameSave save = new GameSave(id, title, gameState, players, currentPlayer == null ? -1 : currentPlayer.getId(), movesHistory, currentMove);
        return persist.saveGame(save);
    }

    public void quit() {
        view.display(GameMessage.SEE_YOU);
    }

    public void welcome() {
        view.display(title);
        GameChoice choice = view.getChoice(GameMessage.WELCOME, List.of(GameChoice.QUICK_START, GameChoice.SETTINGS));
        switch (choice) {
            case QUICK_START -> gameState = QUICK_START;
            case SETTINGS -> gameState = SETTINGS;
        }
    }

    public void settings() {
        view.display(GameTitle.SETTINGS);
        int nbHumanPlayers = view.getInt(GameMessage.GET_NB_HUMAN_PLAYERS, 0, rule.getDefaultNbPlayers());
        int nbArtificialPlayers = rule.getDefaultNbPlayers() - nbHumanPlayers;
        GameChoice choice = view.getChoice(GameMessage.GET_BOARD_SIZE, List.of(GameChoice.LITTLE, GameChoice.BIG));
        view.setSize(choice);
        initPlayers(nbHumanPlayers, nbArtificialPlayers);
        gameState = INIT_GAME;
    }

    public void initPlayers(int nbHumanPlayers, int nbArtificialPlayers) {
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

    public void initGame() {
        view.display(rule.toString());
        currentPlayer = players.get(rule.getFirstPlayerId(listIds));
        this.board = rule.getInitialBoard();
        gameState = TURN;
    }

    public void askForMove() {
        view.display(board, representations, highlights);
        view.display(GameMessage.PLAYER_TURN, currentPlayer.render());
        List<MoveStrategy> validMoves = rule.getValidMoves(board, currentPlayer.getId());
        for (MoveStrategy move : validMoves) {
            view.display(move.toString());
        }
        MoveStrategy move = getNextMove(currentPlayer);

        if (rule.isMoveValid(board, move)) {
            currentMove = move;
            gameState = PLAY_MOVE;
        } else
            view.display(GameError.INVALID_MOVE);
    }

    public void playMove() {
        rule.playMove(board, currentMove);
        movesHistory.add(currentMove);
        gameState = CHECK_IF_ENDED;

    }

    public void checkIfEnded() {
        if (rule.isMoveWinning(board, currentMove))
            gameState = WIN;
        else if (rule.isGameDraw(board))
            gameState = DRAW;
        else
            gameState = NEXT_PLAYER;
    }

    public void getNextPlayer() {
        currentPlayer = players.get(rule.getNextPlayerId(board, movesHistory.getLast(), listIds));
        gameState = TURN;
    }


    public void gameDraw() {
        view.display(board, representations, highlights);
        view.display(GameMessage.GAME_OVER_DRAW);
        gameState = QUIT;
    }

    public void gameWon() {
        if (rule instanceof AlignementGameRule agRule) {
            List<Cell> winningCells = agRule.getWinningCells(movesHistory, board);
            board.highlight(winningCells);
        }
        view.display(board, representations, highlights);
        String representation = players.get(currentMove.getPlayerId()).render();
        view.display(GameMessage.GAME_OVER_WIN, representation);

        gameState = QUIT;
    }

    public MoveStrategy getNextMove(Player player) {
        if (player instanceof HumanPlayer) {
            return adapter.getMoveFromHumanPlayer(board, player);
        } else if (player instanceof ArtificialPlayer aiPlayer) {
            return adapter.getMoveFromAI(board, rule, player, players, aiPlayer.getAi());
        }
        return null;
    }

    @Override
    public String toString() {
        return (this.title + " (" + gameState + ")");
    }
}