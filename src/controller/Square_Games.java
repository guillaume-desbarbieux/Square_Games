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
public class Square_Games {
    private final Viewable view;
    private final Persistence persist;

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
    }

    public void start() {
        GameChoice choice = null;

        while (choice != GameChoice.QUIT) {
            view.display(GameTitle.SQUARE_GAMES);
            choice = view.getChoice(GameMessage.GET_GAME, List.of(GameChoice.TIC_TAC_TOE, GameChoice.GOMOKU, GameChoice.CONNECT4, GameChoice.CHECKERS, GameChoice.QUIT, GameChoice.SAVES));

            GameMaster gameMaster = switch (choice) {
                case TIC_TAC_TOE -> new GameMaster(new TicTacToeRule(), view, GameTitle.TIC_TAC_TOE, persist);
                case GOMOKU -> new GameMaster(new GomokuRule(), view, GameTitle.GOMOKU, persist);
                case CONNECT4 -> new GameMaster(new Connect4Rule(), view, GameTitle.CONNECT4, persist);
                case CHECKERS -> new GameMaster(new CheckersRule(), view, GameTitle.CHECKERS, persist);
                case SAVES -> getSaves();
                default -> null;
            };

            if (gameMaster != null)
                gameMaster.start();
        }
        view.display(GameMessage.SEE_YOU);
    }

    private GameMaster getSaves() {
        List<GameSave> saves = persist.getSaves();

        if (saves.isEmpty()) {
            view.display(GameError.NO_SAVED_GAMES);
            return null;
        }

        view.display("0 : Annuler");
        for (int i = 1; i <= saves.size(); i++)
            view.display(i + " : " + saves.get(i - 1).toString());

        int choice = view.getInt(GameMessage.GET_CHOICE, 0, saves.size());

        if (choice == 0)
            return null;

        GameSave save = saves.get(choice - 1);
        return new GameMaster(view, persist, save);
    }
}