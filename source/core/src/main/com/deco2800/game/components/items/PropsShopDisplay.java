package com.deco2800.game.components.items;


import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.game.GdxGame;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import java.io.FileReader;
import java.io.IOException;

public class PropsShopDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(PropsShopDisplay.class);
    private final GdxGame game;

    private File file;
    private FileReader fileReader;

    public PropsShopDisplay(GdxGame game) {
        this.game = game;
        this.file = new File("./core/assets/gold.txt");
    }

    @Override
    public void create() {
        super.create();
        createPropsShopBoard(0);
    }

    public void update() {
        int goldGotLastRound = getGold();
        createPropsShopBoard(goldGotLastRound);
    }

    private int getGold() {

        int resultNumber;
        char [] result = new char[10];
        int finalResult = 0;
        int timer;
        int dummy;

        try{
            this.fileReader = new FileReader(this.file.getName());
            resultNumber = fileReader.read(result);
            if (!Character.isDigit(result[0])) {
                fileReader.close();
                return 0;
            }
            timer = resultNumber;
            for (char c : result) {
                if (timer == 0) {
                    break;
                }
                dummy = Integer.parseInt(String.valueOf(Character.valueOf(c)));
                if (resultNumber == 1) {
                    fileReader.close();
                    finalResult = dummy;
                    return finalResult;
                }
                finalResult += (Math.pow(10, timer-1)) * dummy;
                timer--;
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return finalResult;
    }

    private void createPropsShopBoard(int goldGotLastRound) {
        Label propsShopLabel = new Label("Props shoP", skin);
        propsShopLabel.setFontScale(3.0f);
        propsShopLabel.setColor(Color.BLACK);
        Label goldGotLabel = new Label("Your $:", skin);
        float heartSideLength = 30f;


        Image heartImage =
                new Image(
                        ServiceLocator.getResourceService()
                                .getAsset("images/heart.png", Texture.class));
        Image addFoodImage =
                new Image(
                        ServiceLocator.getResourceService()
                                .getAsset("images/Items/props/add_food.png", Texture.class));
        Image addHealthImage =
                new Image(
                        ServiceLocator.getResourceService()
                                .getAsset("images/Items/props/add_health.png", Texture.class));
        Image addWaterImage =
                new Image(
                        ServiceLocator.getResourceService()
                                .getAsset("images/Items/props/add_water.png", Texture.class));
        Image addShieldImage =
                new Image(
                        ServiceLocator.getResourceService()
                                .getAsset("images/Items/props/shield.png", Texture.class));
        TextField pointText = new TextField(String.valueOf(goldGotLastRound), skin);
        pointText.setDisabled(true);
        goldGotLabel.setFontScale(1.0f);

        TextButton mainMenuButton = new TextButton("Main Menu", skin);
        TextButton foodPriceButton = new TextButton("$ 2", skin);
        TextButton healthPriceButton = new TextButton("$ 3", skin);
        TextButton waterPriceButton = new TextButton("$ 2", skin);
        TextButton shieldPriceButton = new TextButton("$ 5", skin);

        mainMenuButton.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.info("return menu button clicked");
                        game.setScreen(GdxGame.ScreenType.MAIN_MENU);
                    }
                });

        foodPriceButton.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.info("return menu button clicked");
                        game.setScreen(GdxGame.ScreenType.MAIN_MENU);
                    }
                });

        healthPriceButton.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.info("return menu button clicked");
                        game.setScreen(GdxGame.ScreenType.MAIN_MENU);
                    }
                });

        waterPriceButton.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.info("return menu button clicked");
                        game.setScreen(GdxGame.ScreenType.MAIN_MENU);
                    }
                });

        shieldPriceButton.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.info("return menu button clicked");
                        game.setScreen(GdxGame.ScreenType.MAIN_MENU);
                    }
                });



        // Position each label and assets.

        Table tableForGolds = new Table();
        tableForGolds.top().right();
        tableForGolds.add(goldGotLabel).left().padLeft(10f);
        tableForGolds.add(pointText).right().padRight(10f);

        Table tableForPropsShop = new Table();
        tableForPropsShop.top();
        tableForPropsShop.add(heartImage).size(heartSideLength).pad(5);
        tableForPropsShop.add(propsShopLabel).top().padTop(40f);

        Table tableForPropsFood = new Table();
        tableForPropsFood.center().left();
        tableForPropsFood.add(addFoodImage).size(150f).pad(400).padTop(80);

        Table tableForPropsButton = new Table();
        tableForPropsButton.center().left().row();
        tableForPropsButton.add(foodPriceButton).pad(445).padTop(350);

        Table tableForPropsHealth = new Table();
        tableForPropsHealth.center().right();
        tableForPropsHealth.add(addHealthImage).size(150f).pad(400).padTop(80);

        Table tableForHealthButton = new Table();
        tableForHealthButton.center().right().row();
        tableForHealthButton.add(healthPriceButton).pad(445).padTop(350);

        Table tableForPropsWater = new Table();
        tableForPropsWater.center().left().row();
        tableForPropsWater.add(addWaterImage).size(150f).pad(400).padTop(550);

        Table tableForWaterButton = new Table();
        tableForWaterButton.center().left().row().row();
        tableForWaterButton.add(waterPriceButton).pad(445).padTop(850);

        Table tableForPropsShield = new Table();
        tableForPropsShield.center().right().row();
        tableForPropsShield.add(addShieldImage).size(150f).pad(400).padTop(550);

        Table tableForShieldButton = new Table();
        tableForShieldButton.center().right().row().row();
        tableForShieldButton.add(shieldPriceButton).pad(445).padTop(850);

        Table tableForMainMenu = new Table();
        tableForMainMenu.bottom().left();
        tableForMainMenu.add(mainMenuButton).bottom().padBottom(15f);
        tableForPropsFood.setFillParent(true);
        tableForPropsShop.setFillParent(true);
        tableForGolds.setFillParent(true);
        tableForMainMenu.setFillParent(true);
        tableForPropsHealth.setFillParent(true);
        tableForPropsWater.setFillParent(true);
        tableForPropsShield.setFillParent(true);
        tableForPropsButton.setFillParent(true);
        tableForHealthButton.setFillParent(true);
        tableForWaterButton.setFillParent(true);
        tableForShieldButton.setFillParent(true);
        stage.addActor(tableForShieldButton);
        stage.addActor(tableForWaterButton);
        stage.addActor(tableForHealthButton);
        stage.addActor(tableForPropsButton);
        stage.addActor(tableForPropsShield);
        stage.addActor(tableForPropsWater);
        stage.addActor(tableForPropsHealth);
        stage.addActor(tableForPropsFood);
        stage.addActor(tableForGolds);
        stage.addActor(tableForPropsShop);
        stage.addActor(tableForMainMenu);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    protected void draw(SpriteBatch batch) {

    }
}