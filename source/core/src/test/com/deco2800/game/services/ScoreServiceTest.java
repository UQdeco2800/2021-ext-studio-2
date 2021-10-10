package com.deco2800.game.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.extensions.GameExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(GameExtension.class)
class ScoreServiceTest {
    private static Logger logger = LoggerFactory.getLogger(ScoreServiceTest.class);
    ScoreService scoreService;
    DistanceService distanceService;
    Entity player;

    @BeforeAll
    static void beforeAll() {
        Gdx.graphics = mock(Graphics.class);
        when(Gdx.graphics.getDeltaTime()).thenReturn(10f);
    }

    @BeforeEach
    void setUp() {
        scoreService = new ScoreService();
        distanceService = new DistanceService(player);
    }

   // @Test
    //void shouldAddToScoreAndGetCorrectScore() {
    //    scoreService.addToScore(10);
    //      assertEquals(10, scoreService.getScore());
    //}

    @Test
    void saveCurrentScoreToHistory() {
    }

    @AfterEach
    void tearDown() {
    }
}