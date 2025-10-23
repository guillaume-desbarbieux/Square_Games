package view.dictionary;

/**
 * Represents a set of predefined choices available in the game.
 * These choices are used in various parts of the system to determine user actions,
 * game modes, and configuration preferences. Each choice corresponds to a specific
 * action or option in the game's interface.
 * <p>
 * This enumeration is used in the {@code GameDictionary} class to associate each
 * choice with its corresponding display string or localized label.
 * <p>
 * Enum constants:
 * - SETTINGS: Represents the "Settings" option in the game's menu.
 * - QUICK_START: Represents the "Quick Start" mode for starting a game quickly.
 * - LITTLE: Represents a "small" board size or configuration.
 * - QUIT: Represents the "Quit" option to exit the game.
 * - TIC_TAC_TOE: Represents the "Tic Tac Toe" game mode.
 * - GOMOKU: Represents the "Gomoku" game mode.
 * - CONNECT4: Represents the "Connect 4" game mode.
 * - BIG: Represents a "large" board size or configuration.
 */
public enum GameChoice {
    SETTINGS,
    QUICK_START,
    LITTLE,
    QUIT,
    TIC_TAC_TOE,
    GOMOKU,
    CONNECT4,
    BIG
}
