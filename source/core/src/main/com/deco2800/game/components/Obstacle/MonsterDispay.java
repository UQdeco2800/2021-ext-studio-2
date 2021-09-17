package com.deco2800.game.components.Obstacle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
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

    private void createHistoryScoreBoard() {
        // Create components on the score board
        TextButton mainMenuButton = new TextButton("Main Menu", skin);
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
        monster1Table.left().top().padLeft(450).padTop(40);
        monster1Table.add(monsterName);
        monster1Table.setFillParent(true);
        monster1Table.add(plantImg).size(130,130).padLeft(85).padBottom(30);


        monster2Table = new Table();

        Label monster2Name = new Label("Alien Thorn", skin,"large");
        Image thornImg = new Image(ServiceLocator.getResourceService()
                .getAsset("images/obstacle2_vision2.png", Texture.class));
        monster2Table.left().top().padLeft(450).padTop(170);
        monster2Table.setFillParent(true);
        monster2Table.add(monster2Name);
        monster2Table.add(thornImg).size(130,130).padLeft(75).padBottom(30);


        Monster3Table = new Table();
        Label monster3Name = new Label("Meteorite", skin,"large");
        Image stoneImg = new Image(ServiceLocator.getResourceService()
                .getAsset("images/stone1.png", Texture.class));
        Monster3Table.left().top().padLeft(450).padTop(300);
        Monster3Table.setFillParent(true);
        Monster3Table.add(monster3Name);
        Monster3Table.add(stoneImg).size(120,120).padLeft(110).padBottom(40);


        Monster4Table = new Table();
        Label monster4Name = new Label("Alien Monkey", skin,"large");
        Image monkeyImg = new Image(ServiceLocator.getResourceService()
                .getAsset("images/monkey_original.png", Texture.class));
        Monster4Table.left().top().padLeft(450).padTop(470);
        Monster4Table.setFillParent(true);
        Monster4Table.add(monster4Name);
        Monster4Table.add(monkeyImg).size(120,120).padLeft(60);


        Monster5Table = new Table();
        Label monster5Name = new Label("Face Worm", skin,"large");
        Image bugImg = new Image(ServiceLocator.getResourceService()
                .getAsset("images/Facehugger.png", Texture.class));
        Monster5Table.left().top().padLeft(450).padTop(600);
        Monster5Table.setFillParent(true);
        Monster5Table.add(monster5Name);
        Monster5Table.add(bugImg).size(130,130).padLeft(90);










        //this table contains the button back to main menu
        buttonTable = new Table();
        buttonTable.bottom().right();
        buttonTable.padBottom(100f).padRight(110f);
//        buttonTable.center();

        buttonTable.setFillParent(true);
        buttonTable.add(mainMenuButton);

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
