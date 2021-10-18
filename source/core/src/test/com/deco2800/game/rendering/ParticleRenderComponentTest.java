package com.deco2800.game.rendering;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.services.GameTime;
import com.deco2800.game.services.ServiceLocator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(GameExtension.class)
class ParticleRenderComponentTest {

    @BeforeEach
    void beforeEach() {
        ServiceLocator.registerRenderService(new RenderService());
        ServiceLocator.registerTimeSource(new GameTime());
    }

    @Test
    void shouldStartParticleEffect() {
        ParticleRenderComponent particleRenderComponent = new ParticleRenderComponent("test/files/test.party");
        particleRenderComponent.create();
        particleRenderComponent.startEffect();
        assertTrue(particleRenderComponent.isEffectStart());
    }

    @Test
    void shouldGetParticlePlayTime() {
        ParticleRenderComponent particleRenderComponent = new ParticleRenderComponent("test/files/test.party");
        particleRenderComponent.create();
        particleRenderComponent.startEffect();

        Entity entity = new Entity();
        particleRenderComponent.setEntity(entity);

        SpriteBatch batch = mock(SpriteBatch.class);
        particleRenderComponent.draw(batch);
        assertTrue(particleRenderComponent.getParticlePlayTime() > 0);
    }
}