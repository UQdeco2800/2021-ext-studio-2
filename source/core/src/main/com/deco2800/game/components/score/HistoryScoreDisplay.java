package com.deco2800.game.components.score;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.game.GdxGame;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HistoryScoreDisplay extends UIComponent {

    private static final Logger logger = LoggerFactory.getLogger(HistoryScoreDisplay.class);
    private final GdxGame game;
    private Table table;
    //private Table tableForTitle;
    //private Table tableForButton;

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
        Label historyScoreLabel = new Label("History Scores", skin);
        historyScoreLabel.setFontScale(2.0f);
        Label scoreLabel = new Label("Your Scores", skin);
        scoreLabel.setFontScale(1.0f);
        Label dateLabel = new Label("Date", skin);
        dateLabel.setFontScale(1.0f);
        TextButton mainMenuButton = new TextButton("Main Menu", skin);

        //need to figure out how to read a file and display.

        mainMenuButton.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.info("return menu button clicked");
                        game.setScreen(GdxGame.ScreenType.MAIN_MENU);
                    }
                });

        // Position each label and assets.
        table = new Table();
        table.add(historyScoreLabel);

        table.add(scoreLabel);
        table.add(dateLabel);

        table.add(mainMenuButton);
        table.setFillParent(true);
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
}
