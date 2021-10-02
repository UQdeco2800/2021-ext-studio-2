package com.deco2800.game.components.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.deco2800.game.GdxGame;
import com.badlogic.gdx.graphics.Texture;
import com.deco2800.game.entities.configs.achievements.BaseAchievementConfig;
import com.deco2800.game.files.GameRecords;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deco2800.game.screens.UnlockedAttiresScreen;

import java.util.List;
import java.util.Objects;

public class UnlockedAttiresDisplay extends UIComponent {

    private static final Logger logger = LoggerFactory.getLogger(UnlockedAttiresDisplay.class);
    private final GdxGame game;
    private final List<BaseAchievementConfig> bestAchievements;
    private Table bgTable;
    private Table table;
    private Table crossTable;
    private Table unlockedAttiresTable;
    private int i = 0;
    private static final String[] bgTextures = {"images/menu_background/attires_background.png"};
    private static final String[] achievementTextures = {"images/achievements/veteranSilverTrophy.png", "images/achievements/veteranGoldTrophy.png"};
    private static final String[] attireTextures = {"images/mpc/attires/veteranSilver.png", "images/mpc/attires/veteranGold.png"};

    public UnlockedAttiresDisplay(GdxGame game, List<BaseAchievementConfig> bestAchievements) {
        this.bestAchievements = bestAchievements;
        this.game = game;
        ServiceLocator.registerResourceService(new ResourceService());
        loadAssets();

    }

    private void loadAssets() {
        logger.debug("Loading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(bgTextures);
        resourceService.loadTextures(achievementTextures);
        resourceService.loadTextures(attireTextures);


        ServiceLocator.getResourceService().loadAll();
    }

    private void unloadAssets() {
        logger.debug("Unloading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.unloadAssets(attireTextures);
        resourceService.unloadAssets(achievementTextures);
        resourceService.unloadAssets(bgTextures);
    }
    @Override
    public void create() {
        super.create();
        addActors();
    }

    @Override
    protected void draw(SpriteBatch batch) {

    }

    private void addActors() {
        Image background =
                new Image(
                        ServiceLocator.getResourceService()
                                .getAsset("images/menu_background/attires_background.png", Texture.class));
        ImageButton crossImg = getImageButton("images/achievements/crossButton.png");
        crossImg.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(GdxGame.ScreenType.MAIN_MENU);
            }
        });

        bgTable = new Table();

        bgTable.setFillParent(true);
        bgTable.add(background);
        crossTable = new Table();
        crossTable.setFillParent(true);
        crossTable.top().right();
        crossTable.add(crossImg);

        table = new Table();
        table.setFillParent(true);

        Label label = new Label("Unlocked Attires", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        label.setFontScale(4);

        table.center();
        table.add(label);
        table.row();
        unlockedAttiresTable = new Table();

        if (bestAchievements.isEmpty()) {
            renderZeroUnlockedAttiresTable();
        } else {
            renderUnlockedAttiresTable();
        }

        renderUnlockedAttires(GameRecords.getNextUnlockAchievements(), 0.55f);

        table.add(unlockedAttiresTable);
        table.row();
        stage.addActor(bgTable);
        stage.addActor(table);
        stage.addActor(crossTable);
    }

    /**
     * Renders all unlocked attires
     */

    private void renderUnlockedAttiresTable() {
        renderUnlockedAttires(GameRecords.getAllTimeBestAchievements(), 1);
    }

    /**
     * Renders screens to show zero unlocked attires
     */
    private void renderZeroUnlockedAttiresTable() {
        Label message1 = new Label("You haven't unlocked any new attires yet!",
                new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        message1.setFontScale(3f);
        table.add(message1).padTop(20f).center();
        table.row();
        Label message2 = new Label("Unlock more Gold Achievements to access new attires!",
                new Label.LabelStyle(new BitmapFont(), Color.YELLOW));
        message2.setFontScale(2f);
        table.add(message2).padTop(20f).center();
        table.row();

        Image silverAttire = new Image(ServiceLocator.getResourceService()
                .getAsset("images/mpc/attires/veteranSilver.png", Texture.class));
        table.add(silverAttire).center().padLeft(10f).padRight(10f).padTop(20f).size(220, 150);

        Image goldAttire = new Image(ServiceLocator.getResourceService()
                .getAsset("images/mpc/attires/veteranGold.png", Texture.class));
        table.add(goldAttire).center().padLeft(10f).padRight(10f).padTop(20f).size(220, 150);

    }

    /**
     * Utility function to render the given list of achievements and corresponding unlocked attires
     * @param achievements list of achievements
     * @param alpha the opacity of each image (low for the ones which are locked)
     */
    private void renderUnlockedAttires(List<BaseAchievementConfig> achievements, float alpha) {
        if (achievements.isEmpty()) {
            return;
        }

        for (BaseAchievementConfig achievement : achievements) {
            ++i;
            if(Objects.equals(achievement.name, "VETERAN")) {
                if((Objects.equals(achievement.type, "SILVER") || (Objects.equals(achievement.type, "GOLD")))) {

                    Image achievementImg = new Image(ServiceLocator.getResourceService()
                            .getAsset(achievement.iconPath, Texture.class));
                    achievementImg.setScaling(Scaling.fit);
                    achievementImg.setColor(255, 255, 255, alpha);
                    Image attireImg = new Image(ServiceLocator.getResourceService()
                                    .getAsset(achievement.iconPath, Texture.class));
                    attireImg.setScaling(Scaling.fit);

                    unlockedAttiresTable.add(achievementImg).right().padLeft(10f).padRight(10f).size(220, 150);
                    unlockedAttiresTable.add(attireImg).left().padLeft(10f).padRight(10f).size(220, 150);
                    if (i % 3 == 0) {
                        unlockedAttiresTable.row();
                    }
                }
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        crossTable.clear();
        table.clear();
        bgTable.clear();
    }
    /**
     * Returns an image button to be reused everywhere.
     * @param path the image path
     * @return ImageButton to be displayed
     */
    private ImageButton getImageButton(String path) {
        return new ImageButton(new TextureRegionDrawable(new TextureRegion(
                new Texture(path))));
    }
}