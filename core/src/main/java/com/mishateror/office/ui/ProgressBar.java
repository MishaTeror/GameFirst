package com.mishateror.office.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;

/**
 * The type Progress bar.
 */
public class ProgressBar {
    private float width, height;
    private Color color;

    /**
     * Instantiates a new Progress bar.
     *
     * @param width  the width
     * @param height the height
     * @param color  the color
     */
    public ProgressBar(float width, float height, Color color) {
        this.width = width;
        this.height = height;
        this.color = color;
    }

    /**
     * Draw shape.
     *
     * @param sr      the sr
     * @param x       the x
     * @param y       the y
     * @param current the current
     * @param max     the max
     */
    public void drawShape(ShapeRenderer sr, float x, float y, int current, int max) {
        sr.setColor(Color.BLACK);
        sr.rect(x, y, width, height);

        if (current > 0) {
            float fillPercentage = Math.min((float) current / max, 1f);
            sr.setColor(color);
            sr.rect(x + 1, y + 1, (width - 2) * fillPercentage, height - 2);
        }
    }

    /**
     * Draw text.
     *
     * @param batch the batch
     * @param font  the font
     * @param x     the x
     * @param y     the y
     * @param text  the text
     */
    public void drawText(SpriteBatch batch, BitmapFont font, float x, float y, String text) {
        font.draw(batch, text, x, y + height / 2 + 5, width, Align.center, false);
    }

}
