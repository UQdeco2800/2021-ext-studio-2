package com.deco2800.game.components.achievements.screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
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
        dialog = new Dialog("", skin);
        dialog.setModal(true);
        dialog.setMovable(false);
        dialog.setResizable(true);

        Image background = new Image(new Texture("images/story/chapterDialog.png"));
        background.setScaling(Scaling.fit);
        dialog.setBackground(background.getDrawable());

        dialog.pad(50).padTop(100);

        Label story = new Label(chapter.content, new Label.LabelStyle(new BitmapFont(), Color.DARK_GRAY));
        story.setFontScale(1.1f);
        story.setWrap(true);
        story.setAlignment(Align.topLeft);

        Label heading = new Label("Chapter " + chapter.id, new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        heading.setFontScale(2f);
        dialog.getContentTable().add(heading).expandX().row();
        dialog.getContentTable().add(story).width(600).row();
        dialog.getButtonTable().add(renderCloseButton()).size(50, 50).row();

        dialog.show(stage);
    }

    private ImageButton renderCloseButton(){
        Image crossButtonImg = new Image(new Texture("images/achievements/crossButton.png"));

        ImageButton closeButton = new ImageButton(crossButtonImg.getDrawable());

        closeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dialog.hide();
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