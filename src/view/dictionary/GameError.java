package view.dictionary;

/**
 * Represents different types of errors that can occur in the game.
 * This enumeration is typically used to classify and handle various game-related
 * error scenarios in a structured manner.
 * <p>
 * Enum constants:
 * - IS_NOT_INT: Indicates an input that is not an integer when an integer is required.
 * - OUT_OF_RANGE: Indicates that the input or value is outside the allowed range.
 * - NO_CHOICES: Indicates that no choices are available or provided in a required context.
 * - OUT_OF_BOARD: Indicates an action or move that is outside the boundaries of the game board.
 * - INVALID_MOVE: Indicates an action or move that is not valid according to the game rules.
 * - INVALID_CHOICE: Indicates a choice made by the user that is not valid in the current context.
 */
public enum GameError {
    IS_NOT_INT, OUT_OF_RANGE, NO_CHOICES, OUT_OF_BOARD, INVALID_MOVE, INVALID_CHOICE, NO_SAVED_GAMES,
}
