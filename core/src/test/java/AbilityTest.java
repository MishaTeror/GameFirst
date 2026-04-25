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
        player = new Player("Hero", 20, 5, 10);
        enemy = new Enemy("Monster", 20, 5, 10, "Beast");
        player.onBattleStart();
    }

    @Test
    public void testAttackAbility() {
        AttackAbility strike = new AttackAbility("Strike", 2, 0, 5);
        player.addAbility(strike);

        player.useAbility(strike, enemy);
        // Ворог мав 10 броні. Удар на 5 знімає 5 броні, ХП залишається 20.
        assertEquals(20, enemy.getHealth());
        assertEquals(5, enemy.getBlock());
        assertEquals(2, player.getAp());

        assertEquals(5, strike.getDamage());
        assertEquals("Deals 5 damage.", strike.getDescription());
        assertEquals("Strike", strike.getName());
        assertEquals(2, strike.getApCost());
        assertEquals(0, strike.getCooldown());
    }

    @Test
    public void testDefendAbility() {
        DefendAbility shield = new DefendAbility("Shield", 1, 0, 5);
        player.addAbility(shield);

        player.takeDamage(10); // Збиваємо стартову броню гравця до 0

        player.useAbility(shield, player);
        assertEquals(5, player.getBlock()); // Тепер щит дає 5 броні

        assertEquals(5, shield.getBonusBlock());
        assertEquals("Grants 5 block.", shield.getDescription());
    }

    @Test
    public void testPoisonAbility() {
        PoisonAbility venom = new PoisonAbility("Venom", 2, 0, 2, 3, 4);
        player.addAbility(venom);

        player.useAbility(venom, enemy);
        // Удар на 2 знімає 2 броні. ХП залишається 20.
        assertEquals(20, enemy.getHealth());
        assertEquals(8, enemy.getBlock());
        assertTrue(enemy.isPoisoned());
        assertEquals(3, enemy.getPoisonTurns());

        assertTrue(venom.getDescription().contains("Hit: 2 dmg"));
    }

    @Test
    public void testCooldownSystem() {
        AttackAbility heavy = new AttackAbility("Heavy", 2, 2, 10);
        player.addAbility(heavy);

        player.useAbility(heavy, enemy);
        assertFalse(heavy.isAvailable());
        assertEquals(2, heavy.getCurrentCooldown());

        heavy.reduceCooldown();
        assertEquals(1, heavy.getCurrentCooldown());
        assertFalse(heavy.isAvailable());

        heavy.reduceCooldown();
        assertTrue(heavy.isAvailable());

        heavy.putOnCooldown();
        heavy.resetCooldown();
        assertTrue(heavy.isAvailable());
    }

    @Test
    public void testNotEnoughAp() {
        AttackAbility ult = new AttackAbility("Ult", 10, 0, 50);
        player.addAbility(ult);

        player.useAbility(ult, enemy);
        assertEquals(20, enemy.getHealth());
    }
}
