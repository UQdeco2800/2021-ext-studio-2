package com.deco2800.game.components.score;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.game.GdxGame;
import com.deco2800.game.files.GameRecords;
import com.deco2800.game.files.GameRecords.Score;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import com.deco2800.game.utils.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ScoreHistoryDisplay extends UIComponent {

    private static final Logger logger = LoggerFactory.getLogger(ScoreHistoryDisplay.class);
    private static final int SCORE_DISPLAY_COUNT = 10;

    private final GdxGame game;
    private Table boardTable;
    private Table buttonTable;
    private Table bgTable;
    private Image board;

    public ScoreHistoryDisplay(GdxGame game) {
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

        //this table contains the button back to main menu
        buttonTable = new Table();
        buttonTable.bottom().right();
        buttonTable.padBottom(35f).padRight(52f);
        buttonTable.setFillParent(true);

        board = new Image(ServiceLocator.getResourceService()
                .getAsset("images/historyScoreBoard.png", Texture.class));

        float boardSideLength = 850f;
        boardTable.add(board).size(boardSideLength);
        buttonTable.add(mainMenuButton);

        //this table contains the bg image: the night city
        Image bgImage = new Image(ServiceLocator.getResourceService()
                .getAsset("images/historyScoreBg.png", Texture.class));
        bgTable = new Table();
        bgTable.setFillParent(true);
        bgTable.add(bgImage);


        // add the board to the stage first so that its can be under of score data
        stage.addActor(bgTable);
        stage.addActor(boardTable);
        stage.addActor(buttonTable);

        //prepare the data tables
        List<Table> scoreTablesList = new ArrayList<>();
        List<Table> dateTablesList = new ArrayList<>();
        float padBottomValue = 270;

        //prepare the score data.
        //The Score class contains actual score value and the associated date of gameplay.
        List<Score> pastScores = GameRecords.getHighestScores();

        //loop this list to create labels.
        int endIndex = Math.min(pastScores.size(), SCORE_DISPLAY_COUNT);
        for (Score score : pastScores.subList(0, endIndex)) {
            //score
            Table scoreDataTable = new Table();
            scoreDataTable.center();
            scoreDataTable.padBottom(padBottomValue).padLeft(300);
            scoreDataTable.setFillParent(true);
            Label scoreLabel = new Label(score.getScore() + "", skin, "large");
            scoreDataTable.add(scoreLabel);

            //date
            Table dateDataTable = new Table();
            dateDataTable.center();
            dateDataTable.padBottom(padBottomValue).padRight(300);
            dateDataTable.setFillParent(true);
            // Format to get the date, along with hours and minutes
            String dateText = DateTimeUtils.getFormattedDateTime(score.getDateTime());

            Label dateLabel =
                    new Label(dateText, skin, "large");
            dateDataTable.add(dateLabel);

            //add these two tables to the lists
            scoreTablesList.add(scoreDataTable);
            dateTablesList.add(dateDataTable);

            //now reduce the padding values
            //the history score board only display top 10
            if (padBottomValue < -430) {
                break;
            }
            padBottomValue = padBottomValue - 70;
        }

        for (Table table : scoreTablesList) {
            stage.addActor(table);
        }

        for (Table table : dateTablesList) {
            stage.addActor(table);
        }

        //use another table to display the game count
        Table gameCountTable = new Table();
        gameCountTable.bottom().left().padBottom(50).padLeft(50);
        Label gameCount =
                new Label("Game Count: " + scoreTablesList.size(),
                        new Label.LabelStyle(new BitmapFont(), Color.VIOLET));
        gameCount.setFontScale(2f);
        gameCountTable.add(gameCount);
        stage.addActor(gameCountTable);
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
