package com.deco2800.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.deco2800.game.files.UserSettings;
import com.deco2800.game.screens.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.badlogic.gdx.Gdx.app;

/**
 * Entry point of the non-platform-specific game logic. Controls which screen is currently running.
 * The current screen triggers transitions to other screens. This works similarly to a finite state
 * machine (See the State Pattern).
 */
public class GdxGame extends Game {
    private static final Logger logger = LoggerFactory.getLogger(GdxGame.class);

    private void switchScreen(GdxGame game, ScreenType screenType) {
        game.setScreen(screenType);
    }

    public enum ScreenType {
        MAIN_MENU, MAIN_GAME, SETTINGS, GAME_OVER, PROPS_SHOP, HISTORY_SCORES,
        ACHIEVEMENTS,MONSTER_MENU, UNLOCKED_ATTIRES,BUFF_MENU, INSTRUCTIONS
    }

    @Override
    public void create() {
        logger.info("Creating game");
        loadSettings();

        // Sets background to light yellow
        Gdx.gl.glClearColor(248f / 255f, 249 / 255f, 178 / 255f, 1);

        setScreen(ScreenType.  MAIN_MENU);
    }

    /**
     * Loads the game's settings.
     */
    private void loadSettings() {
        logger.debug("Loading game settings");
        UserSettings.Settings settings = UserSettings.get();
        UserSettings.applySettings(settings);
    }

    /**
     * Sets the game's screen to a new screen of the provided type.
     *
     * @param screenType screen type
     */
    public void setScreen(ScreenType screenType) {
        logger.info("Setting game screen to {}", screenType);
        Screen currentScreen = getScreen();
        if (currentScreen != null) {
            currentScreen.dispose();
        }
        setScreen(newScreen(screenType));
    }

    @Override
    public void dispose() {
        logger.debug("Disposing of current screen");
        getScreen().dispose();
    }

    /**
     * Create a new screen of the provided type.
     *
     * @param screenType screen type
     * @return new screen
     */
    private Screen newScreen(ScreenType screenType) {
        switch (screenType) {
            case MAIN_MENU:
                return new MainMenuScreen(this);
            case MAIN_GAME:
                return new MainGameScreen(this);
            case SETTINGS:
                return new SettingsScreen(this);
            case GAME_OVER:
                return new GameOverScreen(this);
            case PROPS_SHOP:
                return new PropsShopScreen(this);
            case HISTORY_SCORES:
                return new HistoryScoreScreen(this);
            case ACHIEVEMENTS:
                return new AchievementsScreen(this);
            case UNLOCKED_ATTIRES:
                return new UnlockedAttiresScreen(this);
            case MONSTER_MENU:
                return new MonsterMenuScreen(this);
            case BUFF_MENU:
                return new BuffManualMenuScreen(this);
            case INSTRUCTIONS:
                return new InstructionsScreen(this);
            default:
                return null;
        }
    }

    /**
     * Exit the game.
     */
    public void exit() {
        app.exit();
    }
}
