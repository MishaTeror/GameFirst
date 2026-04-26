package com.mishateror.office.patterns;

/**
 * The type Aggressive stance.
 */
public class AggressiveStance implements CombatStance {
    @Override public float getDamageMultiplier() { return 1.5f; }
    @Override public float getDefenseMultiplier() { return 1.2f; }
    @Override public String getStanceName() { return "Aggressive"; }
}
