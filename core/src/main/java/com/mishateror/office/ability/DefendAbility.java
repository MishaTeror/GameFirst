package com.mishateror.office.ability;

import com.mishateror.office.characters.Character;

/**
 * Implementation of Ability that grants block (armor) to the caster, mitigating incoming damage.
 */
public class DefendAbility extends Ability {
    private int bonusBlock;

    /**
     * Instantiates a new Defend ability.
     *
     * @param name       the name
     * @param apCost     the ap cost
     * @param cooldown   the cooldown
     * @param bonusBlock the bonus block
     */
    public DefendAbility(String name, int apCost, int cooldown, int bonusBlock) {
        super(name, apCost, cooldown);
        this.bonusBlock = bonusBlock;
    }

    @Override
    public void use(Character caster, Character target) {
        caster.addBlock(bonusBlock);
    }

    @Override
    public String getDescription() {
        return "Grants " + bonusBlock + " block.";
    }

    /**
     * Gets bonus block.
     *
     * @return the bonus block
     */
    public int getBonusBlock() {
        return bonusBlock;
    }
}
