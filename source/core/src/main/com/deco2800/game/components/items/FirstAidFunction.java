package com.deco2800.game.components.items;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.Component;
import com.deco2800.game.components.player.PlayerActions;
import com.deco2800.game.components.player.PlayerStatsDisplay;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.rendering.AnimationRenderComponent;

import java.lang.reflect.Method;

public class FirstAidFunction extends Component {


    private int health;
    private Entity target;
    HitboxComponent hitboxComponent;

    public FirstAidFunction(Entity target){
        this.target = target;

    }

    public void create(){
        entity.getEvents().addListener("collisionStart", this::onCollisionStart);
        hitboxComponent = this.entity.getComponent(HitboxComponent.class);
    }

    private void onCollisionStart(Fixture me, Fixture other){
        TestBuffForItem incHealth = new TestBuffForItem();
       if (PhysicsLayer.contains(PhysicsLayer.PLAYER, other.getFilterData().categoryBits)) {
                    incHealth.increaseHealth(target);
                    entity.getEvents().trigger("itemPickedUp");


           new Thread(() -> {
                entity.dispose();
            }).start();

        }
    }

}
