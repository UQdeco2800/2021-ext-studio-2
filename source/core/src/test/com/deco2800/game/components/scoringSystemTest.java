package com.deco2800.game.components;

import com.deco2800.game.components.player.PlayerStatsDisplay;
import com.deco2800.game.components.score.ScoringSystem;
import com.deco2800.game.components.score.ScoringSystemV1;
import com.deco2800.game.extensions.GameExtension;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.Assert.*;

//JUnit test for the scoringSystem by team 9
//Because the feature we did can be easily tested by running the project.
//There are only limited tests are necessary.
@ExtendWith(GameExtension.class)
public class scoringSystemTest {
    ScoringSystemV1 clock = new ScoringSystemV1();
    PlayerStatsDisplay player = new PlayerStatsDisplay();
    //@Before
    //public void setup() {
    //    clock.startGameClock();
    // }

    @Test
    public void scoreTest() throws InterruptedException {
        clock.startGameClock();
        Thread.sleep(2300);
        clock.stopTimerTaskForTest();
        clock.stopTimerForTest();
        assertEquals("Seconds are wrong", 2, clock.getSeconds());
    }

}
