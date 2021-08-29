package com.deco2800.game.components.achievements;


import com.deco2800.game.entities.configs.achievements.BaseAchievementConfig;
import com.deco2800.game.entities.configs.achievements.ConditionConfig;
import com.deco2800.game.extensions.GameExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@ExtendWith(GameExtension.class)
public class AchievementStatsComponentTest {
    @InjectMocks
    AchievementsStatsComponent component = new AchievementsStatsComponent();


    @Test
    void shouldCheckIsValid() throws
            NoSuchMethodException,
            InvocationTargetException,
            IllegalAccessException {

        BaseAchievementConfig achievement = new BaseAchievementConfig();
        achievement.bonus = 0;
        achievement.name = "Veteran";
        achievement.type = "BRONZE";
        achievement.iconPath = "asdf";
        achievement.message = "asdf";
        achievement.unlocked = false;
        ConditionConfig condition = new ConditionConfig();
        condition.itemCount = 0;
        condition.time = 50;
        achievement.condition = condition;

        Method method = AchievementsStatsComponent.class
                        .getDeclaredMethod("isValid",
                                BaseAchievementConfig.class);
        method.setAccessible(true);

        assertEquals(method.invoke(component, achievement), false);

        achievement.unlocked = true;

        assertEquals(method.invoke(component, achievement), true);
    }

}
