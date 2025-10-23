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
import view.*;
import model.player.Player;
import model.player.factory.PlayerFactory;
import view.dictionary.GameChoice;
import view.dictionary.GameError;
import view.dictionary.GameMessage;
import view.dictionary.GameTitle;

import java.util.ArrayList;
import java.util.List;

public class GameMaster {
    protected final Rule rule;
    protected final View view;
    protected final PlayerFactory playerFactory;
    protected final MoveAdapter adapter;
    protected final GameTitle title;

    protected final Board board;
    protected List<Player> players;
    protected Player currentPlayer;
    protected final List<Move> movesHistory;


    public GameMaster(Rule rule, View view, GameTitle title) {
        this.rule = rule;
        this.title = title;
        this.view = view;
        this.playerFactory = new PlayerFactory();
        this.adapter = createAdapterForRule(rule);
        this.board = rule.getInitialBoard();
        this.movesHistory = new ArrayList<>();
    }

    private MoveAdapter createAdapterForRule(Rule rule) {
        if (rule instanceof Connect4Rule) {
            return new ColInputAdapter(view);
        } else if (rule instanceof GomokuRule || rule instanceof TicTacToeRule) {
            return new RowColInputAdapter(view);
        }
        // Par défaut
        return new RowColInputAdapter(view);
    }

    public void start() {
        view.display(title);
        GameChoice choice = view.getChoice(GameMessage.WELCOME, List.of(GameChoice.QUICK_START, GameChoice.SETTINGS));
        switch (choice) {
            case QUICK_START -> initPlayers(1, rule.getDefaultNbPlayers() - 1);
            case SETTINGS -> menu();
        }
        play();
    }

    protected void menu() {
        view.display(GameTitle.SETTINGS);
        int nbHumanPlayers = view.getInt(GameMessage.GET_NB_HUMAN_PLAYERS, 0, rule.getDefaultNbPlayers());
        int nbArtificialPlayers = rule.getDefaultNbPlayers() - nbHumanPlayers;
        GameChoice choice = view.getChoice(GameMessage.GET_BOARD_SIZE, List.of(GameChoice.LITTLE, GameChoice.BIG));
        view.setSize(choice);
        initPlayers(nbHumanPlayers, nbArtificialPlayers);
    }

    protected void initPlayers(int nbHumanPlayers, int nbArtificialPlayers) {
        this.players = playerFactory.createPlayers(nbHumanPlayers, nbArtificialPlayers);
    }

    protected void play() {
        view.display(rule.toString());
        currentPlayer = rule.getFirstPlayer(players);

        do {
            view.display(board);
            view.display(GameMessage.PLAYER_TURN, currentPlayer.render());
            Move move = getNextMove(currentPlayer);
            if (rule.isMoveValid(board, move)) {
                rule.playMove(board, move);
                movesHistory.add(move);
                currentPlayer = rule.getNextPlayer(currentPlayer, players);
            } else {
                view.display(GameError.INVALID_MOVE);
            }

        } while (!rule.isGameOver(board, movesHistory.getLast()));

        Move lastMove = movesHistory.getLast();
        if (rule.isMoveWinning(board, lastMove)) {
            displayWinningBoard();
            view.display(board);
            view.display(GameMessage.GAME_OVER_WIN, lastMove.getPlayer().render());
        } else {
            view.display(board);
            view.display(GameMessage.GAME_OVER_DRAW);
        }
    }

    private Move getNextMove(Player player) {
        if (player instanceof HumanPlayer) {
            return adapter.getMoveFromHumanPlayer(board, player);
        } else if (player instanceof ArtificialPlayer aiPlayer) {
            return adapter.getMoveFromAI(board, rule, player, players, aiPlayer.getAi());
        }
        return null;
    }

    private void displayWinningBoard() {
        for (Move move : movesHistory) {
            if (rule.isMoveWinning(board, move)) {
                board.getCell(move.getRow(), move.getCol()).highlight();
            }
        }
    }
}