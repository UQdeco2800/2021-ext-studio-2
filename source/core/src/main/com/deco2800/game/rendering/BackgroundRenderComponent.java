package com.deco2800.game.rendering;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.deco2800.game.services.ServiceLocator;

/**
 * Render the background texture.
 */
public class BackgroundRenderComponent extends RenderComponent {
    private Texture texture;
    private String texturePath;
    private float horizontal = 0;
    private float vertical = 0;

    public BackgroundRenderComponent(String texturePath) {
        this.texturePath = texturePath;
    }

    @Override
    public void create() {
        super.create();
        texture = ServiceLocator.getResourceService().getAsset(texturePath, Texture.class);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, -30, vertical, 30, 15);
        batch.draw(texture, horizontal, vertical, 30, 15);
    }

    @Override
    public int getLayer() {
        return 0;
    }

    @Override
    public float getZIndex() {
        return 0;
    }

    /**
     * Set the horizontal start point for the background texture
     * @param x horizontal value
     */
    public void setHorizontal(float x) {
        horizontal = x;
    }

    /**
     * Set the vertical start point for the background texture
     * @param y vertical value
     */
    public void setVertical(float y) {
        vertical = y;
    }
}
