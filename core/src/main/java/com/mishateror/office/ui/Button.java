package com.mishateror.office.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;

/**
 * The type Button.
 */
public class Button  {
    /**
     * The X.
     */
    protected float x, /**
     * The Y.
     */
    y , /**
     * The Width.
     */
    width, /**
     * The Height.
     */
    height;
    private String text;
    private Color color;

    /**
     * Instantiates a new Button.
     *
     * @param x      the x
     * @param y      the y
     * @param width  the width
     * @param height the height
     * @param text   the text
     */
    public Button(float x, float y, float width, float height, String text) {
        this(x , y, width, height, text, new Color(0.3f, 0.3f, 0.3f, 0.8f));
    }

    /**
     * Instantiates a new Button.
     *
     * @param x      the x
     * @param y      the y
     * @param width  the width
     * @param height the height
     * @param text   the text
     * @param color  the color
     */
    public Button(float x, float y, float width, float height, String text, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        this.color = color;
    }

    /**
     * Draw shape.
     *
     * @param sr the sr
     */
    public void drawShape(ShapeRenderer sr) {
        sr.setColor(color);
        sr.rect(x, y, width, height);
    }

    /**
     * Draw text.
     *
     * @param batch the batch
     * @param font  the font
     */
    public void drawText(SpriteBatch batch,  BitmapFont font) {
        font.draw(batch, text, x, y + height / 2 + 6, width, Align.center, false);
    }

    /**
     * Is clicked boolean.
     *
     * @param mouseX the mouse x
     * @param mouseY the mouse y
     * @return the boolean
     */
    public boolean isClicked(float mouseX, float mouseY) {
        return mouseX >= x &&  mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
}
