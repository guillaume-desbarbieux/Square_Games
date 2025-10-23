package view.dictionary;

import java.util.HashMap;
import java.util.Map;

public class GameDictionary {
    private final Map<GameMessage, String> messages = new HashMap<>();
    private final Map<GameError, String> errors = new HashMap<>();
    private final Map<GameTitle, String> titles = new HashMap<>();
    private final Map<GameChoice, String> choices = new HashMap<>();

    public GameDictionary() {
        initMessages();
        initErrors();
        initTitles();
        initChoices();
    }

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

    private void initErrors() {
        errors.put(GameError.OUT_OF_BOARD, "Coup en dehors du plateau.");
        errors.put(GameError.INVALID_MOVE, "Coup invalide.");
        errors.put(GameError.INVALID_CHOICE, "Choix invalide.");
        errors.put(GameError.IS_NOT_INT, "Ce n'est pas un entier.");
        errors.put(GameError.OUT_OF_RANGE, "Veuillez respecter l'intervalle");
        errors.put(GameError.NO_CHOICES, "Aucun choix disponible");

    }

    private void initTitles() {
        titles.put(GameTitle.SQUARE_GAMES, "Bienvenue sur Square Games, les jeux carrément fun !");
        titles.put(GameTitle.CONNECT4, "Puissance 4");
        titles.put(GameTitle.GOMOKU, "Gomoku");
        titles.put(GameTitle.TIC_TAC_TOE, "Tic Tac Toe");
        titles.put(GameTitle.MAIN_MENU, "Menu Principal");
        titles.put(GameTitle.SETTINGS, "Paramètres");

    }

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

    public String get(GameMessage key) {
        return messages.getOrDefault(key, "Message inconnu : " + key);
    }

    public String get(GameTitle key) {
        return titles.getOrDefault(key, "Titre inconnu : " + key);
    }

    public String get(GameError key) {
        return errors.getOrDefault(key, "Erreur inconnue : " + key);
    }

    public String get(GameChoice key) {
        return choices.getOrDefault(key, "Choix inconnu : " + key);
    }

}