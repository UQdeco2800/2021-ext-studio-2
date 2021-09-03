package com.deco2800.game.components.Obstacle;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Null;
import com.deco2800.game.components.Component;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.BodyUserData;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.AnimationRenderComponent;
//import com.sun.tools.javac.Main;


public class ObstacleDispare extends Component {


//    AnimationRenderComponent animator;
    HitboxComponent hitboxComponent;

    public void create(){
        System.out.println("aaaaaa");
        entity.getEvents().addListener("collisionStart", this::onCollisionStart);
        hitboxComponent = this.entity.getComponent(HitboxComponent.class);
        this.entity.getComponent(HitboxComponent.class).setSensor(false);
//        animator = this.entity.getComponent(AnimationRenderComponent.class);


    }
    private void onCollisionStart(Fixture me, Fixture other){
        if (other.isSensor()){
//            animator.startAnimation("enemy2");
            entity.setDispose();
        }
    }

}
