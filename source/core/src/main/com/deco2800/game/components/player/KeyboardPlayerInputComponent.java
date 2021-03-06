package com.deco2800.game.components.player;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.deco2800.game.areas.ForestGameArea;

import com.deco2800.game.components.foodAndwater.RecycleDisplay;
import com.deco2800.game.components.items.TestBuffForItem;
import com.deco2800.game.components.npc.SpaceshipAttackController;
import com.deco2800.game.input.InputComponent;
import com.deco2800.game.screens.MainGameScreen;
import com.deco2800.game.utils.math.Vector2Utils;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.ItemBar.newItembar;
import com.deco2800.game.components.foodAndwater.FoodDisplay;
import com.deco2800.game.components.foodAndwater.WaterDisplay;


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
        if (SpaceshipAttackController.spaceshipState == SpaceshipAttackController.SpaceshipAttack.ON) {
          return false;
        }
        walkDirection.add(Vector2Utils.RIGHT);
        triggerWalkEvent();
        entity.getEvents().trigger(("walkRight"));
        return true;
      case Keys.SPACE:
        entity.getEvents().trigger("attack");
        if(newItembar.getpao()>0){
          entity.getEvents().trigger("useWeapon", entity.getPosition());
          newItembar.usepao();
        }
        return true;
      case Keys.W:
      case Keys.UP:
        entity.getEvents().trigger(("jump"));
        return true;

      case Keys.B:
        entity.getEvents().trigger("B pressed");
        return true;
      case Keys.X:
        entity.getEvents().trigger("itemPickUp");

        return true;
      case Keys.J:
        if(entity.getComponent(CombatStatsComponent.class).getHealth() < 100){
          newItembar.usekit();
          entity.getEvents().trigger("healthUp");
          entity.getComponent(CombatStatsComponent.class).addHealth(10);
          removeBuff_Debuff();
          ForestGameArea.playitemMusic();
        }
        return true;

      case Keys.L:
        if (newItembar.getfood() > 0){
          newItembar.usefood();
          FoodDisplay.addOrRemoveImage(1);
          ForestGameArea.playitemMusic();
        }
        return true;

      case Keys.K:
        if (newItembar.getwater() > 0){
          newItembar.usewater();
          WaterDisplay.addOrRemoveImage(1);
          ForestGameArea.playitemMusic();
        }
        return true;

      case Keys.NUM_4://consume recycle:add chicken/water/health depends on the state of recycle system
        RecycleDisplay.isKey =false;
        if(RecycleDisplay.recycleNumber ==1){
          if(!RecycleDisplay.isKey){
            TestBuffForItem.countNumber=0;
            RecycleDisplay.recycleNumber =0;
            RecycleDisplay.isKey =true;
            if(RecycleDisplay.recycleState == RecycleDisplay.recycleState.hp){//add health
              MainGameScreen.players.getComponent(CombatStatsComponent.class).setHealth(
                      MainGameScreen.players.getComponent(CombatStatsComponent.class).getHealth()+10
              );
            }else if(RecycleDisplay.recycleState == RecycleDisplay.recycleState.food){//add food
              FoodDisplay.addOrRemoveImage(1);
            }else {//add water
              WaterDisplay.addOrRemoveImage(1);
            }
          }
        }
        return true;

      case Keys.NUM_5:////change recycle state to food
        RecycleDisplay.recycleState = RecycleDisplay.recycleState.food;
        return true;

      case Keys.NUM_6://change recycle state to water
        RecycleDisplay.recycleState = RecycleDisplay.recycleState.water;
        return true;

      case Keys.NUM_7://change recycle state to hp
        RecycleDisplay.recycleState = RecycleDisplay.recycleState.hp;
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
        if (SpaceshipAttackController.spaceshipState == SpaceshipAttackController.SpaceshipAttack.ON) {
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

  //removing buff/debuff after 1s

  public void removeBuff_Debuff() {
    Timer timer=new Timer();
    timer.scheduleTask(new Timer.Task() {
      @Override
      public void run() {
        entity.getEvents().trigger("stopBuffDebuff");
        timer.stop();
      }
    },1);
  }
}

