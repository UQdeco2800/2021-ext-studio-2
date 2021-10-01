package com.deco2800.game.components.player;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.components.npc.SpaceshipAttackController;
import com.deco2800.game.input.InputComponent;
import com.deco2800.game.screens.MainGameScreen;
import com.deco2800.game.utils.math.Vector2Utils;

/**
 * Input handler for the player for keyboard and touch (mouse) input.
 * This input handler only uses keyboard input.
 */
public class KeyboardPlayerInputComponent extends InputComponent {



  private final Vector2 walkDirection = Vector2.Zero.cpy();

  public KeyboardPlayerInputComponent() {
    super(5);
  }

  /**
   * Triggers player events on specific keycodes.
   *
   * @return whether the input was processed
   * @see InputProcessor#keyDown(int)
   */
  @Override
  public boolean keyDown(int keycode) {
    switch (keycode) {

      case Keys.S:
      case Keys.DOWN:
        entity.getEvents().trigger(("crouch"));
        return true;
      case Keys.D:
      case Keys.RIGHT:
        if (SpaceshipAttackController.spaceshipState == SpaceshipAttackController.spaceshipAttack.On) {
          return false;
        }
        walkDirection.add(Vector2Utils.RIGHT);
        triggerWalkEvent();
        entity.getEvents().trigger(("walkRight"));
        return true;
      case Keys.SPACE:
        entity.getEvents().trigger("attack");
        return true;
      case Keys.W:
      case Keys.UP:
        entity.getEvents().trigger(("jump"));
        return true;

      case Keys.B:
        entity.getEvents().trigger("B pressed");

      case Keys.X:
        entity.getEvents().trigger("itemPickUp");

        return true;
      default:
        return false;
    }
  }

  /**
   * Triggers player events on specific keycodes.
   *
   * @return whether the input was processed
   * @see InputProcessor#keyUp(int)
   */
  @Override
  public boolean keyUp(int keycode) {
    switch (keycode) {
      case Keys.S:
      case Keys.DOWN:
        entity.getEvents().trigger(("stopCrouch"));
        return true;
      case Keys.D:
      case Keys.RIGHT:
        if (SpaceshipAttackController.spaceshipState == SpaceshipAttackController.spaceshipAttack.On) {
          return false;
        }
        walkDirection.sub(Vector2Utils.RIGHT);
        entity.getEvents().trigger(("stopWalkRight"));
        triggerWalkEvent();
        return true;
      case Keys.W:
      case Keys.UP:
        entity.getEvents().trigger("stopJump");
        return true;
      case Keys.SPACE:
        entity.getEvents().trigger("stopAttack");
        return true;
      case Keys.X:
        entity.getEvents().trigger("stopPickUp");
        return true;
      default:
        return false;
    }
  }

  private void triggerWalkEvent() {
    if (walkDirection.epsilonEquals(Vector2.Zero)) {
      entity.getEvents().trigger("walkStop");
    } else {
      entity.getEvents().trigger("walk", walkDirection);
    }
  }

  public Vector2 getWalkDirection() {
    return walkDirection;
  }
}
