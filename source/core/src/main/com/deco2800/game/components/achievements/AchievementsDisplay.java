package com.deco2800.game.components.achievements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.deco2800.game.concurrency.AsyncTaskQueue;
import com.deco2800.game.entities.configs.achievements.BaseAchievementConfig;
import com.deco2800.game.entities.factories.AchievementFactory;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;

import java.util.concurrent.ExecutorService;

/**
 * A UI component to display achievement cards and labels for corresponding achievements
 */
public class AchievementsDisplay extends UIComponent {
    private static final int RENDER_DURATION = 5000;
    private static final String[] textures = AchievementFactory.getTextures();
    private Table table;
    private Image achievementImg;
    private Label achievementLabel;
    private ExecutorService service;

    @Override
    public void create() {
        super.create();
        loadAssets();
        addActors();

        /* Listen to achievement events*/
        entity.getEvents().addListener("updateAchievement", this::updateAchievementsUI);
    }

    /**
     * Load all achievements' assets
     */
    private void loadAssets() {
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(textures);
        ServiceLocator.getResourceService().loadAll();
    }

    /**
     * Unload all achievements' assets
     */
    private void unloadAssets() {
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.unloadAssets(textures);
    }

    /**
     * Adds a new table as an actor to the stage
     */
    private void addActors() {
        table = new Table();
        table.top();
        table.setFillParent(true);
        stage.addActor(table);
    }

    @Override
    public void draw(SpriteBatch batch) {

    }

    /**
     * Achievement UI updates are guaranteed to execute sequentially,
     * and no more than one update will be active at any given time
     *
     * @param achievement Configuration with properties and conditions for corresponding achievement
     */
    private void updateAchievementsUI(BaseAchievementConfig achievement) {
        /* Queue expensive task to run on a separate single thread
         * to run asynchronously. */
        AsyncTaskQueue.enqueueTask(() -> {
            try {
                /* Render achievement card */
                renderAchievement(achievement);
                /* Wait for some time */
                Thread.sleep(RENDER_DURATION);
                /* Remove card from screen */
                table.clear();
            } catch (InterruptedException ignored) {
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Renders the current achievement notification on the table
     *
     * @param achievement Configuration with properties and conditions for corresponding achievement
     */
    private void renderAchievement(BaseAchievementConfig achievement) {
        CharSequence text = achievement.message;
        achievementLabel = new Label(text, skin, "small");
        achievementImg = new Image(ServiceLocator.getResourceService()
                .getAsset(achievement.iconPath, Texture.class));
        table.add(achievementImg).size(300f, 150f);
        table.row();
        table.add(achievementLabel);

    }

    @Override
    public void dispose() {
        super.dispose();

        /* If the in game screen is out of focus, cancel all future tasks. This has to be
         * done in order to prevent memory leaks, unforeseen exceptions and
         * other nasty bugs.*/
        AsyncTaskQueue.cancelFutureTasksNow();

        if (achievementImg != null) {
            achievementImg.remove();
        }
        if (achievementLabel != null) {
            achievementLabel.remove();
        }
        unloadAssets();
    }
}
