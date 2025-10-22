package view;

import model.Board;

public class View {
    private static View instance;
    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";
    public static final String YELLOW = "\u001B[33m";
    public static final String RED = "\u001B[31m";

    private View() {}

    public static View getInstance(){
        if (View.instance == null){
            View.instance = new View();
        }
        return View.instance;
    }

    public void display(String message) {
        System.out.println(message);
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
}