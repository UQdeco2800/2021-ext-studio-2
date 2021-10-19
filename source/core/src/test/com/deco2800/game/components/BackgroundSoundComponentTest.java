package com.deco2800.game.components;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.deco2800.game.components.obstacle.ObstacleEventHandler;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import org.junit.jupiter.api.*;

import static com.deco2800.game.entities.factories.NPCFactory.createSpaceShip;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class BackgroundSoundComponentTest {

    private static final String backgroundMusic= "sounds/temp_bgm.wav";
    private static final float volume = 0.3f;
    private static final boolean looping = true;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }

    @BeforeAll
    static void beforeAll() {
        ServiceLocator.registerResourceService(new ResourceService());
    }

    @AfterAll
    static void afterAll() {

    }

    Entity createEntity1() {
        Entity entity =
                new Entity();
        return entity;
    }

    @Test
    void playsound() {
        Entity en = createEntity1();

        Sound sound = mock(Sound.class);

        en.addComponent(new Component());
        en.create();
        en.getEvents().trigger("startBackgroundSound");
    }

    @Test
    void stopSound() {
        Entity en = createEntity1();

        Sound sound = mock(Sound.class);

        en.addComponent(new Component());
        en.create();
        en.getEvents().trigger("stopBackgroundSound");
    }
}