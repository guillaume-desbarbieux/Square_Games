package controller;

import view.dictionary.GameChoice;

public interface MenuObservable {
    void addMenuObserver(MenuObserver menuObserver);
    void removeMenuObserver(MenuObserver menuObserver);

    void notifyGameChoiceAsked(GameChoice gameChoice);
}