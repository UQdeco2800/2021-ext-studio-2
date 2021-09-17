package com.deco2800.game.components.Obstacle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.deco2800.game.GdxGame;
import com.deco2800.game.files.GameRecords;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class MonsterDispay extends UIComponent {

    private static final Logger logger = LoggerFactory.getLogger(MonsterDispay.class);
    private final GdxGame game;
    private Table boardTable;
    private Table buttonTable;
    private Table bgTable;
    private Table monster1Table;
    private Table monster2Table;
    private Table Monster3Table;
    private Table Monster4Table;
    private Table Monster5Table;
    private Image box;

    public MonsterDispay(GdxGame game) {
        this.game = game;
    }

    @Override
    public void create() {
        super.create();
        createHistoryScoreBoard();
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

    private void createHistoryScoreBoard() {
        // Create Button to the monster menu
        TextButton mainMenuButton = new TextButton("Main Menu", skin);
        ImageButton unlockedChapterImg = getImageButton("images/obstacle_1_new.png");
        ImageButton unlockedChapterImg2 = getImageButton("images/obstacle2_vision2.png");
        ImageButton unlockedChapterImg3 = getImageButton("images/stone1.png");
        ImageButton unlockedChapterImg4 = getImageButton("images/monkey_original.png");
        ImageButton unlockedChapterImg5 = getImageButton("images/Facehugger.png");




        unlockedChapterImg.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                entity.getEvents().trigger("openDetailPage");
            }
        });

        unlockedChapterImg2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                entity.getEvents().trigger("openDetailPage2");
            }
        });
        unlockedChapterImg3.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                entity.getEvents().trigger("openDetailPage3");
            }
        });

        unlockedChapterImg4.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                entity.getEvents().trigger("openDetailPage4");
            }
        });
        unlockedChapterImg5.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                entity.getEvents().trigger("openDetailPage5");
            }
        });





        mainMenuButton.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.info("return menu button clicked");
                        game.setScreen(GdxGame.ScreenType.MAIN_MENU);
                    }
                });

        //add the bg image table and scoreboard to stage first.

        //this table contains the board
        boardTable = new Table();
        boardTable.center();
        boardTable.setFillParent(true);

        monster1Table = new Table();
        Label monsterName = new Label("Alien plant", skin,"large");
        Image plantImg = new Image(ServiceLocator.getResourceService()
                .getAsset("images/obstacle_1_new.png", Texture.class));
        monster1Table.left().top().padLeft(460).padTop(75);
        monster1Table.add(monsterName);
        monster1Table.setFillParent(true);
        monster1Table.add(unlockedChapterImg).size(100,100).padLeft(85).padBottom(30);


        monster2Table = new Table();

        Label monster2Name = new Label("Alien Thorn", skin,"large");
        Image thornImg = new Image(ServiceLocator.getResourceService()
                .getAsset("images/obstacle2_vision2.png", Texture.class));
        monster2Table.left().top().padLeft(450).padTop(170);
        monster2Table.setFillParent(true);
        monster2Table.add(monster2Name);
        monster2Table.add(unlockedChapterImg2).size(120,120).padLeft(75).padBottom(30);


        Monster3Table = new Table();
        Label monster3Name = new Label("Meteorite", skin,"large");
        Image stoneImg = new Image(ServiceLocator.getResourceService()
                .getAsset("images/stone1.png", Texture.class));
        Monster3Table.left().top().padLeft(450).padTop(290);
        Monster3Table.setFillParent(true);
        Monster3Table.add(monster3Name);
        Monster3Table.add(unlockedChapterImg3).size(100,100).padLeft(110).padBottom(40);


        Monster4Table = new Table();
        Label monster4Name = new Label("Alien Monkey", skin,"large");
        Image monkeyImg = new Image(ServiceLocator.getResourceService()
                .getAsset("images/monkey_original.png", Texture.class));
        Monster4Table.left().top().padLeft(450).padTop(420);
        Monster4Table.setFillParent(true);
        Monster4Table.add(monster4Name);
        Monster4Table.add(unlockedChapterImg4).size(130,130).padLeft(50);


        Monster5Table = new Table();
        Label monster5Name = new Label("Face Worm", skin,"large");
        Image bugImg = new Image(ServiceLocator.getResourceService()
                .getAsset("images/Facehugger.png", Texture.class));
        Monster5Table.left().top().padLeft(450).padTop(530);
        Monster5Table.setFillParent(true);
        Monster5Table.add(monster5Name);
        Monster5Table.add(unlockedChapterImg5).size(130,130).padLeft(85);










        //this table contains the button back to main menu
        buttonTable = new Table();
        buttonTable.bottom().right();
        buttonTable.padBottom(100f).padRight(110f);
//        buttonTable.center();

        buttonTable.setFillParent(true);
        buttonTable.add(mainMenuButton);
       // buttonTable.add(unlockedChapterImg);

        box = new Image(ServiceLocator.getResourceService()
                .getAsset("images/monster_menu/Monsterbox.jpg", Texture.class));


        boardTable.add(box).size(700,900);

        //this table contains the background image
        Image bgImage = new Image(ServiceLocator.getResourceService()
                .getAsset("images/monster_menu/background.png", Texture.class));
        bgTable = new Table();
        bgTable.setFillParent(true);
        bgTable.add(bgImage).size(1400,1000);


        // add the board to the stage first so that its can be under of score data
        stage.addActor(bgTable);
        stage.addActor(boardTable);
        stage.addActor(monster1Table);
        stage.addActor(monster2Table);
        stage.addActor(Monster3Table);
        stage.addActor(Monster4Table);
        stage.addActor(Monster5Table);
        stage.addActor(buttonTable);


        //prepare the data tables

    }

    @Override
    public void dispose() {
        buttonTable.clear();
        boardTable.clear();
        bgTable.clear();
        super.dispose();
    }

    @Override
    protected void draw(SpriteBatch batch) {

    }
}
