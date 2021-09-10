package com.deco2800.game.components.achievements.screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.deco2800.game.files.GameChapters;
import com.deco2800.game.ui.UIComponent;

public class ChapterDisplay extends UIComponent {

    private Dialog dialog;

    @Override
    public void create() {
        super.create();

        entity.getEvents().addListener("openChapter", this::openChapter);
    }

    private void openChapter(GameChapters.Chapter chapter) {
        // Display gui
        dialog = new Dialog("Chapter "+ chapter.id, skin);
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
        dialog.getButtonTable().add(renderCloseButton());

        dialog.show(stage);
    }

    private TextButton renderCloseButton(){
        TextButton closeButton = new TextButton("CLOSE", skin);

        closeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dialog.remove();
            }
        });

        return closeButton;
    }

    @Override
    protected void draw(SpriteBatch batch) {

    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
