package com.deco2800.game.components.buff;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.obstacle.ObstacleEventHandler;
import com.deco2800.game.components.tasks.ObstacleAttackTask;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BuffDisplay extends UIComponent {

    private static final Logger logger = LoggerFactory.getLogger(BuffDisplay.class);
    private final GdxGame game;
    private Table boardTable;
    private Table buttonTable;
    private Table bgTable;
    private Table buff1Table;
    private Table buff2Table;
    private Table buff3Table;
    private Table buff4Table;
    private Table buff5Table;
    private Table buff6Table;
    private Table buff7Table;
    private Table buff8Table;
    private Table buff9Table;
    private Table buff1AttributeTable;
    private Table buff2AttributeTable;
    private Table buff3AttributeTable;
    private Table buff4AttributeTable;
    private Table buff5AttributeTable;
    private Table buff6AttributeTable;
    private Table buff7AttributeTable;
    private Table buff8AttributeTable;
    private Table buff9AttributeTable;
    private Image box;


    public BuffDisplay(GdxGame game) {
        this.game = game;
    }

    @Override
    public void create() {
        super.create();
        createBuffManualBoard();
    }

    /**
     * Returns an image button to be reused everywhere.
     *
     * @param path the image path
     * @return ImageButton to be displayed
     */
    private ImageButton getImageButton(String path) {
        return new ImageButton(new TextureRegionDrawable(new TextureRegion(
                new Texture(path))));
    }

    private void createBuffManualBoard() {
        // Create Button to the buff menu
        TextButton mainMenuButton = new TextButton("Main Menu", skin);

        ImageButton buffDescriptionImg = getImageButton("buff-debuff-manual/decrease_health1.png");
        ImageButton buffDescriptionImg2 = getImageButton("buff-debuff-manual/decrease_speed1.png");
        ImageButton buffDescriptionImg3 = getImageButton("buff-debuff-manual/dizziness1.png");
        ImageButton buffDescriptionImg4 = getImageButton("buff-debuff-manual/increase_health_limit1.png");
        ImageButton buffDescriptionImg5 = getImageButton("buff-debuff-manual/increase_health1.png");
        ImageButton buffDescriptionImg6 = getImageButton("buff-debuff-manual/low_statu_hunger1.png");
        ImageButton buffDescriptionImg7 = getImageButton("buff-debuff-manual/low_statu_thirst1.png");
        ImageButton buffDescriptionImg8 = getImageButton("buff-debuff-manual/poisoning1.png");
        ImageButton buffDescriptionImg9 = getImageButton("buff-debuff-manual/recovery.png");
        mainMenuButton.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.info("return menu button clicked");
                        game.setScreen(GdxGame.ScreenType.MAIN_MENU);
                    }
                });


        //this table contains the board
        boardTable = new Table();
        boardTable.center();
        boardTable.setFillParent(true);

        buff1Table = new Table();
        Label buffName = new Label("Decrease Health", skin);
        buffName.setFontScale(1.3f);
        buff1Table.left().top().padLeft(420).padTop(45);
        buff1Table.setFillParent(true);
        buff1Table.add(buffDescriptionImg).size(45, 45).padBottom(20);
        buff1Table.add(buffName).padLeft(40).padBottom(50);

        buff1AttributeTable = new Table();
        Label buffAttributes = new Label("Decrease player Health", skin);
        buffAttributes.setColor(255, 255, 255, 0.5f);
        buff1AttributeTable.left().top().padLeft(505).padTop(75);
        buff1AttributeTable.setFillParent(true);
        buff1AttributeTable.add(buffAttributes);

        buff2Table = new Table();
        Label buff2Name = new Label("Decrease Speed", skin);
        buff2Name.setFontScale(1.3f);
        buff2Table.left().top().padLeft(405).padTop(120);
        buff2Table.setFillParent(true);
        buff2Table.add(buffDescriptionImg2).size(45, 45).padLeft(15).padBottom(30);
        buff2Table.add(buff2Name).padLeft(36).padBottom(35);


        buff2AttributeTable = new Table();
        Label buff2Attributes = new Label("Decrease player  Speed", skin);
        buff2Attributes.setColor(255, 255, 255, 0.5f);
        buff2AttributeTable.left().top().padLeft(510).padTop(160);
        buff2AttributeTable.setFillParent(true);
        buff2AttributeTable.add(buff2Attributes);


        buff3Table = new Table();
        Label buff3Name = new Label("Dizziness", skin);
        buff3Name.setFontScale(1.3f);
        buff3Table.left().top().padLeft(405).padTop(195);
        buff3Table.setFillParent(true);
        buff3Table.add(buffDescriptionImg3).size(45, 45).padLeft(15).padBottom(30);
        buff3Table.add(buff3Name).padLeft(36).padBottom(35);

        buff3AttributeTable = new Table();
        Label buff3Attributes = new Label("player Dizziness", skin);
        buff3Attributes.setColor(255, 255, 255, 0.5f);
        buff3AttributeTable.left().top().padLeft(505).padTop(245);
        buff3AttributeTable.setFillParent(true);
        buff3AttributeTable.add(buff3Attributes);


        buff4Table = new Table();
        Label buff4Name = new Label("Increase Health Limit", skin);
        buff4Name.setFontScale(1.3f);
        buff4Table.left().top().padLeft(420).padTop(280);
        buff4Table.setFillParent(true);
        buff4Table.add(buffDescriptionImg4).size(45, 45);
        buff4Table.add(buff4Name).padBottom(36).padLeft(36);

        buff4AttributeTable = new Table();
        Label buff4Attributes = new Label("Increase player's Health Limit", skin);
        buff4Attributes.setColor(255, 255, 255, 0.5f);
        buff4AttributeTable.left().top().padLeft(505).padTop(310);
        buff4AttributeTable.setFillParent(true);
        buff4AttributeTable.add(buff4Attributes);


        buff5Table = new Table();
        Label buff5Name = new Label("Increase Health", skin);
        buff5Name.setFontScale(1.3f);
        buff5Table.left().top().padLeft(405).padTop(345);
        buff5Table.setFillParent(true);
        buff5Table.add(buffDescriptionImg5).size(45, 45).padLeft(15);
        buff5Table.add(buff5Name).padLeft(36).padBottom(36);

        buff5AttributeTable = new Table();
        Label buff5Attributes = new Label("Increase player's Health", skin);
        buff5Attributes.setColor(255, 255, 255, 0.5f);
        buff5AttributeTable.left().top().padLeft(505).padTop(385);
        buff5AttributeTable.setFillParent(true);
        buff5AttributeTable.add(buff5Attributes);


        buff6Table = new Table();
        Label buff6Name = new Label("Low Status Hunger", skin);
        buff5Name.setFontScale(1.3f);
        buff6Table.left().top().padLeft(405).padTop(425);
        buff6Table.setFillParent(true);
        buff6Table.add(buffDescriptionImg6).size(45, 45).padLeft(15);
        buff6Table.add(buff6Name).padLeft(36).padBottom(36);

        buff6AttributeTable = new Table();
        Label buff6Attributes = new Label("player's Low Status Hunger", skin);
        buff6Attributes.setColor(255, 255, 255, 0.5f);
        buff6AttributeTable.left().top().padLeft(505).padTop(470);
        buff6AttributeTable.setFillParent(true);
        buff6AttributeTable.add(buff6Attributes);




        buff7Table = new Table();
        Label buff7Name = new Label("Low Status Thirst", skin);
        buff7Name.setFontScale(1.3f);
        buff7Table.left().top().padLeft(405).padTop(505);
        buff7Table.setFillParent(true);
        buff7Table.add(buffDescriptionImg7).size(45, 45).padLeft(15);
        buff7Table.add(buff7Name).padLeft(36).padBottom(36);

        buff7AttributeTable = new Table();
        Label buff7Attributes = new Label("player's Low Status Thirst", skin);
        buff7Attributes.setColor(255, 255, 255, 0.5f);
        buff7AttributeTable.left().top().padLeft(505).padTop(545);
        buff7AttributeTable.setFillParent(true);
        buff7AttributeTable.add(buff7Attributes);



        buff8Table = new Table();
        Label buff8Name = new Label("Posisoning", skin);
        buff8Name.setFontScale(1.3f);
        buff8Table.left().top().padLeft(405).padTop(590);
        buff8Table.setFillParent(true);
        buff8Table.add(buffDescriptionImg8).size(45, 45).padLeft(15);
        buff8Table.add(buff8Name).padLeft(36).padBottom(36);

        buff8AttributeTable = new Table();
        Label buff8Attributes = new Label("When the character is attacked by some\n obstacles, the character " +
                "will receive the\n effect of poisoning and slowly reduce \nthe health value.", skin);
        buff8Attributes.setColor(255, 255, 255, 0.5f);
        buff8Attributes.setFontScale(0.8f);
        buff8AttributeTable.left().top().padLeft(505).padTop(620);
        buff8AttributeTable.setFillParent(true);
        buff8AttributeTable.add(buff8Attributes);




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


        boardTable.add(box).size(700, 900);

        //this table contains the background image
        Image bgImage = new Image(ServiceLocator.getResourceService()
                .getAsset("images/monster_menu/background.png", Texture.class));
        bgTable = new Table();
        bgTable.setFillParent(true);
        bgTable.add(bgImage).size(1400, 1000);
        // add the board to the stage first so that its can be under of score data
        stage.addActor(bgTable);
        stage.addActor(boardTable);
        stage.addActor(buff1Table);
        stage.addActor(buff2Table);
        stage.addActor(buff3Table);
        stage.addActor(buff4Table);
        stage.addActor(buff5Table);
        stage.addActor(buff6Table);
        stage.addActor(buff7Table);
        stage.addActor(buff8Table);
        //stage.addActor(buff9Table);
        stage.addActor(buff1AttributeTable);
        stage.addActor(buff2AttributeTable);
        stage.addActor(buff3AttributeTable);
        stage.addActor(buff4AttributeTable);
        stage.addActor(buff5AttributeTable);
        stage.addActor(buff6AttributeTable);
        stage.addActor(buff7AttributeTable);
        stage.addActor(buff8AttributeTable);
        //stage.addActor(buff9AttributeTable);
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
