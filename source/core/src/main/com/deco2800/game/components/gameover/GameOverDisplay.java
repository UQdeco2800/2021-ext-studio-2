package com.deco2800.game.components.gameover;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Scaling;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.score.ScoringSystemV1;
import com.deco2800.game.files.GameRecords;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Settings menu display and logic. If you bork the settings, they can be changed manually in
 * DECO2800Game/settings.json under your home directory (This is C:/users/[username] on Windows).
 */
public class GameOverDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(GameOverDisplay.class);
    private final GdxGame game;

    private Table table;
    private double points = 0.0;
    private TextField pointText;
    private final ScoringSystemV1 scoringSystem = new ScoringSystemV1();

    public GameOverDisplay(GdxGame game) {
        super();
        this.game = game;
    }

    @Override
    public void create() {
        super.create();
        createGameOverTable();
    }

    private void createGameOverTable() {
        // Create components
        //Label pointsLabel = new Label("Points:", skin);

        table = new Table();
        table.setFillParent(true);
        points = GameRecords.getLatestScore().score;

        pointText = new TextField(String.valueOf(points), skin);
        pointText.setDisabled(true);



        Image tittle =
                new Image(
                        ServiceLocator.getResourceService()
                                .getAsset("images/gameOver.png", Texture.class));


        TextButton pointsLabel = new TextButton("Points: ", skin);
        TextButton playAgainButton = new TextButton("Click to play \n again", skin);
        TextButton mainMenuButton = new TextButton("Main Menu", skin);
        TextButton historyScoreButton = new TextButton("History Score", skin);

        pointsLabel.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {


                    }
                });

        playAgainButton.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.info("play again button clicked");
                        game.setScreen(GdxGame.ScreenType.MAIN_GAME);
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

        historyScoreButton.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.info("go to history score board button clicked");
                        game.setScreen(GdxGame.ScreenType.HISTORY_SCORES);
                    }
                });



        // Position Components on table
        Stack stack = new Stack();
        stack.setFillParent(true);
        stack.setTouchable(Touchable.disabled); //disable touch inputs so its clickthrough
        Image background = new Image(ServiceLocator.getResourceService()
                .getAsset("images/background.png", Texture.class));
        background.setScaling(Scaling.stretch);
        stack.add(background);

        Table bgTable = new Table();
        bgTable.setFillParent(true);

        Table table = new Table();
        table.add(tittle).bottom().padBottom(50f);
        table.row();
        table.add(pointsLabel).bottom();
        table.row();
        table.add(pointText).bottom().padBottom(15f);

        table.row();
        table.add(playAgainButton).bottom().padBottom(15f);
        table.row();
        table.add(mainMenuButton).bottom().padBottom(15f);
        table.row();
        table.add(historyScoreButton).bottom().padBottom(15f);
        table.row();
        table.setFillParent(true);

        stage.addActor(stack);
        stage.addActor(table);
    }

    @Override
    public void dispose() {
        table.clear();
        super.dispose();
    }

    @Override
    protected void draw(SpriteBatch batch) {

    }

    public void setPoints(double points) {
        this.points = points;
        this.pointText.setText(String.valueOf(points));
    }
}