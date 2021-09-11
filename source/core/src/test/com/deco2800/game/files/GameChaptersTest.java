package com.deco2800.game.files;


import com.deco2800.game.extensions.GameExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(GameExtension.class)
public class GameChaptersTest {
    @Test
    void chaptersAreLockedByDefault(){
        List<GameChapters.Chapter> chapters = GameChapters.getChapters();

        chapters.forEach(chapter -> assertFalse(chapter.unlocked));
    }

    @Test
    void noMissingChaptersAndInCorrectOrder(){
        List<GameChapters.Chapter> chapters = GameChapters.getChapters();

        AtomicInteger id = new AtomicInteger();

        chapters.forEach(chapter -> assertEquals(chapter.id, id.incrementAndGet()));
    }
}
