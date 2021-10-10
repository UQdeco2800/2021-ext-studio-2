package com.deco2800.game.components.achievements;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

/**
 * A UI component to display achievement cards and labels for corresponding achievements
 */
public class AchievementsDisplay extends UIComponent {
    private static final int RENDER_DURATION = 1500;
    private static final String[] textures = AchievementFactory.getTextures();
    private static final String[] bonusSoundPath = {"sounds/achievementSound.wav"};
    private static final String[] bonusBgPath = {"images/achievements/bonusBg.png"};
    private Table table;
    private Table tableForBonusBg;
    private Table tableForBonus;
    private Image achievementImg;
    private Image bonusImg;
    private Label achievementLabel;
    private Label bonusLabel;

    @Override
    public void create() {
        super.create();

        AsyncTaskQueue.newQueue();

        loadAssets();
        addActors();

        bonusLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.GOLD));
        bonusLabel.setFontScale(1.4f);
        bonusLabel.setAlignment(100);

        /* Listen to achievement events*/
        entity.getEvents().addListener("updateAchievement", this::updateAchievementsUI);
        entity.getEvents().addListener("clear", this::clear);
        entity.getEvents().addListener("display", this::display);
    }

    /**
     * Load all achievements' assets
     */
    private void loadAssets() {
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(textures);
        resourceService.loadTextures(bonusBgPath);
        resourceService.loadSounds(bonusSoundPath);
        resourceService.loadAll();
    }

    /**
     * Unload all achievements' assets
     */
    private void unloadAssets() {
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.unloadAssets(bonusBgPath);
        resourceService.unloadAssets(textures);
        resourceService.unloadAssets(bonusSoundPath);
    }

    /**
     * Adds a new table as an actor to the stage
     */
    private void addActors() {
        table = new Table();
        table.top();
        table.setFillParent(true);
        tableForBonusBg = new Table();
        tableForBonusBg.bottom().right();
        tableForBonusBg.setFillParent(true);
        tableForBonus = new Table();
        tableForBonus.bottom().right();
        tableForBonus.setFillParent(true);
        stage.addActor(table);
        stage.addActor(tableForBonusBg);
        stage.addActor(tableForBonus);
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
        /* Queue expensive task to run on a separate
         * single thread to run asynchronously. */
        AsyncTaskQueue.enqueueTask(() -> {
            try {
                entity.getEvents().trigger("display", achievement);
                /* Wait for some time */
                Thread.sleep(RENDER_DURATION);
                entity.getEvents().trigger("clear");
            } catch (InterruptedException ignored) {
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private synchronized void display(BaseAchievementConfig achievement){
        /* Render achievement card */
        renderAchievement(achievement);
        /* Render bonus popup */
        renderBonus(achievement);
        /* Play achievement sound */
        playAchievementSound();
        /* Trigger achievement related mid game events */
        triggerEvents(achievement);
    }

    /**
     * Trigger the necessary events which other features have to listen to
     * @param achievement the unlocked achievement being displayed mid game
     */
    private void triggerEvents(BaseAchievementConfig achievement){
        /* Trigger bonus points event */
        AchievementsHelper.getInstance().trackBonusPoints(achievement.bonus);
        /* Trigger an event when an achievement is being displayed mid-game */
        AchievementsHelper.getInstance().trackAchievementDisplayed(achievement);
        /* Add bonus gold when achievement is being displayed mid-game */
        AchievementsHelper.getInstance().trackAddBonusGold(achievement);
    }

    private synchronized void clear(){
        /* Trigger an event when the achievement is being disposed */
        AchievementsHelper.getInstance().trackUnlockedAchievementDisposed();
        /* Remove card from screen */
        table.clear();
        /* Remove bonus UI From screen */
        tableForBonusBg.clear();
        tableForBonus.clear();
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

    private void playAchievementSound() {
        Sound achievementSound = ServiceLocator.getResourceService().
                getAsset("sounds/achievementSound.wav", Sound.class);
        achievementSound.play();

    }

    /**
     * Renders the bonus score associated with achievements
     */
    private void renderBonus(BaseAchievementConfig achievement) {
        bonusImg = new Image(ServiceLocator.getResourceService().getAsset
                ("images/achievements/bonusBg.png", Texture.class));
        String s = "+";
        CharSequence text = s.concat(Integer.toString(achievement.bonus));
        bonusLabel.setText(text);
        tableForBonusBg.add(bonusImg).size(150f, 45f);
        tableForBonus.add(bonusLabel).padBottom(10f).padRight(60f);

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
        if (bonusLabel != null) {
            bonusLabel.remove();
        }
        if (bonusImg != null) {
            bonusImg.remove();
        }
        unloadAssets();
    }
}
