package controller;

import model.rule.Connect4Rule;
import model.rule.GomokuRule;
import model.rule.TicTacToeRule;
import view.Viewable;
import view.cli.Cli;
import view.dictionary.GameChoice;
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

    /**
     * Constructs a new instance of the Square_Games class.
     * <p>
     * This constructor initializes the Square_Games instance with a default command-line interface (CLI)
     * implementation for the View. The CLI is responsible for displaying messages to the user and managing
     * their input during the gameplay. The created instance serves as the primary interface for starting and
     * managing available board games provided by the application.
     */
    public Square_Games(){
        this.view = new Cli();
    }

    /**
     * Starts the interactive game selection and management process for the Square_Games application.
     * <p>
     * This method displays the main game menu and allows the user to select one of the available games
     * (Tic Tac Toe, Gomoku, or Connect4) or exit the application. Upon selecting a game, a new instance
     * of GameMaster is created with the appropriate game rules, and the selected game is started.
     * The menu continues to be displayed until the user chooses to quit by selecting the "QUIT" option.
     * <p>
     * Behavior:
     * - Displays the game title screen and menu using the provided View instance.
     * - Prompts the user to choose one of the available options: a game to play or to "QUIT".
     * - Runs the selected game by instantiating a GameMaster with the corresponding game rules.
     * - Ensures the application exits cleanly by displaying a farewell message when the user chooses to quit.
     * <p>
     * Displayed Games and Options:
     * - Tic Tac Toe
     * - Gomoku
     * - Connect4
     * - Quit
     * <p>
     * Upon quitting, the method displays a goodbye message to the user indicating the end of the application.
     */
    public void start() {
        GameChoice choice = null;

        while (choice != GameChoice.QUIT) {
            view.display(GameTitle.SQUARE_GAMES);
            choice = view.getChoice(GameMessage.GET_GAME, List.of(GameChoice.TIC_TAC_TOE, GameChoice.GOMOKU, GameChoice.CONNECT4, GameChoice.QUIT));

            switch (choice) {
                case TIC_TAC_TOE -> new GameMaster(new TicTacToeRule(), view, GameTitle.TIC_TAC_TOE).start();
                case GOMOKU -> new GameMaster(new GomokuRule(), view, GameTitle.GOMOKU).start();
                case CONNECT4 -> new GameMaster(new Connect4Rule(), view, GameTitle.CONNECT4).start();
            }
        }
        view.display(GameMessage.SEE_YOU);
    }
}