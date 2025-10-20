import game.connect4.Connect4;
import game.tictactoe.TicTacToe;
import ui.InteractionUser;
import ui.View;

public class Main {
    public static void main(String[] args) {
        View view = View.getInstance();
        InteractionUser interact = InteractionUser.getInstance();
        int choice = 0;

        while (choice != 3) {
            view.displayTitle("Bienvenue sur Square GameType");
            choice = interact.getChoice("Choisissez un jeu", new String[]{"TicTacToe", "Puissance 4", "Quitter"});
            switch (choice) {
                case 1 -> new TicTacToe().start();
                case 2 -> new Connect4().start();
                case 3 -> view.display("A bientôt !");
                default -> view.displayError("Choix invalide");
            }
            view.display("\n");
        }
    }
}
