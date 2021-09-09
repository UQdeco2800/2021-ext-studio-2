package com.deco2800.game.components.achievements.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.deco2800.game.GdxGame;
import com.deco2800.game.GdxGame.ScreenType;
import com.deco2800.game.entities.configs.achievements.BaseAchievementConfig;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;

import java.util.List;

public class AchievementRecordsDisplay extends UIComponent {
    private final GdxGame game;
    List<BaseAchievementConfig> bestAchievements;
    private Table bgTable;
    private Table table;
    private Table crossTable;
    private Table chapterTable;
    private Table achievementsTable;

    public AchievementRecordsDisplay(GdxGame game, List<BaseAchievementConfig> bestAchievements) {
        this.bestAchievements = bestAchievements;
        this.game = game;

    }

    @Override
    public void create() {
        super.create();

        addActors();
    }

    private void addActors() {
        Image img = new Image(ServiceLocator.getResourceService()
                .getAsset("images/achievements/achievementBackground.png", Texture.class));
        ImageButton crossImg = new ImageButton(new TextureRegionDrawable(new TextureRegion(
                new Texture("images/achievements/crossButton.png"))));
        crossImg.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(ScreenType.MAIN_MENU);
            }
        });
        bgTable = new Table();
        bgTable.setFillParent(true);
        bgTable.add(img);
        crossTable = new Table();
        crossTable.setFillParent(true);
        crossTable.top().right();
        crossTable.add(crossImg);
        table = new Table();
        table.setFillParent(true);

        Label label = new Label("Your Best Achievements", skin);
        label.setFontScale(2);

        table.top().left();
        table.add(label);
        table.row();

        if (bestAchievements.isEmpty()) {
            renderNoAchievements();
        } else {
            renderBestAchievements();
        }
        table.add(achievementsTable);
        table.row();
        label = new Label("Game Story", skin);
        label.setFontScale(2);
        table.add(label);
        renderGameStory();
        table.row();
        table.add(chapterTable);
        stage.addActor(bgTable);
        stage.addActor(crossTable);
        stage.addActor(table);
    }

    private void renderGameStory() {
        chapterTable = new Table();


        for (int i = 1; i < 6; i++) {
            Image linkImg = new Image(ServiceLocator.getResourceService().getAsset("images/story/chapterLink.png", Texture.class));
            ImageButton chapterImg = new ImageButton(new TextureRegionDrawable(new TextureRegion(
                    new Texture("images/story/chapter" + i + ".png"))));
            chapterImg.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    game.setScreen(ScreenType.MAIN_MENU);
                }
            });
            chapterTable.add(chapterImg);
            if (i != 5) {
                chapterTable.add(linkImg);
            }

        }
    }

    private void renderNoAchievements() {
        Label message = new Label("Play a game to unlock achievements!", skin);
        table.add(message);
        table.row();
    }

    private void renderBestAchievements() {
        achievementsTable = new Table();
        int i = 0;
        for (BaseAchievementConfig achievement : bestAchievements) {
            ++i;
            Image img = new Image(ServiceLocator.getResourceService()
                    .getAsset(achievement.iconPath, Texture.class));
            achievementsTable.add(img).center().pad(10f).size(150, 120);
            if (i % 3 == 0) {
                achievementsTable.row();
            }
        }
    }

    @Override
    protected void draw(SpriteBatch batch) {

    }

    @Override
    public void dispose() {
        super.dispose();
        crossTable.clear();
        table.clear();
        bgTable.clear();
    }
}
