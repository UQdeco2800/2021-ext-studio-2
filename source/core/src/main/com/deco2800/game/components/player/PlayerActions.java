package com.deco2800.game.components.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.deco2800.game.components.CameraComponent;
import com.deco2800.game.components.Component;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.factories.RenderFactory;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.rendering.Renderer;
import com.deco2800.game.rendering.TextureRenderComponent;
import com.deco2800.game.services.ServiceLocator;

/**
 * Action component for interacting with the player. Player events should be initialised in create()
 * and when triggered should call methods within this class.
 */
public class PlayerActions extends Component {
  private static final Vector2 MAX_SPEED = new Vector2(4f, 8f); // Metres per second

  private PhysicsComponent physicsComponent;
  private Vector2 walkDirection = Vector2.Zero.cpy();
  private boolean moving = false;
  AnimationRenderComponent animator;
  @Override
  public void create() {
    animator = this.entity.getComponent(AnimationRenderComponent.class);
    physicsComponent = entity.getComponent(PhysicsComponent.class);
    entity.getEvents().addListener("walk", this::walk);
    entity.getEvents().addListener("walkStop", this::stopWalking);
    entity.getEvents().addListener("walkRight", this::walkRight);
    entity.getEvents().addListener("walkLeft", this::walkLeft);
    entity.getEvents().addListener("attack", this::attack);
    entity.getEvents().addListener("jump", this::jump);
  }



  public void changeCurrentSpeed(Vector2 currentSpeed) {
    Body body = physicsComponent.getBody();
    Vector2 velocity = body.getLinearVelocity();
    Vector2 desiredVelocity = walkDirection.cpy().scl(currentSpeed);
    // impulse = (desiredVel - currentVel) * mass
    Vector2 impulse = desiredVelocity.sub(velocity).scl(body.getMass());
    body.applyLinearImpulse(impulse, body.getWorldCenter(), true);
  }


  @Override
  public void update() {
    if (moving) {
      updateSpeed();
    }
  }

  private void updateSpeed() {
    Body body = physicsComponent.getBody();
    Vector2 velocity = body.getLinearVelocity();
    Vector2 desiredVelocity = walkDirection.cpy().scl(MAX_SPEED);
    // impulse = (desiredVel - currentVel) * mass
    Vector2 impulse = desiredVelocity.sub(velocity).scl(body.getMass());
    body.applyLinearImpulse(impulse, body.getWorldCenter(), true);
  }

  /**
   * Moves the player towards a given direction.
   *
   * @param direction direction to move in
   */
  void walk(Vector2 direction) {
    this.walkDirection = direction;
    moving = true;
  }

  /**
   * Stops the player from walking.
   */
  void stopWalking() {
    this.walkDirection = Vector2.Zero.cpy();
    updateSpeed();
    moving = false;
  }

  /**
   * Updates the player sprite to turn right
   */

  void walkRight() {
//    animator.getEntity().setRemoveTexture();
//    animator.stopAnimation();
//    animator.startAnimation("main_player_run");
    Sound turnSound = ServiceLocator.getResourceService().getAsset("sounds/turnDirection.ogg", Sound.class);
    turnSound.play();
  }
//
//  void stopWalkRight() {
//    animator.stopAnimation();
//    animator.startAnimation("main_player_walk");
//
//  }

  /** [[DEPRECATED]]
   * Updates the player sprite to turn left
   */
  
  void walkLeft() {
  }

  /**
   * Makes the player attack.
   */
  void attack() {
    Sound attackSound = ServiceLocator.getResourceService().getAsset("sounds/Impact4.ogg", Sound.class);
    attackSound.play();
  }

  /**
   * Makes the player jump
   */

  void jump() {
    Sound jumpSound = ServiceLocator.getResourceService().getAsset("sounds/jump.ogg", Sound.class);
    jumpSound.play();
    
    Body body = physicsComponent.getBody();
    body.applyForceToCenter(0, 400f, true);

//    Vector2 a = body.getLinearVelocity();
//    float force = 0;
//
//    if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
//      if(a.x < 50) force = 400f;
//    }
//    else if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)){
//      if(a.x > -50) force = -400f;
//    }
//    else{
//      body.getFixtureList().get(0).setFriction(.4f);
//      body.getFixtureList().get(0).setFriction(.4f);
//    }
//
//    if(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)){
//      a.y = 5;
//      body.setLinearVelocity(a);
//    }
//    body.applyForceToCenter(force, 400f, true);



  }
}
