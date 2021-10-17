package com.deco2800.game.components.obstacle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.tasks.ObstacleAttackTask;
import com.deco2800.game.entities.configs.BaseEntityConfig;
import com.deco2800.game.entities.configs.NPCConfigs;
import com.deco2800.game.entities.configs.ObstaclesConfigs;
import com.deco2800.game.files.FileLoader;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Display all monsters, It is locked by default, and will only be unlocked when the character encounters this
 * type of monster.
 */
public class MonsterDisplay extends UIComponent {
    private static final NPCConfigs configs =
            FileLoader.readClass(NPCConfigs.class, "configs/NPCs.json");
    private static final ObstaclesConfigs configs2 =
            FileLoader.readClass(ObstaclesConfigs.class, "configs/obstacles.json");
    private static final Logger logger = LoggerFactory.getLogger(MonsterDisplay.class);
    private final GdxGame game;
    private Table boardTable;
    private Table buttonTable;
    private Table bgTable;


    public MonsterDisplay(GdxGame game) {
        this.game = game;
    }

    @Override
    public void create() {
        super.create();
        createMonsterMenuBoard();
    }

    /**
     * Returns an image button to be reused everywhere.
     *
     * @param upPath   the image path
     * @param overPath the image path
     * @return ImageButton to be displayed
     */
    private ImageButton getImageButton(String upPath, String overPath) {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal(upPath))));
        style.over = new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal(overPath))));
        ImageButton button = new ImageButton(style);
        return button;
    }


    private void createMonsterMenuBoard() {
        // Create Button to the monster menu
        TextButton mainMenuButton = new TextButton("Main Menu", skin);
        ImageButton unlockedImg0 = getImageButton("images/obstacle_1_new.png", "images/monster_menu/plant_over.png");
        ImageButton unlockedImg2 = getImageButton("images/obstacle2_vision2.png", "images/monster_menu/thorns_over.png");
        ImageButton unlockedImg3 = getImageButton("images/stone1.png", "images/monster_menu/stone_over.png");
        ImageButton unlockedImg4 = getImageButton("images/monkey_original.png", "images/monster_menu/monkey_original_over.png");
        ImageButton unlockedImg5 = getImageButton("images/Facehugger.png", "images/monster_menu/Facehugger_over.png");
        ImageButton unlockedImg6 = getImageButton("images/monster_menu/boss.png", "images/monster_menu/ufo_over.png");
        ImageButton unlockedImg7 = getImageButton("images/missile.png", "images/monster_menu/missile_over.png");
//
        unlockedImg0.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                logger.info("Plant button clicked");
                entity.getEvents().trigger("openDetailPage");
            }
        });

        unlockedImg2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                logger.info("Thorns button clicked");
                entity.getEvents().trigger("openDetailPage2");
            }
        });
        unlockedImg3.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                logger.info("Meteorite button clicked");
                entity.getEvents().trigger("openDetailPage3");
            }
        });

        unlockedImg4.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                logger.info("Monkey button clicked");
                entity.getEvents().trigger("openDetailPage4");
            }
        });
        unlockedImg5.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                logger.info("Face Worm button clicked");
                entity.getEvents().trigger("openDetailPage5");
            }
        });
        unlockedImg6.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                logger.info("Spaceship button clicked");
                entity.getEvents().trigger("openDetailPage6");
            }
        });

        unlockedImg7.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                entity.getEvents().trigger("openDetailPage7");
            }
        });


        mainMenuButton.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.info("Menu button clicked");
                        game.setScreen(GdxGame.ScreenType.MAIN_MENU);
                    }
                });

        //add the bg image table and scoreboard to stage first.

        //this table contains the board
        boardTable = new Table();
        boardTable.center();
        boardTable.setFillParent(true);

        Table monster1Table = new Table();
        Label monsterName = new Label("Alien plant", skin);
        monsterName.setFontScale(1f);
        monster1Table.padBottom(700);
        monster1Table.setFillParent(true);
        monster1Table.add(unlockedImg0).size(60, 60).padBottom(20);
        monster1Table.add(monsterName).padLeft(55).padBottom(45);

        BaseEntityConfig config_plant = configs2.plant;
        Table monster1AttributeTable = new Table();
        Label monsterAttributes = new Label("attack:" + config_plant.baseAttack , skin);
        monsterAttributes.setColor(255, 255, 255, 0.5f);
        monsterAttributes.setFontScale(0.8f);
        monster1AttributeTable.center().padLeft(90).padBottom(680);
        monster1AttributeTable.setFillParent(true);
        monster1AttributeTable.add(monsterAttributes);


        Table monster2Table = new Table();
        BaseEntityConfig config_thorn = configs2.thorn;
        Label monster2Name = new Label("Alien Thorn", skin);
        monster2Name.setFontScale(1f);
        monster2Table.center().padBottom(460);
        monster2Table.setFillParent(true);
        monster2Table.add(unlockedImg2).size(80, 80).padLeft(15).padBottom(30);
        monster2Table.add(monster2Name).padLeft(40).padBottom(35);


        Table monster2AttributeTable = new Table();
        Label monster2Attributes = new Label("attack:" +config_thorn.baseAttack, skin);
        monster2Attributes.setColor(255, 255, 255, 0.5f);
        monster2Attributes.setFontScale(0.8f);
        monster2AttributeTable.center().padLeft(100).padBottom(435);
        monster2AttributeTable.setFillParent(true);
        monster2AttributeTable.add(monster2Attributes);


        Table monster3Table = new Table();
        BaseEntityConfig config_Meteorite = configs2.smallMeteorite;
        BaseEntityConfig config_Meteorite2 = configs2.bigMeteorite;
         Label monster3Name = new Label("Meteorite", skin);
        monster3Name.setFontScale(1f);
        monster3Table.center().padBottom(220);
        monster3Table.setFillParent(true);
        monster3Table.add(unlockedImg3).size(60, 60).padLeft(10).padBottom(40);
        monster3Table.add(monster3Name).padLeft(50).padBottom(35);

        Table monster3AttributeTable = new Table();
        Label monster3Attributes = new Label("attack:"+ config_Meteorite.baseAttack + "To" + config_Meteorite2.baseAttack, skin);
        monster3Attributes.setColor(255, 255, 255, 0.5f);
        monster3Attributes.setFontScale(0.8f);
        monster3AttributeTable.center().padLeft(150).padBottom(195);
        monster3AttributeTable.setFillParent(true);
        monster3AttributeTable.add(monster3Attributes);


        Table monster4Table = new Table();
        Label monster4Name = new Label("Alien Monkey", skin);
        monster4Name.setFontScale(1f);
        monster4Table.center().padTop(20).padLeft(20);
        monster4Table.setFillParent(true);
        monster4Table.add(unlockedImg4).size(60, 60).padLeft(10).padBottom(0);
        monster4Table.add(monster4Name).padLeft(40).padBottom(35);

        Table monster4AttributeTable = new Table();
        Label monster4Attributes = new Label("attack: 0", skin);
        monster4Attributes.setColor(255, 255, 255, 0.5f);
        monster4Attributes.setFontScale(0.8f);
        monster4AttributeTable.center().padLeft(100).padTop(45);
        monster4AttributeTable.setFillParent(true);
        monster4AttributeTable.add(monster4Attributes);


        Table monster5Table = new Table();
        BaseEntityConfig config_worm = configs.faceWorm;
        Label monster5Name = new Label("Face Worm", skin);
        monster5Name.setFontScale(1f);
        monster5Table.center().padTop(260);
        monster5Table.setFillParent(true);
        monster5Table.add(unlockedImg5).size(80, 80);
        monster5Table.add(monster5Name).padLeft(35).padBottom(40);

        Table monster5AttributeTable = new Table();
        Label monster5Attributes = new Label("attack:" + config_worm.baseAttack, skin);
        monster5Attributes.setColor(255, 255, 255, 0.5f);
        monster5Attributes.setFontScale(0.8f);
        monster5AttributeTable.center().padLeft(110).padTop(285);
        monster5AttributeTable.setFillParent(true);
        monster5AttributeTable.add(monster5Attributes);

        Table monsterTitle = new Table();
        Label bossTitle = new Label("-----------BOSS---------", skin);
        bossTitle.setFontScale(1.5f);
        monsterTitle.center().padTop(560);
        monsterTitle.setFillParent(true);
        monsterTitle.add(bossTitle).padBottom(120);

        Table monster6Table = new Table();
        BaseEntityConfig config_spaceship = configs.smallMissile;
        Label monster6Name = new Label("Spaceship", skin);
        monster6Name.setFontScale(1f);

        monster6Table.center().padTop(600);
        monster6Table.setFillParent(true);
        monster6Table.add(unlockedImg6).size(160, 160).padRight(40);
        monster6Table.add(monster6Name).padBottom(40).padLeft(30);


        Table monster6AttributeTable = new Table();
        Label monster6Attributes = new Label("attack:" + config_spaceship.baseAttack + "\nduration: 20S", skin);
        monster6Attributes.setColor(255, 255, 255, 0.5f);
        monster6Attributes.setFontScale(0.8f);
        monster6AttributeTable.center().padLeft(230).padTop(630);
        monster6AttributeTable.setFillParent(true);
        monster6AttributeTable.add(monster6Attributes);


        Table monster7Table = new Table();
        Label monster7Name = new Label("missile", skin);
        monster7Name.setFontScale(1f);


        //this table contains the button back to main menu
        buttonTable = new Table();
        buttonTable.bottom().left().padLeft(50).padBottom(50);
        buttonTable.setFillParent(true);
        buttonTable.add(mainMenuButton);


        Image box = new Image(ServiceLocator.getResourceService()
                .getAsset("images/monster_menu/box_covered.png", Texture.class));


        boardTable.add(box).size(700, 900);

        //this table contains the background image
        Image bgImage = new Image(ServiceLocator.getResourceService()
                .getAsset("images/monster_menu/background.png", Texture.class));
        bgTable = new Table();
        bgTable.setFillParent(true);
        bgTable.add(bgImage);
         // this table contains the secretImage
        Image secretImage = new Image(ServiceLocator.getResourceService()
                .getAsset("images/monster_menu/plant.jpg", Texture.class));
        Table secretTable = new Table().padBottom(650);
        secretTable.setFillParent(true);
        secretTable.add(secretImage).size(540, 125).padBottom(25);

        Image secretImage2 = new Image(ServiceLocator.getResourceService()
                .getAsset("images/monster_menu/thorns.jpg", Texture.class));
        Table secretTable2 = new Table().padBottom(350);
        secretTable2.setFillParent(true);
        secretTable2.add(secretImage2).size(540, 125).padBottom(90);

        Image secretImage3 = new Image(ServiceLocator.getResourceService()
                .getAsset("images/monster_menu/stone.jpg", Texture.class));
        Table secretTable3 = new Table().padBottom(160);
        secretTable3.setFillParent(true);
        secretTable3.add(secretImage3).size(540, 125).padBottom(30);

        Image secretImage4 = new Image(ServiceLocator.getResourceService()
                .getAsset("images/monster_menu/monkey.jpg", Texture.class));
        Table secretTable4 = new Table().padTop(90);
        secretTable4.setFillParent(true);
        secretTable4.add(secretImage4).size(540, 125).padBottom(30);

        Image secretImage5 = new Image(ServiceLocator.getResourceService()
                .getAsset("images/monster_menu/hugger.jpg", Texture.class));
        Table secretTable5 = new Table().padTop(330);
        secretTable5.setFillParent(true);
        secretTable5.add(secretImage5).size(540, 125).padBottom(30);

        Image secretImage6 = new Image(ServiceLocator.getResourceService()
                .getAsset("images/monster_menu/ship.jpg", Texture.class));
        Table secretTable6 = new Table().padTop(677);
        secretTable6.setFillParent(true);
        secretTable6.add(secretImage6).size(540, 160).padBottom(100);



        // add the board to the stage first so that its can be under of score data
        stage.addActor(bgTable);
        stage.addActor(boardTable);
        stage.addActor(monster1Table);
        stage.addActor(monster2Table);
        stage.addActor(monster3Table);
        stage.addActor(monster4Table);
        stage.addActor(monster5Table);
        stage.addActor(monster6Table);
        stage.addActor(monsterTitle);
        stage.addActor(monster1AttributeTable);
        stage.addActor(monster2AttributeTable);
        stage.addActor(monster3AttributeTable);
        stage.addActor(monster4AttributeTable);
        stage.addActor(monster5AttributeTable);
        stage.addActor(monster6AttributeTable);

        if (ObstacleEventHandler.locked) {
            stage.addActor(secretTable);
            logger.info("Alien Plant Detail Page Locked");
        }
        if (ObstacleEventHandler.locked2) {
            stage.addActor(secretTable2);
            logger.info("Aline thorn Detail Page Locked");
        }

        if (ObstacleEventHandler.locked3) {
            stage.addActor(secretTable3);
            logger.info("Meteorite Detail Page Locked");
        }

        if (ObstacleAttackTask.lock_use) {
            stage.addActor(secretTable4);
            logger.info("Aline Monkey Detail Page Locked");
        }

        if (ObstacleAttackTask.lock_use) {
            stage.addActor(secretTable5);
            logger.info("Face Worm Detail Page Locked");
        }

        if (ObstacleEventHandler.locked_ufo) {
            stage.addActor(secretTable6);
            logger.info("Spaceship Detail Page Locked");

        }


        stage.addActor(buttonTable);



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
