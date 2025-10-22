package view;

import model.Board;

import java.util.InputMismatchException;
import java.util.Scanner;

public class View {
    private static View instance;
    private final Scanner scanner;
    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";
    public static final String YELLOW = "\u001B[33m";
    public static final String RED = "\u001B[31m";

    private boolean maximize = false;

    private View() {
        this.scanner = new Scanner(System.in);
    }

    public static View getInstance(){
        if (View.instance == null){
            View.instance = new View();
        }
        return View.instance;
    }

    public void display(String message) {
        System.out.println(message);
    }

    public void displayBoard(Board board) {
        int indexWidth = String.valueOf(board.height()).length();
        String horizontalSeparator = " ";
        String verticalSeparator = "";
        int cellWidth = horizontalSeparator.length() + 1;

        if (maximize) {
            horizontalSeparator = " | ";
            cellWidth = horizontalSeparator.length() + 1;
            verticalSeparator = " ".repeat(indexWidth + cellWidth/2) + "-".repeat(cellWidth * board.width()) + "\n";
        }

        StringBuilder message = new StringBuilder();
        message.append(" ".repeat(indexWidth));

        for (int j = 0; j < board.width(); j++) {
            message.append(String.format("%" + cellWidth + "d", j + 1));
        }
        message.append("\n").append(verticalSeparator);

        for (int i = 0; i < board.height(); i++) {
            message.append(String.format("%" + indexWidth + "d", i + 1)).append(horizontalSeparator);
            for (int j = 0; j < board.width(); j++) {
                message.append(board.getCell(i, j).render()).append(horizontalSeparator);
            }
            message.append("\n").append(verticalSeparator);
        }
        display (message.toString());
    }

    public void displayTitle(String title) {
        String border = "═".repeat(title.length());
        display(BLUE + "╔══" + border + "══╗" + RESET);
        display(BLUE + "║  " + title + "  ║" + RESET);
        display(BLUE + "╚══" + border + "══╝" + RESET);
    }

    public void displayError(String error) {
        String border = "!".repeat(error.length() );

        display(RED + "!!!!" + border + "!!!!" + RESET);
        display(RED + "!!  " + error + "  !!" + RESET);
        display(RED + "!!!!" + border + "!!!!" + RESET);
    }

    public void setMaximize(boolean maximize) {
        this.maximize = maximize;
    }

    public int getInt(String message) {
        this.display(message);
        while (true) {
            try {
                return this.scanner.nextInt();
            } catch (InputMismatchException e) {
                this.display("Ceci n'est pas un entier.");
                this.scanner.nextLine();
            }
        }
    }

    public int getInt(String message, int min, int max) {
        this.display(message);
        int value = getInt("["+min+".."+max+"]");
        if (value < min || value > max) {
            this.displayError("Veuillez entrer un nombre compris entre " + min + " et " + max);
            return getInt(message, min, max);
        }
        return value;
    }

    public int getChoice(String message, String[] choices) {
        if (choices.length == 0) {
            this.displayError("Aucun choix disponible");
            return 0;
        }

        while (true) {
            this.display(message);

            for (int i = 1; i <= choices.length; i++) {
                this.display(i + " ▸ " + choices[i - 1]);
            }
            int choice = getInt("\n→ Choix ?");

            if (choice > 0 && choice <= choices.length) {
                return choice;
            } else {
                this.displayError("Veuillez faire un choix valide");
            }
        }
    }
}