package Interact;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InteractionUser {
    
    private final Scanner scanner;
    private final View view;
    
    public InteractionUser(View view){
        this.view = view;
        this.scanner = new Scanner(System.in);
    }

    public int getInt(String message) {
        view.display(message);
        while (true) {
            try {
                return this.scanner.nextInt();
            } catch (InputMismatchException e) {
                view.display("Ceci n'est pas un entier.");
                this.scanner.nextLine();
            }
        }
    }

    public int getInt(String message, int min, int max) {
        view.display(message);
        int value = getInt("["+min+".."+max+"]");
        if (value < min || value > max) {
            view.displayError("Veuillez entrer un nombre compris entre " + min + " et " + max);
            return getInt(message, min, max);
        }
        return value;
    }

    public int getChoice(String message, String[] choices) {
        if (choices.length == 0) {
            view.displayError("Aucun choix disponible");
            return 0;
        }

        while (true) {
            view.display(message);

            for (int i = 1; i <= choices.length; i++) {
                view.display(i + " ▸ " + choices[i - 1]);
            }
            int choice = getInt("\n→ Choix ?");

            if (choice > 0 && choice <= choices.length) {
                return choice;
            } else {
                view.displayError("Veuillez faire un choix valide");
            }
        }
    }

    public String getString(String message) {
        view.display(message);
        return scanner.nextLine();
    }
}
