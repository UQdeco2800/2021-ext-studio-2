package com.deco2800.game.components.Items;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.ItemBar.newItembar;
import com.deco2800.game.components.items.PropShopHelper;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.services.ServiceLocator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(GameExtension.class)
public class PropShopHelperTest {
    @BeforeEach
    void beforeEach() {
        ServiceLocator.registerPhysicsService(new PhysicsService());
    }
    Entity createTarget() {
        Entity target =
                new Entity()
                        .addComponent(new CombatStatsComponent(50, 0))
                        .addComponent(new PhysicsComponent())
                        .addComponent(new HitboxComponent().setLayer(PhysicsLayer.PLAYER));
        target.create();
        return target;
    }

    @Test
    void propIncreaseHealth(){
        Entity player = createTarget();
        assertEquals(50, player.getComponent(CombatStatsComponent.class).getHealth());
        PropShopHelper.usePropHealth(player , 170);
        assertEquals(170, player.getComponent(CombatStatsComponent.class).getHealth());

    }
    @Test
    void propIncreaseFood(){
        newItembar bar = new newItembar();
        PropShopHelper.incFood();
        assertEquals(4,bar.foods.size());
    }

    @Test
    void propIncreaseWater(){
        newItembar bar = new newItembar();
        PropShopHelper.incWater();
        assertEquals(4,bar.waters.size());
    }

}
