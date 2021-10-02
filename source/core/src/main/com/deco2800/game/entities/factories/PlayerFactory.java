package com.deco2800.game.entities.factories;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.buff.BuffAnimationController;
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

import java.io.File;

/**
 * Factory to create a player entity.
 *
 * <p>Predefined player properties are loaded from a config stored as a json file and should have
 * the properties stores in 'PlayerConfig'.
 */
public class PlayerFactory {
    private static final PlayerConfig stats =
            FileLoader.readClass(PlayerConfig.class, "configs/player.json");

    private static final String ROOT_DIR = "DECO2800Game";
    private static final String CONFIG_FILE = "mpc.json";
    private static final String path = ROOT_DIR + File.separator + CONFIG_FILE;
    private static final PlayerConfig config =
            FileLoader.readClass(PlayerConfig.class, path, FileLoader.Location.EXTERNAL);
    private static final String attire;

    static {
        assert config != null;
        attire = config.attire;
    }


    /**
     * Create a player entity.
     *
     * @return entity
     */
    public static Entity createPlayer() {
        InputComponent inputComponent =
                ServiceLocator.getInputService().getInputFactory().createForPlayer();

        AnimationRenderComponent mpcAnimator;
        TextureRenderComponent mpcTexture;
        switch (attire) {

            case "gold_2":
                mpcAnimator = createAnimationComponent("images/mpc/finalAtlas/mpcAnimation_2.atlas");
                mpcTexture = new TextureRenderComponent("images/mpc/finalAtlas/mpc_right_2.png");
                break;
            case "gold_4":
                mpcAnimator = createAnimationComponent("images/mpc/finalAtlas/mpcAnimation_4.atlas");
                mpcTexture = new TextureRenderComponent("images/mpc/finalAtlas/mpc_right_4.png");
                break;
            case "gold_6":
                mpcAnimator = createAnimationComponent("images/mpc/finalAtlas/mpcAnimation_6.atlas");
                mpcTexture = new TextureRenderComponent("images/mpc/finalAtlas/mpc_right_6.png");
                break;
            case "OG":
            default:
                mpcAnimator = createAnimationComponent("images/mpc/finalAtlas/mpcAnimation.atlas");
                mpcTexture = new TextureRenderComponent("images/mpc/finalAtlas/mpc_right.png");
                break;
        }
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

        System.out.println("Read "+ attire);



        Entity player =
                new Entity()
                        .addComponent(mpcTexture)
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
