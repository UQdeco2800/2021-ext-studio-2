package com.deco2800.game.files;


import com.deco2800.game.extensions.GameExtension;
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

    MockedStatic<GameRecords> gameRecordsMockedStatic =
            Mockito.mockStatic(GameRecords.class);

    @Test
    void chaptersAreLockedByDefault() {
        List<GameChapters.Chapter> chapters = GameChapters.getChapters();

        chapters.forEach(chapter -> assertFalse(chapter.unlocked));
    }

    @Test
    void noMissingChaptersAndInCorrectOrder() {
        List<GameChapters.Chapter> chapters = GameChapters.getChapters();

        AtomicInteger id = new AtomicInteger();

        chapters.forEach(chapter -> assertEquals(chapter.id, id.incrementAndGet()));
    }

    @Test
    void correctlyReturnsUnlockedChapters() {
        setGoldAchievementsToBeReturned(3);
        int unlockedChapterCount = getUnlockedChaptersCount();
        assertEquals(3, unlockedChapterCount);

        setGoldAchievementsToBeReturned(5);
        unlockedChapterCount = getUnlockedChaptersCount();
        assertEquals(5, unlockedChapterCount);

        setGoldAchievementsToBeReturned(-1);
        unlockedChapterCount = getUnlockedChaptersCount();
        assertEquals(0, unlockedChapterCount);

        setGoldAchievementsToBeReturned(-999999);
        unlockedChapterCount = getUnlockedChaptersCount();
        assertEquals(0, unlockedChapterCount);

        setGoldAchievementsToBeReturned(0);
        unlockedChapterCount = getUnlockedChaptersCount();
        assertEquals(unlockedChapterCount, 0);

        setGoldAchievementsToBeReturned(99999);
        unlockedChapterCount = getUnlockedChaptersCount();
        assertEquals(unlockedChapterCount, GameChapters.getChapters().size());
    }

    @Test
    void chaptersUnlockedInOrder() {
        setGoldAchievementsToBeReturned(4);
        int unlockedChapterCount = getUnlockedChaptersCount();
        assertEquals(4, unlockedChapterCount);

        for (int i = 0; i <= 3; i++) {
            assertTrue(GameChapters.getUnlockedChapters().get(i).unlocked);
            assertEquals(GameChapters.getUnlockedChapters().get(i).id, i + 1);
        }

        setGoldAchievementsToBeReturned(999);
        unlockedChapterCount = getUnlockedChaptersCount();
        assertEquals(GameChapters.getChapters().size(), unlockedChapterCount);

        for (int i = 0; i < GameChapters.getChapters().size(); i++) {
            assertTrue(GameChapters.getUnlockedChapters().get(i).unlocked);
            assertEquals(GameChapters.getUnlockedChapters().get(i).id, i + 1);
        }
    }

    private int getUnlockedChaptersCount() {
        AtomicInteger unlocked = new AtomicInteger();
        GameChapters.getUnlockedChapters().forEach(chapter -> {
            if (chapter.unlocked) unlocked.incrementAndGet();
        });

        return unlocked.get();
    }

    private void setGoldAchievementsToBeReturned(int count) {
        gameRecordsMockedStatic.when(GameRecords::getGoldAchievementsCount)
                .thenReturn(count);
        assertEquals(GameRecords.getGoldAchievementsCount(), count);
    }

    @AfterEach
    void afterEach() {
        // Deregister the mock
        gameRecordsMockedStatic.close();
    }

}
