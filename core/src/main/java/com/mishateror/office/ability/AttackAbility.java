package com.mishateror.office.ability;

import com.mishateror.office.characters.Character;

/**
 * Implementation of Ability that deals direct damage to a target.
 */
public class AttackAbility extends Ability {
    private int damage;

    /**
     * Instantiates a new Attack ability.
     *
     * @param name     the name
     * @param apCost   the ap cost
     * @param cooldown the cooldown
     * @param damage   the damage
     */
    public AttackAbility(String name, int apCost, int cooldown, int damage) {
        super(name, apCost, cooldown);
        this.damage = damage;
    }

    @Override
    public void use(Character caster, Character target) {
        int finalDamage = (int) (damage * caster.getDamageMultiplier());
        target.takeDamage(finalDamage);
    }

    @Override
    public String getDescription() {
        return "Deals " + damage + " damage.";
    }

    /**
     * Gets damage.
     *
     * @return the damage
     */
    public int getDamage() {
        return damage;
    }
}
