package controller;

import model.MoveStrategy;

import model.player.Player;


public interface GameMasterStrategy {

    void setAdapter();

    void start();

    void stateMachine();

    void quit();

    void welcome();

    void settings();

    void initPlayers(int nbHumanPlayers, int nbArtificialPlayers);

    void initGame();

    void askForMove();

    void playMove();

    void checkIfEnded();

    void getNextPlayer();

    void gameDraw();

    void gameWon();

    MoveStrategy getNextMove(Player player);
}
