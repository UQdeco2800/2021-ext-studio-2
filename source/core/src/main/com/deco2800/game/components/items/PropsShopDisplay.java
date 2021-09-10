package com.deco2800.game.components.items;


import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.game.GdxGame;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class PropsShopDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(PropsShopDisplay.class);
    private final GdxGame game;
    private TextField pointText;
    private Table tableForScores;
    private Table tableForGolds;
    private Table tableForMainMenu;
    private Image heartImage;
    private File file;
    private FileReader fileReader;
    private int goldGotLastRound;

    public PropsShopDisplay(GdxGame game) {
        this.game = game;
        this.file = new File("gold.txt");
    }

    @Override
    public void create() {
        super.create();
        createHistoryScoreBoard(0);
    }

    public void update() {

        goldGotLastRound = getGold();
        createHistoryScoreBoard(goldGotLastRound);
    }

    private int getGold() {

        int resultNumber = 0;
        char [] result = new char[10];
        int finalResult = 0;
        int timer;
        int dummy = 0;

        try{
            this.fileReader = new FileReader(this.file);
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
                    return dummy;
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

    private void createHistoryScoreBoard(int goldGotLastRound) {
        // Create components on the score board
        Label historyScoreLabel = new Label("Props shoP", skin);
        historyScoreLabel.setFontScale(3.0f);
        historyScoreLabel.setColor(Color.BLACK);
        Label scoreLabel = new Label("Your golds:", skin);
        float heartSideLength = 30f;
        //heartImage = new Image(ServiceLocator.getResourceService().getAsset("core/assets/images/heart.png", Texture.class));

        pointText = new TextField(String.valueOf(goldGotLastRound), skin);
        pointText.setDisabled(true);
        scoreLabel.setFontScale(1.0f);

        TextButton mainMenuButton = new TextButton("Main Menu", skin);

        mainMenuButton.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.info("return menu button clicked");
                        game.setScreen(GdxGame.ScreenType.MAIN_MENU);
                    }
                });

        // Position each label and assets.

        tableForGolds = new Table();
        tableForGolds.bottom().right();
        tableForGolds.add(scoreLabel).left().padLeft(10f);
        tableForGolds.add(pointText).right().padRight(10f);

        tableForScores = new Table();
        tableForScores.top();
        //tableForScores.add(heartImage).size(heartSideLength).pad(5);
        tableForScores.add(historyScoreLabel).top().padTop(40f);
       // tableForScores.row().padTop(25f);

        tableForMainMenu = new Table();
        tableForMainMenu.bottom().left();
        tableForMainMenu.add(mainMenuButton).bottom().padBottom(15f);
        tableForScores.setFillParent(true);
        tableForGolds.setFillParent(true);
        tableForMainMenu.setFillParent(true);
        stage.addActor(tableForGolds);
        stage.addActor(tableForScores);
        stage.addActor(tableForMainMenu);
    }

    @Override
    public void dispose() {
        tableForScores.clear();
        super.dispose();
    }

    @Override
    protected void draw(SpriteBatch batch) {

    }
}