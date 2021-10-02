package com.deco2800.game.components.obstacle;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.tasks.ObstacleAttackTask;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private Table Monster6Table;
    private Table Monster7Table;
    private Table secretTable;
    private Table secretTable2;
    private Table secretTable3;
    private Table secretTable4;
    private Table secretTable5;
    private Table secretTable6;
    private Table secretTable7;
    private Table monster1AttributeTable;
    private Table monster2AttributeTable;
    private Table monster3AttributeTable;
    private Table monster4AttributeTable;
    private Table monster5AttributeTable;
    private Table monster6AttributeTable;
    private Table monster7AttributeTable;


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
        ImageButton unlockedChapterImg6 = getImageButton("images/ufo.png");
        ImageButton unlockedChapterImg7 = getImageButton("images/missile.png");



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
        unlockedChapterImg6.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                entity.getEvents().trigger("openDetailPage6");
            }
        });

        unlockedChapterImg7.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                entity.getEvents().trigger("openDetailPage7");
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
        Label monsterName = new Label("Alien plant", skin);
        monsterName.setFontScale(1f);
        monster1Table.left().top().padLeft(480).padTop(25);
        monster1Table.setFillParent(true);
        monster1Table.add(unlockedChapterImg).size(60,60).padBottom(20);
        monster1Table.add(monsterName).padLeft(55).padBottom(45);



        monster1AttributeTable = new Table();
        Label monsterAttributes = new Label("attack: 3\nhealth: 100", skin);
        monsterAttributes.setColor(255,255,255,0.5f);
        monsterAttributes.setFontScale(0.8f);
        monster1AttributeTable.left().top().padLeft(600).padTop(55);
        monster1AttributeTable.setFillParent(true);
        monster1AttributeTable.add(monsterAttributes);



        monster2Table = new Table();

        Label monster2Name = new Label("Alien Thorn", skin);
        monster2Name.setFontScale(1f);
        monster2Table.left().top().padLeft(460).padTop(120);
        monster2Table.setFillParent(true);
        monster2Table.add(unlockedChapterImg2).size(80,80).padLeft(15).padBottom(30);
        monster2Table.add(monster2Name).padLeft(40).padBottom(35);


        monster2AttributeTable = new Table();
        Label monster2Attributes = new Label("attack: 3\nhealth: 100", skin);
        monster2Attributes.setColor(255,255,255,0.5f);
        monster2Attributes.setFontScale(0.8f);
        monster2AttributeTable.left().top().padLeft(600).padTop(173);
        monster2AttributeTable.setFillParent(true);
        monster2AttributeTable.add(monster2Attributes);


        Monster3Table = new Table();
        Label monster3Name = new Label("Meteorite", skin);
        monster3Name.setFontScale(1f);
        Monster3Table.left().top().padLeft(480).padTop(245);
        Monster3Table.setFillParent(true);
        Monster3Table.add(unlockedChapterImg3).size(60,60).padLeft(10).padBottom(40);
        Monster3Table.add(monster3Name).padLeft(50).padBottom(35);

        monster3AttributeTable = new Table();
        Label monster3Attributes = new Label("attack: 3\nhealth: 100", skin);
        monster3Attributes.setColor(255,255,255,0.5f);
        monster3Attributes.setFontScale(0.8f);
        monster3AttributeTable.left().top().padLeft(600).padTop(290);
        monster3AttributeTable.setFillParent(true);
        monster3AttributeTable.add(monster3Attributes);



        Monster4Table = new Table();
        Label monster4Name = new Label("Alien Monkey", skin);
        monster4Name.setFontScale(1f);
        Monster4Table.left().top().padLeft(455).padTop(350);
        Monster4Table.setFillParent(true);
        Monster4Table.add(unlockedChapterImg4).size(130,130);
        Monster4Table.add(monster4Name).padBottom(55).padLeft(15);

        monster4AttributeTable = new Table();
        Label monster4Attributes = new Label("attack: 3\nhealth: 100", skin);
        monster4Attributes.setColor(255,255,255,0.5f);
        monster4Attributes.setFontScale(0.8f);
        monster4AttributeTable.left().top().padLeft(600).padTop(405);
        monster4AttributeTable.setFillParent(true);
        monster4AttributeTable.add(monster4Attributes);


        Monster5Table = new Table();
        Label monster5Name = new Label("Face Worm", skin);
        monster5Name.setFontScale(1f);
        Monster5Table.left().top().padLeft(460).padTop(490);
        Monster5Table.setFillParent(true);
        Monster5Table.add(unlockedChapterImg5).size(80,80).padLeft(15);
        Monster5Table.add(monster5Name).padLeft(45).padBottom(40);

        monster5AttributeTable = new Table();
        Label monster5Attributes = new Label("attack: 3\nhealth: 100", skin);
        monster5Attributes.setColor(255,255,255,0.5f);
        monster5Attributes.setFontScale(0.8f);
        monster5AttributeTable.left().top().padLeft(610).padTop(525);
        monster5AttributeTable.setFillParent(true);
        monster5AttributeTable.add(monster5Attributes);


        Monster6Table = new Table();
        Label monster6Name = new Label("Spaceship", skin);
        monster6Name.setFontScale(1f);

        Monster6Table.left().top().padLeft(465).padTop(610);
        Monster6Table.setFillParent(true);
        Monster6Table.add(unlockedChapterImg6).size(80,80).padLeft(15);
        Monster6Table.add(monster6Name).padLeft(45).padBottom(40);

        monster6AttributeTable = new Table();
        Label monster6Attributes = new Label("attack: 3\nhealth: 100", skin);
        monster6Attributes.setColor(255,255,255,0.5f);
        monster6Attributes.setFontScale(0.8f);
        monster6AttributeTable.left().top().padLeft(610).padTop(645);
        monster6AttributeTable.setFillParent(true);
        monster6AttributeTable.add(monster6Attributes);


        Monster7Table = new Table();
        Label monster7Name = new Label("missile", skin);
        monster7Name.setFontScale(1f);

        Monster7Table.left().top().padLeft(465).padTop(720);
        Monster7Table.setFillParent(true);
        Monster7Table.add(unlockedChapterImg7).size(80,80).padLeft(15);
        Monster7Table.add(monster7Name).padLeft(50).padBottom(40);

        monster7AttributeTable = new Table();
        Label monster7Attributes = new Label("attack: 3\nhealth: 100", skin);
        monster7Attributes.setColor(255,255,255,0.5f);
        monster7Attributes.setFontScale(0.8f);
        monster7AttributeTable.left().top().padLeft(615).padTop(755);
        monster7AttributeTable.setFillParent(true);
        monster7AttributeTable.add(monster7Attributes);


        //this table contains the button back to main menu
        buttonTable = new Table();
        buttonTable.bottom().right();
        buttonTable.padBottom(50f).padRight(80f);
//        buttonTable.center();

        buttonTable.setFillParent(true);
        buttonTable.add(mainMenuButton);
       // buttonTable.add(unlockedChapterImg);

        box = new Image(ServiceLocator.getResourceService()
                .getAsset("images/monster_menu/box_covered.png", Texture.class));


        boardTable.add(box).size(700,900);

        //this table contains the background image
        Image bgImage = new Image(ServiceLocator.getResourceService()
                .getAsset("images/monster_menu/background.png", Texture.class));
        bgTable = new Table();
        bgTable.setFillParent(true);
        bgTable.add(bgImage).size(1400,880);

        Image secretImage = new Image(ServiceLocator.getResourceService()
                .getAsset("images/monster_menu/plant.jpg", Texture.class));
        secretTable = new Table().padBottom(650);
        secretTable.setFillParent(true);
        secretTable.add(secretImage).size(540,125).padBottom(25);

        Image secretImage2 = new Image(ServiceLocator.getResourceService()
                .getAsset("images/monster_menu/thorns.jpg", Texture.class));
        secretTable2 = new Table().padBottom(350);
        secretTable2.setFillParent(true);
        secretTable2.add(secretImage2).size(540,125).padBottom(90);

        Image secretImage3 = new Image(ServiceLocator.getResourceService()
                .getAsset("images/monster_menu/stone.jpg", Texture.class));
        secretTable3 = new Table().padBottom(160);
        secretTable3.setFillParent(true);
        secretTable3.add(secretImage3).size(540,125).padBottom(30);

        Image secretImage4 = new Image(ServiceLocator.getResourceService()
                .getAsset("images/monster_menu/monkey.jpg", Texture.class));
        secretTable4 = new Table().padTop(90);
        secretTable4.setFillParent(true);
        secretTable4.add(secretImage4).size(540,125).padBottom(30);

        Image secretImage5 = new Image(ServiceLocator.getResourceService()
                .getAsset("images/monster_menu/hugger.jpg", Texture.class));
        secretTable5 = new Table().padTop(330);
        secretTable5.setFillParent(true);
        secretTable5.add(secretImage5).size(540,125).padBottom(30);

        Image secretImage6 = new Image(ServiceLocator.getResourceService()
                .getAsset("images/monster_menu/ship.jpg", Texture.class));
        secretTable6 = new Table().padTop(570);
        secretTable6.setFillParent(true);
        secretTable6.add(secretImage6).size(540,115).padBottom(30);

        Image secretImage7 = new Image(ServiceLocator.getResourceService()
                .getAsset("images/monster_menu/hugger.jpg", Texture.class));
        secretTable7 = new Table().padTop(780);
        secretTable7.setFillParent(true);
        secretTable7.add(secretImage7).size(540,95).padBottom(30);

        // add the board to the stage first so that its can be under of score data
        stage.addActor(bgTable);
        stage.addActor(boardTable);
        stage.addActor(monster1Table);
        stage.addActor(monster2Table);
        stage.addActor(Monster3Table);
        stage.addActor(Monster4Table);
        stage.addActor(Monster5Table);
        stage.addActor(Monster6Table);
        stage.addActor(Monster7Table);
        stage.addActor(monster1AttributeTable);
        stage.addActor(monster2AttributeTable);
        stage.addActor(monster3AttributeTable);
        stage.addActor(monster4AttributeTable );
        stage.addActor(monster5AttributeTable);
        stage.addActor(monster6AttributeTable);
        stage.addActor(monster7AttributeTable);
        if (ObstacleEventHandler.locked){
           stage.addActor(secretTable);
        }
        if (ObstacleEventHandler.locked2){
            stage.addActor(secretTable2);
        }

        if (ObstacleEventHandler.locked3){
            stage.addActor(secretTable3);
        }

        if (ObstacleAttackTask.lock_use){
            stage.addActor(secretTable4);
        }

        if (ObstacleAttackTask.lock_use){
            stage.addActor(secretTable5);
        }

        if (ObstacleEventHandler.locked_ufo){
            stage.addActor(secretTable6);
            stage.addActor(secretTable7);
        }




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
