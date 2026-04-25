package com.mishateror.office.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mishateror.office.MainGame;
import com.mishateror.office.RewardManager;
import com.mishateror.office.ability.Ability;
import com.mishateror.office.characters.Player;

/**
 * The type Bonus screen.
 */
public class BonusScreen implements Screen {
    private MainGame game;
    private Player player;
    private int nextFloor;

    private Texture healTex;
    private Texture waterTex;
    private Texture bookTex;

    private Ability randomAbility;
    private RewardManager.BuffType randomBuff;

    /**
     * The Item size.
     */
    float itemSize = 150f;
    /**
     * The Gap.
     */
    float gap = 50f;
    /**
     * The Start x.
     */
    float startX;
    /**
     * The Start y.
     */
    float startY;

    /**
     * Instantiates a new Bonus screen.
     *
     * @param game      the game
     * @param player    the player
     * @param nextFloor the next floor
     */
    public BonusScreen(MainGame game, Player player, int nextFloor) {
        this.game = game;
        this.player = player;
        this.nextFloor = nextFloor;

        healTex = new Texture("heal.png");
        waterTex = new Texture("water.png");
        bookTex = new Texture("book.png");

        randomAbility = RewardManager.getRandomAbility();
        randomBuff = RewardManager.getRandomBuff();

        startX = (Gdx.graphics.getWidth() - (3 * itemSize + 2 * gap)) / 2f;
        startY = Gdx.graphics.getHeight() / 2f - itemSize / 2f;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.justTouched()) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (mouseX >= startX && mouseX <= startX + itemSize && mouseY >= startY && mouseY <= startY + itemSize) {
                player.heal(15);
                goToNextFloor();
            }

            float waterX = startX + itemSize + gap;
            if (mouseX >= waterX && mouseX <= waterX + itemSize && mouseY >= startY && mouseY <= startY + itemSize) {
                RewardManager.applyBuffToPlayer(player, randomBuff);
                goToNextFloor();
            }

            float bookX = startX + 2 * (itemSize + gap);
            if (mouseX >= bookX && mouseX <= bookX + itemSize && mouseY >= startY && mouseY <= startY + itemSize) {
                player.addAbility(randomAbility);
                goToNextFloor();
            }
        }

        game.batch.begin();
        game.font.setColor(Color.WHITE);
        game.font.draw(game.batch, "CHOOSE YOUR REWARD", Gdx.graphics.getWidth() / 2f - 90, Gdx.graphics.getHeight() - 100);

        game.batch.draw(healTex, startX, startY, itemSize, itemSize);
        game.font.draw(game.batch, "Drink Tango", startX + 20, startY - 20);
        game.font.draw(game.batch, "(Heal 15 HP)", startX + 20, startY - 40);

        float waterX = startX + itemSize + gap;
        game.batch.draw(waterTex, waterX, startY, itemSize, itemSize);
        game.font.draw(game.batch, "Drink Zhyvchyk", waterX + 10, startY - 20);
        game.font.draw(game.batch, "(" + RewardManager.getBuffDescription(randomBuff) + ")", waterX + 20, startY - 40);

        float bookX = startX + 2 * (itemSize + gap);
        game.batch.draw(bookTex, bookX, startY, itemSize, itemSize);
        game.font.draw(game.batch, "Read Book", bookX + 30, startY - 20);
        game.font.draw(game.batch, randomAbility.getName(), bookX + 10, startY - 40);

        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
        if (mouseX >= bookX && mouseX <= bookX + itemSize && mouseY >= startY && mouseY <= startY + itemSize) {
            game.font.setColor(Color.YELLOW);
            game.font.draw(game.batch, randomAbility.getDescription(), bookX, startY - 70);
        }

        game.batch.end();
    }

    private void goToNextFloor() {
        game.setScreen(new BattleScreen(game, player, nextFloor));
        dispose();
    }

    @Override public void dispose() {
        healTex.dispose();
        waterTex.dispose();
        bookTex.dispose();
    }
    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
