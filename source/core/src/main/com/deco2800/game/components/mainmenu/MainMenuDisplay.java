package com.deco2800.game.components.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A ui component for displaying the Main menu.
 */
public class MainMenuDisplay extends UIComponent {
  private static final Logger logger = LoggerFactory.getLogger(MainMenuDisplay.class);
  private static final float Z_INDEX = 2f;
  private Table table;

  @Override
  public void create() {
    super.create();
    addActors();
  }

  private void addActors() {
    table = new Table();
    table.setFillParent(true);
    Image title =
        new Image(
            ServiceLocator.getResourceService()
                .getAsset("images/new_menu_title.png", Texture.class));

    Image background =
        new Image(
            ServiceLocator.getResourceService()
                .getAsset("images/menu_background/menu_background.png", Texture.class));

    /** build new start button */
    Button.ButtonStyle start = new Button.ButtonStyle();
    start.up= new TextureRegionDrawable(new TextureRegion(
            new Texture(Gdx.files.internal("button/start1.png"))));
    start.over= new TextureRegionDrawable(new TextureRegion(
            new Texture(Gdx.files.internal("button/start2.png"))));
    Button startBtn = new Button(start);

    /** build select unlocked attire button */
    Button.ButtonStyle attires = new Button.ButtonStyle();
    attires.up = new TextureRegionDrawable(new TextureRegion(
            new Texture(Gdx.files.internal("button/select1.png"))));
    attires.over = new TextureRegionDrawable(new TextureRegion(
            new Texture(Gdx.files.internal("button/select2.png"))));
    Button attiresBtn = new Button(attires);

    /** build new setting button */
    Button.ButtonStyle setting = new Button.ButtonStyle();
    setting.up = new TextureRegionDrawable(new TextureRegion(
            new Texture(Gdx.files.internal("button/setting1.png"))));
    setting.over = new TextureRegionDrawable(new TextureRegion(
            new Texture(Gdx.files.internal("button/setting2.png"))));
    Button settingBtn = new Button(setting);

    /** build new exit button */
    Button.ButtonStyle exit = new Button.ButtonStyle();
    exit.up = new TextureRegionDrawable(new TextureRegion(
            new Texture(Gdx.files.internal("button/exit1.png"))));
    exit.over = new TextureRegionDrawable(new TextureRegion(
            new Texture(Gdx.files.internal("button/exit2.png"))));
    Button exitBtn = new Button(exit);

    /** build new prop shop button */
    Button.ButtonStyle propShop = new Button.ButtonStyle();
    propShop.up = new TextureRegionDrawable(new TextureRegion(
            new Texture(Gdx.files.internal("button/shop1.png"))));
    propShop.over = new TextureRegionDrawable(new TextureRegion(
            new Texture(Gdx.files.internal("button/shop2.png"))));
    Button shopBtn = new Button(propShop);
    shopBtn.setPosition(820,650);

    /** build new score button */
    Button.ButtonStyle score = new Button.ButtonStyle();
    score.up = new TextureRegionDrawable(new TextureRegion(
            new Texture(Gdx.files.internal("button/score1.png"))));
    score.over = new TextureRegionDrawable(new TextureRegion(
            new Texture(Gdx.files.internal("button/score2.png"))));
    Button scoreBtn = new Button(score);
    scoreBtn.setPosition(960,685);

    /** build new achievement button */
    Button.ButtonStyle achievement = new Button.ButtonStyle();
    achievement.up = new TextureRegionDrawable(new TextureRegion(
            new Texture(Gdx.files.internal("button/achievement1.png"))));
    achievement.over = new TextureRegionDrawable(new TextureRegion(
            new Texture(Gdx.files.internal("button/achievement2.png"))));
    Button achievementBtn = new Button(achievement);
    achievementBtn.setPosition(1100,682);

    /** build new monster button */
    Button.ButtonStyle monster = new Button.ButtonStyle();
    monster.up = new TextureRegionDrawable(new TextureRegion(
            new Texture(Gdx.files.internal("button/monster1.png"))));
    monster.over = new TextureRegionDrawable(new TextureRegion(
            new Texture(Gdx.files.internal("button/monster2.png"))));

    Button monsterBtn = new Button(monster);
    monsterBtn.setPosition(1120,40);


  Button.ButtonStyle buff = new Button.ButtonStyle();
      buff.up = new TextureRegionDrawable(new TextureRegion(
          new Texture(Gdx.files.internal("button/buff1.png"))));
      buff.over = new TextureRegionDrawable(new TextureRegion(
          new Texture(Gdx.files.internal("button/buff2.png"))));

  Button buffBtn = new Button(buff);
      buffBtn.setPosition(1120,170);


      //TextButton startBtn = new TextButton("Start", skin);
    //TextButton settingsBtn = new TextButton("Settings", skin);
    //TextButton exitBtn = new TextButton("Exit", skin);
    //props shop
    //TextButton propsShopBtn = new TextButton("Props Shop", skin);
    //Team9 History Scores
    //TextButton historyScoreBtn = new TextButton("History Score", skin);
    //TextButton achievementsBtn = new TextButton("Achievements", skin);
    //TextButton attiresBtn = new TextButton("Select Unlocked Attires", skin);
    //Team8
    //TextButton monsterMenuBtn = new TextButton("Monster Menu", skin);


      // Triggers an event when the button is pressed
    startBtn.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent changeEvent, Actor actor) {
            logger.debug("Start button clicked");
            entity.getEvents().trigger("start");
          }
        });


    settingBtn.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent changeEvent, Actor actor) {
            logger.debug("Settings button clicked");
            entity.getEvents().trigger("settings");
          }
        });

    exitBtn.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent changeEvent, Actor actor) {

            logger.debug("Exit button clicked");
            entity.getEvents().trigger("exit");
          }
        });

      shopBtn.addListener(new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
              logger.debug("History Score clicked");
              entity.getEvents().trigger("displayPropsShop");
          }
      });

    scoreBtn.addListener(new ChangeListener() {
        @Override
        public void changed(ChangeEvent event, Actor actor) {
            logger.debug("History Score clicked");
            entity.getEvents().trigger("displayHistoryScores");
        }
    });

    achievementBtn.addListener(new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
              logger.debug("Achievements clicked");
              entity.getEvents().trigger("achievements");
          }
    });

    attiresBtn.addListener(new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
              logger.debug("Unlocked Attires clicked");
              entity.getEvents().trigger("unlockedAttires");
          }
      });

    monsterBtn.addListener(new ChangeListener() {
        @Override
        public void changed(ChangeEvent event, Actor actor) {
            logger.debug("monsterMenu clicked");
            entity.getEvents().trigger("monsterMenu");
        }
    });

    buffBtn.addListener(new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
              logger.debug("buff manual Menu clicked");
              entity.getEvents().trigger("buffMenu");
          }
      });

    Table bgTable = new Table();
    bgTable.setFillParent(true);
    table.add(title);
    table.row();
    table.add(startBtn).padTop(30f);
    table.row();
    table.add(attiresBtn).padTop(30f);
    table.row();
    table.add(settingBtn).padTop(30f);
    table.row();
    table.add(exitBtn).padTop(30f);
    stage.addActor(bgTable);
    stage.addActor(table);
    stage.addActor(shopBtn);
    stage.addActor(scoreBtn);
    stage.addActor(achievementBtn);
    stage.addActor(monsterBtn);
    stage.addActor(buffBtn);
    bgTable.add(background).size(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
  }

  @Override
  public void draw(SpriteBatch batch) {
    // draw is handled by the stage
  }

  @Override
  public float getZIndex() {
    return Z_INDEX;
  }

  @Override
  public void dispose() {
    table.clear();
    super.dispose();
  }
}
