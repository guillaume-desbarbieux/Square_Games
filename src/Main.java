import game.connect4.Connect4;
import game.tictactoe.TicTacToe;
import ui.InteractionUser;
import ui.View;

public class Main {
    public static void main(String[] args) {
        View view = new View();
        InteractionUser interact =  new InteractionUser(view);

        view.displayTitle("Bienvenue sur Square GameType");
        int choice = interact.getChoice("Choisissez un jeu", new String[]{"TicTacToe", "Puissance 4"});
        switch (choice) {
            case 1 -> new TicTacToe().start();
            case 2 -> new Connect4().start();
            default -> view.displayError("Choix invalide");
        }

    }
}
