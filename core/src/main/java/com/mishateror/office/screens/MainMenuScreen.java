package com.mishateror.office.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mishateror.office.MainGame;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mishateror.office.ui.Button;

/**
 * The type Main menu screen.
 */
public class MainMenuScreen implements Screen {
    /**
     * The Game.
     */
    final MainGame game;

    /**
     * The Sprite sheet.
     */
    Texture spriteSheet;
    /**
     * The Animation.
     */
    Animation<TextureRegion> animation;
    /**
     * The State time.
     */
    float stateTime = 0f;

    /**
     * The Play btn.
     */
    Button playBtn;
    private Button btnExit;
    private int highscore;

    /**
     * Instantiates a new Main menu screen.
     *
     * @param game the game
     */
    public MainMenuScreen(final MainGame game) {
        this.game = game;

        spriteSheet = new Texture("menu.png");

        int Frame_Cols = 5;
        int Frame_Rows = 15;

        int frameWidth = spriteSheet.getWidth() / Frame_Cols;
        int frameHeight = spriteSheet.getHeight() / Frame_Rows;

        TextureRegion[][] tmp = TextureRegion.split(spriteSheet, frameWidth, frameHeight);

        TextureRegion[] frames = new TextureRegion[Frame_Rows * Frame_Cols];

        int index = 0;
        for (int i = 0; i < Frame_Rows; i++) {
            for (int j = 0; j < Frame_Cols; j++) {
                frames[index++] = tmp[i][j];
            }
        }

        animation = new Animation<TextureRegion>(0.1f, frames);

        int screenW = Gdx.graphics.getWidth();
        int screenH = Gdx.graphics.getHeight();
        int btnW = 200, btnH = 60;
        int btnX = (screenW - btnW) / 2;
        int btnY = screenH / 2 - 50;

        playBtn = new Button(btnX, btnY, btnW, btnH, "Start");
        btnExit = new Button(btnX, btnY - 80, btnW, btnH, "EXIT");

        Preferences prefs = Gdx.app.getPreferences("OfficeGamePrefs");
        highscore = prefs.getInteger("Highscore", 1);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stateTime += delta;

        int screenW = Gdx.graphics.getWidth();
        int screenH = Gdx.graphics.getHeight();

        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);

        game.batch.begin();
        game.batch.draw(currentFrame, 0, 0, screenW, screenH);
        game.batch.end();

        if (Gdx.input.justTouched()) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (playBtn.isClicked(mouseX, mouseY)) {
                game.setScreen(new BattleScreen(game));
                dispose();
                return;
            } else if (btnExit.isClicked(mouseX, mouseY)) {
                Gdx.app.exit();
            }
        }

        Gdx.gl.glEnable(GL20.GL_BLEND);
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        playBtn.drawShape(game.shapeRenderer);
        btnExit.drawShape(game.shapeRenderer);

        game.shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        game.batch.begin();
        game.font.setColor(Color.WHITE);

        game.font.draw(game.batch, "Slay the Kisliy", screenW / 2f - 60, screenH - 100);

        game.font.setColor(Color.YELLOW);
        game.font.draw(game.batch, "Highest Floor Reached: " + highscore, screenW / 2f - 90, screenH - 150);

        playBtn.drawText(game.batch, game.font);
        btnExit.drawText(game.batch, game.font);

        game.batch.end();
    }

    @Override
    public void dispose() {
        spriteSheet.dispose();
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
