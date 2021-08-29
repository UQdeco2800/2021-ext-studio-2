package com.deco2800.game.components.gameover;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.game.GdxGame;
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

    private Table rootTable;
    private double points = 0.0;
    private TextField pointText;

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
        Label gameOverLabel = new Label("Game Over", skin);
        gameOverLabel.setFontScale(5.0f);
        gameOverLabel.setColor(Color.PINK);

        Label pointsLabel = new Label("Points:", skin);

        pointText = new TextField(String.valueOf(points), skin);
        pointText.setDisabled(true);

        TextButton playAgainButton = new TextButton("Click to play \n again", skin);
        TextButton mainMenuButton = new TextButton("Main Menu", skin);

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

        // Position Components on table
        Table table = new Table();
        table.add(gameOverLabel).top().padTop(10f);
        table.row().padTop(40f);
        table.add(pointsLabel).right();
        table.add(pointText).right().padRight(15f);
        table.row().padTop(25f);
        table.add(playAgainButton).bottom().padBottom(15f);
        table.add(mainMenuButton).bottom().padBottom(15f);
        table.setFillParent(true);
        stage.addActor(table);
    }

    @Override
    public void dispose() {
        rootTable.clear();
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