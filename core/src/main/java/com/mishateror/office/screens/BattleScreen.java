package com.mishateror.office.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.mishateror.office.MainGame;
import com.mishateror.office.BattleManager;
import com.mishateror.office.RewardManager;
import com.mishateror.office.characters.Player;
import com.mishateror.office.characters.Enemy;
import com.mishateror.office.ability.AttackAbility;
import com.mishateror.office.ui.BattleUI;
import com.mishateror.office.patterns.StageFactory;
import com.mishateror.office.patterns.MonsterStageFactory;
import com.mishateror.office.patterns.BossStageFactory;
import com.mishateror.office.GameState;
import com.mishateror.office.SaveManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The type Battle screen.
 */
public class BattleScreen implements Screen {
    private final MainGame game;
    private BattleManager battleManager;
    private int nextFloor;
    private Player knight;

    private BattleRenderer renderer;
    private BattleUI ui;
    private Random random;

    /**
     * Instantiates a new Battle screen.
     *
     * @param game the game
     */
    public BattleScreen(final MainGame game) {
        this.game = game;
        this.nextFloor = 1;

        Player knight = new Player("Knight", 20, 6, 20);
        knight.addAbility(new AttackAbility("Basic Strike", 2, 0, 5));
        knight.addAbility(RewardManager.getInstance().getRandomAbility());
        knight.addAbility(RewardManager.getInstance().getRandomAbility());

        this.knight = knight;
        startBattleForFloor();
    }

    /**
     * Instantiates a new Battle screen.
     *
     * @param game      the game
     * @param player    the player
     * @param nextFloor the next floor
     */
    public BattleScreen(final MainGame game, Player player, int nextFloor) {
        this.game = game;
        this.knight = player;
        this.nextFloor = nextFloor;
        startBattleForFloor();
    }

    private void startBattleForFloor() {
        Preferences prefs = Gdx.app.getPreferences("OfficeGamePrefs");
        if (nextFloor > prefs.getInteger("Highscore", 1)) {
            prefs.putInteger("Highscore", nextFloor);
            prefs.flush();
        }

        StageFactory factory;
        if (nextFloor % 5 == 0) {
            factory = new BossStageFactory();
        } else {
            factory = new MonsterStageFactory();
        }

        Enemy enemy = factory.createEnemy(nextFloor);

        List<Enemy> enemies = new ArrayList<>();
        enemies.add(enemy);

        battleManager = new BattleManager(knight, enemies);
        battleManager.startBattle();

        renderer = new BattleRenderer(game, battleManager);
        ui = new BattleUI(game, battleManager, this);
    }

    @Override
    public void render(float delta) {
        int screenW = Gdx.graphics.getWidth();
        int screenH = Gdx.graphics.getHeight();

        float mouseX = Gdx.input.getX();
        float mouseY = screenH - Gdx.input.getY();

        ui.handleInput(mouseX, mouseY, renderer);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render(delta, screenW, screenH);
        ui.render(delta, renderer);
    }

    /**
     * Trigger attack animation.
     */
    public void triggerAttackAnimation() {
        renderer.triggerAttack();
    }

    /**
     * On enemy defeated.
     */
    public void onEnemyDefeated() {
        if (battleManager.isGameOver() && !knight.isDead()) {
            SaveManager.saveGame(new GameState(knight, nextFloor + 1));

            game.setScreen(new LevelClearedScreen(game, knight, nextFloor));
        }
        dispose();
    }

    /**
     * Exit to menu.
     */
    public void exitToMenu() {
        game.setScreen(new MainMenuScreen(game));
        dispose();
    }

    @Override public void dispose() { renderer.dispose(); }
    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
