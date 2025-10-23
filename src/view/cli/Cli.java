package view.cli;

import controller.GameChoice;
import controller.GameError;
import controller.GameMessage;
import controller.GameTitle;
import model.Board;
import view.GameDictionary;
import view.View;

import java.awt.*;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Cli implements View {
    private final GameDictionary dictionary = new GameDictionary();
    private final Scanner scanner = new Scanner(System.in);
    private boolean maximize = false;

    private static final String RESET = "\u001B[0m";
    private static final String BLUE = "\u001B[34m";
    private static final String RED = "\u001B[31m";


    public Cli() {
    }

    public void display(String message) {
        System.out.println(message);
    }

    @Override
    public void setSize(GameChoice choice) {
        switch (choice) {
            case BIG -> maximize = true;
            case LITTLE -> maximize = false;
        }
    }

    @Override
    public void display(GameMessage message) {
        display(message, "");
    }

    @Override
    public void display(GameMessage message, String extra) {
        display(dictionary.get(message) + " " + extra);
    }

    @Override
    public void display(GameTitle key) {
        String title = dictionary.get(key);
        String border = "═".repeat(title.length());
        display(BLUE + "╔══" + border + "══╗" + RESET);
        display(BLUE + "║  " + title + "  ║" + RESET);
        display(BLUE + "╚══" + border + "══╝" + RESET);
    }

    @Override
    public void display(GameError key) {
        String error = dictionary.get(key);
        String border = "!".repeat(error.length());
        display(RED + "!!!!" + border + "!!!!" + RESET);
        display(RED + "!!  " + error + "  !!" + RESET);
        display(RED + "!!!!" + border + "!!!!" + RESET);
    }

    private int getInt() {
        while (true) {
            try {
                return this.scanner.nextInt();
            } catch (InputMismatchException e) {
                this.display(GameError.IS_NOT_INT);
                this.scanner.nextLine();
            }
        }
    }

    @Override
    public int getInt(GameMessage message) {
        display(message);
        return getInt();
    }

    @Override
    public int getInt(GameMessage message, int min, int max) {
        display(message, "[" + min + ".." + max + "]");
        int value = getInt();
        if (value < min || value > max) {
            display(GameError.OUT_OF_RANGE);
            return getInt(message, min, max);
        }
        return value;
    }

    @Override
    public GameChoice getChoice(GameMessage message, List<GameChoice> choices) {
        display(message);

        int size = choices.size();
        if (size == 0) {
            display(GameError.NO_CHOICES);
            return null;
        }
        for (int i = 1; i <= size; i++)
            display(i + " ▸ " + dictionary.get(choices.get(i - 1)));
        display("");
        int index = getInt(GameMessage.GET_CHOICE);
        if (index < 1 || index > size) {
            display(GameError.INVALID_CHOICE);
            return getChoice(message, choices);
        }
        return choices.get(index - 1);
    }

    @Override
    public String getString(GameMessage message) {
        display(message);
        return scanner.nextLine();
    }

    @Override
    public void display(Board board) {
        int indexWidth = String.valueOf(board.height()).length();
        String horizontalSeparator = " ";
        String verticalSeparator = "";
        int cellWidth = horizontalSeparator.length() + 1;

        if (maximize) {
            horizontalSeparator = " | ";
            cellWidth = horizontalSeparator.length() + 1;
            verticalSeparator = " ".repeat(indexWidth + cellWidth / 2) + "-".repeat(cellWidth * board.width()) + "\n";
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
        display(message.toString());
    }
}