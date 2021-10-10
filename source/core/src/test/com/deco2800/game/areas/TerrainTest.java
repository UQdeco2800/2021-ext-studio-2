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
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.services.ServiceLocator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import com.badlogic.gdx.graphics.Texture;

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
    private static final String[] forestTextures = {"images/rock.jpg", "images/wood.jpg"};

    @Test
    void shouldSpawnRock()  {

        GameArea gameArea =
                new GameArea() {
                    @Override
                    public void create() {
                    }
                };


        ServiceLocator.registerPhysicsService(new PhysicsService());
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerRenderService(new RenderService());
        ResourceService resourceService = new ResourceService();
        resourceService.loadTextures(forestTextures);
        resourceService.loadAll();
        ServiceLocator.registerResourceService(resourceService);
        Entity rockMock = mock(ObstacleFactory.createRock().getClass());

        gameArea.spawnEntity(rockMock);
        verify(rockMock).create();

        gameArea.dispose();
        verify(rockMock).dispose();
    }

    @Test
    void shouldSpawnWoods() {

        GameArea gameArea =
                new GameArea() {
                    @Override
                    public void create() {}
                };

        ServiceLocator.registerPhysicsService(new PhysicsService());
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerRenderService(new RenderService());
        ResourceService resourceService = new ResourceService();
        resourceService.loadTextures(forestTextures);
        resourceService.loadAll();
        ServiceLocator.registerResourceService(resourceService);
        Entity woodMock = mock(ObstacleFactory.createWood().getClass());

        gameArea.spawnEntity(woodMock);
        verify(woodMock).create();

        gameArea.dispose();
        verify(woodMock).dispose();
    }

    @Override
    public void create() {

    }
}
