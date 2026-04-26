package com.mishateror.office;

import com.mishateror.office.characters.Player;
import java.io.Serializable;

/**
 * The type Game state.
 */
public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;

    private Player player;
    private int currentFloor;

    /**
     * Instantiates a new Game state.
     *
     * @param player       the player
     * @param currentFloor the current floor
     */
    public GameState(Player player, int currentFloor) {
        this.player = player;
        this.currentFloor = currentFloor;
    }

    /**
     * Gets player.
     *
     * @return the player
     */
    public Player getPlayer() { return player; }

    /**
     * Gets current floor.
     *
     * @return the current floor
     */
    public int getCurrentFloor() { return currentFloor; }
}
