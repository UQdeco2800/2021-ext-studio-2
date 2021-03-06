package com.deco2800.game.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.components.Component;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.rendering.ParticleRenderComponent;
import com.deco2800.game.rendering.TextureRenderComponent;
import com.deco2800.game.services.GameTime;
import com.deco2800.game.services.ServiceLocator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(GameExtension.class)
class EntityTest {
    @Test
    void shouldSetAndGetPosition() {
        Entity entity = new Entity();
        Vector2 pos = new Vector2(5f, -5f);
        entity.setPosition(pos);
        assertEquals(pos, entity.getPosition());

        entity.setPosition(0f, 0f);
        assertEquals(Vector2.Zero, entity.getPosition());
    }

    @Test
    void shouldSetAndGetScale() {
        Entity entity = new Entity();
        Vector2 scale = new Vector2(2f, 3f);
        entity.setScale(scale);
        assertEquals(scale, entity.getScale());

        entity.setScale(0.1f, 0.2f);
        assertEquals(new Vector2(0.1f, 0.2f), entity.getScale());
    }

    @Test
    void shouldHaveCorrectCenter() {
        Entity entity = new Entity();
        entity.setPosition(0f, 0f);
        entity.setScale(2f, 2f);
        assertEquals(new Vector2(1f, 1f), entity.getCenterPosition());

        entity.setPosition(-5f, -10f);
        assertEquals(new Vector2(-4f, -9f), entity.getCenterPosition());
    }

    @Test
    void shouldUpdateComponents() {
        Entity entity = new Entity();
        Component component1 = spy(TestComponent1.class);
        Component component2 = spy(TestComponent2.class);
        entity.addComponent(component1);
        entity.addComponent(component2);
        entity.create();
        entity.update();

        verify(component1).update();
        verify(component2).update();

        entity.earlyUpdate();
        verify(component1).earlyUpdate();
        verify(component2).earlyUpdate();
    }

    @Test
    void shouldCreateComponents() {
        Entity entity = new Entity();
        Component component1 = spy(TestComponent1.class);
        Component component2 = spy(TestComponent2.class);
        entity.addComponent(component1);
        entity.addComponent(component2);
        entity.create();

        verify(component1).create();
        verify(component2).create();
        assertEquals(entity, component1.getEntity());
        assertEquals(entity, component2.getEntity());
    }

    @Test
    void shouldRejectSameClassComponents() {
        Entity entity = new Entity();
        Component component1 = new TestComponent1();
        Component component2 = new TestComponent1();
        entity.addComponent(component1);
        entity.addComponent(component2);
        assertEquals(component1, entity.getComponent(TestComponent1.class));
    }

    @Test
    void shouldRejectAfterCreate() {
        Entity entity = new Entity();
        entity.create();
        entity.addComponent(new TestComponent1());
        assertNull(entity.getComponent(TestComponent1.class));
    }

    @Test
    void shouldGetComponent() {
        Entity entity = new Entity();
        Component component1 = new TestComponent1();
        Component component2 = new TestComponent2();
        entity.addComponent(component1);
        entity.addComponent(component2);

        TestComponent1 gotComponent1 = entity.getComponent(TestComponent1.class);
        TestComponent2 gotComponent2 = entity.getComponent(TestComponent2.class);

        assertEquals(component1, gotComponent1);
        assertEquals(component2, gotComponent2);
    }

    @Test
    void shouldNotGetMissingComponent() {
        Entity entity = new Entity();
        TestComponent1 component = entity.getComponent(TestComponent1.class);
        assertNull(component);
    }

    @Test
    void shouldDisposeComponents() {
        Entity entity = new Entity();
        TestComponent1 component = spy(TestComponent1.class);
        entity.addComponent(component);
        entity.create();

        EntityService entityService = mock(EntityService.class);
        ServiceLocator.registerEntityService(entityService);

        entity.dispose();
        verify(component).dispose();
        verify(entityService).unregister(entity);
    }

    @Test
    void shouldHaveUniqueId() {
        Entity entity1 = new Entity();
        Entity entity2 = new Entity();

        assertNotEquals(entity1.getId(), entity2.getId());
    }

    @Test
    void shouldEqualWithId() {
        Entity entity1 = new Entity();
        Entity entity2 = mock(Entity.class);
        int id = entity1.getId();
        when(entity2.getId()).thenReturn(id);

        assertEquals(entity1, entity2);
    }

    @Test
    void shouldNotUpdateIfDisabled() {
        Entity entity = new Entity();
        TestComponent1 component = spy(TestComponent1.class);
        entity.addComponent(component);
        entity.create();

        entity.setEnabled(false);
        entity.earlyUpdate();
        entity.update();

        verify(component, times(0)).earlyUpdate();
        verify(component, times(0)).update();
    }

    @Test
    void shouldStopOrDisposeAfterAnimation() {
        Entity entity = new Entity();

        AnimationRenderComponent animator = mock(AnimationRenderComponent.class);
        animator.addAnimation("try", 0.2f, Animation.PlayMode.LOOP);
        entity.addComponent(animator);

        TestComponent1 component = spy(TestComponent1.class);
        entity.addComponent(component);

        entity.create();
        EntityService entityService = mock(EntityService.class);
        ServiceLocator.registerEntityService(entityService);

        entity.setDisappearAfterAnimation(1f, Entity.DisappearType.ANIMATION);
        when(animator.getAnimationPlayTime()).thenReturn(1.1f);

        entity.update();

        verify(animator).stopAnimation();
        verify(component).dispose();
    }

    @Test
    void shouldNotStopOrDisposeAfterAnimation() {
        Entity entity = new Entity();

        AnimationRenderComponent animator = mock(AnimationRenderComponent.class);
        animator.addAnimation("try", 0.2f, Animation.PlayMode.LOOP);
        entity.addComponent(animator);

        TestComponent1 component = spy(TestComponent1.class);
        entity.addComponent(component);

        entity.create();
        EntityService entityService = mock(EntityService.class);
        ServiceLocator.registerEntityService(entityService);

        entity.setDisappearAfterAnimation(1f, Entity.DisappearType.ANIMATION);
        when(animator.getAnimationPlayTime()).thenReturn(1f);

        entity.update();
        verify(animator, times(0)).stopAnimation();
        verify(component, times(0)).dispose();
    }


    @Test
    void shouldRemoveTexture() {
        Entity entity = new Entity();

        TextureRenderComponent textureRenderComponent = mock(TextureRenderComponent.class);
        entity.addComponent(textureRenderComponent);

        entity.create();

        entity.setRemoveTexture();

        entity.update();
        verify(textureRenderComponent).dispose();
    }

    @Test
    void shouldDisposeComponentAfterSetDispose() {
        Entity entity = new Entity();
        TestComponent1 component = spy(TestComponent1.class);
        entity.addComponent(component);

        entity.create();
        EntityService entityService = mock(EntityService.class);
        ServiceLocator.registerEntityService(entityService);

        entity.setDispose();

        entity.update();
        verify(component).dispose();
    }

    @Test
    void shouldStopOrDisposeAfterParticleEffect() {
        Entity entity = new Entity();

        AnimationRenderComponent animator = mock(AnimationRenderComponent.class);
        animator.addAnimation("try", 0.2f, Animation.PlayMode.LOOP);
        entity.addComponent(animator);

        ParticleRenderComponent particleRenderComponent = mock(ParticleRenderComponent.class);
        entity.addComponent(particleRenderComponent);

        TestComponent1 component = spy(TestComponent1.class);
        entity.addComponent(component);

        entity.create();
        when(animator.getCurrentAnimation()).thenReturn("try");
        EntityService entityService = mock(EntityService.class);
        ServiceLocator.registerEntityService(entityService);

        entity.setDisappearAfterParticle(3f, Entity.DisappearType.PARTICLE);
        when(particleRenderComponent.getParticlePlayTime()).thenReturn(3.1f);

        entity.update();

        verify(animator).stopAnimation();
        verify(component).dispose();
        verify(particleRenderComponent).dispose();
    }

    @Test
    void shouldNotStopOrDisposeAfterParticleEffect() {
        Entity entity = new Entity();

        AnimationRenderComponent animator = mock(AnimationRenderComponent.class);
        animator.addAnimation("try", 0.2f, Animation.PlayMode.LOOP);
        entity.addComponent(animator);
        animator.startAnimation("try");

        ParticleRenderComponent particleRenderComponent = mock(ParticleRenderComponent.class);
        entity.addComponent(particleRenderComponent);

        TestComponent1 component = spy(TestComponent1.class);
        entity.addComponent(component);

        entity.create();
        EntityService entityService = mock(EntityService.class);
        ServiceLocator.registerEntityService(entityService);

        entity.setDisappearAfterParticle(3f, Entity.DisappearType.PARTICLE);
        when(particleRenderComponent.getParticlePlayTime()).thenReturn(3f);

        entity.update();

        verify(animator, times(0)).stopAnimation();
        verify(component, times(0)).dispose();
        verify(particleRenderComponent, times(0)).dispose();
    }

    @Test
    void shouldKeepParticleEffectAfterParticleEffect() {
        Entity entity = new Entity();

        AnimationRenderComponent animator = mock(AnimationRenderComponent.class);
        animator.addAnimation("try", 0.2f, Animation.PlayMode.LOOP);
        entity.addComponent(animator);
        animator.startAnimation("try");

        ParticleRenderComponent particleRenderComponent = mock(ParticleRenderComponent.class);
        entity.addComponent(particleRenderComponent);

        TestComponent1 component = spy(TestComponent1.class);
        entity.addComponent(component);

        entity.create();
        EntityService entityService = mock(EntityService.class);
        ServiceLocator.registerEntityService(entityService);

        entity.setParticleTime(3f);
        entity.setDisappearAfterAnimation(2f, Entity.DisappearType.ANIMATION);
        when(particleRenderComponent.getParticlePlayTime()).thenReturn(2f);
        when(animator.getAnimationPlayTime()).thenReturn(3.1f);

        entity.update();

        verify(animator).stopAnimation();
        verify(component).dispose();
        verify(particleRenderComponent, times(0)).dispose();
    }


    static class TestComponent1 extends Component {
    }

    static class TestComponent2 extends Component {
    }
}
