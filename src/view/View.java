package view;

import model.Board;
import view.dictionary.GameChoice;
import view.dictionary.GameError;
import view.dictionary.GameMessage;
import view.dictionary.GameTitle;

import java.util.List;

/**
 * Represents an interface for displaying and receiving input for a game.
 * <p>
 * The View interface provides methods for rendering various game-related
 * messages, titles, errors, and board states, as well as gathering user
 * input such as integers, choices, and strings. Implementations of this
 * interface are responsible for handling interaction between the game
 * logic and the user interface.
 */
public interface View {
    /**
     * Displays a message to the user.
     *
     * @param message the message to be displayed; typically used to convey game-related
     *                information, instructions, or errors to the user
     */
    void display(String message);

    /**
     * Displays the specified {@code GameMessage} to the user.
     * <p>
     * This method is typically used to convey standardized messages
     * or prompts related to the game, such as game events, status updates,
     * or input requests. The specific {@code GameMessage} indicates the
     * nature of the information being displayed.
     *
     * @param message the {@code GameMessage} to be displayed; this could include
     *                prompts for player actions, game state updates, or other
     *                predefined game-related information
     */
    void display(GameMessage message);

    /**
     * Displays the specified {@code GameMessage} to the user along with additional information.
     * <p>
     * This method is typically used to provide standardized game messages
     * accompanied by extra details or context relevant to the user. For example,
     * this could include player details during their turn or a custom message
     * appended to a predefined game status update.
     *
     * @param message the {@code GameMessage} to be displayed; represents predefined game-related
     *                information, such as prompts, status updates, or completion messages
     * @param extra   the additional information to be displayed alongside the {@code GameMessage};
     *                this could include dynamic content such as player names, game states,
     *                or other contextual data
     */
    void display(GameMessage message, String extra);

    /**
     * Displays the specified {@code GameTitle} to the user.
     * <p>
     * This method is used to render the title of a particular game mode, menu,
     * or screen in the user interface. The provided {@code GameTitle} enumeration value
     * determines which title is displayed, ensuring consistent labeling
     * across the application's different areas.
     *
     * @param title the {@code GameTitle} to be displayed; represents predefined titles
     *              for game screens such as "Main Menu," "Settings," or specific games
     *              like "Connect 4" or "Tic Tac Toe"
     */
    void display(GameTitle title);

    /**
     * Displays the specified {@code GameError} to the user.
     * <p>
     * This method is typically used to inform the user about game-related errors
     * encountered during gameplay, such as invalid moves, out-of-range inputs,
     * or invalid choices. The specific {@code GameError} type provides details
     * about the nature of the error.
     *
     * @param error the {@code GameError} to be displayed; represents predefined types
     *              of errors such as {@code INVALID_MOVE}, {@code OUT_OF_BOARD},
     *              or {@code IS_NOT_INT}
     */
    void display(GameError error);

    /**
     * Displays the specified {@code Board} to the user.
     * <p>
     * This method renders the current state of the game board, providing
     * a visual or textual representation of the board's cells and their
     * contents. It is typically used to present the game's progress
     * during gameplay.
     *
     * @param board the {@code Board} to be displayed; represents the grid
     *              containing the current state of the game, including
     *              cell representations and their ownership (if any)
     */
    void display(Board board, List<String> representations, List<String> highlights);

    /**
     * Configures the size of the board or game configuration based on the specified {@code GameChoice}.
     * <p>
     * This method adjusts the visual or logical representation of the game to match
     * the selected board size or related configuration defined by the {@code GameChoice}.
     * Typical choices include predefined options such as "LITTLE" or "BIG" for board sizes.
     *
     * @param choice the {@code GameChoice} determining the size or configuration to be set;
     *               commonly includes options such as {@code LITTLE}, {@code BIG},
     *               or other game-related settings influencing the board or environment
     */
    void setSize(GameChoice choice);

    /**
     * Retrieves an integer input based on the specified {@code GameMessage}.
     * <p>
     * This method is used to prompt the user for an integer input by displaying
     * the provided {@code GameMessage}. The returned integer is typically used
     * in the context of game interactions, such as selecting options, making
     * moves, or configuring game settings.
     *
     * @param message the {@code GameMessage} to display as a prompt when requesting
     *                user input; this message provides context for the expected input
     * @return the integer value entered by the user
     */
    int getInt(GameMessage message);

    /**
     * Retrieves an integer input from the user within the specified range.
     * <p>
     * This method prompts the user to enter an integer by displaying the given
     * {@code GameMessage}. The input value is validated to ensure it falls within
     * the specified minimum and maximum bounds. If the input is invalid, the user
     * may be prompted to try again until a valid input is provided.
     *
     * @param message the {@code GameMessage} describing the prompt to be displayed
     *                to the user when requesting input; provides context for the
     *                expected value
     * @param min     the minimum permissible value for the input; serves as the lower
     *                bound for valid input
     * @param max     the maximum permissible value for the input; serves as the upper
     *                bound for valid input
     * @return the integer value entered by the user that falls within the specified
     *         range
     */
    int getInt(GameMessage message, int min, int max);

    /**
     * Retrieves a {@code GameChoice} selected by the user from a list of available choices.
     * <p>
     * This method is used to present a prompt to the user, specified by the {@code GameMessage},
     * and retrieve one of the valid {@code GameChoice} options available in the provided list.
     *
     * @param message the {@code GameMessage} to be displayed, typically used to describe the
     *                context or purpose of the choice (e.g., "Select a game mode").
     * @param choices a list of valid {@code GameChoice} options from which the user can select;
     *                this determines the available options presented to the user.
     * @return the {@code GameChoice} selected by the user from the provided list of options.
     */
    GameChoice getChoice(GameMessage message, List<GameChoice> choices);

    /**
     * Retrieves a string representation of the specified {@code GameMessage}.
     * <p>
     * This method is typically used to convert a {@code GameMessage} into its
     * corresponding textual form, which can then be displayed or processed further.
     * The returned string may represent a preformatted or localized version
     * of the message, depending on implementation.
     *
     * @param message the {@code GameMessage} to be converted; represents a predefined
     *                message related to the game's state, prompts, or events
     * @return a string representation of the provided {@code GameMessage}
     */
    String getString(GameMessage message);
}