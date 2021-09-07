package com.deco2800.game.components.achievements;

import com.badlogic.gdx.audio.Sound;
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

/**
 * A UI component to display achievement cards and labels for corresponding achievements
 */
public class AchievementsDisplay extends UIComponent {
    private static final int RENDER_DURATION = 5000;
    private static final String[] textures = AchievementFactory.getTextures();
    private Table table;
    private Table tableForBonusBg;
    private Table tableForBonus;
    private Image achievementImg;
    private Image bonusImg;
    private Label achievementLabel;
    private Label bonusLabel;
    private Sound achievementSound;
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
        resourceService.loadTextures(new String[]{"images/achievements/bonusBg.png"});
        String[] s ={"sounds/achievementSound.wav"};
        resourceService.loadSounds(s);
        ServiceLocator.getResourceService().loadAll();
    }

    /**
     * Unload all achievements' assets
     */
    private void unloadAssets() {
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.unloadAssets(textures);
        String[] s ={"sounds/achievementSound.wav"};
        resourceService.unloadAssets(s);
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
        tableForBonusBg.padBottom(0).padRight(0);
        tableForBonus = new Table();
        tableForBonus.bottom().right();
        tableForBonus.setFillParent(true);
        tableForBonus.padBottom(0).padRight(0);
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
                /* Render achievement card */
                renderAchievement(achievement);
                /* Render bonus popup */
                renderBonus(achievement);
                /* Play achievement sound */
                playAchievementSound();
                /* Trigger bonus points event */
                AchievementsHelper.getInstance().trackBonusPoints(achievement.bonus);
                /* Wait for some time */
                Thread.sleep(RENDER_DURATION);
                /* Remove card from screen */
                table.clear();
                /* Remove bonus UI From screen */
                tableForBonusBg.clear();
                tableForBonus.clear();
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

    private void playAchievementSound() {
        achievementSound = ServiceLocator.getResourceService().
                getAsset("sounds/achievementSound.wav", Sound.class);
        achievementSound.play();

    }

    /**
     * Renders the bonus score associated with achievements
     */
    private void renderBonus(BaseAchievementConfig achievement) {
        bonusImg=new Image(ServiceLocator.getResourceService().getAsset
                ("images/achievements/bonusBg.png",Texture.class));
        String s ="+";
        CharSequence text = s.concat(Integer.toString(achievement.bonus));
        bonusLabel = new Label(text,skin,"small");
        bonusLabel.setFontScale(1.4f,1.4f);
        bonusLabel.setAlignment(100);
        tableForBonusBg.add(bonusImg).size(150f,45f);
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
        if (bonusLabel!= null) {
            bonusLabel.remove();
        }
        if (bonusImg != null) {
            bonusImg.remove();
        }
        unloadAssets();
    }
}
