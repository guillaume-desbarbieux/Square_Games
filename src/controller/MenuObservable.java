package controller;

import view.dictionary.GameChoice;

public interface MenuObservable {
    void addMenuObserver(MenuObserver menuObserver);
    void removeMenuObserver(MenuObserver menuObserver);
    void notifySaveGame();
    void notifyChoiceGame(String gameChoice);
    void notifyExitGame();
    void notify(String input);
}
