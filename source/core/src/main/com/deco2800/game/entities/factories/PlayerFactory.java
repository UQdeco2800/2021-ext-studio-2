package com.deco2800.game.entities.factories;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.achievements.AchievementsDisplay;
import com.deco2800.game.components.achievements.AchievementsStatsComponent;
import com.deco2800.game.components.buff.BuffAnimationController;
import com.deco2800.game.components.npc.GhostAnimationController;
import com.deco2800.game.components.player.InventoryComponent;
import com.deco2800.game.components.player.PlayerActions;
import com.deco2800.game.components.player.PlayerStatsDisplay;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.configs.PlayerConfig;
import com.deco2800.game.files.FileLoader;
import com.deco2800.game.input.InputComponent;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.PhysicsUtils;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.rendering.TextureRenderComponent;
import com.deco2800.game.services.ServiceLocator;

/**
 * Factory to create a player entity.
 *
 * <p>Predefined player properties are loaded from a config stored as a json file and should have
 * the properties stores in 'PlayerConfig'.
 */
public class PlayerFactory {
    private static final PlayerConfig stats =
            FileLoader.readClass(PlayerConfig.class, "configs/player.json");

    /**
     * Create a player entity.
     *
     * @return entity
     */
    public static Entity createPlayer() {
        InputComponent inputComponent =
                ServiceLocator.getInputService().getInputFactory().createForPlayer();

        AnimationRenderComponent animator =
                new AnimationRenderComponent(
                        ServiceLocator.getResourceService()
                                .getAsset("images/mpcMovement.atlas",
                                        TextureAtlas.class));
        animator.addAnimation("main_player_run", 0.1f, Animation.PlayMode.LOOP);
        animator.addAnimation("main_player_walk", 0.5f, Animation.PlayMode.LOOP);
        animator.addAnimation("mpc_front", 1f, Animation.PlayMode.LOOP);
        AnimationRenderComponent buffAnimator =
                new AnimationRenderComponent(
                        ServiceLocator.getResourceService()
                                .getAsset("images/buff.atlas",
                                        TextureAtlas.class));
        buffAnimator.addAnimation("buffIncrease", 0.1f, Animation.PlayMode.LOOP);
        AnimationRenderComponent deBuffAnimator =
                new AnimationRenderComponent(
                        ServiceLocator.getResourceService()
                                .getAsset("images/debuff.atlas",
                                        TextureAtlas.class));
        deBuffAnimator.addAnimation("debuffDecrease", 0.1f, Animation.PlayMode.LOOP);
        Entity player =
                new Entity()
                        .addComponent(new TextureRenderComponent("images/mpc_front_stroke.png"))
                        .addComponent(new PhysicsComponent())
                        .addComponent(new ColliderComponent())
                        .addComponent(new HitboxComponent().setLayer(PhysicsLayer.PLAYER))
                        .addComponent(new PlayerActions())
                        .addComponent(new CombatStatsComponent(stats.health, stats.baseAttack))
                        .addComponent(new InventoryComponent(stats.gold))
                        .addComponent(inputComponent)
                        .addComponent(new PlayerStatsDisplay())
                        .addComponent(animator)
                        .addComponent(buffAnimator)
                        .addComponent(deBuffAnimator)
                        .addComponent(new BuffAnimationController());


        PhysicsUtils.setScaledCollider(player, 0.6f, 0.3f);
        player.getComponent(ColliderComponent.class).setDensity(1.5f);
        player.getComponent(TextureRenderComponent.class).scaleEntity();
        return player;
    }

    private PlayerFactory() {
        throw new IllegalStateException("Instantiating static util class");
    }
}
