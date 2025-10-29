package controller;

import view.dictionary.GameChoice;

public interface MenuObserver {
    void onGameChoiceAsked(GameChoice gameChoice);
}
