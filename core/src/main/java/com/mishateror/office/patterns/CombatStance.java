package com.mishateror.office.patterns;
import java.io.Serializable;

/**
 * The interface Combat stance.
 */
public interface CombatStance extends Serializable {
    /**
     * Gets damage multiplier.
     *
     * @return the damage multiplier
     */
    float getDamageMultiplier();

    /**
     * Gets defense multiplier.
     *
     * @return the defense multiplier
     */
    float getDefenseMultiplier();

    /**
     * Gets stance name.
     *
     * @return the stance name
     */
    String getStanceName();
}
