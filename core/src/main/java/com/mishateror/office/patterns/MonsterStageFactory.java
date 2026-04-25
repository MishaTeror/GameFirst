package com.mishateror.office.patterns;

import com.mishateror.office.ability.Ability;
import com.mishateror.office.ability.AttackAbility;
import com.mishateror.office.characters.Enemy;

public class MonsterStageFactory implements StageFactory {
    @Override
    public Enemy createEnemy(int floor) {
        return new EnemyBuilder()
            .setName("Werewolf Lvl." + floor)
            .setStats(10 + floor * 2, 3, 5)
            .setRole("MINION")
            .addAbility(new AttackAbility("Quick Stab", 1, 0, 3))
            .build();
    }

    @Override
    public Ability createStageReward() {
        return new AttackAbility("Axe Strike", 2, 0, 6);
    }
}
