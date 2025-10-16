import java.util.InputMismatchException;
import java.util.Scanner;

public class GameScanner {
    
    private final Scanner scanner;
    
    public GameScanner(){
        this.scanner = new Scanner(System.in);
    }

    public int getIntFromUser() {
        while (true) {
            try {
                return this.scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Ceci n'est pas un entier.");
                this.scanner.nextLine();
            }
        }
    }
}
