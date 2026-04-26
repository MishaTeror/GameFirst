package com.mishateror.office.patterns;

import com.mishateror.office.ability.Ability;
import com.mishateror.office.ability.AttackAbility;
import com.mishateror.office.ability.DefendAbility;
import com.mishateror.office.characters.Enemy;

/**
 * The type Boss stage factory.
 */
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
            .addAbility(new DefendAbility("Thick Fur", 2, 3, 10))
            .addAbility(new AttackAbility("Claw Slash", 1, 0, 5))
            .build();
    }

    @Override
    public Ability createStageReward() {
        Ability base = new AttackAbility("Savage Claw", 3, 1, 12);
        return new EmpoweredAbility(base);
    }
}
