package com.mishateror.office.characters;

/**
 * Interface representing a core living entity in the game. Defines the standard contract
 * for health management and entity status.
 */
public interface GameEntity {
    /**
     * Gets name.
     *
     * @return the name
     */
    String getName();

    /**
     * Gets health.
     *
     * @return the health
     */
    int getHealth();

    /**
     * Gets max health.
     *
     * @return the max health
     */
    int getMaxHealth();

    /**
     * Is dead boolean.
     *
     * @return the boolean
     */
    boolean isDead();

    /**
     * Reduces the entity's health based on the given damage.
     *
     * @param damage The amount of damage to take.
     */
    void takeDamage(int damage);

    /**
     * Restores the entity's health up to its maximum capacity.
     *
     * @param amount The amount of health to restore.
     */
    void heal(int amount);
}
