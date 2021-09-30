package com.deco2800.game.entities.factories;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.achievements.AchievementsDisplay;
import com.deco2800.game.components.achievements.AchievementsStatsComponent;
import com.deco2800.game.components.buff.Buff;
import com.deco2800.game.components.buff.BuffAnimationController;
//import com.deco2800.game.components.npc.GhostAnimationController;
import com.deco2800.game.components.buff.DeBuff;
import com.deco2800.game.components.player.InventoryComponent;
import com.deco2800.game.components.player.PlayerActions;
import com.deco2800.game.components.player.PlayerAnimationController;
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

        AnimationRenderComponent mpcAnimator = createAnimationComponent("images/mpc/mpcAnimation.atlas");
        mpcAnimator.addAnimation("main_player_run", 0.1f, Animation.PlayMode.LOOP);
        mpcAnimator.addAnimation("main_player_walk", 0.5f, Animation.PlayMode.LOOP);
        mpcAnimator.addAnimation("main_player_front", 1f, Animation.PlayMode.LOOP);
        mpcAnimator.addAnimation("main_player_jump", 2.5f, Animation.PlayMode.LOOP);
        mpcAnimator.addAnimation("main_player_attack", 1f, Animation.PlayMode.LOOP);
        mpcAnimator.addAnimation("main_player_crouch", 1f, Animation.PlayMode.LOOP);
        mpcAnimator.addAnimation("main_player_right", 1f, Animation.PlayMode.LOOP);
        mpcAnimator.addAnimation("main_player_pickup",0.125f,Animation.PlayMode.LOOP);

        AnimationRenderComponent buffAnimator = createAnimationComponent("images/buff.atlas");
        buffAnimator.addAnimation("buffIncrease", 0.1f, Animation.PlayMode.LOOP);

        AnimationRenderComponent deBuffAnimator = createAnimationComponent("images/debuff.atlas");
        deBuffAnimator.addAnimation("debuffDecrease", 0.1f, Animation.PlayMode.LOOP);



        Entity player =
                new Entity()
                        .addComponent(new TextureRenderComponent("images/mpc/mpc_right.png"))
                        .addComponent(new PlayerAnimationController())
                        .addComponent(new PhysicsComponent())
                        .addComponent(new ColliderComponent().setLayer(PhysicsLayer.PLAYERCOLLIDER))
                        .addComponent(new HitboxComponent().setLayer(PhysicsLayer.PLAYER))
                        .addComponent(new PlayerActions())
                        .addComponent(new CombatStatsComponent(stats.health, stats.baseAttack))
                        .addComponent(new InventoryComponent(stats.gold))
                        .addComponent(inputComponent)
                        .addComponent(new PlayerStatsDisplay())
                        .addComponent(mpcAnimator)
                        .addComponent(buffAnimator)
                        .addComponent(deBuffAnimator)
                        .addComponent(new BuffAnimationController());


        PhysicsUtils.setScaledCollider(player, 0.6f, 0.3f);
        player.getComponent(ColliderComponent.class).setDensity(1.5f);
        player.getComponent(TextureRenderComponent.class).scaleEntity();
        player.getEvents().trigger(("startMPCAnimation"));
        return player;
    }

    private PlayerFactory() {
        throw new IllegalStateException("Instantiating static util class");
    }

    private static AnimationRenderComponent createAnimationComponent(String atlasPath) {
        return new AnimationRenderComponent(
                ServiceLocator.getResourceService()
                        .getAsset(atlasPath,
                                TextureAtlas.class));
    }

}
