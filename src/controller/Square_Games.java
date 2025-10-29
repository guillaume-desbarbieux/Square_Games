package controller;

import controller.persistence.GameSerialization;
import controller.persistence.Persistence;
import model.*;
import model.rule.CheckersRule;
import model.rule.Connect4Rule;
import model.rule.GomokuRule;
import model.rule.TicTacToeRule;
import view.*;
import view.cli.Cli;
import view.dictionary.*;

import java.util.List;

import static controller.EventType.*;
import static controller.GameState.*;
import static view.dictionary.GameChoice.GET_SAVES;

public class Square_Games implements MenuObserver {
    private final Viewable view;
    private final Persistence persist;
    private GameState gameState;
    private GameChoice currentChoice;
    private GameMaster gameMaster;

    public Square_Games() {
        this.view = new Cli(MAIN_MENU_CHOICE, GET_SAVE_INDEX, GAME_MENU_CHOICE);
        this.persist = new GameSerialization();
        ((MenuObservable) view).subscribe(this, MAIN_MENU_CHOICE);
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
            case GET_SAVE -> getSaves();
            case PLAY -> play();
            case QUIT -> quit();
        }
    }

    private void play() {
        ((MenuObservable) view).unsubscribe(this);
        view.display("C'est parti !!!");
        gameMaster.start();
        ((MenuObservable) view).subscribe(this);
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
       view.getChoice(EventType.MAIN_MENU_CHOICE, GameMessage.GET_GAME, List.of(GameChoice.TIC_TAC_TOE, GameChoice.GOMOKU, GameChoice.CONNECT4, GameChoice.CHECKERS, GameChoice.QUIT, GET_SAVES));
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
    public void onUpdate(EventType eventType, Generique param) {
        switch (eventType) {
            case MAIN_MENU_CHOICE -> treatMenuChoice(param);
            default -> System.err.println("Notification inconnue reçue : " + this + eventType + param);
        }
    }

    private void treatMenuChoice(Generique param) {
        if (param.param() instanceof GameChoice gameChoice)
            switch (gameChoice) {
                case QUIT -> gameState = QUIT;
                case GET_SAVES -> gameState = GameState.GET_SAVE;
                case TIC_TAC_TOE, CONNECT4, CHECKERS, GOMOKU -> {
                    currentChoice = gameChoice;
                    gameState = INIT_GAME;
                }
                default -> view.display(GameError.INVALID_CHOICE);
            }
        stateMachine();
    }
}