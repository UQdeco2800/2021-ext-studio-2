package com.deco2800.game.entities.factories;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.buff.BuffDescriptionDisplay;
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


    /**
     * Create a player entity.
     *
     * @return entity
     */
    public static Entity createPlayer() {

       String attire = updateAttireConfig();

        InputComponent inputComponent =
                ServiceLocator.getInputService().getInputFactory().createForPlayer();

        AnimationRenderComponent mpcAnimator;
        TextureRenderComponent mpcTexture;
        System.out.println("Loading attire: "+ attire);
        switch (attire) {

            case "gold_2":
                mpcAnimator = createAnimationComponent("images/mpc/finalAtlas/gold_2/mpcAnimation_2.atlas");
                mpcTexture = new TextureRenderComponent("images/mpc/finalAtlas/gold_2/mpc_right.png");
                break;
            case "gold_4":
                mpcAnimator = createAnimationComponent("images/mpc/finalAtlas/gold_4_buff_to_be_test/mpcAnimation_4.atlas");
                mpcTexture = new TextureRenderComponent("images/mpc/finalAtlas/gold_4_buff_to_be_test/mpc_right.png");
                break;
            case "gold_6":
                mpcAnimator = createAnimationComponent("images/mpc/finalAtlas/gold_6_buff_to_be_tested/mpcAnimation_6.atlas");
                mpcTexture = new TextureRenderComponent("images/mpc/finalAtlas/gold_6_buff_to_be_tested/mpc_right.png");
                break;
            case "OG":
            default:
                mpcAnimator = createAnimationComponent("images/mpc/finalAtlas/OG/mpcAnimation.atlas");
                mpcTexture = new TextureRenderComponent("images/mpc/finalAtlas/OG/mpc_right.png");
                break;
        }
        mpcAnimator.addAnimation("main_player_run", 0.1f, Animation.PlayMode.LOOP);
        mpcAnimator.addAnimation("main_player_walk", 0.5f, Animation.PlayMode.LOOP);
        mpcAnimator.addAnimation("main_player_jump", 2.5f, Animation.PlayMode.LOOP);
        mpcAnimator.addAnimation("main_player_attack", 1f, Animation.PlayMode.LOOP);
        mpcAnimator.addAnimation("main_player_crouch", 1f, Animation.PlayMode.LOOP);
        mpcAnimator.addAnimation("main_player_right", 1f, Animation.PlayMode.LOOP);
        mpcAnimator.addAnimation("main_player_pickup",0.125f,Animation.PlayMode.LOOP);

        if (attire.equals("gold_6") || attire.equals("gold_4")) {

            mpcAnimator.addAnimation("main_player_run_dizzy", 0.1f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_run_health-down", 0.1f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_run_health-limit-up", 0.1f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_run_health-up", 0.1f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_run_hungry", 0.1f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_run_poisoned", 0.1f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_run_recovered", 0.1f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_run_speed-down", 0.1f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_run_thirsty", 0.1f, Animation.PlayMode.LOOP);

            mpcAnimator.addAnimation("main_player_walk_dizzy", 0.5f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_walk_health-down", 0.5f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_walk_health-limit-up", 0.5f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_walk_health-up", 0.5f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_walk_hungry", 0.5f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_walk_poisoned", 0.5f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_walk_recovered", 0.5f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_walk_speed-down", 0.5f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_walk_thirsty", 0.5f, Animation.PlayMode.LOOP);

            mpcAnimator.addAnimation("main_player_jump_dizzy", 2.5f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_jump_health-down", 2.5f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_jump_health-limit-up", 2.5f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_jump_health-up", 2.5f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_jump_hungry", 2.5f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_jump_poisoned", 2.5f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_jump_recovered", 2.5f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_jump_speed-down", 2.5f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_jump_thirsty", 2.5f, Animation.PlayMode.LOOP);

            mpcAnimator.addAnimation("main_player_attack_dizzy", 1f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_attack_health-down", 1f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_attack_health-limit-up", 1f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_attack_health-up", 1f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_attack_hungry", 1f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_attack_poisoned", 1f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_attack_recovered", 1f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_attack_speed-down", 1f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_attack_thirsty", 1f, Animation.PlayMode.LOOP);


            mpcAnimator.addAnimation("main_player_crouch_dizzy", 1f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_crouch_health-down", 1f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_crouch_health-limit-up", 1f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_crouch_health-up", 1f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_crouch_hungry", 1f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_crouch_poisoned", 1f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_crouch_recovered", 1f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_crouch_speed-down", 1f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_crouch_thirsty", 1f, Animation.PlayMode.LOOP);


            mpcAnimator.addAnimation("main_player_right_dizzy", 1f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_right_health-down", 1f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_right_health-limit-up", 1f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_right_health-up", 1f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_right_hungry", 1f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_right_poisoned", 1f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_right_recovered", 1f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_right_speed-down", 1f, Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_right_thirsty", 1f, Animation.PlayMode.LOOP);

            mpcAnimator.addAnimation("main_player_pickup_dizzy",0.125f,Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_pickup_health-down",0.125f,Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_pickup_health-limit-up",0.125f,Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_pickup_health-up",0.125f,Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_pickup_hungry",0.125f,Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_pickup_poisoned",0.125f,Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_pickup_recovered",0.125f,Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_pickup_speed-down",0.125f,Animation.PlayMode.LOOP);
            mpcAnimator.addAnimation("main_player_pickup_thirsty",0.125f,Animation.PlayMode.LOOP);

        }

        Entity player =
                new Entity("Player")
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
                        .addComponent(new BuffDescriptionDisplay())
                        .addComponent(mpcAnimator);


        PhysicsUtils.setScaledCollider(player, 0.6f, 0.3f);
        player.getComponent(ColliderComponent.class).setDensity(1.5f);
        player.getComponent(TextureRenderComponent.class).scaleEntity();
        player.getEvents().trigger("startMPCAnimation");
        return player;
    }

    private PlayerFactory() {
        throw new IllegalStateException("Instantiating static util class");
    }

    private static String updateAttireConfig() {
        final String ROOT_DIR = "DECO2800Game";
        final String CONFIG_FILE = "mpc.json";
        final String path = ROOT_DIR + File.separator + CONFIG_FILE;
        final PlayerConfig config =
                FileLoader.readClass(PlayerConfig.class, path, FileLoader.Location.EXTERNAL);
        String attire;
        assert config != null;
        attire = config.attire;
        return attire;
    }

    private static AnimationRenderComponent createAnimationComponent(String atlasPath) {
        return new AnimationRenderComponent(
                ServiceLocator.getResourceService()
                        .getAsset(atlasPath,
                                TextureAtlas.class));
    }


}
