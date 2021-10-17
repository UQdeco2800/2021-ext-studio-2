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
import com.badlogic.gdx.utils.Scaling;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * To show the details of every monster when click image.
 */
public class MonsterDetails extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(MonsterDetails.class);
    private Dialog dialog;
    private static final String BACKGROUND = "images/monster_menu/box-background.png";

    @Override
    public void create() {
        super.create();

        entity.getEvents().addListener("openDetailPage", this::openDetailPage1);
        entity.getEvents().addListener("openDetailPage2", this::openDetailPage2);
        entity.getEvents().addListener("openDetailPage3", this::openDetailPage3);
        entity.getEvents().addListener("openDetailPage4", this::openDetailPage4);
        entity.getEvents().addListener("openDetailPage5", this::openDetailPage5);
        entity.getEvents().addListener("openDetailPage6", this::openDetailPage6);

    }

    private void openDetailPage1() {
        logger.info("Alien Plant Detail Page");
        dialog = new Dialog("Alien Plant Detail", skin);
        dialog.setModal(true);
        dialog.setMovable(false);
        dialog.setResizable(true);
        Image plantImage = new Image(new Texture("images/monster_menu/plant_img.png"));
        Label heading = new Label("Alien plant Feature: Trigger bounce ", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        Label story = new Label("This is an alien plant, when the character touches the obstacle, the alien plant will burst into pieces. Then the main character will be slowed down and deducted a certain amount of blood", new Label.LabelStyle(new BitmapFont(), Color.DARK_GRAY));
        heading.setFontScale(2f);
        story.setFontScale(1.3f);
        story.setWrap(true);
        story.setAlignment(Align.topLeft);
        dialog.getContentTable().add(heading).expandX().row();
        dialog.getContentTable().add(plantImage).height(152).width(240).row();
        dialog.getContentTable().add(story).width(600).row();
        dialog.getButtonTable().add(renderCloseButton()).size(50, 50).row();
        Image background = new Image(new Texture(BACKGROUND));
        background.setScaling(Scaling.fit);
        dialog.setBackground(background.getDrawable());
        dialog.show(stage);
    }

    private void openDetailPage2() {
        logger.info("Aline thorn Detail Page");
        dialog = new Dialog("Aline thorn Detail", skin);
        dialog.setModal(true);
        dialog.setMovable(false);
        dialog.setResizable(true);
        Image thornImage = new Image(new Texture("images/monster_menu/thron_img.png"));
        Label heading = new Label("thorn Feature: Slow down", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        Label story = new Label("Dangerous plants mutated by radiation, once the character collides with it, it will be entangled immediately, causing damage, reducing health, and the character will also be slowed.", new Label.LabelStyle(new BitmapFont(), Color.DARK_GRAY));
        heading.setFontScale(2f);
        story.setFontScale(1.3f);
        story.setWrap(true);
        story.setAlignment(Align.topLeft);
        dialog.getContentTable().add(heading).expandX().row();
        dialog.getContentTable().add(thornImage).height(152).width(240).row();
        dialog.getContentTable().add(story).width(600).row();
        dialog.getButtonTable().add(renderCloseButton()).size(50, 50).row();
        Image background = new Image(new Texture(BACKGROUND));
        background.setScaling(Scaling.fit);
        dialog.setBackground(background.getDrawable());
        dialog.show(stage);
    }


    private void openDetailPage3() {
        logger.info("Meteorite Detail Page");
        dialog = new Dialog("meteorite Detail", skin);
        dialog.setModal(true);
        dialog.setMovable(false);
        dialog.setResizable(true);
        Image meteoriteImage = new Image(new Texture("images/monster_menu/stone_img.png"));
        Label heading = new Label("Meteorite Feature: Multiple meteorites drop", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        Label story = new Label("Falling meteorites which will fall from the sky to reduce the speed of main character and cause harm. Meteorites will fall in a certain number at the same time, please pay attention to dodge!", new Label.LabelStyle(new BitmapFont(), Color.DARK_GRAY));
        heading.setFontScale(2f);
        story.setFontScale(1.3f);
        story.setWrap(true);
        story.setAlignment(Align.topLeft);
        dialog.getButtonTable().add(renderCloseButton()).size(50, 50).top();
        dialog.getContentTable().add(heading).expandX().row();
        dialog.getContentTable().add(meteoriteImage).height(152).width(240).row();
        dialog.getContentTable().add(story).width(600).row();
        Image background = new Image(new Texture(BACKGROUND));
        background.setScaling(Scaling.fit);
        dialog.setBackground(background.getDrawable());
        dialog.show(stage);
    }

    private void openDetailPage4() {
        logger.info("Aline Monkey Detail Page");
        dialog = new Dialog("Aline Monkey Detail", skin);
        dialog.setModal(true);
        dialog.setMovable(false);
        dialog.setResizable(true);
        Image monkeyImage = new Image(new Texture("images/monster_menu/monkey_img.png"));
        Label heading = new Label("Monkey Feature: Release face-hugger", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        Label story = new Label("when the character jump close to the monkey, it will throw a face hugger to attack the character.", new Label.LabelStyle(new BitmapFont(), Color.DARK_GRAY));
        heading.setFontScale(2f);
        story.setFontScale(1.3f);
        story.setWrap(true);
        story.setAlignment(Align.topLeft);
        dialog.getContentTable().add(heading).expandX().row();
        dialog.getContentTable().add(monkeyImage).height(152).width(240).row();
        dialog.getContentTable().add(story).width(600).row();
        dialog.getButtonTable().add(renderCloseButton()).size(50, 50).row();
        Image background = new Image(new Texture(BACKGROUND));
        background.setScaling(Scaling.fit);
        dialog.setBackground(background.getDrawable());
        dialog.show(stage);

    }

    private void openDetailPage5() {
        logger.info("Face Worm Detail Page");
        dialog = new Dialog("Face Worm Detail", skin);
        dialog.setModal(true);
        dialog.setMovable(false);
        dialog.setResizable(true);
        Image wormImage = new Image(new Texture("images/monster_menu/hugger_img.png"));
        Label heading = new Label("Feature: Follow the character some distance", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        Label story = new Label("Associated insects of alien creatures. After the character gets too close to the alien monkey, the alien monkey will release the associated insects to pounce and attack the character, causing the character to reduce its health.", new Label.LabelStyle(new BitmapFont(), Color.DARK_GRAY));
        heading.setFontScale(2f);
        story.setFontScale(1.3f);
        story.setWrap(true);
        story.setAlignment(Align.topLeft);
        dialog.getContentTable().add(heading).expandX().row();
        dialog.getContentTable().add(wormImage).height(152).width(240).row();
        dialog.getContentTable().add(story).width(600).row();
        dialog.getButtonTable().add(renderCloseButton()).size(50, 50).row();
        Image background = new Image(new Texture(BACKGROUND));
        background.setScaling(Scaling.fit);
        dialog.setBackground(background.getDrawable());
        dialog.show(stage);
    }

    private void openDetailPage6() {
        logger.info("Spaceship Detail Page");
        dialog = new Dialog("Spaceship Detail", skin);
        dialog.setModal(true);
        dialog.setMovable(false);
        dialog.setResizable(true);
        Image wormImage = new Image(new Texture("images/monster_menu/ufo_img.png"));
        Label heading = new Label("spaceship Feature: 20s missile attack", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        Label story = new Label("The attack method of the alien spacecraft. After the game enters a stage, the alien spacecraft will release several missiles to attack the character. The character needs to avoid the missile attack, otherwise it will reduce the character's life value.", new Label.LabelStyle(new BitmapFont(), Color.DARK_GRAY));
        heading.setFontScale(2f);
        story.setFontScale(1.3f);
        story.setWrap(true);
        story.setAlignment(Align.topLeft);
        dialog.getContentTable().add(heading).expandX().row();
        dialog.getContentTable().add(wormImage).height(152).width(240).row();
        dialog.getContentTable().add(story).width(600).row();
        dialog.getButtonTable().add(renderCloseButton()).size(50, 50).row();
        Image background = new Image(new Texture(BACKGROUND));
        background.setScaling(Scaling.fit);
        dialog.setBackground(background.getDrawable());
        dialog.show(stage);
    }

    /**
     * The close button which triggers a task to close the dialog.
     *
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
        // Don't need to draw, simply keep empty.
    }

}
