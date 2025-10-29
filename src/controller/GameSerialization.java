package controller;

import java.io.*;

public class GameSerialization implements Serializable, Persistence {

    @Override
    public void save(Object gameMaster) throws IOException {
        String fileName = gameMaster.toString();
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("saves/"+ fileName + ".ser"));
        out.writeObject(gameMaster);
        out.close();
        System.out.println("Saved GameMaster to test.ser");
    }

    @Override
    public GameMaster load(String title) throws IOException, ClassNotFoundException {
        ObjectInputStream loadedFile = new ObjectInputStream(new FileInputStream("saves/" + title + ".ser"));

        GameMaster loadedGameMaster = (GameMaster) loadedFile.readObject();
        loadedFile.close();

        System.out.println("Objet chargé : " + loadedGameMaster);
        return  loadedGameMaster;
    }

    @Override
    public void update(int oldGameId, String newGame) {

    }
}
