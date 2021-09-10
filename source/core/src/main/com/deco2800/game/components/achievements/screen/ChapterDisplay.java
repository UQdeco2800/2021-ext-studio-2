package com.deco2800.game.components.achievements.screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.deco2800.game.files.GameChapters;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;

public class ChapterDisplay extends UIComponent {

    @Override
    public void create() {
        super.create();

        entity.getEvents().addListener("openChapter", this::openChapter);
    }

    private void openChapter(GameChapters.Chapter chapter) {
        // Display gui
        ServiceLocator.getResourceService().loadTextures(new String[]{"images/achievements/bg.png"});
        Dialog dialog = new Dialog("Chapter "+ chapter.id, skin);
        dialog.setModal(true);
        dialog.setMovable(true);
        dialog.setResizable(true);
        dialog.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("images/achievements/bg.png"))));
        dialog.padTop(50).padBottom(50);

        Label story = new Label(chapter.content, new Label.LabelStyle(new BitmapFont(), Color.BROWN));
        story.setFontScale(2);
        story.setWrap(true);
        story.setAlignment(Align.topLeft);

        dialog.getContentTable().add(story).width(850).row();
        dialog.getButtonTable().add(new TextButton("Close", skin));

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
