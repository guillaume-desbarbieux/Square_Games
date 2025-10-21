import game.alignementGame.AlignementGame;
import move.factory.ColInputAdapter;
import move.factory.RowColInputAdapter;
import ui.InteractionUser;
import ui.View;

public class Main {
    public static void main(String[] args) {
        View view = View.getInstance();
        InteractionUser interact = InteractionUser.getInstance();
        int choice = 0;

        while (choice != 4) {
            view.displayTitle("Bienvenue sur Square GameType");
            choice = interact.getChoice("Choisissez un jeu", new String[]{
                    "TicTacToe", "Puissance 4", "Gomoku", "Quitter"});
            switch (choice) {
                case 1 -> new AlignementGame("TicTacToe", 3, 3, 3, new RowColInputAdapter()).start();
                case 2 -> new AlignementGame("Puissance 4", 6, 7, 4, new ColInputAdapter()).start();
                case 3 -> new AlignementGame("Gomoku", 15, 15, 5, new RowColInputAdapter()).start();
                case 4 -> view.display("A bientôt !");
                default -> view.displayError("Choix invalide");
            }
            view.display("\n");
        }
    }
}
