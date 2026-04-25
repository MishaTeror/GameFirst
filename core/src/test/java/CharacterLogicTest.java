package com.mishateror.office.characters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CharacterLogicTest {
    private Player player;

    @BeforeEach
    public void setUp() {
        player = new Player("Hero", 20, 5, 10);
    }

    @Test
    public void testInitialStats() {
        assertEquals(20, player.getHealth());
        assertEquals(20, player.getMaxHealth());
        assertEquals(10, player.getBlock());
        assertEquals(0, player.getAp());
        assertFalse(player.isDead());
    }

    @Test
    public void testTakeDamageWithoutBlock() {
        Player unprotected = new Player("Dummy", 20, 5, 0);
        unprotected.takeDamage(5);
        assertEquals(15, unprotected.getHealth());
    }

    @Test
    public void testTakeDamageWithBlock() {
        player.takeDamage(5);
        assertEquals(20, player.getHealth());
        assertEquals(5, player.getBlock());

        player.takeDamage(10);
        assertEquals(15, player.getHealth());
        assertEquals(0, player.getBlock());
    }

    @Test
    public void testHeal() {
        player.takeDamage(15);
        player.heal(5);
        assertEquals(20, player.getHealth());

        player.heal(10);
        assertEquals(20, player.getHealth());
    }

    @Test
    public void testApManagement() {
        player.onBattleStart();
        assertEquals(4, player.getAp());

        player.startTurn();
        assertEquals(5, player.getAp());
    }

    @Test
    public void testPoison() {
        player.applyPoison(2, 3);
        assertTrue(player.isPoisoned());
        assertEquals(2, player.getPoisonTurns());

        player.startTurn();
        assertEquals(17, player.getHealth());
        assertEquals(1, player.getPoisonTurns());

        player.startTurn();
        assertEquals(14, player.getHealth());
        assertFalse(player.isPoisoned());
    }

    @Test
    public void testStatIncreases() {
        player.increaseMaxHealth(5);
        assertEquals(25, player.getMaxHealth());
        assertEquals(25, player.getHealth());

        player.increaseMaxAp(2);
        assertEquals(7, player.getMaxAp());

        player.increaseMaxBlock(5);
        assertEquals(15, player.getMaxBlock());

        player.addDamageMultiplier(0.5f);
        assertEquals(1.5f, player.getDamageMultiplier(), 0.01f);
    }
}
