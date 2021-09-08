package com.deco2800.game.components.items;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
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

public class FirstAidComponent extends Component {


    private Entity target;
    HitboxComponent hitboxComponent;

    /**
     *creates a first aid component that detects when the player collides with
     * the entity and when collided it runs a buff function for the Item
     * @param target  entity on which the buff function will work on
     */
    public FirstAidComponent(Entity target){
        this.target = target;

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



        TestBuffForItem incHealth = new TestBuffForItem();

       if (PhysicsLayer.contains(PhysicsLayer.PLAYER, other.getFilterData().categoryBits)) // checking if the collision is done with the player
       {
                    incHealth.increaseHealth(target);

                    entity.getEvents().trigger("itemPickedUp");
                    AchievementsHelper.getInstance().trackItemPickedUpEvent();
           Body physBody = entity.getComponent(PhysicsComponent.class).getBody();
           if (physBody.getFixtureList().contains(other, true)) {
               physBody.destroyFixture(other);
           }

                    AchievementsHelper.getInstance().trackItemPickedUpEvent(AchievementsHelper.ITEM_FIRST_AID);


                   entity.getComponent(TextureRenderComponent.class).dispose();
                   ServiceLocator.getEntityService().unregister(entity);



        }
    }
}
