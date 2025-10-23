package view.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a localization dictionary for a game application.
 * The dictionary contains mappings of various game-related enumerations
 * (such as messages, errors, titles, and choices) to their corresponding localized strings.
 * <p>
 * This class provides utility methods to retrieve localized versions of game elements,
 * making it easier to adapt the application to different languages or regions.
 */
public class GameDictionary {
    private final Map<GameMessage, String> messages = new HashMap<>();
    private final Map<GameError, String> errors = new HashMap<>();
    private final Map<GameTitle, String> titles = new HashMap<>();
    private final Map<GameChoice, String> choices = new HashMap<>();

    /**
     * Constructs a new instance of the {@code GameDictionary} class and initializes
     * its internal mappings for messages, errors, titles, and choices.
     * <p>
     * The constructor invokes private initialization methods to populate the fields
     * with predefined string values that correspond to localized text or display labels
     * for various game components. These mappings are used throughout the application
     * to provide support for predefined messages, error descriptions, game titles,
     * and user choice labels.
     * <p>
     * Initialization methods called by this constructor:
     * - {@code initMessages()}: Sets up the association between {@code GameMessage} enums
     *   and their corresponding messages.
     * - {@code initErrors()}: Sets up the association between {@code GameError} enums
     *   and their corresponding error messages.
     * - {@code initTitles()}: Sets up the association between {@code GameTitle} enums
     *   and their corresponding titles.
     * - {@code initChoices()}: Sets up the association between {@code GameChoice} enums
     *   and their corresponding choices.
     */
    public GameDictionary() {
        initMessages();
        initErrors();
        initTitles();
        initChoices();
    }

    /**
     * Initializes the internal mapping of {@code GameMessage} enum constants to their
     * corresponding localized string values.
     * <p>
     * This method populates the {@code messages} field with predefined key-value pairs
     * where each {@code GameMessage} serves as the key and its associated localized string
     * serves as the value. These mappings are used throughout the application to retrieve
     * localized text for various game messages.
     * <p>
     * Mapped messages :
     * - {@code GameMessage.WELCOME}: "Bienvenue !"
     * - {@code GameMessage.PLAYER_TURN}: "Au tour du joueur : "
     * - {@code GameMessage.GAME_OVER_WIN}: "Victoire du joueur :"
     * - {@code GameMessage.GAME_OVER_DRAW}: "Match nul !"
     * - {@code GameMessage.GET_CHOICE}: "→ Votre choix ?"
     * - {@code GameMessage.GET_NB_HUMAN_PLAYERS}: "Nombre de joueurs humain ?"
     * - {@code GameMessage.GET_BOARD_SIZE}: "Taille d'affichage du plateau ?"
     * - {@code GameMessage.GET_COL}: "Choisissez une colonne."
     * - {@code GameMessage.GET_GAME}: "Choisissez un jeu."
     * - {@code GameMessage.GET_ROW}: "Choisissez une ligne."
     * - {@code GameMessage.SEE_YOU}: "à bientôt !"
     */
    private void initMessages() {
        messages.put(GameMessage.WELCOME, "Bienvenue !");
        messages.put(GameMessage.PLAYER_TURN, "Au tour du joueur : ");
        messages.put(GameMessage.GAME_OVER_WIN, "Victoire du joueur :");
        messages.put(GameMessage.GAME_OVER_DRAW, "Match nul !");

        messages.put(GameMessage.GET_CHOICE, "→ Votre choix ?");
        messages.put(GameMessage.GET_NB_HUMAN_PLAYERS, "Nombre de joueurs humain ?");
        messages.put(GameMessage.GET_BOARD_SIZE, "Taille d'affichage du plateau ?");
        messages.put(GameMessage.GET_COL, "Choisissez une colonne.");
        messages.put(GameMessage.GET_GAME, "Choisissez un jeu.");
        messages.put(GameMessage.GET_ROW, "Choisissez une ligne.");
        messages.put(GameMessage.SEE_YOU, "à bientôt !");


    }

    /**
     * Initializes the internal mapping of {@code GameError} enum constants to their
     * corresponding localized string values.
     * <p>
     * This method populates the {@code errors} field with predefined key-value pairs
     * where each {@code GameError} serves as the key and its associated localized string
     * serves as the value. These mappings are used throughout the application to provide
     * descriptions for various error scenarios encountered during gameplay or user input.
     * <p>
     * Mapped errors :
     * - {@code GameError.OUT_OF_BOARD}: "Coup en dehors du plateau."
     * - {@code GameError.INVALID_MOVE}: "Coup invalide."
     * - {@code GameError.INVALID_CHOICE}: "Choix invalide."
     * - {@code GameError.IS_NOT_INT}: "Ce n'est pas un entier."
     * - {@code GameError.OUT_OF_RANGE}: "Veuillez respecter l'intervalle"
     * - {@code GameError.NO_CHOICES}: "Aucun choix disponible"
     */
    private void initErrors() {
        errors.put(GameError.OUT_OF_BOARD, "Coup en dehors du plateau.");
        errors.put(GameError.INVALID_MOVE, "Coup invalide.");
        errors.put(GameError.INVALID_CHOICE, "Choix invalide.");
        errors.put(GameError.IS_NOT_INT, "Ce n'est pas un entier.");
        errors.put(GameError.OUT_OF_RANGE, "Veuillez respecter l'intervalle");
        errors.put(GameError.NO_CHOICES, "Aucun choix disponible");

    }

    /**
     * Initializes the internal mapping of {@code GameTitle} enum constants to their
     * corresponding localized string values.
     * <p>
     * This method populates the {@code titles} field with predefined key-value pairs
     * where each {@code GameTitle} serves as the key and its associated localized string
     * serves as the value. These mappings are used throughout the application to
     * retrieve the display labels for various game titles.
     * <p>
     * Mapped titles:
     * - {@code GameTitle.SQUARE_GAMES}: "Bienvenue sur Square Games, les jeux carrément fun !"
     * - {@code GameTitle.CONNECT4}: "Puissance 4"
     * - {@code GameTitle.GOMOKU}: "Gomoku"
     * - {@code GameTitle.TIC_TAC_TOE}: "Tic Tac Toe"
     * - {@code GameTitle.MAIN_MENU}: "Menu Principal"
     * - {@code GameTitle.SETTINGS}: "Paramètres"
     */
    private void initTitles() {
        titles.put(GameTitle.SQUARE_GAMES, "Bienvenue sur Square Games, les jeux carrément fun !");
        titles.put(GameTitle.CONNECT4, "Puissance 4");
        titles.put(GameTitle.GOMOKU, "Gomoku");
        titles.put(GameTitle.TIC_TAC_TOE, "Tic Tac Toe");
        titles.put(GameTitle.MAIN_MENU, "Menu Principal");
        titles.put(GameTitle.SETTINGS, "Paramètres");

    }

    /**
     * Initializes the internal mapping of {@code GameChoice} enum constants to their
     * corresponding localized string values.
     * <p>
     * This method populates the {@code choices} field with predefined key-value pairs
     * where each {@code GameChoice} serves as the key and its associated localized string
     * serves as the value. These mappings are used throughout the application to
     * provide display labels for user-selectable game options in the interface.
     * <p>
     * Mapped game choices:
     * - {@code GameChoice.QUICK_START}: "Partie Rapide"
     * - {@code GameChoice.SETTINGS}: "Paramètres"
     * - {@code GameChoice.CONNECT4}: "Puissance 4"
     * - {@code GameChoice.GOMOKU}: "Gomoku"
     * - {@code GameChoice.TIC_TAC_TOE}: "Tic Tac Toe"
     * - {@code GameChoice.LITTLE}: "petit"
     * - {@code GameChoice.BIG}: "grand"
     * - {@code GameChoice.QUIT}: "Quitter"
     */
    private void initChoices() {
        choices.put(GameChoice.QUICK_START, "Partie Rapide");
        choices.put(GameChoice.SETTINGS, "Paramètres");
        choices.put(GameChoice.CONNECT4, "Puissance 4");
        choices.put(GameChoice.GOMOKU, "Gomoku");
        choices.put(GameChoice.TIC_TAC_TOE, "Tic Tac Toe");
        choices.put(GameChoice.LITTLE, "petit");
        choices.put(GameChoice.BIG, "grand");
        choices.put(GameChoice.QUIT, "Quitter");


    }

    /**
     * Retrieves the localized message corresponding to the provided {@code GameMessage} key.
     * If the key is not found in the {@code messages} mapping, a default message indicating
     * the missing key is returned.
     *
     * @param key the {@code GameMessage} key whose associated message is to be retrieved
     * @return the localized message corresponding to the given key, or a default message
     *         indicating that the key is unknown
     */
    public String get(GameMessage key) {
        return messages.getOrDefault(key, "Message inconnu : " + key);
    }

    /**
     * Retrieves the localized title corresponding to the provided {@code GameTitle} key.
     * If the key is not found in the {@code titles} mapping, a default message indicating
     * the missing title is returned.
     *
     * @param key the {@code GameTitle} key whose associated localized title is to be retrieved
     * @return the localized title corresponding to the given key, or a default message
     *         indicating that the title is unknown
     */
    public String get(GameTitle key) {
        return titles.getOrDefault(key, "Titre inconnu : " + key);
    }

    /**
     * Retrieves the localized error message corresponding to the provided {@code GameError} key.
     * If the key is not found in the {@code errors} mapping, a default message indicating
     * the missing error is returned.
     *
     * @param key the {@code GameError} key whose associated localized error message is to be retrieved
     * @return the localized error message corresponding to the given key, or a default message
     *         indicating an unknown error
     */
    public String get(GameError key) {
        return errors.getOrDefault(key, "Erreur inconnue : " + key);
    }

    /**
     * Retrieves the localized choice corresponding to the provided {@code GameChoice} key.
     * If the key is not found in the {@code choices} mapping, a default message indicating
     * the missing choice is returned.
     *
     * @param key the {@code GameChoice} key whose associated localized choice is to be retrieved
     * @return the localized choice corresponding to the given key, or a default message
     *         indicating that the choice is unknown
     */
    public String get(GameChoice key) {
        return choices.getOrDefault(key, "Choix inconnu : " + key);
    }

}