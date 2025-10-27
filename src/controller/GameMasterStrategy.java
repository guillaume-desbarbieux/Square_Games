package controller;

import controller.moveAdapter.MoveAdapter;
import model.Move;
import model.Rulable;

import model.player.Player;


public interface GameMasterStrategy {

    MoveAdapter createAdapterForRule(Rulable rule);

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

    Move getNextMove(Player player);
}
