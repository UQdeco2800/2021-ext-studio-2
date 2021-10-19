package com.deco2800.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.deco2800.game.components.Component;
import com.deco2800.game.components.ComponentType;
import com.deco2800.game.components.player.PlayerActions;
import com.deco2800.game.events.EventHandler;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.rendering.ParticleRenderComponent;
import com.deco2800.game.rendering.TextureRenderComponent;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

/**
 * Core entity class. Entities exist in the game and are updated each frame. All entities have a
 * position and scale, but have no default behaviour. Components should be added to an entity to
 * give it specific behaviour. This class should not be inherited or modified directly.
 *
 * <p>Example use:
 *
 * <pre>
 * Entity player = new Entity()
 *   .addComponent(new RenderComponent())
 *   .addComponent(new PlayerControllerComponent());
 * ServiceLocator.getEntityService().register(player);
 * </pre>
 */
public class Entity {
    private static final Logger logger = LoggerFactory.getLogger(Entity.class);
    private static int nextId = 0;
    private static final String EVT_NAME_POS = "setPosition";

    private final int id;

    // Used to adjust, show which category the current entity is. For example, "SpaceShip", etc.
    private String type;

    private final IntMap<Component> components;
    private final EventHandler eventHandler;
    private boolean enabled = true;
    private boolean disappear = false;

    public enum DisappearType {
        ANIMATION, PARTICLE
    }

    private DisappearType disappearType = null;
    private boolean removeTexture = false;
    private boolean removeCollision = false;
    private boolean dispose = false;
    private float animationTime = 0;
    private float particleTime = 0;
    private boolean created = false;
    private Vector2 position = Vector2.Zero.cpy();

    private int zIndex = 0;
    private Vector2 scale = new Vector2(1, 1);
    private Array<Component> createdComponents;


    public Entity() {
        id = nextId;
        nextId++;
        type = "Undefined";
        components = new IntMap<>(4);
        eventHandler = new EventHandler();
    }

    /**
     * Create an entity with category information. This will display the category when printing
     *
     * @param type which category the current entity is. For example, "SpaceShip", etc.
     */
    public Entity(String type) {
        id = nextId;
        nextId++;
        this.type = type;
        components = new IntMap<>(4);
        eventHandler = new EventHandler();
    }

    /**
     * Enable or disable an entity. Disabled entities do not run update() or earlyUpdate() on their
     * components, but can still be disposed.
     *
     * @param enabled true for enable, false for disable.
     */
    public void setEnabled(boolean enabled) {
        logger.debug("Setting enabled={} on entity {}", enabled, this);
        this.enabled = enabled;
    }

    /**
     * Get entity type.
     * @return
     */
    public String getType() {
        return this.type;
    }

    public void updateSpeed(Vector2 speed) {
        PlayerActions component = this.getComponent(PlayerActions.class);
        component.changeCurrentSpeed(speed);
    }

    /**
     * Set disappear to true. These variables play a role in removeAfterAnimation() and update().
     *
     * @param animationTime Set how long the animation will disappear after playing
     */
    public void setDisappearAfterAnimation(float animationTime, DisappearType disappearType) {
        this.disappear = true;
        this.animationTime = animationTime;
        this.disappearType = disappearType;
        logger.debug("Setting disappear={} on entity {}", true, this);
    }

    /**
     * Set disappear to true. These variables play a role in removeAfterParticle() and update().
     *
     * @param particleTime Set how long the animation will disappear after playing
     */
    public void setDisappearAfterParticle(float particleTime, DisappearType disappearType) {
        this.disappear = true;
        this.particleTime = particleTime;
        this.disappearType = disappearType;
        logger.debug("Setting disappear={} on entity {}", true, this);
    }

    public void setParticleTime(float particleTime) {
        this.particleTime = particleTime;
    }

    /**
     * Set removeTexture to true. The code that works subsequently is in update.
     */
    public void setRemoveTexture() {
        this.removeTexture = true;
        logger.debug("Setting removeTexture={} on entity {}", true, this);
    }

    /**
     * Set removeCollision to true. The code that works subsequently is in update.
     * When the character's attack hits an obstacle, the obstacle triggers the effect of collision disappearance.
     * However, due to the issue of the animation's playing time, the character will still lose blood if the character
     * collides with an obstacle when the animation is not completed. Therefore, when a character attacks, the obstacle
     * needs to be removed in advance of the collision component.
     *
     * Note: only use when attack to obstacles.
     */
    public void setRemoveCollision() {
        this.removeCollision = true;
        logger.debug("Setting removeCollision={} on entity {}", true, this);
    }

    /**
     * Set dispose to true. The code that works subsequently is in update.
     */
    public void setDispose() {
        this.dispose = true;
        logger.debug("Setting dispose={} on entity {}", true, this);
    }

    /**
     * Get method of disappear.
     */
    public boolean isDisappear() {
        return disappear;
    }

    /**
     * Get method of removeTexture.
     */
    public boolean isRemoveTexture() {
        return removeTexture;
    }

    /**
     * Getter method of removeCollision
     * @return
     */
    public boolean isRemoveCollision() {
        return removeCollision;
    }

    /**
     * Get method of dispose.
     */
    public boolean isDispose() {
        return dispose;
    }

    /**
     * Get method of animationTime.
     *
     * @return How long the animation will disappear after playing
     */
    public float getAnimationTime() {
        return animationTime;
    }

    /**
     * Get the entity's game position.
     *
     * @return position
     */
    public Vector2 getPosition() {
        return position.cpy(); // Cpy gives us pass-by-value to prevent bugs
    }

    /**
     * Set the entity's game position.
     *
     * @param position new position.
     */
    public void setPosition(Vector2 position) {
        this.position = position.cpy();
        getEvents().trigger(EVT_NAME_POS, position.cpy());
    }

    /**
     * Set the entity's game position.
     *
     * @param x new x position
     * @param y new y position
     */
    public void setPosition(float x, float y) {
        this.position.x = x;
        this.position.y = y;
        getEvents().trigger(EVT_NAME_POS, position.cpy());
    }


    /**
     * Set the entity's game position and optionally notifies listeners.
     *
     * @param position new position.
     * @param notify   true to notify (default), false otherwise
     */
    public void setPosition(Vector2 position, boolean notify) {
        this.position = position;
        if (notify) {
            getEvents().trigger(EVT_NAME_POS, position);
        }
    }

    /**
     * Get the entity's scale. Used for rendering and physics bounding box calculations.
     *
     * @return Scale in x and y directions. 1 = 1 metre.
     */
    public Vector2 getScale() {
        return scale.cpy(); // Cpy gives us pass-by-value to prevent bugs
    }

    /**
     * Set the entity's scale.
     *
     * @param scale new scale in metres
     */
    public void setScale(Vector2 scale) {
        this.scale = scale.cpy();
    }

    /**
     * Set the entity's zIndex.
     *
     * @param zIndex Draw priority of the current entity
     */
    public void setZIndex(int zIndex) {
        this.zIndex = zIndex;
    }

    /**
     * Get the entity's zIndex. This is not the final drawing priority. Called by "getZIndex()" in RenderComponent.java.
     *
     * @return Current zIndex value
     */
    public int getZIndex() {
        return zIndex;
    }

    /**
     * Set the entity's scale.
     *
     * @param x width in metres
     * @param y height in metres
     */
    public void setScale(float x, float y) {
        this.scale.x = x;
        this.scale.y = y;
    }

    /**
     * Set the entity's width and scale the height to maintain aspect ratio.
     *
     * @param x width in metres
     */
    public void scaleWidth(float x) {
        this.scale.y = this.scale.y / this.scale.x * x;
        this.scale.x = x;
    }

    /**
     * Set the entity's height and scale the width to maintain aspect ratio.
     *
     * @param y height in metres
     */
    public void scaleHeight(float y) {
        this.scale.x = this.scale.x / this.scale.y * y;
        this.scale.y = y;
    }

    /**
     * Get the entity's center position
     *
     * @return center position
     */
    public Vector2 getCenterPosition() {
        return getPosition().mulAdd(getScale(), 0.5f);
    }

    /**
     * Get a component of type T on the entity.
     *
     * @param type The component class, e.g. RenderComponent.class
     * @param <T>  The component type, e.g. RenderComponent
     * @return The entity component, or null if nonexistent.
     */
    @SuppressWarnings("unchecked")
    public <T extends Component> T getComponent(Class<T> type) {
        ComponentType componentType = ComponentType.getFrom(type);
        return (T) components.get(componentType.getId());
    }

    /**
     * Add a component to the entity. Can only be called before the entity is registered in the world.
     *
     * @param component The component to add. Only one component of a type can be added to an entity.
     * @return Itself
     */
    public Entity addComponent(Component component) {
        if (created) {
            logger.error(
                    "Adding {} to {} after creation is not supported and will be ignored", component, this);
            return this;
        }
        ComponentType componentType = ComponentType.getFrom(component.getClass());
        if (components.containsKey(componentType.getId())) {
            logger.error(
                    "Attempted to add multiple components of class {} to {}. Only one component of a class "
                            + "can be added to an entity, this will be ignored.",
                    component,
                    this);
            return this;
        }
        components.put(componentType.getId(), component);
        component.setEntity(this);

        return this;
    }


    /**
     * Dispose of the entity. This will dispose of all components on this entity.
     */
    public void dispose() {
        for (Component component : createdComponents) {
            component.dispose();
        }
        ServiceLocator.getEntityService().unregister(this);
    }

    /**
     * Dispose of the entity. This will dispose of all components on this entity.
     */
    public void disposeExceptAnimationComponent() {
        Iterator<Component> i = createdComponents.iterator();
        while (i.hasNext()) {
            Component component = i.next(); // must be called before you can call i.remove()
            if (!component.getClass().equals(AnimationRenderComponent.class)) {
                component.dispose();
                i.remove();
                logger.debug("{} disposed on entity {}", component.getClass().getSimpleName(), this);
            } else {
                if (((AnimationRenderComponent) component).getCurrentAnimation() != null) {
                    ((AnimationRenderComponent) component).stopAnimation();
                }
            }
        }
        ServiceLocator.getEntityService().unregister(this);
    }
    /**
     * Let the obstacles disappear after playing the animation for animationTime second. Is called by update().
     * <p>
     * The purpose of setting this method: When dispose() is used for animation components, all entities that use the
     * same animation become black boxes. Therefore, this method is currently used to make obstacles disappear.
     */
    public void removeAfterAnimation() {
        if (this.getComponent(AnimationRenderComponent.class).getAnimationPlayTime() > animationTime) {
            Iterator<Component> i = createdComponents.iterator();
            while (i.hasNext()) {
                Component component = i.next(); // must be called before you can call i.remove()
                if (component.getClass().equals(AnimationRenderComponent.class)) {
                    ((AnimationRenderComponent) component).stopAnimation();
                    logger.debug("{} stopped on entity {}", component.getClass().getSimpleName(), this);
                } else if (!component.getClass().equals(ParticleRenderComponent.class) && !component.getClass().equals(PhysicsComponent.class)) {
                    component.dispose();
                    i.remove();
                    logger.debug("{} disposed on entity {}", component.getClass().getSimpleName(), this);
                }
            }
            if (particleTime == 0) {
                disposeExceptAnimationComponent();
            } else {
                disappearType = DisappearType.PARTICLE;
            }
        }
    }

    /**
     * Let the obstacles disappear after playing the particle for particleTime second. Is called by update().
     */
    public void removeAfterParticle() {
        if (this.getComponent(ParticleRenderComponent.class).getParticlePlayTime() > particleTime) {
            disposeExceptAnimationComponent();
        }
    }

    /**
     * Create the entity and start running. This is called when the entity is registered in the world,
     * and should not be called manually.
     */
    public void create() {
        if (created) {
            logger.error(
                    "{} was created twice. Entity should only be registered with the entity service once.",
                    this);
            return;
        }
        createdComponents = components.values().toArray();
        for (Component component : createdComponents) {
            component.create();
        }
        created = true;
    }

    /**
     * Perform an early update on all components. This is called by the entity service and should not
     * be called manually.
     */
    public void earlyUpdate() {
        if (!enabled) {
            return;
        }
        for (Component component : createdComponents) {
            component.triggerEarlyUpdate();
        }
    }

    /**
     * Perform an update on all components. This is called by the entity service and should not be
     * called manually.
     */
    public void update() {
        if (!enabled) {
            return;
        }

        if (dispose) {
            this.dispose();
            return;
        }

        Iterator<Component> i = createdComponents.iterator();
        while (i.hasNext()) {
            Component component = i.next();
            // When texture and animation are given an entity at the same time, the texture needs to disappear when the
            // animation is played to avoid the conflict between the texture and the animation.
            if (removeTexture) {
                if (component.getClass().equals(TextureRenderComponent.class)) {
                    component.dispose();
                    i.remove();
                    logger.debug("Remove {} on entity{}", component.getClass().getSimpleName(), this);
                }
            }

            if (removeCollision) {
                if (component.getClass().equals(HitboxComponent.class) || component.getClass().equals(ColliderComponent.class)) {
                    component.dispose();
                    i.remove();
                    logger.debug("Remove {} on entity{}", component.getClass().getSimpleName(), this);

                }
            }
            component.triggerUpdate();
        }

        if (disappear) {
            if (disappearType == DisappearType.ANIMATION) {
                this.removeAfterAnimation();
            } else if (disappearType == DisappearType.PARTICLE) {
                this.removeAfterParticle();
            } else {
                logger.error("Error disappearType = {}", disappearType);
            }

        }
    }

    /**
     * This entity's unique ID. Used for equality checks
     *
     * @return unique ID
     */
    public int getId() {
        return id;
    }

    /**
     * Get the event handler attached to this entity. Can be used to trigger events from an attached
     * component, or listen to events from a component.
     *
     * @return entity's event handler
     */
    public EventHandler getEvents() {
        return eventHandler;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Entity && ((Entity) obj).getId() == this.getId());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return String.format("Entity{id=%d, type=%s}", id, type);
    }
}
