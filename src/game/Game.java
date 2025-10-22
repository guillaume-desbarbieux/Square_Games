package game;

import board.Board;
import move.Move;
import move.factory.MoveAdapter;
import ui.InteractionUser;
import ui.View;
import player.Player;
import player.factory.PlayerFactory;

import java.util.ArrayList;
import java.util.List;

public class Game {
    protected final Rule rule;
    protected final View view;
    protected final InteractionUser interact;
    protected final PlayerFactory playerFactory;
    protected final MoveAdapter adapter;

    protected final Board board;
    protected List<Player> players;
    protected Player currentPlayer;
    protected final List<Move> movesHistory;


    public Game(Rule rule) {
        this.rule = rule;
        this.view = View.getInstance();
        this.interact = InteractionUser.getInstance();
        this.playerFactory = new PlayerFactory();
        this.adapter = rule.getAdapter();
        this.board = rule.getInitialBoard();
        this.movesHistory = new ArrayList<>();
    }

    public void start() {
        view.displayTitle(rule.getName());
        int choice = interact.getChoice("Bienvenue !", new String[]{"Partie Rapide", "Paramètres avancés"});
        if (choice == 1) {
            initPlayers(1, rule.getDefaultNbPlayers() - 1);
        } else {
            menu();
        }
        play();
    }

    protected void menu() {
        view.displayTitle("Menu Principal");
        int nbHumanPlayers = interact.getInt("nb Joueurs Humains", 0, rule.getDefaultNbPlayers());
        int nbArtificialPlayers = rule.getDefaultNbPlayers() - nbHumanPlayers;
        int choice = interact.getChoice("Affichage du plateau", new String[]{"Petit", "Grand"});
        view.setMaximize(choice == 2);
        initPlayers(nbHumanPlayers, nbArtificialPlayers);
    }

    protected void initPlayers(int nbHumanPlayers, int nbArtificialPlayers) {
        this.players = playerFactory.createPlayers(nbHumanPlayers, nbArtificialPlayers);
    }

    protected void play() {
        view.display(rule.toString());
        currentPlayer = rule.getFirstPlayer(players);

        do {
            view.displayBoard(board);
            view.display("=== Joueur " + currentPlayer.render() + " ===");
            Move move = currentPlayer.getNextMove(board, rule, adapter, players);
            if (rule.isMoveValid(board, move)) {
                rule.playMove(board, move);
                movesHistory.add(move);
                currentPlayer = rule.getNextPlayer(currentPlayer, players);
            } else {
                view.displayError("Coup invalide");
            }

        } while (!rule.isGameOver(board, movesHistory.getLast()));

        Move lastMove = movesHistory.getLast();
        if (rule.isMoveWinning(board, lastMove)) {
            displayWinningBoard();
            view.displayBoard(board);
            view.display("Victoire du joueur " + lastMove.getPlayer().render());
        } else {
            view.displayBoard(board);
            view.display("Match Nul");
        }
    }

    private void displayWinningBoard() {
        for (Move move : movesHistory) {
            if (rule.isMoveWinning(board, move)) {
                board.getCell(move.getRow(), move.getCol()).highlight();
            }
        }
    }
}