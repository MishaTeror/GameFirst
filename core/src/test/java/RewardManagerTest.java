package com.mishateror.office;

import com.mishateror.office.ability.Ability;
import com.mishateror.office.characters.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RewardManagerTest {

    private Player player;
    private RewardManager rewardManager;

    @BeforeEach
    public void setUp() {
        player = new Player("TestPlayer", 100, 3, 10);
        rewardManager = RewardManager.getInstance();
    }

    @Test
    public void testSingletonInstance() {
        RewardManager anotherInstance = RewardManager.getInstance();
        assertSame(rewardManager, anotherInstance, "Обидва посилання мають вказувати на один і той самий об'єкт (Singleton)");
    }

    @Test
    public void testRandomGenerators() {
        Ability randomAbility = rewardManager.getRandomAbility();
        assertNotNull(randomAbility, "Здібність не повинна бути null");
        assertTrue(randomAbility.getApCost() >= 0, "AP вартість має бути валідною");

        RewardManager.BuffType randomBuff = rewardManager.getRandomBuff();
        assertNotNull(randomBuff, "Баф не повинен бути null");
    }

    @Test
    public void testApplyBuffToPlayer() {
        int initialHp = player.getMaxHealth();
        int initialAp = player.getMaxAp();
        int initialBlock = player.getMaxBlock();
        float initialDmgMult = player.getDamageMultiplier();

        rewardManager.applyBuffToPlayer(player, RewardManager.BuffType.MAX_HP);
        assertEquals(initialHp + 5, player.getMaxHealth());
        assertEquals(initialHp + 5, player.getHealth());

        rewardManager.applyBuffToPlayer(player, RewardManager.BuffType.MAX_AP);
        assertEquals(initialAp + 1, player.getMaxAp());

        rewardManager.applyBuffToPlayer(player, RewardManager.BuffType.MAX_BLOCK);
        assertEquals(initialBlock + 5, player.getMaxBlock());

        rewardManager.applyBuffToPlayer(player, RewardManager.BuffType.DAMAGE_MULT);
        assertEquals(initialDmgMult + 0.5f, player.getDamageMultiplier(), 0.01f);
    }

    @Test
    public void testBuffDescriptions() {
        assertEquals("+5 Max HP", rewardManager.getBuffDescription(RewardManager.BuffType.MAX_HP));
        assertEquals("+1 Max AP", rewardManager.getBuffDescription(RewardManager.BuffType.MAX_AP));
        assertEquals("+5 Max Block", rewardManager.getBuffDescription(RewardManager.BuffType.MAX_BLOCK));
        assertEquals("+50% Damage", rewardManager.getBuffDescription(RewardManager.BuffType.DAMAGE_MULT));
    }
}
