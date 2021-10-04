package com.deco2800.game.components.player;

import com.deco2800.game.components.Component;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.rendering.TextureRenderComponent;

/**
 * This class listens to events relevant to a Player entity's state and
 * plays animations when player action events are triggered
 */

public class PlayerAnimationController extends Component {
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
        entity.getEvents().addListener("stopBuffDebuff", this::animateWalk);
    }
    /**
     *  Activate the hungry animation debuff
     */
    private void animateHungry() {
        animationName = animator.getCurrentAnimation();
        preAnimationCleanUp();
        switch (animationName) {
            case "main_player_run":
                animator.startAnimation("main_player_run_hungry");
                break;
            case "main_player_pickup":
                animator.startAnimation("main_player_pickup_hungry");
                break;
            case "main_player_jump":
                animator.startAnimation("main_player_jump_hungry");
                break;
            case "main_player_attack":
                animator.startAnimation("main_player_attack_hungry");
                break;
            case "main_player_crouch":
                animator.startAnimation("main_player_crouch_hungry");
                break;
            case "main_player_right":
                animator.startAnimation("main_player_right_hungry");
                break;
            case "main_player_walk":
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
        switch (animationName) {
            case "main_player_run":
                animator.startAnimation("main_player_run_poisoned");
                break;
            case "main_player_pickup":
                animator.startAnimation("main_player_pickup_poisoned");
                break;
            case "main_player_jump":
                animator.startAnimation("main_player_jump_poisoned");
                break;
            case "main_player_attack":
                animator.startAnimation("main_player_attack_poisoned");
                break;
            case "main_player_crouch":
                animator.startAnimation("main_player_crouch_poisoned");
                break;
            case "main_player_right":
                animator.startAnimation("main_player_right_poisoned");
                break;
            case "main_player_walk":
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
            case "main_player_run":
                animator.startAnimation("main_player_run_health-down");
                break;
            case "main_player_pickup":
                animator.startAnimation("main_player_pickup_health-down");
                break;
            case "main_player_jump":
                animator.startAnimation("main_player_jump_health-down");
                break;
            case "main_player_attack":
                animator.startAnimation("main_player_attack_health-down_health-down");
                break;
            case "main_player_crouch":
                animator.startAnimation("main_player_crouch_health-down");
                break;
            case "main_player_right":
                animator.startAnimation("main_player_right_health-down");
                break;
            case "main_player_walk":
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
            case "main_player_run":
                animator.startAnimation("main_player_run_dizzy");
                break;
            case "main_player_pickup":
                animator.startAnimation("main_player_pickup_dizzy");
                break;
            case "main_player_jump":
                animator.startAnimation("main_player_jump_dizzy");
                break;
            case "main_player_attack":
                animator.startAnimation("main_player_attack_dizzy");
                break;
            case "main_player_crouch":
                animator.startAnimation("main_player_crouch_dizzy");
                break;
            case "main_player_right":
                animator.startAnimation("main_player_right_dizzy");
                break;
            case "main_player_walk":
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
            case "main_player_run":
                animator.startAnimation("main_player_run_health-limit-up");
                break;
            case "main_player_pickup":
                animator.startAnimation("main_player_pickup_health-limit-up");
                break;
            case "main_player_jump":
                animator.startAnimation("main_player_jump_health-limit-up");
                break;
            case "main_player_attack":
                animator.startAnimation("main_player_attack_health-limit-up");
                break;
            case "main_player_crouch":
                animator.startAnimation("main_player_crouch_health-limit-up");
                break;
            case "main_player_right":
                animator.startAnimation("main_player_right_health-limit-up");
                break;
            case "main_player_walk":
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
        System.out.println("Starting Animation: "+ animationName);
        preAnimationCleanUp();
        switch (animationName) {
            case "main_player_run":
                animator.startAnimation("main_player_run_health-up");
                break;
            case "main_player_pickup":
                animator.startAnimation("main_player_pickup_health-up");
                break;
            case "main_player_jump":
                animator.startAnimation("main_player_jump_health-up");
                break;
            case "main_player_attack":
                animator.startAnimation("main_player_attack_health-up");
                break;
            case "main_player_crouch":
                animator.startAnimation("main_player_crouch_health-up");
                break;
            case "main_player_right":
                animator.startAnimation("main_player_right_health-up");
                break;
            case "main_player_walk":
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
            case "main_player_run":
                animator.startAnimation("main_player_run_recovered");
                break;
            case "main_player_pickup":
                animator.startAnimation("main_player_pickup_recovered");
                break;
            case "main_player_jump":
                animator.startAnimation("main_player_jump_recovered");
                break;
            case "main_player_attack":
                animator.startAnimation("main_player_attack_recovered");
                break;
            case "main_player_crouch":
                animator.startAnimation("main_player_crouch_recovered");
                break;
            case "main_player_right":
                animator.startAnimation("main_player_right_recovered");
                break;
            case "main_player_walk":
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
            case "main_player_run":
                animator.startAnimation("main_player_run_speed-down");
                break;
            case "main_player_pickup":
                animator.startAnimation("main_player_pickup_speed-down");
                break;
            case "main_player_jump":
                animator.startAnimation("main_player_jump_speed-down");
                break;
            case "main_player_attack":
                animator.startAnimation("main_player_attack_speed-down");
                break;
            case "main_player_crouch":
                animator.startAnimation("main_player_crouch_speed-down");
                break;
            case "main_player_right":
                animator.startAnimation("main_player_right_speed-down");
                break;
            case "main_player_walk":
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
            case "main_player_run":
                animator.startAnimation("main_player_run_thirsty");
                break;
            case "main_player_pickup":
                animator.startAnimation("main_player_pickup_thirsty");
                break;
            case "main_player_jump":
                animator.startAnimation("main_player_jump_thirsty");
                break;
            case "main_player_attack":
                animator.startAnimation("main_player_attack_thirsty");
                break;
            case "main_player_crouch":
                animator.startAnimation("main_player_crouch_thirsty");
                break;
            case "main_player_right":
                animator.startAnimation("main_player_right_thirsty");
                break;
            case "main_player_walk":
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
        animator.startAnimation("main_player_pickup");

    }
    /**
     * Makes the player crouch.
     */

    private void animateCrouch() {
        preAnimationCleanUp();
        animator.startAnimation("main_player_crouch");
        
    }

    /**
     * Makes the player run to the right.
     */

    private void animateRight() {
        preAnimationCleanUp();
        animator.startAnimation("main_player_run");
    }

    /**
     * Makes the player attack.
     */

    private void animateAttack() {
        preAnimationCleanUp();
        animator.startAnimation("main_player_attack");
    }

    /**
     * Makes the player jump.
     */

    private void animateJump() {
         preAnimationCleanUp();
         animator.startAnimation("main_player_jump");
    }

    /**
     * Helper function to stop all animations and trigger walking animation
     */

    private void animateWalk() {
        preAnimationCleanUp();
        animator.startAnimation("main_player_walk");
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
