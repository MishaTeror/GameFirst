package com.mishateror.office;

import com.mishateror.office.characters.Player;
import java.io.*;

/**
 * Saves and loads the Player object to/from a binary file.
 */
public class SaveManager {
    private static final String SAVE_FILE = "savegame.dat";

    public static void savePlayer(Player player) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            oos.writeObject(player);
            System.out.println("[SERIALIZATION] Game saved successfully to " + SAVE_FILE);
        } catch (IOException e) {
            System.out.println("[SERIALIZATION ERROR] Could not save game: " + e.getMessage());
        }
    }

    public static Player loadPlayer() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            Player player = (Player) ois.readObject();
            System.out.println("[SERIALIZATION] Game loaded successfully!");
            return player;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("[SERIALIZATION] No save file found.");
            return null;
        }
    }
}
