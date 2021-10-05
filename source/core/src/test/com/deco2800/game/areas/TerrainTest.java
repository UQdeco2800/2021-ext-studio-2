package com.deco2800.game.areas;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.badlogic.gdx.math.GridPoint2;
import com.deco2800.game.areas.terrain.TerrainComponent;
import com.deco2800.game.areas.terrain.TerrainFactory;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.entities.factories.ObstacleFactory;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.services.ServiceLocator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.areas.terrain.TerrainFactory;
import com.deco2800.game.areas.terrain.TerrainFactory.TerrainType;
import com.deco2800.game.components.obstacle.ObstacleEventHandler;
import com.deco2800.game.components.achievements.AchievementsBonusItems;
import com.deco2800.game.components.buff.Buff;
import com.deco2800.game.components.items.InventorySystem;
import com.deco2800.game.components.items.ItemBar;
import com.deco2800.game.components.player.UnlockedAttiresDisplay;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.factories.*;
import com.deco2800.game.files.MPCConfig;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.BackgroundRenderComponent;
import com.deco2800.game.utils.math.GridPoint2Utils;
import com.deco2800.game.utils.math.RandomUtils;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.components.gamearea.GameAreaDisplay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

@ExtendWith(GameExtension.class)





public class TerrainTest extends GameArea {
    @Test
    void shouldSpawnRock(int xValue)  {
        GridPoint2 minPos = new GridPoint2(0, 0);
        GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);

        GameArea gameArea =
                new GameArea() {
                    @Override
                    public void create() {}
                };

        ServiceLocator.registerEntityService(new EntityService());
        GridPoint2 Pos = new GridPoint2 ( xValue + 10, 50);
        Entity rock = ObstacleFactory.createRock();
        spawnEntityAt(rock, Pos, true, false);

        gameArea.spawnEntity(rock);
        verify(rock).create();

        gameArea.dispose();
        verify(rock).dispose();
    }

    @Test
    void shouldSpawnWoods(int xValue) {
        GridPoint2 minPos = new GridPoint2(xValue + 10, 0);
        GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);

        GameArea gameArea =
                new GameArea() {
                    @Override
                    public void create() {}
                };

        ServiceLocator.registerEntityService(new EntityService());
        GridPoint2 PosTwo = new GridPoint2 ( xValue + 10, 50);
        Entity wood = ObstacleFactory.createWood();
        spawnEntityAt(wood, PosTwo, true, false);

        gameArea.spawnEntity(wood);
        verify(wood).create();

        gameArea.dispose();
        verify(wood).dispose();
    }

    @Override
    public void create() {

    }
}
