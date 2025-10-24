package view.cli;

import model.Cell;
import view.dictionary.GameChoice;
import view.dictionary.GameError;
import view.dictionary.GameMessage;
import view.dictionary.GameTitle;
import model.Board;
import view.dictionary.GameDictionary;
import view.View;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * The Cli class represents a Command Line Interface (CLI) implementation of the View interface.
 * It is responsible for displaying messages, prompts, and data to the user, managing user input,
 * and rendering game-specific content such as game boards and titles. This implementation uses
 * escape sequences for styling output, such as coloring or formatting text, and it leverages
 * a GameDictionary instance to retrieve human-readable messages, errors, titles, or choices.
 */
public class Cli implements View {
    private final GameDictionary dictionary = new GameDictionary();
    private final Scanner scanner = new Scanner(System.in);
    private boolean maximize = false;

    private static final String RESET = "\u001B[0m";
    private static final String BLUE = "\u001B[34m";
    private static final String RED = "\u001B[31m";

    /**
     * Constructs a new instance of the Cli class.
     * <p>
     * The Cli class represents a command-line interface implementation
     * of the View, enabling interaction between the user and the
     * application. This constructor initializes the Cli instance,
     * preparing it to handle user input and display output for various
     * game-related functionalities.
     */
    public Cli() {
    }

    /**
     * Displays the provided message to the console.
     *
     * @param message the message to be displayed
     */
    public void display(String message) {
        System.out.println(message);
    }

    /**
     * Configures the display size based on the provided game choice.
     * <p>
     * If the choice is {@code BIG}, the display is set to maximized size.
     * If the choice is {@code LITTLE}, the display is set to minimized size.
     *
     * @param choice the game choice that determines the display size
     */
    @Override
    public void setSize(GameChoice choice) {
        switch (choice) {
            case BIG -> maximize = true;
            case LITTLE -> maximize = false;
        }
    }

    /**
     * Displays the specified game message to the console.
     * <p>
     * This method retrieves the corresponding string representation
     * of the given {@code GameMessage} and displays it. It delegates
     * the actual display to another method with an additional parameter
     * for customization.
     *
     * @param message the game message to be displayed
     */
    @Override
    public void display(GameMessage message) {
        display(message, "");
    }

    /**
     * Displays the specified game message along with additional information to the console.
     * <p>
     * This method retrieves the corresponding string representation of the given
     * {@code GameMessage} from the dictionary, appends the provided extra string,
     * and delegates the output to another method for display.
     *
     * @param message the game message to be displayed
     * @param extra additional information to be appended to the message
     */
    @Override
    public void display(GameMessage message, String extra) {
        display(dictionary.get(message) + " " + extra);
    }

    /**
     * Displays the specified game title in a formatted manner to the console.
     * <p>
     * This method retrieves the corresponding string representation of the given
     * {@code GameTitle} from the dictionary. It then formats the title with a
     * decorative border and outputs the styled result to the console.
     *
     * @param key the game title to be displayed
     */
    @Override
    public void display(GameTitle key) {
        String title = dictionary.get(key);
        String border = "═".repeat(title.length());
        display(BLUE + "╔══" + border + "══╗" + RESET);
        display(BLUE + "║  " + title  + "  ║" + RESET);
        display(BLUE + "╚══" + border + "══╝" + RESET);
    }

    /**
     * Displays the specified game error in a formatted manner to the console.
     * <p>
     * This method retrieves the corresponding string representation of the
     * given {@code GameError} from the dictionary, formats it with a decorative
     * border, and outputs the styled result to the console.
     *
     * @param key the game error to be displayed
     */
    @Override
    public void display(GameError key) {
        String error = dictionary.get(key);
        String border = "!".repeat(error.length());
        display(RED + "!!!!" + border + "!!!!" + RESET);
        display(RED + "!!  " + error + "  !!" + RESET);
        display(RED + "!!!!" + border + "!!!!" + RESET);
    }

    /**
     * Reads an integer input from the user through the scanner.
     * <p>
     * This method continuously prompts the user to provide valid integer input.
     * If the user enters invalid input (e.g., non-integer values), it catches the
     * {@code InputMismatchException}, indicates the error using {@code GameError.IS_NOT_INT},
     * and prompts the user again until a valid integer is provided.
     *
     * @return the integer value entered by the user
     */
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

    /**
     * Displays the specified game message and prompts the user for an integer input.
     * <p>
     * This method first displays the given {@code GameMessage} to the console,
     * prompting the user for input. It then retrieves the input by delegating
     * to a private method that ensures the input is a valid integer.
     *
     * @param message the game message to be displayed before the input prompt
     * @return the integer value entered by the user
     */
    @Override
    public int getInt(GameMessage message) {
        display(message);
        return getInt();
    }

    /**
     * Prompts the user for an integer input within a specified range.
     * <p>
     * This method displays a message to the user along with the acceptable range
     * (inclusive) and reads an integer input. If the input is outside the specified
     * range, it displays an error message and recursively prompts for a valid input
     * until the user provides a value within the range.
     *
     * @param message the game message to display as a prompt
     * @param min the minimum allowable value for the input
     * @param max the maximum allowable value for the input
     * @return the integer value provided by the user within the specified range
     */
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

    /**
     * Retrieves a game choice from the user based on the provided options.
     * <p>
     * This method displays a list of choices for the user to select from
     * using a numbered format. If no choices are available, it displays
     * an error message and returns null. If the user provides an invalid
     * choice, it recursively prompts them to enter a valid one.
     *
     * @param message the game message to display as a prompt to the user
     * @param choices the list of game choices available for selection
     * @return the game choice selected by the user, or null if no choices are available
     */
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

    /**
     * Prompts the user for a string input corresponding to the given game message.
     * <p>
     * This method first displays the specified {@code GameMessage} to the console
     * by delegating to the {@code display} method. It then retrieves the user's
     * response as a string from the scanner.
     *
     * @param message the game message to display as a prompt to the user
     * @return the string input provided by the user
     */
    @Override
    public String getString(GameMessage message) {
        display(message);
        return scanner.nextLine();
    }

    /**
     * Displays the current state of the board in a formatted manner.
     * <p>
     * This method outputs the board's grid structure with indices for rows and columns,
     * providing a clear and easy-to-read representation of the game's state. It accounts
     * for different display settings, such as maximized or minimized display, and output
     * cells formatted according to their rendered representation.
     *
     * @param board the game board to be displayed
     */
    @Override
    public void display(Board board, List<String> representations, List<String> highlights) {
        int indexWidth = String.valueOf(board.getHeight()).length();
        String horizontalSeparator = " ";
        String verticalSeparator = "";
        int cellWidth = horizontalSeparator.length() + 1;

        if (maximize) {
            horizontalSeparator = " | ";
            cellWidth = horizontalSeparator.length() + 1;
            verticalSeparator = " ".repeat(indexWidth + cellWidth / 2) + "-".repeat(cellWidth * board.getWidth()) + "\n";
        }

        StringBuilder message = new StringBuilder();
        message.append(" ".repeat(indexWidth));

        for (int j = 0; j < board.getWidth(); j++) {
            message.append(String.format("%" + cellWidth + "d", j + 1));
        }
        message.append("\n").append(verticalSeparator);

        for (int i = 0; i < board.getHeight(); i++) {
            message.append(String.format("%" + indexWidth + "d", i + 1)).append(horizontalSeparator);
            for (int j = 0; j < board.getWidth(); j++) {
                Cell cell = board.getCell(i,j);
                String render = "·";
                if (!cell.isEmpty())
                    if (cell.isHighlighted())
                        render = highlights.get(cell.getOwnerId());
                    else
                        render = representations.get(cell.getOwnerId());
                message.append(render).append(horizontalSeparator);
            }
            message.append("\n").append(verticalSeparator);
        }
        display(message.toString());
    }
}