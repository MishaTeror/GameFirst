package com.mishateror.office.patterns;

/**
 * The type Aggressive stance.
 */
public class AggressiveStance implements CombatStance {
    @Override public float getDamageMultiplier() { return 1.5f; } // +50% урону
    @Override public float getDefenseMultiplier() { return 1.2f; } // Отримує на 20% більше урону
    @Override public String getStanceName() { return "Aggressive"; }
}
