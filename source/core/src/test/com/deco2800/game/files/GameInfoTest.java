package com.deco2800.game.files;


import com.deco2800.game.extensions.GameExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(GameExtension.class)
public class GameInfoTest {

    /**
     * Change the value of a private static final variable
     *
     * NOTE: Sensitive operations will only work <= JDK13
     *
     * @param field    the private static final variable field
     * @param newValue the value to replace
     */
    static void setFinalStatic(Field field, Object newValue) {
        //
        try {
            // Make the private field accessible
            field.setAccessible(true);

            // Turn off the final modifier so that the field can be manipulated
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

            // Set the new value to this now non final public field
            field.set(null, newValue);
        } catch (Exception ignored) {
            // SecurityException and other similar exceptions ignored
        }
    }


    @BeforeEach
    void beforeEach() throws Exception {
        // Changing the JSON file path so that the original game is not affected
        setFinalStatic(GameInfo.class.getDeclaredField("PATH"),
                "test/files/gameInfoTest.json");
        // Changing the JSON file path so that the original game is not affected
        setFinalStatic(GameInfo.class.getDeclaredField("location"), FileLoader.Location.LOCAL);
    }


    /**
     * Check if the game count is being incremented properly
     */
    @Test
    void incrementsGameCountByOne() {
        int count = GameInfo.getGameCount();
        // Increment the game count
        GameInfo.incrementGameCount();
        int newCount = GameInfo.getGameCount();
        // The difference in current and original game count should now be 1
        assertEquals(newCount, count + 1);
    }

    /**
     * Check if the game count is 0 by default
     */
    @Test
    void returnsZeroGameCountsIfNoPrevious() {
        try {
            // The game count should be zero by default, or if the JSON file is missing
            setFinalStatic(GameInfo.class.getDeclaredField("PATH"),
                    "test/files/missingGameCount.json");
            assertEquals(GameInfo.getGameCount(), 0);
        } catch (Exception ignored){
            // Ignore field change exceptions
        }
    }
}
