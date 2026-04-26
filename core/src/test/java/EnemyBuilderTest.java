package com.mishateror.office.patterns;

import com.mishateror.office.ability.AttackAbility;
import com.mishateror.office.characters.Enemy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EnemyBuilderTest {

    @Test
    public void testEnemyBuilderCreation() {
        Enemy enemy = new EnemyBuilder()
            .setName("Test Boss")
            .setStats(150, 6, 25)
            .setRole("BOSS")
            .addAbility(new AttackAbility("Slam", 3, 1, 20))
            .build();

        assertEquals("Test Boss", enemy.getName());
        assertEquals(150, enemy.getMaxHealth());
        assertEquals(150, enemy.getHealth());
        assertEquals(6, enemy.getMaxAp());
        assertEquals(25, enemy.getMaxBlock());
        assertEquals("BOSS", enemy.getRole());
        assertEquals(1, enemy.getAbilities().size());
        assertEquals("Slam", enemy.getAbilities().get(0).getName());
    }
}
