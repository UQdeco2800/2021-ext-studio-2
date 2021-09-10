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
        Dialog dialog = new Dialog("Game Story", skin);

        Label heading = new Label("Chapter " + chapter.id, new Label.LabelStyle(new BitmapFont(), Color.GOLD));
        heading.setFontScale(2);

        Label story = new Label(chapter.content, new Label.LabelStyle(new BitmapFont(), Color.BROWN));
        story.setFontScale(0.7f);
        story.setWrap(true);
        Table table = new Table();
        table.setFillParent(true);
        table.center().top();

        table.add(heading).center();

        table.row();
        table.add(story).center();
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
