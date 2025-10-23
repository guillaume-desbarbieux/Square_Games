import controller.GameMaster;
import view.dictionary.GameChoice;
import view.dictionary.GameMessage;
import view.dictionary.GameTitle;
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
                case TIC_TAC_TOE -> new GameMaster(new TicTacToeRule(), view, GameTitle.TIC_TAC_TOE).start();
                case GOMOKU -> new GameMaster(new GomokuRule(), view, GameTitle.GOMOKU).start();
                case CONNECT4 -> new GameMaster(new Connect4Rule(), view, GameTitle.CONNECT4).start();
            }
        }
        view.display(GameMessage.SEE_YOU);
    }
}