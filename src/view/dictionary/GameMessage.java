package view.dictionary;

/**
 * Represents various types of messages used during the game lifecycle.
 * Each message corresponds to a specific prompt, status, or feedback
 * that might be displayed to the user during gameplay. This enumeration
 * is commonly used in the UI to standardize the messages presented across
 * different parts of the game.
 * <p>
 * Enum constants:
 * - WELCOME: Represents a message to welcome the player to the game.
 * - PLAYER_TURN: Indicates it is the current player's turn.
 * - GAME_OVER_WIN: Represents a message indicating a player has won the game.
 * - GAME_OVER_DRAW: Represents a message indicating the game ended in a draw.
 * - GET_CHOICE: Prompts the player to make a choice.
 * - GET_NB_HUMAN_PLAYERS: Prompts for the number of human players.
 * - GET_BOARD_SIZE: Prompts for the size of the game board.
 * - GET_COL: Prompts for a column input.
 * - GET_GAME: Prompts to select a game type or mode.
 * - SEE_YOU: Represents a farewell message.
 * - GET_ROW: Prompts for a row input.
 */
public enum GameMessage {
    WELCOME,
    PLAYER_TURN,
    GAME_OVER_WIN,
    GAME_OVER_DRAW,
    GET_CHOICE,
    GET_NB_HUMAN_PLAYERS,
    GET_BOARD_SIZE,
    GET_COL,
    GET_GAME,
    SEE_YOU,
    WANT_REPLAY, IS_TURN_FINISH, GET_ROW
}

