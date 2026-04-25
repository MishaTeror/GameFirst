package com.mishateror.office.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mishateror.office.MainGame;
import com.mishateror.office.BattleManager;
import com.mishateror.office.characters.Enemy;
import com.mishateror.office.characters.Player;
import com.mishateror.office.characters.AnimationManager;

/**
 * The type Battle renderer.
 */
public class BattleRenderer {
    private MainGame game;
    private BattleManager battleManager;

    private Texture background;
    private AnimationManager heroAnimator;
    private AnimationManager enemyAnimator;

    private float stateTime = 0f;
    /**
     * The Enemy w.
     */
    public float enemyW, /**
     * The Target height.
     */
    targetHeight;

    /**
     * Instantiates a new Battle renderer.
     *
     * @param game          the game
     * @param battleManager the battle manager
     */
    public BattleRenderer(MainGame game, BattleManager battleManager) {
        this.game = game;
        this.battleManager = battleManager;
        background = new Texture("arena.png");

        heroAnimator = new AnimationManager(
            "heroIdle.png", 4,
            "heroDead.png", 6,
            "heroAttack.png", 5,
            "heroWalk.png", 7
        );

        enemyAnimator = new AnimationManager(
            "enemyIdle.png", 8,
            "enemyDead.png", 2,
            "enemyAttack.png", 6,
            "enemyWalk.png", 9
        );
    }

    /**
     * Trigger attack.
     */
    public void triggerAttack() { heroAnimator.startWalkingToTarget(); }

    /**
     * Trigger enemy attack.
     */
    public void triggerEnemyAttack() { enemyAnimator.startWalkingToTarget(); }

    /**
     * Render.
     *
     * @param delta   the delta
     * @param screenW the screen w
     * @param screenH the screen h
     */
    public void render(float delta, int screenW, int screenH) {
        stateTime += delta;

        heroAnimator.update(delta);
        enemyAnimator.update(delta);

        Player p = battleManager.getPlayer();
        Enemy e = battleManager.getEnemies().get(0);

        if (p.getHealth() <= 0) heroAnimator.playDead();
        if (e.getHealth() <= 0) enemyAnimator.playDead();

        TextureRegion currentHeroFrame = heroAnimator.getCurrentFrame();
        TextureRegion currentEnemyFrame = enemyAnimator.getCurrentFrame();

        targetHeight = 280f;

        float heroRatio = (float) currentHeroFrame.getRegionWidth() / currentHeroFrame.getRegionHeight();
        float heroW = targetHeight * heroRatio;

        float enemyRatio = (float) currentEnemyFrame.getRegionWidth() / currentEnemyFrame.getRegionHeight();
        enemyW = targetHeight * enemyRatio;

        float baseHeroX = 150f;
        float baseEnemyX = screenW - 200f - enemyW;
        float posY = 150f;

        float dashDistance = baseEnemyX - baseHeroX - heroW + 60f;

        float heroDashOffset = heroAnimator.getDashProgress() * dashDistance;
        float enemyDashOffset = -(enemyAnimator.getDashProgress() * dashDistance);

        float currentHeroX = baseHeroX + heroDashOffset;
        float currentEnemyX = baseEnemyX + enemyDashOffset;

        game.batch.begin();
        game.batch.draw(background, 0, 0, screenW, screenH);

        if (p.isPoisoned()) {
            game.batch.setColor(0.3f, 1f, 0.3f, 1f);
        }
        game.batch.draw(currentHeroFrame, currentHeroX, posY, heroW, targetHeight);
        game.batch.setColor(Color.WHITE);

        if (e.isPoisoned()) {
            game.batch.setColor(0.3f, 1f, 0.3f, 1f);
        }
        game.batch.draw(currentEnemyFrame, currentEnemyX + enemyW, posY, -enemyW, targetHeight);
        game.batch.setColor(Color.WHITE);

        game.batch.end();
    }

    /**
     * Dispose.
     */
    public void dispose() {
        background.dispose(); heroAnimator.dispose(); enemyAnimator.dispose();
    }
}
