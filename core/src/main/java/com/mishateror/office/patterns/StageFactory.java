package com.mishateror.office.patterns;

import com.mishateror.office.ability.Ability;
import com.mishateror.office.characters.Enemy;

/**
 * The interface Stage factory.
 */
public interface StageFactory {
    /**
     * Create enemy enemy.
     *
     * @param floor the floor
     * @return the enemy
     */
    Enemy createEnemy(int floor);

    /**
     * Create stage reward ability.
     *
     * @return the ability
     */
    Ability createStageReward();
}
