package controller;

import controller.persistence.GameSerialization;
import controller.persistence.Persistence;
import model.GameSave;
import model.rule.CheckersRule;
import model.rule.Connect4Rule;
import model.rule.GomokuRule;
import model.rule.TicTacToeRule;
import view.Viewable;
import view.cli.Cli;
import view.dictionary.GameChoice;
import view.dictionary.GameError;
import view.dictionary.GameMessage;
import view.dictionary.GameTitle;

import java.util.List;

import static controller.GameState.*;
import static view.dictionary.GameChoice.SAVES;

/**
 * The Square_Games class serves as the main entry point for running and managing
 * a variety of board games such as Tic Tac Toe, Gomoku, and Connect4. The class
 * presents an interactive command-line interface (CLI) that allows users to select
 * and play their desired game. The user can choose to play one of the available
 * games or exit the application.
 * <p>
 * Responsibilities of this class include:
 * - Displaying the game menu and title screen using a View implementation (CLI).
 * - Allowing users to select a game from a list of available options.
 * - Initializing and starting the selected game using the appropriate game rules.
 * - Providing the ability to quit the application through the menu.
 * <p>
 * Each game is managed by the GameMaster class, which handles the game logic and player interactions.
 */
public class Square_Games implements MenuObserver {
    private final Viewable view;
    private final Persistence persist;
    private GameState gameState;
    private GameChoice currentChoice;
    private GameMaster gameMaster;

    /**
     * Constructs a new instance of the Square_Games class.
     * <p>
     * This constructor initializes the Square_Games instance with a default command-line interface (CLI)
     * implementation for the View. The CLI is responsible for displaying messages to the user and managing
     * their input during the gameplay. The created instance serves as the primary interface for starting and
     * managing available board games provided by the application.
     */
    public Square_Games() {
        this.view = new Cli();
        this.persist = new GameSerialization();
        ((MenuObservable) view).addMenuObserver(this);
    }

    public void start() {
        if (gameState == null)
            gameState = GameState.WELCOME;
        stateMachine();
    }

    private void stateMachine() {
        switch (gameState) {
            case WELCOME -> welcome();
            case INIT_GAME -> initGame();
            case SAVES -> getSaves();
            case PLAY -> play();
            case QUIT -> quit();
        }
    }

    private void play() {
        ((MenuObservable) view).removeMenuObserver(this);
        view.display("C'est parti !!!");
        gameMaster.start();
        ((MenuObservable) view).addMenuObserver(this);
        gameState = WELCOME;
        stateMachine();
    }

    private void quit() {
        view.display(GameMessage.SEE_YOU);
    }

    private void initGame() {
        gameMaster = switch (currentChoice) {
            case CHECKERS -> new GameMaster(new CheckersRule(), view, GameTitle.CHECKERS, persist);
            case CONNECT4 -> new GameMaster(new Connect4Rule(), view, GameTitle.CONNECT4, persist);
            case GOMOKU -> new GameMaster(new GomokuRule(), view, GameTitle.GOMOKU, persist);
            case TIC_TAC_TOE -> new GameMaster(new TicTacToeRule(), view, GameTitle.TIC_TAC_TOE, persist);
            default -> null;
        };

        if (gameMaster != null)
            gameState = PLAY;
        stateMachine();
    }

    private void welcome() {
        view.display(GameTitle.SQUARE_GAMES);
        view.getChoice(GameMessage.GET_GAME, List.of(GameChoice.TIC_TAC_TOE, GameChoice.GOMOKU, GameChoice.CONNECT4, GameChoice.CHECKERS, GameChoice.QUIT, SAVES));
        stateMachine();
    }

    private void getSaves() {
        List<GameSave> saves = persist.getSaves();

        if (saves.isEmpty())
            view.display(GameError.NO_SAVED_GAMES);

        view.display("0 : Annuler");
        for (int i = 1; i <= saves.size(); i++)
            view.display(i + " : " + saves.get(i - 1).toString());

        int choice = view.getInt(GameMessage.GET_CHOICE, 0, saves.size());
        if (choice == 0)
            gameState = WELCOME;
        else {
            gameMaster = new GameMaster(view, persist, saves.get(choice - 1));
            gameState = PLAY;
        }
        stateMachine();
    }

    @Override
    public void onGameChoiceAsked(GameChoice gameChoice) {
        currentChoice = gameChoice;
        if (currentChoice == GameChoice.QUIT)
            gameState = QUIT;
        else if (currentChoice == SAVES)
            gameState = GameState.SAVES;
        else
            gameState = INIT_GAME;
        stateMachine();
    }
}