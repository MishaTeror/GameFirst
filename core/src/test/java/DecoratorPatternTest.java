package com.mishateror.office.patterns;

import com.mishateror.office.ability.Ability;
import com.mishateror.office.ability.AttackAbility;
import com.mishateror.office.characters.Enemy;
import com.mishateror.office.characters.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DecoratorPatternTest {

    private Player player;
    private Enemy enemy;
    private Ability baseAbility;
    private Ability empoweredAbility;

    @BeforeEach
    public void setUp() {
        player = new Player("Player", 100, 5, 0);
        enemy = new Enemy("Target", 50, 3, 0, "MONSTER");
        baseAbility = new AttackAbility("Slash", 2, 0, 10);
        empoweredAbility = new EmpoweredAbility(baseAbility);
    }

    @Test
    public void testDecoratorProperties() {
        assertEquals("Empowered Slash", empoweredAbility.getName());
        assertEquals(2, empoweredAbility.getApCost());
        assertEquals(0, empoweredAbility.getCooldown());
        assertTrue(empoweredAbility.getDescription().contains("[+3 Bonus Dmg]"));
    }

    @Test
    public void testDecoratorBonusDamage() {
        empoweredAbility.use(player, enemy);

        assertEquals(37, enemy.getHealth());
    }
}
