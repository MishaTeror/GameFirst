package com.mishateror.office.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.mishateror.office.MainGame;
import com.mishateror.office.ui.Button;
import com.mishateror.office.GameState;
import com.mishateror.office.SaveManager;

/**
 * The type Main menu screen.
 */
public class MainMenuScreen implements Screen {

    private MainGame game;
    private OrthographicCamera camera;

    private Button playButton;
    private Button loadButton;
    private Button exitButton;

    private Vector3 touchPoint;
    private String feedbackMessage = "";

    /**
     * Instantiates a new Main menu screen.
     *
     * @param game the game
     */
    public MainMenuScreen(MainGame game) {
        this.game = game;
        this.touchPoint = new Vector3();

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        int buttonWidth = 200;
        int buttonHeight = 50;
        int startX = (screenWidth - buttonWidth) / 2;

        playButton = new Button(startX, screenHeight / 2 + 30, buttonWidth, buttonHeight, "NEW GAME");
        loadButton = new Button(startX, screenHeight / 2 - 40, buttonWidth, buttonHeight, "LOAD GAME");
        exitButton = new Button(startX, screenHeight / 2 - 110, buttonWidth, buttonHeight, "EXIT", new Color(0.5f, 0.2f, 0.2f, 0.8f));
    }

    @Override
    public void show() { }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        if (Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (playButton.isClicked(touchPoint.x, touchPoint.y)) {
                game.setScreen(new BattleScreen(game));
                dispose();
            }

            if (loadButton.isClicked(touchPoint.x, touchPoint.y)) {
                GameState savedState = SaveManager.loadGame();
                if (savedState != null && savedState.getPlayer() != null) {
                    game.setScreen(new BattleScreen(game, savedState.getPlayer(), savedState.getCurrentFloor()));
                    dispose();
                } else {
                    feedbackMessage = "No save file found!";
                }
            }

            if (exitButton.isClicked(touchPoint.x, touchPoint.y)) {
                Gdx.app.exit();
            }
        }

        game.shapeRenderer.setProjectionMatrix(camera.combined);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        game.shapeRenderer.begin(com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Filled);

        playButton.drawShape(game.shapeRenderer);
        loadButton.drawShape(game.shapeRenderer);
        exitButton.drawShape(game.shapeRenderer);

        game.shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        game.font.setColor(Color.WHITE);
        game.font.draw(game.batch, "THE OFFICE DUNGEON", Gdx.graphics.getWidth() / 2f - 80, Gdx.graphics.getHeight() / 2f + 120);

        playButton.drawText(game.batch, game.font);
        loadButton.drawText(game.batch, game.font);
        exitButton.drawText(game.batch, game.font);

        if (!feedbackMessage.isEmpty()) {
            game.font.setColor(Color.RED);
            game.font.draw(game.batch, feedbackMessage, (Gdx.graphics.getWidth() / 2f) - 60, (Gdx.graphics.getHeight() / 2f) - 150);
            game.font.setColor(Color.WHITE);
        }

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() { }
}
