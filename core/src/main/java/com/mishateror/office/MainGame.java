package com.mishateror.office;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mishateror.office.screens.BattleScreen;
import com.mishateror.office.screens.BonusScreen;
import com.mishateror.office.screens.MainMenuScreen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * The type Main game.
 */
public class MainGame extends Game  {
    /**
     * The Batch.
     */
    public SpriteBatch batch;
    /**
     * The Font.
     */
    public BitmapFont font;
    /**
     * The Shape renderer.
     */
    public ShapeRenderer shapeRenderer;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        shapeRenderer = new ShapeRenderer();

        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
