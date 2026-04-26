package com.mishateror.office;

import java.io.*;

public class SaveManager {
    private static final String SAVE_FILE = "savegame.dat";

    public static void saveGame(GameState state) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            oos.writeObject(state);
            System.out.println("[SERIALIZATION] Game saved successfully to " + SAVE_FILE);
        } catch (IOException e) {
            System.out.println("[SERIALIZATION ERROR] Could not save game: " + e.getMessage());
        }
    }

    public static GameState loadGame() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            GameState state = (GameState) ois.readObject();
            System.out.println("[SERIALIZATION] Game loaded successfully!");
            return state;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("[SERIALIZATION] No save file found.");
            return null;
        }
    }
}
