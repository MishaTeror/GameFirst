package com.mishateror.office;

import com.mishateror.office.ability.Ability;
import com.mishateror.office.ability.AttackAbility;
import com.mishateror.office.ability.DefendAbility;
import com.mishateror.office.ability.PoisonAbility;
import com.mishateror.office.characters.Player;

import java.util.Random;

/**
 * The type Reward manager.
 */
public class RewardManager {

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
            case MAX_HP:
                player.increaseMaxHealth(5);
                break;
            case MAX_AP:
                player.increaseMaxAp(1);
                break;
            case MAX_BLOCK:
                player.increaseMaxBlock(5);
                break;
            case DAMAGE_MULT:
                player.addDamageMultiplier(0.5f);
                break;
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
        Random rand = new Random();
        int r = rand.nextInt(6);
        switch (r) {
            case 0: return new AttackAbility("Fireball", 3, 1, 14);
            case 1: return new AttackAbility("Quick Slash", 1, 0, 5);
            case 2: return new AttackAbility("Sniper Shot", 4, 0, 22);
            case 3: return new DefendAbility("Iron Wall", 2, 2, 8);
            case 4: return new DefendAbility("Dodge", 1, 1, 4);
            case 5: return new PoisonAbility("Venom Flask", 2, 2, 4, 3, 3);
            default: return new AttackAbility("Basic Strike", 2, 0, 5);
        }
    }

    /**
     * Gets random buff.
     *
     * @return the random buff
     */
    public static BuffType getRandomBuff() {
        Random rand = new Random();
        BuffType[] buffs = BuffType.values();
        return buffs[rand.nextInt(buffs.length)];
    }
}
