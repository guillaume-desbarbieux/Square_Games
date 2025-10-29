package controller;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface Persistence {
    void save(Object gameMaster) throws IOException;
    GameMaster load(String title) throws IOException, ClassNotFoundException;
    void update(int oldGameId, String newGame);
}
