package TicTacToe;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InteractionUser {
    
    private final Scanner scanner;
    private final View view;
    
    public InteractionUser(View view){
        this.view = view;
        this.scanner = new Scanner(System.in);
    }

    public int getIntFromUser() {
        while (true) {
            try {
                return this.scanner.nextInt();
            } catch (InputMismatchException e) {
                view.displayMessage("Ceci n'est pas un entier.");
                this.scanner.nextLine();
            }
        }
    }
}
