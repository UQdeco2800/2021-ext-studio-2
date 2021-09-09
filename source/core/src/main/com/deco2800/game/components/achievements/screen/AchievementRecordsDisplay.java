package com.deco2800.game.components.achievements.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.deco2800.game.entities.configs.achievements.BaseAchievementConfig;
import com.deco2800.game.entities.factories.AchievementFactory;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;

import java.util.List;

public class AchievementRecordsDisplay extends UIComponent {
    Table bgTable;
    Table table;
    List<BaseAchievementConfig> bestAchievements;

    public AchievementRecordsDisplay(List<BaseAchievementConfig> bestAchievements) {
        this.bestAchievements = bestAchievements;
    }

    @Override
    public void create() {
        super.create();

        addActors();
    }

    private void addActors() {
        Image img = new Image(ServiceLocator.getResourceService()
                .getAsset("images/achievements/achievementBackground.png", Texture.class));

        bgTable = new Table();
        bgTable.setFillParent(true);
        bgTable.add(img);

        table = new Table();
        table.setFillParent(true);

        Label label = new Label("Your Best Achievements", skin);
        label.setFontScale(2);
        table.add(label);

        table.row();

        if (bestAchievements.isEmpty()) {
            renderNoAchievements();
        } else {
            renderBestAchievements();
        }

        label = new Label("Game Story", skin);
        label.setFontScale(2);
        table.add(label);

        stage.addActor(bgTable);
        stage.addActor(table);
    }

    private void renderNoAchievements() {
        Label message = new Label("Play a game to unlock achievements!", skin);
        table.add(message);
        table.row();
    }

    private void renderBestAchievements() {
        for (BaseAchievementConfig achievement : AchievementFactory.getAchievements()) {
                Image img = new Image(ServiceLocator.getResourceService()
                        .getAsset(achievement.iconPath, Texture.class));
        }
    }

    @Override
    protected void draw(SpriteBatch batch) {

    }

    @Override
    public void dispose() {
        super.dispose();

        table.clear();
        bgTable.clear();
    }
}
