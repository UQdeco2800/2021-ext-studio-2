package com.deco2800.game.components.score;

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

public class HistoryScoreDisplay extends UIComponent {

    private static final Logger logger = LoggerFactory.getLogger(HistoryScoreDisplay.class);
    private final GdxGame game;
    private Table scoreDataTable;
    private Table boardTable;
    private Table buttonTable;
    private Image board;

    public HistoryScoreDisplay(GdxGame game) {
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
        //prepare all the tables
        scoreDataTable = new Table();
        scoreDataTable.top().padTop(2.0f);
        scoreDataTable.setFillParent(true);
        boardTable = new Table();
        boardTable.center();
        boardTable.setFillParent(true);
        buttonTable = new Table();
        buttonTable.bottom().right();
        buttonTable.padBottom(35f).padRight(52f);
        buttonTable.setFillParent(true);

        board = new Image(ServiceLocator.getResourceService()
                .getAsset("images/historyScoreBoard.png", Texture.class));

        float boardSideLength = 800f;
        boardTable.add(board).size(boardSideLength);
        buttonTable.add(mainMenuButton);
        // add the board to the stage first so that its can be under of score data
        stage.addActor(boardTable);
        stage.addActor(scoreDataTable);
        stage.addActor(buttonTable);
    }

    @Override
    public void dispose() {
        scoreDataTable.clear();
        buttonTable.center();
        boardTable.clear();
        super.dispose();
    }

    @Override
    protected void draw(SpriteBatch batch) {

    }
}
