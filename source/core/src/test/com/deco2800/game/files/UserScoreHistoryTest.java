package com.deco2800.game.files;

import com.deco2800.game.extensions.GameExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.deco2800.game.files.UserScoreHistory.ScoreHistory;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(GameExtension.class)
class UserScoreHistoryTest {
    private static final Logger logger = LoggerFactory.getLogger(UserScoreHistoryTest.class);

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }


    /** write UserScoreHistory to JSON file
     *
     */
    @Test
    void shouldReadAndWriteToJSONFile() {
        int _testInt = 200; boolean _testBool = true;

        // create a sample class
        ScoreHistory _Score_history = new ScoreHistory();
        _Score_history.testInt = _testInt;
        _Score_history.testBool = _testBool;

        // write that class
        UserScoreHistory.set(_Score_history);

        // read the class
        ScoreHistory scoreHistory = UserScoreHistory.get();

        // verify write
        assertEquals(scoreHistory.testBool, _Score_history.testBool);
        assertEquals(scoreHistory.testInt, _Score_history.testInt);
    }
}