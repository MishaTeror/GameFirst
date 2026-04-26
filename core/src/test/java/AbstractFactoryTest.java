package com.mishateror.office.patterns;

import com.mishateror.office.ability.Ability;
import com.mishateror.office.characters.Enemy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AbstractFactoryTest {

    @Test
    public void testMonsterStageFactory() {
        StageFactory factory = new MonsterStageFactory();

        Enemy enemy = factory.createEnemy(2);
        assertNotNull(enemy);
        assertTrue(enemy.getName().contains("Goblin Lvl.2"));
        assertEquals("MINION", enemy.getRole());
        assertEquals(14, enemy.getMaxHealth());
        assertFalse(enemy.getAbilities().isEmpty());

        Ability reward = factory.createStageReward();
        assertNotNull(reward);
        assertEquals("Axe Strike", reward.getName());
        assertFalse(reward instanceof EmpoweredAbility);
    }

    @Test
    public void testBossStageFactory() {
        StageFactory factory = new BossStageFactory();

        Enemy boss = factory.createEnemy(5);
        assertNotNull(boss);
        assertTrue(boss.getName().contains("Alpha Werewolf Lvl.5"));
        assertEquals("BOSS", boss.getRole());
        assertEquals(55, boss.getMaxHealth());
        assertFalse(boss.getAbilities().isEmpty());

        Ability reward = factory.createStageReward();
        assertNotNull(reward);
        assertTrue(reward instanceof EmpoweredAbility);
        assertTrue(reward.getName().contains("Empowered Savage Claw"));
    }
}
