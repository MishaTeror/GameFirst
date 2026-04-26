package com.mishateror.office;

import com.mishateror.office.ability.Ability;
import com.mishateror.office.ability.AttackAbility;
import com.mishateror.office.ability.DefendAbility;
import com.mishateror.office.ability.PoisonAbility;
import com.mishateror.office.characters.Player;

/**
 * The type Reward manager.
 */
public class RewardManager {

    private static RewardManager instance;

    private static RandomPool<Ability> abilityPool;
    private static RandomPool<BuffType> buffPool;

    private RewardManager() {
        abilityPool = new RandomPool<>();
        buffPool = new RandomPool<>();

        abilityPool.add(new AttackAbility("Fireball", 3, 1, 14));
        abilityPool.add(new AttackAbility("Quick Slash", 1, 0, 5));
        abilityPool.add(new AttackAbility("Sniper Shot", 4, 0, 22));
        abilityPool.add(new DefendAbility("Iron Wall", 2, 2, 8));
        abilityPool.add(new DefendAbility("Dodge", 1, 1, 4));
        abilityPool.add(new PoisonAbility("Venom Flask", 2, 2, 4, 3, 3));

        for (BuffType buff : BuffType.values()) {
            buffPool.add(buff);
        }
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static RewardManager getInstance() {
        if (instance == null) {
            instance = new RewardManager();
        }
        return instance;
    }

    /**
     * The enum Buff type.
     */
    public enum BuffType {
        /**
         * Max hp buff type.
         */
        MAX_HP,
        /**
         * Max ap buff type.
         */
        MAX_AP,
        /**
         * Max block buff type.
         */
        MAX_BLOCK,
        /**
         * Damage mult buff type.
         */
        DAMAGE_MULT
    }

    /**
     * Apply buff to player.
     *
     * @param player the player
     * @param buff   the buff
     */
    public static void applyBuffToPlayer(Player player, BuffType buff) {
        switch (buff) {
            case MAX_HP: player.increaseMaxHealth(5); break;
            case MAX_AP: player.increaseMaxAp(1); break;
            case MAX_BLOCK: player.increaseMaxBlock(5); break;
            case DAMAGE_MULT: player.addDamageMultiplier(0.5f); break;
        }
    }

    /**
     * Gets buff description.
     *
     * @param buff the buff
     * @return the buff description
     */
    public static String getBuffDescription(BuffType buff) {
        switch (buff) {
            case MAX_HP: return "+5 Max HP";
            case MAX_AP: return "+1 Max AP";
            case MAX_BLOCK: return "+5 Max Block";
            case DAMAGE_MULT: return "+50% Damage";
            default: return "";
        }
    }

    /**
     * Gets random ability.
     *
     * @return the random ability
     */
    public static Ability getRandomAbility() {
        return abilityPool.getRandom();
    }

    /**
     * Gets random buff.
     *
     * @return the random buff
     */
    public static BuffType getRandomBuff() {
        return buffPool.getRandom();
    }
}
