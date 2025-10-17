package ui;

import board.Board;

public class View {
    private boolean maximize = false;
    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";
    public static final String YELLOW = "\u001B[33m";
    public static final String RED = "\u001B[31m";

    public View() {
    }

    public void setMaximize(boolean maximize) {
        this.maximize = maximize;
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
                message.append(board.getCell(i, j).getRepresentation()).append(horizontalSeparator);
            }
            message.append("\n").append(verticalSeparator);
        }
        display(message.toString());
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

    public void displaySuccess(String success) {
        String border = "✓".repeat(success.length());
        display(GREEN + "✓✓✓" + border + "✓✓✓" + RESET);
        display(GREEN + "✓  " + success + "  ✓" + RESET);
        display(GREEN + "✓✓✓" + border + "✓✓✓" + RESET);
    }

    public void displayWarning(String warning) {
        String border = "⚠".repeat(warning.length());
        display(YELLOW + "⚠⚠⚠" + border + "⚠⚠⚠" + RESET);
        display(YELLOW + "⚠  " + warning + "  ⚠" + RESET);
        display(YELLOW + "⚠⚠⚠" + border + "⚠⚠⚠" + RESET);
    }
}
