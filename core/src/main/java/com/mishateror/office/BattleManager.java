package com.mishateror.office;

import com.mishateror.office.ability.Ability;
import com.mishateror.office.characters.Enemy;
import com.mishateror.office.characters.Player;
import com.mishateror.office.exceptions.NotEnoughApException;
import com.mishateror.office.screens.BattleRenderer;
import java.util.logging.Logger;
import java.util.logging.Level;

import java.util.List;

/**
 * The type Battle manager.
 */
public class BattleManager {
    private Player player;
    private List<Enemy> enemies;
    private boolean isPlayerTurn;
    private boolean gameOver;
    private String battleLog;
    private static final Logger LOGGER = Logger.getLogger(BattleManager.class.getName());

    /**
     * Instantiates a new Battle manager.
     *
     * @param player  the player
     * @param enemies the enemies
     */
    public BattleManager(Player player, List<Enemy> enemies) {
        this.player = player;
        this.enemies = enemies;
        this.isPlayerTurn = true;
        this.gameOver = false;
        this.battleLog = "Battle Started! Your turn.";
    }

    /**
     * Start battle.
     */
    public void startBattle() {
        player.onBattleStart();
        setBattleLog("Battle Started! Your turn.");
        for (Enemy enemy : enemies) {
            enemy.onBattleStart();
        }
        player.startTurn();
        LOGGER.info("Battle started! Player against " + enemies.get(0).getName());
    }

    /**
     * On player use ability.
     *
     * @param abilityIndex the ability index
     * @param target       the target
     */
    public void onPlayerUseAbility(int abilityIndex, Enemy target) {
        if (!isPlayerTurn || gameOver) return;

        List<Ability> abilities = player.getAbilities();
        if (abilityIndex < 0 || abilityIndex >= abilities.size()) return;

        Ability selectedAbility = abilities.get(abilityIndex);

        if (!selectedAbility.isAvailable()) {
            battleLog = "Ability is on cooldown!";
            return;
        }
        try {
            player.useAbility(selectedAbility, target);
            setBattleLog("Player used " + selectedAbility.getName() + "!");
            LOGGER.info("Player successfully used " + selectedAbility.getName() + " on " + target.getName());
        } catch (NotEnoughApException e) {
            // Якщо вилетів виняток, пишемо його повідомлення в лог гри і в консоль
            setBattleLog(e.getMessage());
            LOGGER.warning("Failed to use ability: " + e.getMessage());
        }
        checkWinCondition();
    }

    /**
     * End player turn.
     */
    public void endPlayerTurn() {
        if (!isPlayerTurn || gameOver) return;

        isPlayerTurn = false;
        battleLog = "Turn passed to enemies...";
    }

    /**
     * Execute enemy turn.
     *
     * @param renderer the renderer
     */
    public void executeEnemyTurn(BattleRenderer renderer) {
        if (gameOver) return;

        for (Enemy enemy : enemies) {
            if (enemy.isDead()) continue;

            enemy.startTurn();

            boolean abilityUsed = false;

            for (Ability ability : enemy.getAbilities()) {
                if (ability.isAvailable()) {
                    try {
                        enemy.useAbility(ability, player);
                        setBattleLog("Enemy used " + ability.getName() + "!");
                        LOGGER.info("Enemy used " + ability.getName() + " on " + player.getName());
                        abilityUsed = true;
                        break;
                    } catch (NotEnoughApException e) {
                        LOGGER.info("Enemy failed to use " + ability.getName() + ", trying next one.");
                    }
                }
            }

            if (abilityUsed) {
                renderer.triggerEnemyAttack();
                battleLog += "\nEnemy " + enemy.getName() + " attacked you!";
            } else {
                battleLog += "\nEnemy " + enemy.getName() + " is resting (Not enough AP/Cooldowns).";
            }
        }

        checkWinCondition();

        if (!gameOver) {
            isPlayerTurn = true;
            player.startTurn();
            battleLog += "\nYour turn!";
        }
    }

    /**
     * Check win condition boolean.
     *
     * @return the boolean
     */
    public boolean checkWinCondition() {
        if (player.isDead()) {
            gameOver = true;
            battleLog = "Game Over. Korben is fired...";
            return false;
        }

        boolean allEnemiesDead = true;
        for (Enemy e : enemies) {
            if (!e.isDead()) {
                allEnemiesDead = false;
                break;
            }
        }

        if (allEnemiesDead) {
            gameOver = true;
            battleLog = "Victory! Office cleared!";

            ReflectionDebugger.printObjectState(player);

            return true;
        }
        return false;
    }

    /**
     * Set battle log.
     *
     * @param s the s
     */
    public void setBattleLog(String s){this.battleLog = s;}

    /**
     * Gets player.
     *
     * @return the player
     */
    public Player getPlayer() { return player; }

    /**
     * Gets enemies.
     *
     * @return the enemies
     */
    public List<Enemy> getEnemies() { return enemies; }

    /**
     * Gets battle log.
     *
     * @return the battle log
     */
    public String getBattleLog() { return battleLog; }

    /**
     * Is player turn boolean.
     *
     * @return the boolean
     */
    public boolean isPlayerTurn() { return isPlayerTurn; }

    /**
     * Is game over boolean.
     *
     * @return the boolean
     */
    public boolean isGameOver() { return gameOver; }
}
