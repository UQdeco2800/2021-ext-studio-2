package com.deco2800.game.files;


import com.deco2800.game.extensions.GameExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(GameExtension.class)
public class GameInfoTest {

    /**
     * Change the value of a private static final variable
     * @param field the private static final variable field
     * @param newValue the value to replace
     * @throws Exception arbitrary exceptions to be ignored
     */
    static void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, newValue);
    }


    @BeforeEach
    void beforeEach() throws Exception {
        // Changing the JSON file path so that the original game is not affected
        setFinalStatic(GameInfo.class.getDeclaredField("path"),
                "test/files/gameInfoTest.json");
        setFinalStatic(GameInfo.class.getDeclaredField("location"), FileLoader.Location.LOCAL);
    }


    @Test
    void incrementsGameCountByOne() {
        int count = GameInfo.getGameCount();
        GameInfo.incrementGameCount();
        int newCount = GameInfo.getGameCount();
        assertEquals(newCount, count + 1);
    }

    @Test
    void returnsZeroGameCountsIfNoPrevious() throws Exception {
        setFinalStatic(GameInfo.class.getDeclaredField("path"),
                "test/files/missing.json");
        int count = GameInfo.getGameCount();
        assertEquals(count, 0);
    }
}
