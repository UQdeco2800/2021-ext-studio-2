package com.deco2800.game.components.achievements;


import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.configs.achievements.BaseAchievementConfig;
import com.deco2800.game.entities.configs.achievements.ConditionConfig;
import com.deco2800.game.extensions.GameExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(GameExtension.class)
public class AchievementStatsComponentTest {

    private BaseAchievementConfig genAchievement(String name, int time, int health, int itemCount) {
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

    private List<BaseAchievementConfig> getList() {
        List<BaseAchievementConfig> achievements = new ArrayList<>();
        achievements.add(genAchievement("Veteran", 10, -1, -1));
        achievements.add(genAchievement("Veteran", 15, -1, -1));
        achievements.add(genAchievement("Veteran", 20, -1, -1));
        achievements.add(genAchievement("Game Breaker", 5, 100, -1));
        achievements.add(genAchievement("Game Breaker", 8, 100, -1));
        achievements.add(genAchievement("Game Breaker", 10, 100, -1));
        achievements.add(genAchievement("Tool Master", -1, -1, 1));
        achievements.add(genAchievement("Tool Master", -1, -1, 2));
        achievements.add(genAchievement("Tool Master", -1, -1, 3));

        return achievements;
    }

    private Entity generateEntity() {
        Entity achievementsEntity =
                new Entity().addComponent(new AchievementsStatsComponent());
        achievementsEntity.create();
        return achievementsEntity;
    }

    @Test
    void shouldCheckIsValid() throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {

        BaseAchievementConfig achievement = genAchievement("ignored", 50, -1, 0);

        AchievementsStatsComponent component = new AchievementsStatsComponent();

        Method method = getIsValidMethod();

        assertEquals(method.invoke(component, achievement), false);

        achievement.unlocked = true;
        assertEquals(method.invoke(component, achievement), true);

    }

    private Method getIsValidMethod() throws NoSuchMethodException {
        Method method = AchievementsStatsComponent.class
                .getDeclaredMethod("isValid",
                        BaseAchievementConfig.class);
        method.setAccessible(true);
        return method;
    }

    @Test
    void shouldCorrectlyValidateVeteranAchievements() throws
            IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {

        Method method = getIsValidMethod();

        AchievementsStatsComponent component = generateEntity()
                .getComponent(AchievementsStatsComponent.class);

        for (BaseAchievementConfig a : getList()) {
            component.setTime(999999999);
            component.setHealth(-1);

            if (a.name.equals("Veteran")) {
                assertEquals(method.invoke(component, a), true);
            } else {
                assertEquals(method.invoke(component, a), false);
            }
        }
    }

    @Test
    void shouldCorrectlyValidateToolMasterAchievements() throws
            IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {

        Method method = getIsValidMethod();

        AchievementsStatsComponent component = generateEntity()
                .getComponent(AchievementsStatsComponent.class);

        for (BaseAchievementConfig a : getList()) {

            component.setTime(9);
            component.setHealth(70);

            for (int i = 0; i <= 99; i++) {
                component.setItemCount();
            }

            if (a.name.equals("Tool Master")) {
                assertEquals(method.invoke(component, a), true);
            } else {
                assertEquals(method.invoke(component, a), false);
            }
        }
    }

    @Test
    void shouldCorrectlyValidateGameBreakerAchievements() throws
            IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {

        Method method = getIsValidMethod();

        AchievementsStatsComponent component = generateEntity()
                .getComponent(AchievementsStatsComponent.class);

        for (BaseAchievementConfig a : getList()) {
            component.setTime(999999999);
            component.setHealth(100);

            if (a.name.equals("Game Breaker") || a.name.equals("Veteran")) {
                assertEquals(method.invoke(component, a), true);
            } else {
                assertEquals(method.invoke(component, a), false);
            }
        }
    }

    @Test
    void allAchievementsShouldBeValid() throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {

        Method method = getIsValidMethod();

        AchievementsStatsComponent component = generateEntity()
                .getComponent(AchievementsStatsComponent.class);

        for (BaseAchievementConfig a : getList()) {
            component.setTime(999999999);
            component.setHealth(100);
            for (int i = 0; i < 999; i++) {
                component.setItemCount();
            }

            assertEquals(method.invoke(component, a), true);
        }
    }

    @Test
    void noAchievementShouldBeValid() throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {

        Method method = getIsValidMethod();

        AchievementsStatsComponent component =
                generateEntity().getComponent(AchievementsStatsComponent.class);

        for (BaseAchievementConfig a : getList()) {
            component.setTime(2);
            component.setHealth(0);

            assertEquals(method.invoke(component, a), false);
        }
    }


}
