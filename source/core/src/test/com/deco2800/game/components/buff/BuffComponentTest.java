package com.deco2800.game.components.buff;

import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.player.InventoryComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.deco2800.game.entities.factories.PlayerFactory.createPlayer;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(GameExtension.class)
class BuffComponentTest {
    Entity player =
            new Entity().addComponent(new CombatStatsComponent(90, 1));
    CombatStatsComponent component = player.getComponent(CombatStatsComponent.class);

    @BeforeEach
    void beforeEach() {
        ServiceLocator.registerPhysicsService(new PhysicsService());
    }

    @Test
    void shouldAddHealth() {
        Buff buff = new Buff(player);
        assertEquals(90, component.getHealth());
        buff.addHealth();
        assertEquals(100, component.getHealth());

    }

    @Test
    void shouldAddMaxHealth() {
        Buff buff = new Buff(player);
        assertEquals(90, component.getHealthMax());
        buff.increaseHealthLimit();
        assertEquals(110, component.getHealthMax());

    }

}
