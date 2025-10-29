package controller;

import view.dictionary.GameChoice;

import java.util.ArrayList;
import java.util.List;

public class UserListener implements MenuObservable {
    private final List<MenuObserver> observers;

    public UserListener() {
        this.observers = new ArrayList<>();
    }

    @Override
    public void addMenuObserver(MenuObserver menuObserver) {
        observers.add(menuObserver);
    }

    @Override
    public void removeMenuObserver(MenuObserver menuObserver) {
        observers.remove(menuObserver);
    }

    @Override
    public void notifySaveGame() {
        for (MenuObserver menuObserver : observers) {
            menuObserver.onSaveGame();
        }
    }

    @Override
    public void notifyChoiceGame(String gameChoice) {
        for (MenuObserver menuObserver : observers) {
            menuObserver.onChoiceGame(gameChoice);
        }
    }

    @Override
    public void notifyExitGame() {
        for (MenuObserver menuObserver : observers) {
            menuObserver.onExitGame();
        }
    }

    @Override
    public void notify(String input) {
        switch (input) {
            case "Save":
                notifySaveGame();
                break;
            case "Exit":
                notifyExitGame();
                break;
            default:
                System.out.println(input);
        }
    }


}

