package com.deco2800.game.components.obstacle;

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
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonsterDetails extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(MonsterDispay.class);
    private Dialog dialog;
    @Override
    public void create() {
        super.create();

        entity.getEvents().addListener("openDetailPage", this::openDetailPage1);
        entity.getEvents().addListener("openDetailPage2", this::openDetailPage2);
        entity.getEvents().addListener("openDetailPage3", this::openDetailPage3);
        entity.getEvents().addListener("openDetailPage4", this::openDetailPage4);
        entity.getEvents().addListener("openDetailPage5", this::openDetailPage5);
        entity.getEvents().addListener("openDetailPage6", this::openDetailPage6);
        entity.getEvents().addListener("openDetailPage7", this::openDetailPage7);
    }

    private void openDetailPage1() {
        logger.info("open details page");
        dialog = new Dialog("Alien Plant Detail", skin);
        dialog.setModal(true);
        dialog.setMovable(false);
        dialog.setResizable(true);
        Image plantImage = new Image(new Texture("images/monster_menu/plant_img.png"));
        Label heading = new Label("Aline Plant Detail " , new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        Label story = new Label("In deep space, the crew of the commercial starship Nostromo is awakened from their cryo-sleep capsules halfway through their journey home to investigate a distress call from an alien vessel. The terror begins when the crew encounters a nest of eggs inside the alien ship. An organism from inside an egg leaps out and attaches itself to one of the crew, causing him to fall into a coma.", new Label.LabelStyle(new BitmapFont(), Color.DARK_GRAY));
        heading.setFontScale(2f);
        story.setFontScale(1.3f);
        story.setWrap(true);
        story.setAlignment(Align.topLeft);
        dialog.getContentTable().add(heading).expandX().row();
        dialog.getContentTable().add(plantImage).height(122).width(240).row();
        dialog.getContentTable().add(story).width(600).row();
        dialog.getButtonTable().add(renderCloseButton()).size(50, 50).row();
        dialog.show(stage);
    }

    private void openDetailPage2() {
        logger.info("open details page2");
        dialog = new Dialog("Aline thorn Detail", skin);
        dialog.setModal(true);
        dialog.setMovable(false);
        dialog.setResizable(true);
        Image thornImage = new Image(new Texture("images/monster_menu/plant_img.png"));
        Label heading = new Label("Aline thorn Detail " , new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        Label story = new Label("In deep space, the crew of the commercial starship Nostromo is awakened from their cryo-sleep capsules halfway through their journey home to investigate a distress call from an alien vessel. The terror begins when the crew encounters a nest of eggs inside the alien ship. An organism from inside an egg leaps out and attaches itself to one of the crew, causing him to fall into a coma.", new Label.LabelStyle(new BitmapFont(), Color.DARK_GRAY));
        heading.setFontScale(2f);
        story.setFontScale(1.3f);
        story.setWrap(true);
        story.setAlignment(Align.topLeft);
        dialog.getContentTable().add(heading).expandX().row();
        dialog.getContentTable().add(thornImage).height(122).width(240).row();
        dialog.getContentTable().add(story).width(600).row();
        dialog.getButtonTable().add(renderCloseButton()).size(50, 50).row();
        dialog.show(stage);
    }


    private void openDetailPage3() {
        logger.info("open details page3");
        dialog = new Dialog("meteorite Detail", skin);
        dialog.setModal(true);
        dialog.setMovable(false);
        dialog.setResizable(true);
        Image meteoriteImage = new Image(new Texture("images/story/chapter2art.png"));
        Label heading = new Label("meteorite Detail " , new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        Label story = new Label("In deep space, the crew of the commercial starship Nostromo is awakened from their cryo-sleep capsules halfway through their journey home to investigate a distress call from an alien vessel. The terror begins when the crew encounters a nest of eggs inside the alien ship.", new Label.LabelStyle(new BitmapFont(), Color.DARK_GRAY));
        heading.setFontScale(2f);
        story.setFontScale(1.3f);
        story.setWrap(true);
        story.setAlignment(Align.topLeft);
        dialog.getButtonTable().add(renderCloseButton()).size(50, 50).top();
        dialog.getContentTable().add(heading).expandX().row();
        dialog.getContentTable().add(story).width(600).row();
      //  dialog.getContentTable().add(meteoriteImage).height(122).width(240).row();
        dialog.show(stage);
    }

    private void openDetailPage4() {
        logger.info("open details page4");
        dialog = new Dialog("Aline Monkey Detail", skin);
        dialog.setModal(true);
        dialog.setMovable(false);
        dialog.setResizable(true);
        Image MonkeyImage = new Image(new Texture("images/story/chapter2art.png"));
        Label heading = new Label("Aline Monkey Detail " , new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        Label story = new Label("In deep space, the crew of the commercial starship Nostromo is awakened from their cryo-sleep capsules halfway through their journey home to investigate a distress call from an alien vessel. The terror begins when the crew encounters a nest of eggs inside the alien ship.", new Label.LabelStyle(new BitmapFont(), Color.DARK_GRAY));
        heading.setFontScale(2f);
        story.setFontScale(1.3f);
        story.setWrap(true);
        story.setAlignment(Align.topLeft);
        dialog.getContentTable().add(heading).expandX().row();
        //dialog.getContentTable().add(MonkeyImage).height(122).width(240).row();
        dialog.getContentTable().add(story).width(600).row();
        dialog.getButtonTable().add(renderCloseButton()).size(50, 50).row();
        dialog.show(stage);

    }

    private void openDetailPage5() {
        logger.info("open details page5");
        dialog = new Dialog("Face Worm Detail", skin);
        dialog.setModal(true);
        dialog.setMovable(false);
        dialog.setResizable(true);
        Image wormImage = new Image(new Texture("images/story/chapter2art.png"));
        Label heading = new Label("Face Worm Detail" , new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        Label story = new Label("In deep space, the crew of the commercial starship Nostromo is awakened from their cryo-sleep capsules halfway through their journey home to investigate a distress call from an alien vessel. The terror begins when the crew encounters a nest of eggs inside the alien ship.", new Label.LabelStyle(new BitmapFont(), Color.DARK_GRAY));
        heading.setFontScale(2f);
        story.setFontScale(1.3f);
        story.setWrap(true);
        story.setAlignment(Align.topLeft);
        dialog.getContentTable().add(heading).expandX().row();
        //dialog.getContentTable().add(wormImage).height(122).width(240).row();
        dialog.getContentTable().add(story).width(600).row();
        dialog.getButtonTable().add(renderCloseButton()).size(50, 50).row();
        dialog.show(stage);
    }

    private void openDetailPage6() {
        logger.info("open details page6");
        dialog = new Dialog("Spaceship Detail", skin);
        dialog.setModal(true);
        dialog.setMovable(false);
        dialog.setResizable(true);
        Image wormImage = new Image(new Texture("images/story/chapter2art.png"));
        Label heading = new Label("Spaceship Detail" , new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        Label story = new Label("In deep space, the crew of the commercial starship Nostromo is awakened from their cryo-sleep capsules halfway through their journey home to investigate a distress call from an alien vessel. The terror begins when the crew encounters a nest of eggs inside the alien ship.", new Label.LabelStyle(new BitmapFont(), Color.DARK_GRAY));
        heading.setFontScale(2f);
        story.setFontScale(1.3f);
        story.setWrap(true);
        story.setAlignment(Align.topLeft);
        dialog.getContentTable().add(heading).expandX().row();
        //dialog.getContentTable().add(wormImage).height(122).width(240).row();
        dialog.getContentTable().add(story).width(600).row();
        dialog.getButtonTable().add(renderCloseButton()).size(50, 50).row();
        dialog.show(stage);
    }

    private void openDetailPage7() {
        logger.info("open details page7");
        dialog = new Dialog("Missile Detail", skin);
        dialog.setModal(true);
        dialog.setMovable(false);
        dialog.setResizable(true);
        Image wormImage = new Image(new Texture("images/story/chapter2art.png"));
        Label heading = new Label("Missile Detail" , new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        Label story = new Label("In deep space, the crew of the commercial starship Nostromo is awakened from their cryo-sleep capsules halfway through their journey home to investigate a distress call from an alien vessel. The terror begins when the crew encounters a nest of eggs inside the alien ship.", new Label.LabelStyle(new BitmapFont(), Color.DARK_GRAY));
        heading.setFontScale(2f);
        story.setFontScale(1.3f);
        story.setWrap(true);
        story.setAlignment(Align.topLeft);
        dialog.getContentTable().add(heading).expandX().row();
        //dialog.getContentTable().add(wormImage).height(122).width(240).row();
        dialog.getContentTable().add(story).width(600).row();
        dialog.getButtonTable().add(renderCloseButton()).size(50, 50).row();
        dialog.show(stage);
    }

    /**
     * The close button which triggers a task to close the dialog.
     * @return closeButton image
     */
    private ImageButton renderCloseButton() {
        Image crossButtonImg = new Image(new Texture("images/monster_menu/back.png"));

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
