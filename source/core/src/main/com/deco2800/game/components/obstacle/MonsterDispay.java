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
    private Table secretTable;
    private Table secretTable2;
    private Table secretTable3;
    private Table secretTable4;
    private Table secretTable5;
    private Table secretTable6;
    private Table monster1AttributeTable;
    private Table monster2AttributeTable;
    private Table monster3AttributeTable;
    private Table monster4AttributeTable;
    private Table monster5AttributeTable;

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
        Label monsterName = new Label("Alien plant", skin);
        monsterName.setFontScale(1.3f);
        Image plantImg = new Image(ServiceLocator.getResourceService()
                .getAsset("images/obstacle_1_new.png", Texture.class));
        monster1Table.left().top().padLeft(480).padTop(45);
        monster1Table.setFillParent(true);
        monster1Table.add(unlockedChapterImg).size(90,90).padBottom(20);
        monster1Table.add(monsterName).padLeft(50).padBottom(70);



        monster1AttributeTable = new Table();
        Label monsterAttributes = new Label("attack: 3\nhealth: 100", skin);
        monsterAttributes.setColor(255,255,255,0.5f);
        monster1AttributeTable.left().top().padLeft(625).padTop(85);
        monster1AttributeTable.setFillParent(true);
        monster1AttributeTable.add(monsterAttributes);



        monster2Table = new Table();

        Label monster2Name = new Label("Alien Thorn", skin);
        monster2Name.setFontScale(1.3f);
        Image thornImg = new Image(ServiceLocator.getResourceService()
                .getAsset("images/obstacle2_vision2.png", Texture.class));
        monster2Table.left().top().padLeft(450).padTop(140);
        monster2Table.setFillParent(true);
        monster2Table.add(unlockedChapterImg2).size(130,130).padLeft(15).padBottom(30);
        monster2Table.add(monster2Name).padLeft(26).padBottom(35);


        monster2AttributeTable = new Table();
        Label monster2Attributes = new Label("attack: 3\nhealth: 100", skin);
        monster2Attributes.setColor(255,255,255,0.5f);
        monster2AttributeTable.left().top().padLeft(625).padTop(220);
        monster2AttributeTable.setFillParent(true);
        monster2AttributeTable.add(monster2Attributes);




        Monster3Table = new Table();
        Label monster3Name = new Label("Meteorite", skin);
        monster3Name.setFontScale(1.3f);
        Image stoneImg = new Image(ServiceLocator.getResourceService()
                .getAsset("images/stone1.png", Texture.class));
        Monster3Table.left().top().padLeft(480).padTop(285);
        Monster3Table.setFillParent(true);
        Monster3Table.add(unlockedChapterImg3).size(90,90).padLeft(10).padBottom(40);
        Monster3Table.add(monster3Name).padLeft(50).padBottom(55);

        monster3AttributeTable = new Table();
        Label monster3Attributes = new Label("attack: 3\nhealth: 100", skin);
        monster3Attributes.setColor(255,255,255,0.5f);
        monster3AttributeTable.left().top().padLeft(630).padTop(340);
        monster3AttributeTable.setFillParent(true);
        monster3AttributeTable.add(monster3Attributes);



        Monster4Table = new Table();
        Label monster4Name = new Label("Alien Monkey", skin);
        monster4Name.setFontScale(1.3f);
        Image monkeyImg = new Image(ServiceLocator.getResourceService()
                .getAsset("images/monkey_original.png", Texture.class));
        Monster4Table.left().top().padLeft(475).padTop(420);
        Monster4Table.setFillParent(true);
        Monster4Table.add(unlockedChapterImg4).size(130,130);
        Monster4Table.add(monster4Name).padBottom(55).padLeft(25);

        monster4AttributeTable = new Table();
        Label monster4Attributes = new Label("attack: 3\nhealth: 100", skin);
        monster4Attributes.setColor(255,255,255,0.5f);
        monster4AttributeTable.left().top().padLeft(635).padTop(480);
        monster4AttributeTable.setFillParent(true);
        monster4AttributeTable.add(monster4Attributes);


        Monster5Table = new Table();
        Label monster5Name = new Label("Face Worm", skin);
        monster5Name.setFontScale(1.3f);
        Image bugImg = new Image(ServiceLocator.getResourceService()
                .getAsset("images/Facehugger.png", Texture.class));
        Monster5Table.left().top().padLeft(435).padTop(530);
        Monster5Table.setFillParent(true);
        Monster5Table.add(unlockedChapterImg5).size(130,130).padLeft(15);
        Monster5Table.add(monster5Name).padLeft(40).padBottom(40);

        monster5AttributeTable = new Table();
        Label monster5Attributes = new Label("attack: 3\nhealth: 100", skin);
        monster5Attributes.setColor(255,255,255,0.5f);
        monster5AttributeTable.left().top().padLeft(635).padTop(600);
        monster5AttributeTable.setFillParent(true);
        monster5AttributeTable.add(monster5Attributes);









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

        Image secretImage = new Image(ServiceLocator.getResourceService()
                .getAsset("images/monster_menu/secret.jpg", Texture.class));
        secretTable = new Table().padBottom(600);
        secretTable.setFillParent(true);
        secretTable.add(secretImage).size(540,145).padBottom(25);

        Image secretImage2 = new Image(ServiceLocator.getResourceService()
                .getAsset("images/monster_menu/secret.jpg", Texture.class));
        secretTable2 = new Table().padBottom(250);
        secretTable2.setFillParent(true);
        secretTable2.add(secretImage2).size(540,145).padBottom(90);

        Image secretImage3 = new Image(ServiceLocator.getResourceService()
                .getAsset("images/monster_menu/secret.jpg", Texture.class));
        secretTable3 = new Table().padBottom(40);
        secretTable3.setFillParent(true);
        secretTable3.add(secretImage3).size(540,145).padBottom(30);

        Image secretImage4 = new Image(ServiceLocator.getResourceService()
                .getAsset("images/monster_menu/secret.jpg", Texture.class));
        secretTable4 = new Table().padTop(220);
        secretTable4.setFillParent(true);
        secretTable4.add(secretImage4).size(540,145).padBottom(30);

        Image secretImage5 = new Image(ServiceLocator.getResourceService()
                .getAsset("images/monster_menu/secret.jpg", Texture.class));
        secretTable5 = new Table().padTop(455);
        secretTable5.setFillParent(true);
        secretTable5.add(secretImage5).size(540,145).padBottom(30);

        Image secretImage6 = new Image(ServiceLocator.getResourceService()
                .getAsset("images/monster_menu/secret.jpg", Texture.class));
        secretTable6 = new Table().padTop(680);
        secretTable6.setFillParent(true);
        secretTable6.add(secretImage6).size(540,125).padBottom(30);

        // add the board to the stage first so that its can be under of score data
        stage.addActor(bgTable);
        stage.addActor(boardTable);
        stage.addActor(monster1Table);
        stage.addActor(monster2Table);
        stage.addActor(Monster3Table);
        stage.addActor(Monster4Table);
        stage.addActor(Monster5Table);
        stage.addActor(monster1AttributeTable);
        stage.addActor(monster2AttributeTable);
        stage.addActor(monster3AttributeTable);
        stage.addActor(monster4AttributeTable );
        stage.addActor(monster5AttributeTable);
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
        stage.addActor(secretTable6);


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
