package com.mishateror.office;

import com.mishateror.office.characters.Player;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RewardManagerTest {

    @Test
    public void testApplyBuffToPlayer() {
        Player p = new Player("Hero", 20, 5, 10);

        RewardManager.applyBuffToPlayer(p, RewardManager.BuffType.MAX_HP);
        assertEquals(25, p.getMaxHealth());

        RewardManager.applyBuffToPlayer(p, RewardManager.BuffType.MAX_AP);
        assertEquals(6, p.getMaxAp());

        RewardManager.applyBuffToPlayer(p, RewardManager.BuffType.MAX_BLOCK);
        assertEquals(15, p.getMaxBlock());

        RewardManager.applyBuffToPlayer(p, RewardManager.BuffType.DAMAGE_MULT);
        assertEquals(1.5f, p.getDamageMultiplier(), 0.01f);
    }

    @Test
    public void testRandomGenerators() {
        assertNotNull(RewardManager.getRandomAbility());
        assertNotNull(RewardManager.getRandomBuff());
    }

    @Test
    public void testBuffDescriptions() {
        assertNotEquals("", RewardManager.getBuffDescription(RewardManager.BuffType.MAX_HP));
        assertNotEquals("", RewardManager.getBuffDescription(RewardManager.BuffType.MAX_AP));
        assertNotEquals("", RewardManager.getBuffDescription(RewardManager.BuffType.MAX_BLOCK));
        assertNotEquals("", RewardManager.getBuffDescription(RewardManager.BuffType.DAMAGE_MULT));
    }
}
