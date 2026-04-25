package com.mishateror.office.ability;

import com.mishateror.office.characters.Character;

/**
 * The type Poison ability.
 */
public class PoisonAbility extends Ability {
    private int directDamage;
    private int poisonTurns;
    private int poisonDamagePerTurn;

    /**
     * Instantiates a new Poison ability.
     *
     * @param name                the name
     * @param apCost              the ap cost
     * @param cooldown            the cooldown
     * @param directDamage        the direct damage
     * @param poisonTurns         the poison turns
     * @param poisonDamagePerTurn the poison damage per turn
     */
    public PoisonAbility(String name, int apCost, int cooldown, int directDamage, int poisonTurns, int poisonDamagePerTurn) {
        super(name, apCost, cooldown);
        this.directDamage = directDamage;
        this.poisonTurns = poisonTurns;
        this.poisonDamagePerTurn = poisonDamagePerTurn;
    }

    @Override
    public void use(Character caster, Character target) {
        if (directDamage > 0) {
            int finalDamage = (int) (directDamage * caster.getDamageMultiplier());
            target.takeDamage(finalDamage);
        }
        target.applyPoison(poisonTurns, poisonDamagePerTurn);
    }

    @Override
    public String getDescription() {
        return "Hit: " + directDamage + " dmg. Poison: " + poisonDamagePerTurn + " dmg/turn (" + poisonTurns + " turns).";
    }
}
