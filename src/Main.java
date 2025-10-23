import controller.Game;
import controller.GameChoice;
import controller.GameMessage;
import controller.GameTitle;
import model.rule.Connect4Rule;
import model.rule.GomokuRule;
import model.rule.TicTacToeRule;
import view.View;
import view.cli.Cli;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        View view = new Cli();
        GameChoice choice = null;

        while (choice != GameChoice.QUIT) {
            view.display(GameTitle.SQUARE_GAMES);
            choice = view.getChoice(GameMessage.GET_GAME, List.of(GameChoice.TIC_TAC_TOE, GameChoice.GOMOKU, GameChoice.CONNECT4, GameChoice.QUIT));

            switch (choice) {
                case TIC_TAC_TOE -> new Game(new TicTacToeRule(), view).start();
                case GOMOKU -> new Game(new GomokuRule(), view).start();
                case CONNECT4 -> new Game(new Connect4Rule(), view).start();
            }
        }
        view.display(GameMessage.SEE_YOU);
    }
}