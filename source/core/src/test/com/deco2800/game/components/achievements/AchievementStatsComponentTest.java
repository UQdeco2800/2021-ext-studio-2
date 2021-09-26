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

    private BaseAchievementConfig genAchievement(String name, int time, int health, int itemCount, int score, int firstAids, int gold) {
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
        condition.score = score;
        condition.firstAids = firstAids;
        achievement.condition = condition;
        achievement.condition.gold = gold;

        return achievement;
    }

    private List<BaseAchievementConfig> getList() {
        List<BaseAchievementConfig> achievements = new ArrayList<>();
        achievements.add(genAchievement("Veteran", 10, -1, -1, -1, -1, -1));
        achievements.add(genAchievement("Veteran", 15, -1, -1, -1, -1, -1));
        achievements.add(genAchievement("Veteran", 20, -1, -1, -1, -1, -1));
        achievements.add(genAchievement("Game Breaker", 5, 100, -1, -1, -1, -1));
        achievements.add(genAchievement("Game Breaker", 8, 100, -1, -1, -1, -1));
        achievements.add(genAchievement("Game Breaker", 10, 100, -1, -1, -1, -1));
        achievements.add(genAchievement("Master", -1, -1, -1, 50, -1, -1));
        achievements.add(genAchievement("Master", -1, -1, -1, 100, -1, -1));
        achievements.add(genAchievement("Master", -1, -1, -1, 150, -1, -1));
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

        BaseAchievementConfig achievement = genAchievement("ignored", 50, -1, 0, -1, -1, -1);

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

        component.setTime(0);
        component.setHealth(0);

        component.setItemCountByVal(1);
        BaseAchievementConfig achievement = genAchievement("Tool Master", -1, -1, 1, -1, -1, -1);
        assertEquals(method.invoke(component, achievement), true);
        achievement.unlocked = false;

        component.setItemCountByVal(2);
        achievement = genAchievement("Tool Master", -1, -1, 2, -1, -1, -1);
        assertEquals(method.invoke(component, achievement), true);
        achievement.unlocked = false;

        component.setItemCountByVal(3);
        achievement = genAchievement("Tool Master", -1, -1, 3, -1, -1, -1);
        assertEquals(method.invoke(component, achievement), true);
        achievement.unlocked = false;

        component.setItemCountByVal(-1);
        assertEquals(method.invoke(component, achievement), false);

        component.setItemCountByVal(1);
        assertEquals(method.invoke(component, achievement), false);

        component.setItemCountByVal(2);
        assertEquals(method.invoke(component, achievement), false);

        component.setItemCountByVal(4);
        assertEquals(method.invoke(component, achievement), false);

        component.setItemCountByVal(99);
        assertEquals(method.invoke(component, achievement), false);
    }

    @Test
    void shouldCorrectlyValidateStrangerAchievements() throws
            IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {

        Method method = getIsValidMethod();


        AchievementsStatsComponent component = generateEntity()
                .getComponent(AchievementsStatsComponent.class);

        component.setTime(999999999);
        component.setItemCountByVal(0);
        BaseAchievementConfig achievement = genAchievement("Stranger", 10, -1, 0, -1, -1, -1);
        assertEquals(method.invoke(component, achievement), true);
        achievement.unlocked = false;
        component.setTime(999999999);
        component.setItemCountByVal(0);
        achievement = genAchievement("Stranger", 15, -1, 0, -1, -1, -1);
        assertEquals(method.invoke(component, achievement), true);
        achievement.unlocked = false;
        component.setTime(999999999);
        component.setItemCountByVal(0);
        achievement = genAchievement("Stranger", 20, -1, 0, -1, -1, -1);
        assertEquals(method.invoke(component, achievement), true);
        achievement.unlocked = false;

        component.setItemCountByVal(-1);
        assertEquals(method.invoke(component, achievement), false);

        component.setItemCountByVal(1);
        assertEquals(method.invoke(component, achievement), false);

        component.setItemCountByVal(2);
        assertEquals(method.invoke(component, achievement), false);

        component.setItemCountByVal(4);
        assertEquals(method.invoke(component, achievement), false);

        component.setItemCountByVal(99);
        assertEquals(method.invoke(component, achievement), false);
    }

    @Test
    void shouldCorrectlyValidateHealerAchievements() throws
            IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {

        Method method = getIsValidMethod();


        AchievementsStatsComponent component = generateEntity()
                .getComponent(AchievementsStatsComponent.class);

        component.setTime(0);
        component.setHealth(0);
        component.setFirstAidByVal(1);
        BaseAchievementConfig achievement = genAchievement("Healer", -1, -1, -1, -1, 1, -1);
        assertEquals(method.invoke(component, achievement), true);
        achievement.unlocked = false;

        component.setFirstAidByVal(2);
        achievement = genAchievement("Healer", -1, -1, -1, -1, 2, -1);
        assertEquals(method.invoke(component, achievement), true);
        achievement.unlocked = false;

        component.setFirstAidByVal(3);
        achievement = genAchievement("Healer", -1, -1, -1, -1, 3, -1);
        assertEquals(method.invoke(component, achievement), true);
        achievement.unlocked = false;

        component.setFirstAidByVal(1);
        assertEquals(method.invoke(component, achievement), false);

        component.setFirstAidByVal(1);
        assertEquals(method.invoke(component, achievement), false);

        component.setFirstAidByVal(2);
        assertEquals(method.invoke(component, achievement), false);

        component.setFirstAidByVal(4);
        assertEquals(method.invoke(component, achievement), false);

        component.setFirstAidByVal(99);
        assertEquals(method.invoke(component, achievement), false);
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
    void shouldCorrectlyValidateGameFanaticAchievements() throws
            IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {

        Method method = getIsValidMethod();

        AchievementsStatsComponent component = generateEntity()
                .getComponent(AchievementsStatsComponent.class);

        for (BaseAchievementConfig a : getList()) {
            component.setFirstAidByVal(9999);
            component.setHealth(100);

            if (a.name.equals("Game Fanatic")) {
                assertEquals(method.invoke(component, a), true);
            } else {
                assertEquals(method.invoke(component, a), false);
            }
        }
    }


    @Test
    void shouldCorrectlyValidateCollectorAchievements() throws
            IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {

        Method method = getIsValidMethod();


        AchievementsStatsComponent component = generateEntity()
                .getComponent(AchievementsStatsComponent.class);

        component.setTime(0);
        component.setHealth(0);
        component.setGoldByVal(1);
        BaseAchievementConfig achievement = genAchievement("Healer", -1, -1, -1, -1, -1, 1);
        assertEquals(method.invoke(component, achievement), true);
        achievement.unlocked = false;

        component.setGoldByVal(2);
        achievement = genAchievement("Healer", -1, -1, -1, -1, -1, 2);
        assertEquals(method.invoke(component, achievement), true);
        achievement.unlocked = false;

        component.setGoldByVal(3);
        achievement = genAchievement("Healer", -1, -1, -1, -1, -1, 3);
        assertEquals(method.invoke(component, achievement), true);
        achievement.unlocked = false;

        component.setGoldByVal(1);
        assertEquals(method.invoke(component, achievement), false);

        component.setGoldByVal(1);
        assertEquals(method.invoke(component, achievement), false);

        component.setGoldByVal(2);
        assertEquals(method.invoke(component, achievement), false);

        component.setGoldByVal(4);
        assertEquals(method.invoke(component, achievement), false);

        component.setGoldByVal(99);
        assertEquals(method.invoke(component, achievement), false);
    }

    @Test
    void shouldCorrectlyValidateMasterAchievements() throws
            NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {

        Method method = getIsValidMethod();

        AchievementsStatsComponent component = generateEntity()
                .getComponent(AchievementsStatsComponent.class);
        for (BaseAchievementConfig a : getList()) {
            component.setScore(999999999);

            if (a.name.equals("Master")) {
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
            component.setScore(99999);
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
