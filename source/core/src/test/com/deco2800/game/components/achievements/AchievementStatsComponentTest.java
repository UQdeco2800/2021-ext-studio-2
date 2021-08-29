package com.deco2800.game.components.achievements;


import com.deco2800.game.entities.configs.achievements.BaseAchievementConfig;
import com.deco2800.game.entities.configs.achievements.ConditionConfig;
import com.deco2800.game.extensions.GameExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@ExtendWith(GameExtension.class)
public class AchievementStatsComponentTest {
    @InjectMocks
    AchievementsStatsComponent component = new AchievementsStatsComponent();



    List<BaseAchievementConfig> getValidAchievementList (){
        List<BaseAchievementConfig> achievements = new ArrayList<>();
        achievements.add(generateAchievement("Veteran",10,-1,-1));
        achievements.add(generateAchievement("Veteran",15,-1,-1));
        achievements.add(generateAchievement("Veteran",20,-1,-1));
        achievements.add(generateAchievement("Game Breaker",5,100,-1));
        achievements.add(generateAchievement("Game Breaker",8,100,-1));
        achievements.add(generateAchievement("Game Breaker",10,100,-1));
        achievements.add(generateAchievement("Tool Master",-1,-1,1));
        achievements.add(generateAchievement("Tool Master",-1,-1,2));
        achievements.add(generateAchievement("Tool Master",-1,-1,3));

        return achievements;
    }

    @Test
    void shouldCheckIsValid() throws
            NoSuchMethodException,
            InvocationTargetException,
            IllegalAccessException {
        BaseAchievementConfig achievement = generateAchievement("ignored",50,-1,0);


        Method method = getMethod();

        assertEquals(method.invoke(component, achievement), false);

        achievement.unlocked = true;
        assertEquals(method.invoke(component, achievement), true);


    }

    private Method getMethod() throws NoSuchMethodException {
        Method method = AchievementsStatsComponent.class
                .getDeclaredMethod("isValid",
                        BaseAchievementConfig.class);
        method.setAccessible(true);
        return method;
    }

    void validateVeteranAchievement() throws
            NoSuchFieldException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Method method = getMethod();
        Field timeField = AchievementsStatsComponent.class.getDeclaredField("time");
        timeField.setAccessible(true);
        timeField.set(component, 999999);
        Field healthField = AchievementsStatsComponent.class.getDeclaredField("health");
        healthField.setAccessible(true);
        healthField.set(component, 20);
        Field itemCountField = AchievementsStatsComponent.class.getDeclaredField("itemCount");
        itemCountField.setAccessible(true);
        itemCountField.set(component, 20);
        for (BaseAchievementConfig a : getValidAchievementList()) {
            if (a.name.equals("Veteran")) {
                assertEquals(method.invoke(component, a), true);
            } else {
                assertEquals(method.invoke(component, a), false);
            }
        }
    }


    BaseAchievementConfig generateAchievement(String name, int time, int health, int itemCount) {
        BaseAchievementConfig achievement = new BaseAchievementConfig();
        achievement.bonus = 0;
        achievement.name = name;
        achievement.type = "ignored";
        achievement.iconPath = "ignored";
        achievement.message = "ignored";
        achievement.unlocked = false;
        ConditionConfig condition = new ConditionConfig();
        condition.itemCount = itemCount;
        condition.time = time;
        condition.health = health;
        achievement.condition = condition;

        return achievement;

    }

}
