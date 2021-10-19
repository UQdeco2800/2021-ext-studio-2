package com.deco2800.game.components.player;

import com.deco2800.game.components.Component;
import com.deco2800.game.entities.factories.PlayerFactory;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.rendering.TextureRenderComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class listens to events relevant to a Player entity's state and
 * plays animations when player action events are triggered
 */

public class PlayerAnimationController extends Component {

    private static final Logger logger = LoggerFactory.getLogger(PlayerAnimationController.class);
    public static final String MAIN_PLAYER_RUN = "main_player_run";
    public static final String MAIN_PLAYER_PICKUP = "main_player_pickup";
    public static final String MAIN_PLAYER_JUMP = "main_player_jump";
    public static final String MAIN_PLAYER_ATTACK = "main_player_attack";
    public static final String MAIN_PLAYER_CROUCH = "main_player_crouch";
    public static final String MAIN_PLAYER_RIGHT = "main_player_right";
    public static final String MAIN_PLAYER_WALK = "main_player_walk";
    public static final String MAIN_PLAYER_HURT = "main_player_hurt";
    public static final String MAIN_PLAYER_BURN = "main_player_burn";

    AnimationRenderComponent animator;
    private boolean texturePresent = true;
    String animationName;
    /**
     * The function create animation listeners and
     * adds them to the events of the entity,
     * so that they can be triggered
     */
    @Override
    public void create() {
        super.create();
        animator = this.entity.getComponent(AnimationRenderComponent.class);
        entity.getEvents().addListener("walkRight", this::animateRight);
        entity.getEvents().addListener("crouch", this::animateCrouch);
        entity.getEvents().addListener("stopCrouch", this::animateWalk);
        entity.getEvents().addListener("attack", this::animateAttack);
        entity.getEvents().addListener("stopAttack", this::animateWalk);
        entity.getEvents().addListener("jump", this::animateJump);
        entity.getEvents().addListener("stopJump", this::animateWalk);
        entity.getEvents().addListener("itemPickUp", this::animatePickUp);
        entity.getEvents().addListener("stopPickUp", this::animateWalk);
        entity.getEvents().addListener("startMPCAnimation", this::animateWalk);
        entity.getEvents().addListener("stopMPCAnimation", this::preAnimationCleanUp);
        entity.getEvents().addListener("hungry", this::animateHungry);
        entity.getEvents().addListener("poisoned", this::animatePoison);
        entity.getEvents().addListener("healthDown", this::animateHealthDown);
        entity.getEvents().addListener("dizzy", this::animateDizzy);
        entity.getEvents().addListener("health_limit_up", this::animateHealthLimit);
        entity.getEvents().addListener("healthUp", this::animateHealthUP);
        entity.getEvents().addListener("recovered", this::animateRecovered);
        entity.getEvents().addListener("speedDown", this::animateSpeedDown);
        entity.getEvents().addListener("thirsty", this::animateThirsty);
        entity.getEvents().addListener("hurt", this::animateHurt);
        entity.getEvents().addListener("burn", this::animateBurn);
        entity.getEvents().addListener("stopBuffDebuff", this::animateWalk);
        entity.getEvents().addListener("stopAnimations", this::animateWalk);
    }

    /**
     *  Activate the hungry animation debuff
     */
    private void animateHungry() {
        animationName = animator.getCurrentAnimation();
        preAnimationCleanUp();
        switch (animationName) {
            case MAIN_PLAYER_RUN:
                animator.startAnimation("main_player_run_hungry");
                break;
            case MAIN_PLAYER_PICKUP:
                animator.startAnimation("main_player_pickup_hungry");
                break;
            case MAIN_PLAYER_JUMP:
                animator.startAnimation("main_player_jump_hungry");
                break;
            case MAIN_PLAYER_ATTACK:
                animator.startAnimation("main_player_attack_hungry");
                break;
            case MAIN_PLAYER_CROUCH:
                animator.startAnimation("main_player_crouch_hungry");
                break;
            case MAIN_PLAYER_RIGHT:
                animator.startAnimation("main_player_right_hungry");
                break;
            case MAIN_PLAYER_HURT:
                animator.startAnimation("main_player_hurt_hungry");
                break;
            case MAIN_PLAYER_BURN:
                animator.startAnimation("main_player_burn_hungry");
                break;
            case MAIN_PLAYER_WALK:
            default:
                animator.startAnimation("main_player_walk_hungry");
                break;
        }
    }
    /**
     *  Activate the poison animation debuff
     */
    private void animatePoison() {
        animationName = animator.getCurrentAnimation();
        preAnimationCleanUp();
        logger.info("Loading Posion");
        switch (animationName) {
            case MAIN_PLAYER_RUN:
                animator.startAnimation("main_player_run_poisoned");
                break;
            case MAIN_PLAYER_PICKUP:
                animator.startAnimation("main_player_pickup_poisoned");
                break;
            case MAIN_PLAYER_JUMP:
                animator.startAnimation("main_player_jump_poisoned");
                break;
            case MAIN_PLAYER_ATTACK:
                animator.startAnimation("main_player_attack_poisoned");
                break;
            case MAIN_PLAYER_CROUCH:
                animator.startAnimation("main_player_crouch_poisoned");
                break;
            case MAIN_PLAYER_RIGHT:
                animator.startAnimation("main_player_right_poisoned");
                break;
            case MAIN_PLAYER_HURT:
                animator.startAnimation("main_player_hurt_poisoned");
                break;
            case MAIN_PLAYER_BURN:
                animator.startAnimation("main_player_burn_poisoned");
                break;
            case MAIN_PLAYER_WALK:
            default:
                animator.startAnimation("main_player_walk_poisoned");
                break;
        }
    }
    /**
     *  Activate the Health Down animation debuff
     */
    private void animateHealthDown() {
        animationName = animator.getCurrentAnimation();
        preAnimationCleanUp();
        switch (animationName) {
            case MAIN_PLAYER_RUN:
                animator.startAnimation("main_player_run_health-down");
                break;
            case MAIN_PLAYER_PICKUP:
                animator.startAnimation("main_player_pickup_health-down");
                break;
            case MAIN_PLAYER_JUMP:
                animator.startAnimation("main_player_jump_health-down");
                break;
            case MAIN_PLAYER_ATTACK:
                animator.startAnimation("main_player_attack_health-down_health-down");
                break;
            case MAIN_PLAYER_CROUCH:
                animator.startAnimation("main_player_crouch_health-down");
                break;
            case MAIN_PLAYER_RIGHT:
                animator.startAnimation("main_player_right_health-down");
                break;
            case MAIN_PLAYER_HURT:
                animator.startAnimation("main_player_hurt_health-down");
                break;
            case MAIN_PLAYER_BURN:
                animator.startAnimation("main_player_burn_health-down");
                break;
            case MAIN_PLAYER_WALK:
            default:
                animator.startAnimation("main_player_walk_health-down");
                break;
        }
    }
    /**
     *  Activate the Dizzy animation debuff
     */
    private void animateDizzy() {
        animationName = animator.getCurrentAnimation();
        preAnimationCleanUp();
        switch (animationName) {
            case MAIN_PLAYER_RUN:
                animator.startAnimation("main_player_run_dizzy");
                break;
            case MAIN_PLAYER_PICKUP:
                animator.startAnimation("main_player_pickup_dizzy");
                break;
            case MAIN_PLAYER_JUMP:
                animator.startAnimation("main_player_jump_dizzy");
                break;
            case MAIN_PLAYER_ATTACK:
                animator.startAnimation("main_player_attack_dizzy");
                break;
            case MAIN_PLAYER_CROUCH:
                animator.startAnimation("main_player_crouch_dizzy");
                break;
            case MAIN_PLAYER_RIGHT:
                animator.startAnimation("main_player_right_dizzy");
                break;
            case MAIN_PLAYER_HURT:
                animator.startAnimation("main_player_hurt_dizzy");
                break;
            case MAIN_PLAYER_BURN:
                animator.startAnimation("main_player_burn_dizzy");
                break;
            case MAIN_PLAYER_WALK:
            default:
                animator.startAnimation("main_player_walk_dizzy");
                break;
        }
    }
    /**
     *  Activate the HealthLimit animation debuff
     */
    private void animateHealthLimit() {
        animationName = animator.getCurrentAnimation();
        preAnimationCleanUp();
        switch (animationName) {
            case MAIN_PLAYER_RUN:
                animator.startAnimation("main_player_run_health-limit-up");
                break;
            case MAIN_PLAYER_PICKUP:
                animator.startAnimation("main_player_pickup_health-limit-up");
                break;
            case MAIN_PLAYER_JUMP:
                animator.startAnimation("main_player_jump_health-limit-up");
                break;
            case MAIN_PLAYER_ATTACK:
                animator.startAnimation("main_player_attack_health-limit-up");
                break;
            case MAIN_PLAYER_CROUCH:
                animator.startAnimation("main_player_crouch_health-limit-up");
                break;
            case MAIN_PLAYER_RIGHT:
                animator.startAnimation("main_player_right_health-limit-up");
                break;
            case MAIN_PLAYER_HURT:
                animator.startAnimation("main_player_hurt_health-limit-up");
                break;
            case MAIN_PLAYER_BURN:
                animator.startAnimation("main_player_burn_health-limit-up");
                break;
            case MAIN_PLAYER_WALK:
            default:
                animator.startAnimation("main_player_walk_health-limit-up");
                break;
        }
    }
    /**
     *  Activate the Health UP animation buff
     */
    private void animateHealthUP() {
        animationName = animator.getCurrentAnimation();
        preAnimationCleanUp();
        switch (animationName) {
            case MAIN_PLAYER_RUN:
                animator.startAnimation("main_player_run_health-up");
                break;
            case MAIN_PLAYER_PICKUP:
                animator.startAnimation("main_player_pickup_health-up");
                break;
            case MAIN_PLAYER_JUMP:
                animator.startAnimation("main_player_jump_health-up");
                break;
            case MAIN_PLAYER_ATTACK:
                animator.startAnimation("main_player_attack_health-up");
                break;
            case MAIN_PLAYER_CROUCH:
                animator.startAnimation("main_player_crouch_health-up");
                break;
            case MAIN_PLAYER_RIGHT:
                animator.startAnimation("main_player_right_health-up");
                break;
            case MAIN_PLAYER_HURT:
                animator.startAnimation("main_player_hurt_health-up");
                break;
            case MAIN_PLAYER_BURN:
                animator.startAnimation("main_player_burn_health-up");
                break;
            case MAIN_PLAYER_WALK:
            default:
                animator.startAnimation("main_player_walk_health-up");
                break;
        }
    }
    /**
     *  Activate the Recovered animation buff
     */
    private void animateRecovered() {
        animationName = animator.getCurrentAnimation();
        preAnimationCleanUp();
        switch (animationName) {
            case MAIN_PLAYER_RUN:
                animator.startAnimation("main_player_run_recovered");
                break;
            case MAIN_PLAYER_PICKUP:
                animator.startAnimation("main_player_pickup_recovered");
                break;
            case MAIN_PLAYER_JUMP:
                animator.startAnimation("main_player_jump_recovered");
                break;
            case MAIN_PLAYER_ATTACK:
                animator.startAnimation("main_player_attack_recovered");
                break;
            case MAIN_PLAYER_CROUCH:
                animator.startAnimation("main_player_crouch_recovered");
                break;
            case MAIN_PLAYER_RIGHT:
                animator.startAnimation("main_player_right_recovered");
                break;
            case MAIN_PLAYER_HURT:
                animator.startAnimation("main_player_hurt_recovered");
                break;
            case MAIN_PLAYER_BURN:
                animator.startAnimation("main_player_burn_recovered");
                break;
            case MAIN_PLAYER_WALK:
            default:
                animator.startAnimation("main_player_walk_recovered");
                break;
        }
    }
    /**
     *  Activate the Speed Down animation debuff
     */
    private void animateSpeedDown() {
        animationName = animator.getCurrentAnimation();
        preAnimationCleanUp();
        switch (animationName) {
            case MAIN_PLAYER_RUN:
                animator.startAnimation("main_player_run_speed-down");
                break;
            case MAIN_PLAYER_PICKUP:
                animator.startAnimation("main_player_pickup_speed-down");
                break;
            case MAIN_PLAYER_JUMP:
                animator.startAnimation("main_player_jump_speed-down");
                break;
            case MAIN_PLAYER_ATTACK:
                animator.startAnimation("main_player_attack_speed-down");
                break;
            case MAIN_PLAYER_CROUCH:
                animator.startAnimation("main_player_crouch_speed-down");
                break;
            case MAIN_PLAYER_RIGHT:
                animator.startAnimation("main_player_right_speed-down");
                break;
            case MAIN_PLAYER_HURT:
                animator.startAnimation("main_player_hurt_speed-down");
                break;
            case MAIN_PLAYER_BURN:
                animator.startAnimation("main_player_burn_speed-down");
                break;
            case MAIN_PLAYER_WALK:
            default:
                animator.startAnimation("main_player_walk_speed-down");
                break;
        }
    }
    /**
     *  Activate the Thirsty animation debuff
     */
    private void animateThirsty() {
        animationName = animator.getCurrentAnimation();
        preAnimationCleanUp();
        switch (animationName) {
            case MAIN_PLAYER_RUN:
                animator.startAnimation("main_player_run_thirsty");
                break;
            case MAIN_PLAYER_PICKUP:
                animator.startAnimation("main_player_pickup_thirsty");
                break;
            case MAIN_PLAYER_JUMP:
                animator.startAnimation("main_player_jump_thirsty");
                break;
            case MAIN_PLAYER_ATTACK:
                animator.startAnimation("main_player_attack_thirsty");
                break;
            case MAIN_PLAYER_CROUCH:
                animator.startAnimation("main_player_crouch_thirsty");
                break;
            case MAIN_PLAYER_RIGHT:
                animator.startAnimation("main_player_right_thirsty");
                break;
            case MAIN_PLAYER_HURT:
                animator.startAnimation("main_player_hurt_thirsty");
                break;
            case MAIN_PLAYER_BURN:
                animator.startAnimation("main_player_burn_thirsty");
                break;
            case MAIN_PLAYER_WALK:
            default:
                animator.startAnimation("main_player_walk_thirsty");
                break;
        }
    }


    /**
     *  Makes the player pickup items
     */
    private void animatePickUp() {
        preAnimationCleanUp();
        animator.startAnimation(MAIN_PLAYER_PICKUP);

    }
    /**
     * Makes the player crouch.
     */

    private void animateCrouch() {
        preAnimationCleanUp();
        animator.startAnimation(MAIN_PLAYER_CROUCH);
        
    }

    /**
     * Makes the player run to the right.
     */

    private void animateRight() {
        preAnimationCleanUp();
        animator.startAnimation(MAIN_PLAYER_RUN);
    }

    /**
     * Makes the player attack.
     */

    private void animateAttack() {
        preAnimationCleanUp();
        animator.startAnimation(MAIN_PLAYER_ATTACK);
    }

    /**
     * Makes the player jump.
     */

    private void animateJump() {
         preAnimationCleanUp();
         animator.startAnimation(MAIN_PLAYER_JUMP);
    }

    /**
     * Helper function to stop all animations and trigger walking animation
     */

    private void animateWalk() {
        preAnimationCleanUp();
        animator.startAnimation(MAIN_PLAYER_WALK);
    }

    /**
     * Animates MPC hurting
     */

    private void animateHurt() {
        preAnimationCleanUp();
        animator.startAnimation(MAIN_PLAYER_HURT);
    }

    /**
     * Animates MPC burning
     */

    private void animateBurn() {
        preAnimationCleanUp();
        animator.startAnimation(MAIN_PLAYER_BURN);
    }

    /**
     * Helper function to dispose texture (if it exists) and stop all prior running animations.
     */

    private void preAnimationCleanUp() {
        if(texturePresent) {
            animator.getEntity().getComponent(TextureRenderComponent.class).dispose();
            texturePresent = false;
        }
        animator.stopAnimation();
    }

    public void setTexturePresent(boolean texturePresent) {
        this.texturePresent = texturePresent;
    }
}
