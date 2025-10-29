package controller;

public interface MenuObserver {
    void onSaveGame();
    void onChoiceGame(String gameChoice);
    void onExitGame();
}
