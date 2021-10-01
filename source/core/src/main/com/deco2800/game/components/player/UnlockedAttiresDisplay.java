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


    public UnlockedAttiresDisplay(GdxGame game, List<BaseAchievementConfig> bestAchievements) {
        this.bestAchievements = bestAchievements;
        this.game = game;

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
                                .getAsset("images/menu_background/menu_background.png", Texture.class));
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
        label.setFontScale(2);

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
        Label message = new Label("Unlock Veteran Silver and Veteran Gold Achievements to access new attires!",
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        table.add(message);
        table.row();
        Image silver = new Image(ServiceLocator.getResourceService()
                .getAsset("images/achievements/veteranSilverTrophy.png", Texture.class));
        table.add(silver).center().padLeft(10f).padRight(10f).size(220, 150);
        table.row();
        Image gold = new Image(ServiceLocator.getResourceService()
                .getAsset("images/achievements/veteranGoldTrophy.png", Texture.class));
        table.add(gold).center().padLeft(10f).padRight(10f).size(220, 150);
        table.row();
    }

    /**
     * Utility function to render the given list of achievements
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