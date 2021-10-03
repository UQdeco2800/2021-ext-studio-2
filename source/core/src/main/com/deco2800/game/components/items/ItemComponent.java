package com.deco2800.game.components.items;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Timer;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.Component;
import com.deco2800.game.components.achievements.AchievementsHelper;
import com.deco2800.game.components.player.PlayerActions;
import com.deco2800.game.components.player.PlayerStatsDisplay;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.rendering.TextureRenderComponent;
import com.deco2800.game.services.ServiceLocator;

import java.lang.reflect.Method;
import java.util.function.Consumer;

public class ItemComponent extends Component {


    private Entity target;
    HitboxComponent hitboxComponent;
    private Consumer<Entity> callback;

    /**
     *creates a first aid component that detects when the player collides with
     * the entity and when collided it runs a buff function for the Item
     * @param target  entity on which the buff function will work on
     */
    public ItemComponent(Entity target, Consumer<Entity> callback){
        this.target = target;
        this.callback = callback;
    }

    public void create(){
        entity.getEvents().addListener("collisionStart", this::onCollisionStart);
        hitboxComponent = this.entity.getComponent(HitboxComponent.class);
    }

    /**
     * checks if the collision is done with the player and then runs a testBuff effect for the Item
     * and triggers "ItemPickedUp" event
     */
    private void onCollisionStart(Fixture me, Fixture other){

        if (PhysicsLayer.contains(PhysicsLayer.PLAYER, other.getFilterData().categoryBits)) {
           // checking if the collision is done with the player
           callback.accept(target);
           target.getEvents().trigger("itemPickUp");
           entity.getEvents().trigger("itemPickedUp");
           AchievementsHelper.getInstance().trackItemPickedUpEvent();
           Body physBody = entity.getComponent(PhysicsComponent.class).getBody();
           if (physBody.getFixtureList().contains(other, true)) {
               physBody.destroyFixture(other);
           }

           AchievementsHelper.getInstance().trackItemPickedUpEvent(AchievementsHelper.ITEM_FIRST_AID);
           try {
               entity.getComponent(TextureRenderComponent.class).dispose();
               ServiceLocator.getEntityService().unregister(entity);
               // after 1s stop the item pickUp animation
               Timer timer=new Timer();
               timer.scheduleTask(new Timer.Task() {
                   @Override
                   public void run() {
                       target.getEvents().trigger("stopPickUp");
                       timer.stop();
                   }
               },1);
           }
           catch (Exception e){
               System.out.print(e);
           }
        }
    }
}
