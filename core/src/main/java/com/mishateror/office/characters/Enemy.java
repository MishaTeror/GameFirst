package com.mishateror.office.characters;

import com.mishateror.office.ability.Ability;
import com.mishateror.office.exceptions.NotEnoughApException;
import java.util.logging.Logger;

/**
 * The type Enemy.
 */
public class Enemy extends Character {
    private String role;
    private static final Logger LOGGER = Logger.getLogger(Enemy.class.getName());

    /**
     * Instantiates a new Enemy.
     *
     * @param name      the name
     * @param maxHealth the max health
     * @param maxAp     the max ap
     * @param maxBlock  the max block
     * @param role      the role
     */
    public Enemy(String name, int maxHealth, int maxAp, int maxBlock, String role) {
        super(name, maxHealth, maxAp, maxBlock);
        this.role = role;
    }

    /**
     * Gets role.
     *
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * Take turn string.
     *
     * @param target the target
     * @return the string
     */
    public String takeTurn(Character target) {
        for (Ability ability : getAbilities()) {
            if (ability.isAvailable()) {
                try {
                    this.useAbility(ability, target);
                    LOGGER.info(this.getName() + " used " + ability.getName() + " on " + target.getName());
                    return ability.getName();
                } catch (NotEnoughApException e) {
                    LOGGER.info(this.getName() + " failed to use " + ability.getName() + ": " + e.getMessage());
                }
            }
        }
        return null;
    }
}
