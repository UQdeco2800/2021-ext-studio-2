package com.deco2800.game.components.buff;

import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(GameExtension.class)
class DeBuffComponentTest {


    @BeforeEach
    void beforeEach() {
        ServiceLocator.registerPhysicsService(new PhysicsService());
    }

    @Test
    void shouldDecreaseHealth() throws InterruptedException {
        Entity player =
                new Entity().addComponent(new CombatStatsComponent(100, 1));
        CombatStatsComponent component = player.getComponent(CombatStatsComponent.class);
        DeBuff deBuff = new DeBuff(player);
        assertEquals(100, component.getHealth());
        deBuff.decreaseHealth();
        assertEquals(90, component.getHealth());

    }

    @Test
    void shouldPoisoning() throws InterruptedException {
        Entity player =
                new Entity().addComponent(new CombatStatsComponent(100, 1));
        CombatStatsComponent component = player.getComponent(CombatStatsComponent.class);
        DeBuff deBuff = new DeBuff(player);
        assertEquals(100, component.getHealth());
        deBuff.poisoning();
        assertEquals(70, component.getHealth());

    }

}
