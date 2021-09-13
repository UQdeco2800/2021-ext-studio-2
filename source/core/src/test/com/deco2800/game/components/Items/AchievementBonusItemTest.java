package com.deco2800.game.components.Items;

import com.deco2800.game.components.achievements.AchievementStatsComponentTest;
import com.deco2800.game.components.achievements.AchievementsBonusItems;
import com.deco2800.game.extensions.GameExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
public class AchievementBonusItemTest extends AchievementStatsComponentTest {

    @Test
    void shouldSpawnBonusItems() {
        AchievementsBonusItems achievementsBonusItems = mock(AchievementsBonusItems.class);
        achievementsBonusItems.spawnBonusItem();
        verify(achievementsBonusItems).spawnBonusItem();
    }
}
