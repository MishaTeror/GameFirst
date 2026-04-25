package com.mishateror.office.ability;

import com.mishateror.office.characters.Character;
import java.io.Serializable;

/**
 * Abstract base class representing a usable skill or action in combat.
 * Abilities use the Strategy Design Pattern, allowing characters to swap combat behaviors dynamically.
 */
public abstract class Ability implements Serializable{
    private String name;
    private int apCost;
    private int cooldown;
    private int currentCooldown;

    /**
     * Instantiates a new Ability.
     *
     * @param name     the name
     * @param apCost   the ap cost
     * @param cooldown the cooldown
     */
    public Ability(String name, int apCost, int cooldown) {
        this.name = name;
        this.apCost = apCost;
        this.cooldown = cooldown;
        this.currentCooldown = 0;
    }

    /**
     * Executes the core logic of the ability.
     *
     * @param caster The character casting the ability.
     * @param target The character targeted by the ability.
     */
    public abstract void use(Character caster, Character target);

    /**
     * Gets description.
     *
     * @return A string containing the ability's description.
     */
    public abstract String getDescription();

    /**
     * Is available boolean.
     *
     * @return the boolean
     */
    public boolean isAvailable() {
        return currentCooldown <= 0;
    }

    /**
     * Put on cooldown.
     */
    public void putOnCooldown() {
        this.currentCooldown = cooldown;
    }

    /**
     * Decreases the current cooldown by 1 turn.
     */
    public void reduceCooldown() {
        if (this.currentCooldown > 0) {
            this.currentCooldown--;
        }
    }

    /**
     * Reset cooldown.
     */
    public void resetCooldown() {
        this.currentCooldown = 0;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() { return name; }

    /**
     * Gets ap cost.
     *
     * @return the ap cost
     */
    public int getApCost() { return apCost; }

    /**
     * Gets cooldown.
     *
     * @return the cooldown
     */
    public int getCooldown() { return cooldown; }

    /**
     * Gets current cooldown.
     *
     * @return the current cooldown
     */
    public int getCurrentCooldown() { return currentCooldown; }
}
