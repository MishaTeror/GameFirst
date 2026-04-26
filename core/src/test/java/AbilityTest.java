package com.mishateror.office.ability;

import com.mishateror.office.characters.Enemy;
import com.mishateror.office.characters.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AbilityTest {

    private Player player;
    private Enemy enemy;

    @BeforeEach
    public void setUp() {
        player = new Player("Player", 100, 5, 15);
        enemy = new Enemy("Enemy", 100, 3, 0, "MONSTER");
    }

    @Test
    public void testDefendAbility() {
        DefendAbility defend = new DefendAbility("Shield", 1, 0, 15);
        defend.use(player, enemy);

        assertEquals(15, player.getBlock());
    }

    @Test
    public void testPoisonAbility() {
        PoisonAbility poison = new PoisonAbility("Toxic", 2, 0, 5, 3, 4);
        poison.use(player, enemy);

        assertEquals(95, enemy.getHealth());
        assertTrue(enemy.isPoisoned());
        assertEquals(3, enemy.getPoisonTurns());
    }

    @Test
    public void testAbilityCooldown() {
        AttackAbility attack = new AttackAbility("Strike", 1, 2, 10);
        assertTrue(attack.isAvailable());

        attack.putOnCooldown();
        assertFalse(attack.isAvailable());
        assertEquals(2, attack.getCurrentCooldown());

        attack.reduceCooldown();
        assertEquals(1, attack.getCurrentCooldown());
        assertFalse(attack.isAvailable());

        attack.reduceCooldown();
        assertTrue(attack.isAvailable());
    }
}
