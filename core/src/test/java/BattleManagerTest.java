package com.mishateror.office;

import com.mishateror.office.ability.AttackAbility;
import com.mishateror.office.characters.Enemy;
import com.mishateror.office.characters.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BattleManagerTest {
    private BattleManager battleManager;
    private Player player;
    private Enemy enemy;

    @BeforeEach
    public void setUp() {
        player = new Player("Hero", 20, 6, 10);
        player.addAbility(new AttackAbility("Strike", 2, 0, 5));

        // Ворог має 15 ХП і 5 Броні
        enemy = new Enemy("Goblin", 15, 3, 5, "MONSTER");
        enemy.addAbility(new AttackAbility("Bite", 2, 0, 3));

        List<Enemy> enemies = new ArrayList<>();
        enemies.add(enemy);

        battleManager = new BattleManager(player, enemies);
    }

    @Test
    public void testInitialization() {
        battleManager.startBattle();
        assertNotNull(battleManager.getPlayer());
        assertEquals(1, battleManager.getEnemies().size());
        assertEquals("Battle Started! Your turn.", battleManager.getBattleLog());
    }

    @Test
    public void testStartBattle() {
        battleManager.startBattle();
        assertEquals(6, player.getAp());
    }

    @Test
    public void testSetAndGetBattleLog() {
        battleManager.setBattleLog("Test log");
        assertEquals("Test log", battleManager.getBattleLog());
    }

    @Test
    public void testCheckWinCondition() {
        assertFalse(battleManager.checkWinCondition());

        enemy.takeDamage(100);
        assertTrue(battleManager.checkWinCondition());
    }

    @Test
    public void testPlayerUseAbility() {
        battleManager.startBattle();
        battleManager.onPlayerUseAbility(0, enemy);

        // Ворог мав 15 ХП і 5 броні. Удар на 5 зносить броню до 0, а ХП залишається 15.
        assertEquals(15, enemy.getHealth());
        assertEquals(0, enemy.getBlock());
        assertTrue(battleManager.getBattleLog().contains("Strike"));
    }

    @Test
    public void testEndPlayerTurn() {
        battleManager.startBattle();
        battleManager.endPlayerTurn();
        assertNotNull(battleManager.getBattleLog());
    }
}
