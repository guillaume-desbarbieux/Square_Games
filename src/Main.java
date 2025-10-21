import game.Game;
import game.alignementGameRule.Connect4Rule;
import game.alignementGameRule.GomokuRule;
import game.alignementGameRule.TicTacToeRule;
import ui.InteractionUser;
import ui.View;

public class Main {
    public static void main(String[] args) {
        View view = View.getInstance();
        InteractionUser interact = InteractionUser.getInstance();
        int choice = 0;

        while (choice != 4) {
            view.displayTitle("Bienvenue sur Square Game");
            choice = interact.getChoice("Choisissez un jeu", new String[]{
                    "TicTacToe", "Gomoku", "Puissance 4", "Quitter"});
            switch (choice) {
                case 1 -> new Game(new TicTacToeRule()).start();
                case 2 -> new Game(new GomokuRule()).start();
                case 3 -> new Game(new Connect4Rule()).start();
                case 4 -> view.display("à bientôt");
                default -> view.displayError("Choix invalide");
            }
            view.display("\n");
        }
    }
}