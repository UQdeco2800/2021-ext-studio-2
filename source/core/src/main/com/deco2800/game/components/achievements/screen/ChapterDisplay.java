package com.deco2800.game.components.achievements.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deco2800.game.files.GameChapters;
import com.deco2800.game.ui.UIComponent;

public class ChapterDisplay extends UIComponent {

    @Override
    public void create() {
        super.create();

        entity.getEvents().addListener("openChapter", this::openChapter);
    }

    private void openChapter(GameChapters.Chapter chapter){
        // Display gui
    }

    @Override
    protected void draw(SpriteBatch batch) {

    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
