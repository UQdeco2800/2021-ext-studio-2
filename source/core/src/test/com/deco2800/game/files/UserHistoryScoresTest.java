package com.deco2800.game.files;

import com.deco2800.game.extensions.GameExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Random;

import static com.deco2800.game.files.UserHistoryScores.HistoryScores;
import static com.deco2800.game.files.UserHistoryScores.Score;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(GameExtension.class)
class UserHistoryScoresTest {
    private static final Logger logger = LoggerFactory.getLogger(UserHistoryScoresTest.class);

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }


    /** write UserHistoryScores to JSON file
     *
     */
    @Test
    void shouldReadAndWriteToJSONFile() {
        int _testInt = 200; boolean _testBool = true;

        // create a sample class
        HistoryScores _historyScores = new HistoryScores();
        _historyScores.testInt = _testInt;
        _historyScores.testBool = _testBool;

        // write that class
        UserHistoryScores.set(_historyScores);

        // read the class
        HistoryScores historyScores = UserHistoryScores.get();

        // verify write
        assertEquals(historyScores.testBool, _historyScores.testBool);
        assertEquals(historyScores.testInt, _historyScores.testInt);
    }
}