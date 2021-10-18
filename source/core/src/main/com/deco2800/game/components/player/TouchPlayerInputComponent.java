package com.deco2800.game.components.player;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.input.InputComponent;
import com.deco2800.game.utils.math.Vector2Utils;
import com.badlogic.gdx.InputProcessor;

/**
 * Input handler for the player for keyboard and touch (mouse) input.
 * This input handler uses keyboard and touch input.
 */
public class TouchPlayerInputComponent extends InputComponent {
  private final Vector2 walkDirection = Vector2.Zero.cpy();

  public TouchPlayerInputComponent() {
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
      case Input.Keys.UP:
        entity.getEvents().trigger(("jump"));
        return true;
      case Input.Keys.DOWN:
        entity.getEvents().trigger(("crouch"));
        return true;
      case Input.Keys.RIGHT:
        walkDirection.add(Vector2Utils.RIGHT);
        triggerWalkEvent();
        entity.getEvents().trigger(("walkRight"));
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
      case Input.Keys.UP:
        entity.getEvents().trigger("stopJump");
        return true;
      case Input.Keys.DOWN:
        entity.getEvents().trigger(("stopCrouch"));
        return true;
      case Input.Keys.RIGHT:
        walkDirection.sub(Vector2Utils.RIGHT);
        entity.getEvents().trigger(("stopWalkRight"));
        triggerWalkEvent();
        return true;
      default:
        return false;
    }
  }

  /**
   * Triggers the player attack.
   * @return whether the input was processed
   * @see InputProcessor#touchDown(int, int, int, int)
   */
  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    entity.getEvents().trigger("attack");
    return true;
  }

  private void triggerWalkEvent() {
    if (walkDirection.epsilonEquals(Vector2.Zero)) {
      entity.getEvents().trigger("walkStop");
    } else {
      entity.getEvents().trigger("walk", walkDirection);
    }
  }
}
