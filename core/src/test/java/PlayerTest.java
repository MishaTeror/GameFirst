package com.mishateror.office.characters;

import com.mishateror.office.ability.AttackAbility;
import com.mishateror.office.exceptions.NotEnoughApException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    private Player player;
    private Enemy enemy;

    @BeforeEach
    public void setUp() {
        player = new Player("Korben", 100, 5, 20);
        enemy = new Enemy("Goblin", 50, 3, 0, "MONSTER");
    }

    @Test
    public void testInitialization() {
        assertEquals("Korben", player.getName());
        assertEquals(100, player.getHealth());
        assertEquals(100, player.getMaxHealth());
        assertEquals(0, player.getAp());
        assertEquals(20, player.getBlock());
    }

    @Test
    public void testTakeDamageWithoutBlock() {
        player.takeDamage(20);
        assertEquals(100, player.getHealth());
        assertEquals(0, player.getBlock());

        player.takeDamage(10);
        assertEquals(90, player.getHealth());
    }

    @Test
    public void testHeal() {
        player.takeDamage(50);
        player.heal(20);
        assertEquals(90, player.getHealth());

        player.heal(50);
        assertEquals(100, player.getHealth());
    }

    @Test
    public void testAddBlock() {
        player.takeDamage(30);
        assertEquals(90, player.getHealth());

        player.addBlock(10);
        assertEquals(10, player.getBlock());

        player.addBlock(15);
        assertEquals(20, player.getBlock());
    }

    @Test
    public void testOnBattleStartAndStartTurn() {
        player.onBattleStart();
        assertEquals(4, player.getAp());

        player.startTurn();
        assertEquals(5, player.getAp());
    }

    @Test
    public void testUseAbilitySuccess() throws NotEnoughApException {
        AttackAbility attack = new AttackAbility("Strike", 2, 0, 10);
        player.addAbility(attack);
        player.onBattleStart();

        player.useAbility(attack, enemy);

        assertEquals(2, player.getAp());
        assertEquals(40, enemy.getHealth());
    }

    @Test
    public void testUseAbilityThrowsException() {
        AttackAbility attack = new AttackAbility("Heavy Strike", 6, 0, 20);
        player.addAbility(attack);
        player.onBattleStart();

        assertThrows(NotEnoughApException.class, () -> {
            player.useAbility(attack, enemy);
        });
    }

    @Test
    public void testPoisonEffect() {
        player.applyPoison(2, 5);
        assertTrue(player.isPoisoned());
        assertEquals(2, player.getPoisonTurns());

        player.startTurn();
        assertEquals(95, player.getHealth());
        assertEquals(1, player.getPoisonTurns());

        player.startTurn();
        assertEquals(90, player.getHealth());
        assertFalse(player.isPoisoned());
    }
}
