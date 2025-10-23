package view;

import controller.GameChoice;
import controller.GameError;
import controller.GameMessage;
import controller.GameTitle;
import model.Board;
import java.util.List;

public interface View {

    void display(String message);

    void display(GameMessage message);

    void display(GameMessage message, String extra);

    void display(GameTitle title);

    void display(GameError error);

    void display(Board board);

    void setSize(GameChoice choice);

    int getInt(GameMessage message);

    int getInt(GameMessage message, int min, int max);

    GameChoice getChoice(GameMessage message, List<GameChoice> choices);

    String getString(GameMessage message);
}