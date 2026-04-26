package com.mishateror.office.patterns;

import com.mishateror.office.characters.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StrategyPatternTest {

    private Player player;

    @BeforeEach
    public void setUp() {
        player = new Player("TestPlayer", 100, 5, 0);
    }

    @Test
    public void testNormalStanceDefault() {
        CombatStance stance = player.getStance();
        assertNotNull(stance);
        assertEquals("Normal", stance.getStanceName());
        assertEquals(1.0f, stance.getDamageMultiplier());
        assertEquals(1.0f, stance.getDefenseMultiplier());
    }

    @Test
    public void testAggressiveStanceSwitch() {
        player.setStance(new AggressiveStance());
        CombatStance stance = player.getStance();

        assertEquals("Aggressive", stance.getStanceName());
        assertEquals(1.5f, stance.getDamageMultiplier());
        assertEquals(1.2f, stance.getDefenseMultiplier());
    }

    @Test
    public void testAggressiveStanceDamageTaken() {
        player.setStance(new AggressiveStance());

        player.takeDamage(10);

        assertEquals(88, player.getHealth());
    }
}
