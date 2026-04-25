package com.mishateror.office.characters;

import com.mishateror.office.ability.Ability;
import com.mishateror.office.exceptions.NotEnoughApException;
import com.mishateror.office.patterns.CombatStance;
import com.mishateror.office.patterns.NormalStance;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Character.
 */
public abstract class Character implements GameEntity, Serializable {
    private String name;
    private int health;
    private int maxHealth;
    private int ap;
    private int maxAp;
    private int block;
    private int maxBlock;
    private List<Ability> abilities;
    private float damageMultiplier = 1.0f;

    private int poisonTurns = 0;
    private int poisonDamage = 0;

    private CombatStance currentStance;

    /**
     * Instantiates a new Character.
     *
     * @param name      the name
     * @param maxHealth the max health
     * @param maxAp     the max ap
     * @param maxBlock  the max block
     */
    public Character(String name, int maxHealth, int maxAp, int maxBlock) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.maxAp = maxAp;
        this.ap = 0;
        this.maxBlock = maxBlock;
        this.block = maxBlock;
        this.abilities = new ArrayList<>();
        this.currentStance = new NormalStance();
    }

    /**
     * On battle start.
     */
    public void onBattleStart() {
        this.ap = Math.min(this.maxAp, 4);
        for (Ability ability : abilities) {
            ability.resetCooldown();
        }
    }

    /**
     * Start turn.
     */
    public void startTurn() {
        this.ap = Math.min(this.maxAp, this.ap + 4);

        abilities.forEach(Ability::reduceCooldown);

        if (poisonTurns > 0) {
            this.health -= poisonDamage;
            if (this.health < 0) this.health = 0;
            poisonTurns--;
        }
    }

    public void takeDamage(int damage) {
        if (damage <= 0) return;

        int modifiedDamage = (int) (damage * currentStance.getDefenseMultiplier());

        int absorbed = Math.min(this.block, modifiedDamage);
        this.block -= absorbed;

        int remaining = modifiedDamage - absorbed;
        this.health -= remaining;

        if (this.health < 0) this.health = 0;
    }

    public float getDamageMultiplier() {
        return damageMultiplier * currentStance.getDamageMultiplier();
    }

    public void setStance(CombatStance stance) {
        if (stance != null) this.currentStance = stance;
    }

    public CombatStance getStance() { return currentStance; }

    public void heal(int heal) {
        if (heal <= 0) return;
        this.health = Math.min(this.maxHealth, this.health + heal);
    }

    /**
     * Add block.
     *
     * @param amount the amount
     */
    public void addBlock(int amount) {
        if (amount <= 0) return;
        this.block = Math.min(this.maxBlock, this.block + amount);
    }

    /**
     * Apply poison.
     *
     * @param turns         the turns
     * @param damagePerTurn the damage per turn
     */
    public void applyPoison(int turns, int damagePerTurn) {
        this.poisonTurns = turns;
        this.poisonDamage = damagePerTurn;
    }

    /**
     * Is poisoned boolean.
     *
     * @return the boolean
     */
    public boolean isPoisoned() {
        return poisonTurns > 0;
    }

    /**
     * Gets poison turns.
     *
     * @return the poison turns
     */
    public int getPoisonTurns() {
        return poisonTurns;
    }

    /**
     * Is status immune boolean.
     *
     * @return the boolean
     */
    public boolean isStatusImmune() {
        return this.block > 0;
    }


    /**
     * Use ability.
     *
     * @param ability the ability
     * @param target  the target
     * @throws NotEnoughApException the not enough ap exception
     */
    public void useAbility(Ability ability, Character target) throws NotEnoughApException {
        if (ability == null || target == null) return;
        if (!ability.isAvailable()) return;

        if (this.ap < ability.getApCost()) {
            throw new NotEnoughApException("Not enough AP");
        }

        this.ap -= ability.getApCost();
        ability.use(this, target);
        ability.putOnCooldown();

        }

    public boolean isDead() {
        return this.health <= 0;
    }

    /**
     * Add ability.
     *
     * @param ability the ability
     */
    public void addAbility(Ability ability) {
        if (ability != null) abilities.add(ability);
    }

    /**
     * Increase max health.
     *
     * @param amount the amount
     */
    public void increaseMaxHealth(int amount) {
        this.maxHealth += amount;
        this.health += amount;
    }

    /**
     * Increase max ap.
     *
     * @param amount the amount
     */
    public void increaseMaxAp(int amount) {
        this.maxAp += amount;
    }

    /**
     * Increase max block.
     *
     * @param amount the amount
     */
    public void increaseMaxBlock(int amount) {
        this.maxBlock += amount;
    }

    /**
     * Add damage multiplier.
     *
     * @param amount the amount
     */
    public void addDamageMultiplier(float amount) {
        this.damageMultiplier += amount;
    }

    /**
     * Gets damage multiplier.
     *
     * @return the damage multiplier
     */
    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }

    /**
     * Gets ap.
     *
     * @return the ap
     */
    public int getAp() { return ap; }

    /**
     * Gets max ap.
     *
     * @return the max ap
     */
    public int getMaxAp() { return maxAp; }

    /**
     * Gets block.
     *
     * @return the block
     */
    public int getBlock() { return block; }

    /**
     * Gets max block.
     *
     * @return the max block
     */
    public int getMaxBlock() { return maxBlock; }

    /**
     * Gets abilities.
     *
     * @return the abilities
     */
    public List<Ability> getAbilities() { return abilities; }
}
