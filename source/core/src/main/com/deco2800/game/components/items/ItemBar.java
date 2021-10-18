package com.deco2800.game.components.items;
import com.badlogic.gdx.Gdx;
import com.deco2800.game.entities.factories.ItemFactory;
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
import java.util.ArrayList;
import java.util.List;
import com.deco2800.game.components.player.KeyboardPlayerInputComponent;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.input.InputComponent;
import com.deco2800.game.utils.math.Vector2Utils;
import com.deco2800.game.components.items.InventorySystem;

public class ItemBar extends Component {
    private Entity target;
    private final InventorySystem ss;
    TestBuffForItem sumer =  new TestBuffForItem();
    private int health;
    HitboxComponent hitboxComponent;
    int index = 0;
    List<Entity> inv = new ArrayList<Entity>();
    public ItemBar(Entity target, InventorySystem ss){
        this.target = target;
        this.ss=ss;

    }

    public void create(){
        entity.getEvents().addListener("collisionStart", this::onCollisionStart);
        hitboxComponent = this.entity.getComponent(HitboxComponent.class);

    }
    private void onButtonPressed()
    {
        ss.pressbutton();
    }


    private void onCollisionStart(Fixture me, Fixture other) {
        if (PhysicsLayer.contains(PhysicsLayer.PLAYER, other.getFilterData().categoryBits)) {
                ss.updateinventory();
        }

    }
}

