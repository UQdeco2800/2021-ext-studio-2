package com.deco2800.game.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.services.GameTime;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Render particle effect for entities.
 * <p>
 * Usages:  ParticleRenderComponent particle =
 * new ParticleRenderComponent("path.party");
 * entity.addComponent(particle);
 * <p>
 * Start particle effect: particle.startEffect();
 */
public class ParticleRenderComponent extends RenderComponent {
    private String texturePath;
    private ParticleEffect pe;
    private boolean EffectStart = false;
    private float particlePlayTime;
    private final GameTime timeSource;
    private static final Logger logger = LoggerFactory.getLogger(ParticleRenderComponent.class);

    /**
     * Constructor of particle effect
     *
     * @param texturePath the path of .party file
     */
    public ParticleRenderComponent(String texturePath) {
        this.texturePath = texturePath;
        this.timeSource = ServiceLocator.getTimeSource();
    }

    @Override
    public void create() {
        super.create();
        pe = new ParticleEffect();
        pe.load(Gdx.files.internal(texturePath), Gdx.files.internal("images/particle"));
    }

    /**
     * Set the zIndex of the entity according to the y coordinate of the entity and the original zIndex.
     * <p>
     * If zIndex is not set originally, then The smaller the Y value, the higher the Z index, so that closer entities
     * are drawn in front.
     * <p>
     * If zIndex has been set, zIndex will not change.
     *
     * @return The drawing priority of the current entity
     */
    @Override
    public float getZIndex() {
        return 1;
    }

    /**
     * get if the particle effect is start, for test.
     * @return
     */
    public boolean isEffectStart() {
        return EffectStart;
    }

    /**
     * Start the particle effect on this entity
     */
    public void startEffect() {
        EffectStart = true;
    }

    @Override
    protected void draw(SpriteBatch batch) {
        if (EffectStart) {
            Vector2 entityPosition = entity.getPosition();
            pe.update(Gdx.graphics.getDeltaTime());
            pe.setPosition(entityPosition.x, entityPosition.y);
            pe.draw(batch);
            if (pe.isComplete()) {
                pe.reset();
            }
            particlePlayTime += timeSource.getDeltaTime();
        }
    }

    /**
     * Get the playing time of the animation
     *
     * @return float The time the animation has been played (seconds).
     */
    public float getParticlePlayTime() {
        return this.particlePlayTime;
    }

    @Override
    public void dispose() {
        pe.dispose();
        super.dispose();
    }

}
