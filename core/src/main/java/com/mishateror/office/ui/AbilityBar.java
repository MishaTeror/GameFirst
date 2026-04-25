package com.mishateror.office.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mishateror.office.ability.Ability;
import com.mishateror.office.characters.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Ability bar.
 */
public class AbilityBar {
    private float finalX, finalY, width, height;
    /**
     * The Current y.
     */
    protected float currentY;
    private boolean isVisable;
    private float speed = 500f;

    private Texture bgTex;
    private List<Button> abilityButtons;
    private Button endTurn;

    private int currentPage = 0;
    private int maxPages = 0;
    private static final int SLOTS_PER_PAGE = 3;

    /**
     * Instantiates a new Ability bar.
     *
     * @param x      the x
     * @param y      the y
     * @param width  the width
     * @param height the height
     */
    public AbilityBar(float x, float y, float width, float height) {
        this.finalX = x;
        this.finalY = y;
        this.width = width;
        this.height = height;
        this.currentY = -height - 20;

        bgTex = new Texture("AbilityBar.png");
        this.abilityButtons = new ArrayList<>();
    }

    /**
     * Refresh ability bar.
     *
     * @param player the player
     */
    public void refreshAbilityBar(Player player) {
        abilityButtons.clear();
        List<Ability> abilities = player.getAbilities();

        maxPages = (int) Math.ceil((double) abilities.size() / SLOTS_PER_PAGE);
        currentPage = 0;

        float btnWidth = 140;
        float btnHeight = 60;
        float gapX = 35;
        float startX = 65;
        float btnYOffset = 30;

        for (int i = 0; i < abilities.size(); i++) {
            Ability a = abilities.get(i);
            String abilityName = a.getName() + " (" + a.getApCost() + "AP)";

            int slotIndex = i % SLOTS_PER_PAGE;
            float btnX = finalX + startX + (btnWidth + gapX) * slotIndex;

            Button abilityButton = new Button(btnX, finalY + btnYOffset, btnWidth, btnHeight, abilityName);
            abilityButtons.add(abilityButton);
        }

        endTurn = new Button(finalX + width - 120, finalY + height + 10, 120, 40, "END TURN", new Color(0.6f, 0.1f, 0.1f, 0.8f));
    }

    /**
     * Update.
     *
     * @param delta the delta
     */
    public void update(float delta) {
        if (isVisable) {
            if (currentY < finalY) {
                currentY += speed * delta;
                if (currentY > finalY) currentY = finalY;
            }
        } else {
            if (currentY > -height - 20) {
                currentY -= speed * delta;
                if (currentY < -height - 20) currentY = -height - 20;
            }
        }

        float btnYOffset = 30;
        for (Button b : abilityButtons) {
            b.y = currentY + btnYOffset;
        }
        endTurn.y = currentY + height + 10;
    }

    /**
     * Show.
     */
    public void show() { isVisable = true; }

    /**
     * Hide.
     */
    public void hide() { isVisable = false; }

    /**
     * Is visable boolean.
     *
     * @return the boolean
     */
    public boolean isVisable() { return isVisable; }

    /**
     * Gets ability index.
     *
     * @param mouseX the mouse x
     * @param mouseY the mouse y
     * @return the ability index
     */
    public int getAbilityIndex(float mouseX, float mouseY) {
        float edgeClickWidth = 50f;

        if (mouseX >= finalX && mouseX <= finalX + edgeClickWidth && mouseY >= currentY && mouseY <= currentY + height) {
            if (currentPage > 0) currentPage--;
            return -1;
        }
        if (mouseX >= finalX + width - edgeClickWidth && mouseX <= finalX + width && mouseY >= currentY && mouseY <= currentY + height) {
            if (currentPage < maxPages - 1) currentPage++;
            return -1;
        }

        if (endTurn.isClicked(mouseX, mouseY)) return -2;

        int startIndex = currentPage * SLOTS_PER_PAGE;
        int endIndex = Math.min(startIndex + SLOTS_PER_PAGE, abilityButtons.size());

        for (int i = startIndex; i < endIndex; i++) {
            if (abilityButtons.get(i).isClicked(mouseX, mouseY)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Draw shape.
     *
     * @param shape the shape
     */
    public void drawShape(ShapeRenderer shape) {
        if (currentY <= -height) return;

        int startIndex = currentPage * SLOTS_PER_PAGE;
        int endIndex = Math.min(startIndex + SLOTS_PER_PAGE, abilityButtons.size());

        for (int i = startIndex; i < endIndex; i++) {
            abilityButtons.get(i).drawShape(shape);
        }
        endTurn.drawShape(shape);
    }

    /**
     * Draw texture.
     *
     * @param batch the batch
     */
    public void drawTexture(SpriteBatch batch) {
        if (currentY <= -height) return;
        batch.draw(bgTex, finalX, currentY, width, height);
    }

    /**
     * Draw text.
     *
     * @param batch the batch
     * @param font  the font
     */
    public void drawText(SpriteBatch batch, BitmapFont font) {
        if (currentY < -height) return;

        int startIndex = currentPage * SLOTS_PER_PAGE;
        int endIndex = Math.min(startIndex + SLOTS_PER_PAGE, abilityButtons.size());

        for (int i = startIndex; i < endIndex; i++) {
            abilityButtons.get(i).drawText(batch, font);
        }

        font.setColor(Color.WHITE);
        font.draw(batch, "Page " + (currentPage + 1) + "/" + maxPages, finalX + 10, currentY + height + 25);

        endTurn.drawText(batch, font);
    }
}
