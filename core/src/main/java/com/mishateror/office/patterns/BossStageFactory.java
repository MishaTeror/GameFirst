package com.mishateror.office.patterns;

import com.mishateror.office.ability.Ability;
import com.mishateror.office.ability.AttackAbility;
import com.mishateror.office.characters.Enemy;

public class BossStageFactory implements StageFactory {
    @Override
    public Enemy createEnemy(int floor) {

        Ability baseBite = new AttackAbility("Lethal Bite", 3, 1, 10);

        Ability empoweredBite = new EmpoweredAbility(baseBite);

        return new EnemyBuilder()
            .setName("Alpha Werewolf Lvl." + floor)
            .setStats(30 + floor * 5, 5, 15)
            .setRole("BOSS")
            .addAbility(empoweredBite)
            .build();
    }

    @Override
    public Ability createStageReward() {
        Ability base = new AttackAbility("Savage Claw", 3, 1, 12);
        return new EmpoweredAbility(base);
    }
}
