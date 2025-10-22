package controller;

import controller.moveAdapter.ColInputAdapter;
import controller.moveAdapter.MoveAdapter;
import controller.moveAdapter.RowColInputAdapter;
import model.player.ai.ArtificialPlayer;
import model.player.HumanPlayer;
import model.rule.Connect4Rule;
import model.rule.GomokuRule;
import model.rule.Rule;
import model.Board;
import model.Move;
import model.rule.TicTacToeRule;
import view.View;
import model.player.Player;
import model.player.factory.PlayerFactory;

import java.util.ArrayList;
import java.util.List;

public class Game {
    protected final Rule rule;
    protected final View view;
    protected final PlayerFactory playerFactory;
    protected final MoveAdapter adapter;

    protected final Board board;
    protected List<Player> players;
    protected Player currentPlayer;
    protected final List<Move> movesHistory;


    public Game(Rule rule) {
        this.rule = rule;
        this.view = View.getInstance();
        this.playerFactory = new PlayerFactory();
        this.adapter = createAdapterForRule(rule);
        this.board = rule.getInitialBoard();
        this.movesHistory = new ArrayList<>();
    }

    private MoveAdapter createAdapterForRule(Rule rule) {
        if (rule instanceof Connect4Rule) {
            return new ColInputAdapter();
        } else if (rule instanceof GomokuRule || rule instanceof TicTacToeRule) {
            return new RowColInputAdapter();
        }
        // Par défaut
        return new RowColInputAdapter();
    }

    public void start() {
        view.displayTitle(rule.getName());
        int choice = view.getChoice("Bienvenue !", new String[]{"Partie Rapide", "Paramètres avancés"});
        if (choice == 1) {
            initPlayers(1, rule.getDefaultNbPlayers() - 1);
        } else {
            menu();
        }
        play();
    }

    protected void menu() {
        view.displayTitle("Menu Principal");
        int nbHumanPlayers = view.getInt("nb Joueurs Humains", 0, rule.getDefaultNbPlayers());
        int nbArtificialPlayers = rule.getDefaultNbPlayers() - nbHumanPlayers;
        int choice = view.getChoice("Affichage du plateau", new String[]{"Petit", "Grand"});
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
            Move move = getNextMove(currentPlayer);
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

    private Move getNextMove(Player player) {
        if (player instanceof HumanPlayer) {
            return adapter.getMoveFromHumanPlayer(board, player);
        } else if (player instanceof ArtificialPlayer aiPlayer) {
            return adapter.getMoveFromAI(board, rule, player, players, aiPlayer.getAi());
        }
        throw new IllegalStateException("Type de joueur inconnu");
    }

    private void displayWinningBoard() {
        for (Move move : movesHistory) {
            if (rule.isMoveWinning(board, move)) {
                board.getCell(move.getRow(), move.getCol()).highlight();
            }
        }
    }
}