package com.deco2800.game.files;


import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.files.stats.GameChapters;
import com.deco2800.game.files.stats.GameRecordUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(GameExtension.class)
public class GameChaptersTest {

    MockedStatic<GameRecordUtils> gameInfoMockedStatic =
            Mockito.mockStatic(GameRecordUtils.class);

    /**
     * Check if all the chapters are locked by default
     */
    @Test
    void chaptersAreLockedByDefault() {
        List<GameChapters.Chapter> chapters = GameChapters.getChapters();

        // Loop through all the chapters and check if they are not unlocked
        chapters.forEach(chapter -> assertFalse(chapter.unlocked));
    }

    /**
     * All chapters should be in the correct order when they are fetched.
     * This is to ensure that they are unlocked in the correct order as well.
     */
    @Test
    void noMissingChaptersAndInCorrectOrder() {
        List<GameChapters.Chapter> chapters = GameChapters.getChapters();

        AtomicInteger id = new AtomicInteger();

        // Ensure each chapter is followed by its sequel
        chapters.forEach(chapter -> assertEquals(chapter.id, id.incrementAndGet()));
    }

    /**
     * Check if the correct number of chapters are unlocked, given
     * the unlocked gold achievements.
     */
    @Test
    void correctlyReturnsUnlockedChapters() {
        // 3 chapters should be unlocked on 3 gold achievements
        setGoldAchievementsToBeReturned(3);
        int unlockedChapterCount = getUnlockedChaptersCount();
        assertEquals(3, unlockedChapterCount);

        // 5 chapters should be unlocked on 5 gold achievements
        setGoldAchievementsToBeReturned(5);
        unlockedChapterCount = getUnlockedChaptersCount();
        assertEquals(5, unlockedChapterCount);

        // 5 chapters should be unlocked on 5 gold achievements
        setGoldAchievementsToBeReturned(-1);
        unlockedChapterCount = getUnlockedChaptersCount();
        assertEquals(0, unlockedChapterCount);

        // No chapters to be unlocked on some invalid negative value
        setGoldAchievementsToBeReturned(-999999);
        unlockedChapterCount = getUnlockedChaptersCount();
        assertEquals(0, unlockedChapterCount);

        // If no gold achievements have been unlocked, no chapters should be unlocked.
        setGoldAchievementsToBeReturned(0);
        unlockedChapterCount = getUnlockedChaptersCount();
        assertEquals(unlockedChapterCount, 0);

        // Chapter unlocked count should not necessarily be equal to
        // the number of gold achievements unlocked
        setGoldAchievementsToBeReturned(99999);
        unlockedChapterCount = getUnlockedChaptersCount();
        assertEquals(unlockedChapterCount, GameChapters.getChapters().size());
    }

    /**
     * Checks if the chapters are unlocked in the correct order.
     */
    @Test
    void chaptersUnlockedInOrder() {
        // Unlock 4 chapters
        setGoldAchievementsToBeReturned(4);
        int unlockedChapterCount = getUnlockedChaptersCount();
        assertEquals(4, unlockedChapterCount);

        // Check if the 4 chapters are unlocked in the correct order
        for (int i = 0; i <= 3; i++) {
            assertTrue(GameChapters.getUnlockedChapters().get(i).unlocked);
            assertEquals(GameChapters.getUnlockedChapters().get(i).id, i + 1);
        }

        // Now, unlock all chapters
        setGoldAchievementsToBeReturned(999);
        unlockedChapterCount = getUnlockedChaptersCount();
        assertEquals(GameChapters.getChapters().size(), unlockedChapterCount);

        // Check if all chapters are unlocked in the correct order
        for (int i = 0; i < GameChapters.getChapters().size(); i++) {
            assertTrue(GameChapters.getUnlockedChapters().get(i).unlocked);
            assertEquals(GameChapters.getUnlockedChapters().get(i).id, i + 1);
        }
    }

    /**
     * Calculate and return the number of unlocked chapters
     *
     * @return number of unlocked chapters
     */
    private int getUnlockedChaptersCount() {
        AtomicInteger unlocked = new AtomicInteger();
        GameChapters.getUnlockedChapters().forEach(chapter -> {
            if (chapter.unlocked) unlocked.incrementAndGet();
        });

        return unlocked.get();
    }

    /**
     * Mock the number of gold achievements that the user has unlocked
     *
     * @param count number of gold achievements unlocked
     */
    private void setGoldAchievementsToBeReturned(int count) {
        gameInfoMockedStatic.when(GameRecordUtils::getGoldAchievementsCount)
                .thenReturn(count);
        assertEquals(GameRecordUtils.getGoldAchievementsCount(), count);
    }

    /**
     * Deregister the mock after use
     */
    @AfterEach
    void afterEach() {
        gameInfoMockedStatic.close();
    }

}
