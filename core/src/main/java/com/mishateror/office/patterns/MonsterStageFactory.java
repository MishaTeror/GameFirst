package com.mishateror.office.patterns;

import com.mishateror.office.ability.Ability;
import com.mishateror.office.ability.AttackAbility;
import com.mishateror.office.ability.DefendAbility;
import com.mishateror.office.ability.PoisonAbility;
import com.mishateror.office.characters.Enemy;

/**
 * The type Monster stage factory.
 */
public class MonsterStageFactory implements StageFactory {
    @Override
    public Enemy createEnemy(int floor) {
        return new EnemyBuilder()
            .setName("Goblin Lvl." + floor)
            .setStats(10 + floor * 2, 4, 5)
            .setRole("MINION")
            .addAbility(new AttackAbility("Heavy Smash", 2, 2, 6 + floor))
            .addAbility(new PoisonAbility("Toxic Spit", 2, 3, 2, 2, 2))
            .addAbility(new DefendAbility("Block", 1, 2, 5))
            .addAbility(new AttackAbility("Quick Stab", 1, 0, 3 + floor/2))
            .build();
    }

    @Override
    public Ability createStageReward() {
        return new AttackAbility("Axe Strike", 2, 0, 6);
    }
}
