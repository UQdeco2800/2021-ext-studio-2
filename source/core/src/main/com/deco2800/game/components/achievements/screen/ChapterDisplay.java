package com.deco2800.game.components.achievements.screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.deco2800.game.files.GameChapters;
import com.deco2800.game.ui.UIComponent;

public class ChapterDisplay extends UIComponent {

    @Override
    public void create() {
        super.create();

        entity.getEvents().addListener("openChapter", this::openChapter);
    }

    private void openChapter(GameChapters.Chapter chapter) {
        // Display gui
        Dialog dialog = new Dialog("Game Story", skin);

        Label heading = new Label("Chapter " + chapter.id, new Label.LabelStyle(new BitmapFont(), Color.GOLD));
        heading.setFontScale(2);

        Label story = new Label(chapter.content, new Label.LabelStyle(new BitmapFont(), Color.BROWN));
        story.setFontScale(1);
        story.setWrap(true);

        Table table = new Table(skin);
        table.setFillParent(true);
        table.top().left();
        table.add(heading);
        table.row().fillX();
        table.add(story);

        dialog.add(table);

        dialog.show(stage);
    }

    @Override
    protected void draw(SpriteBatch batch) {

    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
