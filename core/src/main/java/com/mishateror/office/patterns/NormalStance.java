package com.mishateror.office.patterns;

/**
 * The type Normal stance.
 */
public class NormalStance implements CombatStance {
    @Override public float getDamageMultiplier() { return 1.0f; }
    @Override public float getDefenseMultiplier() { return 1.0f; }
    @Override public String getStanceName() { return "Normal"; }
}
