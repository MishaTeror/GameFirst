package com.mishateror.office.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.mishateror.office.MainGame;
import com.mishateror.office.characters.Player;

/**
 * The type Level cleared screen.
 */
public class LevelClearedScreen implements Screen {
    private MainGame game;
    private Player player;
    private int clearedFloor;

    /**
     * Instantiates a new Level cleared screen.
     *
     * @param game         the game
     * @param player       the player
     * @param clearedFloor the cleared floor
     */
    public LevelClearedScreen(MainGame game, Player player, int clearedFloor) {
        this.game = game;
        this.player = player;
        this.clearedFloor = clearedFloor;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.justTouched()) {
            game.setScreen(new BonusScreen(game, player, clearedFloor + 1));
            dispose();
        }

        game.batch.begin();
        game.font.setColor(Color.YELLOW);
        game.font.draw(game.batch, "STAGE " + clearedFloor + " CLEARED!", Gdx.graphics.getWidth() / 2f - 60, Gdx.graphics.getHeight() / 2f + 20);

        game.font.setColor(Color.WHITE);
        game.font.draw(game.batch, "Tap to continue", Gdx.graphics.getWidth() / 2f - 50, Gdx.graphics.getHeight() / 2f - 20);
        game.batch.end();
    }

    @Override public void show() {}
    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
}
