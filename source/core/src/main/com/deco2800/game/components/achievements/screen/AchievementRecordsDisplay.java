package com.deco2800.game.components.achievements.screen;

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
import com.deco2800.game.GdxGame.ScreenType;
import com.deco2800.game.entities.configs.achievements.BaseAchievementConfig;
import com.deco2800.game.files.GameChapters;
import com.deco2800.game.files.GameRecords;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;

import java.util.List;

/**
 * This class is responsible for displaying the achievement records
 * and the associated locked and unlocked chapters.
 */
public class AchievementRecordsDisplay extends UIComponent {
    private static final int CARD_COLUMN_COUNT = 4;
    private final GdxGame game;
    private final List<BaseAchievementConfig> bestAchievements;
    private Table bgTable;
    private Table table;
    private Table crossTable;
    private Table chapterTable;
    private Table achievementsTable;
    private int i = 0;


    /**
     * Only the best achievements are displayed. Takes in the list of best
     * achievements as an argument.
     * @param game Libgdx game object
     * @param bestAchievements The list of best achievements
     */
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
        ImageButton crossImg = getImageButton("images/achievements/crossButton.png");
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

        Label label = new Label("Your Best Achievements", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        label.setFontScale(2);

        table.center();
        table.add(label);
        table.row();
        achievementsTable = new Table();
        if (bestAchievements.isEmpty()) {
            renderNoAchievements();
        } else {
            renderBestAchievements();
        }
        renderAchievements(GameRecords.getNextUnlockAchievements(), 0.55f);
        table.add(achievementsTable);
        table.row();
        label = new Label("Game Story", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        label.setFontScale(2);
        table.add(label);
        renderGameStory();
        table.row();
        table.add(chapterTable);
        stage.addActor(bgTable);
        stage.addActor(table);
        stage.addActor(crossTable);
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

    /**
     * Renders the list of chapters (both locked and unlocked)
     * Clicking on an unlocked chapter results in the opening of a dialog
     * which displays the chapter art, some part of the story. A close
     * button has been added so that the dialog can be dismissed.
     *
     * On clicking a chapter, an event is emitted with the chapter's necessary content.
     */
    private void renderGameStory() {
        chapterTable = new Table();
        List<GameChapters.Chapter> chapters = GameChapters.getUnlockedChapters();
        chapters.forEach(chapter -> {
            Image linkImg = new Image(ServiceLocator.getResourceService()
                    .getAsset("images/story/chapterLink.png", Texture.class));
            ImageButton unlockedChapterImg = getImageButton("images/story/chapter" + chapter.id + ".png");
            Image lockedChapterImg = new Image(ServiceLocator.getResourceService()
                    .getAsset("images/story/chapterLock.png", Texture.class));
            unlockedChapterImg.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    entity.getEvents().trigger("openChapter", chapter);
                }
            });

            if (chapter.unlocked) {
                chapterTable.add(unlockedChapterImg);
            } else {
                chapterTable.add(lockedChapterImg);
            }

            if (chapter.id != chapters.size()) {
                chapterTable.add(linkImg);
            }
        });
    }

    /**
     * UI state when there are no achievements
     */
    private void renderNoAchievements() {
        Label message = new Label("Play a game to unlock achievements!",
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        table.add(message);
        table.row();
    }

    /**
     * Renders the list of best achievements (the ones with full opacity)
     */
    private void renderBestAchievements() {
        renderAchievements(GameRecords.getAllTimeBestAchievements(), 1);
    }


    /**
     * Utility function to render the given list of achievements
     * @param achievements list of achievements
     * @param alpha the opacity of each image (low for the ones which are locked)
     */
    private void renderAchievements(List<BaseAchievementConfig> achievements, float alpha) {
        if (achievements.isEmpty()) {
            return;
        }

        for (BaseAchievementConfig achievement : achievements) {
            ++i;
            Image img = new Image(ServiceLocator.getResourceService()
                    .getAsset(achievement.iconPath, Texture.class));
            img.setScaling(Scaling.fit);
            img.setColor(255, 255, 255, alpha);
            achievementsTable.add(img).center().padLeft(10f).padRight(10f).size(220, 150);
            if (i % CARD_COLUMN_COUNT == 0) {
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
