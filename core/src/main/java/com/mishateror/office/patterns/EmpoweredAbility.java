package com.mishateror.office.patterns;

import com.mishateror.office.ability.Ability;
import com.mishateror.office.characters.Character;

/**
 * The type Empowered ability.
 */
public class EmpoweredAbility extends Ability {
    private Ability baseAbility;

    /**
     * Instantiates a new Empowered ability.
     *
     * @param baseAbility the base ability
     */
    public EmpoweredAbility(Ability baseAbility) {
        super("Empowered " + baseAbility.getName(), baseAbility.getApCost(), baseAbility.getCooldown());
        this.baseAbility = baseAbility;
    }

    @Override
    public void use(Character caster, Character target) {
        baseAbility.use(caster, target);
        target.takeDamage(3);
    }

    @Override
    public String getDescription() {
        return baseAbility.getDescription() + " [+3 Bonus Dmg]";
    }
}
